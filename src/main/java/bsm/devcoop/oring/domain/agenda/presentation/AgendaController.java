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
    public ResponseEntity<?> createAgenda(@RequestBody MakeAgendaRequestDto dto) throws GlobalException {
        return agendaService.create(dto);
    }

    @GetMapping("/read")
    public ResponseEntity<?> readAgenda(@RequestParam LocalDate date, int agendaNo) throws GlobalException {
        return ResponseEntity.ok(agendaService.read(date, agendaNo));
    }

    @PostMapping("/updateAgenda")
    public ResponseEntity<?> updateAgenda(@RequestBody UpdateAgendaRequestDto dto) throws GlobalException {
        return agendaService.update(dto);
    }

    @PostMapping("/updateIsPossible")
    public ResponseEntity<?> updateIsPossible(@RequestBody UpdateIsPossibleRequestDto dto) throws GlobalException {
        return agendaService.updateIsPossible(dto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAgenda(@RequestParam LocalDate conferenceDate, int agendaNo) throws GlobalException {
        return agendaService.delete(conferenceDate, agendaNo);
    }
}