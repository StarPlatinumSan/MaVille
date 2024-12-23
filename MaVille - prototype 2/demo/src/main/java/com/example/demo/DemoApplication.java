package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.context.ApplicationContext;

/**
 * Classe principale de l'application Spring Boot.
 * Cette classe configure et lance l'application.
 */
@SpringBootApplication
public class DemoApplication {

    /**
     * Point d'entrée principal de l'application.
     * Cette méthode initialise et exécute l'application Spring Boot.
     *
     * @param args Arguments de ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
