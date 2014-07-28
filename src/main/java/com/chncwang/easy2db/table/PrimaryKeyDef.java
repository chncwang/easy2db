package com.chncwang.easy2db.table;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PrimaryKeyDef {
    private final ColumnDef mColumnDef;
    private final boolean mNeedInsert;

    public PrimaryKeyDef(final ColumnDef columnDef, final boolean needInsert) {
        mColumnDef = columnDef;
        mNeedInsert = needInsert;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public ColumnDef getColumnDef() {
        return mColumnDef;
    }

    public boolean needInsert() {
        return mNeedInsert;
    }
}
