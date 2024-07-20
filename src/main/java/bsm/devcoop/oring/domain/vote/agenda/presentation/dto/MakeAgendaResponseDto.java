package bsm.devcoop.oring.domain.vote.agenda.presentation.dto;

import bsm.devcoop.oring.domain.vote.agenda.Agenda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class MakeAgendaResponseDto {
    private Agenda agenda;
}