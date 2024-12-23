import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { AuthContext } from "../contexts/AuthContext";

function Plage() {
	const [plages, setPlages] = useState([]);
	const [jour, setJour] = useState("MONDAY");
	const [heureDebut, setHeureDebut] = useState("09:00");
	const [heureFin, setHeureFin] = useState("12:00");

	const { auth } = useContext(AuthContext);
	const userEmail = auth.user.email;

	useEffect(() => {
		// Récupérer les plages horaires depuis le backend
		axios
			.get("/api/users/plages", {
				params: {
					email: userEmail,
				},
			})
			.then((response) => {
				setPlages(response.data);
			})
			.catch((error) => {
				console.error("Erreur lors de la récupération des plages :", error);
			});
	}, [userEmail]);

	const ajouterPlage = () => {
		// Vérifie si la plage existe déjà
		const existeDeja = plages.some((plage) => {
			const jourMatch = plage.jour === jour;
			const heureDebutMatch = plage.heureDebut.slice(0, 5) === heureDebut; // Normalise au format HH:mm
			const heureFinMatch = plage.heureFin.slice(0, 5) === heureFin; // Normalise au format HH:mm
			return jourMatch && heureDebutMatch && heureFinMatch;
		});

		if (existeDeja) {
			alert("Cette plage horaire existe déjà.");
			return;
		}

		// Ajouter une nouvelle plage via API
		axios
			.post(
				"/api/users/plages",
				{
					jour,
					heureDebut,
					heureFin,
				},
				{
					params: {
						email: userEmail,
					},
				}
			)
			.then((response) => {
				alert("Plage ajoutée avec succès !");
				// Recharger les plages après ajout
				axios
					.get("/api/users/plages", {
						params: {
							email: userEmail,
						},
					})
					.then((response) => {
						setPlages(response.data);
					});
			})
			.catch((error) => {
				console.error("Erreur lors de l'ajout de la plage :", error);
			});
	};

	const supprimerPlage = (plage) => {
		axios
			.delete("/api/users/plages", {
				data: plage,
				params: {
					email: userEmail,
				},
			})
			.then(() => {
				setPlages(plages.filter((p) => !(p.jour === plage.jour && p.heureDebut === plage.heureDebut && p.heureFin === plage.heureFin)));
				alert("Plage supprimée avec succès !");
			})
			.catch((error) => {
				console.error("Erreur lors de la suppression de la plage :", error);
			});
	};

	return (
		<div className="plageMain">
			<h1 className="travauxTitreMain">Fournir des plages horaires</h1>
			<div className="selectionPlage">
				<select value={jour} onChange={(e) => setJour(e.target.value)}>
					{["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"].map((day) => (
						<option key={day} value={day}>
							{day}
						</option>
					))}
				</select>
				<input type="time" value={heureDebut} onChange={(e) => setHeureDebut(e.target.value)} />
				<input type="time" value={heureFin} onChange={(e) => setHeureFin(e.target.value)} />
				<button onClick={ajouterPlage} className="btn">
					Ajouter
				</button>
			</div>
			<div className="text-black">
				<h2>Plages horaires existantes :</h2>
				<div className="separation"></div>
				<ul className="plagesList">
					{plages.map((plage, index) => (
						<li key={index}>
							{plage.jour} : {plage.heureDebut} - {plage.heureFin}
							<button className="btn" onClick={() => supprimerPlage(plage)}>
								Supprimer
							</button>
						</li>
					))}
				</ul>
				<div className="separation"></div>
			</div>
		</div>
	);
}

export default Plage;
