package com.chncwang.easy2db.parser;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.chncwang.easy2db.table.ColumnDef;
import com.chncwang.easy2db.table.ForeignKeyDef;
import com.chncwang.easy2db.table.PrimaryKeyDef;
import com.chncwang.easy2db.table.TableDef;
import com.chncwang.easy2db.table.annotation.Column;
import com.chncwang.easy2db.table.annotation.Foreign;
import com.chncwang.easy2db.table.annotation.PrimaryKey;
import com.chncwang.easy2db.table.annotation.Table;
import com.chncwang.easy2db.table.annotation.UniqueKey;
import com.chncwang.easy2db.table.util.TableUtil;
import com.chncwang.easy2db.table.value.ColumnWithValue;
import com.chncwang.easy2db.table.value.ForeignKeyWithValue;
import com.chncwang.easy2db.table.value.PrimaryKeyWithValue;
import com.chncwang.easy2db.table.value.Row;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class TableParser {
    private static final Logger LOG = Logger.getLogger(TableParser.class);

    private static final Map<Class<?>, TableParser> sTableParserMap = Maps
            .newHashMap();

    private final Class<?> mClass;

    private TableDef mTableDef;
    private Field mPrimaryKeyField;
    private Field mUniqueKeyField;
    private List<Field> mForeignFields;
    private List<Field> mColumnFields;

    public static synchronized TableParser getTableParser(final Class<?> clazz) {
        TableParser tableParser = sTableParserMap.get(clazz);
        if (tableParser == null) {
            tableParser = new TableParser(clazz).init();
            sTableParserMap.put(clazz, tableParser);
        }
        return tableParser;
    }

    private TableParser(final Class<?> clazz) {
        mClass = clazz;
    }

    public Class<?> getClazz() {
        return mClass;
    }

    public TableDef getTableDef() {
        return mTableDef;
    }

    public Field getPrimaryKeyField() {
        return mPrimaryKeyField;
    }

    public Field getUniqueKeyField() {
        return mUniqueKeyField;
    }

    public List<Field> getForeignFields() {
        return mForeignFields;
    }

    public List<Field> getColumnFields() {
        return mColumnFields;
    }

    public Row toRow(final Object o) {
        LOG.debug("toRow - mTableDef:" + mTableDef + " - o:" + o);

        final PrimaryKeyWithValue primaryKeyValue;
        final ColumnWithValue uniqueKeyValue;
        try {
            primaryKeyValue = new PrimaryKeyWithValue(mTableDef.getPrimaryKeyDef(),
                    mPrimaryKeyField.get(o));
            uniqueKeyValue = new ColumnWithValue(mTableDef.getUniqueKeyDef(),
                    mUniqueKeyField.get(o));
        } catch (final IllegalAccessException e) {
            throw new IllegalStateException(e);
        }

        final List<ForeignKeyWithValue> foreignKeyValues = Lists.newArrayList();
        for (int i=0; i<mForeignFields.size(); ++i) {
            final String columnName = mTableDef.getForeignKeyDefs().get(i)
                    .getColumnName();
            final Field foreignField = mForeignFields.get(i);
            final Class<?> foreignClass = foreignField.getType();
            final Object foreignRowObject;
            try {
                foreignRowObject = foreignField.get(o);
            } catch (final IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
            final Row foreignRow = TableParser.getTableParser(foreignClass)
                    .toRow(foreignRowObject);

            foreignKeyValues.add(new ForeignKeyWithValue(columnName, foreignRow));
        }

        final List<ColumnWithValue> columnValues = Lists.newArrayList();
        for (int i=0; i<mColumnFields.size(); ++i) {
            try {
                columnValues.add(new ColumnWithValue(mTableDef.getColumnDefs().get(
                        i), mColumnFields.get(i).get(o)));
            } catch (final IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }

        return new Row(mClass, mTableDef.getTableName(), primaryKeyValue,
                uniqueKeyValue, foreignKeyValues, columnValues);
    }

    private TableParser init() {
        final Table tableAnnotation = mClass.getAnnotation(Table.class);
        final String tableName = tableAnnotation.value();

        PrimaryKeyDef primaryKeyDef = null;
        ColumnDef uniqueKeyDef = null;
        final List<ForeignKeyDef> foreignKeyDefs = Lists.newArrayList();
        final List<ColumnDef> columnDefs = Lists.newArrayList();
        final List<Field> foreignFields = Lists.newArrayList();
        final List <Field> columnFields = Lists.newArrayList();

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
            final Foreign foreign = field.getAnnotation(Foreign.class);

            if (primaryKey != null) {
                if (primaryKeyDef != null) {
                    throw new IllegalStateException(
                            "More than 1 primay key found!");
                }

                mPrimaryKeyField = field;
                final ColumnDef columnDef = new ColumnDef(
                        field.getType(), column.value());
                primaryKeyDef = new PrimaryKeyDef(columnDef);
            } else if (uniqueKey != null) {
                if (uniqueKeyDef != null) {
                    throw new IllegalStateException(
                            "More than 1 unique key found!");
                }

                mUniqueKeyField = field;
                uniqueKeyDef = new ColumnDef(field.getType(),
                        column.value());
            } else if (foreign != null) {
                foreignFields.add(field);
                final TableDef tableDef = TableParser.getTableParser(
                        field.getType()).getTableDef();
                foreignKeyDefs.add(new ForeignKeyDef(column.value(), tableDef));
            } else {
                columnFields.add(field);
                columnDefs.add(new ColumnDef(field.getType(), column
                        .value()));
            }
        }

        mForeignFields = Collections.unmodifiableList(foreignFields);
        mColumnFields = Collections.unmodifiableList(columnFields);

        mTableDef = new TableDef(mClass, tableName, primaryKeyDef, uniqueKeyDef,
                foreignKeyDefs, columnDefs);

        validate();

        return this;
    }

    private void validate() {
        Preconditions.checkState(mForeignFields.size() == mTableDef
                .getForeignKeyDefs().size());
        Preconditions.checkState(mColumnFields.size() == mTableDef
                .getColumnDefs().size());

        final Set<String> columnNames = Sets.newTreeSet();
        columnNames.add(TableUtil.getPrimaryKeyName(mTableDef));
        columnNames.add(mTableDef.getUniqueKeyDef().getName());

        for (final ColumnDef columnDef : mTableDef.getColumnDefs()) {
            columnNames.add(columnDef.getName());
        }

        Preconditions.checkState(columnNames.size() == mTableDef
                .getColumnDefs().size() + 2, "Duplicate column name found!");
    }
}
