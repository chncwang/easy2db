package com.chncwang.easy2db.table.value;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Row {
    private final String mTableName;
    private final PrimaryKeyValue mPrimaryKeyValue;
    private final ColumnValue mUniqueKeyValue;
    private final List<ColumnValue> mColumnValues;

    public Row(final String tableName, final PrimaryKeyValue primaryKeyValue,
            final ColumnValue uniqueKeyValue,
            final List<ColumnValue> columnValues) {
        mTableName = tableName;
        mPrimaryKeyValue = primaryKeyValue;
        mUniqueKeyValue = uniqueKeyValue;
        mColumnValues = Collections.unmodifiableList(columnValues);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getTableName() {
        return mTableName;
    }

    public PrimaryKeyValue getPrimaryKeyValue() {
        return mPrimaryKeyValue;
    }

    public ColumnValue getUniqueKeyValue() {
        return mUniqueKeyValue;
    }

    public List<ColumnValue> getColumnValues() {
        return mColumnValues;
    }
}
