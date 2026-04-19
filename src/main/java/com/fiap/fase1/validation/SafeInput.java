package com.fiap.fase1.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SafeInputValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SafeInput {
    String message() default "O campo contém caracteres inválidos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
