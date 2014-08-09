package com.chncwang.easy2db.sql;

import java.util.List;

import com.chncwang.easy2db.Constants;
import com.chncwang.easy2db.table.util.ColumnUtil;
import com.chncwang.easy2db.table.util.RowUtil;
import com.chncwang.easy2db.table.value.ColumnWithValue;
import com.chncwang.easy2db.table.value.Row;
import com.google.common.collect.Lists;

public class UpdateBuilder {
    public static String build(final Row row) {
        final List<ColumnWithValue> foreignColumns = RowUtil
                .getForeignKeyWithValueListAsColumnWithValueList(row);
        final List<ColumnWithValue> columnWithValueList = row
                .getColumnWithValueList();
        final int expectedSize = foreignColumns.size()
                + columnWithValueList.size();

        if (expectedSize == 0) {
            return null;
        }

        final StringBuilder stringBuilder = new StringBuilder("UPDATE ");
        stringBuilder.append(row.getTableName());
        stringBuilder.append(Constants.SET);

        final List<ColumnWithValue> fullColumnWithValueList = Lists
                .newArrayListWithCapacity(expectedSize);
        fullColumnWithValueList.addAll(foreignColumns);
        fullColumnWithValueList.addAll(columnWithValueList);

        stringBuilder.append(ColumnUtil
                .joinNameValuePairWithComma(fullColumnWithValueList));
        stringBuilder.append(Constants.WHERE);
        stringBuilder.append(RowUtil.getUniqueKeyName(row));
        stringBuilder.append(Constants.EQUAL);
        stringBuilder.append(ColumnUtil.getColumnValueString(row
                .getUniqueKeyValue()));

        return stringBuilder.toString();
    }
}
