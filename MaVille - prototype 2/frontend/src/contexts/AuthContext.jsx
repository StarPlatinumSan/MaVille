import React, { createContext, useEffect, useState } from "react";

export const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
	const [auth, setAuth] = useState(() => {
		const savedUser = localStorage.getItem("auth");
		return savedUser ? JSON.parse(savedUser) : { isAuthenticated: false, user: null };
	});

	useEffect(() => {
		localStorage.setItem("auth", JSON.stringify(auth));
	}, [auth]);

	const loginUser = (user) => {
		setAuth({ isAuthenticated: true, user: { ...user, role: user.role } });
	};

	const logoutUser = () => {
		setAuth({ isAuthenticated: false, user: null });
	};

	return <AuthContext.Provider value={{ auth, loginUser, logoutUser }}>{children}</AuthContext.Provider>;
};
