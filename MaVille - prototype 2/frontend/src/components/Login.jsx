import React, { useState, useContext } from "react";
import axios from "axios";
import { AuthContext } from "../contexts/AuthContext";
import { useNavigate } from "react-router-dom";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [key, setKey] = useState("");
  const [type, setType] = useState("");
  const [message, setMessage] = useState("");

  const { loginUser } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    const user = type === "resident" ? { email, password } : { email, password, key };

    try {
      const response = await axios.post("http://localhost:8080/api/auth/login", user);
      setMessage(response.data);

      if (response.data.message === "Connexion réussie.") {
        loginUser({
          username: response.data.username,
          email: response.data.email,
          role: response.data.role,
          phone: response.data.phone,
          address: response.data.address,
          birthDate: response.data.birthDate,
        });
        navigate("/account");
      } else {
        setMessage(response.data.message);
      }
    } catch (error) {
      console.log(error);
      setMessage("L'adresse email ou le mot de passe est incorrect.");
    }
  };

  return (
    <div className="authDiv">
      {type === "" ? (
        <div className="selectionType">
          <h2>Quel type de compte souhaitez-vous utiliser ?</h2>
          <button className="btn" onClick={() => setType("resident")}>
            Résident
          </button>
          <button className="btn" onClick={() => setType("intervenant")}>
            Intervenant
          </button>
        </div>
      ) : (
        <div className="authDiv">
          <h2 className="authTitle">Connexion ({type === "resident" ? "Résident" : "Intervenant"})</h2>
          <form onSubmit={handleLogin}>
            <input type="text" placeholder="Adresse email" value={email} className="authInput" onChange={(e) => setEmail(e.target.value)} required />
            <input type="password" placeholder="Mot de passe" value={password} className="authInput" onChange={(e) => setPassword(e.target.value)} required />

            {type === "intervenant" && <input type="text" placeholder="Clé d'accès" value={key} className="authInput" onChange={(e) => setKey(e.target.value)} required />}

            <button type="submit" className="authButton">
              Connexion
            </button>
          </form>
          <p>{message}</p>
        </div>
      )}
    </div>
  );
}

export default Login;
