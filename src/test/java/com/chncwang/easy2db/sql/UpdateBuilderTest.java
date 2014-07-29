package com.chncwang.easy2db.sql;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.chncwang.easy2db.GithubRepo;
import com.chncwang.easy2db.GithubRepos;
import com.chncwang.easy2db.log.LogUtil;
import com.chncwang.easy2db.parser.TableParser;
import com.chncwang.easy2db.table.value.Row;

public class UpdateBuilderTest {
    private static final Logger LOG = Logger.getLogger(UpdateBuilderTest.class);

    @Before
    public void setUp() {
        LogUtil.init();
    }

    @Test
    public void testBuilder() {
        final Row row = TableParser.newTableParser(GithubRepo.class).toRow(
                GithubRepos.fool2048());
        final String sql = UpdateBuilder.build(row);
        LOG.debug("testBuilder - sql:" + sql);
    }
}
