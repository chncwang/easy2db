package com.chncwang.easy2db.table;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.chncwang.easy2db.PreconditionsUtil;

public class TableDef {
    private final Class<?> mClass;
    private final String mTableName;
    private final PrimaryKeyDef mPrimaryKeyDef;
    private final ColumnDef mUniqueKeyDef;
    private final List<ForeignKeyDef> mForeignKeyDefs;
    private final List<ColumnDef> mColumnDefs;

    public TableDef(final Class<?> clazz, final String tableName,
            final PrimaryKeyDef primaryKeyDef,
            final ColumnDef uniqueKeyDef,
            final List<ForeignKeyDef> foreignKeyDefs,
            final List<ColumnDef> columnDefs) {
        PreconditionsUtil.checkNotNull(tableName, "tableName");
        PreconditionsUtil.checkNotNull(primaryKeyDef, "primaryKeyDef");
        PreconditionsUtil.checkNotNull(uniqueKeyDef, "uniqueKeyDef");

        mClass = clazz;
        mTableName = tableName;
        mPrimaryKeyDef = primaryKeyDef;
        mUniqueKeyDef = uniqueKeyDef;
        mForeignKeyDefs = Collections.unmodifiableList(foreignKeyDefs);
        mColumnDefs = Collections.unmodifiableList(columnDefs);
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

    public PrimaryKeyDef getPrimaryKeyDef() {
        return mPrimaryKeyDef;
    }

    public ColumnDef getUniqueKeyDef() {
        return mUniqueKeyDef;
    }

    public List<ForeignKeyDef> getForeignKeyDefs() {
        return mForeignKeyDefs;
    }

    public List<ColumnDef> getColumnDefs() {
        return mColumnDefs;
    }
}
