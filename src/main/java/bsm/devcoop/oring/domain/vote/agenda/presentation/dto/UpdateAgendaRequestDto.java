package bsm.devcoop.oring.domain.vote.agenda.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UpdateAgendaRequestDto {
    private LocalDate conferenceDate;
    private int agendaNo;
    private String agendaContent;
}
