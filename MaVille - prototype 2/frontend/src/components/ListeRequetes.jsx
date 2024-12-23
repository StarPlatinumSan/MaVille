import { useState, useEffect, useContext } from "react";
import axios from "axios";
import { AuthContext } from "../contexts/AuthContext";

function ListeRequetes() {
	const [requetes, setRequetes] = useState([]);
	const { auth } = useContext(AuthContext);

	useEffect(() => {
		const fetchRequetes = async () => {
			const response = await axios.get("${process.env.REACT_APP_API_URL}/api/requetes");
			setRequetes(response.data);
		};
		fetchRequetes();
	}, []);

	const soumissionner = async (id) => {
		try {
			await axios.post(
				"${process.env.REACT_APP_API_URL}/api/requetes/${id}/soumissionner",
				{},
				{
					params: { intervenantEmail: auth.user.email },
				}
			);

			// Met à jour l'état local
			setRequetes((prevRequetes) => prevRequetes.map((requete) => (requete.id === id ? { ...requete, hasSoumission: true, intervenant: auth.user.email } : requete)));

			alert("Soumission réussie !");
		} catch (error) {
			console.error("Erreur lors de la soumission :", error);
		}
	};

	const annulerSoumission = async (id) => {
		try {
			await axios.post(
				"${process.env.REACT_APP_API_URL}/api/requetes/${id}/unsoumissionner",
				{}, // Corps vide car nous utilisons des paramètres
				{
					params: { intervenantEmail: auth.user.email }, // Ajout des paramètres ici
				}
			);

			// Met à jour l'état local
			setRequetes((prevRequetes) => prevRequetes.map((requete) => (requete.id === id ? { ...requete, hasSoumission: false, intervenant: null, isConfirmed: false } : requete)));
			alert("Soumission annulée !");
		} catch (error) {
			console.error("Erreur :", error);
		}
	};

	const accepterSoumission = async (id) => {
		try {
			await axios.post("${process.env.REACT_APP_API_URL}/api/requetes/${id}/accept", {
				email: auth.user.email,
			});
			alert("Soumission acceptée !");
		} catch (error) {
			console.error("Erreur :", error);
		}
	};

	const confirmerCandidature = async (id) => {
		try {
			await axios.post(
				"${process.env.REACT_APP_API_URL}/api/requetes/${id}/confirmer",
				{}, // Corps vide
				{
					params: { intervenantEmail: auth.user.email }, // Passer l'email de l'intervenant
				}
			);

			// Mettre à jour l'état local après confirmation
			setRequetes((prevRequetes) => prevRequetes.map((requete) => (requete.id === id ? { ...requete, isConfirmed: true } : requete)));

			alert("Candidature confirmée avec succès !");
		} catch (error) {
			console.error("Erreur lors de la confirmation :", error);
			alert("Une erreur est survenue lors de la confirmation.");
		}
	};

	// Filtrer les requêtes confirmées par d'autres intervenants
	const filteredRequetes = requetes.filter((requete) => (!requete.isConfirmed || requete.intervenant === auth.user.email) && (!requete.hasSoumission || requete.intervenant === auth.user.email));

	return (
		<div className="text-black listeRequetes">
			<h1 className="requeteTitle">Liste des Requêtes</h1>
			{filteredRequetes.map((requete) => (
				<div key={requete.id} className="requeteElement">
					<h2>{requete.titre}</h2>
					<p>{requete.description}</p>
					<p>
						<strong>Adresse : </strong>
						{requete.adresse}
					</p>
					<p>
						<strong>Date espéré : </strong> {requete.date}
					</p>
					{requete.isAccepted &&
						requete.intervenant === auth.user.email &&
						(requete.isConfirmed ? (
							<button className="btn btn-success" disabled>
								Candidature Retenue
							</button>
						) : (
							<button className="btn btn-success" onClick={() => confirmerCandidature(requete.id)}>
								Confirmer candidature
							</button>
						))}
					{!requete.hasSoumission && !requete.isAccepted && (
						<button className="btn btn-primary" onClick={() => soumissionner(requete.id)}>
							Soumissionner
						</button>
					)}
					{requete.hasSoumission && !requete.isAccepted && requete.intervenant === auth.user.email && (
						<button className="btn btn-danger" onClick={() => annulerSoumission(requete.id)}>
							Annuler Soumission
						</button>
					)}
					{requete.hasSoumission && requete.isAccepted && requete.intervenant !== auth.user.email && <p className="text-info">En attente de confirmation de l'intervenant</p>}
				</div>
			))}
		</div>
	);
}

export default ListeRequetes;
