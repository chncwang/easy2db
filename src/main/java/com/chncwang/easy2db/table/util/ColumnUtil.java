package com.chncwang.easy2db.table.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.chncwang.easy2db.Constants;
import com.chncwang.easy2db.sql.NumericTypes;
import com.chncwang.easy2db.table.ColumnDef;
import com.chncwang.easy2db.table.value.ColumnWithValue;
import com.google.common.collect.Lists;

public class ColumnUtil {
    private ColumnUtil() {}

    public static String getColumnValueString(final ColumnWithValue columnValue) {
        return NumericTypes
                .isNumericType(columnValue.getColumnDef().getClazz()) ? columnValue
                .getValue().toString() : Constants.QUOTATION
                + columnValue.getValue() + Constants.QUOTATION;
    }

    public static String joinNamesWithCommaWithColumnDefs(
            final List<ColumnDef> columnDefs) {
        final List<String> names = Lists.newArrayListWithCapacity(columnDefs
                .size());

        for (final ColumnDef columnDef : columnDefs) {
            names.add(columnDef.getName());
        }

        return StringUtils.join(names.toArray(), Constants.COMMA);
    }

    public static String joinNamesWithComma(
            final List<ColumnWithValue> columnValues) {
        final List<String> names = Lists.newArrayListWithCapacity(columnValues
                .size());

        for (final ColumnWithValue columnValue : columnValues) {
            names.add(columnValue.getColumnDef().getName());
        }

        return StringUtils.join(names.toArray(), Constants.COMMA);
    }

    public static String joinValuesWithComma(
            final List<ColumnWithValue> columnValues) {
        final List<String> values = Lists.newArrayListWithCapacity(columnValues
                .size());

        for (final ColumnWithValue columnValue : columnValues) {
            values.add(getColumnValueString(columnValue));
        }

        return StringUtils.join(values.toArray(), Constants.COMMA);
    }

    public static String joinNameValuePairWithComma(
            final List<ColumnWithValue> columnValues) {
        final List<String> pairs = Lists.newArrayListWithCapacity(columnValues
                .size());

        for (final ColumnWithValue columnValue : columnValues) {
            pairs.add(columnValue.getColumnDef().getName() + Constants.EQUAL
                    + getColumnValueString(columnValue));
        }

        return StringUtils.join(pairs.toArray(), Constants.COMMA);
    }
}
