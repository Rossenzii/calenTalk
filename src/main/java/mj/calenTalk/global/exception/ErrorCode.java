package mj.calenTalk.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 401: Auth Error
    INVALID_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, 401, "유효하지 않은 토큰입니다"),
    USER_INFO_FAIL(HttpStatus.BAD_REQUEST,402, "사용자 정보 요청 실패");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
