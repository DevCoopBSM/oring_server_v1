package bsm.devcoop.oring.domain.vote.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VoteResultRequest {
    private Long agendaId; // 또는 필요한 경우 Integer로 변경
}
