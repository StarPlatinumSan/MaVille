package com.example.demo.controllers;

import com.example.demo.models.Notification;
import com.example.demo.models.PlageHoraire;
import com.example.demo.models.Requete;
import com.example.demo.models.User;
import com.example.demo.repositories.RequeteRepository;
import com.example.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Contrôleur REST pour gérer les utilisateurs.
 * Permet de gérer les notifications, plages horaires et requêtes des utilisateurs.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationsController notificationService;

    @Autowired
    private RequeteRepository requeteRepository;

    /**
     * Récupère toutes les notifications d'un utilisateur.
     *
     * @param email L'email de l'utilisateur.
     * @return La liste des notifications ou un statut 404 si l'utilisateur n'est pas trouvé.
     */
    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getAllNotifications(@RequestParam String email) {
        User user = userRepository.getUserByEmail(email);
        if (user == null) return ResponseEntity.status(404).body(null);

        return ResponseEntity.ok(user.getNotifications());
    }

    /**
     * Met à jour les notifications d'un utilisateur.
     *
     * @param email         L'email de l'utilisateur.
     * @param notifications La liste des nouvelles notifications.
     * @return Un message indiquant le succès ou l'échec de l'opération.
     */
    @PutMapping("/notifications")
    public ResponseEntity<String> updateNotifications(@RequestParam String email, @RequestBody List<Notification> notifications) {
        User user = userRepository.getUserByEmail(email);
        if (user == null) return ResponseEntity.status(404).body("Utilisateur non trouvé.");

        user.setNotifications(notifications);
        userRepository.saveIndividualUser(user);

        return ResponseEntity.ok("Notifications mises à jour.");
    }

    /**
     * Marque des notifications comme vues pour un utilisateur.
     *
     * @param email            L'email de l'utilisateur.
     * @param notificationIds  Les identifiants des notifications à marquer comme vues.
     * @return Un message indiquant le succès ou l'échec de l'opération.
     */
    @PostMapping("/notifications/vu")
    public ResponseEntity<String> NotifVu(@RequestParam String email, @RequestBody List<String> notificationIds) {
        User user = userRepository.getUserByEmail(email);

        if (user == null) {
            return ResponseEntity.status(404).body("Utilisateur non trouvé.");
        }

        boolean updated = false;
        for (Notification notification : user.getNotifications()) {
            if (notificationIds.contains(notification.getId()) && !notification.isSeen()) {
                notification.setSeen(true);
                updated = true;
            }
        }

        if (updated) {
            userRepository.saveIndividualUser(user);
        }

        return ResponseEntity.ok("Les notifications ont été marquées comme vues.");
    }

    /**
     * Ajoute une plage horaire pour un utilisateur.
     *
     * @param email        L'email de l'utilisateur.
     * @param plageHoraire La plage horaire à ajouter.
     * @return Un message indiquant le succès ou l'échec de l'ajout.
     */
    @PostMapping("/plages")
    public ResponseEntity<String> ajouterPlageHoraire(@RequestParam String email, @RequestBody PlageHoraire plageHoraire) {
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(404).body("Utilisateur non trouvé.");
        }

        try {
            user.ajouterPlageHoraire(plageHoraire);
            userRepository.saveIndividualUser(user);
            return ResponseEntity.ok("Plage horaire ajoutée avec succès.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    /**
     * Récupère les plages horaires d'un utilisateur.
     *
     * @param email L'email de l'utilisateur.
     * @return La liste des plages horaires ou un statut 404 si l'utilisateur n'est pas trouvé.
     */
    @GetMapping("/plages")
    public ResponseEntity<?> getPlagesByUser(@RequestParam String email) {
        // Vérifier que l'email est bien reçu
        System.out.println("Email reçu : " + email);
    
        // Logique pour traiter l'email
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(404).body("Utilisateur non trouvé.");
        }
    
        return ResponseEntity.ok(user.getPlagesHoraires());
    }

    /**
     * Supprime une plage horaire pour un utilisateur.
     *
     * @param email        L'email de l'utilisateur.
     * @param plageHoraire La plage horaire à supprimer.
     * @return Un message indiquant le succès ou l'échec de la suppression.
     */
    @DeleteMapping("/plages")
    public ResponseEntity<String> supprimerPlage(@RequestParam String email, @RequestBody PlageHoraire plageHoraire) {
        User user = userRepository.getUserByEmail(email);

        if (user == null) {
            return ResponseEntity.status(404).body("Utilisateur non trouvé.");
        }

        boolean removed = user.getPlagesHoraires().removeIf(plage ->
            plage.getJour().equals(plageHoraire.getJour()) &&
            plage.getHeureDebut().equals(plageHoraire.getHeureDebut()) &&
            plage.getHeureFin().equals(plageHoraire.getHeureFin())
        );

        if (removed) {
            userRepository.saveIndividualUser(user);
            return ResponseEntity.ok("Plage supprimée avec succès.");
        } else {
            return ResponseEntity.status(404).body("Plage non trouvée.");
        }
    }

    /**
     * Récupère les plages horaires des utilisateurs dans un quartier spécifique.
     *
     * @param quartier Le quartier cible. Si vide ou null, retourne toutes les plages horaires.
     * @return Une liste de plages horaires.
     */
    @GetMapping("/plages/quartier")
    public ResponseEntity<List<PlageHoraire>> getPlagesByQuartier(@RequestParam(required = false) String quartier) {
        List<User> users = userRepository.getAllUsers();
        List<PlageHoraire> plages = new ArrayList<>();

        for (User user : users) {
            if ((quartier == null || quartier.isBlank()) || quartier.equalsIgnoreCase(user.getQuartier())) {
                if (user.getPlagesHoraires() != null) {
                    plages.addAll(user.getPlagesHoraires());
                }
            }
        }

        return ResponseEntity.ok(plages);
    }
    
    /**
     * Récupère les requêtes d'un utilisateur.
     *
     * @param email L'email de l'utilisateur.
     * @return La liste des requêtes ou un statut 404 si l'utilisateur n'est pas trouvé.
     */
    @GetMapping("/requetes")
    public ResponseEntity<List<Requete>> getRequetesByUser(@RequestParam String email) {
        List<Requete> requetes = requeteRepository.getAllRequetes();

        // Filtrer les requêtes par email
        List<Requete> userRequetes = requetes.stream()
            .filter(requete -> requete.getEmail().equals(email))
            .toList();

        return ResponseEntity.ok(userRequetes);
    }
}
