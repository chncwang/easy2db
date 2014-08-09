package com.chncwang.easy2db;

import com.google.common.base.Preconditions;

public class PreconditionsUtil {
    private PreconditionsUtil() {}

    public static void checkNotNull(final Object arg, final String argName) {
        Preconditions.checkNotNull(arg, Constants.NULL_POINTER_ERROR_TEMPLATE,
                argName);
    }
}
