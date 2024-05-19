package bsm.devcoop.oring.domain.conference.presentation.dto;

import bsm.devcoop.oring.domain.agenda.Agenda;
import bsm.devcoop.oring.domain.agenda.presentation.dto.ReadAgendaResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class ReadConfResponseDto {
    private LocalDate date;
    private String fileLink;
}
