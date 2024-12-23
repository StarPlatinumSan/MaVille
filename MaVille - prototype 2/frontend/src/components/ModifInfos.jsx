import React, { useState, useEffect, useContext } from "react";
import "../styles/modifinfos.scss";
import { AuthContext } from "../contexts/AuthContext";

const ModifInfos = () => {
	const { auth } = useContext(AuthContext);
	const [projets, setProjets] = useState([]);
	const [statutModifie, setStatutModifie] = useState({});
	const [message, setMessage] = useState(null);
	const apiBaseUrl = import.meta.env.VITE_API_URL;

	useEffect(() => {
		const fetchProjets = async () => {
			try {
				const response = await fetch(`${apiBaseUrl}/api/projets`);
				if (response.ok) {
					const data = await response.json();
					const filteredData = data.filter((projet) => projet.nomIntervenant === auth.user.username);
					setProjets(filteredData);
				} else {
					console.error("Erreur lors de la récupération des projets.");
				}
			} catch (error) {
				console.error("Erreur réseau : ", error);
			}
		};

		fetchProjets();
	}, []);

	const handleStatutChange = (id, nouveauStatut) => {
		setStatutModifie((prev) => ({ ...prev, [id]: nouveauStatut }));
	};

	const handleSubmitStatut = async (id) => {
		const nouveauStatut = statutModifie[id];
		if (!nouveauStatut) {
			setMessage("Veuillez sélectionner un statut avant de soumettre.");
			return;
		}

		try {
			const response = await fetch(`${apiBaseUrl}/api/projets/${id}`, {
				method: "PUT",
				headers: { "Content-Type": "text/plain" },
				body: nouveauStatut,
			});

			if (response.ok) {
				setMessage("Statut mis à jour avec succès !");
				const updatedProjets = projets.map((projet) => (projet.id === id ? { ...projet, statut: nouveauStatut } : projet));
				setProjets(updatedProjets);
				setStatutModifie((prev) => ({ ...prev, [id]: "" }));
			} else {
				setMessage("Erreur lors de la mise à jour du statut.");
			}
		} catch (error) {
			console.error("Erreur réseau : ", error);
			setMessage("Une erreur s'est produite.");
		}
	};

	const handleDeleteProjet = async (id) => {
		try {
			const response = await fetch(`${apiBaseUrl}/api/projets/${id}`, {
				method: "DELETE",
			});

			if (response.ok) {
				setMessage("Projet supprimé avec succès !");
				setProjets(projets.filter((projet) => projet.id !== id));

				setTimeout(() => {
					setMessage(null);
				}, 3000);
			} else {
				setMessage("Erreur lors de la suppression du projet.");
			}
		} catch (error) {
			console.error("Erreur réseau : ", error);
			setMessage("Une erreur s'est produite.");
		}
	};

	return (
		<div className="modif-infos">
			<h1>Modifier les Informations des Projets</h1>
			{projets.length === 0 ? (
				<p>Aucun projet disponible pour le moment.</p>
			) : (
				<ul>
					{projets.map((projet) => (
						<li key={projet.id}>
							<div>
								<strong>Nom :</strong> {projet.nom}
							</div>
							<div>
								<strong>Arrondissement :</strong> {projet.arrondissement}
							</div>
							<div>
								<strong>Statut :</strong> {projet.statut}
							</div>
							<div>
								<strong>Raison :</strong> {projet.raison}
							</div>
							<div>
								<strong>Dates :</strong> {projet.dates}
							</div>
							<div>
								<strong>Catégorie d'intervenant :</strong> {projet.categorieIntervenant}
							</div>
							<div>
								<strong>Nom de l'intervenant :</strong> {projet.nomIntervenant}
							</div>
							<div className="projet-actions">
								<label htmlFor={`statut-${projet.id}`}>Modifier le statut :</label>
								<select id={`statut-${projet.id}`} value={statutModifie[projet.id] || ""} onChange={(e) => handleStatutChange(projet.id, e.target.value)}>
									<option value="">-- Choisir un statut --</option>
									<option value="Prévu">Prévu</option>
									<option value="En cours">En cours</option>
									<option value="Terminé">Terminé</option>
								</select>
								<div className="button-group">
									<button onClick={() => handleSubmitStatut(projet.id)}>Mettre à jour</button>
									<button onClick={() => handleDeleteProjet(projet.id)}>Supprimer</button>
								</div>
							</div>
						</li>
					))}
				</ul>
			)}
			{message && <p className="message">{message}</p>}
		</div>
	);
};

export default ModifInfos;
