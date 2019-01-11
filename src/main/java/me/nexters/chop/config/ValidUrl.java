package me.nexters.chop.config;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author junho.park
 */
@Documented
@Constraint(validatedBy = UrlValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUrl {
    String message() default "유효하지 않은 입력입니다";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
