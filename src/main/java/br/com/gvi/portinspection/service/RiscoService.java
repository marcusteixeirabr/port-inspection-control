package br.com.gvi.portinspection.service;

import br.com.gvi.portinspection.domain.dto.NavioForm;
import br.com.gvi.portinspection.domain.dto.NavioResultado;
import br.com.gvi.portinspection.domain.enums.GrauRisco;
import br.com.gvi.portinspection.domain.enums.Prioridade;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * RiscoService — camada de serviço.
 *
 * Aqui mora TODA a lógica de negócio da classificação de navios.
 * O Controller não sabe como calcular risco — ele só sabe chamar
 * o Service e passar o resultado para a View. Essa separação é
 * o coração da arquitetura em camadas.
 *
 * @Service: diz ao Spring para gerenciar esta classe como um
 * "bean" — criá-la, mantê-la em memória e injetá-la onde for
 * necessário via @Autowired ou construtor.
 *
 * REGRAS DE NEGÓCIO implementadas aqui:
 *
 *   Grau de Risco (por idade):
 *     ALTO   → > 20 anos
 *     PADRÃO → 10 a 20 anos
 *     BAIXO  → < 10 anos
 *
 *   Prioridade (por grau + meses desde inspeção):
 *     ALTO:   P0 ≤ 2m | P2 ≤ 4m | P1 > 4m
 *     PADRÃO: P0 ≤ 5m | P2 ≤ 10m | P1 > 10m
 *     BAIXO:  P0 ≤ 9m | P2 ≤ 18m | P1 > 18m
 */
@Service
public class RiscoService {

    /**
     * Método principal: recebe os dados do formulário e retorna
     * um NavioResultado com todas as informações calculadas.
     *
     * O Controller chama exatamente este método — nada mais.
     */
    public NavioResultado calcular(NavioForm form) {

        // ── 1. Calcular a idade do navio ──────────────────────
        // LocalDate.now() retorna a data de hoje.
        // Period.between() calcula a diferença entre duas datas.
        // .getYears() extrai só os anos completos.
        int anoAtual   = LocalDate.now().getYear();
        int idadeAnos  = anoAtual - form.getAnoConstrucao();

        // Garante que a idade nunca seja negativa
        // (o @Max no form já protege, mas defesa em profundidade é boa prática)
        if (idadeAnos < 0) idadeAnos = 0;

        // ── 2. Calcular meses desde a última inspeção ─────────
        // ChronoUnit.MONTHS.between() conta meses inteiros entre duas datas.
        // Usamos Math.abs() para garantir valor positivo mesmo se a data
        // de inspeção for ligeiramente futura por fuso horário.
        long mesesDesdeInspecao = Math.abs(
            ChronoUnit.MONTHS.between(form.getUltimaInspecao(), LocalDate.now())
        );

        // ── 3. Determinar o Grau de Risco ─────────────────────
        // Delegamos para o método estático do enum — a lógica fica encapsulada lá.
        GrauRisco grauRisco = GrauRisco.calcular(idadeAnos);

        // ── 4. Determinar a Prioridade ────────────────────────
        // A prioridade depende de dois fatores: grau de risco + meses.
        // Usamos um método privado para manter o método principal limpo.
        Prioridade prioridade = calcularPrioridade(grauRisco, mesesDesdeInspecao);

        // ── 5. Montar e retornar o resultado ──────────────────
        // O construtor de NavioResultado exige todos os campos —
        // assim não corremos o risco de esquecer algum dado.
        return new NavioResultado(
            form.getNome(),
            form.getTipo(),
            form.getAnoConstrucao(),
            form.getUltimaInspecao(),
            idadeAnos,
            mesesDesdeInspecao,
            grauRisco,
            prioridade
        );
    }

    /**
     * Calcula a prioridade de inspeção com base no grau de risco
     * e nos meses decorridos desde a última inspeção.
     *
     * Método privado: detalhe de implementação do Service.
     * Ninguém de fora precisa saber como isso funciona internamente.
     *
     * O switch usa "pattern matching" do Java moderno para cobrir
     * cada case do enum de forma explícita — o compilador avisa
     * se algum case do enum não for tratado.
     */
    private Prioridade calcularPrioridade(GrauRisco grauRisco, long meses) {
        return switch (grauRisco) {

            case ALTO ->
                // Alto risco: janelas curtas de 2 e 4 meses
                meses <= 2  ? Prioridade.P0 :
                meses <= 4  ? Prioridade.P2 :
                              Prioridade.P1;

            case PADRAO ->
                // Risco padrão: janelas de 5 e 10 meses
                meses <= 5  ? Prioridade.P0 :
                meses <= 10 ? Prioridade.P2 :
                              Prioridade.P1;

            case BAIXO ->
                // Baixo risco: janelas de 9 e 18 meses
                meses <= 9  ? Prioridade.P0 :
                meses <= 18 ? Prioridade.P2 :
                              Prioridade.P1;
        };
    }
}