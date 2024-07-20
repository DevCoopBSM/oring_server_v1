package bsm.devcoop.oring.domain.vote.conference.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MakeConfRequestDto {
    private LocalDate date;
    private String fileLink;
}
