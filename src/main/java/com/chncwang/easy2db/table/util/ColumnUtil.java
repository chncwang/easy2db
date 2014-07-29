package com.chncwang.easy2db.table.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.chncwang.easy2db.Constants;
import com.chncwang.easy2db.sql.NumericTypes;
import com.chncwang.easy2db.table.value.ColumnValue;
import com.google.common.collect.Lists;

public class ColumnUtil {
    private ColumnUtil() {}

    public static String getColumnValueString(final ColumnValue columnValue) {
        return NumericTypes
                .isNumericType(columnValue.getColumnDef().getClazz()) ? columnValue
                .getValue().toString() : Constants.QUOTATION
                + columnValue.getValue() + Constants.QUOTATION;
    }

    public static String joinNamesWithComma(final List<ColumnValue> columnValues) {
        final List<String> names = Lists.newArrayList();

        for (final ColumnValue columnValue : columnValues) {
            names.add(columnValue.getColumnDef().getName());
        }

        return StringUtils.join(names.toArray(), Constants.COMMA);
    }

    public static String joinValuesWithComma(
            final List<ColumnValue> columnValues) {
        final List<String> values = Lists.newArrayList();

        for (final ColumnValue columnValue : columnValues) {
            values.add(getColumnValueString(columnValue));
        }

        return StringUtils.join(values.toArray(), Constants.COMMA);
    }

    public static String joinNameValuePairWithComma(
            final List<ColumnValue> columnValues) {
        final List<String> pairs = Lists.newArrayList();

        for (final ColumnValue columnValue : columnValues) {
            pairs.add(columnValue.getColumnDef().getName() + Constants.EQUAL
                    + getColumnValueString(columnValue));
        }

        return StringUtils.join(pairs.toArray(), Constants.COMMA);
    }
}
