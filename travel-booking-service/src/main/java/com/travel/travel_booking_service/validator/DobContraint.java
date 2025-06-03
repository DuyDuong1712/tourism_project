package com.travel.travel_booking_service.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD}) // Áp dụng cho field
@Retention(RetentionPolicy.RUNTIME) // Xử lý lúc runtime
@Constraint(
        validatedBy = {DobValidator.class} // Liên kết với class validator
        )
public @interface DobContraint {
    String message() default "Invalid date of birth";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 0;

    int max() default 100;
}
