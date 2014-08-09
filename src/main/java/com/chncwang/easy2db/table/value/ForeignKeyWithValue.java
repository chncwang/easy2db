package com.chncwang.easy2db.table.value;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class ForeignKeyWithValue {
    private final String mColumnName;
    private final Row mForeignRow;

    public ForeignKeyWithValue(final String columnName, final Row foreignRow) {
        mColumnName = columnName;
        mForeignRow = foreignRow;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getColumnName() {
        return mColumnName;
    }

    public Row getForeignRow() {
        return mForeignRow;
    }
}