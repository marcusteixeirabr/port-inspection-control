package br.com.gvi.portinspection.controller;

import br.com.gvi.portinspection.domain.entity.Inspetor;
import br.com.gvi.portinspection.service.InspetorService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * InspetorController — rotas protegidas pelo Spring Security.
 *
 * CONCEITO: @AuthenticationPrincipal
 * Injeta os dados do usuário logado diretamente no método.
 * O Spring Security preenche isso automaticamente a partir
 * da sessão — você não precisa buscar no banco de novo.
 *
 * Todas as rotas /inspetores/** já estão protegidas pelo
 * SecurityConfig — se não estiver logado, o Spring redireciona
 * automaticamente para /login antes de chegar aqui.
 */
@Controller
@RequestMapping("/inspetores")
public class InspetorController {

    private final InspetorService service;

    public InspetorController(InspetorService service) {
        this.service = service;
    }

    // GET /inspetores
    @GetMapping
    public String listar(Model model,
            @AuthenticationPrincipal UserDetails usuarioLogado) {

        model.addAttribute("inspetores", service.listarTodos());
        model.addAttribute("usuarioLogado", usuarioLogado.getUsername());
        model.addAttribute("inspetorEditando", null);
        return "inspetores";
    }

    // POST /inspetores
    @PostMapping
    public String salvar(
            @RequestParam String nome,
            @RequestParam String apelido,
            RedirectAttributes redirect) {

        if (nome == null || nome.trim().length() < 2) {
            redirect.addFlashAttribute("erro", "Nome inválido (mínimo 2 caracteres).");
            return "redirect:/inspetores";
        }
        if (apelido == null || apelido.trim().isEmpty()) {
            redirect.addFlashAttribute("erro", "Apelido é obrigatório.");
            return "redirect:/inspetores";
        }

        service.salvar(nome, apelido);
        redirect.addFlashAttribute("sucesso", "Inspetor cadastrado com sucesso!");
        return "redirect:/inspetores";
    }

    // GET /inspetores/{id}/editar
    @GetMapping("/{id}/editar")
    public String exibirEdicao(@PathVariable Long id, Model model,
            @AuthenticationPrincipal UserDetails usuarioLogado) {

        Optional<Inspetor> opcional = service.buscarPorId(id);
        if (opcional.isEmpty()) return "redirect:/inspetores";

        model.addAttribute("inspetores", service.listarTodos());
        model.addAttribute("usuarioLogado", usuarioLogado.getUsername());
        model.addAttribute("inspetorEditando", opcional.get());
        return "inspetores";
    }

    // POST /inspetores/{id}
    @PostMapping("/{id}")
    public String atualizar(
            @PathVariable Long id,
            @RequestParam String nome,
            @RequestParam String apelido,
            RedirectAttributes redirect) {

        boolean ok = service.atualizar(id, nome, apelido);
        redirect.addFlashAttribute(
            ok ? "sucesso" : "erro",
            ok ? "Inspetor atualizado!" : "Inspetor não encontrado."
        );
        return "redirect:/inspetores";
    }

    // POST /inspetores/{id}/remover
    @PostMapping("/{id}/remover")
    public String remover(@PathVariable Long id, RedirectAttributes redirect) {
        boolean ok = service.remover(id);
        redirect.addFlashAttribute(
            ok ? "sucesso" : "erro",
            ok ? "Inspetor removido." : "Inspetor não encontrado."
        );
        return "redirect:/inspetores";
    }
}