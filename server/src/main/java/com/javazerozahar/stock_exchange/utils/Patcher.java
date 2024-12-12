package com.javazerozahar.stock_exchange.utils;

import com.javazerozahar.stock_exchange.exceptions.CannotPatchException;
import com.javazerozahar.stock_exchange.model.annotations.DtoId;
import com.javazerozahar.stock_exchange.model.annotations.Updatable;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@Log4j2
public class Patcher {

    public <T> T patch(T target, T source) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Received null argument");
        }

        Class<?> clazz = target.getClass();

        try {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(DtoId.class)) {
                    continue;
                }

                field.setAccessible(true);

                Object value = field.get(source);

                if (value != null) {
                    if (field.isAnnotationPresent(Updatable.class)) {
                        field.set(target, value);
                    } else {
                        if (!(value instanceof Iterable<?>) && !value.equals(field.get(target))) {
                            log.info(value.getClass().getName() + ": " + value);
                            throw new CannotPatchException("Field " + field.getName() + " is not updatable");
                        }
                    }
                }

                field.setAccessible(false);
            }

            return target;

        } catch (IllegalAccessException | CannotPatchException e) {
            log.error("Cannot match field: {}", e.getMessage());
            throw new CannotPatchException("Cannot match field: " + e.getMessage());
        }
    }

}