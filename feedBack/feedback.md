# Glossaire 
- Code de la ville 
- Statut du projet, Projet
- Type de travaux, Travaux

Les points marqués ci-dessus sont les points importants que vous n'avez pas définis dans votre glossaire.

- **Courriel d'identification** :
    - L'utilisation d'un courriel pour s'identifier est un concept standard dans la plupart des applications, et il n'est pas indispensable de l'inclure dans un glossaire dédié à une application spécifique. Il s'agit d'un terme généralement compris par les utilisateurs, sans besoin d'une définition particulière dans ce contexte.

- **Mot de passe** :
    - Comme pour le courriel d'identification, l'utilisation d'un mot de passe pour accéder à une application est une fonctionnalité standard. Les utilisateurs comprennent généralement cette notion sans avoir besoin d'une définition spécifique dans le glossaire.

- **Fermeture de route** :
    - La fermeture de route est un exemple d'entrave spécifique qui peut être incluse sous un terme plus général comme **Entrave** ou **Projets de travaux**. L'application MaVille semble traiter de manière plus large les travaux et entraves. Distinguer la fermeture de route spécifiquement n'est pas nécessaire si le glossaire définit déjà un terme comme **Chantier** ou **Entrave**.

- **Menu principal** :
    - La définition du menu principal est basique et fait référence à une fonctionnalité standard dans toutes les applications. Il n'apporte pas une plus-value spécifique à l'application MaVille, et son inclusion semble redondante.

# Diagramme de cas d'utilisation 

### Respect du formalisme 
- Le formalisme est bien respecté, aucun problème de syntaxe UML.

### Identification des acteurs 
- Tous les acteurs sont bien identifiés.
- Plus de détails dans l'autre fichier de feedback.

### Cas d'utilisation 
- Le CU permettant la modification du profil est manquant.
- Les détails de la correction de cette section peuvent être trouvés dans l'autre fichier de feedback.

# Scénarios 

- **Signaler un problème à la ville** :
    - **Acteurs** :
        - L'acteur principal est le **résident** et non l'**utilisateur**.
        - Problème de cohérence avec le diagramme de classes.
    - Après l'étape 1, le système doit intervenir pour afficher le formulaire.
    - **Postcondition** :
        - Le problème a bien été signalé.

- **Consulter la liste des travaux** :
    - **Acteurs** :
        - Le système ne peut pas être un acteur dans ce CU.
    - **Précondition** :
        - Cette précondition ne peut pas être correcte, vu que ce CU inclut le CU **S'authentifier**.
        - Il doit obligatoirement être dans le scénario principal.
    - Problème : on veut consulter la liste des travaux et non un travail en particulier.

- **Créer un compte résident** :
    - **Acteurs** :
        - L'acteur principal est le **résident** et non l'**utilisateur**.
        - Le système ne peut pas être un acteur dans ce CU.
    - **Scénario alternatif** :
        - Pas de scénario alternatif pour l'étape 4.

- **Évaluer un travail terminé** :
    - **Acteurs** :
        - L'acteur principal est le **résident** et non l'**utilisateur**.
        - Le système ne peut pas être un acteur dans ce CU.
    - **Précondition** :
        - Cette précondition ne peut pas être correcte, vu que ce CU inclut le CU **S'authentifier**.
        - Il doit obligatoirement être dans le scénario principal.
    - **Postcondition** :
        - **"L'utilisateur reçoit une confirmation de l'enregistrement de son évaluation"** n'est pas une postcondition valide. Une postcondition décrit l'état du système après l'interaction avec l'utilisateur.
    - **Étapes** :
        - Comment l'utilisateur accède-t-il à la liste des travaux terminés auxquels il a été abonné ou qui l'intéressent ?
        - Il manque au moins deux étapes pour faire la transition entre l'étape 3 et l'étape 4, qui permettront à l'utilisateur de choisir une option pour donner son avis.

- **Consulter les notifications** :
    - **Acteurs** :
        - L'acteur principal est le **résident** et non l'**utilisateur**.
        - Le système ne peut pas être un acteur dans ce CU.
    - **Étapes manquantes** :
        - Qu'est-ce qui permet à l'utilisateur de sélectionner l'option **Consulter mes notifications** ?
    - **Précondition** :
        - Cette précondition ne peut pas être correcte, vu que ce CU inclut le CU **S'authentifier**.
        - Il doit obligatoirement être dans le scénario principal.
    - **Postcondition** :
        - Le système affiche les notifications.
    - **Étape en trop** :
        - L'étape 3 n'est pas nécessaire, car l'étape 2 nous permet déjà de voir les notifications.

- **Créer un compte intervenant** :
    - **Acteurs** :
        - Le système ne peut pas être un acteur dans ce CU.
    - **Étapes manquantes** :
        - Qu'est-ce qui permet à l'intervenant d'accéder à la page de création ?
    - **Postcondition** :
        - Postcondition incorrecte : **Le compte a bien été créé**.
    - **Scénario alternatif manquant** :
        - Après cette étape, si l'adresse courriel est déjà utilisée ou que les informations ne sont pas valides, le système affiche un message d'erreur. Que fait-on après ?

- **Mettre à jour les informations d'un chantier** :
    - **Étapes manquantes** :
        - Il faut faire quelque chose avant de pouvoir faire l'étape 1.
        - Il manque une étape entre l'étape 3 et l'étape 4 : le système affiche les détails du chantier choisi et permet à l'utilisateur de faire une modification.

- **Soumettre un nouveau projet de travaux** :
    - **Scénario alternatif** :
        - Un scénario manque pour permettre de consulter les préférences des utilisateurs afin d'être cohérent avec le diagramme de CU.
    - **Étapes manquantes** :
        - Il faut faire quelque chose avant de pouvoir faire l'étape 1.
        - Il manque une étape entre l'étape 1 et l'étape 2.
    - **Scénario alternatif** :
        - Le scénario alternatif est pour l'étape 3 et non l'étape 2.

- **Consulter la liste des requêtes de travaux** :
    - L'étape 4 doit être dans le scénario alternatif, vu que c'est une extension du CU. Cela permet d'être cohérent avec le diagramme de CU.
    - **Précondition** :
        - La partie **avoir les droits nécessaires pour répondre aux requêtes de travaux des résidents** n'est pas nécessaire, car on veut juste connaître l'état du système avant l'interaction avec l'utilisateur.
    - **Étapes manquantes** :
        - Il faut faire quelque chose avant de pouvoir faire l'étape 1.

# Diagramme d'activités 

### Respect du formalisme 
- Le formalisme et la syntaxe UML sont bien respectés.

### Contenu du diagramme 
- Voir les détails de la correction dans l'autre fichier de feedback.

# Analyse 📈 

#### Risques 
- Les risques énoncés sont corrects et bien définis.

#### Besoins non fonctionnels 
- Les besoins non fonctionnels sont bien choisis et bien définis.

#### Besoins matériels 
- Un peu plus de détails.

#### Solution de stockage 
- Un peu plus de détails.

#### Solutions d'intégration 
- Un peu plus de détails.

# Prototype 
- Pas de gestion d'erreur pour une mauvaise entrée.

# Git 
- Git ok.

# Rapports 
- Rapport ok.

# Bonus 
