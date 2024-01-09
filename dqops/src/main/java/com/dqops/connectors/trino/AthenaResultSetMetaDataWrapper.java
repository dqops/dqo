package com.dqops.connectors.trino;

import com.amazon.athena.jdbc.AthenaResultSetMetaData;
import com.amazon.athena.jdbc.support.AutoUnwrap;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;


/**
 * AthenaResultSetMetaData wrapper class
 */
public class AthenaResultSetMetaDataWrapper implements ResultSetMetaData, AutoUnwrap {
    private final AthenaResultSetMetaData athenaResultSetMetaData;

    public AthenaResultSetMetaDataWrapper(AthenaResultSetMetaData athenaResultSetMetaData) {
        this.athenaResultSetMetaData = athenaResultSetMetaData;
    }

    /**
     * A wrapper method. Fixed the athena implementation providing the 0 value in case of null precission value.
     * @param column the first column is 1, the second is 2, ...
     * @return Precision
     * @throws SQLException
     */
    @Override
    public int getPrecision(int column) throws SQLException {
        try {
            return athenaResultSetMetaData.getPrecision(column);
        }
        catch(NullPointerException ex){
            return 0;
        }
    }

    /**
     * A wrapper method. Uses the custom implementation of get precision.
     * @param column the first column is 1, the second is 2, ...
     * @return Precision
     * @throws SQLException
     */
    @Override
    public int getScale(int column) throws SQLException {
        return getPrecision(column);
    }

    @Override
    public int getColumnCount() throws SQLException {
        return athenaResultSetMetaData.getColumnCount();
    }

    @Override
    public boolean isAutoIncrement(int column) throws SQLException {
        return athenaResultSetMetaData.isAutoIncrement(column);
    }

    @Override
    public boolean isCaseSensitive(int column) throws SQLException {
        return athenaResultSetMetaData.isCaseSensitive(column);
    }

    @Override
    public boolean isSearchable(int column) throws SQLException {
        return athenaResultSetMetaData.isSearchable(column);
    }

    @Override
    public boolean isCurrency(int column) throws SQLException {
        return athenaResultSetMetaData.isCurrency(column);
    }

    @Override
    public int isNullable(int column) throws SQLException {
        return athenaResultSetMetaData.isNullable(column);
    }

    @Override
    public boolean isSigned(int column) throws SQLException {
        return athenaResultSetMetaData.isSigned(column);
    }

    @Override
    public int getColumnDisplaySize(int column) throws SQLException {
        return athenaResultSetMetaData.getColumnDisplaySize(column);
    }

    @Override
    public String getColumnLabel(int column) throws SQLException {
        return athenaResultSetMetaData.getColumnLabel(column);
    }

    @Override
    public String getColumnName(int column) throws SQLException {
        return athenaResultSetMetaData.getColumnName(column);
    }

    @Override
    public String getSchemaName(int column) throws SQLException {
        return athenaResultSetMetaData.getSchemaName(column);
    }

    @Override
    public String getTableName(int column) throws SQLException {
        return athenaResultSetMetaData.getTableName(column);
    }

    @Override
    public String getCatalogName(int column) throws SQLException {
        return athenaResultSetMetaData.getCatalogName(column);
    }

    @Override
    public int getColumnType(int column) throws SQLException {
        return athenaResultSetMetaData.getColumnType(column);
    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        return athenaResultSetMetaData.getColumnTypeName(column);
    }

    @Override
    public boolean isReadOnly(int column) throws SQLException {
        return athenaResultSetMetaData.isReadOnly(column);
    }

    @Override
    public boolean isWritable(int column) throws SQLException {
        return athenaResultSetMetaData.isWritable(column);
    }

    @Override
    public boolean isDefinitelyWritable(int column) throws SQLException {
        return athenaResultSetMetaData.isDefinitelyWritable(column);
    }

    @Override
    public String getColumnClassName(int column) throws SQLException {
        return athenaResultSetMetaData.getColumnClassName(column);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return AutoUnwrap.super.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) {
        return AutoUnwrap.super.isWrapperFor(iface);
    }

}
