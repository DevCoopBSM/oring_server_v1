package bsm.devcoop.oring.domain.vote.voting.service;

import bsm.devcoop.oring.domain.vote.agenda.Agenda;
import bsm.devcoop.oring.domain.vote.agenda.AgendaId;
import bsm.devcoop.oring.domain.vote.agenda.service.AgendaService;
import bsm.devcoop.oring.domain.account.User;
import bsm.devcoop.oring.domain.account.repository.UserRepository;
import bsm.devcoop.oring.domain.vote.voting.Vote;
import bsm.devcoop.oring.domain.vote.voting.VoteId;
import bsm.devcoop.oring.domain.vote.voting.repository.VoteRepository;
import bsm.devcoop.oring.domain.vote.voting.presentation.dto.VoteResultRequest;
import bsm.devcoop.oring.domain.vote.voting.presentation.dto.VoteResultResponse;
import bsm.devcoop.oring.domain.vote.voting.presentation.dto.VotingRequestDto;
import bsm.devcoop.oring.domain.vote.voting.presentation.dto.VotingResponseDto;
import bsm.devcoop.oring.domain.vote.voting.presentation.dto.DisagreeVoteResponse;
import bsm.devcoop.oring.global.exception.GlobalException;
import bsm.devcoop.oring.global.exception.enums.ErrorCode;
import bsm.devcoop.oring.global.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VoteService {
    private final AgendaService agendaService;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;

    private final JwtUtil jwtUtil;

    // 투표하기
    @Transactional
    public ResponseEntity<?> voting(VotingRequestDto requestDto) throws GlobalException {
        log.info("Voting Started");

        LocalDate conferenceDate = requestDto.getConferenceDate();
        int agendaNo = requestDto.getAgendaNo();

        try {
            // 회의 안건 찾기
            Agenda agenda = agendaService.read(conferenceDate, agendaNo);

            // 유저 정보 인증
            String voteAuthToken = requestDto.getVoteAuthToken();
            String userCode = jwtUtil.getUserCode(voteAuthToken);

            log.info("Received userCode : {}", userCode);
            User user = userRepository.findByUserCode(userCode);


            // 투표 정보 추출
            char vote = requestDto.getVote();
            String reason = requestDto.getReason();

            
            // '반대'인데 '사유'가 없을 경우, 에러 반환
            if (vote == 'N' && reason == null) {
                return ResponseEntity.status(400).body("Reason is required when voting 'No'");
            }

            // 투표가 가능하지 않을 경우, 에러 반환
            if (agenda.getIsPossible() == '0') {
                return ResponseEntity.status(401).body("FORBIDDEN: AGENDA NOT OPENED");
            }

            // 투표 정보 저장하기
            VoteId voteId = VoteId.builder()
                    .agendaId(agenda.getId())
                    .userId(userCode)
                    .build();

            // 저장 전, 투표 정보 중복 여부 확인 
            if (voteRepository.existsById(voteId)) {
                return ResponseEntity.status(409).body(ErrorCode.Conflict);
            }

            Vote voteData = Vote.builder()
                    .voteId(voteId)
                    .vote(vote)
                    .reason(reason)
                    .agenda(agenda)
                    .user(user)
                    .build();

            voteRepository.save(voteData);

            VotingResponseDto responseDto = VotingResponseDto.builder()
                    .isSuccess(true)
                    .build();

            return ResponseEntity.ok(responseDto);

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).body(ErrorCode.Conflict);
        } catch (NullPointerException e) {
            return ResponseEntity.status(404).body(ErrorCode.Not_Found);
        } catch(Exception e) {
            return ResponseEntity.status(500).body(ErrorCode.Internal_Server_Error);
        }
    }

    // 투표 결과 취합하기
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

        List<DisagreeVoteResponse> disagreeVotesList = votes.stream()
                .filter(vote -> vote.getVote() == 'N')
                .map(vote -> new DisagreeVoteResponse(vote.getUser().getUserName(), vote.getReason(), vote.getVoteId().toString()))
                .collect(Collectors.toList());

        return VoteResultResponse.builder()
                .participants(totalVotes)
                .agreePercentage(agreePercentage)
                .disagreePercentage(disagreePercentage)
                .disagreeVotes(disagreeVotesList)
                .build();
    }
}
