package bsm.devcoop.oring.domain.vote.service;

import bsm.devcoop.oring.domain.agenda.Agenda;
import bsm.devcoop.oring.domain.agenda.AgendaId;
import bsm.devcoop.oring.domain.agenda.service.AgendaService;
import bsm.devcoop.oring.domain.user.User;
import bsm.devcoop.oring.domain.user.repository.UserRepository;
import bsm.devcoop.oring.domain.vote.Vote;
import bsm.devcoop.oring.domain.vote.VoteId;
import bsm.devcoop.oring.domain.vote.presentation.dto.VoteResultRequest;
import bsm.devcoop.oring.domain.vote.presentation.dto.VoteResultResponse;
import bsm.devcoop.oring.domain.vote.presentation.dto.VotingRequestDto;
import bsm.devcoop.oring.domain.vote.presentation.dto.VotingResponseDto;
import bsm.devcoop.oring.domain.vote.repository.VoteRepository;
import bsm.devcoop.oring.global.exception.GlobalException;
import bsm.devcoop.oring.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
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
        char voteCode = requestDto.getVote();
        String reason = requestDto.getReason();

        try {
            Agenda agenda = agendaService.read(conferenceDate, agendaNo);
            User user = userRepository.findByStuNumber(stuNumber);

            if (voteCode == 'N' && reason == null) {
                return ResponseEntity.status(400).body("Reason is required when voting 'No'");
            } else if (agenda.getIsPossible() == '0') {
                return ResponseEntity.status(401).body("FORBIDDEN: AGENDA NOT OPENED");
            }

            VoteId voteId = VoteId.builder()
                    .agendaId(agenda.getId())
                    .studentId(stuNumber)
                    .build();

            // Check for duplicate vote
            if (voteRepository.existsById(voteId)) {
                return ResponseEntity.status(409).body(ErrorCode.DUPLICATE_DATA);
            }

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
            return ResponseEntity.status(409).body(ErrorCode.DUPLICATE_DATA);
        } catch (NullPointerException e) {
            return ResponseEntity.status(404).body(ErrorCode.DATA_NOT_FOUND);
        } catch(Exception e) {
            return ResponseEntity.status(500).body(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 투표 결과 계산
    @Transactional(readOnly = true)
    public VoteResultResponse calculateVoteResult(VoteResultRequest request) throws GlobalException {
        AgendaId agendaId = new AgendaId(request.getAgendaNo(), request.getConferenceId());
        Agenda agenda = agendaService.readById(agendaId);

        List<Vote> votes = voteRepository.findByAgenda(agenda);
        int totalVotes = votes.size();
        int agreeVotes = (int) votes.stream().filter(vote -> vote.getVote() == 'Y').count();
        int disagreeVotes = totalVotes - agreeVotes;

        double agreePercentage = (totalVotes > 0) ? (agreeVotes * 100.0 / totalVotes) : 0;
        double disagreePercentage = (totalVotes > 0) ? (disagreeVotes * 100.0 / totalVotes) : 0;

        return VoteResultResponse.builder()
                .participants(totalVotes)
                .agreePercentage(agreePercentage)
                .disagreePercentage(disagreePercentage)
                .build();
    }
}
