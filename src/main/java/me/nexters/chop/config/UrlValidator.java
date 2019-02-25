package me.nexters.chop.config;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author junho.park
 */
public class UrlValidator implements ConstraintValidator<ValidUrl, String> {
    private static final Pattern URL_REGEX = Pattern.compile("^[\\s]*(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;()]*[-a-zA-Z0-9+&@#/%=~_|()]*[\\s]*");

    @Override
    public boolean isValid(String url, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();

        if (url.isEmpty()) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Url이 입력되지 않았습니다.")
                    .addConstraintViolation();

            return false;
        }

        Matcher matcher = URL_REGEX.matcher(url);

        if (!matcher.matches()) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("유효한 url이 아닙니다.")
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}
