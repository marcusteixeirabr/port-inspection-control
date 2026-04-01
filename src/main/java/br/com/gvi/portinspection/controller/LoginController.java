package br.com.gvi.portinspection.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * LoginController — rotas públicas de autenticação.
 *
 * CONCEITO: o Spring Security processa o POST /login sozinho.
 * Nós só precisamos do GET /login para exibir o formulário.
 * O Spring Security cuida de validar username/senha, criar
 * sessão e redirecionar — sem precisarmos escrever isso.
 */
@Controller
public class LoginController {

    // GET /login — exibe o formulário de login
    @GetMapping("/login")
    public String login() {
        return "login"; // → templates/login.html
    }
}