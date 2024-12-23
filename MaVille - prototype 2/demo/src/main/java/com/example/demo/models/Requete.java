package com.example.demo.models;

import java.util.UUID;

/**
 * Représente une requête avec ses détails et ses propriétés.
 */
public class Requete {

    private String titre;
    private String description;
    private String type;
    private String adresse;
    private String date;
    private boolean notif;
    private String email;
    private boolean hasSoumission;
    private boolean isAccepted;
    private String intervenantEmail;
    private String id;
    private boolean isConfirmed;

    /**
     * Constructeur par défaut.
     * Initialise une requête avec un identifiant unique.
     */
    public Requete() {
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Constructeur avec paramètres pour initialiser une requête.
     *
     * @param titre       Le titre de la requête.
     * @param description La description de la requête.
     * @param type        Le type de la requête.
     * @param adresse     L'adresse associée à la requête.
     * @param date        La date de la requête.
     * @param notif       Indique si une notification est activée.
     * @param email       L'adresse e-mail associée à la requête.
     */
    public Requete(String titre, String description, String type, String adresse, String date, boolean notif, String email) {
        this.id = UUID.randomUUID().toString();
        this.titre = titre;
        this.description = description;
        this.type = type;
        this.adresse = adresse;
        this.date = date;
        this.notif = notif;
        this.email = email;
        this.hasSoumission = false;
        this.isAccepted = false;
        this.isConfirmed = false;
        this.intervenantEmail = null;
    }

    /**
     * Obtient l'identifiant unique de la requête.
     *
     * @return L'identifiant unique.
     */
    public String getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique de la requête.
     *
     * @param id L'identifiant unique.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtient le titre de la requête.
     *
     * @return Le titre de la requête.
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Définit le titre de la requête.
     *
     * @param titre Le titre de la requête.
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Obtient la description de la requête.
     *
     * @return La description de la requête.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la description de la requête.
     *
     * @param description La description de la requête.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtient le type de la requête.
     *
     * @return Le type de la requête.
     */
    public String getType() {
        return type;
    }

    /**
     * Définit le type de la requête.
     *
     * @param type Le type de la requête.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Obtient l'adresse associée à la requête.
     *
     * @return L'adresse.
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Définit l'adresse associée à la requête.
     *
     * @param adresse L'adresse.
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Obtient la date de la requête.
     *
     * @return La date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Définit la date de la requête.
     *
     * @param date La date.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Indique si une notification est activée pour cette requête.
     *
     * @return {@code true} si activée, sinon {@code false}.
     */
    public boolean isNotif() {
        return notif;
    }

    /**
     * Définit si une notification est activée pour cette requête.
     *
     * @param notif {@code true} pour activer, sinon {@code false}.
     */
    public void setNotif(boolean notif) {
        this.notif = notif;
    }

    /**
     * Obtient l'adresse e-mail associée à la requête.
     *
     * @return L'adresse e-mail.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'adresse e-mail associée à la requête.
     *
     * @param email L'adresse e-mail.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Indique si la requête a une soumission associée.
     *
     * @return {@code true} si une soumission est présente, sinon {@code false}.
     */
    public boolean getHasSoumission() {
        return hasSoumission;
    }

    /**
     * Définit si la requête a une soumission associée.
     *
     * @param soumission {@code true} si une soumission est présente, sinon {@code false}.
     */
    public void setHasSoumission(boolean soumission) {
        this.hasSoumission = soumission;
    }

    /**
     * Indique si la requête est acceptée.
     *
     * @return {@code true} si acceptée, sinon {@code false}.
     */
    public boolean getIsAccepted() {
        return isAccepted;
    }

    /**
     * Définit si la requête est acceptée.
     *
     * @param accepted {@code true} si acceptée, sinon {@code false}.
     */
    public void setIsAccepted(boolean accepted) {
        this.isAccepted = accepted;
    }

    /**
     * Obtient l'adresse e-mail de l'intervenant associé à la requête.
     *
     * @return L'adresse e-mail de l'intervenant.
     */
    public String getIntervenant() {
        return intervenantEmail;
    }

    /**
     * Définit l'adresse e-mail de l'intervenant associé à la requête.
     *
     * @param email L'adresse e-mail de l'intervenant.
     */
    public void setIntervenant(String email) {
        this.intervenantEmail = email;
    }

    /**
     * Indique si la requête est confirmée.
     *
     * @return {@code true} si confirmée, sinon {@code false}.
     */
    public boolean getIsConfirmed() {
        return isConfirmed;
    }

    /**
     * Définit si la requête est confirmée.
     *
     * @param confirmed {@code true} si confirmée, sinon {@code false}.
     */
    public void setIsConfirmed(boolean confirmed) {
        this.isConfirmed = confirmed;
    }
}
