package com.chncwang.easy2db.table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.chncwang.easy2db.PreconditionsUtil;

public class ForeignKeyDef {
    private final String mColumnName;
    private final TableDef mTableDef;

    public ForeignKeyDef(final String columnName, final TableDef tableDef) {
        PreconditionsUtil.checkNotNull(columnName, "columnName");

        mColumnName = columnName;
        mTableDef = tableDef;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getColumnName() {
        return mColumnName;
    }

    public TableDef getTableDef() {
        return mTableDef;
    }
}