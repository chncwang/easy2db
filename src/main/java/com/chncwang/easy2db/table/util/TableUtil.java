package com.chncwang.easy2db.table.util;

import com.chncwang.easy2db.table.TableDef;

public class TableUtil {
    private TableUtil() {}

    public static String getPrimaryKeyName(final TableDef tableDef) {
        return tableDef.getPrimaryKeyDef().getColumnDef().getName();
    }

    public static Class<?> getPrimaryKeyClass(final TableDef tableDef) {
        return tableDef.getPrimaryKeyDef().getColumnDef().getClazz();
    }
}
