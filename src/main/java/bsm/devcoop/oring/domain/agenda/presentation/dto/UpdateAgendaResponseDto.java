package bsm.devcoop.oring.domain.agenda.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class UpdateAgendaResponseDto {
    private int agendaNo;
    private String agendaContent;
}
