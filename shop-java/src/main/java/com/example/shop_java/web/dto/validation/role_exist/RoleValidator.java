package com.example.shop_java.web.dto.validation.role_exist;

import com.example.shop_java.entity.user.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<RoleExist, String> {

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return Role.valueOf(field) == Role.ROLE_USER;
        } catch (RuntimeException e) {
            return false;
        }
    }

}
