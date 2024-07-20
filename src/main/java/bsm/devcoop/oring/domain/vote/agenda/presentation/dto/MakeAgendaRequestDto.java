package bsm.devcoop.oring.domain.vote.agenda.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MakeAgendaRequestDto {
    private int agendaNo;
    private LocalDate conferenceDate;
    private String agendaContent;
}