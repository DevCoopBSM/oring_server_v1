package bsm.devcoop.oring.domain.vote.voting.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class VotingResponseDto {
    private Boolean isSuccess;
}
