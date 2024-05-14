package bsm.devcoop.oring.domain.vote.service;

import bsm.devcoop.oring.domain.user.User;
import bsm.devcoop.oring.domain.vote.presentation.dto.VotingRequestDto;
import bsm.devcoop.oring.domain.user.presentation.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VoteService {
  private final UserRepository userRepository;

  // 투표하기 ( 현재 학번만 return )
  @Transactional
  public char vote(VotingRequestDto requestDto) {
    User user = userRepository.findByStuNumber(requestDto.getStuNumber());
    return user.getStuNumber();
  }
}
