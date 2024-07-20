package bsm.devcoop.oring.domain.auth.presentation;

import bsm.devcoop.oring.domain.auth.presentation.dto.PwChangeReq;
import bsm.devcoop.oring.domain.auth.service.AuthService;
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

    @PatchMapping("/pwChange")
    public ResponseEntity<?> pwChange(@RequestBody PwChangeReq request) throws GlobalException {
        return authService.pwChange(request);
    }
}
