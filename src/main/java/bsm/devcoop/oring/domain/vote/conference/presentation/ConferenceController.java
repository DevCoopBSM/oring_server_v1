package bsm.devcoop.oring.domain.vote.conference.presentation;

import bsm.devcoop.oring.domain.vote.conference.presentation.dto.MakeConfRequestDto;
import bsm.devcoop.oring.domain.vote.conference.service.ConferenceService;
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
  @GetMapping("/readConf")
  public ResponseEntity<?> readConference(@RequestHeader String token, @RequestParam LocalDate date) {
    return conferenceService.readConf(token, date);
  }

  // 특정 날짜의 아젠다만 가져오는 엔드포인트 추가
  @GetMapping("/readAgenda")
  public ResponseEntity<?> readAgenda(@RequestParam LocalDate date) {
      return conferenceService.readAgenda(date);
  }

  // 회의 만들기
  @PostMapping("/createConf")
  public ResponseEntity<?> createConference(@RequestHeader String token, @RequestBody MakeConfRequestDto dto) throws GlobalException {
    return conferenceService.create(token, dto);
  }

  // file 읽기 ; 현재 사용 X
  @GetMapping("/readFile")
  public ResponseEntity<?> readFile() throws GlobalException {
    return conferenceService.readFile();
  }
}
