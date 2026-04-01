package br.com.gvi.portinspection.repository;

import br.com.gvi.portinspection.domain.entity.Inspetor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * InspetorRepository — acesso ao banco para inspetores.
 * Mesmo padrão do InspecaoRepository que você já conhece.
 */
@Repository
public interface InspetorRepository extends JpaRepository<Inspetor, Long> {
    List<Inspetor> findAllByOrderByNomeAsc();
}