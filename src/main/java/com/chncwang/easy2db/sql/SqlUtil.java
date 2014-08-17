package com.chncwang.easy2db.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.chncwang.easy2db.parser.TableParser;
import com.chncwang.easy2db.parser.TableParserUtil;
import com.chncwang.easy2db.table.ColumnDef;
import com.chncwang.easy2db.table.TableDef;
import com.chncwang.easy2db.table.util.TableUtil;

public class SqlUtil {
    private SqlUtil() {}

    public static Object toObject(final ResultSet resultSet,
            final Class<?> clazz) throws SQLException {
        final Object object;
        try {
            object = clazz.newInstance();
        } catch (final InstantiationException e) {
            throw new IllegalArgumentException(e);
        } catch (final IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }

        final TableParser tableParser = TableParser.getTableParser(clazz);
        final Map<String, Class<?>> classMap = TableParserUtil
                .createColumnClassMap(tableParser);

        final TableDef tableDef = tableParser.getTableDef();
        try {
            final Object primaryKeyValue = resultSet.getObject(
                    TableUtil.getPrimaryKeyName(tableDef), classMap);
            tableParser.getPrimaryKeyField().set(object, primaryKeyValue);

            final Object uniqueKeyValue = resultSet.getObject(tableDef
                    .getUniqueKeyDef().getName(), classMap);
            tableParser.getUniqueKeyField().set(object, uniqueKeyValue);

            for (int i=0; i<tableDef.getColumnDefs().size(); ++i) {
                final ColumnDef columnDef = tableDef.getColumnDefs().get(i);
                final Object columnValue = resultSet.getObject(
                        columnDef.getName(), classMap);
                tableParser.getColumnFields().get(i).set(object, columnValue);
            }
        } catch (final IllegalAccessException e) {
            throw new IllegalStateException(e);
        }

        return object;
    }
}
