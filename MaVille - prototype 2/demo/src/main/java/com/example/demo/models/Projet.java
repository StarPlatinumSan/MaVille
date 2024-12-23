package com.example.demo.models;

/**
 * Représente un projet avec ses propriétés principales.
 */
public class Projet {

    private String id;
    private String nom;
    private String arrondissement;
    private String statut; 
    private String raison; 
    private String dates; 
    private String categorieIntervenant; 
    private String nomIntervenant; 

    /**
     * Constructeur par défaut.
     */
    public Projet() {}

    /**
     * Constructeur pour initialiser toutes les propriétés d'un projet.
     *
     * @param id                   L'identifiant unique du projet.
     * @param nom                  Le nom du projet.
     * @param arrondissement       L'arrondissement où se trouve le projet.
     * @param statut               Le statut actuel du projet.
     * @param raison               La raison du projet.
     * @param dates                Les dates associées au projet.
     * @param categorieIntervenant La catégorie de l'intervenant.
     * @param nomIntervenant       Le nom de l'intervenant.
     */
    public Projet(String id, String nom, String arrondissement, String statut, String raison, String dates,
                  String categorieIntervenant, String nomIntervenant) {
        this.id = id;
        this.nom = nom;
        this.arrondissement = arrondissement;
        this.statut = statut;
        this.raison = raison;
        this.dates = dates;
        this.categorieIntervenant = categorieIntervenant;
        this.nomIntervenant = nomIntervenant;
    }

    /**
     * Obtient l'identifiant unique du projet.
     *
     * @return L'identifiant du projet.
     */
    public String getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique du projet.
     *
     * @param id L'identifiant à définir.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtient le nom du projet.
     *
     * @return Le nom du projet.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom du projet.
     *
     * @param nom Le nom à définir.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obtient l'arrondissement du projet.
     *
     * @return L'arrondissement du projet.
     */
    public String getArrondissement() {
        return arrondissement;
    }

    /**
     * Définit l'arrondissement du projet.
     *
     * @param arrondissement L'arrondissement à définir.
     */
    public void setArrondissement(String arrondissement) {
        this.arrondissement = arrondissement;
    }

    /**
     * Obtient le statut du projet.
     *
     * @return Le statut du projet.
     */
    public String getStatut() {
        return statut;
    }

    /**
     * Définit le statut du projet.
     *
     * @param statut Le statut à définir.
     */
    public void setStatut(String statut) {
        this.statut = statut;
    }

    /**
     * Obtient la raison du projet.
     *
     * @return La raison du projet.
     */
    public String getRaison() {
        return raison;
    }

    /**
     * Définit la raison du projet.
     *
     * @param raison La raison à définir.
     */
    public void setRaison(String raison) {
        this.raison = raison;
    }

    /**
     * Obtient les dates associées au projet.
     *
     * @return Les dates du projet.
     */
    public String getDates() {
        return dates;
    }

    /**
     * Définit les dates associées au projet.
     *
     * @param dates Les dates à définir.
     */
    public void setDates(String dates) {
        this.dates = dates;
    }

    /**
     * Obtient la catégorie de l'intervenant.
     *
     * @return La catégorie de l'intervenant.
     */
    public String getCategorieIntervenant() {
        return categorieIntervenant;
    }

    /**
     * Définit la catégorie de l'intervenant.
     *
     * @param categorieIntervenant La catégorie à définir.
     */
    public void setCategorieIntervenant(String categorieIntervenant) {
        this.categorieIntervenant = categorieIntervenant;
    }

    /**
     * Obtient le nom de l'intervenant.
     *
     * @return Le nom de l'intervenant.
     */
    public String getNomIntervenant() {
        return nomIntervenant;
    }

    /**
     * Définit le nom de l'intervenant.
     *
     * @param nomIntervenant Le nom à définir.
     */
    public void setNomIntervenant(String nomIntervenant) {
        this.nomIntervenant = nomIntervenant;
    }
}
