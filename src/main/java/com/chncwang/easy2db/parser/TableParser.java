package com.chncwang.easy2db.parser;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.log4j.Logger;

import com.chncwang.easy2db.table.ColumnDef;
import com.chncwang.easy2db.table.PrimaryKeyDef;
import com.chncwang.easy2db.table.TableDef;
import com.chncwang.easy2db.table.annotation.Column;
import com.chncwang.easy2db.table.annotation.PrimaryKey;
import com.chncwang.easy2db.table.annotation.Table;
import com.chncwang.easy2db.table.annotation.UniqueKey;
import com.chncwang.easy2db.table.value.ColumnValue;
import com.chncwang.easy2db.table.value.PrimaryKeyValue;
import com.chncwang.easy2db.table.value.Row;
import com.google.common.collect.Lists;

public class TableParser<T> {
    private static final Logger LOG = Logger.getLogger(TableParser.class);

    private final Class<T> mClass;

    private TableDef mTableDef;
    private Field mPrimayKeyField;
    private Field mUniqueKeyField;
    private List<Field> mColumnFields = Lists.newArrayList();

    public static <T> TableParser<T> newTableParser(final Class<T> clazz) {
        return new TableParser<T>(clazz).init();
    }

    private TableParser(final Class<T> clazz) {
        mClass = clazz;
    }

    public TableDef getTableDef() {
        return mTableDef;
    }

    public Row toRow(final T o) {
        final PrimaryKeyValue primaryKeyValue;
        final ColumnValue uniqueKeyValue;
        try {
            primaryKeyValue = new PrimaryKeyValue(mTableDef.getPrimaryKeyDef(),
                    mPrimayKeyField.get(o));
            uniqueKeyValue = new ColumnValue(mTableDef.getUniqueKeyDef(),
                    mUniqueKeyField.get(o));
        } catch (final IllegalAccessException e) {
            throw new IllegalStateException(e);
        }

        if (mColumnFields.size() != mTableDef.getColumnDefs().size()) {
            throw new IllegalStateException(
                    "mColumnFields.size() is not equal to mTableDef.getColumnDefs().size()!");
        }

        final List<ColumnValue> columnValues = Lists.newArrayList();
        for (int i=0; i<mColumnFields.size(); ++i) {
            try {
                columnValues.add(new ColumnValue(mTableDef.getColumnDefs().get(
                        i), mColumnFields.get(i).get(o)));
            } catch (final IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }

        return new Row(mTableDef.getTableName(), primaryKeyValue,
                uniqueKeyValue, columnValues);
    }

    private TableParser<T> init() {
        final Table tableAnnotation = mClass.getAnnotation(Table.class);
        final String tableName = tableAnnotation.value();

        PrimaryKeyDef primaryKeyDef = null;
        ColumnDef uniqueKeyDef = null;
        final List<ColumnDef> columnDefs = Lists.newArrayList();

        final Field[] fields = mClass.getDeclaredFields();
        for (final Field field : fields) {
            LOG.debug("init - field:" + field);

            field.setAccessible(true);
            final Column column = field.getAnnotation(Column.class);
            if (column == null) {
                continue;
            }

            final PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
            final UniqueKey uniqueKey = field.getAnnotation(UniqueKey.class);

            if (primaryKey != null) {
                if (primaryKeyDef != null) {
                    throw new IllegalStateException(
                            "More than 1 primay key found!");
                }

                mPrimayKeyField = field;
                final ColumnDef columnDef = new ColumnDef(
                        field.getType(), column.value());
                primaryKeyDef = new PrimaryKeyDef(columnDef,
                        primaryKey.needInsert());
            } else if (uniqueKey != null) {
                if (uniqueKeyDef != null) {
                    throw new IllegalStateException(
                            "More than 1 unique key found!");
                }

                mUniqueKeyField = field;
                uniqueKeyDef = new ColumnDef(field.getType(),
                        column.value());
            } else {
                mColumnFields.add(field);
                columnDefs.add(new ColumnDef(field.getType(), column
                        .value()));
            }
        }

        if (tableName == null || primaryKeyDef == null || uniqueKeyDef == null) {
            LOG.error("init - tableName:" + tableName + " primaryKeyDef:"
                    + primaryKeyDef + " uniqueKeyDef:" + uniqueKeyDef);
            throw new IllegalStateException(
                    "tableName is null or primaryKeyDef is null or uniqueKeyDef is null!");
        }

        mTableDef = new TableDef(tableName, primaryKeyDef, uniqueKeyDef,
                columnDefs);

        return this;
    }
}
