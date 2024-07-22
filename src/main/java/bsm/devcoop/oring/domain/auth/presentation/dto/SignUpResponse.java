package bsm.devcoop.oring.domain.auth.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SignUpResponse {
    private int status;
    private String stuName;
}
