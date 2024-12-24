import { useState, useEffect } from "react";
import Register from "./Register";
import Login from "./Login";

function Lobby() {
	const [showLogin, setShowLogin] = useState(false);
	const [showRegister, setShowRegister] = useState(false);

	useEffect(() => {
		fetch(`${import.meta.env.VITE_API_URL}/api/message`)
			.then((response) => {
				if (!response.ok) {
					throw new Error(`HTTP error! status: ${response.status}`);
				}
				return response.text();
			})
			.then((data) => console.log("SUCCESS: ", data))
			.catch((error) => console.error("Error fetching data: ", error));
	}, []);

	const setShowAuth = (bool) => {
		const leftSide = document.querySelector(".leftSide");
		setShowLogin(bool);

		console.log("VITE_API_URL:", import.meta.env.VITE_API_URL);

		if (bool) {
			leftSide.style.display = "none";
		} else {
			leftSide.style.display = "flex";
		}
	};

	const setShowCreate = (bool) => {
		const leftSide = document.querySelector(".leftSide");
		setShowRegister(bool);

		if (bool) {
			leftSide.style.display = "none";
		} else {
			leftSide.style.display = "flex";
		}
	};

	return (
		<>
			<main className="container">
				<section className="leftSide">
					<h1 className="titleMain">MaVille</h1>
					<p className="txt txtMain">
						MaVille est une application interactive qui informe les citoyens de Montréal sur les projets de construction, les travaux en cours et leurs impacts, pour simplifier leurs déplacements et leur compréhension de l'évolution urbaine.
					</p>
					<button className="authMain buttonMain" onClick={() => setShowAuth(true)}>
						{" "}
						Se connecter
					</button>
					<div className="elseConnect">ou</div>
					<button className="creerMain buttonMain" onClick={() => setShowCreate(true)}>
						Créer un compte
					</button>
				</section>

				{showLogin && (
					<div className="authSection">
						<button className="btnRetour" onClick={() => setShowAuth(false)}>
							Retour
						</button>
						<Login />
					</div>
				)}
				{showRegister && (
					<div className="authSection">
						<button className="btnRetour" onClick={() => setShowCreate(false)}>
							Retour
						</button>
						<Register />
					</div>
				)}
			</main>
		</>
	);
}

export default Lobby;
