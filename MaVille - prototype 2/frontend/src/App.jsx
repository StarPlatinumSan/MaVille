import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./components/Login";
import Register from "./components/Register";
import Account from "./components/Account";
import Lobby from "./components/Lobby";
import "./styles/app.scss";

function App() {
	return (
		<Router>
			<Routes>
				<Route path="/" element={<Lobby />} />
				<Route path="/login" element={<Login />} />
				<Route path="/register" element={<Register />} />
				<Route path="/account" element={<Account />} />
			</Routes>
		</Router>
	);
}

export default App;
