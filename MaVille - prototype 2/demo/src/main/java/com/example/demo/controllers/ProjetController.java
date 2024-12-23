package com.example.demo.controllers;

import com.example.demo.models.Notification;
import com.example.demo.models.Projet;
import com.example.demo.repositories.ProjetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Contrôleur REST pour gérer les projets.
 * Permet de consulter, ajouter, modifier et supprimer des projets.
 */
@RestController
@RequestMapping("/api/projets")
public class ProjetController {

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private NotificationsController notificationService;

    /**
     * Récupère tous les projets existants.
     *
     * @return Une liste de projets.
     */
    @GetMapping
    public ResponseEntity<List<Projet>> getAllProjets() {
        return ResponseEntity.ok(projetRepository.getAllProjets());
    }

    /**
     * Ajoute un nouveau projet et envoie une notification.
     *
     * @param projet Le projet à ajouter.
     * @return Un message indiquant le résultat de l'ajout.
     */
    @PostMapping
    public ResponseEntity<String> addProjet(@RequestBody Projet projet) {
        try {
            projet.setId(UUID.randomUUID().toString());
            projetRepository.addProjet(projet);

            Notification notification = new Notification(
                "Nouveau travail dans votre quartier",
                "Un nouveau projet \"" + projet.getNom() + "\" a été ajouté dans votre quartier " + projet.getArrondissement() + "."
            );

            if (!sendNotification(projet.getArrondissement(), notification)) {
                return ResponseEntity.ok("Projet ajouté avec succès, mais erreur lors de l'envoi des notifications.");
            }

            return ResponseEntity.ok("Projet ajouté avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de l'ajout du projet.");
        }
    }

    /**
     * Met à jour le statut d'un projet existant.
     *
     * @param id            L'identifiant du projet.
     * @param nouveauStatut Le nouveau statut à appliquer.
     * @return Un message indiquant le résultat de la mise à jour.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateStatut(@PathVariable String id, @RequestBody String nouveauStatut) {
        try {
            List<Projet> projets = projetRepository.getAllProjets();
            Optional<Projet> optionalProjet = projets.stream().filter(projet -> projet.getId().equals(id)).findFirst();

            if (optionalProjet.isEmpty()) {
                return ResponseEntity.status(404).body("Projet non trouvé.");
            }

            Projet projet = optionalProjet.get();
            projet.setStatut(nouveauStatut);
            projetRepository.saveProjets(projets);

            Notification notification = new Notification(
                "Mise-à-jour de projet dans votre quartier",
                "Le status du projet \"" + projet.getNom() + "\" a été modifié dans votre quartier " + projet.getArrondissement() + " à [" + nouveauStatut + "]."
            );

            if (!sendNotification(projet.getArrondissement(), notification)) {
                return ResponseEntity.ok("Statut mis à jour avec succès, mais erreur lors de l'envoi des notifications.");
            }

            return ResponseEntity.ok("Statut mis à jour avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de la mise à jour du statut.");
        }
    }

    /**
     * Supprime un projet existant.
     *
     * @param id L'identifiant du projet à supprimer.
     * @return Un message indiquant le résultat de la suppression.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProjet(@PathVariable String id) {
        try {
            List<Projet> projets = projetRepository.getAllProjets();
            Optional<Projet> optionalProjet = projets.stream().filter(projet -> projet.getId().equals(id)).findFirst();

            if (optionalProjet.isEmpty()) {
                return ResponseEntity.status(404).body("Projet non trouvé.");
            }

            Projet projetToDelete = optionalProjet.get();
            projets.remove(projetToDelete);
            projetRepository.saveProjets(projets);

            Notification notification = new Notification(
                "Projet terminé dans votre quartier",
                "Le projet \"" + projetToDelete.getNom() + "\" a été terminé dans votre quartier."
            );

            if (!sendNotification(projetToDelete.getArrondissement(), notification)) {
                return ResponseEntity.ok("Projet supprimé avec succès, mais erreur lors de l'envoi des notifications.");
            }

            return ResponseEntity.ok("Projet supprimé avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de la suppression du projet.");
        }
    }

    /**
     * Envoie une notification à un quartier spécifique.
     *
     * @param arrondissement Le quartier cible.
     * @param notification    La notification à envoyer.
     * @return {@code true} si la notification a été envoyée avec succès, sinon {@code false}.
     */
    private boolean sendNotification(String arrondissement, Notification notification) {
        try {
            notificationService.sendNotifQuartier(arrondissement, notification);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
