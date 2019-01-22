package me.nexters.chop.api.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author junho.park
 */
@Getter
@NoArgsConstructor
class ErrorDetail {
    private HttpStatus status;
    private String message;

    ErrorDetail(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
