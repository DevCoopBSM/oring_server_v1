package bsm.devcoop.oring.domain.vote.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MakeConfRequestDto {
    private int date;
    private String pdfLink;
}
