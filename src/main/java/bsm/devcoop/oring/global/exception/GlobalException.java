package bsm.devcoop.oring.global.exception;

import bsm.devcoop.oring.global.exception.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GlobalException extends Throwable {
    private final ErrorCode errorCode;
}
