package bsm.devcoop.oring.domain.vote.presentation.dto;

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
    private String stuNumber;
    private short vote;
    private String reason;
}
