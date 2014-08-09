package com.chncwang.easy2db;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.chncwang.easy2db.table.annotation.Column;
import com.chncwang.easy2db.table.annotation.PrimaryKey;
import com.chncwang.easy2db.table.annotation.Table;
import com.chncwang.easy2db.table.annotation.UniqueKey;

@Table("user")
public class User {
    @PrimaryKey
    @Column("id")
    private String mId;

    @UniqueKey
    @Column("user_name")
    private String mUserName;

    public User() {}

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getId() {
        return mId;
    }

    public void setId(final String id) {
        mId = id;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(final String userName) {
        mUserName = userName;
    }
}
