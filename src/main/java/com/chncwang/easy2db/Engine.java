package com.chncwang.easy2db;

import java.sql.SQLException;

public interface Engine {
    Object select(Object uniqueKey) throws SQLException;

    void update(Object o) throws SQLException;

    void insert(Object o) throws SQLException;

    void sendObjectToDb(Object o) throws SQLException;
}
