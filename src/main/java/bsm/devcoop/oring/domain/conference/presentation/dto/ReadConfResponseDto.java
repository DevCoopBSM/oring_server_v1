package bsm.devcoop.oring.domain.conference.presentation.dto;

import bsm.devcoop.oring.domain.conference.Conference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ReadConfResponseDto {
    private Conference conference;
}
