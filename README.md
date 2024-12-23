# MaVille

=========================

## Description

Par Andrei Bituleanu, Alexandre Toutant, Samuel Michaud

MaVille est une application interactive qui informe les citoyens de Montréal sur les projets de construction,
les travaux en cours et leurs impacts, pour simplifier leurs déplacements et leur compréhension de l'évolution urbaine.

=========================

## Fonctionnalités par role

### Client

- L'utilisateur peut créer un compte résident ou intervenant
- L'utilisateur peut se connecter à son compte et se déconnecter

### Résident

- Le résident peut consulter la liste des les travaux de Montréal, tous, en cours et à venir d'ici 3 mois, il peut les filtrer par nom et quartier, et les voir sur la map de Montréal.
- Le résident peut consulter les entraves et les filtrer aussi.
- Le résident peut créer une requête de travail et optionnellement de recevoir une notification lors de sa prise en charge.
- Le résident peut ajouter des plages horaires.
- Le résident peut consulter ses requêtes de travail, accepter une candidature et fermer une requête prise en charge.
- Le résident peut consulter ses notifications, les marquer individuellement (ou toutes en un bouton) comme vues, il peut aussi les effacer.

### Intervenant

- L'intervenant peut consulter la liste des requêtes et soumettre sa candidature.
- L'intervenant peut soumettre un projet de travail qui sera également affiché dans la section Travaux du résident selon sa date de début et fin.
- L'intervenant peut procéder à la confirmation de la requête que sa soummission a été acceptée.
- L'intervenant peut consulter ses projets soumis, modifier leurs status et les supprimer, le tout notifiant les résidents du quartier du projet.

=========================

## Organisation du répertoire

MaVille - prototype 2 // le fichier complet, nom outdated
│
├── demo // backend
│ ├── .mvn
│ ├── src
│ │ ├── main
│ │ │ ├── java/com/example/demo
│ │ │ │ ├── config
│ │ │ │ ├── controllers
│ │ │ │ │ ├── AuthController.java
│ │ │ │ │ ├── NotificationsController.java
│ │ │ │ │ ├── ProjetController.java
│ │ │ │ │ ├── RequeteController.java
│ │ │ │ │ ├── TravauxAPI.java
│ │ │ │ │ └── UserController.java
│ │ │ │ ├── models
│ │ │ │ │ ├── Notification.java
│ │ │ │ │ ├── PlageHoraire.java
│ │ │ │ │ ├── Projet.java
│ │ │ │ │ ├── Requete.java
│ │ │ │ │ └── User.java
│ │ │ │ ├── repositories
│ │ │ │ │ ├── ProjetRepository.java
│ │ │ │ │ ├── RequeteRepository.java
│ │ │ │ │ └── UserRepository.java
│ │ │ │ ├── util
│ │ │ │ │ ├── DemoApplication.java
│ │ │ │ │ ├── HomeController.java
│ │ │ │ │ └── WebController.java
│ │ ├── test (...) // les jUnits
│
├── target
├── .gitattributes
├── .gitignore
├── Dockerfile
├── mvnw
├── mvnw.cmd
├── pom.xml
├── projets.json
├── requetes.json
├── users.json
frontend // frontend
│
├── dist
├── node_modules
├── public
├── src
│ ├── assets
│ ├── components
│ │ ├── Account.jsx
│ │ ├── Entraves.jsx
│ │ ├── ListeRequetes.jsx
│ │ ├── Lobby.jsx
│ │ ├── Login.jsx
│ │ ├── MesRequetes.jsx
│ │ ├── ModifInfos.jsx
│ │ ├── Notifications.jsx
│ │ ├── Plage.jsx
│ │ ├── Register.jsx
│ │ ├── Requete.jsx
│ │ ├── Soumettre.jsx
│ │ ├── Travaux.jsx
│ ├── contexts
│ │ └── AuthContext.jsx
│ ├── styles
│ │ ├── App.jsx
│ │ ├── main.jsx
│ │ ├── main.scss
│
├── .env
├── .gitignore
├── Dockerfile
├── eslint.config.js
├── index.html
├── package-lock.json
├── package.json
├── vite.config.js

=========================

## NOTES

Les tests unitaires sont dans le fichier : MaVille - prototype 2/demo/src/test/java/com/example/demo/controllersTest/AuthControllerTest.java
et sont lancés automatiquement lors du démarrage du serveur, la sortie est visible dans la console.

⚠️⚠️⚠️ LES USERS AVEC DES ATTRIBUTS KEY NON-NUL SONT DES INTERVENANTS, LES USERS AVEC DES KEY: null SONT DES RÉSIDENTS⚠️⚠️⚠️

=========================

## Description des données incluses dans l'application

`Résidents`

⚠️LE MOT DE PASSE DU COMPTE RÉSIDENT `levi.ackerman@gmail.com` est `123`

⚠️LE MOT DE PASSE DU COMPTE RÉSIDENT `jotaro.kujo@gmail.com` est `jjba`

⚠️LE MOT DE PASSE DU COMPTE RÉSIDENT `gojo.satoru@yahoo.com` est `jjk`

⚠️LE MOT DE PASSE DU COMPTE RÉSIDENT `caitlyn@hotmail.com` est `arcane`

⚠️LE MOT DE PASSE DU COMPTE RÉSIDENT `black.panther@gmail.com` est `marvel`

`Intervenants`

⚠️LE MOT DE PASSE DU COMPTE INTERVENANT `admin@villedemontreal.ca` est `000` ainsi que sa clé.

⚠️LE MOT DE PASSE DU COMPTE INTERVENANT `andrei.bituleanu@yahoo.com` est `and` et sa clé est `bit`.

⚠️LE MOT DE PASSE DU COMPTE INTERVENANT `alexandre.bloodysoma@gmail.com` est `alex123` et sa clé est `key426139`.

⚠️LE MOT DE PASSE DU COMPTE INTERVENANT `dawnsawnn@hotmail.com` est `dawnsawnn` et sa clé est `5551903`.

⚠️LE MOT DE PASSE DU COMPTE INTERVENANT `god@gmail.com` est `universe1` et sa clé est `infinity`.

`requêtes`

1. Réparation des trottoirs :
   Réparation des fissures et trous pour sécuriser les piétons
   (Rue Principale, 2025-01-15).
   Acceptée,
   confirmée,
   intervenant : andrei.bituleanu@yahoo.com.

2. Plantation d'arbres : Planter 15 arbres pour améliorer le parc
   (Parc Verdure, 2024-12-20). Non acceptée,
   non confirmée,
   aucun intervenant.

3. Nouvel éclairage public : Installation de lampadaires LED (Avenue Lumière, 2025-03-05).
   Acceptée,
   non confirmée,
   intervenant : admin@villedemontreal.ca.

4. Réfection de toiture : Remplacement de la toiture d’un centre communautaire
   (Boulevard Principal, 2025-01-25).
   Non acceptée,
   non confirmée,
   aucun intervenant.

5. Jeux pour enfants : Installation de modules sécuritaires dans un parc
   (Rue Arc-en-Ciel, 2026-02-20).
   Non acceptée,
   non confirmée,
   intervenant : god@gmail.com.

`projets`

1. Réfection du trottoir - Rue Sherbrooke

Arrondissement : Le Plateau-Mont-Royal
Statut : Prévu
Raison : Travaux de sécurité
Dates : 2025-02-15 à 2025-03-10
Intervenant : Ville de Montréal (admin)

2. Réparation de la chaussée - Boulevard René-Lévesque

Arrondissement : Ville-Marie
Statut : En cours
Raison : Travaux routiers
Dates : 2023-12-01 à 2025-01-15
Intervenant : Entrepreneur sous contrat (Samuel)

3. Installation de bornes de recharge électrique

Arrondissement : Rosemont-La Petite-Patrie
Statut : Terminé
Raison : Modernisation énergétique
Dates : 2024-10-01 à 2024-11-15
Intervenant : Ville de Montréal (admin)

4. Réparation des conduites d'eau - Avenue Papineau

Arrondissement : Ahuntsic-Cartierville
Statut : Prévu
Raison : Maintenance
Dates : 2024-12-25 à 2025-04-30
Intervenant : Entrepreneur sous contrat (Alexandre)

5. Rénovation du parc - Parc Angrignon

Arrondissement : Le Sud-Ouest
Statut : En cours
Raison : Amélioration des espaces verts
Dates : 2024-12-05 à 2025-02-28
Intervenant : Entreprise privée (God)

6. Démolition Bibliothèque Rivière-des-Prairies

Arrondissement : Rivière-des-Prairies-Pointe-aux-Trembles
Statut : En cours
Raison : Rénovation de l'infrastructure
Dates : 2025-02-09 à 2028-12-27
Intervenant : Ville de Montréal (admin)

`autres`

certains utilisateurs ont déjà des plages d'horaires, requêtes et notifications de faites tel que `levi.ackerman@gmail.com`

---

## Installation et instructions

Assurez-vous d'avoir les outils suivants installés sur votre machine :

- [Node.js](https://nodejs.org/) (Version 16+ recommandée)
- [Java JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) (Version 11+)
- Maven intégré avec le projet grâce à `mvnw` (pas besoin d'installation séparée)

---

VEUILLEZ TOUT LIRE AVANT DE SE METTRE À SUIVRE LES ÉTAPES

Le sections entre des accolades qui ont des ⚠️⚠️⚠️ sont `CONDITIONNELLES` et sont à réaliser `SEULEMENT` si le lancement plante.

## Installation

### Étape 1 : Cloner le dépôt

Clonez ce dépôt sur votre machine locale :

```bash
git clone <url_du_dépôt>
cd IFT2255-Groupe06/MaVille - prototype 2
```

### Étape 2 : Installer les dépendances pour le frontend et le backend (Spring Boot) et set-up le serveur local

1. En premier lieu, allez dans IFT2255-Groupe06/MaVille - prototype 2/frontend et faites `npm install`

2. Ensuite allez dans le répertoire IFT2255-Groupe06/MaVille - prototype 2/demo (backend) sur un terminal
   et exécutez la commande suivante pour démarrer l'application Spring Boot et build l'image: `./mvnw spring-boot:run`

> IMPORTANT: Cela démarre le serveur backend sur http://localhost:8080 qui DOIT être inutilisé sur votre machine, car le port est statique et non-dynamique
> et si vous avez déjà le port 8080 utilisé, il faudra le libérer de la manière indiquée plus bas:

3. Une fois fait, vous devez retourner sur le terminal dans le répertoire IFT2255-Groupe06/MaVille - prototype 2/frontend
   et effectuer `npm run dev`
   Le serveur frontend sera lancé sur `http://localhost:5173`

4. Sur le terminal vous devriez voir ceci :

> IMPORTANT: VOUS DEVEZ AVOIR LE SERVEUR LOCAL :5173 DE LIBÉRÉ

> springboot@0.0.0 dev
> vite

VITE v5.4.10 ready in 164 ms

➜ Local: http://localhost:5173/
➜ Network: use --host to expose
➜ press h + enter to show help

FAITES CTRL-CLIC SUR http://localhost:5173/ et ça vous ouvrira le serveur local pour tester l'application web.

⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️
IL EST IMPORTANT DE TOUJOURS FAIRE `CTRL-C` et `CONFIRMER avec O ou Y` DANS LE TERMINAL
OÙ VOUS AVEZ LANCÉ LE BACKEND ET LE FRONTEND RESPECTIVEMENT.
LA FERMETURE DU PORT BACKEND ET DU SERVEUR LOCAL FRONTEND POUR ÉVITER QUE VOUS N'ARRIVIEZ PLUS À LANCER LE SERVEUR PROCHAINE FOIS.⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️

---

### UN FICHIER DOCKER-COMPOSE SE RETROUVE À LA RACINE DU PROJET POUR LANCER LE SEVEUR ET LE CLIENT DANS DES CONTENEURS DOCKER

---

## Executer les tests

⚠️⚠️⚠️LES CLASSES DE TESTS SONT DANS `MaVille - prototype 2/demo/test/java/com/example/demo`⚠️⚠️⚠️

---

---

---

### ÉTAPES OPTIONNELLES, POUR DEBUGGER LE LANCEMENT

- SI LE PORT :8080 EST DÉJÀ UTILISÉ { ⚠️⚠️⚠️

Dans le command line de votre machine (CMD)

    Sous Linux / macOS :
        `lsof -i :8080` et notez le PID
            `kill -9 <PID>`

    Sous Windows :
        `netstat -ano | findstr :8080` et notez le PID
            `taskkill /PID <PID> /F`

}

- SI LE SERVEUR LOCAL :5173 EST DÉJÀ UTILISÉ { ⚠️⚠️⚠️

  Sous Linux / macOS :
  `lsof -i :5173` et notez le PID
  `kill -9 <PID>`

  Sous Windows :
  `netstat -ano | findstr :5173` et notez le PID
  `taskkill /PID <PID> /F`

}
