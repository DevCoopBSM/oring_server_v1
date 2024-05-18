package bsm.devcoop.oring.domain.agenda.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ReadAgendaResponseDto {
    private int agendaNo;
    private String agendaContent;
}