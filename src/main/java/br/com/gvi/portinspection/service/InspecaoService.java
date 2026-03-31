package br.com.gvi.portinspection.service;

import br.com.gvi.portinspection.domain.entity.Inspecao;
import br.com.gvi.portinspection.repository.InspecaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * InspecaoService — camada de serviço para inspeções.
 *
 * Mesmo padrão do RiscoService que você já conhece:
 * o Controller não fala com o banco diretamente —
 * ele sempre passa pelo Service.
 *
 * CONCEITO NOVO: @Transactional
 * Uma transação garante que operações no banco sejam
 * atômicas — ou tudo funciona, ou nada é salvo.
 *
 * Exemplo: se você salvar e der erro no meio,
 * o banco volta ao estado anterior (rollback automático).
 *
 * Boas práticas:
 *   @Transactional(readOnly = true) → operações de leitura
 *   @Transactional                  → operações de escrita
 */
@Service
public class InspecaoService {

    // Injeção via construtor — mesmo padrão do NavioController
    private final InspecaoRepository repository;

    public InspecaoService(InspecaoRepository repository) {
        this.repository = repository;
    }

    /**
     * Retorna todas as inspeções ordenadas por data (mais recente primeiro).
     * readOnly = true: otimização para consultas — sem overhead de transação.
     */
    @Transactional(readOnly = true)
    public List<Inspecao> listarTodas() {
        return repository.findAllByOrderByDataInspecaoDesc();
    }

    /**
     * Busca uma inspeção pelo ID.
     * Optional<> evita NullPointerException — você trata o caso
     * "não encontrado" de forma explícita.
     */
    @Transactional(readOnly = true)
    public Optional<Inspecao> buscarPorId(Long id) {
        if (id == null) return Optional.empty(); // linha 56 — guard clause
        return repository.findById(id);
    }

    /**
     * Salva uma nova inspeção no banco.
     * repository.save() faz INSERT quando id é null.
     */
    @Transactional
    public Inspecao salvar(String nomeNavio, LocalDate dataInspecao) {
        Inspecao inspecao = new Inspecao(nomeNavio, dataInspecao);
        return repository.save(inspecao);
    }

    /**
     * Atualiza uma inspeção existente.
     * repository.save() faz UPDATE quando id já existe.
     * Retorna false se o id não for encontrado.
     */
    @Transactional
    public boolean atualizar(Long id, String nomeNavio, LocalDate dataInspecao) {
        if (id == null) return false;            // guard clause antes do findById
        Optional<Inspecao> opcional = repository.findById(id);
        if (opcional.isEmpty()) return false;

        Inspecao inspecao = opcional.get();
        inspecao.setNomeNavio(nomeNavio);
        inspecao.setDataInspecao(dataInspecao);
        repository.save(inspecao);
        return true;
    }

    /**
     * Remove uma inspeção pelo ID.
     * Retorna false se o id não existir.
     */
    @Transactional
    public boolean remover(Long id) {
        if (id == null) return false;            // guard clause
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}