package bsm.devcoop.oring.domain.auth.service;

import bsm.devcoop.oring.domain.auth.User;
import bsm.devcoop.oring.domain.auth.presentation.dto.PwChangeReq;
import bsm.devcoop.oring.domain.auth.presentation.dto.PwChangeRes;
import bsm.devcoop.oring.domain.auth.repository.UserRepository;
import bsm.devcoop.oring.global.exception.GlobalException;
import bsm.devcoop.oring.global.exception.enums.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public ResponseEntity<?> pwChange(PwChangeReq request) throws GlobalException {
        log.info("Password Change Started");

        String email = request.getEmail();
        String password = request.getPassword();
        String newPassword = request.getNewPassword();

        try {
            log.info("Find User by Email : {} and Match Password", email);
            User user = userRepository.findByEmail(email);

            // req 현재 비밀번호와 저장된 비밀번호가 다르다면 예외 처리
            if(!bCryptPasswordEncoder.matches(password, user.getPassword())) {
                throw new GlobalException(ErrorCode.Forbidden, "Incorrect Password");
            }

            // req 새로운 비밀번호와 저장된 비밀번호가 같다면 예외 처리
            if(bCryptPasswordEncoder.matches(newPassword, user.getPassword())) {
                throw new GlobalException(ErrorCode.Bad_Request, "Same Password");
            }

            log.info("Encoded newPassword : {}", newPassword);
            String encodedPassword = bCryptPasswordEncoder.encode(newPassword);

            log.info("Set new Password : {}", encodedPassword);
            user.changePassword(encodedPassword);

            userRepository.save(user);
        } catch (EntityNotFoundException e) {
            throw new GlobalException(ErrorCode.Not_Found, "Cannot found User");
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.Internal_Server_Error, "Error during change password");
        }

        PwChangeRes response = PwChangeRes.builder()
                .status(200)
                .message("Password Change Success")
                .build();

        return ResponseEntity.ok().body(response);
    }

    // 해야 할 일 : 비즈니스 로직과 데이터 다루는 작업 ( Controller / Service ) 을 분리할 것
    // 현재 너무나도 많은 로직이 Service 로 치우쳐져 있다.

//    @Transactional
//    public ResponseEntity<?> createAuthVoteToken()
}
