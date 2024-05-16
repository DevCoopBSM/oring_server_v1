package bsm.devcoop.oring.domain.conference.presentation;

import bsm.devcoop.oring.domain.conference.presentation.dto.MakeConfRequestDto;
import bsm.devcoop.oring.domain.conference.service.ConferenceService;
import bsm.devcoop.oring.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/conference")
@RequiredArgsConstructor
@Slf4j
public class ConferenceController {
  private final ConferenceService conferenceService;

  // 회의 가져오기

  @GetMapping("/read")
  public ResponseEntity<?> readConference(@RequestParam LocalDate date) {
    return conferenceService.read(date);
  }

  // 회의 만들기 ( 현재 학번만 return )
  @PostMapping("/create")
  public ResponseEntity<?> createConference(@RequestBody MakeConfRequestDto dto) throws GlobalException {
    return conferenceService.create(dto);
  }
}
