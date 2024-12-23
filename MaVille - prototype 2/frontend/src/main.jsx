import { AuthProvider } from "./contexts/AuthContext";
import { createRoot } from "react-dom/client";
import "./main.scss";
import App from "./App.jsx";

createRoot(document.getElementById("root")).render(
	<AuthProvider>
		<App />
	</AuthProvider>
);