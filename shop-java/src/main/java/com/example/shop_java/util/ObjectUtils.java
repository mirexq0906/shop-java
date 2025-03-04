package com.example.shop_java.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;

@UtilityClass
public class ObjectUtils {

    @SneakyThrows
    public void copyFields(Object source, Object target) {
        Class<?> sourceClass = source.getClass();
        Field[] fields = sourceClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(source);
            if (value != null) {
                field.set(target, value);
            }
        }

    }

}
