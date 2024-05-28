package bsm.devcoop.oring.domain.agenda.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UpdateIsPossibleRequestDto {
    private LocalDate conferenceDate;
    private int agendaNo;
    private char isPossible;
}
