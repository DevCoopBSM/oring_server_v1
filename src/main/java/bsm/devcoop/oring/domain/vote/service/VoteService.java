package bsm.devcoop.oring.domain.vote.service;

import bsm.devcoop.oring.domain.vote.Vote;
import bsm.devcoop.oring.domain.vote.presentation.dto.VotingRequestDto;
import bsm.devcoop.oring.domain.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VoteService {
    private final VoteRepository voteRepository;

    // 투표하기 ( 현재 학번만 return )
    @Transactional
    public String vote(VotingRequestDto requestDto) {

        Vote vote = voteRepository.findByStuNumber(requestDto.getStuNumber());
        System.out.println("vote = " + vote);
        return vote.getStuNumber();
    }
}
