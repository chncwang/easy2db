package com.chncwang.easy2db.sql;

import java.util.List;

import com.chncwang.easy2db.Constants;
import com.chncwang.easy2db.table.util.ColumnUtil;
import com.chncwang.easy2db.table.util.RowUtil;
import com.chncwang.easy2db.table.value.ColumnWithValue;
import com.chncwang.easy2db.table.value.Row;
import com.google.common.collect.Lists;

public class InsertBuilder {
    public static String build(final Row row) {
        final StringBuilder stringBuilder = new StringBuilder("INSERT INTO ");

        stringBuilder.append(row.getTableName());
        stringBuilder.append(Constants.LEFT_PARENTHESES);

        final List<ColumnWithValue> foreignColumns = RowUtil
                .getForeignKeyWithValueListAsColumnWithValueList(row);
        final List<ColumnWithValue> columnWithValueList = row
                .getColumnWithValueList();
        final int expectedSize = foreignColumns.size()
                + columnWithValueList.size() + 1;
        final List<ColumnWithValue> fullColumns = Lists
                .newArrayListWithCapacity(expectedSize);
        fullColumns.add(row.getUniqueKeyValue());
        fullColumns.addAll(foreignColumns);
        fullColumns.addAll(columnWithValueList);

        stringBuilder.append(ColumnUtil.joinNamesWithComma(fullColumns));
        stringBuilder.append(Constants.RIGHT_PARENTHESES);

        stringBuilder.append(Constants.VALUES);
        stringBuilder.append(Constants.LEFT_PARENTHESES);
        stringBuilder.append(ColumnUtil.joinValuesWithComma(fullColumns));
        stringBuilder.append(Constants.RIGHT_PARENTHESES);

        return stringBuilder.toString();
    }
}
