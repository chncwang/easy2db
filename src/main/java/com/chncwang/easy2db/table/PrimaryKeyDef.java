package com.chncwang.easy2db.table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PrimaryKeyDef {
    private final ColumnDef mColumnDef;

    public PrimaryKeyDef(final ColumnDef columnDef) {
        mColumnDef = columnDef;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

    public ColumnDef getColumnDef() {
        return mColumnDef;
    }
}
