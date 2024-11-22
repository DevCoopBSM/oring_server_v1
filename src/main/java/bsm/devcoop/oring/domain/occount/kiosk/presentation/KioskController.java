package bsm.devcoop.oring.domain.occount.kiosk.presentation;//package bssm.devcoop.occount.domain.kiosk.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kiosk", produces = "application/json; charset=UTF8")
@RequiredArgsConstructor
@Slf4j
public class KioskController {
}
