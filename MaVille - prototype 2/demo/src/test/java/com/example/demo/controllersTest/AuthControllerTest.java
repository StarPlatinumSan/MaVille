package com.example.demo.controllersTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.controllers.AuthController;
import com.example.demo.controllers.ProjetController;
import com.example.demo.controllers.NotificationsController;
import com.example.demo.repositories.RequeteRepository;
import com.example.demo.controllers.RequeteController;
import com.example.demo.controllers.TravauxAPI;
import com.example.demo.models.PlageHoraire;
import com.example.demo.models.Notification;
import com.example.demo.models.Projet;
import com.example.demo.models.User;
import com.example.demo.controllers.UserController;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.ProjetRepository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;


class AuthControllerTest {

    @Mock
    private RequeteRepository requeteRepository;

    @Mock
    private UserRepository utilisateurRepository;

    @Mock
    private ProjetRepository projetRepository;

    @Mock 
    private NotificationsController notificationService;

    @InjectMocks
    private ProjetController projetController;

    @InjectMocks
    private UserController userController;      

    @InjectMocks
    private RequeteController requeteController;

    @InjectMocks
    private AuthController authController;

    private TravauxAPI travauxAPI;
    private MockRestServiceServer mockServer;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        travauxAPI = new TravauxAPI();
        RestTemplate restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        MockitoAnnotations.openMocks(this);
        when(notificationService.sendNotifQuartier(anyString(), any(com.example.demo.models.Notification.class)))
            .thenReturn(ResponseEntity.ok("Notification envoyée."));

    }


    // ***1*** Enregistrement d'un utilisateur 

    // Vérifie qu'un utilisateur résident peut être enregistré avec succès
    @Test
    void testEnregistrer_SuccesResident() {
        User nouveauUtilisateur = new User("nomResident", "mdpResident", 20, "resident@exemple.com", "5141234567", "123 Avenue Rue", "h1e2a9");
    
        when(utilisateurRepository.getAllUsers()).thenReturn(List.of());
        doNothing().when(utilisateurRepository).saveUser(anyList());
    
        String resultat = authController.register(nouveauUtilisateur);
    
        assertEquals("L'utilisateur a été enregistré avec succès.", resultat);
        verify(utilisateurRepository, times(1)).saveUser(anyList());
    }
    
    // Vérifie que l'enregistrement échoue si l'email existe déjà
    @Test
    void testEnregistrer_EmailExistant() {
     
        User utilisateurExistant = new User("nomResident", "mdpResident", 20, "resident@exemple.com", "5141234567", "123 Avenue Rue", "h1e2a9");
        User nouveauUtilisateur = new User("nouveauNom", "nouveauMdp", 20, "resident@exemple.com", "5141234568", "456 Avenue Rue", "h2e3b4");
        when(utilisateurRepository.getAllUsers()).thenReturn(List.of(utilisateurExistant));

        String resultat = authController.register(nouveauUtilisateur);

        assertEquals("Cet email est déjà associé à un compte.", resultat);
        verify(utilisateurRepository, never()).saveUser(anyList());
    }

    // Vérifie que l'enregistrement échoue si l'utilisateur est mineur
    @Test
    void testEnregistrer_EchecMineur() {
        User utilisateurMineur = new User("youngUser", "password", 15, "young@example.com", "5141234567", "123 Youth Street", "H2Y3K6");
    
        when(utilisateurRepository.getAllUsers()).thenReturn(List.of());
    
        String resultat = authController.register(utilisateurMineur);
    
        assertEquals("Vous devez avoir au moins 16 ans pour créer un compte.", resultat);
        verify(utilisateurRepository, never()).saveUser(anyList());
    }
    


    // ***2*** Connexion d'un utilisateur 

    // Vérifie qu'un utilisateur résident peut se connecter avec succès
    @Test
    void testConnexion_SuccesResident() {
     
        User utilisateurExistant = new User("utilisateur1", null, 20, "email@mail.com", "5141234567", "123 Avenue", "h1e2a9");
        utilisateurExistant.setPassword(BCrypt.hashpw("mdpSimple", BCrypt.gensalt())); 

        when(utilisateurRepository.getAllUsers()).thenReturn(List.of(utilisateurExistant));

        User utilisateurConnexion = new User(null, "mdpSimple", 20, "email@mail.com", null, null, null);

        ResponseEntity<?> reponse = authController.login(utilisateurConnexion);

        assertEquals(200, reponse.getStatusCodeValue());
        assertTrue(reponse.getBody().toString().contains("Connexion réussie."));
    }

    // Vérifie qu'une connexion échoue avec un mot de passe incorrect
    @Test
    void testConnexion_EchecMotDePasseIncorrect() {
        User utilisateurExistant = new User("nomUtilisateur", null, 25, "resident@exemple.com", "5141234567", "123 Avenue Rue", "H1E2A9");
        utilisateurExistant.setPassword(BCrypt.hashpw("mdpCorrect", BCrypt.gensalt()));
    
        when(utilisateurRepository.getAllUsers()).thenReturn(List.of(utilisateurExistant));
    
        User utilisateurConnexion = new User(null, "mdpIncorrect", 25, "resident@exemple.com", null, null, null);
    
        ResponseEntity<?> reponse = authController.login(utilisateurConnexion);
    
        assertEquals(401, reponse.getStatusCodeValue());
        assertEquals("Compte introuvable.", reponse.getBody());
    }
    
    // Vérifie qu'une connexion échoue pour un utilisateur inexistant
    @Test
    void testConnexion_EchecUtilisateurInexistant() {
        when(utilisateurRepository.getAllUsers()).thenReturn(List.of());
    
        User utilisateurConnexion = new User(null, "mdpQuelconque", 25, "nonexistant@exemple.com", null, null, null);
    
        ResponseEntity<?> reponse = authController.login(utilisateurConnexion);
    
        assertEquals(401, reponse.getStatusCodeValue());
        assertEquals("Informations incorrectes.", reponse.getBody());
    }
    


    // ***3*** Soumission d'un projet 

    // Vérifie que l'ajout d'un projet est effectué avec succès
    @Test
    void testAjouterProjet_Succes() {
        Projet projet = new Projet(null, "Projet Pont", "Ahuntsic", "En cours", "Construction de pont", "2024-12-01", "Intervenant Public", "John Doe");

        doNothing().when(projetRepository).addProjet(any(Projet.class));

        ResponseEntity<String> response = projetController.addProjet(projet);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Projet ajouté avec succès !", response.getBody());

        verify(projetRepository, times(1)).addProjet(any(Projet.class));
    }

    // Vérifie qu'une notification est envoyée lors de l'ajout d'un projet
    @Test
    void testSoumissionProjet_SuccesNotification() {
        Projet projetValide = new Projet(null, "Projet Parc", "Villeray", "Planifié", "Aménagement d'un parc", "2024-12-15", "Privé", "Alice Smith");
    
        doNothing().when(projetRepository).addProjet(any(Projet.class));
        when(notificationService.sendNotifQuartier(anyString(), any(com.example.demo.models.Notification.class)))
            .thenReturn(ResponseEntity.ok("Notification envoyée."));

        ResponseEntity<String> response = projetController.addProjet(projetValide);
    
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Projet ajouté avec succès"));
        verify(projetRepository, times(1)).addProjet(any(Projet.class));
        verify(notificationService, times(1)).sendNotifQuartier(anyString(), any(com.example.demo.models.Notification.class));
    }

    // Vérifie que le statut d'un projet peut être mis à jour avec succès
    @Test
    void testMiseAJourStatutProjet_Succes() {
        Projet projetExistant = new Projet("1", "Projet École", "Rosemont", "Planifié", "Construction d'école", "2024-12-01", "Public", "Jean Dupont");
        List<Projet> projets = List.of(projetExistant);

        when(projetRepository.getAllProjets()).thenReturn(projets);
        doNothing().when(projetRepository).saveProjets(anyList());

        ResponseEntity<String> response = projetController.updateStatut("1", "En cours");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Statut mis à jour avec succès !", response.getBody());
        verify(projetRepository, times(1)).saveProjets(anyList());
    }


    
    // ***4*** Voir les notifications

    // Vérifie que les notifications d'un utilisateur existant sont récupérées
    @Test
    void testVoirNotifications_UtilisateurExistant() {
        User utilisateur = new User("nomUser", "password", 25, "user@example.com", "5141234567", "123 Rue Exemple", "H2X1Y1");
        Notification notification1 = new Notification("Titre 1", "Description 1");
        Notification notification2 = new Notification("Titre 2", "Description 2");
        utilisateur.addNotification(notification1);
        utilisateur.addNotification(notification2);
    
        when(utilisateurRepository.getUserByEmail("user@example.com")).thenReturn(utilisateur);
    
        ResponseEntity<List<Notification>> response = userController.getAllNotifications("user@example.com");
    
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().stream().anyMatch(n -> n.getTitle().equals("Titre 1")));
        assertTrue(response.getBody().stream().anyMatch(n -> n.getTitle().equals("Titre 2")));
    }
    
    // Vérifie que la récupération échoue pour un utilisateur inexistant
    @Test
    void testVoirNotifications_UtilisateurInexistant() {
        when(utilisateurRepository.getUserByEmail("inconnu@example.com")).thenReturn(null);

        ResponseEntity<List<Notification>> response = userController.getAllNotifications("inconnu@example.com");

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    // Vérifie qu'aucune notification n'est retournée si la liste est vide
    @Test
    void testVoirNotifications_AucuneNotification() {
        User utilisateur = new User("nomUser", "password", 25, "user@example.com", "5141234567", "123 Rue Exemple", "H2X1Y1");

        when(utilisateurRepository.getUserByEmail("user@example.com")).thenReturn(utilisateur);

        ResponseEntity<List<Notification>> response = userController.getAllNotifications("user@example.com");

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isEmpty());
    }



    // ***5*** Consultation des travaux 

    // Vérifie que les travaux sont récupérés avec succès, même si la liste est vide
    @Test
    void testGetTravaux_Succes() {
        String apiResponse = "{\"success\": true, \"result\": {\"records\": []}}";
        mockServer.expect(requestTo("https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b"))
                .andRespond(withSuccess(apiResponse, org.springframework.http.MediaType.APPLICATION_JSON));

        ResponseEntity<?> response = travauxAPI.getTravaux();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Vérifie que les travaux avec données sont récupérés avec succès
    @Test
    void testGetTravaux_ReponseValide_avecDonnees() {
        String apiResponse = "{\"success\": true, \"result\": {\"records\": [{\"id\": 1, \"name\": \"Travaux exemple\"}]}}";
    
        mockServer.expect(requestTo("https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b"))
                .andRespond(withSuccess(apiResponse, org.springframework.http.MediaType.APPLICATION_JSON));
    
        ResponseEntity<?> response = travauxAPI.getTravaux();
    
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Vérifie que la récupération des entraves est effectuée avec succès
    @Test
    void testGetEntraves_Succes() {
        String apiResponse = "{\"success\": true, \"result\": {\"records\": []}}";
        mockServer.expect(requestTo("https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd"))
                .andRespond(withSuccess(apiResponse, org.springframework.http.MediaType.APPLICATION_JSON));

        ResponseEntity<?> response = travauxAPI.getEntraves();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    // ***6*** Filtrer les travaux par quartier 

    // Vérifie que les travaux d'un quartier spécifique sont retournés
    @Test
    void testChercherTravaux_QuartierSpecifique() {
        PlageHoraire plage1 = new PlageHoraire(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0));
        PlageHoraire plage2 = new PlageHoraire(DayOfWeek.TUESDAY, LocalTime.of(13, 0), LocalTime.of(17, 0));
        User user1 = new User("User1", "password", 25, "user1@example.com", "5141234567", "123 Rue Exemple", "H2X1Y1");
        User user2 = new User("User2", "password", 30, "user2@example.com", "5149876543", "456 Rue Exemple", "H3Z2W2");
    
        user1.setQuartier("Ahuntsic");
        user1.ajouterPlageHoraire(plage1);
        user2.setQuartier("Villeray");
        user2.ajouterPlageHoraire(plage2);

        when(utilisateurRepository.getAllUsers()).thenReturn(List.of(user1, user2));
    
        ResponseEntity<List<PlageHoraire>> response = userController.getPlagesByQuartier("Ahuntsic");
   
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertTrue(response.getBody().stream().anyMatch(p -> p.getJour().equals(DayOfWeek.MONDAY)));
    }
    
    // Vérifie qu'aucun travail n'est retourné pour un quartier sans travaux
    @Test
    void testChercherTravaux_PasDeResultat() {
        User user1 = new User("User1", "password", 25, "user1@example.com", "5141234567", "123 Rue Exemple", "H2X1Y1");
        user1.setQuartier("Ahuntsic");
    
        when(utilisateurRepository.getAllUsers()).thenReturn(List.of(user1));

        ResponseEntity<List<PlageHoraire>> response = userController.getPlagesByQuartier("Villeray");
    
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isEmpty());
    }
    
    // Vérifie que tous les travaux sont retournés lorsque le filtre est absent
    @Test
    void testChercherTravaux_TousLesQuartiers() {
        PlageHoraire plage1 = new PlageHoraire(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0));
        PlageHoraire plage2 = new PlageHoraire(DayOfWeek.TUESDAY, LocalTime.of(13, 0), LocalTime.of(17, 0));
        User user1 = new User("User1", "password", 25, "user1@example.com", "5141234567", "123 Rue Exemple", "H2X1Y1");
        User user2 = new User("User2", "password", 30, "user2@example.com", "5149876543", "456 Rue Exemple", "H3Z2W2");
    
        user1.ajouterPlageHoraire(plage1);
        user2.ajouterPlageHoraire(plage2);
    
        when(utilisateurRepository.getAllUsers()).thenReturn(List.of(user1, user2));
    
        ResponseEntity<List<PlageHoraire>> response = userController.getPlagesByQuartier(null);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().stream().anyMatch(p -> p.getJour().equals(DayOfWeek.MONDAY)));
        assertTrue(response.getBody().stream().anyMatch(p -> p.getJour().equals(DayOfWeek.TUESDAY)));
    }
    
}









