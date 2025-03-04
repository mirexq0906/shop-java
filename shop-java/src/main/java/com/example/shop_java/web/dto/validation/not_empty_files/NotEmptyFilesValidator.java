package com.example.shop_java.web.dto.validation.not_empty_files;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class NotEmptyFilesValidator implements ConstraintValidator<NotEmptyFiles, MultipartFile[]> {

    @Override
    public boolean isValid(MultipartFile[] multipartFiles, ConstraintValidatorContext constraintValidatorContext) {
        if (multipartFiles == null) {
            return true;
        }

        for (MultipartFile multipartFile : multipartFiles) {
            return !multipartFile.isEmpty();
        }

        return true;
    }

}
