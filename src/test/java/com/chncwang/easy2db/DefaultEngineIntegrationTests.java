package com.chncwang.easy2db;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Before;
import org.junit.Test;

import com.chncwang.easy2db.log.LogUtil;

public class DefaultEngineIntegrationTests {
    @Before
    public void setUp() {
        LogUtil.init();
    }

    @Test
    public void testSendObject() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        final Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/github", "wqs", "");
        final Engine<GithubRepo> engine = new DefaultEngine<GithubRepo>(
                GithubRepo.class, connection);
        engine.sendObjectToDb(GithubRepos.fool2048());
    }
}
