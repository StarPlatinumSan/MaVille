package com.example.demo;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST pour gérer les requêtes de base de l'API.
 * Fournit des informations sur l'état du backend.
 */
@RestController
@RequestMapping("/api")
public class HomeController {

    /**
     * Endpoint pour vérifier l'état du backend.
     *
     * @return Un message indiquant que le backend fonctionne correctement.
     */
    @GetMapping("/message")
    public String message() {
        return "Backend OK. Avec useEffect fetch data API.";
    }
}
