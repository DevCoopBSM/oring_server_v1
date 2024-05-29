package bsm.devcoop.oring.domain.vote.presentation.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VoteResultResponse {
    private int participants;
    private double agreePercentage;
    private double disagreePercentage;
}
