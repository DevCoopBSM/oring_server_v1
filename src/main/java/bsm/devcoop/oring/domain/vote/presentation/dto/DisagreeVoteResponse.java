package bsm.devcoop.oring.domain.vote.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DisagreeVoteResponse {
    private String studentName;
    private String reason;
    private String voteId;
}
