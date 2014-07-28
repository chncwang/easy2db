package com.chncwang.easy2db.table;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ColumnDef {
    private final Class<?> mClass;
    private final String mName;

    public ColumnDef(final Class<?> clazz, final String name) {
        mClass = clazz;
        mName = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Class<?> getClazz() {
        return mClass;
    }

    public String getName() {
        return mName;
    }
}
