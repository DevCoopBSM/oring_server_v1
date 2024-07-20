package bsm.devcoop.oring.domain.vote.voting.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VotingRequestDto {
    private LocalDate conferenceDate;
    private int agendaNo;

    private String voteAuthToken; // 투표 시 본인 인증용 토큰

    private char vote; // 투표 결과 Y or N
    private String reason; // 반대 사유
}
