package bsm.devcoop.oring.domain.conference.presentation;

import bsm.devcoop.oring.domain.conference.presentation.dto.MakeConfRequestDto;
import bsm.devcoop.oring.domain.conference.presentation.dto.ReadConfRequestDto;
import bsm.devcoop.oring.domain.conference.service.ConferenceService;
import bsm.devcoop.oring.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conference")
@RequiredArgsConstructor
@Slf4j
public class ConferenceController {
  private final ConferenceService conferenceService;

  // 회의 가져오기

  /**
   * 회의 가져오는 API GET으로 만드는 것이 바람직함
   * TODO : fix PostMapping -> GetMapping
   * @param dto
   * @return
   */

  @PostMapping("/read")
  public ResponseEntity<?> readConference(@RequestBody ReadConfRequestDto dto) {
    return conferenceService.read(dto);
  }

  // 회의 만들기 ( 현재 학번만 return )
  @PostMapping("/create")
  public ResponseEntity<?> createConference(@RequestBody MakeConfRequestDto dto) throws GlobalException {
    return conferenceService.create(dto);
  }
}
