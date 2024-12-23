import { useState } from "react";
import axios from "axios";

function Register() {
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [email, setEmail] = useState("");
	const [phone, setPhone] = useState("");
	const [address, setAddress] = useState("");
	const [codePostal, setCodePostal] = useState("");
	const [birthDate, setBirthDate] = useState("");
	const [key, setKey] = useState("");
	const [type, setType] = useState("");
	const [categorieIntervenant, setCategorieIntervenant] = useState("Ville de Montréal");
	const [message, setMessage] = useState("");

	const handleRegister = async (e) => {
		e.preventDefault();

		if (phone === "") {
			setPhone("NULL");
		}

		const age = calculateAge(birthDate);

		const user = type === "resident" ? { username, password, age, email, phone, address, codePostal } : { username, password, age, email, key, typeIntervenant: categorieIntervenant };

		try {
			const response = await axios.post(`${process.env.VITE_API_URL}/api/auth/register`, user);
			setMessage(response.data);
			setTimeout(() => {
				setMessage(null);
			}, 5000);
		} catch (error) {
			console.log("Error details:", error.response ? error.response.data : error.message);
			setMessage("Une erreur est survenue pendant la création du compte.");
		}
	};

	const calculateAge = (birthDate) => {
		const aujd = new Date();
		const naissance = new Date(birthDate);
		let age = aujd.getFullYear() - naissance.getFullYear();
		const monthDiff = aujd.getMonth() - naissance.getMonth();

		if (monthDiff < 0 || (monthDiff === 0 && aujd.getDate() < naissance.getDate())) {
			age--;
		}
		console.log(age);
		return age;
	};

	return (
		<div className="authDiv">
			{type === "" ? (
				<div className="selectionType">
					<h2>Quel type de compte souhaitez-vous créer ?</h2>
					<button className="btn" onClick={() => setType("resident")}>
						Résident
					</button>
					<button className="btn" onClick={() => setType("intervenant")}>
						Intervenant
					</button>
				</div>
			) : (
				<div className="authDiv registerMobile">
					<h2 className="authTitle">Créer un compte ({type === "resident" ? "Résident" : "Intervenant"})</h2>
					<form onSubmit={handleRegister}>
						<div className="groupAuth">
							<input type="text" placeholder="Nom d'utilisateur" value={username} className="authInput" onChange={(e) => setUsername(e.target.value)} required />
							<input type="password" placeholder="Mot de passe" value={password} className="authInput" onChange={(e) => setPassword(e.target.value)} required />
						</div>
						<div className="groupAuth">
							<label> Date de naissance: </label>
							<input type="date" value={birthDate} className="birthInput authInput" onChange={(e) => setBirthDate(e.target.value)} required />
						</div>

						{type === "resident" && (
							<>
								<div className="groupAuth">
									<input type="email" placeholder="Email" value={email} className="authInput" onChange={(e) => setEmail(e.target.value)} required />
									<input type="tel" placeholder="Téléphone" value={phone} className="authInput" onChange={(e) => setPhone(e.target.value)} />
								</div>

								<div className="groupAuth">
									<input type="text" placeholder="Adresse" value={address} className="authInput" onChange={(e) => setAddress(e.target.value)} required />
									<input type="text" placeholder="Code postal" value={codePostal} className="authInput" onChange={(e) => setCodePostal(e.target.value)} required />
								</div>
							</>
						)}

						{type === "intervenant" && (
							<>
								<div className="groupAuth">
									<input type="email" placeholder="Email" value={email} className="authInput" onChange={(e) => setEmail(e.target.value)} required />
								</div>

								<div className="groupAuth">
									<label htmlFor="categorieIntervenant">Catégorie d'intervenant :</label>
									<select id="categorieIntervenant" value={categorieIntervenant} className="authInput authSelect" onChange={(e) => setCategorieIntervenant(e.target.value)}>
										<option value="Ville de Montréal">Ville de Montréal</option>
										<option value="Entrepreneur sous contrat avec la Ville de Montréal">Entrepreneur sous contrat avec la Ville de Montréal</option>
										<option value="Entreprise privé">Entreprise privé</option>
									</select>
								</div>

								<div className="groupAuth">
									<input type="text" placeholder="Clé d'accès" value={key} className="authInput keyInput" onChange={(e) => setKey(e.target.value)} required />
								</div>
							</>
						)}

						<button type="submit" className="authButton">
							Créer un compte
						</button>
					</form>
					<p>{message}</p>
				</div>
			)}
		</div>
	);
}

export default Register;
