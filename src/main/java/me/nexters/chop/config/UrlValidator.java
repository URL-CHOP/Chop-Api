package me.nexters.chop.config;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author junho.park
 */
public class UrlValidator implements ConstraintValidator<ValidUrl, String> {

    private String urlRegex = "^[\\s]*(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]*[\\s]*";

    @Override
    public void initialize(ValidUrl constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        constraintValidatorContext.disableDefaultConstraintViolation();

        if (s.length() == 0) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Url이 입력되지 않았습니다.")
                    .addConstraintViolation();

            return false;
        }

        if (!s.matches(urlRegex)) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("유효한 url이 아닙니다.")
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}
