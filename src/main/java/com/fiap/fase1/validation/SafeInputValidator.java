package com.fiap.fase1.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SafeInputValidator implements ConstraintValidator<SafeInput, String> {

    // Rejects HTML tags and null bytes
    private static final String DANGEROUS_PATTERN = ".*[<>\"'`\u0000].*";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return !value.matches(DANGEROUS_PATTERN);
    }
}
