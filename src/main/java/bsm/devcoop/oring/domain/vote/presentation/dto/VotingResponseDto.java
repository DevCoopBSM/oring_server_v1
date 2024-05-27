package bsm.devcoop.oring.domain.vote.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class VotingResponseDto {
    private Boolean isSuccess;
}
