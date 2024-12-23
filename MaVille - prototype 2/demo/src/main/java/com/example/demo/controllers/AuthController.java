package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.util.QuartierMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur pour gérer l'authentification des utilisateurs.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private UserRepository userRepository;

    /**
     * Enregistre un nouvel utilisateur dans le système.
     *
     * @param user L'objet User contenant les informations du nouvel utilisateur.
     * @return Un message indiquant le succès ou l'échec de l'enregistrement.
     */
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        System.out.println("Utilisateur : " + user);

        if ((user.getPhone() == null || user.getPhone().isEmpty()) && user.getKey() == null) {
            return "Un numéro de téléphone est requis pour créer un compte.";
        }

        if (user.getAge() < 16) {
            return "Vous devez avoir au moins 16 ans pour créer un compte.";
        }

        List<User> usersList = new ArrayList<>(userRepository.getAllUsers());

        for (User u : usersList) {
            if (u.getEmail().equals(user.getEmail())) {
                return "Cet email est déjà associé à un compte.";
            }
        }

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        try {	
            String quartier = QuartierMapper.getQuartierByCodePostal(user.getCodePostal());
            user.setQuartier(quartier);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user.getKey() != null) {
            user.setTypeIntervenant(user.getTypeIntervenant());
        }
                
        usersList.add(user);
        userRepository.saveUser(usersList);

        return "L'utilisateur a été enregistré avec succès.";
    }
    
    /**
     * Authentifie un utilisateur en fonction de ses identifiants.
     *
     * @param user L'objet User contenant l'email et le mot de passe de l'utilisateur.
     * @return Une réponse HTTP avec un message de réussite et les informations utilisateur,
     *         ou une erreur si l'authentification échoue.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        List<User> usersList = userRepository.getAllUsers();

        for (User u : usersList) {
            if (u.getEmail().equals(user.getEmail())) {
                if (BCrypt.checkpw(user.getPassword(), u.getPassword())) {
                    if (u.getKey() != null && u.getKey().equals(user.getKey())) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("message", "Connexion réussie.");
                        response.put("username", u.getUsername());
                        response.put("email", u.getEmail());
                        response.put("role", u.getKey() == null ? "Résident" : "Intervenant");
                        response.put("phone", u.getPhone());
                        response.put("address", u.getAddress());
                        response.put("key", u.getKey());
                        return ResponseEntity.ok(response);
                    } else if (u.getKey() == null) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("message", "Connexion réussie.");
                        response.put("username", u.getUsername());
                        response.put("email", u.getEmail());
                        response.put("role", u.getKey() == null ? "Résident" : "Intervenant");
                        response.put("phone", u.getPhone());
                        response.put("address", u.getAddress());
                        response.put("key", u.getKey());
                        return ResponseEntity.ok(response);
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Compte introuvable.");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Informations incorrectes.");
    }
}
