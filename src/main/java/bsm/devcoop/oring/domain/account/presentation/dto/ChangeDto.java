package bsm.devcoop.oring.domain.account.presentation.dto;

import lombok.Builder;
import lombok.Getter;

public class ChangeDto {
    @Getter
    public static class PasswordReq {
        private String newPassword;
    }

    @Builder
    @Getter
    public static class Response {
        private String message;
    }
}
