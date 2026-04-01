package br.com.gvi.portinspection.repository;

import br.com.gvi.portinspection.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UsuarioRepository — acesso ao banco para usuários.
 *
 * O método findByUsername é um Query Method — o Spring gera
 * automaticamente:
 *   SELECT * FROM usuarios WHERE username = ?
 *
 * Retorna Optional<Usuario> porque o usuário pode não existir
 * (login incorreto) — tratamos isso sem NullPointerException.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}