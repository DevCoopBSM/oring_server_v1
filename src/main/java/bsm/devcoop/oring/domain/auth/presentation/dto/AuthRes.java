package bsm.devcoop.oring.domain.auth.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class AuthRes {
    private String stuNumber;
    private String stuName;
}
