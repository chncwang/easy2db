package com.chncwang.easy2db;

import org.apache.log4j.Logger;

import com.chncwang.easy2db.log.LogUtil;

public class MainClass {
    private static final Logger LOG = Logger.getLogger(MainClass.class);

    public static void main(final String[] args) {
        LogUtil.init();
        LOG.info("main - Hello world!");
    }
}
