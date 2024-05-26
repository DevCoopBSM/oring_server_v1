package bsm.devcoop.oring.domain.user.service;

import bsm.devcoop.oring.domain.user.User;
import bsm.devcoop.oring.domain.user.presentation.dto.AuthRequestDto;
import bsm.devcoop.oring.domain.user.presentation.dto.AuthResponseDto;
import bsm.devcoop.oring.domain.user.repository.UserRepository;
import bsm.devcoop.oring.global.exception.GlobalException;
import bsm.devcoop.oring.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    // 학생 인증하기
    @Transactional
    public ResponseEntity<?> authByCode(AuthRequestDto requestDto) throws GlobalException {
        User user = userRepository.findByStuCode(requestDto.getStuCode());

        if (user == null) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        AuthResponseDto responseDto = AuthResponseDto.builder()
                .stuNumber(user.getStuNumber())
                .build();

        return ResponseEntity.ok(responseDto);
    }
}
