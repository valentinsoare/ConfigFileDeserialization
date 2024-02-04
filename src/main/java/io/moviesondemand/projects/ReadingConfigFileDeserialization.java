package io.moviesondemand.projects;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ReadingConfigFileDeserialization {

    public static <T> Optional<T> createConfigObject(Class<T> clazz, Path filePath) {

        try {
            Scanner sc = new Scanner(filePath);

            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);

            T cfgInstance = (T) constructor.newInstance();

            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                String[] elements = s.split("=");

                String propertyName = elements[0];
                String propertyValue = elements[1];

                Field field = clazz.getDeclaredField(propertyName);

                field.setAccessible(true);
                Object parsedValue;

                if (field.getType().isArray()) {
                    parsedValue = parseArray(field.getType().getComponentType(), propertyValue);
                } else {
                    parsedValue = parseValue(field.getType(), propertyValue);
                }

                field.set(cfgInstance, parsedValue);
            }

            return Optional.of(cfgInstance);
        } catch (Exception e) {
            System.out.printf("%n%s", e.getMessage());
        }

        return Optional.empty();
    }

    private static Object parseArray(Class<?> componentType, String value) {
        String[] elements = value.split(",");

        Object arrObj = Array.newInstance(componentType, elements.length);

        for (int i = 0; i < elements.length; i++) {
            Array.set(arrObj, i, parseValue(componentType, elements[i].trim()));
        }

        return arrObj;
    }

    private static Object parseValue(Class<?> type, String value) {
        return switch (type.getSimpleName().toLowerCase()) {
            case "int" -> Integer.parseInt(value);
            case "double" -> Double.parseDouble(value);
            case "float" -> Float.parseFloat(value);
            case "short" -> Short.parseShort(value);
            case "long" -> Long.parseLong(value);
            case "string" -> value;
            default -> throw new RuntimeException(String.format("Type %s is not supported", type.getSimpleName()));
        };
    }

    public static <T> Optional<T> concat(Class<?> type, Object... arguments) {
        if (arguments.length == 0) {
            return Optional.empty();
        }

        List<Object> e = new ArrayList<>();

        for (Object argument : arguments) {
            if (argument.getClass().isArray()) {
                int length = Array.getLength(argument);

                for (int i = 0 ; i < length ; i++) {
                    e.add(Array.get(argument, i));
                }
            } else {
                e.add(argument);
            }
        }

        Object flattenedArray = Array.newInstance(type, e.size());

        for (int i = 0; i < e.size() ; i++) {
            Array.set(flattenedArray, i, e.get(i));
        }

        return Optional.of((T) flattenedArray);
    }
}
