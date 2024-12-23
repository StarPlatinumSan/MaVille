package com.example.demo.controllers;

import com.example.demo.models.Notification;
import com.example.demo.models.Requete;
import com.example.demo.models.User;
import com.example.demo.repositories.RequeteRepository;
import com.example.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour gérer les requêtes.
 */
@RestController
@RequestMapping("/api/requetes")
public class RequeteController {
    @Autowired
    private RequeteRepository requeteRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Crée une nouvelle requête.
     *
     * @param requete La requête à ajouter.
     * @return Une réponse HTTP avec un message de confirmation ou d'erreur.
     */
    @PostMapping
    public ResponseEntity<String> createRequete(@RequestBody Requete requete) {
        try {
            List<Requete> requetes = requeteRepository.getAllRequetes();
            requetes.add(requete);
            requeteRepository.saveRequete(requetes);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur dans la création de requête");
        }
        return ResponseEntity.ok("La requête a été envoyée avec succès");   
    }

    /**
     * Récupère les requêtes filtrées par email ou type.
     *
     * @param email L'email associé aux requêtes (optionnel).
     * @param type  Le type de la requête (optionnel).
     * @return Une réponse HTTP contenant une liste de requêtes filtrées.
     */
    @GetMapping("/filtre")
    public ResponseEntity<List<Requete>> getRequetes(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String type) {
        List<Requete> requetes = requeteRepository.getAllRequetes();

        if (email != null) {
            requetes = requetes.stream()
                .filter(requete -> requete.getEmail().equals(email))
                .toList();
        }

        if (type != null) {
            requetes = requetes.stream()
                .filter(requete -> requete.getType().equalsIgnoreCase(type))
                .toList();
        }

        return ResponseEntity.ok(requetes);
    }

    /**
     * Envoie une notification pour une requête donnée.
     *
     * @param requeteId L'identifiant de la requête.
     * @return Une réponse HTTP indiquant le succès ou l'échec de l'envoi.
     */
    @PostMapping("/{requeteId}/notify")
    public ResponseEntity<String> notifyUser(@PathVariable String requeteId) {
        try {
            List<Requete> requetes = requeteRepository.getAllRequetes();
            Requete requete = requetes.stream().filter(r -> r.getId().equals(requeteId)).findFirst().orElse(null);
            if(requete == null) return ResponseEntity.status(404).body("Requête non trouvée.");

            if (!requete.isNotif()) return ResponseEntity.status(400).body("Les notifications ne sont pas activées pour cette requête.");

            User user = userRepository.getUserByEmail(requete.getEmail());
            if(user == null) return ResponseEntity.status(404).body("Utilisateur non trouvé.");

            Notification notification = new Notification(
                "Requête prise en charge.",
                "Votre requête \"" + requete.getTitre() + "\" a été acceptée par un intervenant."
            );

            user.addNotification(notification);
            userRepository.saveIndividualUser(user);

            return ResponseEntity.ok("Notification envoyée avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de l'envoi de la notification.");       

        }
    }

    /**
     * Récupère toutes les requêtes.
     *
     * @return Une réponse HTTP contenant la liste de toutes les requêtes.
     */
    @GetMapping
    public ResponseEntity<List<Requete>> getAllRequetes() {
        return ResponseEntity.ok(requeteRepository.getAllRequetes());
    }

    /**
     * Soumissionne une requête par un intervenant.
     *
     * @param requeteId        L'identifiant de la requête.
     * @param intervenantEmail L'email de l'intervenant.
     * @return Une réponse HTTP avec un message de confirmation ou d'erreur.
     */
    @PostMapping("/{requeteId}/soumissionner")
    public ResponseEntity<String> soumissionnerRequete(
            @PathVariable String requeteId,
            @RequestParam String intervenantEmail) {
        try {
            // Charger toutes les requêtes
            List<Requete> requetes = requeteRepository.getAllRequetes();

            // Trouver la requête correspondante
            Requete requete = requetes.stream()
                    .filter(r -> r.getId().equals(requeteId))
                    .findFirst()
                    .orElse(null);

            if (requete == null) {
                return ResponseEntity.status(404).body("Requête non trouvée.");
            }

            if (requete.getHasSoumission()) {
                return ResponseEntity.status(400).body("La requête a déjà été soumise.");
            }

            // Vérifier si l'intervenant existe
            User intervenant = userRepository.getUserByEmail(intervenantEmail);
            if (intervenant == null) {
                return ResponseEntity.status(404).body("Intervenant non trouvé.");
            }

            if (intervenant.getKey() == null) { // Vérification que le user est bien un intervenant
                return ResponseEntity.status(403).body("L'utilisateur spécifié n'est pas un intervenant.");
            }

            // Mettre à jour la requête
            requete.setHasSoumission(true);
            requete.setIntervenant(intervenantEmail);

            // Sauvegarder les changements
            requeteRepository.saveRequete(requetes);

            return ResponseEntity.ok("La soumission a été enregistrée avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de la soumission.");
        }
    }
    
    /**
     * Accepte une soumission pour une requête.
     *
     * @param requeteId L'identifiant de la requête.
     * @param email     L'email de l'utilisateur.
     * @return Une réponse HTTP avec un message de confirmation ou d'erreur.
     */
    @PostMapping("/{requeteId}/accept")
    public ResponseEntity<String> accepterSoumission(
            @PathVariable String requeteId,
            @RequestParam String email) {
        try {
            List<Requete> requetes = requeteRepository.getAllRequetes();

            // Trouver la requête par ID
            Requete requete = requetes.stream()
                    .filter(r -> r.getId().equals(requeteId))
                    .findFirst()
                    .orElse(null);

            if (requete == null) {
                return ResponseEntity.status(404).body("Requête non trouvée.");
            }

            if (!requete.getEmail().equals(email)) {
                return ResponseEntity.status(403).body("Vous n'êtes pas l'auteur de cette requête.");
            }

            if (requete.getIsAccepted()) {
                return ResponseEntity.status(400).body("La soumission a déjà été acceptée.");
            }

            if (!requete.getHasSoumission()) {
                return ResponseEntity.status(400).body("Aucune soumission à accepter.");
            }

            // Mettre à jour la requête
            requete.setIsAccepted(true);

            // Sauvegarder les changements
            requeteRepository.saveRequete(requetes);

            return ResponseEntity.ok("Soumission acceptée avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de l'acceptation de la soumission.");
        }
    }

    @PostMapping("/{requeteId}/unsoumissionner")
    public ResponseEntity<String> annulerSoumission(
            @PathVariable String requeteId,
            @RequestParam String intervenantEmail) {
        try {
            List<Requete> requetes = requeteRepository.getAllRequetes();

            // Trouver la requête par ID
            Requete requete = requetes.stream()
                    .filter(r -> r.getId().equals(requeteId))
                    .findFirst()
                    .orElse(null);

            if (requete == null) {
                return ResponseEntity.status(404).body("Requête non trouvée.");
            }

            if (!requete.getHasSoumission() || requete.getIsAccepted()) {
                return ResponseEntity.status(400).body("Impossible d'annuler cette soumission.");
            }

            if (!requete.getIntervenant().equals(intervenantEmail)) {
                return ResponseEntity.status(403).body("Vous n'êtes pas l'intervenant de cette soumission.");
            }

            // Mettre à jour la requête
            requete.setHasSoumission(false);
            requete.setIntervenant(null);

            // Sauvegarder les changements
            requeteRepository.saveRequete(requetes);

            return ResponseEntity.ok("Soumission annulée avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de l'annulation.");
        }
    }

    @PostMapping("/{requeteId}/confirmer")
    public ResponseEntity<String> confirmerCandidature(
            @PathVariable String requeteId,
            @RequestParam String intervenantEmail) {
 
        try {
            List<Requete> requetes = requeteRepository.getAllRequetes();

            // Trouver la requête par ID
            Requete requete = requetes.stream()
                    .filter(r -> r.getId().equals(requeteId))
                    .findFirst()
                    .orElse(null);

    
            if (requete == null) {
                return ResponseEntity.status(404).body("Requête non trouvée.");
            }

            if (!requete.getIntervenant().equals(intervenantEmail)) {
                return ResponseEntity.status(403).body("Vous n'êtes pas l'intervenant de cette requête.");
            }

            if (!requete.getIsAccepted()) {
                return ResponseEntity.status(400).body("La candidature n'a pas encore été acceptée.");
            }

            // Confirmation de la candidature
            requete.setHasSoumission(false); 
            requete.setIsConfirmed(true);

            // Sauvegarder les modifications
            requeteRepository.saveRequete(requetes);

            return ResponseEntity.ok("Candidature confirmée avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de la confirmation de la candidature.");
        }
    }


    /**
     * Supprime une requête par son identifiant.
     *
     * @param id L'identifiant de la requête.
     * @return Une réponse HTTP avec un message de confirmation ou d'erreur.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRequete(@PathVariable String id) {
        try {
            List<Requete> requetes = requeteRepository.getAllRequetes();
            boolean removed = requetes.removeIf(requete -> requete.getId().equals(id));
            if (!removed) {
                return ResponseEntity.status(404).body("Requête non trouvée.");
            }
            requeteRepository.saveRequete(requetes);
            return ResponseEntity.ok("Requête supprimée avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de la suppression de la requête.");
        }
    }

}
