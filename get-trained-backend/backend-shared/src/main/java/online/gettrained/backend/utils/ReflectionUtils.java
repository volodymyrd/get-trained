package online.gettrained.backend.utils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Util methods that use the reflection
 */
public class ReflectionUtils {
    public static Map<String, Field> getAllFields(Map<String, Field> fields, Class<?> type) {
        if (type.getSuperclass() != null) {
            fields = getAllFields(fields, type.getSuperclass());
        }

        for (Field field : type.getDeclaredFields()) {
            fields.put(field.getName(), field);
        }

        return fields;
    }
}
