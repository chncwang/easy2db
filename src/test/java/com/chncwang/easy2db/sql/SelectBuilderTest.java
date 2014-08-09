package com.chncwang.easy2db.sql;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.chncwang.easy2db.GithubRepo;
import com.chncwang.easy2db.log.LogUtil;
import com.chncwang.easy2db.parser.TableParser;

public class SelectBuilderTest {
    private static final Logger LOG = Logger.getLogger(SelectBuilderTest.class);

    @Before
    public void setUp() {
        LogUtil.init();
    }

    @Test
    public void testBuild() {
        final TableParser parser = TableParser.getTableParser(GithubRepo.class);
        final String sql = SelectBuilder.build(parser.getTableDef(), "abc");
        LOG.debug("testBuild - sql:" + sql);
    }
}
