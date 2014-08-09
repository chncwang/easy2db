package com.chncwang.easy2db.table.value;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Row {
    private final Class<?> mClass;
    private final String mTableName;
    private final PrimaryKeyWithValue mPrimaryKeyWithValue;
    private final ColumnWithValue mUniqueKeyWithValue;
    private final List<ForeignKeyWithValue> mForeignKeyWithValueList;
    private final List<ColumnWithValue> mColumnWithValueList;

    public Row(final Class<?> clazz,
            final String tableName,
            final PrimaryKeyWithValue primaryKeyValue,
            final ColumnWithValue uniqueKeyValue,
            final List<ForeignKeyWithValue> foreignKeyValues,
            final List<ColumnWithValue> columnValues) {
        mClass = clazz;
        mTableName = tableName;
        mPrimaryKeyWithValue = primaryKeyValue;
        mUniqueKeyWithValue = uniqueKeyValue;
        mForeignKeyWithValueList = Collections
                .unmodifiableList(foreignKeyValues);
        mColumnWithValueList = Collections.unmodifiableList(columnValues);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

    public Class<?> getClazz() {
        return mClass;
    }

    public String getTableName() {
        return mTableName;
    }

    public PrimaryKeyWithValue getPrimaryKeyValue() {
        return mPrimaryKeyWithValue;
    }

    public ColumnWithValue getUniqueKeyValue() {
        return mUniqueKeyWithValue;
    }

    public List<ForeignKeyWithValue> getForeignKeyWithValueList() {
        return mForeignKeyWithValueList;
    }

    public List<ColumnWithValue> getColumnWithValueList() {
        return mColumnWithValueList;
    }
}
