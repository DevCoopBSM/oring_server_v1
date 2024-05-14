package bsm.devcoop.oring.domain.vote.service;

import bsm.devcoop.oring.domain.vote.Student;
import bsm.devcoop.oring.domain.vote.Vote;
import bsm.devcoop.oring.domain.vote.presentation.dto.VotingRequestDto;
import bsm.devcoop.oring.domain.vote.repository.StudentRepository;
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
    private final StudentRepository studentRepository;

    // 투표하기 ( 현재 학번만 return )
    @Transactional
    public char vote(VotingRequestDto requestDto) {
        Student student = studentRepository.findByStuNumber(requestDto.getStuNumber());
        return student.getStuNumber();
    }
}
