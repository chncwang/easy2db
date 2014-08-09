package com.chncwang.easy2db.table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.chncwang.easy2db.PreconditionsUtil;

public class ColumnDef {
    private final Class<?> mClass;
    private final String mName;

    public ColumnDef(final Class<?> clazz, final String name) {
        PreconditionsUtil.checkNotNull(name, "name");

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
