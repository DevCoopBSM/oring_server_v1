package bsm.devcoop.oring.domain.vote.presentation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VoteResultResponse {
    private int participants;
    private double agreePercentage;
    private double disagreePercentage;
    private List<DisagreeVoteResponse> disagreeVotes;

    @Builder
    public VoteResultResponse(int participants, double agreePercentage, double disagreePercentage, List<DisagreeVoteResponse> disagreeVotes) {
        this.participants = participants;
        this.agreePercentage = agreePercentage;
        this.disagreePercentage = disagreePercentage;
        this.disagreeVotes = disagreeVotes;
    }
}
