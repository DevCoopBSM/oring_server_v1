package bsm.devcoop.oring.domain.vote.presentation;

import bsm.devcoop.oring.domain.vote.presentation.dto.VotingRequestDto;
import bsm.devcoop.oring.domain.vote.service.VoteService;
import bsm.devcoop.oring.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vote")
@RequiredArgsConstructor
@Slf4j
public class VoteController {
  private final VoteService voteService;

  @PostMapping("/voting")
  public ResponseEntity<?> voting(@RequestBody VotingRequestDto dto) throws GlobalException {
    return voteService.voting(dto);
  }
}
