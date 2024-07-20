package bsm.devcoop.oring.global.config.filter;

import bsm.devcoop.oring.domain.user.CustomUserDetails;
import bsm.devcoop.oring.global.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        log.info("Initializing LoginFilter with URL pattern /auth/login");
        setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("LoginFilter attemptAuthentication started");

        // 요청 ( request ) 으로부터 사용자 정보 추출 후

        Map<String, String> loginRequest;

        try {
            loginRequest = objectMapper.readValue(request.getInputStream(), Map.class);
        } catch (Exception e) {
            log.error("Error reading value from loginRequest");
            throw new RuntimeException(e);
        }

        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        log.info("Received Email : {}", email);
        log.info("Received Password : {}", password);

        // 사용자 인증을 위한 토큰 생성 후 Manager 에게 인증 요청

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);

        log.info("Created Token : {}", authToken);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        log.info("LoginFilter successfulAuthentication");

        /*
        사용자 인증에 성공 ( 로그인 성공 )
        -> 사용자 정보를 추출하여 토큰 발급 후 로그인 처리
        */

        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();

        // 사용자 정보 ( email, role ) 추출
        String email = customUserDetails.getUsername();
        String role = customUserDetails.getAuthorities().iterator().next().getAuthority();

        log.info("Generating token for user : {} with role : {}", email, role);

        // 토큰 생성 후 헤더에 추가
        String token = customUserDetails.getUsername();
        log.info("Generated Token : {}", token);

        response.addHeader("Authorization", "bearer " + token);
        log.info("Added token to response header");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("LoginFilter unsuccessfulAuthentication");

        /*
        사용자 인증에 실패 ( 로그인 실패 )
        -> 인증 실패 status 처리
        */

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
