package bsm.devcoop.oring.domain.vote.conference.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ReadFileResponseDto {
    private String fileLink;
}
