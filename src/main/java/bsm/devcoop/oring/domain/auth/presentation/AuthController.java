package bsm.devcoop.oring.domain.auth.presentation;

import bsm.devcoop.oring.domain.auth.User;
import bsm.devcoop.oring.domain.auth.presentation.dto.PwChangeRequest;
import bsm.devcoop.oring.domain.auth.presentation.dto.SignUpRequest;
import bsm.devcoop.oring.domain.auth.service.AuthService;
import bsm.devcoop.oring.domain.auth.types.Role;
import bsm.devcoop.oring.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signUp")
    public void signUp(@RequestBody SignUpRequest request) throws GlobalException {
        log.info("Sign Up Started");

        String userCode = request.getUserCode();
        String userName = request.getUserName();
        String email = request.getEmail();

        String password = request.getPassword();
        String pin = "1234";

        Role role = Role.valueOf(request.getUserType());

        // 이미 존재하는 유저일 시 예외 발생
        authService.existsUser(userCode);
        
        // 비밀번호 암호화
        String encodedPassword = authService.encodePassword(password);
        String encodedPin = authService.encodePassword(pin);
        
        // User DB에 새롭게 저장
        User user = User.builder()
                .userCode(userCode)
                .userName(userName)
                .email(email)
                .password(encodedPassword)
                .point(0)
                .pin(encodedPin)
                .roles(role)
                .build();

        User savedUser = authService.saveUser(user);
        log.info("Save user Success");
    }

    @PostMapping("/token")
    public ResponseEntity<?> voteToken(@RequestHeader("Authorization") String header) throws GlobalException {
        log.info("Create Vote Token Started");
        log.info("Get a Header : {}", header);

        // Header 정보 추출하여 User 객체 가져오기
        User user = authService.getUserFromHeader(header);

        String userName = user.getUserName();

        // 투표 인증용 토큰 발급
        String voteJwt = authService.createVoteJwt(userName);

        // Header 에 발급된 토큰 추가
        return ResponseEntity.ok().header("AuthorizationV", voteJwt).build(); // 해당 토큰 이름 고려할 필요
    }

    @PatchMapping("/pwChange")
    public ResponseEntity<?> pwChange(@RequestBody PwChangeRequest request) throws GlobalException {
        return authService.pwChange(request);
    }
}
