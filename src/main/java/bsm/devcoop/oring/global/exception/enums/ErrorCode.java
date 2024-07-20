package bsm.devcoop.oring.global.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // client Error
    Bad_Request(400, "bad request : "),

    Forbidden(403, "forbidden : "),

    Not_Found(404, "not found : "),

    Conflict(409, "duplicate : "),

    // server Error
    Internal_Server_Error(500, "internal server error");

    private final int status;
    private final String message;
}
