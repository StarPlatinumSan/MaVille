package com.example.demo.models;

import java.util.UUID;

/**
 * Représente une notification avec ses propriétés principales.
 */
public class Notification {

    private String id;
    private String title;
    private String description;
    private boolean seen;

    /**
     * Constructeur par défaut.
     * Initialise une notification avec un identifiant unique.
     */
    public Notification() {
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Constructeur pour créer une notification avec un titre et une description.
     *
     * @param title       Le titre de la notification.
     * @param description La description de la notification.
     */
    public Notification(String title, String description) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.seen = false;
    }

    /**
     * Obtient l'identifiant unique de la notification.
     *
     * @return L'identifiant de la notification.
     */
    public String getId() {
        return id;
    }

    /**
     * Définit si la notification a été vue.
     *
     * @param seen {@code true} si la notification a été vue, sinon {@code false}.
     */
    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    /**
     * Indique si la notification a été vue.
     *
     * @return {@code true} si vue, sinon {@code false}.
     */
    public boolean isSeen() {
        return seen;
    }

    /**
     * Obtient le titre de la notification.
     *
     * @return Le titre de la notification.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Définit le titre de la notification.
     *
     * @param title Le titre à définir.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Obtient la description de la notification.
     *
     * @return La description de la notification.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la description de la notification.
     *
     * @param description La description à définir.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
