package com.chncwang.easy2db.sql;

import com.chncwang.easy2db.Constants;
import com.chncwang.easy2db.table.util.ColumnUtil;
import com.chncwang.easy2db.table.util.RowUtil;
import com.chncwang.easy2db.table.value.Row;

public class UpdateBuilder {
    public static String build(final Row row) {
        final StringBuilder stringBuilder = new StringBuilder("UPDATE ");
        stringBuilder.append(row.getTableName());
        stringBuilder.append(Constants.SET);
        stringBuilder.append(ColumnUtil.joinNameValuePairWithComma(row
                .getColumnValues()));
        stringBuilder.append(Constants.WHERE);
        stringBuilder.append(RowUtil.getUniqueKeyName(row));
        stringBuilder.append(Constants.EQUAL);
        stringBuilder.append(ColumnUtil.getColumnValueString(row
                .getUniqueKeyValue()));

        return stringBuilder.toString();
    }
}
