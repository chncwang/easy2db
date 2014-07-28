package com.chncwang.easy2db.table.util;

import com.chncwang.easy2db.table.value.ColumnValue;
import com.chncwang.easy2db.table.value.Row;

public class RowUtil {
    private RowUtil() {}

    public static String getPrimaryKeyName(final Row row) {
        return row.getPrimaryKeyValue().getPrimaryKeyDef().getColumnDef()
                .getName();
    }

    public static ColumnValue getPrimaryKeyAsColumnValue(final Row row) {
        return new ColumnValue(row.getPrimaryKeyValue().getPrimaryKeyDef()
                .getColumnDef(), row.getPrimaryKeyValue().getValue());
    }

    public static String getUniqueKeyName(final Row row) {
        return row.getUniqueKeyValue().getColumnDef().getName();
    }

    public static Class<?> getUniqueKeyType(final Row row) {
        return row.getUniqueKeyValue().getColumnDef().getClazz();
    }

    public static String getColumnName(final Row row, final int index) {
        return row.getColumnValues().get(index).getColumnDef().getName();
    }
}
