package com.example.shop_java.service.user;

import com.example.shop_java.entity.user.User;
import com.example.shop_java.web.dto.validation.entity_exist.EntityExistService;

public interface UserService extends EntityExistService {

    User findById(Long id);

    User findByUsername(String username);

}
