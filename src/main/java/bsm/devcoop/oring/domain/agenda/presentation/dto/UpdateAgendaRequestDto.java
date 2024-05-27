package bsm.devcoop.oring.domain.agenda.presentation.dto;

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
