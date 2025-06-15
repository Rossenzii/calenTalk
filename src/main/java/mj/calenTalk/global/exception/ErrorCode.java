package mj.calenTalk.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 400: Auth Error
    INVALID_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, 401, "유효하지 않은 토큰입니다"),
    USER_INFO_FAIL(HttpStatus.BAD_REQUEST,402, "사용자 정보 요청 실패"),
    NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,403,"사용자가 존재하지 않습니다"),
    // 500: Message Error
    MESSAGE_PARSING_FAIL(HttpStatus.BAD_REQUEST, 501, "json 파싱에 실패했습니다."),
    MESSAGE_SERIALIZATION_FAIL(HttpStatus.BAD_REQUEST, 502, "메세지 직렬화에 실패했습니다."),
    // 600: Calendar Error
    GOOGLE_API_ERROR(HttpStatus.BAD_GATEWAY, 601, "Google Calendar API 호출 실패");


    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
