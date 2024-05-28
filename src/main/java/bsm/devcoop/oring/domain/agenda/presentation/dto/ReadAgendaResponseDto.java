package bsm.devcoop.oring.domain.agenda.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ReadAgendaResponseDto {
    private int agendaNo;
    private LocalDate conferenceDate;
    private String agendaContent;
}
