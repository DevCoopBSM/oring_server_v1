package bsm.devcoop.oring.domain.conference.presentation.dto;

import bsm.devcoop.oring.domain.conference.Conference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class MakeConfResponseDto {
    private Conference conference;
}
