package com.chncwang.easy2db.table;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TableDef {
    private final String mTableName;
    private final PrimaryKeyDef mPrimaryKeyDef;
    private final ColumnDef mUniqueKeyDef;
    private final List<ColumnDef> mColumnDefs;

    public TableDef(final String tableName, final PrimaryKeyDef primaryKeyDef,
            final ColumnDef uniqueKeyDef, final List<ColumnDef> columnDefs) {
        mTableName = tableName;
        mPrimaryKeyDef = primaryKeyDef;
        mUniqueKeyDef = uniqueKeyDef;
        mColumnDefs = Collections.unmodifiableList(columnDefs);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getTableName() {
        return mTableName;
    }

    public PrimaryKeyDef getPrimaryKeyDef() {
        return mPrimaryKeyDef;
    }

    public ColumnDef getUniqueKeyDef() {
        return mUniqueKeyDef;
    }

    public List<ColumnDef> getColumnDefs() {
        return mColumnDefs;
    }
}
