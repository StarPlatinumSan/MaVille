package com.example.demo.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe utilitaire pour mapper les codes postaux aux quartiers correspondants.
 */
public class QuartierMapper {

    private static final Map<String, String> CODEPOSTAL_QUARTIER = new HashMap<>();

    static {
        CODEPOSTAL_QUARTIER.put("H3L", "Ahuntsic-Cartierville");
        CODEPOSTAL_QUARTIER.put("H2M", "Ahuntsic-Cartierville");
        CODEPOSTAL_QUARTIER.put("H3N", "Ahuntsic-Cartierville");
        CODEPOSTAL_QUARTIER.put("H1K", "Anjou");
        CODEPOSTAL_QUARTIER.put("H1J", "Anjou");
        CODEPOSTAL_QUARTIER.put("H4A", "Côte-des-Neiges-Notre-Dame-de-Grâce");
        CODEPOSTAL_QUARTIER.put("H3T", "Côte-des-Neiges-Notre-Dame-de-Grâce");
        CODEPOSTAL_QUARTIER.put("H3V", "Côte-des-Neiges-Notre-Dame-de-Grâce");
        CODEPOSTAL_QUARTIER.put("H8S", "Lachine");
        CODEPOSTAL_QUARTIER.put("H8R", "Lachine");
        CODEPOSTAL_QUARTIER.put("H8N", "LaSalle");
        CODEPOSTAL_QUARTIER.put("H8P", "LaSalle");
        CODEPOSTAL_QUARTIER.put("H2J", "Le Plateau-Mont-Royal");
        CODEPOSTAL_QUARTIER.put("H2H", "Le Plateau-Mont-Royal");
        CODEPOSTAL_QUARTIER.put("H3C", "Le Sud-Ouest");
        CODEPOSTAL_QUARTIER.put("H3K", "Le Sud-Ouest");
        CODEPOSTAL_QUARTIER.put("H9C", "L'Île-Bizard-Sainte-Geneviève");
        CODEPOSTAL_QUARTIER.put("H9E", "L'Île-Bizard-Sainte-Geneviève");
        CODEPOSTAL_QUARTIER.put("H1L", "Mercier-Hochelaga-Maisonneuve");
        CODEPOSTAL_QUARTIER.put("H1M", "Mercier-Hochelaga-Maisonneuve");
        CODEPOSTAL_QUARTIER.put("H1N", "Mercier-Hochelaga-Maisonneuve");
        CODEPOSTAL_QUARTIER.put("H1V", "Mercier-Hochelaga-Maisonneuve");
        CODEPOSTAL_QUARTIER.put("H1W", "Mercier-Hochelaga-Maisonneuve");
        CODEPOSTAL_QUARTIER.put("H1G", "Montréal-Nord");
        CODEPOSTAL_QUARTIER.put("H1H", "Montréal-Nord");
        CODEPOSTAL_QUARTIER.put("H2V", "Outremont");
        CODEPOSTAL_QUARTIER.put("H8Z", "Pierrefonds-Roxboro");
        CODEPOSTAL_QUARTIER.put("H9J", "Pierrefonds-Roxboro");
        CODEPOSTAL_QUARTIER.put("H1E", "Rivière-des-Prairies-Pointe-aux-Trembles");
        CODEPOSTAL_QUARTIER.put("H1B", "Rivière-des-Prairies-Pointe-aux-Trembles");
        CODEPOSTAL_QUARTIER.put("H1Y", "Rosemont-La Petite-Patrie");
        CODEPOSTAL_QUARTIER.put("H2G", "Rosemont-La Petite-Patrie");
        CODEPOSTAL_QUARTIER.put("H2S", "Rosemont-La Petite-Patrie");
        CODEPOSTAL_QUARTIER.put("H4L", "Saint-Laurent");
        CODEPOSTAL_QUARTIER.put("H4M", "Saint-Laurent");
        CODEPOSTAL_QUARTIER.put("H1P", "Saint-Léonard");
        CODEPOSTAL_QUARTIER.put("H1R", "Saint-Léonard");
        CODEPOSTAL_QUARTIER.put("H4G", "Verdun");
        CODEPOSTAL_QUARTIER.put("H3E", "Verdun");
        CODEPOSTAL_QUARTIER.put("H2X", "Ville-Marie");
        CODEPOSTAL_QUARTIER.put("H2Y", "Ville-Marie");
        CODEPOSTAL_QUARTIER.put("H3A", "Ville-Marie");
        CODEPOSTAL_QUARTIER.put("H3B", "Ville-Marie");
        CODEPOSTAL_QUARTIER.put("H2P", "Villeray-Saint-Michel-Parc-Extension");
        CODEPOSTAL_QUARTIER.put("H2R", "Villeray-Saint-Michel-Parc-Extension");
        CODEPOSTAL_QUARTIER.put("H2N", "Villeray-Saint-Michel-Parc-Extension");
        CODEPOSTAL_QUARTIER.put("H2E", "Villeray-Saint-Michel-Parc-Extension");
    }

    /**
     * Obtient le quartier correspondant au code postal donné.
     *
     * @param codePostal Le code postal à mapper.
     * @return Le nom du quartier ou "Quartier inconnu" si le code postal n'est pas trouvé.
     * @throws IllegalArgumentException Si le code postal est invalide.
     */
    public static String getQuartierByCodePostal(String codePostal) {
        if (codePostal == null || codePostal.length() < 3) {
            throw new IllegalArgumentException("Code postal invalide.");
        }
        String prefix = codePostal.substring(0, 3).toUpperCase();
        return CODEPOSTAL_QUARTIER.getOrDefault(prefix, "Quartier inconnu");
    }
}
