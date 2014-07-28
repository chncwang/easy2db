package com.chncwang.easy2db;

import com.chncwang.easy2db.table.annotation.Column;
import com.chncwang.easy2db.table.annotation.PrimaryKey;
import com.chncwang.easy2db.table.annotation.Table;
import com.chncwang.easy2db.table.annotation.UniqueKey;

@Table("github_repo")
public class GithubRepo {
    @PrimaryKey(needInsert = true)
    @Column("id")
    private String mId;

    @UniqueKey
    @Column("url")
    private String mUrl;

    @Column("name")
    private String mName;

    @Column("star_count")
    private Integer mStarCount;

    public GithubRepo() {}

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
