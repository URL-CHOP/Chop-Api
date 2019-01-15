package me.nexters.chop.api.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author junho.park
 */
@Getter
@NoArgsConstructor
class ErrorInfo {
    private HttpStatus status;
    private String message;

    ErrorInfo(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
