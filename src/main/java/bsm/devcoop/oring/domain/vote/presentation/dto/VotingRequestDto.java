package bsm.devcoop.oring.domain.vote.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VotingRequestDto {
//    @NotNull
//    private int date;
//    private int agendaNo;
    private String stuNumber;
//    private char vote;
//    private String reason;
}
