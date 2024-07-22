package bsm.devcoop.oring.domain.auth.service;

import bsm.devcoop.oring.domain.auth.User;
import bsm.devcoop.oring.domain.auth.presentation.dto.PwChangeRequest;
import bsm.devcoop.oring.domain.auth.presentation.dto.PwChangeResponse;
import bsm.devcoop.oring.domain.auth.repository.UserRepository;
import bsm.devcoop.oring.global.exception.GlobalException;
import bsm.devcoop.oring.global.exception.enums.ErrorCode;
import bsm.devcoop.oring.global.utils.JwtUtil;
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
    private final JwtUtil jwtUtil;

    @Transactional
    public User saveUser(User user) {
        long userId = userRepository.count() + 1;

        user.createUserId(userId);

        return userRepository.save(user);
    }

    @Transactional
    public User existsUser(String userCode) throws GlobalException {
        try {
            return userRepository.existsByUserCode(userCode);
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.Conflict, "Already exists User");
        }
    }

    @Transactional
    public String encodePassword(String password) {
        log.info("Encode new Password : {}", password);
        return bCryptPasswordEncoder.encode(password);
    }

    @Transactional
    public User getUserFromHeader(String header) throws GlobalException {
        log.info("Get Email from Header");

        String token = header.substring(7); // "Bearer " 이후의 토큰 부분만 가져옵니다.
        log.info("Token : {}", token);

        // 토큰이 만료되었을 경우, 검사 없이 지나간다
        if (jwtUtil.isExpired(token)) {
            log.warn("Expired token");
            throw new GlobalException(ErrorCode.Bad_Request, "Expired Token");
        }

        // 토큰에서 email 추출
        String email = jwtUtil.getEmail(token);
        
        // email 로 유저 정보 찾기
        return userRepository.findByEmail(email);
    }

    @Transactional
    public String createVoteJwt(String userName) throws GlobalException {
        log.info("Create JWT by userName : {}", userName);

        return jwtUtil.createVoteAuthorizationJwt(userName);
    }

    @Transactional
    public ResponseEntity<?> pwChange(PwChangeRequest request) throws GlobalException {
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

        PwChangeResponse response = PwChangeResponse.builder()
                .status(200)
                .message("Password Change Success")
                .build();

        return ResponseEntity.ok().body(response);
    }
}
