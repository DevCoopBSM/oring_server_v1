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
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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
        char voteCode = requestDto.getVote();
        String reason = requestDto.getReason();

        try {
            Agenda agenda = agendaService.read(conferenceDate, agendaNo);
            System.out.println("Agenda fetched: " + agenda);
            System.out.println("Agenda isPossible value: " + agenda.getIsPossible());

            User user = userRepository.findByStuNumber(stuNumber);
            System.out.println("User fetched: " + user);

            if (voteCode == 0 && reason == null) {
                System.out.println("Reason is required when voting 'No'");
                return ResponseEntity.notFound().build();
            } else if (agenda.getIsPossible() == '0') {
                System.out.println("Agenda is not opened for voting");
                return ResponseEntity.status(401).body("FORBIDDEN: AGENDA NOT OPENED");
            }

            AgendaId agendaId = AgendaId.builder()
                    .conferenceId(conferenceDate)
                    .agendaNo(agendaNo)
                    .build();

            VoteId voteId = VoteId.builder()
                    .agendaId(agendaId)
                    .studentId(stuNumber)
                    .build();

            // Check for duplicate vote
            if (voteRepository.existsById(voteId)) {
                System.out.println("Duplicate vote detected for voteId: " + voteId);
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

            System.out.println("Vote saved successfully: " + vote);
            return ResponseEntity.ok(responseDto);

        } catch (DataIntegrityViolationException e) {
            System.out.println("Data integrity violation: " + e.getMessage());
            return ResponseEntity.status(409).body(ErrorCode.DUPLICATE_DATA);
        } catch (NullPointerException e) {
            System.out.println("Null pointer exception: " + e.getMessage());
            return ResponseEntity.status(404).body(ErrorCode.DATA_NOT_FOUND);
        } catch(Exception e) {
            System.out.println("Internal server error: " + e.getMessage());
            return ResponseEntity.status(500).body(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 투표 결과 계산
    @Transactional(readOnly = true)
    public VoteResultResponse calculateVoteResult(VoteResultRequest request) {
        Long agendaId = request.getAgendaId();
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
