package com.example.shop_java.web.dto.validation.entity_exist;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class EntityExistValidator implements ConstraintValidator<EntityExist, Object>, ApplicationContextAware {

    private Class<?> entityServiceClass;
    private ApplicationContext applicationContext;
    private TypeField typeField;

    @Override
    public void initialize(EntityExist constraintAnnotation) {
        entityServiceClass = constraintAnnotation.service();
        typeField = constraintAnnotation.typeField();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object field, ConstraintValidatorContext constraintValidatorContext) {
        if (field == null) {
            return true;
        }
        EntityExistService entityExistService = (EntityExistService) applicationContext.getBean(entityServiceClass);
        return switch (typeField) {
            case TypeField.NUMBER -> entityExistService.checkEntityExistById((Long) field);
            case TypeField.STRING -> entityExistService.checkEntityExistByString((String) field);
            default -> true;
        };
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
