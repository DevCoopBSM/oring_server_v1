package bsm.devcoop.oring.domain.auth.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PwChangeReq {
    private String email;
    private String password;
    private String newPassword;
}
