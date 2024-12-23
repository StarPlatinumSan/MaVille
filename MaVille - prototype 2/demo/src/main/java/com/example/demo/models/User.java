package com.example.demo.models;

import java.util.List;
import java.util.ArrayList;

/**
 * Classe représentant un utilisateur.
 * Utilisée pour l'authentification et les interactions avec la base de données JSON.
 */
public class User {

    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String codePostal;
    private String quartier;
    private String key;
    private int age;
    private List<Notification> notifications;
    private List<PlageHoraire> plagesHoraires;
    private String typeIntervenant;

    /**
     * Constructeur par défaut.
     * Initialise les listes de notifications et de plages horaires.
     */
    public User() {
        this.notifications = new ArrayList<>();
        this.plagesHoraires = new ArrayList<>();
    }

    /**
     * Constructeur pour un utilisateur résident.
     *
     * @param username   Le nom d'utilisateur.
     * @param password   Le mot de passe.
     * @param age        L'âge de l'utilisateur.
     * @param email      L'adresse e-mail.
     * @param phone      Le numéro de téléphone.
     * @param address    L'adresse postale.
     * @param codePostal Le code postal.
     */
    public User(String username, String password, int age, String email, String phone, String address, String codePostal) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.codePostal = codePostal;
        this.quartier = null;
        this.key = null;
        this.notifications = new ArrayList<>();
        this.plagesHoraires = new ArrayList<>();
    }

    /**
     * Constructeur pour un utilisateur intervenant.
     *
     * @param username   Le nom d'utilisateur.
     * @param password   Le mot de passe.
     * @param age        L'âge de l'utilisateur.
     * @param email      L'adresse e-mail.
     * @param key        La clé d'accès de la ville.
     * @param type       Le type d'intervenant.
     */
    public User(String username, String password, int age, String email, String key, String type) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.email = email;
        this.key = key;
        this.typeIntervenant = type;
        this.notifications = new ArrayList<>();
        this.plagesHoraires = new ArrayList<>();
    }

    /**
     * Ajoute une plage horaire à l'utilisateur.
     *
     * @param plage La plage horaire à ajouter.
     * @return {@code true} si l'ajout a réussi, sinon {@code false}.
     */
    public boolean ajouterPlageHoraire(PlageHoraire plage) {
        if (plage != null) {
            this.plagesHoraires.add(plage);
            return true;
        }
        return false;
    }

    /**
     * Obtient la liste des plages horaires de l'utilisateur.
     *
     * @return La liste des plages horaires.
     */
    public List<PlageHoraire> getPlagesHoraires() {
        return plagesHoraires;
    }

    /**
     * Définit les plages horaires de l'utilisateur.
     *
     * @param plagesHoraires La liste des plages horaires.
     */
    public void setPlagesHoraires(List<PlageHoraire> plagesHoraires) {
        this.plagesHoraires = plagesHoraires;
    }

    /**
     * Définit les notifications de l'utilisateur.
     *
     * @param notifications La liste des notifications.
     */
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    /**
     * Obtient la liste des notifications de l'utilisateur.
     *
     * @return La liste des notifications.
     */
    public List<Notification> getNotifications() {
        return notifications;
    }

    /**
     * Ajoute une notification à l'utilisateur.
     *
     * @param notification La notification à ajouter.
     */
    public void addNotification(Notification notification) {
        this.notifications.add(notification);
    }

    /**
     * Obtient le nom d'utilisateur.
     *
     * @return Le nom d'utilisateur.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Définit le nom d'utilisateur.
     *
     * @param username Le nom d'utilisateur.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtient le mot de passe.
     *
     * @return Le mot de passe.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Définit le mot de passe.
     *
     * @param password Le mot de passe.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtient l'adresse e-mail.
     *
     * @return L'adresse e-mail.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'adresse e-mail.
     *
     * @param email L'adresse e-mail.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtient le numéro de téléphone.
     *
     * @return Le numéro de téléphone.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Définit le numéro de téléphone.
     *
     * @param phone Le numéro de téléphone.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Obtient l'adresse postale.
     *
     * @return L'adresse postale.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Définit l'adresse postale.
     *
     * @param address L'adresse postale.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Obtient le code postal.
     *
     * @return Le code postal.
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * Définit le code postal.
     *
     * @param codePostal Le code postal.
     */
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    /**
     * Obtient le quartier associé à l'utilisateur.
     *
     * @return Le quartier.
     */
    public String getQuartier() {
        return quartier;
    }

    /**
     * Définit le quartier associé à l'utilisateur.
     *
     * @param quartier Le quartier.
     */
    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    /**
     * Définit l'âge de l'utilisateur.
     *
     * @param age L'âge.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Obtient l'âge de l'utilisateur.
     *
     * @return L'âge.
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Obtient la clé de l'utilisateur.
     * Si la clé est {@code null}, il s'agit d'un résident.
     *
     * @return La clé d'accès ou {@code null}.
     */
    public String getKey() {
        return key;
    }

    /**
     * Définit le type d'intervenant.
     *
     * @param typeIntervenant Le type d'intervenant.
     */
    public void setTypeIntervenant(String typeIntervenant) {
        this.typeIntervenant = typeIntervenant;
    }

    /**
     * Obtient le type d'intervenant.
     *
     * @return Le type d'intervenant.
     */
    public String getTypeIntervenant() {
        return typeIntervenant;
    }
}
