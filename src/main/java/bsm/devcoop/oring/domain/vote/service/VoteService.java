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
        LocalDate conferenceDate = LocalDate.of(2024, 5, 29);
        int agendaNo = requestDto.getAgendaNo();
        String stuNumber = requestDto.getStuNumber();
        short voteCode = requestDto.getVote();
        String reason = requestDto.getReason();

        try {
            Agenda agenda = agendaService.read(conferenceDate, agendaNo);
            User user = userRepository.findByStuNumber(stuNumber);
            if (voteCode == 0 && reason == null) {
                throw new GlobalException(ErrorCode.DATA_NOT_FOUND);
            } else if (agenda.getIsPossible() == '0') {
                throw new GlobalException(ErrorCode.FORBIDDEN);
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

            voteRepository.save(vote);

            VotingResponseDto responseDto = VotingResponseDto.builder()
                    .isSuccess(true)
                    .build();

            return ResponseEntity.ok(responseDto);

        } catch (DataIntegrityViolationException e) {
            throw new GlobalException(ErrorCode.DUPLICATE_DATA);
        } catch (NullPointerException e) {
            throw new GlobalException(ErrorCode.DATA_NOT_FOUND);
        } catch(Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
