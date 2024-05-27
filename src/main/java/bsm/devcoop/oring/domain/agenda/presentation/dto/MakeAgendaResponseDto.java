package bsm.devcoop.oring.domain.agenda.presentation.dto;

import bsm.devcoop.oring.domain.agenda.Agenda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class MakeAgendaResponseDto {
    private Agenda agenda;
}