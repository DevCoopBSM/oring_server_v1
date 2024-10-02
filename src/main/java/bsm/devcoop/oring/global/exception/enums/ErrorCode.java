package bsm.devcoop.oring.global.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Client
    BAD_REQUEST(400, "BAD REQUEST : "),

    FORBIDDEN(403, "FORBIDDEN : "),

    NOT_FOUND(404, "NOT_FOUND : "),

    CONFLICT(409, "CONFLICT : "),


    // Server Error
    INTERNAL_SERVER_ERROR(500, "INTERNAL SERVER ERROR");

    private final int status;
    private final String message;
}
