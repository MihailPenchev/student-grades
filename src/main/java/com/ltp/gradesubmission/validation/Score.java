package com.ltp.gradesubmission.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ScoreValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Score {
    String message() default "Invalid data";
    // always expected:
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
