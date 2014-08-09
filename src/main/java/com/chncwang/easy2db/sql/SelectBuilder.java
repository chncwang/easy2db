package com.chncwang.easy2db.sql;

import java.util.List;

import com.chncwang.easy2db.Constants;
import com.chncwang.easy2db.table.ColumnDef;
import com.chncwang.easy2db.table.TableDef;
import com.chncwang.easy2db.table.util.ColumnUtil;
import com.chncwang.easy2db.table.value.ColumnWithValue;
import com.google.common.collect.Lists;

public class SelectBuilder {
    public static String build(final TableDef tableDef,
            final Object uniqueKeyValue) {
        final StringBuilder stringBuilder = new StringBuilder("SELECT ");

        final List<ColumnDef> columnValues = Lists.newArrayList();
        columnValues.add(tableDef.getPrimaryKeyDef().getColumnDef());
        columnValues.add(tableDef.getUniqueKeyDef());
        columnValues.addAll(tableDef.getColumnDefs());

        stringBuilder.append(ColumnUtil
                .joinNamesWithCommaWithColumnDefs(columnValues));

        stringBuilder.append(Constants.FROM);
        stringBuilder.append(tableDef.getTableName());
        stringBuilder.append(Constants.WHERE);
        stringBuilder.append(tableDef.getUniqueKeyDef().getName());
        stringBuilder.append(Constants.EQUAL);

        stringBuilder.append(ColumnUtil.getColumnValueString(new ColumnWithValue(
                tableDef.getUniqueKeyDef(), uniqueKeyValue)));

        return stringBuilder.toString();
    }
}
