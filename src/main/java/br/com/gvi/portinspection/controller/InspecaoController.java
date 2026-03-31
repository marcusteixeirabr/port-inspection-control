package br.com.gvi.portinspection.controller;

import br.com.gvi.portinspection.domain.entity.Inspecao;
import br.com.gvi.portinspection.service.InspecaoService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * InspecaoController — controller de inspeções.
 *
 * CONCEITO NOVO: RedirectAttributes
 * Após um POST bem-sucedido, redirecionar para um GET
 * evita o problema de "reenviar formulário ao atualizar a página".
 * Isso se chama padrão PRG (Post/Redirect/Get).
 *
 * RedirectAttributes permite passar mensagens temporárias
 * (flash messages) que sobrevivem ao redirect e somem depois.
 *
 * ROTAS desta página:
 *   GET  /inspecoes          → lista todas
 *   POST /inspecoes          → salva nova
 *   GET  /inspecoes/{id}/editar → exibe form de edição
 *   POST /inspecoes/{id}     → atualiza existente
 *   POST /inspecoes/{id}/remover → remove
 */
@Controller
@RequestMapping("/inspecoes")
public class InspecaoController {

    private final InspecaoService service;

    public InspecaoController(InspecaoService service) {
        this.service = service;
    }

    // ── GET /inspecoes ────────────────────────────────────────
    /**
     * Exibe a tabela com todas as inspeções + formulário de nova.
     *
     * Model recebe:
     *   inspecoes → lista para a tabela
     *   hoje      → preenche a data padrão do input
     */
    @GetMapping
    public String listar(Model model) {
        List<Inspecao> inspecoes = service.listarTodas();
        model.addAttribute("inspecoes", inspecoes);
        model.addAttribute("hoje", LocalDate.now());
        model.addAttribute("inspecaoEditando", null); // sem edição ativa
        return "inspecoes";
    }

    // ── POST /inspecoes ───────────────────────────────────────
    /**
     * Salva uma nova inspeção.
     *
     * @RequestParam: extrai parâmetros individuais do formulário.
     * Diferente do @ModelAttribute que preenche um objeto inteiro,
     * aqui pegamos campo a campo — mais simples para forms pequenos.
     *
     * @DateTimeFormat: diz ao Spring como converter a string
     * "yyyy-MM-dd" do input date para LocalDate.
     *
     * RedirectAttributes.addFlashAttribute(): mensagem temporária
     * que aparece só uma vez após o redirect.
     */
    @PostMapping
    public String salvar(
            @RequestParam @NotBlank String nomeNavio,
            @RequestParam @NotNull
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInspecao,
            RedirectAttributes redirect) {

        service.salvar(nomeNavio.trim(), dataInspecao);
        redirect.addFlashAttribute("sucesso", "Inspeção registrada com sucesso!");
        return "redirect:/inspecoes"; // PRG: redireciona para o GET
    }

    // ── GET /inspecoes/{id}/editar ────────────────────────────
    /**
     * Recarrega a página com o registro em modo de edição.
     *
     * @PathVariable: captura o {id} da URL.
     * Ex: GET /inspecoes/3/editar → id = 3
     */
    @GetMapping("/{id}/editar")
    public String exibirEdicao(@PathVariable Long id, Model model) {
        Optional<Inspecao> opcional = service.buscarPorId(id);

        // se não encontrar, volta para a lista sem quebrar
        if (opcional.isEmpty()) {
            return "redirect:/inspecoes";
        }

        model.addAttribute("inspecoes", service.listarTodas());
        model.addAttribute("hoje", LocalDate.now());
        model.addAttribute("inspecaoEditando", opcional.get()); // linha em edição
        return "inspecoes";
    }

    // ── POST /inspecoes/{id} ──────────────────────────────────
    /**
     * Atualiza uma inspeção existente.
     *
     * CONCEITO: _method override
     * Formulários HTML só suportam GET e POST.
     * Para simular PUT/DELETE usamos um campo oculto _method
     * e o HiddenHttpMethodFilter do Spring converte.
     * Mas para manter simples, usamos POST mesmo com path diferente.
     */
    @PostMapping("/{id}")
    public String atualizar(
            @PathVariable Long id,
            @RequestParam @NotBlank String nomeNavio,
            @RequestParam @NotNull
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInspecao,
            RedirectAttributes redirect) {

        boolean atualizado = service.atualizar(id, nomeNavio.trim(), dataInspecao);

        if (atualizado) {
            redirect.addFlashAttribute("sucesso", "Inspeção atualizada com sucesso!");
        } else {
            redirect.addFlashAttribute("erro", "Inspeção não encontrada.");
        }
        return "redirect:/inspecoes";
    }

    // ── POST /inspecoes/{id}/remover ──────────────────────────
    /**
     * Remove uma inspeção.
     * Usamos POST porque HTML não suporta DELETE nativamente.
     */
    @PostMapping("/{id}/remover")
    public String remover(@PathVariable Long id, RedirectAttributes redirect) {
        boolean removido = service.remover(id);

        if (removido) {
            redirect.addFlashAttribute("sucesso", "Inspeção removida.");
        } else {
            redirect.addFlashAttribute("erro", "Inspeção não encontrada.");
        }
        return "redirect:/inspecoes";
    }
}