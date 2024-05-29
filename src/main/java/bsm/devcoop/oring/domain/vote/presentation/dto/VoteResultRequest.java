package bsm.devcoop.oring.domain.vote.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class VoteResultRequest {
    private int agendaNo;
    private LocalDate conferenceId;

    // 추가된 메서드
    public int getAgendaNo() {
        return agendaNo;
    }

    public LocalDate getConferenceId() {
        return conferenceId;
    }
}
