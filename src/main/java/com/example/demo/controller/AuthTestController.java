package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class AuthTestController {

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {

        String username = payload.get("username");
        String password = payload.get("password");

        if ("admin@teste.com".equals(username) && "123456".equals(password)) {

            Map<String, Object> response = new HashMap<>();

            response.put("token", "fake-jwt-token-123");

            Map<String, Object> user = new HashMap<>();
            user.put("id", 1);
            user.put("name", "Kaique");
            user.put("email", username);
            user.put("ad_groups", List.of("TI"));

            List<Map<String, Object>> permissions = new ArrayList<>();

            Map<String, Object> permission = new HashMap<>();
            permission.put("id", 1);
            permission.put("ad_group_name", "TI");
            permission.put("branch_id", null);
            permission.put("department_id", null);
            permission.put("is_admin", true);
            permission.put("description", "Administrador do sistema");

            permissions.add(permission);

            response.put("user", user);
            response.put("permissions", permissions);
            response.put("message", "Login realizado com sucesso backend");

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Credenciais inválidas"));
    }
}