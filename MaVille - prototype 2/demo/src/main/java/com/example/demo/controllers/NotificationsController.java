package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.Notification;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;

/**
 * Contrôleur pour la gestion des notifications.
 */
@Service
public class NotificationsController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Envoie une notification à tous les utilisateurs d'un quartier spécifique.
     *
     * @param quartier     Le quartier cible pour l'envoi des notifications.
     * @param notification La notification à envoyer.
     * @return Une réponse HTTP indiquant le succès ou l'échec de l'opération.
     */
    public ResponseEntity<String> sendNotifQuartier(@RequestParam String quartier, @RequestBody Notification notification) {
        List<User> users = userRepository.getAllUsers();
        boolean notifEnvoyee = false;

        for (User user : users) {
            if (user.getQuartier() != null && user.getQuartier().equalsIgnoreCase(quartier)) {
                System.out.println("Envoie de notification au résident: " + user.getUsername());
                user.addNotification(notification);
                notifEnvoyee = true;
            }
        }

        if (notifEnvoyee) {
            userRepository.saveUser(users);
            return ResponseEntity.ok("Notification envoyée au quartier.");
        } else {
            return ResponseEntity.status(404).body("Aucun utilisateur n'a ce quartier.");
        }
    }
}
