package com.chncwang.easy2db.parser;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.chncwang.easy2db.GithubRepo;
import com.chncwang.easy2db.GithubRepos;
import com.chncwang.easy2db.log.LogUtil;
import com.chncwang.easy2db.table.TableDef;
import com.chncwang.easy2db.table.value.Row;

public class TableParserTest {
    private static final Logger LOG = Logger.getLogger(TableParserTest.class);

    private TableParser<GithubRepo> mTableParser;

    @Before
    public void setUp() {
        LogUtil.init();
        mTableParser = TableParser.newTableParser(GithubRepo.class);
    }

    @Test
    public void testInit() {
        final TableDef tableDef = mTableParser.getTableDef();
        LOG.debug("testInit - tableDef:" + tableDef);
    }

    @Test
    public void testToRow() {
        final Row row = mTableParser.toRow(GithubRepos.fool2048());
        LOG.debug("testToRow - row:" + row);
    }
}
