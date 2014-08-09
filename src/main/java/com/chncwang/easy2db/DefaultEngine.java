package com.chncwang.easy2db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.chncwang.easy2db.parser.TableParser;
import com.chncwang.easy2db.sql.InsertBuilder;
import com.chncwang.easy2db.sql.SelectBuilder;
import com.chncwang.easy2db.sql.SqlUtil;
import com.chncwang.easy2db.sql.UpdateBuilder;
import com.chncwang.easy2db.table.util.ForeignKeyUtil;
import com.chncwang.easy2db.table.value.ForeignKeyWithValue;
import com.chncwang.easy2db.table.value.Row;
import com.google.common.base.Function;

public class DefaultEngine implements Engine {
    private static final Logger LOG = Logger.getLogger(DefaultEngine.class);

    private final TableParser mTableParser;
    private final Connection mConnection;

    public DefaultEngine(final Class<?> clazz, final Connection connection) {
        mTableParser = TableParser.getTableParser(clazz);
        mConnection = connection;
    }

    @Override
    public Object select(final Object uniqueKeyValue) throws SQLException {
        Statement statement = null;
        try {
            statement = mConnection.createStatement();
            final String select = SelectBuilder.build(
                    mTableParser.getTableDef(), uniqueKeyValue);
            LOG.info("select - select:" + select);
            final ResultSet resultSet = statement.executeQuery(select);
            if (resultSet.next()) {
                final Object result = SqlUtil.toObject(resultSet,
                        mTableParser.getClazz());
                if (resultSet.next()) {
                    throw new SQLException("More than one result! SQL:"
                            + select);
                }
                return result;
            } else {
                return null;
            }
        } finally {
            statement.close();
        }
    }

    @Override
    public void update(final Object o) throws SQLException {
        update(mTableParser.toRow(o));
    }

    @Override
    public void insert(final Object o) throws SQLException {
        insert(mTableParser.toRow(o));
    }

    @Override
    public void sendObjectToDb(final Object o) throws SQLException {
        sendRowToDb(mTableParser.toRow(o));
    }

    private void update(final Row row) throws SQLException {
        final String sql = UpdateBuilder.build(row);
        if (sql != null) {
            executeSql(row, new Function<Row, String>() {
                @Override
                public String apply(final Row input) {
                    return sql;
                }
            });
        }
    }

    private void insert(final Row row) throws SQLException {
        executeSql(row, new Function<Row, String>() {
            @Override
            public String apply(final Row row) {
                return InsertBuilder.build(row);
            }
        });
    }

    private void executeSql(final Row row, final Function<Row, String> buildSql)
            throws SQLException {
        Statement statement = null;
        try {
            statement = mConnection.createStatement();
            final String sql = buildSql.apply(row);
            LOG.info("executeSql - sql:" + sql);
            statement.execute(sql);
        } finally {
            statement.close();
        }
    }

    private void sendRowToDb(final Row row) throws SQLException {
        for (final ForeignKeyWithValue foreignKeyWithValue : row
                .getForeignKeyWithValueList()) {
            final Row foreignRow = foreignKeyWithValue.getForeignRow();
            final Class<?> clazz = foreignRow.getClazz();
            LOG.debug("sendRowToDb - clazz:" + clazz);

            final DefaultEngine engine = new DefaultEngine(clazz, mConnection);

            engine.sendRowToDb(foreignRow);

            final Object object = engine.select(foreignRow.getUniqueKeyValue()
                    .getValue());
            LOG.debug("sendRowToDb - object:" + object);

            final Row selectedForeignRow = TableParser.getTableParser(
                    object.getClass()).toRow(object);
            final Object foreignKeyPrimaryKeyValue = selectedForeignRow
                    .getPrimaryKeyValue().getValue();
            ForeignKeyUtil.setForeignKeyPrimaryKeyValue(foreignKeyWithValue,
                    foreignKeyPrimaryKeyValue);
        }

        final Object o = select(row.getUniqueKeyValue().getValue());
        if (o == null) {
            insert(row);
        } else {
            update(row);
        }
    }
}
