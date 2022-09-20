package com.ltp.gradesubmission.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class ScoreValidator implements ConstraintValidator<Score, String> {
    private static final List<String> ALLOWED_SCORES = Arrays.asList("A", "A+", "A-", "B", "B+", "B-", "C", "C+", "C-",
            "D", "D+", "D-", "F");
    @Override
    public boolean isValid(String score, ConstraintValidatorContext var2) {
        return ALLOWED_SCORES.contains(score);
    }
}
