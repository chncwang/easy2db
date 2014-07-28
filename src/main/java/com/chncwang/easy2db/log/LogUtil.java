package com.chncwang.easy2db.log;

import java.io.File;

import org.apache.log4j.PropertyConfigurator;

public class LogUtil {
    private static final String FILE_NAME_A = "log4j.properties";
    private static final String FILE_NAME_B = "target/log4j.properties";

    public static void init() {
        final String fileName = new File(FILE_NAME_A).exists() ? FILE_NAME_A
                : FILE_NAME_B;
        PropertyConfigurator.configure(fileName);
    }
}
