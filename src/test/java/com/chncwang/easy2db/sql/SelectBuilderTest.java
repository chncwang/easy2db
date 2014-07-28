package com.chncwang.easy2db.sql;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.chncwang.easy2db.GithubRepo;
import com.chncwang.easy2db.GithubRepos;
import com.chncwang.easy2db.log.LogUtil;
import com.chncwang.easy2db.parser.TableParser;
import com.chncwang.easy2db.table.value.Row;

public class SelectBuilderTest {
    private static final Logger LOG = Logger.getLogger(SelectBuilderTest.class);

    @Before
    public void setUp() {
        LogUtil.init();
    }

    @Test
    public void testBuild() {
        final Row row = TableParser.newTableParser(GithubRepo.class).toRow(
                GithubRepos.fool2048());
        final String sql = SelectBuilder.build(row);
        LOG.debug("testBuild - sql:" + sql);
    }
}
