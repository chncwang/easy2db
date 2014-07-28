package com.chncwang.easy2db.sql;

import java.util.List;

import com.chncwang.easy2db.Constants;
import com.chncwang.easy2db.table.util.ColumnUtil;
import com.chncwang.easy2db.table.util.RowUtil;
import com.chncwang.easy2db.table.value.ColumnValue;
import com.chncwang.easy2db.table.value.Row;
import com.google.common.collect.Lists;

public class InsertBuilder {
    public static String build(final Row row) {
        final StringBuilder stringBuilder = new StringBuilder("INSERT INTO ");

        stringBuilder.append(row.getTableName());
        stringBuilder.append(Constants.LEFT_PARENTHESES);

        final List<ColumnValue> columns = Lists.newArrayList();
        if (row.getPrimaryKeyValue().getPrimaryKeyDef().needInsert()) {
            columns.add(RowUtil.getPrimaryKeyAsColumnValue(row));
        }
        columns.add(row.getUniqueKeyValue());
        columns.addAll(row.getColumnValues());

        stringBuilder.append(ColumnUtil.joinNamesWithComma(columns));
        stringBuilder.append(Constants.RIGHT_PARENTHESES);

        stringBuilder.append(Constants.VALUES);
        stringBuilder.append(Constants.LEFT_PARENTHESES);
        stringBuilder.append(ColumnUtil.joinValuesWithComma(columns));
        stringBuilder.append(Constants.RIGHT_PARENTHESES);

        return stringBuilder.toString();
    }
}
