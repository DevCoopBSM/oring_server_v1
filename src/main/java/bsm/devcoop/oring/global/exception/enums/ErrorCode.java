package bsm.devcoop.oring.global.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    PASSWORD_NOT_CORRECT("비밀번호가 올바르지 않습니다", 400),
    DATE_NOT_CORRECT("회의 날짜가 올바르지 않습니다", 400),

    FORBIDDEN("권한이 없습니다.", 401),

    USER_NOT_FOUND("사용자를 찾을 수 없습니다.", 404),
    CONFERENCE_NOT_FOUND("회의를 찾을 수 없습니다.", 404),
    AGENDA_NOT_FOUND("안건을 찾을 수 없습니다.", 404),
    DATA_NOT_FOUND("필요한 데이터를 찾을 수 없습니다.", 404),

    DUPLICATE_DATA("중복되는 데이터입니다.", 409),

    INTERNAL_SERVER_ERROR("서버 에러가 발생하였습니다", 500);

    private final String message;
    private final int status;
}
