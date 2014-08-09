package com.chncwang.easy2db.table.util;

import java.util.List;

import com.chncwang.easy2db.table.value.ColumnWithValue;
import com.chncwang.easy2db.table.value.ForeignKeyWithValue;
import com.chncwang.easy2db.table.value.Row;
import com.google.common.collect.Lists;

public class RowUtil {
    private RowUtil() {}

    public static Class<?> getPrimaryKeyType(final Row row) {
        return row.getPrimaryKeyValue().getPrimaryKeyDef().getColumnDef()
                .getClazz();
    }

    public static String getPrimaryKeyName(final Row row) {
        return row.getPrimaryKeyValue().getPrimaryKeyDef().getColumnDef()
                .getName();
    }

    public static ColumnWithValue getPrimaryKeyAsColumnWithValue(final Row row) {
        return new ColumnWithValue(row.getPrimaryKeyValue().getPrimaryKeyDef()
                .getColumnDef(), row.getPrimaryKeyValue().getValue());
    }

    public static String getUniqueKeyName(final Row row) {
        return row.getUniqueKeyValue().getColumnDef().getName();
    }

    public static Class<?> getUniqueKeyType(final Row row) {
        return row.getUniqueKeyValue().getColumnDef().getClazz();
    }

    public static String getColumnName(final Row row, final int index) {
        return row.getColumnWithValueList().get(index).getColumnDef().getName();
    }

    public static List<ColumnWithValue> getForeignKeyWithValueListAsColumnWithValueList(
            final Row row) {
        final List<ForeignKeyWithValue> foreignKeyWithValueList = row
                .getForeignKeyWithValueList();
        final List<ColumnWithValue> columnWithValueList = Lists
                .newArrayListWithCapacity(foreignKeyWithValueList.size());

        for (final ForeignKeyWithValue foreignKeyWithValue : foreignKeyWithValueList) {
            final ColumnWithValue columnWithValue = ForeignKeyUtil
                    .toColumnWithValue(foreignKeyWithValue);
            columnWithValueList.add(columnWithValue);
        }

        return columnWithValueList;
    }
}
