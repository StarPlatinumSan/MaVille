import React, { useState, useContext } from "react";
import "../styles/soumettre.scss";
import { AuthContext } from "../contexts/AuthContext";
import axios from "axios";

const Soumettre = () => {
	const { auth } = useContext(AuthContext);
	const [nom, setNom] = useState("");
	const [arrondissement, setArrondissement] = useState("");
	const [raison, setRaison] = useState("");
	const [dateDebut, setDateDebut] = useState("");
	const [dateFin, setDateFin] = useState("");
	const [categorieIntervenant, setCategorieIntervenant] = useState("");
	const [message, setMessage] = useState(null);
	const [dateError, setDateError] = useState("");
	const [conflits, setConflits] = useState([]);
	const apiBaseUrl = import.meta.env.VITE_API_URL;

	const validateDates = () => {
		const today = new Date();
		today.setHours(0, 0, 0, 0);

		const debut = new Date(dateDebut);
		const fin = new Date(dateFin);

		if (debut < today) {
			setDateError("La date de début ne peut pas être dans le passé");
			return false;
		}

		if (fin <= debut) {
			setDateError("La date de fin doit être après la date de début");
			return false;
		}

		setDateError("");
		return true;
	};

	const verifierConflitsPlages = async () => {
		if (!arrondissement) return [];

		try {
			const response = await axios.get(`${apiBaseUrl}/api/users/plages/quartier?quartier=${arrondissement}`);
			const plages = response.data;

			const conflitsTrouves = plages
				.map((plage) => {
					const jourSemaine = plage.jour;
					const heureDebut = plage.heureDebut;
					const heureFin = plage.heureFin;

					return {
						jour: jourSemaine,
						heureDebut: heureDebut,
						heureFin: heureFin,
					};
				})
				.filter((plage) => plage);

			return conflitsTrouves;
		} catch (error) {
			return [];
		}
	};

	const traduireJour = (jour) => {
		const traductions = {
			MONDAY: "Lundi",
			TUESDAY: "Mardi",
			WEDNESDAY: "Mercredi",
			THURSDAY: "Jeudi",
			FRIDAY: "Vendredi",
			SATURDAY: "Samedi",
			SUNDAY: "Dimanche",
		};
		return traductions[jour] || jour;
	};

	const formaterHeure = (heure) => {
		if (Array.isArray(heure)) {
			return `${heure[0].toString().padStart(2, "0")}:${heure[1].toString().padStart(2, "0")}`;
		} else if (typeof heure === "string") {
			return heure;
		}
		return heure;
	};

	const removeCircularReferences = (obj) => {
		const seen = new WeakSet();
		return JSON.parse(
			JSON.stringify(obj, (key, value) => {
				if (typeof value === "object" && value !== null) {
					if (seen.has(value)) {
						return;
					}
					seen.add(value);
				}
				return value;
			})
		);
	};

	const handleSubmit = async (e) => {
		e.preventDefault();

		if (!validateDates()) {
			return;
		}

		const conflitsTrouves = await verifierConflitsPlages();
		setConflits(conflitsTrouves);

		const data = {
			nom,
			arrondissement,
			statut: "Prévu",
			raison,
			dates: `${dateDebut} - ${dateFin}`,
			categorieIntervenant,
			nomIntervenant: auth.user.username,
		};

		try {
			const cleanedData = removeCircularReferences(data);
			const response = await axios.post("/api/projets", cleanedData);

			if (response.status === 200) {
				let messageSucces = "Projet soumis avec succès !";

				if (conflitsTrouves.length > 0) {
					messageSucces += "\n\nAttention : Des résidents ont indiqué des préférences pour les périodes suivantes :";
					conflitsTrouves.forEach((conflit) => {
						messageSucces += `\n- ${traduireJour(conflit.jour)} de ${formaterHeure(conflit.heureDebut)} à ${formaterHeure(conflit.heureFin)}`;
					});
					messageSucces += "\n\nVeuillez tenir compte de ces préférences dans la planification des travaux.";
				}

				setMessage(messageSucces);
				setNom("");
				setArrondissement("");
				setRaison("");
				setDateDebut("");
				setDateFin("");
				setCategorieIntervenant("");
				setDateError("");
				setConflits([]);
			} else {
				setMessage("Erreur lors de la soumission.");
			}
		} catch (error) {
			setMessage(error.message);
		}
	};

	return (
		<div className="soumettre-form">
			<h1>Soumettre un Projet</h1>
			<form onSubmit={handleSubmit}>
				<div>
					<label htmlFor="nom">Nom :</label>
					<input type="text" id="nom" value={nom} onChange={(e) => setNom(e.target.value)} required />
				</div>
				<div>
					<label htmlFor="arrondissement">Arrondissement :</label>
					<select id="arrondissement" value={arrondissement} onChange={(e) => setArrondissement(e.target.value)} required>
						<option value="">-- Choisir un arrondissement --</option>
						{[
							"Ahuntsic-Cartierville",
							"Anjou",
							"Côte-des-Neiges-Notre-Dame-de-Grâce",
							"Lachine",
							"LaSalle",
							"Le Plateau-Mont-Royal",
							"Le Sud-Ouest",
							"L'Île-Bizard-Sainte-Geneviève",
							"Mercier-Hochelaga-Maisonneuve",
							"Montréal-Nord",
							"Outremont",
							"Pierrefonds-Roxboro",
							"Rivière-des-Prairies-Pointe-aux-Trembles",
							"Rosemont-La Petite-Patrie",
							"Saint-Laurent",
							"Saint-Léonard",
							"Verdun",
							"Ville-Marie",
							"Villeray-Saint-Michel-Parc-Extension",
						].map((arr) => (
							<option key={arr} value={arr}>
								{arr}
							</option>
						))}
					</select>
				</div>
				<div>
					<label htmlFor="raison">Raison :</label>
					<input type="text" id="raison" value={raison} onChange={(e) => setRaison(e.target.value)} required />
				</div>
				<div>
					<label htmlFor="dateDebut">Date de début :</label>
					<input type="date" id="dateDebut" value={dateDebut} min={new Date().toISOString().split("T")[0]} onChange={(e) => setDateDebut(e.target.value)} required />
				</div>
				<div>
					<label htmlFor="dateFin">Date de fin :</label>
					<input type="date" id="dateFin" value={dateFin} min={dateDebut} onChange={(e) => setDateFin(e.target.value)} required />
				</div>
				{dateError && (
					<div className="error-message" style={{ color: "red", margin: "10px 0" }}>
						{dateError}
					</div>
				)}
				<div>
					<label htmlFor="categorieIntervenant">Catégorie d'intervenant :</label>
					<select id="categorieIntervenant" value={categorieIntervenant} onChange={(e) => setCategorieIntervenant(e.target.value)} required>
						<option value="">-- Choisir une catégorie --</option>
						<option value="Ville de Montréal">Ville de Montréal</option>
						<option value="Entrepreneur sous contrat avec la Ville de Montréal">Entrepreneur sous contrat avec la Ville de Montréal</option>
						<option value="Entreprise privé">Entreprise privé</option>
					</select>
				</div>
				<div>
					<label htmlFor="nomIntervenant">Nom de l'intervenant :</label>
					<input type="text" id="nomIntervenant" value={auth?.user?.username || "Utilisateur inconnu"} disabled required />
				</div>
				<button type="submit" disabled={!!dateError}>
					Soumettre
				</button>
			</form>
			{message && <div className="message">{message}</div>}
			{conflits.length > 0 && (
				<div className="conflits-container">
					<h3>Plages horaires préférées des résidents :</h3>
					<ul>
						{conflits.map((conflit, index) => (
							<li key={index}>
								{traduireJour(conflit.jour)} de {formaterHeure(conflit.heureDebut)} à {formaterHeure(conflit.heureFin)}
							</li>
						))}
					</ul>
				</div>
			)}
		</div>
	);
};

export default Soumettre;
