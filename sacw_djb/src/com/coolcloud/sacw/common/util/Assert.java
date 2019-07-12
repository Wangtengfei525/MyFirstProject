package com.coolcloud.sacw.common.util;

import com.coolcloud.sacw.common.exception.InvalidArgumentException;

public class Assert {

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new InvalidArgumentException(message);
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new InvalidArgumentException(message);
        }
    }

}
