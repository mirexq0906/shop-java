package com.example.shop_java.web.dto.validation.entity_exist;

public interface EntityExistService {

    default boolean checkEntityExistById(Long id) {
        return false;
    }

    default boolean checkEntityExistByString(String field) {
        return false;
    }

}
