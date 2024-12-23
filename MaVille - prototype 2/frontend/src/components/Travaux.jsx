import { useState, useEffect } from "react";
import axios from "axios";
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import "leaflet/dist/leaflet.css";

function Travaux() {
	const [travaux, setTravaux] = useState([]);
	const [projets, setProjets] = useState([]);
	const [recherche, setRecherche] = useState("");
	const [typeTravaux, setTypeTravaux] = useState("tous");
	const [showMap, setShowMap] = useState(false);
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		const fetchTravaux = async () => {
			try {
				const travauxResponse = await axios.get("${process.env.REACT_APP_API_URL}/api/travaux");
				setTravaux(travauxResponse.data.result.records);
			} catch (error) {
				console.error("Erreur lors de la récupération des travaux :", error);
			}
		};

		const fetchProjets = async () => {
			try {
				const response = await fetch("/api/projets");
				const data = await response.json();
				setProjets(data);
			} catch (error) {
				console.error("Erreur lors de la récupération des projets locaux :", error);
			}
		};

		const fetchData = async () => {
			setLoading(true);
			await Promise.all([fetchTravaux(), fetchProjets()]);
			setLoading(false);
		};

		fetchData();
	}, []);

	const handleRecherche = (event) => {
		setRecherche(event.target.value);
	};

	const highlightText = (text, keyword) => {
		if (!recherche) return text;

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

	const enCours = (travail) => {
		const current = new Date();
		const startDate = new Date(travail.duration_start_date || travail.dates?.start);
		const endDate = new Date(travail.duration_end_date || travail.dates?.end);

		return startDate <= current && current <= endDate;
	};

	const aVenir = (travail) => {
		const current = new Date();

		const troisMois = new Date(current);
		troisMois.setMonth(troisMois.getMonth() + 3);

		const startDateStr = travail.duration_start_date || travail.dates?.split(" - ")[0];
		const startDate = startDateStr ? new Date(startDateStr) : null;

		return startDate > current && startDate <= troisMois;
	};

	const travauxFiltres = [...travaux, ...projets].filter((travail) => {
		const rechercheNom = (travail.occupancy_name || travail.nom || "").toLowerCase().includes(recherche.toLowerCase());
		const rechercheArrondissement = (travail.boroughid || travail.arrondissement || "").toLowerCase().includes(recherche.toLowerCase());

		if (typeTravaux === "tous") {
			return rechercheNom || rechercheArrondissement;
		} else if (typeTravaux === "encours") {
			return (rechercheNom || rechercheArrondissement) && enCours(travail);
		} else if (typeTravaux === "avenir") {
			return (rechercheNom || rechercheArrondissement) && aVenir(travail);
		}
		return false;
	});

	const selectionTravail = (type) => {
		setTypeTravaux(type);
	};

	const toggleMap = () => {
		setShowMap((prev) => !prev);
	};

	if (loading) {
		return <p className="travauxLoading">Chargement des travaux...</p>;
	}

	return (
		<div className="travauxDiv">
			<h1 className="travauxTitreMain">Travaux</h1>
			<input type="text" placeholder="Rechercher un travail ou projet" className="travauxInput" onChange={handleRecherche} />
			<div className="buttonsTemps">
				<button className={`btn ${typeTravaux === "tous" ? "selected" : ""}`} onClick={() => selectionTravail("tous")}>
					Tous
				</button>
				<button className={`btn ${typeTravaux === "encours" ? "selected" : ""}`} onClick={() => selectionTravail("encours")}>
					En cours
				</button>
				<button className={`btn ${typeTravaux === "avenir" ? "selected" : ""}`} onClick={() => selectionTravail("avenir")}>
					À venir
				</button>
			</div>
			<div className="btnFooter">
				<h2 className="travauxTitre">Afficher la carte de Montréal</h2>
				<input type="checkbox" onChange={toggleMap} checked={showMap} />
			</div>
			<div className="separation"></div>
			{showMap ? (
				<MapContainer center={[45.5017, -73.5673]} zoom={12} style={{ height: "500px", width: "100%" }}>
					<TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" attribution="&copy; OpenStreetMap contributors" />
					{travauxFiltres
						.filter((travail) => travail.latitude && travail.longitude)
						.map((travail, index) => (
							<Marker key={index} position={[parseFloat(travail.latitude), parseFloat(travail.longitude)]}>
								<Popup className="travauxPopup">
									<h3>{travail.occupancy_name || travail.nom}</h3>
									<p>
										<strong>Arrondissement:</strong> {travail.boroughid || travail.arrondissement}
									</p>
									<p>
										<strong>Statut:</strong> {travail.currentstatus || travail.statut}
									</p>
								</Popup>
							</Marker>
						))}
				</MapContainer>
			) : (
				<ul className="travauxList">
					{travauxFiltres.map((travail, index) => (
						<li key={travail._id || `projet-${index}`} className="travauxItem">
							<h3 className="travauxTitre">Nom: {highlightText(travail.occupancy_name || travail.nom, recherche)}</h3>
							<p className="travauxInfo">
								<strong>Arrondissement:</strong> {highlightText(travail.boroughid || travail.arrondissement, recherche)}
							</p>
							<p className="travauxInfo">
								<strong>Statut:</strong> {travail.currentstatus || travail.statut}
							</p>
							<p className="travauxInfo">
								<strong>Raison:</strong> {travail.reason_category || travail.raison}
							</p>
							<p className="travauxInfo">
								<strong>Dates:</strong> {travail.duration_start_date ? `${new Date(travail.duration_start_date).toLocaleDateString()} - ${new Date(travail.duration_end_date).toLocaleDateString()}` : travail.dates}
							</p>
							<div className="separation"></div>
						</li>
					))}
				</ul>
			)}
		</div>
	);
}

export default Travaux;
