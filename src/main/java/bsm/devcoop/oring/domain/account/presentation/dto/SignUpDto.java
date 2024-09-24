package bsm.devcoop.oring.domain.account.presentation.dto;

import lombok.Builder;
import lombok.Getter;

public class SignUpDto {
    @Getter
    public static class Request {
        private String userEmail;
        private String userName;
        private String userPassword;
        private String userType;
    }

    @Builder
    @Getter
    public static class Response {
        private String message;
    }
}
