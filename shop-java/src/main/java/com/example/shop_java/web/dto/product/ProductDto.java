package com.example.shop_java.web.dto.product;

import com.example.shop_java.service.CategoryService;
import com.example.shop_java.web.dto.validation.OnCreate;
import com.example.shop_java.web.dto.validation.OnUpdate;
import com.example.shop_java.web.dto.validation.entity_exist.EntityExist;
import com.example.shop_java.web.dto.validation.not_empty_file.NotEmptyFile;
import com.example.shop_java.web.dto.validation.not_empty_files.NotEmptyFiles;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDto {

    @Size(min = 2, max = 255, message = "Поле |name| должно содержать не менее {min} и не более {max} символов", groups = {OnCreate.class, OnUpdate.class})
    @NotNull(message = "Поле |name| является обязательным для заполнения", groups = {OnCreate.class})
    private String name;

    @Size(min = 2, message = "Поле |description| должно содержать не менее {min} символов", groups = {OnCreate.class, OnUpdate.class})
    @NotNull(message = "Поле |description| является обязательным для заполнения", groups = {OnCreate.class})
    private String description;

    @Min(value = 1, message = "Поле |price| должно содержать не менее {value}", groups = {OnCreate.class, OnUpdate.class})
    @NotNull(message = "Поле |price| является обязательным для заполнения", groups = {OnCreate.class})
    private Long price;

    @Min(value = 1, message = "Поле |oldPrice| должно содержать не менее {value}", groups = {OnCreate.class, OnUpdate.class})
    @NotNull(message = "Поле |oldPrice| является обязательным для заполнения", groups = {OnCreate.class})
    private Long oldPrice;

    @NotEmptyFile(message = "В поле |image| не передан файл", groups = {OnCreate.class, OnUpdate.class})
    @NotNull(message = "Поле |image| является обязательным для заполнения", groups = {OnCreate.class})
    private MultipartFile image;

    @NotEmptyFiles(message = "В поле |gallery| не передан файл", groups = {OnCreate.class, OnUpdate.class})
    @NotNull(message = "Поле |gallery| является обязательным для заполнения", groups = {OnCreate.class})
    private MultipartFile[] gallery;

    @EntityExist(message = "Такой категории не существует", groups = {OnCreate.class, OnUpdate.class}, service = CategoryService.class)
    @NotNull(message = "Поле |categoryId| является обязательным для заполнения", groups = {OnCreate.class})
    private Long categoryId;

}
