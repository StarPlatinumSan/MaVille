import React, { useState, useContext } from "react";
import { AuthContext } from "../contexts/AuthContext";
import axios from "axios";

function Requete() {
	const { auth } = useContext(AuthContext);
	const [formData, setFormData] = useState({
		titre: "",
		description: "",
		type: "",
		adresse: "",
		date: "",
		notif: false,
	});
	const [message, setMessage] = useState("");

	const changeForm = (e) => {
		const { name, value, type, checked } = e.target;
		setFormData({
			...formData,
			[name]: type === "checkbox" ? checked : value,
		});
	};

	const submitForm = async (e) => {
		e.preventDefault();
		try {
			const formInfo = { ...formData, email: auth.user.email };

			const response = await axios.post("${process.env.REACT_APP_API_URL}/api/requetes", formInfo);
			setMessage("La requête a été envoyée avec succès !");
			alert("La requête a été envoyée avec succès !");
			setFormData({
				titre: "",
				description: "",
				type: "",
				adresse: "",
				date: "",
				notif: false,
			});

			setTimeout(() => {
				setMessage("");
			}, 5000);
		} catch (error) {
			alert("Une erreur est survenue pendant l'envoi de la requête.");
			console.error("Erreur lors de la soumission du formulaire:", error);
		}
	};

	return (
		<div className="requeteDiv">
			<div className="travauxTitreMain">Remplir un formulaire de requête de travail</div>;
			<form onSubmit={submitForm} className="formulaireRequete">
				<label htmlFor="type">Titre du travail à réaliser:</label>
				<input type="text" name="titre" id="titre" value={formData.titre} onChange={changeForm} required />

				<label htmlFor="description">Description détaillée:</label>
				<textarea type="textarea" name="description" id="description" value={formData.description} onChange={changeForm} required />

				<label htmlFor="adresse">Type de travaux:</label>
				<input type="text" name="type" id="type" value={formData.type} onChange={changeForm} required />

				<label htmlFor="adresse">Adresse:</label>
				<input type="text" name="adresse" id="adresse" value={formData.adresse} onChange={changeForm} required />

				<label htmlFor="date">Date de début espéré:</label>
				<input type="date" name="date" id="date" value={formData.date} onChange={changeForm} required />

				<div className="checkBoxNotif">
					<label htmlFor="notif">Recevoir une notification en cas de réponse ?</label>
					<input type="checkbox" name="notif" id="notif" value={formData.notif} onChange={changeForm} />
				</div>

				<button type="submit">Envoyer la requête</button>
			</form>
		</div>
	);
}

export default Requete;
