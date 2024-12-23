import { useState, useEffect } from "react";
import axios from "axios";

function Entraves() {
	const [entraves, setEntraves] = useState([]);
	const [rechercheEntrave, setRechercheEntrave] = useState("");
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		const fetchEntraves = async () => {
			try {
				const response = await axios.get(`${import.meta.env.VITE_API_URL}/api/entraves`);
				setEntraves(response.data.result.records);
				setLoading(false);
			} catch (error) {
				console.error("Erreur lors de la recherche des entraves:", error);
			}
		};

		fetchEntraves();
	}, []);

	const recherche = (event) => {
		setRechercheEntrave(event.target.value);
	};

	/* Cette fonction ne m'appartient pas, c'est basically du code fait par copilote */
	const highlightText = (text, keyword) => {
		if (!rechercheEntrave) return text;

		const regex = new RegExp(`(${keyword})`, "gi");
		const parts = text.split(regex);

		return parts.map((part, index) =>
			regex.test(part) ? (
				<span key={index} className="highlight">
					{part}
				</span>
			) : (
				part
			)
		);
	};

	const entravesFiltres = entraves.filter((entraves) => entraves.shortname.toLowerCase().includes(rechercheEntrave.toLowerCase()));

	if (loading) {
		return <p className="travauxLoading">Chargement des entraves...</p>;
	}

	return (
		<div className="travauxDiv">
			<h1 className="travauxTitreMain">Entraves</h1>
			<input type="text" placeholder="Rechercher une entrave" className="travauxInput" onChange={recherche} />
			<div className="separation"></div>
			{entraves.length === 0 ? (
				<p>Aucune entrave est en cours ou le API n'a pas renvoyé de données.</p>
			) : (
				<ul className="entravesList">
					{entravesFiltres.map((entrave) => (
						<li key={entrave._id} className="entravesItem">
							<h3 className="travauxTitre">{highlightText(entrave.shortname, rechercheEntrave)}</h3>
							<p className="travauxInfo">
								<strong className="travauxStrong">Nom de la rue: </strong>
								{entrave.streetid}
							</p>
							<p className="travauxInfo">
								<strong className="travauxStrong">Impact: </strong>
								{entrave.streetimpacttype}
							</p>
							<p className="travauxInfo">
								<strong className="travauxStrong">ID: </strong>
								{entrave._id}
							</p>
							<div className="separation"></div>
						</li>
					))}
				</ul>
			)}
		</div>
	);
}

export default Entraves;
