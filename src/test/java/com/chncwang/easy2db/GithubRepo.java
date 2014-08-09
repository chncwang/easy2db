package com.chncwang.easy2db;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.chncwang.easy2db.table.annotation.Column;
import com.chncwang.easy2db.table.annotation.Foreign;
import com.chncwang.easy2db.table.annotation.PrimaryKey;
import com.chncwang.easy2db.table.annotation.Table;
import com.chncwang.easy2db.table.annotation.UniqueKey;

@Table("github_repo")
public class GithubRepo {
    @PrimaryKey
    @Column("id")
    private String mId;

    @UniqueKey
    @Column("url")
    private String mUrl;

    @Foreign
    @Column("user_id")
    private User mUser;

    @Column("name")
    private String mName;

    @Column("star_count")
    private Integer mStarCount;

    public GithubRepo() {}

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getId() {
        return mId;
    }

    public void setId(final String id) {
        mId = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(final String url) {
        mUrl = url;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(final User user) {
        mUser = user;
    }

    public String getName() {
        return mName;
    }

    public void setName(final String name) {
        mName = name;
    }

    public Integer getStarCount() {
        return mStarCount;
    }

    public void setStarCount(final Integer starCount) {
        mStarCount = starCount;
    }
}
