package bsm.devcoop.oring.domain.vote.service;

import bsm.devcoop.oring.domain.agenda.Agenda;
import bsm.devcoop.oring.domain.agenda.AgendaId;
import bsm.devcoop.oring.domain.agenda.service.AgendaService;
import bsm.devcoop.oring.domain.user.User;
import bsm.devcoop.oring.domain.user.repository.UserRepository;
import bsm.devcoop.oring.domain.vote.Vote;
import bsm.devcoop.oring.domain.vote.VoteId;
import bsm.devcoop.oring.domain.vote.presentation.dto.VotingRequestDto;
import bsm.devcoop.oring.domain.vote.presentation.dto.VotingResponseDto;
import bsm.devcoop.oring.domain.vote.repository.VoteRepository;
import bsm.devcoop.oring.global.exception.GlobalException;
import bsm.devcoop.oring.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VoteService {
    private final AgendaService agendaService;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;

    // 투표하기
    @Transactional
    public ResponseEntity<?> voting(VotingRequestDto requestDto) throws GlobalException {
        LocalDate conferenceDate = requestDto.getConferenceDate();
        int agendaNo = requestDto.getAgendaNo();
        String stuNumber = requestDto.getStuNumber();
        short voteCode = requestDto.getVote();
        String reason = requestDto.getReason();

        Agenda agenda = agendaService.read(conferenceDate, agendaNo);
        User user = userRepository.findByStuNumber(stuNumber);

        if (agenda == null) {
            throw new GlobalException(ErrorCode.AGENDA_NOT_FOUND);
        } else if (user == null) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        } else if (voteCode == 0 && reason == null) {
            throw new GlobalException(ErrorCode.DATA_NOT_FOUND);
        }

        AgendaId agendaId = AgendaId.builder()
                .conferenceId(conferenceDate)
                .agendaNo(agendaNo)
                .build();

        VoteId voteId = VoteId.builder()
                .agendaId(agendaId)
                .studentId(stuNumber)
                .build();

        Vote vote = Vote.builder()
                .voteId(voteId)
                .vote(voteCode)
                .reason(reason)
                .agenda(agenda)
                .user(user)
                .build();

        try {
            voteRepository.save(vote);
        } catch (DataIntegrityViolationException e) {
            throw new GlobalException(ErrorCode.DUPLICATE_DATA); // 적절한 에러 코드를 사용하세요
        }
        VotingResponseDto responseDto = VotingResponseDto.builder()
                .isSuccess(true)
                .build();

        return ResponseEntity.ok(responseDto);
    }
}
