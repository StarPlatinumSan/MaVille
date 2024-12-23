import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { AuthContext } from "../contexts/AuthContext";

function MesRequetes() {
	const [mesRequetes, setMesRequetes] = useState([]);
	const { auth } = useContext(AuthContext);

	useEffect(() => {
		const fetchMesRequetes = async () => {
			try {
				const response = await axios.get(`${import.meta.env.VITE_API_URL}/api/users/requetes`, {
					params: { email: auth.user.email },
				});
				setMesRequetes(response.data);
			} catch (error) {
				console.error("Erreur lors de la récupération de vos requêtes :", error);
			}
		};

		fetchMesRequetes();
	}, [auth.user.email]);

	const accepterSoumission = async (id) => {
		try {
			await axios.post(`${import.meta.env.VITE_API_URL}/api/requetes/${id}/accept`, {}, { params: { email: auth.user.email } });

			setMesRequetes((prevRequetes) => prevRequetes.map((requete) => (requete.id === id ? { ...requete, isAccepted: true } : requete)));

			alert("Soumission acceptée avec succès !");
		} catch (error) {
			console.error("Erreur lors de l'acceptation de la soumission :", error);
			alert("Une erreur est survenue lors de l'acceptation de la soumission.");
		}
	};

	const fermerRequete = async (id) => {
		try {
			await axios.delete(`${import.meta.env.VITE_API_URL}/api/requetes/${id}`);

			setMesRequetes((prevRequetes) => prevRequetes.filter((requete) => requete.id !== id));

			alert("Requête fermée avec succès !");
		} catch (error) {
			console.error("Erreur lors de la fermeture de la requête :", error);
			alert("Une erreur est survenue lors de la fermeture de la requête.");
		}
	};

	if (mesRequetes.length === 0) {
		return <p className="filtreEmpty">Vous n'avez pas encore soumis de requêtes.</p>;
	}

	return (
		<div className="text-black mesRequetes">
			<h1>Mes Requêtes</h1>
			{mesRequetes.map((requete) => (
				<div key={requete.id} className="requeteCard">
					<h2>{requete.titre}</h2>
					<p>
						<strong>Description : </strong>
						{requete.description}
					</p>
					<p>
						<strong>Type : </strong>
						{requete.type}
					</p>
					<p>
						<strong>Adresse : </strong>
						{requete.adresse}
					</p>
					<p>
						<strong>Date : </strong>
						{requete.date}
					</p>
					<p>
						<strong>Intervenant : </strong>
						{requete.intervenant ? requete.intervenant : "Aucun intervenant pour l'instant"}
					</p>
					{requete.isConfirmed ? (
						<button className="btn btn-danger" onClick={() => fermerRequete(requete.id)}>
							Candidature retenue, fermer requête
						</button>
					) : (
						<button className="btn btn-success" onClick={() => accepterSoumission(requete.id)} disabled={requete.isConfirmed || requete.isAccepted || !requete.hasSoumission}>
							{requete.isAccepted && !requete.isConfirmed ? "Soumission acceptée, en attente de l'intervenant" : requete.hasSoumission ? "Accepter la soumission" : "En attente de soumission"}
						</button>
					)}
				</div>
			))}
		</div>
	);
}

export default MesRequetes;
