package com.chncwang.easy2db.table.value;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.chncwang.easy2db.table.ColumnDef;

public class ColumnValue {
    private final ColumnDef mColumnDef;
    private final Object mValue;

    public ColumnValue(final ColumnDef columnDef, final Object value) {
        mColumnDef = columnDef;
        mValue = value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

    public ColumnDef getColumnDef() {
        return mColumnDef;
    }

    public Object getValue() {
        return mValue;
    }
}
