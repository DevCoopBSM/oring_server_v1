package bsm.devcoop.oring.domain.agenda.presentation;

import bsm.devcoop.oring.domain.agenda.presentation.dto.MakeAgendaRequestDto;
import bsm.devcoop.oring.domain.agenda.presentation.dto.UpdateAgendaRequestDto;
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

    @GetMapping("/read")
    public ResponseEntity<?> readAllAgenda(@RequestParam LocalDate conferenceDate) throws GlobalException {
        return agendaService.readAll(conferenceDate);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAgenda(@RequestBody MakeAgendaRequestDto dto) throws GlobalException {
        return agendaService.create(dto);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateAgenda(@RequestBody UpdateAgendaRequestDto dto) throws GlobalException {
        return agendaService.update(dto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAgenda(@RequestParam LocalDate conferenceDate, int agendaNo) throws GlobalException {
        return agendaService.delete(conferenceDate, agendaNo);
    }
}