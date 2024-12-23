package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Contrôleur REST pour accéder aux données sur les travaux publics.
 */
@RestController
@RequestMapping("/api")
public class TravauxAPI {

    /**
     * Récupère les données sur les travaux publics depuis une API externe.
     *
     * @return Une réponse HTTP contenant les données des travaux ou un message d'erreur.
     */
    @GetMapping("/travaux")
    public ResponseEntity<?> getTravaux() {
        String url = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b";
        RestTemplate restTemplate = new RestTemplate();

        try {
            String response = restTemplate.getForObject(url, String.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur de récupération des données");
        }
    }

    /**
     * Récupère les données sur les entraves à la circulation depuis une API externe.
     *
     * @return Une réponse HTTP contenant les données des entraves ou un message d'erreur.
     */
    @GetMapping("/entraves")
    public ResponseEntity<?> getEntraves() {
        String url = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd";
        RestTemplate restTemplate = new RestTemplate();

        try {
            String response = restTemplate.getForObject(url, String.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur de récupération des données");
        }
    }
}
