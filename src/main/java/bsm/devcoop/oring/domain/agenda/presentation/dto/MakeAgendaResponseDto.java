package bsm.devcoop.oring.domain.agenda.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class MakeAgendaResponseDto {
    private int agendaNo;
    private LocalDate conferenceDate;
    private String agendaContent;
}