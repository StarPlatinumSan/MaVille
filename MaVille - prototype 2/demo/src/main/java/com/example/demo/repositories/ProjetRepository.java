package com.example.demo.repositories;

import com.example.demo.models.Projet;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository pour gérer les projets.
 * Utilise un fichier JSON pour stocker et récupérer les données des projets.
 */
@Repository
public class ProjetRepository {

    private final String filePath = "projets.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Récupère tous les projets depuis le fichier JSON.
     *
     * @return Une liste de projets. Retourne une liste vide si le fichier est introuvable ou illisible.
     */
    public List<Projet> getAllProjets() {
        try {
            return objectMapper.readValue(new File(filePath), new TypeReference<List<Projet>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Sauvegarde une liste de projets dans le fichier JSON.
     *
     * @param projets La liste de projets à sauvegarder.
     */
    public void saveProjets(List<Projet> projets) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), projets);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ajoute un projet à la liste existante et le sauvegarde dans le fichier JSON.
     *
     * @param projet Le projet à ajouter.
     */
    public void addProjet(Projet projet) {
        List<Projet> projets = getAllProjets();
        projets.add(projet);
        saveProjets(projets);
    }
}
