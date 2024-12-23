import { useState, useEffect, useContext } from "react";
import { AuthContext } from "../contexts/AuthContext";
import { useNavigate } from "react-router-dom";
import Travaux from "./Travaux";
import Entraves from "./Entraves";
import Requete from "./Requete";
import Plage from "./Plage";
import Notifications from "./Notifications";
import MesRequetes from "./MesRequetes";

import ListeRequetes from "./ListeRequetes";
import Soumettre from "./Soumettre";
import ModifInfos from "./ModifInfos";

function Account() {
	const { auth, logoutUser } = useContext(AuthContext);
	const navigate = useNavigate();

	const [selectedOption, setSelectedOption] = useState(null);
	const [showTravaux, setShowTravaux] = useState(false);
	const [showEntraves, setShowEntraves] = useState(false);
	const [showRequete, setShowRequete] = useState(false);
	const [showPlage, setShowPlage] = useState(false);

	const [showListeRequetes, setShowListeRequetes] = useState(false);
	const [showSoumettre, setShowSoumettre] = useState(false);
	const [showModifInfos, setShowModifInfos] = useState(false);
	const [showMesRequetes, setShowMesRequetes] = useState(false);

	const [showScrollTop, setShowScrollTop] = useState(false);
	const [showNotifications, setShowNotifications] = useState(false);

	useEffect(() => {
		const handleScroll = () => {
			if (window.scrollY > 300) {
				setShowScrollTop(true);
			} else {
				setShowScrollTop(false);
			}
		};

		window.addEventListener("scroll", handleScroll);

		return () => {
			window.removeEventListener("scroll", handleScroll);
		};
	}, []);

	useEffect(() => {
		const handleResize = () => {
			const sideBar = document.querySelector(".sideBar");
			const menuMobile = document.querySelector(".menuMobile");
			if (window.innerWidth > 900) {
				sideBar.classList.remove("open");
				menuMobile.style.backgroundImage = "url(../../public/menu.svg)";
				menuMobile.style.width = "40px";
				menuMobile.style.height = "40px";
			}
		};
		window.addEventListener("resize", handleResize);
		return () => {
			window.removeEventListener("resize", handleResize);
		};
	}, []);

	if (!auth.isAuthenticated) {
		navigate("/");
		return null;
	}

	const cleanUp = () => {
		setShowTravaux(false);
		setShowEntraves(false);
		setShowRequete(false);
		setShowPlage(false);
		setShowListeRequetes(false);
		setShowSoumettre(false);
		setShowModifInfos(false);
		setShowMesRequetes(false);
	};

	const selection = (option) => {
		cleanUp();
		setSelectedOption(option);

		if (option === "option1") {
			setShowTravaux(true);
		} else if (option === "option2") {
			setShowEntraves(true);
		} else if (option === "option3") {
			setShowRequete(true);
		} else if (option === "option4") {
			setShowPlage(true);
		} else if (option === "option5") {
			setShowListeRequetes(true);
		} else if (option === "option6") {
			setShowSoumettre(true);
		} else if (option === "option7") {
			setShowModifInfos(true);
		} else if (option === "option8") {
			setShowMesRequetes(true);
		}
	};

	const hideNotifications = () => {
		setShowNotifications(false);
	};

	const showSideBar = () => {
		const sideBar = document.querySelector(".sideBar");
		sideBar.classList.toggle("open");
	};

	const closeSideBar = () => {
		const sideBar = document.querySelector(".sideBar");
		const menuMobile = document.querySelector(".menuMobile");
		sideBar.classList.remove("open");
		menuMobile.style.backgroundImage = "url(../../public/menu.svg)";
		menuMobile.style.width = "40px";
		menuMobile.style.height = "40px";
	};

	const handleLogout = () => {
		logoutUser();
		navigate("/");
	};

	const scrollToTop = () => {
		window.scrollTo({ top: 0, behavior: "smooth" });
	};

	return (
		<div className="accountDiv" onClick={hideNotifications}>
			<div className="sideBar">
				<button className="closeSideBar" onClick={closeSideBar}></button>
				<button
					className="notification"
					style={{ zIndex: "2" }}
					onClick={(e) => {
						e.stopPropagation();
						setShowNotifications((prev) => !prev);
					}}
				>
					{" "}
					{!showNotifications && <div className="notifs"></div>}
					<div className={`notifsInfo ${showNotifications ? "visible" : ""}`} onClick={(e) => e.stopPropagation()}>
						<Notifications />
					</div>
				</button>
				<div className="accountUsername sideBarDiv">
					<svg xmlns="http://www.w3.org/2000/svg" className="userImg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" class="size-6">
						<path strokeLinecap="round" strokeLinejoin="round" d="M15.75 6a3.75 3.75 0 1 1-7.5 0 3.75 3.75 0 0 1 7.5 0ZM4.501 20.118a7.5 7.5 0 0 1 14.998 0A17.933 17.933 0 0 1 12 21.75c-2.676 0-5.216-.584-7.499-1.632Z" />
					</svg>{" "}
					<span>Bienvenue, {auth.user.username}</span>{" "}
				</div>

				<span>Rôle : {auth.user.role}</span>

				<button onClick={handleLogout} className="btn deconnectMobile">
					Se déconnecter
				</button>
			</div>
			{showScrollTop && (
				<button onClick={scrollToTop} className="scrollTopButton">
					↑ Retour en haut
				</button>
			)}

			<header className="accountHeader">
				<div className="accountGreeting hiddenMobile">
					<div className="accountUsername">
						<svg xmlns="http://www.w3.org/2000/svg" className="userImg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
							<path stroke-linecap="round" stroke-linejoin="round" d="M15.75 6a3.75 3.75 0 1 1-7.5 0 3.75 3.75 0 0 1 7.5 0ZM4.501 20.118a7.5 7.5 0 0 1 14.998 0A17.933 17.933 0 0 1 12 21.75c-2.676 0-5.216-.584-7.499-1.632Z" />
						</svg>{" "}
						<span>Bienvenue, {auth.user.username}</span>{" "}
					</div>

					<span>Rôle : {auth.user.role}</span>
				</div>

				<h1 className="accountTitle" onClick={() => selection(null)}>
					<span>MaVille - Montréal</span>
				</h1>

				<div className="accountControl">
					<button className="menuMobile" onClick={showSideBar}></button>
					<button
						className="notification hiddenMobile"
						style={{ zIndex: "2" }}
						onClick={(e) => {
							e.stopPropagation();
							setShowNotifications((prev) => !prev);
						}}
					>
						{!showNotifications && <div className="notifs"></div>}

						<div className={`notifsInfo ${showNotifications ? "visible" : ""}`} onClick={(e) => e.stopPropagation()}>
							<Notifications />
						</div>
					</button>

					<button onClick={handleLogout} className="btn hiddenMobile">
						Se déconnecter
					</button>
				</div>
			</header>
			<div className="accountContainer">
				{auth.user.role === "Intervenant" ? (
					<div className="select">
						<div className={`options optionsIntervenant ${selectedOption === "option5" ? "selected" : ""}`} onClick={() => selection("option5")}>
							<h3>Consulter la liste des requêtes de travail</h3>
						</div>

						<div className={`options optionsIntervenant ${selectedOption === "option6" ? "selected" : ""}`} onClick={() => selection("option6")}>
							<h3>Soumettre un nouveau projet</h3>
						</div>

						<div className={`options optionsIntervenant ${selectedOption === "option7" ? "selected" : ""}`} onClick={() => selection("option7")}>
							<h3>Mettre à jour les informations d'un projet</h3>
						</div>
					</div>
				) : (
					<div className="select">
						<div className={`options ${selectedOption === "option1" ? "selected" : ""}`} onClick={() => selection("option1")}>
							<div className="optionsImg" id="listeTravauxImg">
								<div className="optionsOpacity"></div>
							</div>
							<h3>Consulter la liste des travaux en cours et à venir</h3>
						</div>

						<div className={`options ${selectedOption === "option2" ? "selected" : ""}`} onClick={() => selection("option2")}>
							<div className="optionsImg" id="entravesImg">
								<div className="optionsOpacity"></div>
							</div>
							<h3>Consulter les entraves</h3>
						</div>

						<div className={`options ${selectedOption === "option3" ? "selected" : ""}`} onClick={() => selection("option3")}>
							<div className="optionsImg" id="requeteImg">
								<div className="optionsOpacity"></div>
							</div>
							<h3>Soumettre une requête de travail</h3>
						</div>

						<div className={`options ${selectedOption === "option4" ? "selected" : ""}`} onClick={() => selection("option4")}>
							<div className="optionsImg" id="plageImg">
								<div className="optionsOpacity"></div>
							</div>
							<h3>Planifier une plage horaire</h3>
						</div>

						<div className={`options ${selectedOption === "option8" ? "selected" : ""}`} onClick={() => selection("option8")}>
							<div className="optionsImg" id="mesRequetesImg">
								<div className="optionsOpacity"></div>
							</div>
							<h3>Voir mes requêtes</h3>
						</div>
					</div>
				)}

				{!showTravaux && !showEntraves && !showRequete && !showPlage && !showListeRequetes && !showSoumettre && !showModifInfos && !showMesRequetes && (
					<div className="accountBase">
						{auth.user.role === "Intervenant" ? (
							<div className="intervenantBase">
								<h1 className="titleBase">Bienvenue sur votre profil d'intervenant MaVille</h1>
								<p className="txt">Vous pouvez consulter la liste des requêtes de travail des résidents.</p>
								<p className="txt">Vous pouvez soumettre un nouveau projet, ou encore mettre à jour les informations d'un projet déjà existant.</p>
							</div>
						) : (
							<div className="residentBase">
								<h1 className="titleBase">Bienvenue sur votre profil de résident MaVille</h1>
								<p className="txt">Vous pouvez consulter la liste des travaux en cours et à venir, consulter les entraves, planifier votre plage horaire et soumettre une requête de travail.</p>
								<p className="txt">Vous pouvez aussi vous abonner aux notifications personnalisées concernant les travaux en cours ou à venir.</p>
								<p className="txt">
									Vous pouvez à tout moment cliquer sur le titre <strong>MaVille - Montréal</strong> pour retourner sur cet affichage.
								</p>
							</div>
						)}
					</div>
				)}

				{showTravaux && <Travaux />}
				{showEntraves && <Entraves />}
				{showRequete && <Requete />}
				{showPlage && <Plage />}
				{showListeRequetes && <ListeRequetes />}
				{showSoumettre && <Soumettre />}
				{showModifInfos && <ModifInfos />}
				{showMesRequetes && <MesRequetes />}
			</div>
		</div>
	);
}

export default Account;
