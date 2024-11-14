package com.service.auth.constants;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface BaseEnum {

    String getValue();

    default int getValueAsInt() {
        try {
            return Integer.parseInt(getValue());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    default String getName() {
        Class<?> clazz = this.getClass();
        if (BaseEnum.class.isAssignableFrom(clazz)) {
            return clazz.getSimpleName() + "." + this.toString().replace("_", ".").toLowerCase();
        } else {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                return superClass.getSimpleName() + "." + clazz.getSimpleName().replace("_", ".").toLowerCase();
            }
            return null; // or throw an exception if needed
        }
    }

    static <T extends BaseEnum> T valOf(Class<T> clazz, String value) {
        if (clazz.isEnum()) {
            for (T enumConstant : clazz.getEnumConstants()) {
                if (enumConstant.getValue().equals(value)) {
                    return enumConstant;
                }
            }
        } else {
            // Handle nested classes and companion objects (if applicable)
            // This part may require additional logic depending on your use case
            // For simplicity, we will not implement this in detail as it depends on your specific structure.
        }
        return null;
    }
}