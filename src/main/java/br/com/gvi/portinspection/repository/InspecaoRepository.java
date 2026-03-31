package br.com.gvi.portinspection.repository;

import br.com.gvi.portinspection.domain.entity.Inspecao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * InspecaoRepository — camada de acesso ao banco.
 *
 * CONCEITO NOVO: Repository
 * Esta é provavelmente a parte mais "mágica" do Spring Data JPA.
 * Você declara uma INTERFACE — sem escrever nenhuma implementação —
 * e o Spring gera automaticamente todos os métodos de banco.
 *
 * Só de estender JpaRepository<Inspecao, Long> você ganha de graça:
 *
 *   save(inspecao)          → INSERT ou UPDATE
 *   findById(id)            → SELECT WHERE id = ?
 *   findAll()               → SELECT * FROM inspecoes
 *   deleteById(id)          → DELETE WHERE id = ?
 *   count()                 → SELECT COUNT(*)
 *   existsById(id)          → SELECT EXISTS(...)
 *
 * O <Inspecao, Long> significa:
 *   Inspecao → qual entidade este repository gerencia
 *   Long     → tipo da chave primária (@Id)
 *
 * @Repository: opcional quando se estende JpaRepository,
 * mas boa prática para deixar explícito o papel da classe.
 */
@Repository
public interface InspecaoRepository extends JpaRepository<Inspecao, Long> {

    /**
     * Busca todas as inspeções ordenadas por data (mais recente primeiro).
     *
     * CONCEITO: Query Methods
     * O Spring Data lê o nome do método e gera o SQL automaticamente!
     * "findAllByOrderByDataInspecaoDesc" vira:
     *   SELECT * FROM inspecoes ORDER BY data_inspecao DESC
     *
     * Você não escreve SQL — só nomeia o método seguindo a convenção.
     * Parece mágica, mas é só parsing inteligente do nome.
     */
    List<Inspecao> findAllByOrderByDataInspecaoDesc();
}