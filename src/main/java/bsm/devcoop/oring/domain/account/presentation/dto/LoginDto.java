package bsm.devcoop.oring.domain.account.presentation.dto;

import bsm.devcoop.oring.domain.account.types.Role;
import lombok.Builder;
import lombok.Getter;

public class LoginDto {
    @Builder
    @Getter
    public static class Response {
        private String userEmail;
        private String userName;
        private String userCode;
        private int userPoint;
        private String roles;
    }
}
