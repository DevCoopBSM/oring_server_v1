package bsm.devcoop.oring.domain.vote.presentation;

import bsm.devcoop.oring.domain.vote.presentation.dto.MakeConfRequestDto;
import bsm.devcoop.oring.domain.vote.presentation.dto.VotingRequestDto;
import bsm.devcoop.oring.domain.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vote")
@RequiredArgsConstructor
@Slf4j
public class VoteController {
  private final VoteService voteService;

  // 투표하기 ( 현재 학번만 return )
  @PostMapping("/voting")
  public ResponseEntity<?> vote(@RequestBody VotingRequestDto dto) {
    return ResponseEntity.ok(voteService.vote(dto));
  }
}
