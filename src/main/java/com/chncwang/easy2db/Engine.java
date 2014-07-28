package com.chncwang.easy2db;

import java.sql.SQLException;

public interface Engine<T> {
    void sendObjectToDb(T o) throws SQLException;
}
