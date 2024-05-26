package bsm.devcoop.oring.domain.user.presentation;

import bsm.devcoop.oring.domain.user.presentation.dto.AuthRequestDto;
import bsm.devcoop.oring.domain.user.service.UserService;
import bsm.devcoop.oring.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(AuthRequestDto dto) throws GlobalException {
        return userService.authByCode(dto);
    }
}
