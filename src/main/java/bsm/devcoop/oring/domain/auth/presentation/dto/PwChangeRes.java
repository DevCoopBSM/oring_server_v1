package bsm.devcoop.oring.domain.auth.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PwChangeRes {
    private int status;
    private String message;
}
