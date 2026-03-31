package br.com.gvi.portinspection.controller;

import br.com.gvi.portinspection.domain.dto.NavioForm;
import br.com.gvi.portinspection.domain.dto.NavioResultado;
import br.com.gvi.portinspection.domain.enums.TipoNavio;
import br.com.gvi.portinspection.service.RiscoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * NavioController — camada de apresentação.
 *
 * Responsabilidades ÚNICAS deste Controller:
 *   1. Receber requisições HTTP
 *   2. Preparar os dados para a View (formulário ou resultado)
 *   3. Chamar o Service quando houver lógica a executar
 *   4. Decidir qual template renderizar
 *
 * O que o Controller NÃO faz:
 *   - Calcular risco ou prioridade (isso é do RiscoService)
 *   - Acessar banco de dados diretamente
 *   - Formatar datas ou textos para exibição (isso é do Thymeleaf)
 *
 * @RequestMapping("/navios"): todas as rotas deste controller
 * começam com /navios — boa prática de organização.
 */
@Controller
@RequestMapping("/navios")
public class NavioController {

    /**
     * Injeção de dependência via construtor.
     *
     * Por que construtor e não @Autowired no campo?
     *   - Mais explícito: fica claro o que o Controller precisa para funcionar
     *   - Facilita testes unitários (você passa um mock no construtor)
     *   - O Spring detecta automaticamente e injeta o bean correto
     */
    private final RiscoService riscoService;

    public NavioController(RiscoService riscoService) {
        this.riscoService = riscoService;
    }

    // ── GET /navios/identificar ───────────────────────────────
    /**
     * Exibe o formulário vazio para identificação do navio.
     *
     * Model: objeto que o Spring passa para o Thymeleaf.
     * Tudo que adicionamos no Model fica disponível no HTML
     * como variável: ${navio}, ${tiposNavio}, etc.
     *
     * "navio" no model: o Thymeleaf usa para associar os campos
     * do formulário (th:field="*{nome}") ao objeto NavioForm.
     *
     * "tiposNavio": lista de todos os valores do enum TipoNavio,
     * usada para popular o <select> no HTML.
     */
    @GetMapping("/identificar")
    public String exibirFormulario(Model model) {
        model.addAttribute("navio", new NavioForm());          // form vazio
        model.addAttribute("tiposNavio", TipoNavio.values()); // opções do select
        return "navio-form";                                   // → templates/navio-form.html
    }

    // ── POST /navios/identificar ──────────────────────────────
    /**
     * Processa o formulário enviado pelo usuário.
     *
     * @ModelAttribute: o Spring pega os campos do formulário HTTP
     * e preenche automaticamente o objeto NavioForm.
     *
     * @Valid: dispara a validação do Bean Validation (as anotações
     * @NotBlank, @NotNull, etc. do NavioForm).
     *
     * BindingResult: contém o resultado da validação.
     * IMPORTANTE: deve vir logo APÓS o @ModelAttribute — se vier
     * depois, o Spring lança exceção antes de você poder tratá-lo.
     *
     * Se houver erros → volta o formulário com mensagens de erro.
     * Se tudo ok    → chama o Service, passa resultado para a View.
     */
    @PostMapping("/identificar")
    public String processarFormulario(
            @Valid @ModelAttribute("navio") NavioForm form,
            BindingResult bindingResult,
            Model model) {

        // ── valida os dados do formulário ─────────────────────
        if (bindingResult.hasErrors()) {
            // Adiciona novamente os tipos para re-popular o <select>
            // (o Spring não mantém isso entre requisições)
            model.addAttribute("tiposNavio", TipoNavio.values());
            // Retorna o mesmo formulário — o Thymeleaf exibirá
            // as mensagens de erro ao lado de cada campo inválido
            return "navio-form";
        }

        // ── delega o cálculo para o Service ──────────────────
        NavioResultado resultado = riscoService.calcular(form);

        // ── envia o resultado para a View ─────────────────────
        model.addAttribute("resultado", resultado);

        return "navio-resultado"; // → templates/navio-resultado.html
    }
}