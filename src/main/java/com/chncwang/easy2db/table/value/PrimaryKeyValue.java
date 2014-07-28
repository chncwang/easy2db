package com.chncwang.easy2db.table.value;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.chncwang.easy2db.table.PrimaryKeyDef;

public class PrimaryKeyValue {
    private final PrimaryKeyDef mPrimaryKeyDef;
    private final Object mValue;

    public PrimaryKeyValue(final PrimaryKeyDef primaryKeyDef, final Object value) {
        mPrimaryKeyDef = primaryKeyDef;
        mValue = value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

    public PrimaryKeyDef getPrimaryKeyDef() {
        return mPrimaryKeyDef;
    }

    public Object getValue() {
        return mValue;
    }
}
