package bsm.devcoop.oring.domain.auth.presentation.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class SignUpRequest {
    private String userCode;
    private String userName;
    private String email;
    private String password;
    private String userType;
}
