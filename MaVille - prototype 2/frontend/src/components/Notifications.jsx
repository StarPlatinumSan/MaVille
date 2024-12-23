import { useState, useEffect, useContext } from "react";
import axios from "axios";
import { AuthContext } from "../contexts/AuthContext";

function Notifications() {
	const { auth } = useContext(AuthContext);
	const [notifications, setNotifications] = useState([]);
	/* const [notifsVu, setNotifsVu] = useState(new Set()); */

	useEffect(() => {
		const fetchNotifications = async () => {
			try {
				const response = await axios.get(`http://localhost:8080/api/users/notifications`, {
					params: { email: auth.user.email },
				});
				setNotifications(response.data);
			} catch (error) {
				console.error("Erreur lors de la recherche des notifications:", error);
			}
		};

		fetchNotifications();
	}, []);

	const markAsSeen = async () => {
		const unseenIds = notifications.filter((n) => !n.seen).map((n) => n.id);

		if (unseenIds.length > 0) {
			try {
				await axios.post(`http://localhost:8080/api/users/notifications/vu`, unseenIds, {
					params: { email: auth.user.email },
				});

				setNotifications((prevNotifications) => prevNotifications.map((notification) => (unseenIds.includes(notification.id) ? { ...notification, seen: true } : notification)));
			} catch (error) {
				console.error("Erreur lors de la mise à jour des notifications:", error);
			}
		}
	};

	const markAsSeenIndividual = async (notificationId) => {
		try {
			await axios.post(`http://localhost:8080/api/users/notifications/vu`, [notificationId], {
				params: { email: auth.user.email },
			});

			setNotifications((prevNotifications) => prevNotifications.map((notification) => (notification.id === notificationId ? { ...notification, seen: true } : notification)));
		} catch (error) {
			console.error("Erreur lors de la mise à jour de la notification:", error);
		}
	};

	const removeNotification = async (notificationId) => {
		try {
			/* Je garde toutes les notifs qui ont pas l'id de la notif que je veux supprimer */
			const updatedNotifications = notifications.filter((notification) => notification.id !== notificationId);
			setNotifications(updatedNotifications);

			await axios.put(`http://localhost:8080/api/users/notifications`, updatedNotifications, {
				params: { email: auth.user.email },
			});
		} catch (error) {
			console.error("Erreur lors de la suppression de la notification:", error);
		}
	};

	return (
		<div>
			<p className="notifPlaceholder">Vos notifications :</p>
			{notifications.length === 0 ? (
				<p>Aucune notification pour le moment</p>
			) : (
				<ul style={{ listStyle: "none" }}>
					<button className="btn seenBtn" onClick={markAsSeen}>
						Marquer tout comme vu
					</button>
					{notifications.map((notification) => (
						<li key={notification.id} onClick={() => markAsSeenIndividual(notification.id)} className={`notifObject ${notification.seen ? "seen" : "unseen"}`}>
							<div className="notifObjTxt">
								<strong>{notification.title}</strong>
								<p>{notification.description}</p>
							</div>

							<button
								className="btnCloseNotif"
								onClick={() => {
									removeNotification(notification.id);
								}}
							></button>
						</li>
					))}
				</ul>
			)}
		</div>
	);
}

export default Notifications;
