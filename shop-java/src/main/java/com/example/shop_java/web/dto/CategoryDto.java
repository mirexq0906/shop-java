package com.example.shop_java.web.dto;

import com.example.shop_java.web.dto.validation.OnCreate;
import com.example.shop_java.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDto {

    @NotNull(message = "Поле |name| является обязательным для заполнения", groups = {OnCreate.class})
    @Size(min = 2, max = 255, message = "Поле |name| должно содержать не менее {min} и не более {max} символов", groups = {OnCreate.class, OnUpdate.class})
    private String name;

}
