package com.chncwang.easy2db.parser;

import java.util.Map;

import com.chncwang.easy2db.table.ColumnDef;
import com.chncwang.easy2db.table.ForeignKeyDef;
import com.chncwang.easy2db.table.TableDef;
import com.chncwang.easy2db.table.util.TableUtil;
import com.google.common.collect.Maps;

public class TableParserUtil {
    private TableParserUtil() {}

    public static Map<String, Class<?>> createColumnClassMap(
            final TableParser tableParser) {
        final Map<String, Class<?>> map = Maps.newTreeMap();

        final TableDef tableDef = tableParser.getTableDef();

        map.put(TableUtil.getPrimaryKeyName(tableDef),
                TableUtil.getPrimaryKeyClass(tableDef));

        map.put(tableDef.getUniqueKeyDef().getName(), tableDef
                .getUniqueKeyDef().getClazz());

        for (final ForeignKeyDef foreignKeyDef : tableDef.getForeignKeyDefs()) {
            map.put(foreignKeyDef.getColumnName(),
                    TableUtil.getPrimaryKeyClass(foreignKeyDef.getTableDef()));
        }

        for (final ColumnDef columnDef : tableDef.getColumnDefs()) {
            map.put(columnDef.getName(), columnDef.getClazz());
        }

        return map;
    }
}