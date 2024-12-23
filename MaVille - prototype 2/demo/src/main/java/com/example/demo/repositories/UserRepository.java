package com.example.demo.repositories;

import com.example.demo.models.PlageHoraire;
import com.example.demo.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository pour gérer les utilisateurs.
 * Fournit des méthodes pour lire, écrire et modifier les données des utilisateurs.
 */
@Repository
public class UserRepository {

    private final String filePath = "users.json";
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS);
    private final ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();

    /**
     * Récupère tous les utilisateurs depuis le fichier JSON.
     *
     * @return Une liste contenant tous les utilisateurs. Si le fichier n'existe pas ou est vide, retourne une liste vide.
     */
    public List<User> getAllUsers() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Fichier introuvable : " + file.getAbsolutePath());
                return new ArrayList<>();
            }
    
            List<User> users = objectMapper.readValue(file, new TypeReference<List<User>>() {});
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Sauvegarde une liste entière d'utilisateurs dans le fichier JSON.
     *
     * @param users La liste d'utilisateurs à sauvegarder.
     */
    public void saveUser(List<User> users) {
        try {
            writer.writeValue(new File(filePath), users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Met à jour les informations d'un utilisateur spécifique en utilisant son email.
     *
     * @param user L'utilisateur contenant les informations mises à jour.
     */
    public void saveIndividualUser(User user) {
        try {
            List<User> users = getAllUsers();
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getEmail().equals(user.getEmail())) {
                    users.set(i, user);
                    break;
                }
            }
            saveUser(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère un utilisateur par son email.
     *
     * @param email L'email de l'utilisateur à rechercher.
     * @return L'utilisateur correspondant, ou {@code null} si non trouvé.
     */
    public User getUserByEmail(String email) {
        try {
            List<User> users = getAllUsers();
            return users.stream()
                    .filter(user -> email.equalsIgnoreCase(user.getEmail()))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Récupère les plages horaires associées à un quartier spécifique.
     *
     * @param quartier Le nom du quartier. Si {@code null} ou vide, retourne toutes les plages horaires.
     * @return Une liste de plages horaires correspondant au quartier.
     */
    public List<PlageHoraire> getPlagesByQuartier(String quartier) {
        List<User> users = getAllUsers();
        List<PlageHoraire> plages = new ArrayList<>();
        for (User user : users) {
            if ((quartier == null || quartier.isBlank()) || quartier.equalsIgnoreCase(user.getQuartier())) {
                if (user.getPlagesHoraires() != null) {
                    plages.addAll(user.getPlagesHoraires());
                }
            }
        }
        return plages;
    }
}
