package br.com.gvi.portinspection.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class WelcomeController {

    // Mapeia a rota "/" para retornar o template "welcome"
    // O Spring Boot, por padrão, procura por templates Thymeleaf em src/main/resources/templates
    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/sobre")
    public String sobre() {
        return "sobre";
    }
    
    
}
