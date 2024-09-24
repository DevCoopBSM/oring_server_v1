package bsm.devcoop.oring.domain.account.presentation;

import bsm.devcoop.oring.domain.account.User;
import bsm.devcoop.oring.domain.account.presentation.dto.ChangeDto;
import bsm.devcoop.oring.domain.account.presentation.dto.SignUpDto;
import bsm.devcoop.oring.domain.account.service.AuthService;
import bsm.devcoop.oring.domain.account.student.Student;
import bsm.devcoop.oring.domain.account.types.Role;
import bsm.devcoop.oring.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final String defaultPin = "1234";

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto.Request request) {
        log.info("Sign Up Started");

        String userName = request.getUserName();
        String userEmail = request.getUserEmail();
        String userPassword = request.getUserPassword();
        Role role = Role.valueOf(request.getUserType());

        // 만약 유저 이메일이 이미 존재한다면 409 반환
        log.info("Check User Duplication");
        if (authService.isUserEmailExist(userEmail)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("UserEmail Already Exist");
        }

        try {
            // 아니라면 학생 DB 에서 학생 정보 찾아오기
            Student student = authService.getStudentByEmail(userEmail);

            // 비밀번호 암호화
            log.info("Password/Pin before encoding : {}", userPassword);
            String encodedPassword = authService.encodePassword(userPassword);
            log.info("Password/Pin after encoding : {}", encodedPassword);

            // userNumber 설정
            String userNumber = userEmail.substring(0, 4) + "-학-" + userEmail.substring(5, 7);
            log.info("Create UserNumber : {}", userNumber);

            // User DB에 새롭게 저장
            User user = User.builder()
                    .userNumber(userNumber)
                    .userCode(student.getStuCode())
                    .userName(userName)
                    .userEmail(userEmail)
                    .userPassword(encodedPassword)
                    .userPin(defaultPin)
                    .userPoint(0)
                    .roles(role)
                    .build();

            User savedUser = authService.saveUser(user);
            log.info("Save user Success");

            SignUpDto.Response response = SignUpDto.Response.builder()
                    .message("회원가입이 성공적으로 완료되었습니다.")
                    .build();

            return ResponseEntity.ok().body(response);
        } catch (GlobalException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("학생 정보를 찾을 수 없습니다.");
        }
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

    @PatchMapping("/pwChange/{jwtToken}")
    public ResponseEntity<?> pwChange(@PathVariable String jwtToken, @RequestBody ChangeDto.PasswordReq request) {
        log.info("Change Password Started");

        try {
            String userEmail = authService.getUserEmailByJwt(jwtToken);
            String newPassword = request.getNewPassword();

            log.info("Find User by Email : {} and Match Password", userEmail);
            User user = authService.getUserByEmail(userEmail);

            // 만약 새 비밀번호와 기존 비밀번호가 동일하다면 Bad Request
            if(authService.matchPasswords(newPassword, user.getUserPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("기존과 동일한 비밀번호로는 설정할 수 없습니다.");
            }

            log.info("Encode newPassword : {}", newPassword);
            String encodedPassword = authService.encodePassword(newPassword);

            log.info("Set new Password : {}", encodedPassword);
            user.changePassword(encodedPassword);

            authService.saveUser(user);
            log.info("Save changed Password Success");

            ChangeDto.Response response = ChangeDto.Response.builder()
                    .message("비밀번호 변경이 성공적으로 완료되었습니다.")
                    .build();

            return ResponseEntity.ok().body(response);
        } catch (GlobalException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
