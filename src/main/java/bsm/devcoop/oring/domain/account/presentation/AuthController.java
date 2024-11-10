package bsm.devcoop.oring.domain.account.presentation;

import bsm.devcoop.oring.entity.account.user.User;
import bsm.devcoop.oring.domain.account.presentation.dto.ChangeDto;
import bsm.devcoop.oring.domain.account.presentation.dto.SignUpDto;
import bsm.devcoop.oring.domain.account.service.AuthService;
import bsm.devcoop.oring.entity.account.student.Student;
import bsm.devcoop.oring.entity.account.user.types.Role;
import bsm.devcoop.oring.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth", produces = "application/json; charset=UTF8")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

}
