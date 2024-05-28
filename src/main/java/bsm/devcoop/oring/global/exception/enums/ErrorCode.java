package bsm.devcoop.oring.global.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_CORRECT("데이터가 올바르지 않습니다", 400),

    FORBIDDEN("권한이 없습니다.", 401),

    DATA_NOT_FOUND("필요한 데이터를 찾을 수 없습니다.", 404),

    DUPLICATE_DATA("중복되는 데이터입니다.", 409),

    INTERNAL_SERVER_ERROR("서버 에러가 발생하였습니다", 500);

    private final String message;
    private final int status;
}
