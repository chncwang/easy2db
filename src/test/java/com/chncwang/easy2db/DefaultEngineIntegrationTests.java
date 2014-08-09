package com.chncwang.easy2db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.chncwang.easy2db.log.LogUtil;

public class DefaultEngineIntegrationTests {
    private static final Logger LOG = Logger
            .getLogger(DefaultEngineIntegrationTests.class);

    private DefaultEngine mEngine;

    @Before
    public void setUp() throws Exception {
        LogUtil.init();
        Class.forName("com.mysql.jdbc.Driver");
        final Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/github", "wqs", "");
        mEngine = new DefaultEngine(GithubRepo.class, connection);
    }

    @Test
    public void testSelect() throws SQLException {
        final String uniqueKeyValue = "https://github.com/chncwang/fool2048";
        final GithubRepo repo = (GithubRepo) mEngine.select(uniqueKeyValue);
        LOG.debug("testSelect - repo:" + repo);
    }

    @Test
    public void testUpdate() throws SQLException {
        final GithubRepo repo = GithubRepos.fool2048();
        repo.setStarCount(10);
        mEngine.update(repo);
    }

    @Test
    public void testSendObject() throws Exception {
        mEngine.sendObjectToDb(GithubRepos.fool2048());
    }
}
