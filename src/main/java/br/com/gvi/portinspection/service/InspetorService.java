package br.com.gvi.portinspection.service;

import br.com.gvi.portinspection.domain.entity.Inspetor;
import br.com.gvi.portinspection.repository.InspetorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * InspetorService — camada de serviço para inspetores.
 * Mesmo padrão do InspecaoService — já familiar para você!
 */
@Service
public class InspetorService {

    private final InspetorRepository repository;

    public InspetorService(InspetorRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Inspetor> listarTodos() {
        return repository.findAllByOrderByNomeAsc();
    }

    @Transactional(readOnly = true)
    public Optional<Inspetor> buscarPorId(Long id) {
        if (id == null) return Optional.empty();
        return repository.findById(id);
    }

    @Transactional
    public Inspetor salvar(String nome, String apelido) {
        return repository.save(new Inspetor(nome.trim(), apelido.trim()));
    }

    @Transactional
    public boolean atualizar(Long id, String nome, String apelido) {
        if (id == null) return false;
        Optional<Inspetor> opcional = repository.findById(id);
        if (opcional.isEmpty()) return false;

        Inspetor inspetor = opcional.get();
        inspetor.setNome(nome.trim());
        inspetor.setApelido(apelido.trim());
        repository.save(inspetor);
        return true;
    }

    @Transactional
    public boolean remover(Long id) {
        if (id == null) return false;
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}