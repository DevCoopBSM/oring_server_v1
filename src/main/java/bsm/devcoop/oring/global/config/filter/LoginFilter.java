package bsm.devcoop.oring.global.config.filter;

import bsm.devcoop.oring.domain.account.CustomUserDetails;
import bsm.devcoop.oring.domain.account.presentation.dto.LoginDto;
import bsm.devcoop.oring.domain.account.types.Role;
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

        Map<String, String> loginRequest;

        try {
            loginRequest = objectMapper.readValue(request.getInputStream(), Map.class);
        } catch (Exception e) {
            log.error("Error parsing LoginRequest : {}", e.getMessage());
            throw new RuntimeException("Invalid LoginRequest Data", e);
        }

        String userEmail = loginRequest.get("userEmail");
        String userPassword = loginRequest.get("userPassword");

        if (userEmail == null || userPassword == null) {
            log.error("UserEmail or UserPassword is missing in LoginRequest");
            throw new RuntimeException("UserEmail or UserPassword is missing");
        }

        log.info("Received UserEmail : {}", userEmail);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userEmail, userPassword);

        log.info("Created Token : {}", authToken);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        log.info("LoginFilter successfulAuthentication");

        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();

        // 초기 비밀번호라면? 인증 로직으로 리다이렉트
        if (customUserDetails.isInitialUserPassword()) {
            log.warn("User's Password is initialPassword!");
            /*

            LoginDto.RedirectResponse responseBody = LoginDto.RedirectResponse.builder()
                    .success(false)
                    .status("REDIRECT")
                    .redirectUrl("/pwchange")
                    .build();

            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(responseBody));

            log.info("Sending redirect info to client");

             */
            return;
        }

        // 반환할 사용자 정보 추출 후 정리
        String userEmail = customUserDetails.getUserEmail();
        String userName = customUserDetails.getUsername();
        String userCode = customUserDetails.getUserCode();
        int userPoint = customUserDetails.getUserPoint();
        Role roles = customUserDetails.getRole();

        log.info("Generating token for user : {} with role : {}", userEmail, roles);

        // JWT 토큰 생성
        String token = jwtUtil.createAuthorizationJwt(userEmail, roles.name());
        log.info("Generated Token : {}", token);

        // 응답 헤더에 WJT 토큰 추가
        response.addHeader("Authorization", "Bearer " + token);
        log.info("Added token to response header");

        // 응답 본문에 사용자 정보와 토큰 추가
        LoginDto.Response responseBody = LoginDto.Response.builder()
                .success(true)
                .userCode(userCode)
                .userName(userName)
                .userEmail(userEmail)
                .userPoint(userPoint)
                .roles(roles.name())
                .build();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));

        log.info("Added responseDto to body");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        log.info("LoginFilter unsuccessfulAuthentication : {}", failed.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        LoginDto.UnsuccessfulResponse responseBody = LoginDto.UnsuccessfulResponse.builder()
                .success(false)
                .error("인증 실패")
                .message("아이디와 비밀번호를 확인해주세요.")
                .build();

        log.info("Return UnsuccessfulResponse to body");

        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}
