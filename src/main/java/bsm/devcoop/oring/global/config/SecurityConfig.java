package bsm.devcoop.oring.global.config;

import bsm.devcoop.oring.global.config.filter.JwtAuthFilter;
import bsm.devcoop.oring.global.config.filter.LoginFilter;
import bsm.devcoop.oring.global.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
  private final JwtUtil jwtUtil;
  private final AuthenticationConfiguration authenticationConfiguration;
  private final ObjectMapper objectMapper;

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    AuthenticationManager authManager = configuration.getAuthenticationManager();
    log.info("AuthenticationManager created : {}", authManager);
    return authManager;
  }

  // Security Filter
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    AuthenticationManager authManager = authenticationManager(authenticationConfiguration);
    log.info("Setting up LoginFilter with AuthenticationManager : {}", authManager);

    // LoginFilter 는 로그인 요청 시에만 작동
    LoginFilter loginFilter = new LoginFilter(jwtUtil, authManager, objectMapper);
    loginFilter.setFilterProcessesUrl("/auth/login");

    http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)

            .authorizeHttpRequests(
                    auth -> auth
                            // ai
                            .requestMatchers("/ai/**").hasAnyRole("USER", "MEMBER", "COOP", "ADMIN")

                            // account
                            .requestMatchers("/auth/**").permitAll()

                            // occount
                            .requestMatchers("/item/**").permitAll()

                            .requestMatchers("/inventory/**").hasAnyRole("COOP", "ADMIN")

                            .requestMatchers("/transaction/log").authenticated()

                            // chat
                            .requestMatchers("/wsChat/**").permitAll() // ChannelFilter
                            .requestMatchers("/chat/**").authenticated()

                            // vote
                            .requestMatchers("/conference/**").hasRole("ADMIN")

                            .requestMatchers("/agenda/**").hasRole("ADMIN")

                            .requestMatchers("/vote/voting").hasAnyRole("USER", "MEMBER", "COOP", "ADMIN")
                            .requestMatchers("/vote/**").hasRole("ADMIN")

                            .anyRequest().authenticated()
            )
            .addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)

            .sessionManagement(
                    session -> session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS));

    log.info("Security filter chain configured");

    return http.build();
  }
}
