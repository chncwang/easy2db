package com.chncwang.easy2db.sql;

import java.util.Set;

import com.google.common.collect.Sets;

public class NumericTypes {
    private static final Set<Class<?>> sNumericClasses = Sets.newHashSet();

    static {
        sNumericClasses.add(Byte.class);
        sNumericClasses.add(Short.class);
        sNumericClasses.add(Integer.class);
        sNumericClasses.add(Long.class);
        sNumericClasses.add(Float.class);
        sNumericClasses.add(Double.class);
        sNumericClasses.add(Boolean.class);
    }

    public static boolean isNumericType(final Class<?> clazz) {
        return sNumericClasses.contains(clazz);
    }
}
