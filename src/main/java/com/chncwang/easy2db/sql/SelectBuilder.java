package com.chncwang.easy2db.sql;

import java.util.List;

import com.chncwang.easy2db.Constants;
import com.chncwang.easy2db.table.util.ColumnUtil;
import com.chncwang.easy2db.table.util.RowUtil;
import com.chncwang.easy2db.table.value.ColumnValue;
import com.chncwang.easy2db.table.value.Row;
import com.google.common.collect.Lists;

public class SelectBuilder {
    public static String build(final Row row) {
        final StringBuilder stringBuilder = new StringBuilder("SELECT ");

        final List<ColumnValue> columnValues = Lists.newArrayList();
        columnValues.add(RowUtil.getPrimaryKeyAsColumnValue(row));
        columnValues.add(row.getUniqueKeyValue());
        columnValues.addAll(row.getColumnValues());

        stringBuilder.append(ColumnUtil.joinNamesWithComma(columnValues));

        stringBuilder.append(Constants.FROM);
        stringBuilder.append(row.getTableName());
        stringBuilder.append(Constants.WHERE);
        stringBuilder.append(RowUtil.getUniqueKeyName(row));
        stringBuilder.append(Constants.EQUAL);

        stringBuilder.append(ColumnUtil.getColumnValueString(row
                .getUniqueKeyValue()));

        return stringBuilder.toString();
    }
}
