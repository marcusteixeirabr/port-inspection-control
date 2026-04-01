package br.com.gvi.portinspection.service;

import br.com.gvi.portinspection.domain.entity.Usuario;
import br.com.gvi.portinspection.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserDetailsServiceImpl — ponte entre o Spring Security e o banco.
 *
 * CONCEITO: UserDetailsService
 * Esta é a interface que o Spring Security usa para buscar
 * o usuário durante o login. Você implementa UM método:
 * loadUserByUsername() — e o Spring cuida do resto.
 *
 * O fluxo de login funciona assim:
 *
 *   1. Usuário digita username + senha no formulário
 *   2. Spring Security chama loadUserByUsername(username)
 *   3. Nós buscamos no banco via UsuarioRepository
 *   4. Devolvemos um UserDetails com username, senha (hash) e role
 *   5. Spring compara a senha digitada com o hash via BCrypt
 *   6. Se bater → sessão criada → usuário logado
 *   7. Se não bater → redireciona para /login?error
 *
 * Repare: NÓS não comparamos senhas. O Spring faz isso.
 * Nós só buscamos o usuário e devolvemos — separação clara.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository repository;

    public UserDetailsServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // busca no banco — se não encontrar lança exceção
        // o Spring Security captura e redireciona para /login?error
        Usuario usuario = repository.findByUsername(username)
            .orElseThrow(() ->
                new UsernameNotFoundException("Usuário não encontrado: " + username)
            );

        // SimpleGrantedAuthority: converte a string "ROLE_USER"
        // no formato que o Spring Security entende
        return new User(
            usuario.getUsername(),
            usuario.getPassword(),
            List.of(new SimpleGrantedAuthority(usuario.getRole()))
        );
    }
}