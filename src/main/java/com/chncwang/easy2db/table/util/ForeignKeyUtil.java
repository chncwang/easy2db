package com.chncwang.easy2db.table.util;

import com.chncwang.easy2db.table.ColumnDef;
import com.chncwang.easy2db.table.value.ColumnWithValue;
import com.chncwang.easy2db.table.value.ForeignKeyWithValue;
import com.chncwang.easy2db.table.value.PrimaryKeyWithValue;
import com.chncwang.easy2db.table.value.Row;

public class ForeignKeyUtil {
    private ForeignKeyUtil() {}

    public static void setForeignKeyPrimaryKeyValue(
            final ForeignKeyWithValue foreignKeyWithValue, final Object value) {
        getPrimayKeyWithValue(foreignKeyWithValue).setValue(value);
    }

    public static Object getPrimaryKeyValue(
            final ForeignKeyWithValue foreignKeyWithValue) {
        return getPrimayKeyWithValue(foreignKeyWithValue).getValue();
    }

    public static PrimaryKeyWithValue getPrimayKeyWithValue(
            final ForeignKeyWithValue foreignKeyValue) {
        return foreignKeyValue.getForeignRow().getPrimaryKeyValue();
    }

    public static Class<?> getType(final ForeignKeyWithValue foreignKeyWithValue) {
        final Row row = foreignKeyWithValue.getForeignRow();
        return RowUtil.getPrimaryKeyType(row);
    }

    public static ColumnWithValue toColumnWithValue(
            final ForeignKeyWithValue foreignKeyWithValue) {
        final Class<?> type = getType(foreignKeyWithValue);
        final ColumnDef columnDef = new ColumnDef(type,
                foreignKeyWithValue.getColumnName());
        final Object value = getPrimaryKeyValue(foreignKeyWithValue);
        return new ColumnWithValue(columnDef, value);
    }
}
