package com.fiap.fase1.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "A senha deve ter no mínimo 8 caracteres, incluindo letra maiúscula, minúscula e número";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
