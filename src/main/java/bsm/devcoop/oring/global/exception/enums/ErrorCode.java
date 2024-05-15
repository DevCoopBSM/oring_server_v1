package bsm.devcoop.oring.global.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    PASSWORD_NOT_CORRECT("비밀번호가 올바르지 않습니다", 400),
    DATE_NOT_CORRECT("비밀번호가 올바르지 않습니다", 400),

    FORBIDDEN("권한이 없습니다.", 401),

    USER_NOT_FOUND("사용자를 찾을 수 없습니다.", 404),
    CONFERENCE_NOT_FOUND("회의를 찾을 수 없습니다.", 404),

    INTERNAL_SERVER_ERROR("서버 에러가 발생하였습니다", 500);

    private final String message;
    private final int status;
}
