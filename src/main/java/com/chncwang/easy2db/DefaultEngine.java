package com.chncwang.easy2db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.chncwang.easy2db.parser.TableParser;
import com.chncwang.easy2db.sql.InsertBuilder;
import com.chncwang.easy2db.sql.SelectBuilder;
import com.chncwang.easy2db.sql.UpdateBuilder;
import com.chncwang.easy2db.table.value.Row;

public class DefaultEngine<T> implements Engine<T> {
    private static final Logger LOG = Logger.getLogger(DefaultEngine.class);

    private final TableParser<T> mTableParser;
    private final Connection mConnection;

    public DefaultEngine(final Class<T> clazz, final Connection connection) {
        mTableParser = TableParser.newTableParser(clazz);
        mConnection = connection;
    }

    @Override
    public void sendObjectToDb(final T o) throws SQLException {
        final Row row = mTableParser.toRow(o);
        Statement statement = null;
        try {
            statement = mConnection.createStatement();
            final String select = SelectBuilder.build(row);
            LOG.debug("sendObjectToDb - select:" + select);

            final ResultSet resultSet = statement.executeQuery(select);

            if (resultSet.next()) {
                if (resultSet.next()) {
                    throw new SQLException("More than one result! SQL:"
                            + select);
                }
                resultSet.close();

                final String update = UpdateBuilder.build(row);
                LOG.debug("sendObjectToDb 0 update:" + update);

                statement.executeUpdate(update);
            } else {
                resultSet.close();

                final String insert = InsertBuilder.build(row);
                LOG.debug("sendObjectToDb - insert:" + insert);

                statement.execute(insert);
            }
        } finally {
            statement.close();
        }
    }
}
