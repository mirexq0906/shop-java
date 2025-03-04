package com.example.shop_java.web.dto.user;

import com.example.shop_java.service.user.UserService;
import com.example.shop_java.web.dto.validation.entity_exist.EntityExist;
import com.example.shop_java.web.dto.validation.entity_exist.TypeField;
import com.example.shop_java.web.dto.validation.role_exist.RoleExist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

    @EntityExist(message = "Пользователь с таким |username| существует", service = UserService.class, typeField = TypeField.STRING)
    @Size(min = 2, max = 255, message = "Поле |username| должно содержать не менее {min} и не более {max} символов")
    @NotNull(message = "Поле |username| является обязательным для заполнения")
    private String username;

    @Size(min = 6, max = 255, message = "Поле |password| должно содержать не менее {min} и не более {max} символов")
    @NotNull(message = "Поле |password| является обязательным для заполнения")
    private String password;

    @Email(message = "Поле |email| не является email адресом")
    @NotNull(message = "Поле |email| является обязательным для заполнения")
    private String email;

    @NotNull(message = "Поле |role| является обязательным для заполнения")
    @RoleExist(message = "Поле |role| не является допустимой ролью или регистрация под этой ролью запрещена")
    private String role;

}
