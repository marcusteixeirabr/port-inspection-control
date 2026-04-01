package br.com.gvi.portinspection.config;

import br.com.gvi.portinspection.domain.entity.Usuario;
import br.com.gvi.portinspection.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig — configuração central do Spring Security.
 *
 * CONCEITO: @Configuration + @EnableWebSecurity
 * Esta classe substitui o arquivo XML de segurança que existia
 * no Spring antigo. Aqui definimos em Java:
 *   - Quais rotas são públicas
 *   - Quais rotas exigem login
 *   - Como é o formulário de login
 *   - Como é o logout
 *   - Como as senhas são codificadas
 *
 * CONCEITO: @Bean
 * Métodos anotados com @Bean criam objetos gerenciados pelo Spring.
 * É como dizer: "Spring, quando alguém precisar de um
 * PasswordEncoder, use este objeto que estou criando aqui."
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * SecurityFilterChain — o coração da configuração.
     *
     * Define as regras de acesso para cada rota:
     *   permitAll()          → qualquer um acessa (sem login)
     *   authenticated()      → precisa estar logado
     *   hasRole("ADMIN")     → precisa ter papel específico
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // ── rotas PÚBLICAS ─────────────────────────────
                .requestMatchers("/login").permitAll()
                .requestMatchers("/css/**", "/js/**").permitAll() // arquivos estáticos
                .requestMatchers("/h2-console/**").permitAll()    // console H2
                .requestMatchers("/").permitAll()                  // página inicial
                .requestMatchers("/sobre").permitAll()
                .requestMatchers("/navios/**").permitAll()
                .requestMatchers("/inspecoes/**").permitAll()

                // ── rotas PROTEGIDAS ───────────────────────────
                // qualquer rota não listada acima exige login
                .anyRequest().authenticated()
            )

            // ── FORMULÁRIO DE LOGIN ───────────────────────────
            .formLogin(form -> form
                .loginPage("/login")           // nossa página customizada
                .loginProcessingUrl("/login")  // onde o form faz POST
                .defaultSuccessUrl("/inspetores", true) // após login OK
                .failureUrl("/login?error")    // após login falhar
                .permitAll()
            )

            // ── LOGOUT ────────────────────────────────────────
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout") // após logout
                .invalidateHttpSession(true)        // limpa a sessão
                .deleteCookies("JSESSIONID")        // limpa o cookie
                .permitAll()
            )

            // ── H2 CONSOLE ────────────────────────────────────
            // O H2 usa frames — precisamos liberar para o console funcionar
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            )

            // Desabilita CSRF só para o H2 console
            // (em produção NUNCA desabilite o CSRF)
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            );

        return http.build();
    }

    /**
     * PasswordEncoder — define como as senhas são codificadas.
     *
     * BCryptPasswordEncoder é o padrão da indústria:
     *   - Gera um hash diferente a cada vez (salt automático)
     *   - Impossível de reverter
     *   - Lento por design (dificulta força bruta)
     *
     * O Spring Security usa este bean automaticamente para
     * comparar senhas durante o login.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * CommandLineRunner — executa código ao iniciar a aplicação.
     *
     * CONCEITO: dados iniciais (seed)
     * Cria um usuário padrão "admin/admin123" se não existir.
     * Assim você sempre tem um usuário para entrar no sistema.
     *
     * Em produção, você removeria isso e teria um processo
     * seguro de criação do primeiro usuário.
     */
    @Bean
    public CommandLineRunner criarUsuarioPadrao(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            // só cria se ainda não existir
            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario(
                    "admin",
                    passwordEncoder.encode("admin123") // hash BCrypt
                );
                usuarioRepository.save(admin);
                System.out.println("✓ Usuário padrão criado: admin / admin123");
            }
        };
    }
}