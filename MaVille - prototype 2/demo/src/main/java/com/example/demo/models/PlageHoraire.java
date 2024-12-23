/**
 * Package contenant les modèles de l'application.
 */
package com.example.demo.models;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Représente une plage horaire avec un jour de la semaine, une heure de début et une heure de fin.
 */
public class PlageHoraire {

    private DayOfWeek jour;
    private LocalTime heureDebut;
    private LocalTime heureFin;

    /**
     * Constructeur par défaut pour créer une plage horaire vide.
     */
    public PlageHoraire() {
    }

    /**
     * Constructeur pour créer une plage horaire avec un jour, une heure de début et une heure de fin.
     *
     * @param jour       Le jour de la semaine.
     * @param heureDebut L'heure de début de la plage horaire.
     * @param heureFin   L'heure de fin de la plage horaire.
     * @throws IllegalArgumentException si l'heure de fin est avant ou égale à l'heure de début.
     */
    public PlageHoraire(DayOfWeek jour, LocalTime heureDebut, LocalTime heureFin) {
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;

        // Validation pour s'assurer que l'heure de fin est après l'heure de début
        if (heureDebut != null && heureFin != null && !heureFin.isAfter(heureDebut)) {
            throw new IllegalArgumentException("L'heure de fin doit être après l'heure de début.");
        }
    }

    /**
     * Obtient le jour de la semaine de la plage horaire.
     *
     * @return Le jour de la semaine.
     */
    public DayOfWeek getJour() {
        return jour;
    }

    /**
     * Définit le jour de la semaine de la plage horaire.
     *
     * @param jour Le jour de la semaine.
     */
    public void setJour(DayOfWeek jour) {
        this.jour = jour;
    }

    /**
     * Obtient l'heure de début de la plage horaire.
     *
     * @return L'heure de début.
     */
    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    /**
     * Définit l'heure de début de la plage horaire.
     *
     * @param heureDebut L'heure de début.
     * @throws IllegalArgumentException si l'heure de fin est avant ou égale à l'heure de début.
     */
    public void setHeureDebut(LocalTime heureDebut) {
        if (heureFin != null && heureDebut != null && !heureFin.isAfter(heureDebut)) {
            throw new IllegalArgumentException("L'heure de fin doit être après l'heure de début.");
        }
        this.heureDebut = heureDebut;
    }

    /**
     * Obtient l'heure de fin de la plage horaire.
     *
     * @return L'heure de fin.
     */
    public LocalTime getHeureFin() {
        return heureFin;
    }

    /**
     * Définit l'heure de fin de la plage horaire.
     *
     * @param heureFin L'heure de fin.
     * @throws IllegalArgumentException si l'heure de fin est avant ou égale à l'heure de début.
     */
    public void setHeureFin(LocalTime heureFin) {
        if (heureDebut != null && heureFin != null && !heureFin.isAfter(heureDebut)) {
            throw new IllegalArgumentException("L'heure de fin doit être après l'heure de début.");
        }
        this.heureFin = heureFin;
    }
}
