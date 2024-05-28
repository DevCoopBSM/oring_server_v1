package bsm.devcoop.oring.domain.agenda.presentation;

import bsm.devcoop.oring.domain.agenda.presentation.dto.MakeAgendaRequestDto;
import bsm.devcoop.oring.domain.agenda.presentation.dto.UpdateAgendaRequestDto;
import bsm.devcoop.oring.domain.agenda.presentation.dto.UpdateIsPossibleRequestDto;
import bsm.devcoop.oring.domain.agenda.service.AgendaService;
import bsm.devcoop.oring.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/agenda")
@RequiredArgsConstructor
@Slf4j
public class AgendaController {
    private final AgendaService agendaService;

    @PostMapping("/create")
    public ResponseEntity<?> createAgenda(@RequestHeader String token, @RequestBody MakeAgendaRequestDto dto) throws GlobalException {
        return agendaService.create(token, dto);
    }

    @GetMapping("/read")
    public ResponseEntity<?> readAgenda(@RequestParam LocalDate date, int agendaNo) throws GlobalException {
        return ResponseEntity.ok(agendaService.read(date, agendaNo));
    }

    @PostMapping("/updateAgenda")
    public ResponseEntity<?> updateAgenda(@RequestHeader String token, @RequestBody UpdateAgendaRequestDto dto) throws GlobalException {
        return agendaService.update(token, dto);
    }

    @PostMapping("/updateIsPossible")
    public ResponseEntity<?> updateIsPossible(@RequestHeader String token, @RequestBody UpdateIsPossibleRequestDto dto) throws GlobalException {
        return agendaService.updateIsPossible(token, dto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAgenda(@RequestHeader String token, @RequestParam LocalDate conferenceDate, int agendaNo) throws GlobalException {
        return agendaService.delete(token, conferenceDate, agendaNo);
    }
}