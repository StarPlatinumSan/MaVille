package com.example.demo.repositories;

import com.example.demo.models.Requete;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository pour gérer les données des requêtes.
 * Cette classe utilise un fichier JSON comme base de données pour stocker et récupérer des requêtes.
 */
@Repository
public class RequeteRepository {

    private final String filePath = "requetes.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();

    /**
     * Récupère toutes les requêtes enregistrées dans le fichier JSON.
     *
     * @return Une liste de requêtes. Si le fichier est introuvable ou illisible, retourne une liste vide.
     */
    public List<Requete> getAllRequetes() {
        try {
            return objectMapper.readValue(new File(filePath), new TypeReference<List<Requete>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Sauvegarde une liste de requêtes dans le fichier JSON.
     *
     * @param requetes La liste de requêtes à sauvegarder.
     */
    public void saveRequete(List<Requete> requetes) {
        try {
            writer.writeValue(new File(filePath), requetes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

