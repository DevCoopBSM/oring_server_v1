package bsm.devcoop.oring.domain.conference.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReadConfRequestDto {
    private LocalDate date;
}
