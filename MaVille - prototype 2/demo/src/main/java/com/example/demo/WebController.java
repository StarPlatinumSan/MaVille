package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Contrôleur pour rediriger les chemins non-API vers le fichier `index.html`.
 */
@Controller
public class WebController {

    /**
     * Redirige les requêtes non-API vers `index.html`.
     *
     * @return La redirection vers le fichier `index.html`.
     */
    @GetMapping(value = {"/", "/{path:[^\\.]*}"})
    public String index() {
        return "forward:/index.html";
    }
}
