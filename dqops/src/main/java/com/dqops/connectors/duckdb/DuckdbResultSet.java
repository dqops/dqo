/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.connectors.duckdb;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Map;


/**
 * Custom implementation of ResultSet interface for DuckDB.
 */
public class DuckdbResultSet implements ResultSet {

    private final ResultSet resultSet;

    public DuckdbResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    /**
     * Returns the Object.
     * If the object is of type LocalDateTime,
     * the object will be converted to
     * a type compatible with the Instant type.
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return a java.lang.Object holding the column value.
     * @throws SQLException if the columnLabel is not valid;
     * if a database access error occurs or this method is
     * called on a closed result set
     */
    @Override
    public Object getObject(int columnIndex) throws SQLException {
        Object value = resultSet.getObject(columnIndex);
        if (value instanceof OffsetDateTime) {
            OffsetDateTime offsetDateTime = (OffsetDateTime) value;
            Instant instant = offsetDateTime.toInstant();
            return Timestamp.from(instant);
        } else {
            return value;
        }
    }

    /**
     * <p>Gets the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * an {@code Object} in the Java programming language.
     *
     * <p>This method will return the value of the given column as a
     * Java object.  The type of the Java object will be the default
     * Java object type corresponding to the column's SQL type,
     * following the mapping for built-in types specified in the JDBC
     * specification. If the value is an SQL {@code NULL},
     * the driver returns a Java {@code null}.
     * <p>
     * This method may also be used to read database-specific
     * abstract data types.
     * <p>
     * In the JDBC 2.0 API, the behavior of the method
     * {@code getObject} is extended to materialize
     * data of SQL user-defined types.  When a column contains
     * a structured or distinct value, the behavior of this method is as
     * if it were a call to: {@code getObject(columnIndex,
     * this.getStatement().getConnection().getTypeMap())}.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return a {@code java.lang.Object} holding the column value
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public Object getObject(String columnLabel) throws SQLException {
        return resultSet.getObject(columnLabel);
    }

    /**
     * Maps the given {@code ResultSet} column label to its
     * {@code ResultSet} column index.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return the column index of the given column name
     * @throws SQLException if the {@code ResultSet} object
     *                      does not contain a column labeled {@code columnLabel}, a database access error occurs
     *                      or this method is called on a closed result set
     */
    @Override
    public int findColumn(String columnLabel) throws SQLException {
        return resultSet.findColumn(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a
     * {@code java.io.Reader} object.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return a {@code java.io.Reader} object that contains the column
     * value; if the value is SQL {@code NULL}, the value returned is
     * {@code null} in the Java programming language.
     * @throws SQLException if the columnIndex is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     * @since 1.2
     */
    @Override
    public Reader getCharacterStream(int columnIndex) throws SQLException {
        return resultSet.getCharacterStream(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a
     * {@code java.io.Reader} object.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return a {@code java.io.Reader} object that contains the column
     * value; if the value is SQL {@code NULL}, the value returned is
     * {@code null} in the Java programming language
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     * @since 1.2
     */
    @Override
    public Reader getCharacterStream(String columnLabel) throws SQLException {
        return resultSet.getCharacterStream(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a
     * {@code java.math.BigDecimal} with full precision.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value (full precision);
     * if the value is SQL {@code NULL}, the value returned is
     * {@code null} in the Java programming language.
     * @throws SQLException if the columnIndex is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     * @since 1.2
     */
    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        return resultSet.getBigDecimal(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a
     * {@code java.math.BigDecimal} with full precision.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return the column value (full precision);
     * if the value is SQL {@code NULL}, the value returned is
     * {@code null} in the Java programming language.
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     * @since 1.2
     */
    @Override
    public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
        return resultSet.getBigDecimal(columnLabel);
    }

    /**
     * Retrieves whether the cursor is before the first row in
     * this {@code ResultSet} object.
     * <p>
     * <strong>Note:</strong>Support for the {@code isBeforeFirst} method
     * is optional for {@code ResultSet}s with a result
     * set type of {@code TYPE_FORWARD_ONLY}
     *
     * @return {@code true} if the cursor is before the first row;
     * {@code false} if the cursor is at any other position or the
     * result set contains no rows
     * @throws SQLException                    if a database access error occurs or this method is
     *                                         called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public boolean isBeforeFirst() throws SQLException {
        return resultSet.isBeforeFirst();
    }

    /**
     * Retrieves whether the cursor is after the last row in
     * this {@code ResultSet} object.
     * <p>
     * <strong>Note:</strong>Support for the {@code isAfterLast} method
     * is optional for {@code ResultSet}s with a result
     * set type of {@code TYPE_FORWARD_ONLY}
     *
     * @return {@code true} if the cursor is after the last row;
     * {@code false} if the cursor is at any other position or the
     * result set contains no rows
     * @throws SQLException                    if a database access error occurs or this method is
     *                                         called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public boolean isAfterLast() throws SQLException {
        return resultSet.isAfterLast();
    }

    /**
     * Retrieves whether the cursor is on the first row of
     * this {@code ResultSet} object.
     * <p>
     * <strong>Note:</strong>Support for the {@code isFirst} method
     * is optional for {@code ResultSet}s with a result
     * set type of {@code TYPE_FORWARD_ONLY}
     *
     * @return {@code true} if the cursor is on the first row;
     * {@code false} otherwise
     * @throws SQLException                    if a database access error occurs or this method is
     *                                         called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public boolean isFirst() throws SQLException {
        return resultSet.isFirst();
    }

    /**
     * Retrieves whether the cursor is on the last row of
     * this {@code ResultSet} object.
     * <strong>Note:</strong> Calling the method {@code isLast} may be expensive
     * because the JDBC driver
     * might need to fetch ahead one row in order to determine
     * whether the current row is the last row in the result set.
     * <p>
     * <strong>Note:</strong> Support for the {@code isLast} method
     * is optional for {@code ResultSet}s with a result
     * set type of {@code TYPE_FORWARD_ONLY}
     *
     * @return {@code true} if the cursor is on the last row;
     * {@code false} otherwise
     * @throws SQLException                    if a database access error occurs or this method is
     *                                         called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public boolean isLast() throws SQLException {
        return resultSet.isLast();
    }

    /**
     * Moves the cursor to the front of
     * this {@code ResultSet} object, just before the
     * first row. This method has no effect if the result set contains no rows.
     *
     * @throws SQLException                    if a database access error
     *                                         occurs; this method is called on a closed result set or the
     *                                         result set type is {@code TYPE_FORWARD_ONLY}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void beforeFirst() throws SQLException {
        resultSet.beforeFirst();
    }

    /**
     * Moves the cursor to the end of
     * this {@code ResultSet} object, just after the
     * last row. This method has no effect if the result set contains no rows.
     *
     * @throws SQLException                    if a database access error
     *                                         occurs; this method is called on a closed result set
     *                                         or the result set type is {@code TYPE_FORWARD_ONLY}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void afterLast() throws SQLException {
        resultSet.afterLast();
    }

    /**
     * Moves the cursor to the first row in
     * this {@code ResultSet} object.
     *
     * @return {@code true} if the cursor is on a valid row;
     * {@code false} if there are no rows in the result set
     * @throws SQLException                    if a database access error
     *                                         occurs; this method is called on a closed result set
     *                                         or the result set type is {@code TYPE_FORWARD_ONLY}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public boolean first() throws SQLException {
        return resultSet.first();
    }

    /**
     * Moves the cursor to the last row in
     * this {@code ResultSet} object.
     *
     * @return {@code true} if the cursor is on a valid row;
     * {@code false} if there are no rows in the result set
     * @throws SQLException                    if a database access error
     *                                         occurs; this method is called on a closed result set
     *                                         or the result set type is {@code TYPE_FORWARD_ONLY}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public boolean last() throws SQLException {
        return resultSet.last();
    }

    /**
     * Retrieves the current row number.  The first row is number 1, the
     * second number 2, and so on.
     * <p>
     * <strong>Note:</strong>Support for the {@code getRow} method
     * is optional for {@code ResultSet}s with a result
     * set type of {@code TYPE_FORWARD_ONLY}
     *
     * @return the current row number; {@code 0} if there is no current row
     * @throws SQLException                    if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public int getRow() throws SQLException {
        return resultSet.getRow();
    }

    /**
     * Moves the cursor to the given row number in
     * this {@code ResultSet} object.
     *
     * <p>If the row number is positive, the cursor moves to
     * the given row number with respect to the
     * beginning of the result set.  The first row is row 1, the second
     * is row 2, and so on.
     *
     * <p>If the given row number is negative, the cursor moves to
     * an absolute row position with respect to
     * the end of the result set.  For example, calling the method
     * {@code absolute(-1)} positions the
     * cursor on the last row; calling the method {@code absolute(-2)}
     * moves the cursor to the next-to-last row, and so on.
     *
     * <p>If the row number specified is zero, the cursor is moved to
     * before the first row.
     *
     * <p>An attempt to position the cursor beyond the first/last row in
     * the result set leaves the cursor before the first row or after
     * the last row.
     *
     * <p><B>Note:</B> Calling {@code absolute(1)} is the same
     * as calling {@code first()}. Calling {@code absolute(-1)}
     * is the same as calling {@code last()}.
     *
     * @param row the number of the row to which the cursor should move.
     *            A value of zero indicates that the cursor will be positioned
     *            before the first row; a positive number indicates the row number
     *            counting from the beginning of the result set; a negative number
     *            indicates the row number counting from the end of the result set
     * @return {@code true} if the cursor is moved to a position in this
     * {@code ResultSet} object;
     * {@code false} if the cursor is before the first row or after the
     * last row
     * @throws SQLException                    if a database access error
     *                                         occurs; this method is called on a closed result set
     *                                         or the result set type is {@code TYPE_FORWARD_ONLY}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public boolean absolute(int row) throws SQLException {
        return resultSet.absolute(row);
    }

    /**
     * Moves the cursor a relative number of rows, either positive or negative.
     * Attempting to move beyond the first/last row in the
     * result set positions the cursor before/after the
     * the first/last row. Calling {@code relative(0)} is valid, but does
     * not change the cursor position.
     *
     * <p>Note: Calling the method {@code relative(1)}
     * is identical to calling the method {@code next()} and
     * calling the method {@code relative(-1)} is identical
     * to calling the method {@code previous()}.
     *
     * @param rows an {@code int} specifying the number of rows to
     *             move from the current row; a positive number moves the cursor
     *             forward; a negative number moves the cursor backward
     * @return {@code true} if the cursor is on a row;
     * {@code false} otherwise
     * @throws SQLException                    if a database access error occurs;  this method
     *                                         is called on a closed result set or the result set type is
     *                                         {@code TYPE_FORWARD_ONLY}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public boolean relative(int rows) throws SQLException {
        return resultSet.relative(rows);
    }

    /**
     * Moves the cursor to the previous row in this
     * {@code ResultSet} object.
     * <p>
     * When a call to the {@code previous} method returns {@code false},
     * the cursor is positioned before the first row.  Any invocation of a
     * {@code ResultSet} method which requires a current row will result in a
     * {@code SQLException} being thrown.
     * <p>
     * If an input stream is open for the current row, a call to the method
     * {@code previous} will implicitly close it.  A {@code ResultSet}
     * object's warning change is cleared when a new row is read.
     *
     * @return {@code true} if the cursor is now positioned on a valid row;
     * {@code false} if the cursor is positioned before the first row
     * @throws SQLException                    if a database access error
     *                                         occurs; this method is called on a closed result set
     *                                         or the result set type is {@code TYPE_FORWARD_ONLY}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public boolean previous() throws SQLException {
        return resultSet.previous();
    }

    /**
     * Gives a hint as to the direction in which the rows in this
     * {@code ResultSet} object will be processed.
     * The initial value is determined by the
     * {@code Statement} object
     * that produced this {@code ResultSet} object.
     * The fetch direction may be changed at any time.
     *
     * @param direction an {@code int} specifying the suggested
     *                  fetch direction; one of {@code ResultSet.FETCH_FORWARD},
     *                  {@code ResultSet.FETCH_REVERSE}, or
     *                  {@code ResultSet.FETCH_UNKNOWN}
     * @throws SQLException if a database access error occurs; this
     *                      method is called on a closed result set or
     *                      the result set type is {@code TYPE_FORWARD_ONLY} and the fetch
     *                      direction is not {@code FETCH_FORWARD}
     * @see Statement#setFetchDirection
     * @see #getFetchDirection
     * @since 1.2
     */
    @Override
    public void setFetchDirection(int direction) throws SQLException {
        resultSet.setFetchDirection(direction);
    }

    /**
     * Retrieves the fetch direction for this
     * {@code ResultSet} object.
     *
     * @return the current fetch direction for this {@code ResultSet} object
     * @throws SQLException if a database access error occurs
     *                      or this method is called on a closed result set
     * @see #setFetchDirection
     * @since 1.2
     */
    @Override
    public int getFetchDirection() throws SQLException {
        return resultSet.getFetchDirection();
    }

    /**
     * Gives the JDBC driver a hint as to the number of rows that should
     * be fetched from the database when more rows are needed for this
     * {@code ResultSet} object.
     * If the fetch size specified is zero, the JDBC driver
     * ignores the value and is free to make its own best guess as to what
     * the fetch size should be.  The default value is set by the
     * {@code Statement} object
     * that created the result set.  The fetch size may be changed at any time.
     *
     * @param rows the number of rows to fetch
     * @throws SQLException if a database access error occurs; this method
     *                      is called on a closed result set or the
     *                      condition {@code rows >= 0} is not satisfied
     * @see #getFetchSize
     * @since 1.2
     */
    @Override
    public void setFetchSize(int rows) throws SQLException {
        resultSet.setFetchSize(rows);
    }

    /**
     * Retrieves the fetch size for this
     * {@code ResultSet} object.
     *
     * @return the current fetch size for this {@code ResultSet} object
     * @throws SQLException if a database access error occurs
     *                      or this method is called on a closed result set
     * @see #setFetchSize
     * @since 1.2
     */
    @Override
    public int getFetchSize() throws SQLException {
        return resultSet.getFetchSize();
    }

    /**
     * Retrieves the type of this {@code ResultSet} object.
     * The type is determined by the {@code Statement} object
     * that created the result set.
     *
     * @return {@code ResultSet.TYPE_FORWARD_ONLY},
     * {@code ResultSet.TYPE_SCROLL_INSENSITIVE},
     * or {@code ResultSet.TYPE_SCROLL_SENSITIVE}
     * @throws SQLException if a database access error occurs
     *                      or this method is called on a closed result set
     * @since 1.2
     */
    @Override
    public int getType() throws SQLException {
        return resultSet.getType();
    }

    /**
     * Retrieves the concurrency mode of this {@code ResultSet} object.
     * The concurrency used is determined by the
     * {@code Statement} object that created the result set.
     *
     * @return the concurrency type, either
     * {@code ResultSet.CONCUR_READ_ONLY}
     * or {@code ResultSet.CONCUR_UPDATABLE}
     * @throws SQLException if a database access error occurs
     *                      or this method is called on a closed result set
     * @since 1.2
     */
    @Override
    public int getConcurrency() throws SQLException {
        return resultSet.getConcurrency();
    }

    /**
     * Retrieves whether the current row has been updated.  The value returned
     * depends on whether or not the result set can detect updates.
     * <p>
     * <strong>Note:</strong> Support for the {@code rowUpdated} method is optional with a result set
     * concurrency of {@code CONCUR_READ_ONLY}
     *
     * @return {@code true} if the current row is detected to
     * have been visibly updated by the owner or another; {@code false} otherwise
     * @throws SQLException                    if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see DatabaseMetaData#updatesAreDetected
     * @since 1.2
     */
    @Override
    public boolean rowUpdated() throws SQLException {
        return resultSet.rowUpdated();
    }

    /**
     * Retrieves whether the current row has had an insertion.
     * The value returned depends on whether or not this
     * {@code ResultSet} object can detect visible inserts.
     * <p>
     * <strong>Note:</strong> Support for the {@code rowInserted} method is optional with a result set
     * concurrency of {@code CONCUR_READ_ONLY}
     *
     * @return {@code true} if the current row is detected to
     * have been inserted; {@code false} otherwise
     * @throws SQLException                    if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see DatabaseMetaData#insertsAreDetected
     * @since 1.2
     */
    @Override
    public boolean rowInserted() throws SQLException {
        return resultSet.rowInserted();
    }

    /**
     * Retrieves whether a row has been deleted.  A deleted row may leave
     * a visible "hole" in a result set.  This method can be used to
     * detect holes in a result set.  The value returned depends on whether
     * or not this {@code ResultSet} object can detect deletions.
     * <p>
     * <strong>Note:</strong> Support for the {@code rowDeleted} method is optional with a result set
     * concurrency of {@code CONCUR_READ_ONLY}
     *
     * @return {@code true} if the current row is detected to
     * have been deleted by the owner or another; {@code false} otherwise
     * @throws SQLException                    if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see DatabaseMetaData#deletesAreDetected
     * @since 1.2
     */
    @Override
    public boolean rowDeleted() throws SQLException {
        return resultSet.rowDeleted();
    }

    /**
     * Updates the designated column with a {@code null} value.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow}
     * or {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateNull(int columnIndex) throws SQLException {
        resultSet.updateNull(columnIndex);
    }

    /**
     * Updates the designated column with a {@code boolean} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateBoolean(int columnIndex, boolean x) throws SQLException {
        resultSet.updateBoolean(columnIndex, x);
    }

    /**
     * Updates the designated column with a {@code byte} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateByte(int columnIndex, byte x) throws SQLException {
        resultSet.updateByte(columnIndex, x);
    }

    /**
     * Updates the designated column with a {@code short} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateShort(int columnIndex, short x) throws SQLException {
        resultSet.updateShort(columnIndex, x);
    }

    /**
     * Updates the designated column with an {@code int} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateInt(int columnIndex, int x) throws SQLException {
        resultSet.updateInt(columnIndex, x);
    }

    /**
     * Updates the designated column with a {@code long} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateLong(int columnIndex, long x) throws SQLException {
        resultSet.updateLong(columnIndex, x);
    }

    /**
     * Updates the designated column with a {@code float} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateFloat(int columnIndex, float x) throws SQLException {
        resultSet.updateFloat(columnIndex, x);
    }

    /**
     * Updates the designated column with a {@code double} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateDouble(int columnIndex, double x) throws SQLException {
        resultSet.updateDouble(columnIndex, x);
    }

    /**
     * Updates the designated column with a {@code java.math.BigDecimal}
     * value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
        resultSet.updateBigDecimal(columnIndex, x);
    }

    /**
     * Updates the designated column with a {@code String} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateString(int columnIndex, String x) throws SQLException {
        resultSet.updateString(columnIndex, x);
    }

    /**
     * Updates the designated column with a {@code byte} array value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateBytes(int columnIndex, byte[] x) throws SQLException {
        resultSet.updateBytes(columnIndex, x);
    }

    /**
     * Updates the designated column with a {@code java.sql.Date} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateDate(int columnIndex, Date x) throws SQLException {
        resultSet.updateDate(columnIndex, x);
    }

    /**
     * Updates the designated column with a {@code java.sql.Time} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateTime(int columnIndex, Time x) throws SQLException {
        resultSet.updateTime(columnIndex, x);
    }

    /**
     * Updates the designated column with a {@code java.sql.Timestamp}
     * value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
        resultSet.updateTimestamp(columnIndex, x);
    }

    /**
     * Updates the designated column with an ascii stream value, which will have
     * the specified number of bytes.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @param length      the length of the stream
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
        resultSet.updateAsciiStream(columnIndex, x, length);
    }

    /**
     * Updates the designated column with a binary stream value, which will have
     * the specified number of bytes.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @param length      the length of the stream
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
        resultSet.updateBinaryStream(columnIndex, x, length);
    }

    /**
     * Updates the designated column with a character stream value, which will have
     * the specified number of bytes.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @param length      the length of the stream
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
        resultSet.updateCharacterStream(columnIndex, x, length);
    }

    /**
     * Updates the designated column with an {@code Object} value.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     * <p>
     * If the second argument is an {@code InputStream} then the stream must contain
     * the number of bytes specified by scaleOrLength.  If the second argument is a
     * {@code Reader} then the reader must contain the number of characters specified
     * by scaleOrLength. If these conditions are not true the driver will generate a
     * {@code SQLException} when the statement is executed.
     *
     * @param columnIndex   the first column is 1, the second is 2, ...
     * @param x             the new column value
     * @param scaleOrLength for an object of {@code java.math.BigDecimal} ,
     *                      this is the number of digits after the decimal point. For
     *                      Java Object types {@code InputStream} and {@code Reader},
     *                      this is the length
     *                      of the data in the stream or reader.  For all other types,
     *                      this value will be ignored.
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
        resultSet.updateObject(columnIndex, x, scaleOrLength);
    }

    /**
     * Updates the designated column with an {@code Object} value.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateObject(int columnIndex, Object x) throws SQLException {
        resultSet.updateObject(columnIndex, x);
    }

    /**
     * Updates the designated column with a {@code null} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateNull(String columnLabel) throws SQLException {
        resultSet.updateNull(columnLabel);
    }

    /**
     * Updates the designated column with a {@code boolean} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateBoolean(String columnLabel, boolean x) throws SQLException {
        resultSet.updateBoolean(columnLabel, x);
    }

    /**
     * Updates the designated column with a {@code byte} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateByte(String columnLabel, byte x) throws SQLException {
        resultSet.updateByte(columnLabel, x);
    }

    /**
     * Updates the designated column with a {@code short} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateShort(String columnLabel, short x) throws SQLException {
        resultSet.updateShort(columnLabel, x);
    }

    /**
     * Updates the designated column with an {@code int} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateInt(String columnLabel, int x) throws SQLException {
        resultSet.updateInt(columnLabel, x);
    }

    /**
     * Updates the designated column with a {@code long} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateLong(String columnLabel, long x) throws SQLException {
        resultSet.updateLong(columnLabel, x);
    }

    /**
     * Updates the designated column with a {@code float} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateFloat(String columnLabel, float x) throws SQLException {
        resultSet.updateFloat(columnLabel, x);
    }

    /**
     * Updates the designated column with a {@code double} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateDouble(String columnLabel, double x) throws SQLException {
        resultSet.updateDouble(columnLabel, x);
    }

    /**
     * Updates the designated column with a {@code java.sql.BigDecimal}
     * value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
        resultSet.updateBigDecimal(columnLabel, x);
    }

    /**
     * Updates the designated column with a {@code String} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateString(String columnLabel, String x) throws SQLException {
        resultSet.updateString(columnLabel, x);
    }

    /**
     * Updates the designated column with a byte array value.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow}
     * or {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateBytes(String columnLabel, byte[] x) throws SQLException {
        resultSet.updateBytes(columnLabel, x);
    }

    /**
     * Updates the designated column with a {@code java.sql.Date} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateDate(String columnLabel, Date x) throws SQLException {
        resultSet.updateDate(columnLabel, x);
    }

    /**
     * Updates the designated column with a {@code java.sql.Time} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateTime(String columnLabel, Time x) throws SQLException {
        resultSet.updateTime(columnLabel, x);
    }

    /**
     * Updates the designated column with a {@code java.sql.Timestamp}
     * value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
        resultSet.updateTimestamp(columnLabel, x);
    }

    /**
     * Updates the designated column with an ascii stream value, which will have
     * the specified number of bytes.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @param length      the length of the stream
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
        resultSet.updateAsciiStream(columnLabel, x, length);
    }

    /**
     * Updates the designated column with a binary stream value, which will have
     * the specified number of bytes.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @param length      the length of the stream
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
        resultSet.updateBinaryStream(columnLabel, x, length);
    }

    /**
     * Updates the designated column with a character stream value, which will have
     * the specified number of bytes.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param reader      the {@code java.io.Reader} object containing
     *                    the new column value
     * @param length      the length of the stream
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
        resultSet.updateCharacterStream(columnLabel, reader, length);
    }

    /**
     * Updates the designated column with an {@code Object} value.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     * <p>
     * If the second argument is an {@code InputStream} then the stream must contain
     * the number of bytes specified by scaleOrLength.  If the second argument is a
     * {@code Reader} then the reader must contain the number of characters specified
     * by scaleOrLength. If these conditions are not true the driver will generate a
     * {@code SQLException} when the statement is executed.
     *
     * @param columnLabel   the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x             the new column value
     * @param scaleOrLength for an object of {@code java.math.BigDecimal} ,
     *                      this is the number of digits after the decimal point. For
     *                      Java Object types {@code InputStream} and {@code Reader},
     *                      this is the length
     *                      of the data in the stream or reader.  For all other types,
     *                      this value will be ignored.
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
        resultSet.updateObject(columnLabel, x, scaleOrLength);
    }

    /**
     * Updates the designated column with an {@code Object} value.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateObject(String columnLabel, Object x) throws SQLException {
        resultSet.updateObject(columnLabel, x);
    }

    /**
     * Inserts the contents of the insert row into this
     * {@code ResultSet} object and into the database.
     * The cursor must be on the insert row when this method is called.
     *
     * @throws SQLException                    if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY},
     *                                         this method is called on a closed result set,
     *                                         if this method is called when the cursor is not on the insert row,
     *                                         or if not all of non-nullable columns in
     *                                         the insert row have been given a non-null value
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void insertRow() throws SQLException {
        resultSet.insertRow();
    }

    /**
     * Updates the underlying database with the new contents of the
     * current row of this {@code ResultSet} object.
     * This method cannot be called when the cursor is on the insert row.
     *
     * @throws SQLException                    if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY};
     *                                         this method is called on a closed result set or
     *                                         if this method is called when the cursor is on the insert row
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void updateRow() throws SQLException {
        resultSet.updateRow();
    }

    /**
     * Deletes the current row from this {@code ResultSet} object
     * and from the underlying database.  This method cannot be called when
     * the cursor is on the insert row.
     *
     * @throws SQLException                    if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY};
     *                                         this method is called on a closed result set
     *                                         or if this method is called when the cursor is on the insert row
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void deleteRow() throws SQLException {
        resultSet.deleteRow();
    }

    /**
     * Refreshes the current row with its most recent value in
     * the database.  This method cannot be called when
     * the cursor is on the insert row.
     *
     * <P>The {@code refreshRow} method provides a way for an
     * application to
     * explicitly tell the JDBC driver to refetch a row(s) from the
     * database.  An application may want to call {@code refreshRow} when
     * caching or prefetching is being done by the JDBC driver to
     * fetch the latest value of a row from the database.  The JDBC driver
     * may actually refresh multiple rows at once if the fetch size is
     * greater than one.
     *
     * <P> All values are refetched subject to the transaction isolation
     * level and cursor sensitivity.  If {@code refreshRow} is called after
     * calling an updater method, but before calling
     * the method {@code updateRow}, then the
     * updates made to the row are lost.  Calling the method
     * {@code refreshRow} frequently will likely slow performance.
     *
     * @throws SQLException                    if a database access error
     *                                         occurs; this method is called on a closed result set;
     *                                         the result set type is {@code TYPE_FORWARD_ONLY} or if this
     *                                         method is called when the cursor is on the insert row
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method or this method is not supported for the specified result
     *                                         set type and result set concurrency.
     * @since 1.2
     */
    @Override
    public void refreshRow() throws SQLException {
        resultSet.refreshRow();
    }

    /**
     * Cancels the updates made to the current row in this
     * {@code ResultSet} object.
     * This method may be called after calling an
     * updater method(s) and before calling
     * the method {@code updateRow} to roll back
     * the updates made to a row.  If no updates have been made or
     * {@code updateRow} has already been called, this method has no
     * effect.
     *
     * @throws SQLException                    if a database access error
     *                                         occurs; this method is called on a closed result set;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or if this method is called when the cursor is
     *                                         on the insert row
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void cancelRowUpdates() throws SQLException {
        resultSet.cancelRowUpdates();
    }

    /**
     * Moves the cursor to the insert row.  The current cursor position is
     * remembered while the cursor is positioned on the insert row.
     * <p>
     * The insert row is a special row associated with an updatable
     * result set.  It is essentially a buffer where a new row may
     * be constructed by calling the updater methods prior to
     * inserting the row into the result set.
     * <p>
     * Only the updater, getter,
     * and {@code insertRow} methods may be
     * called when the cursor is on the insert row.  All of the columns in
     * a result set must be given a value each time this method is
     * called before calling {@code insertRow}.
     * An updater method must be called before a
     * getter method can be called on a column value.
     *
     * @throws SQLException                    if a database access error occurs; this
     *                                         method is called on a closed result set
     *                                         or the result set concurrency is {@code CONCUR_READ_ONLY}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void moveToInsertRow() throws SQLException {
        resultSet.moveToInsertRow();
    }

    /**
     * Moves the cursor to the remembered cursor position, usually the
     * current row.  This method has no effect if the cursor is not on
     * the insert row.
     *
     * @throws SQLException                    if a database access error occurs; this
     *                                         method is called on a closed result set
     *                                         or the result set concurrency is {@code CONCUR_READ_ONLY}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public void moveToCurrentRow() throws SQLException {
        resultSet.moveToCurrentRow();
    }

    /**
     * Retrieves the {@code Statement} object that produced this
     * {@code ResultSet} object.
     * If the result set was generated some other way, such as by a
     * {@code DatabaseMetaData} method, this method  may return
     * {@code null}.
     *
     * @return the {@code Statement} object that produced
     * this {@code ResultSet} object or {@code null}
     * if the result set was produced some other way
     * @throws SQLException if a database access error occurs
     *                      or this method is called on a closed result set
     * @since 1.2
     */
    @Override
    public Statement getStatement() throws SQLException {
        return resultSet.getStatement();
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as an {@code Object}
     * in the Java programming language.
     * If the value is an SQL {@code NULL},
     * the driver returns a Java {@code null}.
     * This method uses the given {@code Map} object
     * for the custom mapping of the
     * SQL structured or distinct type that is being retrieved.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param map         a {@code java.util.Map} object that contains the mapping
     *                    from SQL type names to classes in the Java programming language
     * @return an {@code Object} in the Java programming language
     * representing the SQL value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
        return resultSet.getObject(columnIndex, map);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a {@code Ref} object
     * in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return a {@code Ref} object representing an SQL {@code REF}
     * value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public Ref getRef(int columnIndex) throws SQLException {
        return resultSet.getRef(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a {@code Blob} object
     * in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return a {@code Blob} object representing the SQL
     * {@code BLOB} value in the specified column
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public Blob getBlob(int columnIndex) throws SQLException {
        return resultSet.getBlob(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a {@code Clob} object
     * in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return a {@code Clob} object representing the SQL
     * {@code CLOB} value in the specified column
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public Clob getClob(int columnIndex) throws SQLException {
        return resultSet.getClob(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as an {@code Array} object
     * in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return an {@code Array} object representing the SQL
     * {@code ARRAY} value in the specified column
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public Array getArray(int columnIndex) throws SQLException {
        return resultSet.getArray(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as an {@code Object}
     * in the Java programming language.
     * If the value is an SQL {@code NULL},
     * the driver returns a Java {@code null}.
     * This method uses the specified {@code Map} object for
     * custom mapping if appropriate.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param map         a {@code java.util.Map} object that contains the mapping
     *                    from SQL type names to classes in the Java programming language
     * @return an {@code Object} representing the SQL value in the
     * specified column
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
        return resultSet.getObject(columnLabel, map);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a {@code Ref} object
     * in the Java programming language.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return a {@code Ref} object representing the SQL {@code REF}
     * value in the specified column
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public Ref getRef(String columnLabel) throws SQLException {
        return resultSet.getRef(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a {@code Blob} object
     * in the Java programming language.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return a {@code Blob} object representing the SQL {@code BLOB}
     * value in the specified column
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public Blob getBlob(String columnLabel) throws SQLException {
        return resultSet.getBlob(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a {@code Clob} object
     * in the Java programming language.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return a {@code Clob} object representing the SQL {@code CLOB}
     * value in the specified column
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public Clob getClob(String columnLabel) throws SQLException {
        return resultSet.getClob(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as an {@code Array} object
     * in the Java programming language.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return an {@code Array} object representing the SQL {@code ARRAY} value in
     * the specified column
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    @Override
    public Array getArray(String columnLabel) throws SQLException {
        return resultSet.getArray(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a {@code java.sql.Date} object
     * in the Java programming language.
     * This method uses the given calendar to construct an appropriate millisecond
     * value for the date if the underlying database does not store
     * timezone information.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param cal         the {@code java.util.Calendar} object
     *                    to use in constructing the date
     * @return the column value as a {@code java.sql.Date} object;
     * if the value is SQL {@code NULL},
     * the value returned is {@code null} in the Java programming language
     * @throws SQLException if the columnIndex is not valid;
     *                      if a database access error occurs
     *                      or this method is called on a closed result set
     * @since 1.2
     */
    @Override
    public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        return resultSet.getDate(columnIndex, cal);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a {@code java.sql.Date} object
     * in the Java programming language.
     * This method uses the given calendar to construct an appropriate millisecond
     * value for the date if the underlying database does not store
     * timezone information.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param cal         the {@code java.util.Calendar} object
     *                    to use in constructing the date
     * @return the column value as a {@code java.sql.Date} object;
     * if the value is SQL {@code NULL},
     * the value returned is {@code null} in the Java programming language
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs
     *                      or this method is called on a closed result set
     * @since 1.2
     */
    @Override
    public Date getDate(String columnLabel, Calendar cal) throws SQLException {
        return resultSet.getDate(columnLabel, cal);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a {@code java.sql.Time} object
     * in the Java programming language.
     * This method uses the given calendar to construct an appropriate millisecond
     * value for the time if the underlying database does not store
     * timezone information.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param cal         the {@code java.util.Calendar} object
     *                    to use in constructing the time
     * @return the column value as a {@code java.sql.Time} object;
     * if the value is SQL {@code NULL},
     * the value returned is {@code null} in the Java programming language
     * @throws SQLException if the columnIndex is not valid;
     *                      if a database access error occurs
     *                      or this method is called on a closed result set
     * @since 1.2
     */
    @Override
    public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        return resultSet.getTime(columnIndex, cal);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a {@code java.sql.Time} object
     * in the Java programming language.
     * This method uses the given calendar to construct an appropriate millisecond
     * value for the time if the underlying database does not store
     * timezone information.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param cal         the {@code java.util.Calendar} object
     *                    to use in constructing the time
     * @return the column value as a {@code java.sql.Time} object;
     * if the value is SQL {@code NULL},
     * the value returned is {@code null} in the Java programming language
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs
     *                      or this method is called on a closed result set
     * @since 1.2
     */
    @Override
    public Time getTime(String columnLabel, Calendar cal) throws SQLException {
        return resultSet.getTime(columnLabel, cal);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a {@code java.sql.Timestamp} object
     * in the Java programming language.
     * This method uses the given calendar to construct an appropriate millisecond
     * value for the timestamp if the underlying database does not store
     * timezone information.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param cal         the {@code java.util.Calendar} object
     *                    to use in constructing the timestamp
     * @return the column value as a {@code java.sql.Timestamp} object;
     * if the value is SQL {@code NULL},
     * the value returned is {@code null} in the Java programming language
     * @throws SQLException if the columnIndex is not valid;
     *                      if a database access error occurs
     *                      or this method is called on a closed result set
     * @since 1.2
     */
    @Override
    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
        return resultSet.getTimestamp(columnIndex, cal);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a {@code java.sql.Timestamp} object
     * in the Java programming language.
     * This method uses the given calendar to construct an appropriate millisecond
     * value for the timestamp if the underlying database does not store
     * timezone information.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param cal         the {@code java.util.Calendar} object
     *                    to use in constructing the date
     * @return the column value as a {@code java.sql.Timestamp} object;
     * if the value is SQL {@code NULL},
     * the value returned is {@code null} in the Java programming language
     * @throws SQLException if the columnLabel is not valid or
     *                      if a database access error occurs
     *                      or this method is called on a closed result set
     * @since 1.2
     */
    @Override
    public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
        return resultSet.getTimestamp(columnLabel, cal);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a {@code java.net.URL}
     * object in the Java programming language.
     *
     * @param columnIndex the index of the column 1 is the first, 2 is the second,...
     * @return the column value as a {@code java.net.URL} object;
     * if the value is SQL {@code NULL},
     * the value returned is {@code null} in the Java programming language
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs; this method
     *                                         is called on a closed result set or if a URL is malformed
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.4
     */
    @Override
    public URL getURL(int columnIndex) throws SQLException {
        return resultSet.getURL(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a {@code java.net.URL}
     * object in the Java programming language.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return the column value as a {@code java.net.URL} object;
     * if the value is SQL {@code NULL},
     * the value returned is {@code null} in the Java programming language
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs; this method
     *                                         is called on a closed result set or if a URL is malformed
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.4
     */
    @Override
    public URL getURL(String columnLabel) throws SQLException {
        return resultSet.getURL(columnLabel);
    }

    /**
     * Updates the designated column with a {@code java.sql.Ref} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.4
     */
    @Override
    public void updateRef(int columnIndex, Ref x) throws SQLException {
        resultSet.updateRef(columnIndex, x);
    }

    /**
     * Updates the designated column with a {@code java.sql.Ref} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.4
     */
    @Override
    public void updateRef(String columnLabel, Ref x) throws SQLException {
        resultSet.updateRef(columnLabel, x);
    }

    /**
     * Updates the designated column with a {@code java.sql.Blob} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.4
     */
    @Override
    public void updateBlob(int columnIndex, Blob x) throws SQLException {
        resultSet.updateBlob(columnIndex, x);
    }

    /**
     * Updates the designated column with a {@code java.sql.Blob} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.4
     */
    @Override
    public void updateBlob(String columnLabel, Blob x) throws SQLException {
        resultSet.updateBlob(columnLabel, x);
    }

    /**
     * Updates the designated column with a {@code java.sql.Clob} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.4
     */
    @Override
    public void updateClob(int columnIndex, Clob x) throws SQLException {
        resultSet.updateClob(columnIndex, x);
    }

    /**
     * Updates the designated column with a {@code java.sql.Clob} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.4
     */
    @Override
    public void updateClob(String columnLabel, Clob x) throws SQLException {
        resultSet.updateClob(columnLabel, x);
    }

    /**
     * Updates the designated column with a {@code java.sql.Array} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.4
     */
    @Override
    public void updateArray(int columnIndex, Array x) throws SQLException {
        resultSet.updateArray(columnIndex, x);
    }

    /**
     * Updates the designated column with a {@code java.sql.Array} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.4
     */
    @Override
    public void updateArray(String columnLabel, Array x) throws SQLException {
        resultSet.updateArray(columnLabel, x);
    }

    /**
     * Retrieves the value of the designated column in the current row of this
     * {@code ResultSet} object as a {@code java.sql.RowId} object in the Java
     * programming language.
     *
     * @param columnIndex the first column is 1, the second 2, ...
     * @return the column value; if the value is a SQL {@code NULL} the
     * value returned is {@code null}
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public RowId getRowId(int columnIndex) throws SQLException {
        return resultSet.getRowId(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row of this
     * {@code ResultSet} object as a {@code java.sql.RowId} object in the Java
     * programming language.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return the column value ; if the value is a SQL {@code NULL} the
     * value returned is {@code null}
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public RowId getRowId(String columnLabel) throws SQLException {
        return resultSet.getRowId(columnLabel);
    }

    /**
     * Updates the designated column with a {@code RowId} value. The updater
     * methods are used to update column values in the current row or the insert
     * row. The updater methods do not update the underlying database; instead
     * the {@code updateRow} or {@code insertRow} methods are called
     * to update the database.
     *
     * @param columnIndex the first column is 1, the second 2, ...
     * @param x           the column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        resultSet.updateRowId(columnIndex, x);
    }

    /**
     * Updates the designated column with a {@code RowId} value. The updater
     * methods are used to update column values in the current row or the insert
     * row. The updater methods do not update the underlying database; instead
     * the {@code updateRow} or {@code insertRow} methods are called
     * to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        resultSet.updateRowId(columnLabel, x);
    }

    /**
     * Retrieves the holdability of this {@code ResultSet} object
     *
     * @return either {@code ResultSet.HOLD_CURSORS_OVER_COMMIT} or {@code ResultSet.CLOSE_CURSORS_AT_COMMIT}
     * @throws SQLException if a database access error occurs
     *                      or this method is called on a closed result set
     * @since 1.6
     */
    @Override
    public int getHoldability() throws SQLException {
        return resultSet.getHoldability();
    }

    /**
     * Retrieves whether this {@code ResultSet} object has been closed. A {@code ResultSet} is closed if the
     * method close has been called on it, or if it is automatically closed.
     *
     * @return true if this {@code ResultSet} object is closed; false if it is still open
     * @throws SQLException if a database access error occurs
     * @since 1.6
     */
    @Override
    public boolean isClosed() throws SQLException {
        return resultSet.isClosed();
    }

    /**
     * Updates the designated column with a {@code String} value.
     * It is intended for use when updating {@code NCHAR},{@code NVARCHAR}
     * and {@code LONGNVARCHAR} columns.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second 2, ...
     * @param nString     the value for the column to be updated
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if the driver does not support national
     *                                         character sets;  if the driver can detect that a data conversion
     *                                         error could occur; this method is called on a closed result set;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or if a database access error occurs
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateNString(int columnIndex, String nString) throws SQLException {
        resultSet.updateNString(columnIndex, nString);
    }

    /**
     * Updates the designated column with a {@code String} value.
     * It is intended for use when updating {@code NCHAR},{@code NVARCHAR}
     * and {@code LONGNVARCHAR} columns.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param nString     the value for the column to be updated
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if the driver does not support national
     *                                         character sets;  if the driver can detect that a data conversion
     *                                         error could occur; this method is called on a closed result set;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or if a database access error occurs
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateNString(String columnLabel, String nString) throws SQLException {
        resultSet.updateNString(columnLabel, nString);
    }

    /**
     * Updates the designated column with a {@code java.sql.NClob} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second 2, ...
     * @param nClob       the value for the column to be updated
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if the driver does not support national
     *                                         character sets;  if the driver can detect that a data conversion
     *                                         error could occur; this method is called on a closed result set;
     *                                         if a database access error occurs or
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
        resultSet.updateNClob(columnIndex, nClob);
    }

    /**
     * Updates the designated column with a {@code java.sql.NClob} value.
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param nClob       the value for the column to be updated
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if the driver does not support national
     *                                         character sets;  if the driver can detect that a data conversion
     *                                         error could occur; this method is called on a closed result set;
     *                                         if a database access error occurs or
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
        resultSet.updateNClob(columnLabel, nClob);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a {@code NClob} object
     * in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return a {@code NClob} object representing the SQL
     * {@code NCLOB} value in the specified column
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if the driver does not support national
     *                                         character sets;  if the driver can detect that a data conversion
     *                                         error could occur; this method is called on a closed result set
     *                                         or if a database access error occurs
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public NClob getNClob(int columnIndex) throws SQLException {
        return resultSet.getNClob(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a {@code NClob} object
     * in the Java programming language.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return a {@code NClob} object representing the SQL {@code NCLOB}
     * value in the specified column
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if the driver does not support national
     *                                         character sets;  if the driver can detect that a data conversion
     *                                         error could occur; this method is called on a closed result set
     *                                         or if a database access error occurs
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public NClob getNClob(String columnLabel) throws SQLException {
        return resultSet.getNClob(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in  the current row of
     * this {@code ResultSet} as a
     * {@code java.sql.SQLXML} object in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return a {@code SQLXML} object that maps an {@code SQL XML} value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        return resultSet.getSQLXML(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in  the current row of
     * this {@code ResultSet} as a
     * {@code java.sql.SQLXML} object in the Java programming language.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return a {@code SQLXML} object that maps an {@code SQL XML} value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        return resultSet.getSQLXML(columnLabel);
    }

    /**
     * Updates the designated column with a {@code java.sql.SQLXML} value.
     * The updater
     * methods are used to update column values in the current row or the insert
     * row. The updater methods do not update the underlying database; instead
     * the {@code updateRow} or {@code insertRow} methods are called
     * to update the database.
     *
     * @param columnIndex the first column is 1, the second 2, ...
     * @param xmlObject   the value for the column to be updated
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs; this method
     *                                         is called on a closed result set;
     *                                         the {@code java.xml.transform.Result},
     *                                         {@code Writer} or {@code OutputStream} has not been closed
     *                                         for the {@code SQLXML} object;
     *                                         if there is an error processing the XML value or
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}.  The {@code getCause} method
     *                                         of the exception may provide a more detailed exception, for example, if the
     *                                         stream does not contain valid XML.
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
        resultSet.updateSQLXML(columnIndex, xmlObject);
    }

    /**
     * Updates the designated column with a {@code java.sql.SQLXML} value.
     * The updater
     * methods are used to update column values in the current row or the insert
     * row. The updater methods do not update the underlying database; instead
     * the {@code updateRow} or {@code insertRow} methods are called
     * to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param xmlObject   the column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs; this method
     *                                         is called on a closed result set;
     *                                         the {@code java.xml.transform.Result},
     *                                         {@code Writer} or {@code OutputStream} has not been closed
     *                                         for the {@code SQLXML} object;
     *                                         if there is an error processing the XML value or
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}.  The {@code getCause} method
     *                                         of the exception may provide a more detailed exception, for example, if the
     *                                         stream does not contain valid XML.
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
        resultSet.updateSQLXML(columnLabel, xmlObject);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code String} in the Java programming language.
     * It is intended for use when
     * accessing  {@code NCHAR},{@code NVARCHAR}
     * and {@code LONGNVARCHAR} columns.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code null}
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public String getNString(int columnIndex) throws SQLException {
        return resultSet.getNString(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code String} in the Java programming language.
     * It is intended for use when
     * accessing  {@code NCHAR},{@code NVARCHAR}
     * and {@code LONGNVARCHAR} columns.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code null}
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public String getNString(String columnLabel) throws SQLException {
        return resultSet.getNString(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a
     * {@code java.io.Reader} object.
     * It is intended for use when
     * accessing  {@code NCHAR},{@code NVARCHAR}
     * and {@code LONGNVARCHAR} columns.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return a {@code java.io.Reader} object that contains the column
     * value; if the value is SQL {@code NULL}, the value returned is
     * {@code null} in the Java programming language.
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        return resultSet.getNCharacterStream(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a
     * {@code java.io.Reader} object.
     * It is intended for use when
     * accessing  {@code NCHAR},{@code NVARCHAR}
     * and {@code LONGNVARCHAR} columns.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return a {@code java.io.Reader} object that contains the column
     * value; if the value is SQL {@code NULL}, the value returned is
     * {@code null} in the Java programming language
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        return resultSet.getNCharacterStream(columnLabel);
    }

    /**
     * Updates the designated column with a character stream value, which will have
     * the specified number of bytes.   The
     * driver does the necessary conversion from Java character format to
     * the national character set in the database.
     * It is intended for use when
     * updating  {@code NCHAR},{@code NVARCHAR}
     * and {@code LONGNVARCHAR} columns.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @param length      the length of the stream
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY} or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        resultSet.updateNCharacterStream(columnIndex, x, length);
    }

    /**
     * Updates the designated column with a character stream value, which will have
     * the specified number of bytes.  The
     * driver does the necessary conversion from Java character format to
     * the national character set in the database.
     * It is intended for use when
     * updating  {@code NCHAR},{@code NVARCHAR}
     * and {@code LONGNVARCHAR} columns.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param reader      the {@code java.io.Reader} object containing
     *                    the new column value
     * @param length      the length of the stream
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY} or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        resultSet.updateNCharacterStream(columnLabel, reader, length);
    }

    /**
     * Updates the designated column with an ascii stream value, which will have
     * the specified number of bytes.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @param length      the length of the stream
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
        resultSet.updateAsciiStream(columnIndex, x, length);
    }

    /**
     * Updates the designated column with a binary stream value, which will have
     * the specified number of bytes.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @param length      the length of the stream
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
        resultSet.updateBinaryStream(columnIndex, x, length);
    }

    /**
     * Updates the designated column with a character stream value, which will have
     * the specified number of bytes.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @param length      the length of the stream
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        resultSet.updateCharacterStream(columnIndex, x, length);
    }

    /**
     * Updates the designated column with an ascii stream value, which will have
     * the specified number of bytes.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @param length      the length of the stream
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
        resultSet.updateAsciiStream(columnLabel, x, length);
    }

    /**
     * Updates the designated column with a binary stream value, which will have
     * the specified number of bytes.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @param length      the length of the stream
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
        resultSet.updateBinaryStream(columnLabel, x, length);
    }

    /**
     * Updates the designated column with a character stream value, which will have
     * the specified number of bytes.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param reader      the {@code java.io.Reader} object containing
     *                    the new column value
     * @param length      the length of the stream
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        resultSet.updateCharacterStream(columnLabel, reader, length);
    }

    /**
     * Updates the designated column using the given input stream, which
     * will have the specified number of bytes.
     *
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param inputStream An object that contains the data to set the parameter
     *                    value to.
     * @param length      the number of bytes in the parameter data.
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
        resultSet.updateBlob(columnIndex, inputStream, length);
    }

    /**
     * Updates the designated column using the given input stream, which
     * will have the specified number of bytes.
     *
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param inputStream An object that contains the data to set the parameter
     *                    value to.
     * @param length      the number of bytes in the parameter data.
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
        resultSet.updateBlob(columnLabel, inputStream, length);
    }

    /**
     * Updates the designated column using the given {@code Reader}
     * object, which is the given number of characters long.
     * When a very large UNICODE value is input to a {@code LONGVARCHAR}
     * parameter, it may be more practical to send it via a
     * {@code java.io.Reader} object. The JDBC driver will
     * do any necessary conversion from UNICODE to the database char format.
     *
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param reader      An object that contains the data to set the parameter value to.
     * @param length      the number of characters in the parameter data.
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
        resultSet.updateClob(columnIndex, reader, length);
    }

    /**
     * Updates the designated column using the given {@code Reader}
     * object, which is the given number of characters long.
     * When a very large UNICODE value is input to a {@code LONGVARCHAR}
     * parameter, it may be more practical to send it via a
     * {@code java.io.Reader} object.  The JDBC driver will
     * do any necessary conversion from UNICODE to the database char format.
     *
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param reader      An object that contains the data to set the parameter value to.
     * @param length      the number of characters in the parameter data.
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
        resultSet.updateNClob(columnLabel, reader, length);
    }

    /**
     * Updates the designated column using the given {@code Reader}
     * object, which is the given number of characters long.
     * When a very large UNICODE value is input to a {@code LONGVARCHAR}
     * parameter, it may be more practical to send it via a
     * {@code java.io.Reader} object. The JDBC driver will
     * do any necessary conversion from UNICODE to the database char format.
     *
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnIndex the first column is 1, the second 2, ...
     * @param reader      An object that contains the data to set the parameter value to.
     * @param length      the number of characters in the parameter data.
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if the driver does not support national
     *                                         character sets;  if the driver can detect that a data conversion
     *                                         error could occur; this method is called on a closed result set,
     *                                         if a database access error occurs or
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
        resultSet.updateNClob(columnIndex, reader, length);
    }

    /**
     * Updates the designated column using the given {@code Reader}
     * object, which is the given number of characters long.
     * When a very large UNICODE value is input to a {@code LONGVARCHAR}
     * parameter, it may be more practical to send it via a
     * {@code java.io.Reader} object. The JDBC driver will
     * do any necessary conversion from UNICODE to the database char format.
     *
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param reader      An object that contains the data to set the parameter value to.
     * @param length      the number of characters in the parameter data.
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if the driver does not support national
     *                                         character sets;  if the driver can detect that a data conversion
     *                                         error could occur; this method is called on a closed result set;
     *                                         if a database access error occurs or
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
        resultSet.updateNClob(columnLabel, reader, length);
    }

    /**
     * Updates the designated column with a character stream value.
     * The data will be read from the stream
     * as needed until end-of-stream is reached.  The
     * driver does the necessary conversion from Java character format to
     * the national character set in the database.
     * It is intended for use when
     * updating  {@code NCHAR},{@code NVARCHAR}
     * and {@code LONGNVARCHAR} columns.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * {@code updateNCharacterStream} which takes a length parameter.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY} or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
        resultSet.updateNCharacterStream(columnIndex, x);
    }

    /**
     * Updates the designated column with a character stream value.
     * The data will be read from the stream
     * as needed until end-of-stream is reached.  The
     * driver does the necessary conversion from Java character format to
     * the national character set in the database.
     * It is intended for use when
     * updating  {@code NCHAR},{@code NVARCHAR}
     * and {@code LONGNVARCHAR} columns.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * {@code updateNCharacterStream} which takes a length parameter.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param reader      the {@code java.io.Reader} object containing
     *                    the new column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY} or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
        resultSet.updateNCharacterStream(columnLabel, reader);
    }

    /**
     * Updates the designated column with an ascii stream value.
     * The data will be read from the stream
     * as needed until end-of-stream is reached.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * {@code updateAsciiStream} which takes a length parameter.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
        resultSet.updateAsciiStream(columnIndex, x);
    }

    /**
     * Updates the designated column with a binary stream value.
     * The data will be read from the stream
     * as needed until end-of-stream is reached.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * {@code updateBinaryStream} which takes a length parameter.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
        resultSet.updateBinaryStream(columnIndex, x);
    }

    /**
     * Updates the designated column with a character stream value.
     * The data will be read from the stream
     * as needed until end-of-stream is reached.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * {@code updateCharacterStream} which takes a length parameter.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param x           the new column value
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
        resultSet.updateCharacterStream(columnIndex, x);
    }

    /**
     * Updates the designated column with an ascii stream value.
     * The data will be read from the stream
     * as needed until end-of-stream is reached.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * {@code updateAsciiStream} which takes a length parameter.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
        resultSet.updateAsciiStream(columnLabel, x);
    }

    /**
     * Updates the designated column with a binary stream value.
     * The data will be read from the stream
     * as needed until end-of-stream is reached.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * {@code updateBinaryStream} which takes a length parameter.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param x           the new column value
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
        resultSet.updateBinaryStream(columnLabel, x);
    }

    /**
     * Updates the designated column with a character stream value.
     * The data will be read from the stream
     * as needed until end-of-stream is reached.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * {@code updateCharacterStream} which takes a length parameter.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param reader      the {@code java.io.Reader} object containing
     *                    the new column value
     * @throws SQLException                    if the columnLabel is not valid; if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
        resultSet.updateCharacterStream(columnLabel, reader);
    }

    /**
     * Updates the designated column using the given input stream. The data will be read from the stream
     * as needed until end-of-stream is reached.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * {@code updateBlob} which takes a length parameter.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param inputStream An object that contains the data to set the parameter
     *                    value to.
     * @throws SQLException                    if the columnIndex is not valid; if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
        resultSet.updateBlob(columnIndex, inputStream);
    }

    /**
     * Updates the designated column using the given input stream. The data will be read from the stream
     * as needed until end-of-stream is reached.
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * {@code updateBlob} which takes a length parameter.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param inputStream An object that contains the data to set the parameter
     *                    value to.
     * @throws SQLException                    if the columnLabel is not valid; if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
        resultSet.updateBlob(columnLabel, inputStream);
    }

    /**
     * Updates the designated column using the given {@code Reader}
     * object.
     * The data will be read from the stream
     * as needed until end-of-stream is reached.  The JDBC driver will
     * do any necessary conversion from UNICODE to the database char format.
     *
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * {@code updateClob} which takes a length parameter.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param reader      An object that contains the data to set the parameter value to.
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        resultSet.updateNClob(columnIndex, reader);
    }

    /**
     * Updates the designated column using the given {@code Reader}
     * object.
     * The data will be read from the stream
     * as needed until end-of-stream is reached.  The JDBC driver will
     * do any necessary conversion from UNICODE to the database char format.
     *
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * {@code updateClob} which takes a length parameter.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param reader      An object that contains the data to set the parameter value to.
     * @throws SQLException                    if the columnLabel is not valid; if a database access error occurs;
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     *                                         or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateClob(String columnLabel, Reader reader) throws SQLException {
        resultSet.updateNClob(columnLabel, reader);
    }

    /**
     * Updates the designated column using the given {@code Reader}
     * <p>
     * The data will be read from the stream
     * as needed until end-of-stream is reached.  The JDBC driver will
     * do any necessary conversion from UNICODE to the database char format.
     *
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * {@code updateNClob} which takes a length parameter.
     *
     * @param columnIndex the first column is 1, the second 2, ...
     * @param reader      An object that contains the data to set the parameter value to.
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if the driver does not support national
     *                                         character sets;  if the driver can detect that a data conversion
     *                                         error could occur; this method is called on a closed result set,
     *                                         if a database access error occurs or
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        resultSet.updateNClob(columnIndex, reader);
    }

    /**
     * Updates the designated column using the given {@code Reader}
     * object.
     * The data will be read from the stream
     * as needed until end-of-stream is reached.  The JDBC driver will
     * do any necessary conversion from UNICODE to the database char format.
     *
     * <p>
     * The updater methods are used to update column values in the
     * current row or the insert row.  The updater methods do not
     * update the underlying database; instead the {@code updateRow} or
     * {@code insertRow} methods are called to update the database.
     *
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * {@code updateNClob} which takes a length parameter.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param reader      An object that contains the data to set the parameter value to.
     * @throws SQLException                    if the columnLabel is not valid; if the driver does not support national
     *                                         character sets;  if the driver can detect that a data conversion
     *                                         error could occur; this method is called on a closed result set;
     *                                         if a database access error occurs or
     *                                         the result set concurrency is {@code CONCUR_READ_ONLY}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    @Override
    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
        resultSet.updateNClob(columnLabel, reader);
    }

    /**
     * <p>Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object and will convert from the
     * SQL type of the column to the requested Java data type, if the
     * conversion is supported. If the conversion is not
     * supported  or null is specified for the type, a
     * {@code SQLException} is thrown.
     * <p>
     * At a minimum, an implementation must support the conversions defined in
     * Appendix B, Table B-3 and conversion of appropriate user defined SQL
     * types to a Java type which implements {@code SQLData}, or {@code Struct}.
     * Additional conversions may be supported and are vendor defined.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param type        Class representing the Java data type to convert the designated
     *                    column to.
     * @return an instance of {@code type} holding the column value
     * @throws SQLException                    if conversion is not supported, type is null or
     *                                         another error occurs. The getCause() method of the
     *                                         exception may provide a more detailed exception, for example, if
     *                                         a conversion error occurs
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.7
     */
    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        return resultSet.getObject(columnIndex, type);
    }

    /**
     * <p>Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object and will convert from the
     * SQL type of the column to the requested Java data type, if the
     * conversion is supported. If the conversion is not
     * supported  or null is specified for the type, a
     * {@code SQLException} is thrown.
     * <p>
     * At a minimum, an implementation must support the conversions defined in
     * Appendix B, Table B-3 and conversion of appropriate user defined SQL
     * types to a Java type which implements {@code SQLData}, or {@code Struct}.
     * Additional conversions may be supported and are vendor defined.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.
     *                    If the SQL AS clause was not specified, then the label is the name
     *                    of the column
     * @param type        Class representing the Java data type to convert the designated
     *                    column to.
     * @return an instance of {@code type} holding the column value
     * @throws SQLException                    if conversion is not supported, type is null or
     *                                         another error occurs. The getCause() method of the
     *                                         exception may provide a more detailed exception, for example, if
     *                                         a conversion error occurs
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.7
     */
    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        return resultSet.getObject(columnLabel, type);
    }


        @Override
        public boolean next() throws SQLException {
            return resultSet.next();
        }

    /**
     * Releases this {@code ResultSet} object's database and
     * JDBC resources immediately instead of waiting for
     * this to happen when it is automatically closed.
     *
     * <P>The closing of a {@code ResultSet} object does <strong>not</strong> close the {@code Blob},
     * {@code Clob} or {@code NClob} objects created by the {@code ResultSet}. {@code Blob},
     * {@code Clob} or {@code NClob} objects remain valid for at least the duration of the
     * transaction in which they are created, unless their {@code free} method is invoked.
     * <p>
     * When a {@code ResultSet} is closed, any {@code ResultSetMetaData}
     * instances that were created by calling the  {@code getMetaData}
     * method remain accessible.
     *
     * <P><B>Note:</B> A {@code ResultSet} object
     * is automatically closed by the
     * {@code Statement} object that generated it when
     * that {@code Statement} object is closed,
     * re-executed, or is used to retrieve the next result from a
     * sequence of multiple results.
     * <p>
     * Calling the method {@code close} on a {@code ResultSet}
     * object that is already closed is a no-op.
     *
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void close() throws SQLException {
        resultSet.close();
    }

    /**
     * Reports whether
     * the last column read had a value of SQL {@code NULL}.
     * Note that you must first call one of the getter methods
     * on a column to try to read its value and then call
     * the method {@code wasNull} to see if the value read was
     * SQL {@code NULL}.
     *
     * @return {@code true} if the last column value read was SQL
     * {@code NULL} and {@code false} otherwise
     * @throws SQLException if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public boolean wasNull() throws SQLException {
        return resultSet.wasNull();
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code String} in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code null}
     * @throws SQLException if the columnIndex is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public String getString(int columnIndex) throws SQLException {
        return resultSet.getString(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code boolean} in the Java programming language.
     *
     * <P>If the designated column has a datatype of CHAR or VARCHAR
     * and contains a "0" or has a datatype of BIT, TINYINT, SMALLINT, INTEGER or BIGINT
     * and contains  a 0, a value of {@code false} is returned.  If the designated column has a datatype
     * of CHAR or VARCHAR
     * and contains a "1" or has a datatype of BIT, TINYINT, SMALLINT, INTEGER or BIGINT
     * and contains  a 1, a value of {@code true} is returned.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code false}
     * @throws SQLException if the columnIndex is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        return resultSet.getBoolean(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code byte} in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code 0}
     * @throws SQLException if the columnIndex is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public byte getByte(int columnIndex) throws SQLException {
        return resultSet.getByte(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code short} in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code 0}
     * @throws SQLException if the columnIndex is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public short getShort(int columnIndex) throws SQLException {
        return resultSet.getShort(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * an {@code int} in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code 0}
     * @throws SQLException if the columnIndex is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public int getInt(int columnIndex) throws SQLException {
        return resultSet.getInt(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code long} in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code 0}
     * @throws SQLException if the columnIndex is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public long getLong(int columnIndex) throws SQLException {
        return resultSet.getLong(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code float} in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code 0}
     * @throws SQLException if the columnIndex is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public float getFloat(int columnIndex) throws SQLException {
        return resultSet.getFloat(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code double} in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code 0}
     * @throws SQLException if the columnIndex is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public double getDouble(int columnIndex) throws SQLException {
        return resultSet.getDouble(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code java.sql.BigDecimal} in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param scale       the number of digits to the right of the decimal point
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code null}
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs or this method is
     *                                         called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @deprecated Use {@code getBigDecimal(int columnIndex)}
     * or {@code getBigDecimal(String columnLabel)}
     */
    @Override
    @Deprecated
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        return resultSet.getBigDecimal(columnIndex, scale);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code byte} array in the Java programming language.
     * The bytes represent the raw values returned by the driver.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code null}
     * @throws SQLException if the columnIndex is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        return resultSet.getBytes(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code java.sql.Date} object in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code null}
     * @throws SQLException if the columnIndex is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public Date getDate(int columnIndex) throws SQLException {
        return resultSet.getDate(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code java.sql.Time} object in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code null}
     * @throws SQLException if the columnIndex is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public Time getTime(int columnIndex) throws SQLException {
        return resultSet.getTime(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code java.sql.Timestamp} object in the Java programming language.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code null}
     * @throws SQLException if the columnIndex is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        return resultSet.getTimestamp(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a stream of ASCII characters. The value can then be read in chunks from the
     * stream. This method is particularly
     * suitable for retrieving large {@code LONGVARCHAR} values.
     * The JDBC driver will
     * do any necessary conversion from the database format into ASCII.
     *
     * <P><B>Note:</B> All the data in the returned stream must be
     * read prior to getting the value of any other column. The next
     * call to a getter method implicitly closes the stream.  Also, a
     * stream may return {@code 0} when the method
     * {@code InputStream.available}
     * is called whether there is data available or not.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return a Java input stream that delivers the database column value
     * as a stream of one-byte ASCII characters;
     * if the value is SQL {@code NULL}, the
     * value returned is {@code null}
     * @throws SQLException if the columnIndex is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public InputStream getAsciiStream(int columnIndex) throws SQLException {
        return resultSet.getAsciiStream(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * as a stream of two-byte 3 characters. The first byte is
     * the high byte; the second byte is the low byte.
     * <p>
     * The value can then be read in chunks from the
     * stream. This method is particularly
     * suitable for retrieving large {@code LONGVARCHAR}values.  The
     * JDBC driver will do any necessary conversion from the database
     * format into Unicode.
     *
     * <P><B>Note:</B> All the data in the returned stream must be
     * read prior to getting the value of any other column. The next
     * call to a getter method implicitly closes the stream.
     * Also, a stream may return {@code 0} when the method
     * {@code InputStream.available}
     * is called, whether there is data available or not.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return a Java input stream that delivers the database column value
     * as a stream of two-byte Unicode characters;
     * if the value is SQL {@code NULL}, the value returned is
     * {@code null}
     * @throws SQLException                    if the columnIndex is not valid;
     *                                         if a database access error occurs or this method is
     *                                         called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @deprecated use {@code getCharacterStream} in place of
     * {@code getUnicodeStream}
     */
    @Override
    @Deprecated
    public InputStream getUnicodeStream(int columnIndex) throws SQLException {
        return resultSet.getUnicodeStream(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a  stream of
     * uninterpreted bytes. The value can then be read in chunks from the
     * stream. This method is particularly
     * suitable for retrieving large {@code LONGVARBINARY} values.
     *
     * <P><B>Note:</B> All the data in the returned stream must be
     * read prior to getting the value of any other column. The next
     * call to a getter method implicitly closes the stream.  Also, a
     * stream may return {@code 0} when the method
     * {@code InputStream.available}
     * is called whether there is data available or not.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return a Java input stream that delivers the database column value
     * as a stream of uninterpreted bytes;
     * if the value is SQL {@code NULL}, the value returned is
     * {@code null}
     * @throws SQLException if the columnIndex is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public InputStream getBinaryStream(int columnIndex) throws SQLException {
        return resultSet.getBinaryStream(columnIndex);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code String} in the Java programming language.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code null}
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public String getString(String columnLabel) throws SQLException {
        return resultSet.getString(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code boolean} in the Java programming language.
     *
     * <P>If the designated column has a datatype of CHAR or VARCHAR
     * and contains a "0" or has a datatype of BIT, TINYINT, SMALLINT, INTEGER or BIGINT
     * and contains  a 0, a value of {@code false} is returned.  If the designated column has a datatype
     * of CHAR or VARCHAR
     * and contains a "1" or has a datatype of BIT, TINYINT, SMALLINT, INTEGER or BIGINT
     * and contains  a 1, a value of {@code true} is returned.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code false}
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public boolean getBoolean(String columnLabel) throws SQLException {
        return resultSet.getBoolean(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code byte} in the Java programming language.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code 0}
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public byte getByte(String columnLabel) throws SQLException {
        return resultSet.getByte(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code short} in the Java programming language.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code 0}
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public short getShort(String columnLabel) throws SQLException {
        return resultSet.getShort(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * an {@code int} in the Java programming language.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code 0}
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public int getInt(String columnLabel) throws SQLException {
        return resultSet.getInt(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code long} in the Java programming language.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code 0}
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public long getLong(String columnLabel) throws SQLException {
        return resultSet.getLong(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code float} in the Java programming language.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code 0}
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public float getFloat(String columnLabel) throws SQLException {
        return resultSet.getFloat(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code double} in the Java programming language.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code 0}
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public double getDouble(String columnLabel) throws SQLException {
        return resultSet.getDouble(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code java.math.BigDecimal} in the Java programming language.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @param scale       the number of digits to the right of the decimal point
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code null}
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs or this method is
     *                                         called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @deprecated Use {@code getBigDecimal(int columnIndex)}
     * or {@code getBigDecimal(String columnLabel)}
     */
    @Override
    @Deprecated
    public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
        return resultSet.getBigDecimal(columnLabel, scale);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code byte} array in the Java programming language.
     * The bytes represent the raw values returned by the driver.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code null}
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public byte[] getBytes(String columnLabel) throws SQLException {
        return resultSet.getBytes(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code java.sql.Date} object in the Java programming language.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code null}
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public Date getDate(String columnLabel) throws SQLException {
        return resultSet.getDate(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code java.sql.Time} object in the Java programming language.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return the column value;
     * if the value is SQL {@code NULL},
     * the value returned is {@code null}
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public Time getTime(String columnLabel) throws SQLException {
        return resultSet.getTime(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as
     * a {@code java.sql.Timestamp} object in the Java programming language.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return the column value; if the value is SQL {@code NULL}, the
     * value returned is {@code null}
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public Timestamp getTimestamp(String columnLabel) throws SQLException {
        return resultSet.getTimestamp(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a stream of
     * ASCII characters. The value can then be read in chunks from the
     * stream. This method is particularly
     * suitable for retrieving large {@code LONGVARCHAR} values.
     * The JDBC driver will
     * do any necessary conversion from the database format into ASCII.
     *
     * <P><B>Note:</B> All the data in the returned stream must be
     * read prior to getting the value of any other column. The next
     * call to a getter method implicitly closes the stream. Also, a
     * stream may return {@code 0} when the method {@code available}
     * is called whether there is data available or not.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return a Java input stream that delivers the database column value
     * as a stream of one-byte ASCII characters.
     * If the value is SQL {@code NULL},
     * the value returned is {@code null}.
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public InputStream getAsciiStream(String columnLabel) throws SQLException {
        return resultSet.getAsciiStream(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a stream of two-byte
     * Unicode characters. The first byte is the high byte; the second
     * byte is the low byte.
     * <p>
     * The value can then be read in chunks from the
     * stream. This method is particularly
     * suitable for retrieving large {@code LONGVARCHAR} values.
     * The JDBC technology-enabled driver will
     * do any necessary conversion from the database format into Unicode.
     *
     * <P><B>Note:</B> All the data in the returned stream must be
     * read prior to getting the value of any other column. The next
     * call to a getter method implicitly closes the stream.
     * Also, a stream may return {@code 0} when the method
     * {@code InputStream.available} is called, whether there
     * is data available or not.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return a Java input stream that delivers the database column value
     * as a stream of two-byte Unicode characters.
     * If the value is SQL {@code NULL}, the value returned
     * is {@code null}.
     * @throws SQLException                    if the columnLabel is not valid;
     *                                         if a database access error occurs or this method is
     *                                         called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @deprecated use {@code getCharacterStream} instead
     */
    @Override
    @Deprecated
    public InputStream getUnicodeStream(String columnLabel) throws SQLException {
        return resultSet.getUnicodeStream(columnLabel);
    }

    /**
     * Retrieves the value of the designated column in the current row
     * of this {@code ResultSet} object as a stream of uninterpreted
     * {@code byte}s.
     * The value can then be read in chunks from the
     * stream. This method is particularly
     * suitable for retrieving large {@code LONGVARBINARY}
     * values.
     *
     * <P><B>Note:</B> All the data in the returned stream must be
     * read prior to getting the value of any other column. The next
     * call to a getter method implicitly closes the stream. Also, a
     * stream may return {@code 0} when the method {@code available}
     * is called whether there is data available or not.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.  If the SQL AS clause was not specified, then the label is the name of the column
     * @return a Java input stream that delivers the database column value
     * as a stream of uninterpreted bytes;
     * if the value is SQL {@code NULL}, the result is {@code null}
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public InputStream getBinaryStream(String columnLabel) throws SQLException {
        return resultSet.getBinaryStream(columnLabel);
    }

    /**
     * Retrieves the first warning reported by calls on this
     * {@code ResultSet} object.
     * Subsequent warnings on this {@code ResultSet} object
     * will be chained to the {@code SQLWarning} object that
     * this method returns.
     *
     * <P>The warning chain is automatically cleared each time a new
     * row is read.  This method may not be called on a {@code ResultSet}
     * object that has been closed; doing so will cause an
     * {@code SQLException} to be thrown.
     * <p>
     * <B>Note:</B> This warning chain only covers warnings caused
     * by {@code ResultSet} methods.  Any warning caused by
     * {@code Statement} methods
     * (such as reading OUT parameters) will be chained on the
     * {@code Statement} object.
     *
     * @return the first {@code SQLWarning} object reported or
     * {@code null} if there are none
     * @throws SQLException if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    /**
     * Clears all warnings reported on this {@code ResultSet} object.
     * After this method is called, the method {@code getWarnings}
     * returns {@code null} until a new warning is
     * reported for this {@code ResultSet} object.
     *
     * @throws SQLException if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public void clearWarnings() throws SQLException {
        resultSet.clearWarnings();
    }

    /**
     * Retrieves the name of the SQL cursor used by this {@code ResultSet}
     * object.
     *
     * <P>In SQL, a result table is retrieved through a cursor that is
     * named. The current row of a result set can be updated or deleted
     * using a positioned update/delete statement that references the
     * cursor name. To insure that the cursor has the proper isolation
     * level to support update, the cursor's {@code SELECT} statement
     * should be of the form {@code SELECT FOR UPDATE}. If
     * {@code FOR UPDATE} is omitted, the positioned updates may fail.
     *
     * <P>The JDBC API supports this SQL feature by providing the name of the
     * SQL cursor used by a {@code ResultSet} object.
     * The current row of a {@code ResultSet} object
     * is also the current row of this SQL cursor.
     *
     * @return the SQL name for this {@code ResultSet} object's cursor
     * @throws SQLException                    if a database access error occurs or this method is called on a closed result set
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     */
    @Override
    public String getCursorName() throws SQLException {
        return resultSet.getCursorName();
    }

    /**
     * Retrieves the  number, types and properties of
     * this {@code ResultSet} object's columns.
     *
     * @return the description of this {@code ResultSet} object's columns
     * @throws SQLException if a database access error occurs or this method is
     *                      called on a closed result set
     */
    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return resultSet.getMetaData();
    }

    /**
     * Returns an object that implements the given interface to allow access to
     * non-standard methods, or standard methods not exposed by the proxy.
     * <p>
     * If the receiver implements the interface then the result is the receiver
     * or a proxy for the receiver. If the receiver is a wrapper
     * and the wrapped object implements the interface then the result is the
     * wrapped object or a proxy for the wrapped object. Otherwise return the
     * the result of calling {@code unwrap} recursively on the wrapped object
     * or a proxy for that result. If the receiver is not a
     * wrapper and does not implement the interface, then an {@code SQLException} is thrown.
     *
     * @param iface A Class defining an interface that the result must implement.
     * @return an object that implements the interface. May be a proxy for the actual implementing object.
     * @throws SQLException If no object found that implements the interface
     * @since 1.6
     */
    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return resultSet.unwrap(iface);
    }

    /**
     * Returns true if this either implements the interface argument or is directly or indirectly a wrapper
     * for an object that does. Returns false otherwise. If this implements the interface then return true,
     * else if this is a wrapper then return the result of recursively calling {@code isWrapperFor} on the wrapped
     * object. If this does not implement the interface and is not a wrapper, return false.
     * This method should be implemented as a low-cost operation compared to {@code unwrap} so that
     * callers can use this method to avoid expensive {@code unwrap} calls that may fail. If this method
     * returns true then calling {@code unwrap} with the same argument should succeed.
     *
     * @param iface a Class defining an interface.
     * @return true if this implements the interface or directly or indirectly wraps an object that does.
     * @throws SQLException if an error occurs while determining whether this is a wrapper
     *                      for an object with the given interface.
     * @since 1.6
     */
    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return resultSet.isWrapperFor(iface);
    }

    /**
     * Executes the given SQL statement, which returns a single
     * {@code ResultSet} object.
     * <p>
     * <strong>Note:</strong>This method cannot be called on a
     * {@code PreparedStatement} or {@code CallableStatement}.
     *
     * @param sql an SQL statement to be sent to the database, typically a
     *            static SQL {@code SELECT} statement
     * @return a {@code ResultSet} object that contains the data produced
     * by the given query; never {@code null}
     * @throws SQLException        if a database access error occurs,
     *                             this method is called on a closed {@code Statement}, the given
     *                             SQL statement produces anything other than a single
     *                             {@code ResultSet} object, the method is called on a
     *                             {@code PreparedStatement} or {@code CallableStatement}
     * @throws SQLTimeoutException when the driver has determined that the
     *                             timeout value that was specified by the {@code setQueryTimeout}
     *                             method has been exceeded and has at least attempted to cancel
     *                             the currently running {@code Statement}
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        return getStatement().executeQuery(sql);
    }

    /**
     * Executes the given SQL statement, which may be an {@code INSERT},
     * {@code UPDATE}, or {@code DELETE} statement or an
     * SQL statement that returns nothing, such as an SQL DDL statement.
     * <p>
     * <strong>Note:</strong>This method cannot be called on a
     * {@code PreparedStatement} or {@code CallableStatement}.
     *
     * @param sql an SQL Data Manipulation Language (DML) statement, such as {@code INSERT}, {@code UPDATE} or
     *            {@code DELETE}; or an SQL statement that returns nothing,
     *            such as a DDL statement.
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements
     * or (2) 0 for SQL statements that return nothing
     * @throws SQLException        if a database access error occurs,
     *                             this method is called on a closed {@code Statement}, the given
     *                             SQL statement produces a {@code ResultSet} object, the method is called on a
     *                             {@code PreparedStatement} or {@code CallableStatement}
     * @throws SQLTimeoutException when the driver has determined that the
     *                             timeout value that was specified by the {@code setQueryTimeout}
     *                             method has been exceeded and has at least attempted to cancel
     *                             the currently running {@code Statement}
     */
    public int executeUpdate(String sql) throws SQLException {
        return getStatement().executeUpdate(sql);
    }

    /**
     * Retrieves the maximum number of bytes that can be
     * returned for character and binary column values in a {@code ResultSet}
     * object produced by this {@code Statement} object.
     * This limit applies only to  {@code BINARY}, {@code VARBINARY},
     * {@code LONGVARBINARY}, {@code CHAR}, {@code VARCHAR},
     * {@code NCHAR}, {@code NVARCHAR}, {@code LONGNVARCHAR}
     * and {@code LONGVARCHAR} columns.  If the limit is exceeded, the
     * excess data is silently discarded.
     *
     * @return the current column size limit for columns storing character and
     * binary values; zero means there is no limit
     * @throws SQLException if a database access error occurs or
     *                      this method is called on a closed {@code Statement}
     * @see #setMaxFieldSize
     */
    public int getMaxFieldSize() throws SQLException {
        return getStatement().getMaxFieldSize();
    }

    /**
     * Sets the limit for the maximum number of bytes that can be returned for
     * character and binary column values in a {@code ResultSet}
     * object produced by this {@code Statement} object.
     * <p>
     * This limit applies
     * only to {@code BINARY}, {@code VARBINARY},
     * {@code LONGVARBINARY}, {@code CHAR}, {@code VARCHAR},
     * {@code NCHAR}, {@code NVARCHAR}, {@code LONGNVARCHAR} and
     * {@code LONGVARCHAR} fields.  If the limit is exceeded, the excess data
     * is silently discarded. For maximum portability, use values
     * greater than 256.
     *
     * @param max the new column size limit in bytes; zero means there is no limit
     * @throws SQLException if a database access error occurs,
     *                      this method is called on a closed {@code Statement}
     *                      or the condition {@code max >= 0} is not satisfied
     * @see #getMaxFieldSize
     */
    public void setMaxFieldSize(int max) throws SQLException {
        getStatement().setMaxFieldSize(max);
    }

    /**
     * Retrieves the maximum number of rows that a
     * {@code ResultSet} object produced by this
     * {@code Statement} object can contain.  If this limit is exceeded,
     * the excess rows are silently dropped.
     *
     * @return the current maximum number of rows for a {@code ResultSet}
     * object produced by this {@code Statement} object;
     * zero means there is no limit
     * @throws SQLException if a database access error occurs or
     *                      this method is called on a closed {@code Statement}
     * @see #setMaxRows
     */
    public int getMaxRows() throws SQLException {
        return getStatement().getMaxRows();
    }

    /**
     * Sets the limit for the maximum number of rows that any
     * {@code ResultSet} object  generated by this {@code Statement}
     * object can contain to the given number.
     * If the limit is exceeded, the excess
     * rows are silently dropped.
     *
     * @param max the new max rows limit; zero means there is no limit
     * @throws SQLException if a database access error occurs,
     *                      this method is called on a closed {@code Statement}
     *                      or the condition {@code max >= 0} is not satisfied
     * @see #getMaxRows
     */
    public void setMaxRows(int max) throws SQLException {
        getStatement().setMaxRows(max);
    }

    /**
     * Sets escape processing on or off.
     * If escape scanning is on (the default), the driver will do
     * escape substitution before sending the SQL statement to the database.
     * <p>
     * The {@code Connection} and {@code DataSource} property
     * {@code escapeProcessing} may be used to change the default escape processing
     * behavior.  A value of true (the default) enables escape Processing for
     * all {@code Statement} objects. A value of false disables escape processing
     * for all {@code Statement} objects.  The {@code setEscapeProcessing}
     * method may be used to specify the escape processing behavior for an
     * individual {@code Statement} object.
     * <p>
     * Note: Since prepared statements have usually been parsed prior
     * to making this call, disabling escape processing for
     * {@code PreparedStatements} objects will have no effect.
     *
     * @param enable {@code true} to enable escape processing;
     *               {@code false} to disable it
     * @throws SQLException if a database access error occurs or
     *                      this method is called on a closed {@code Statement}
     */
    public void setEscapeProcessing(boolean enable) throws SQLException {
        getStatement().setEscapeProcessing(enable);
    }

    /**
     * Retrieves the number of seconds the driver will
     * wait for a {@code Statement} object to execute.
     * If the limit is exceeded, a
     * {@code SQLException} is thrown.
     *
     * @return the current query timeout limit in seconds; zero means there is
     * no limit
     * @throws SQLException if a database access error occurs or
     *                      this method is called on a closed {@code Statement}
     * @see #setQueryTimeout
     */
    public int getQueryTimeout() throws SQLException {
        return getStatement().getQueryTimeout();
    }

    /**
     * Sets the number of seconds the driver will wait for a
     * {@code Statement} object to execute to the given number of seconds.
     * By default there is no limit on the amount of time allowed for a running
     * statement to complete. If the limit is exceeded, an
     * {@code SQLTimeoutException} is thrown.
     * A JDBC driver must apply this limit to the {@code execute},
     * {@code executeQuery} and {@code executeUpdate} methods.
     * <p>
     * <strong>Note:</strong> JDBC driver implementations may also apply this
     * limit to {@code ResultSet} methods
     * (consult your driver vendor documentation for details).
     * <p>
     * <strong>Note:</strong> In the case of {@code Statement} batching, it is
     * implementation defined as to whether the time-out is applied to
     * individual SQL commands added via the {@code addBatch} method or to
     * the entire batch of SQL commands invoked by the {@code executeBatch}
     * method (consult your driver vendor documentation for details).
     *
     * @param seconds the new query timeout limit in seconds; zero means
     *                there is no limit
     * @throws SQLException if a database access error occurs,
     *                      this method is called on a closed {@code Statement}
     *                      or the condition {@code seconds >= 0} is not satisfied
     * @see #getQueryTimeout
     */
    public void setQueryTimeout(int seconds) throws SQLException {
        getStatement().setQueryTimeout(seconds);
    }

    /**
     * Cancels this {@code Statement} object if both the DBMS and
     * driver support aborting an SQL statement.
     * This method can be used by one thread to cancel a statement that
     * is being executed by another thread.
     *
     * @throws SQLException                    if a database access error occurs or
     *                                         this method is called on a closed {@code Statement}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     */
    public void cancel() throws SQLException {
        getStatement().cancel();
    }

    /**
     * Sets the SQL cursor name to the given {@code String}, which
     * will be used by subsequent {@code Statement} object
     * {@code execute} methods. This name can then be
     * used in SQL positioned update or delete statements to identify the
     * current row in the {@code ResultSet} object generated by this
     * statement.  If the database does not support positioned update/delete,
     * this method is a noop.  To insure that a cursor has the proper isolation
     * level to support updates, the cursor's {@code SELECT} statement
     * should have the form {@code SELECT FOR UPDATE}.  If
     * {@code FOR UPDATE} is not present, positioned updates may fail.
     *
     * <P><B>Note:</B> By definition, the execution of positioned updates and
     * deletes must be done by a different {@code Statement} object than
     * the one that generated the {@code ResultSet} object being used for
     * positioning. Also, cursor names must be unique within a connection.
     *
     * @param name the new cursor name, which must be unique within
     *             a connection
     * @throws SQLException                    if a database access error occurs or
     *                                         this method is called on a closed {@code Statement}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     */
    public void setCursorName(String name) throws SQLException {
        getStatement().setCursorName(name);
    }

    /**
     * Executes the given SQL statement, which may return multiple results.
     * In some (uncommon) situations, a single SQL statement may return
     * multiple result sets and/or update counts.  Normally you can ignore
     * this unless you are (1) executing a stored procedure that you know may
     * return multiple results or (2) you are dynamically executing an
     * unknown SQL string.
     * <p>
     * The {@code execute} method executes an SQL statement and indicates the
     * form of the first result.  You must then use the methods
     * {@code getResultSet} or {@code getUpdateCount}
     * to retrieve the result, and {@code getMoreResults} to
     * move to any subsequent result(s).
     * <p>
     * <strong>Note:</strong>This method cannot be called on a
     * {@code PreparedStatement} or {@code CallableStatement}.
     *
     * @param sql any SQL statement
     * @return {@code true} if the first result is a {@code ResultSet}
     * object; {@code false} if it is an update count or there are
     * no results
     * @throws SQLException        if a database access error occurs,
     *                             this method is called on a closed {@code Statement},
     *                             the method is called on a
     *                             {@code PreparedStatement} or {@code CallableStatement}
     * @throws SQLTimeoutException when the driver has determined that the
     *                             timeout value that was specified by the {@code setQueryTimeout}
     *                             method has been exceeded and has at least attempted to cancel
     *                             the currently running {@code Statement}
     * @see #getResultSet
     * @see #getUpdateCount
     * @see #getMoreResults
     */
    public boolean execute(String sql) throws SQLException {
        return getStatement().execute(sql);
    }

    /**
     * Retrieves the current result as a {@code ResultSet} object.
     * This method should be called only once per result.
     *
     * @return the current result as a {@code ResultSet} object or
     * {@code null} if the result is an update count or there are no more results
     * @throws SQLException if a database access error occurs or
     *                      this method is called on a closed {@code Statement}
     * @see #execute
     */
    public ResultSet getResultSet() throws SQLException {
        return getStatement().getResultSet();
    }

    /**
     * Retrieves the current result as an update count;
     * if the result is a {@code ResultSet} object or there are no more results, -1
     * is returned. This method should be called only once per result.
     *
     * @return the current result as an update count; -1 if the current result is a
     * {@code ResultSet} object or there are no more results
     * @throws SQLException if a database access error occurs or
     *                      this method is called on a closed {@code Statement}
     * @see #execute
     */
    public int getUpdateCount() throws SQLException {
        return getStatement().getUpdateCount();
    }

    /**
     * Moves to this {@code Statement} object's next result, returns
     * {@code true} if it is a {@code ResultSet} object, and
     * implicitly closes any current {@code ResultSet}
     * object(s) obtained with the method {@code getResultSet}.
     *
     * <P>There are no more results when the following is true:
     * <PRE>{@code
     * // stmt is a Statement object
     * ((stmt.getMoreResults() == false) && (stmt.getUpdateCount() == -1))
     * }</PRE>
     *
     * @return {@code true} if the next result is a {@code ResultSet}
     * object; {@code false} if it is an update count or there are
     * no more results
     * @throws SQLException if a database access error occurs or
     *                      this method is called on a closed {@code Statement}
     * @see #execute
     */
    public boolean getMoreResults() throws SQLException {
        return getStatement().getMoreResults();
    }

    /**
     * Retrieves the result set concurrency for {@code ResultSet} objects
     * generated by this {@code Statement} object.
     *
     * @return either {@code ResultSet.CONCUR_READ_ONLY} or
     * {@code ResultSet.CONCUR_UPDATABLE}
     * @throws SQLException if a database access error occurs or
     *                      this method is called on a closed {@code Statement}
     * @since 1.2
     */
    public int getResultSetConcurrency() throws SQLException {
        return getStatement().getResultSetConcurrency();
    }

    /**
     * Retrieves the result set type for {@code ResultSet} objects
     * generated by this {@code Statement} object.
     *
     * @return one of {@code ResultSet.TYPE_FORWARD_ONLY},
     * {@code ResultSet.TYPE_SCROLL_INSENSITIVE}, or
     * {@code ResultSet.TYPE_SCROLL_SENSITIVE}
     * @throws SQLException if a database access error occurs or
     *                      this method is called on a closed {@code Statement}
     * @since 1.2
     */
    public int getResultSetType() throws SQLException {
        return getStatement().getResultSetType();
    }

    /**
     * Adds the given SQL command to the current list of commands for this
     * {@code Statement} object. The commands in this list can be
     * executed as a batch by calling the method {@code executeBatch}.
     * <p>
     * <strong>Note:</strong>This method cannot be called on a
     * {@code PreparedStatement} or {@code CallableStatement}.
     *
     * @param sql typically this is a SQL {@code INSERT} or
     *            {@code UPDATE} statement
     * @throws SQLException if a database access error occurs,
     *                      this method is called on a closed {@code Statement}, the
     *                      driver does not support batch updates, the method is called on a
     *                      {@code PreparedStatement} or {@code CallableStatement}
     * @see #executeBatch
     * @see DatabaseMetaData#supportsBatchUpdates
     * @since 1.2
     */
    public void addBatch(String sql) throws SQLException {
        getStatement().addBatch(sql);
    }

    /**
     * Empties this {@code Statement} object's current list of
     * SQL commands.
     *
     * @throws SQLException if a database access error occurs,
     *                      this method is called on a closed {@code Statement} or the
     *                      driver does not support batch updates
     * @see #addBatch
     * @see DatabaseMetaData#supportsBatchUpdates
     * @since 1.2
     */
    public void clearBatch() throws SQLException {
        getStatement().clearBatch();
    }

    /**
     * Submits a batch of commands to the database for execution and
     * if all commands execute successfully, returns an array of update counts.
     * The {@code int} elements of the array that is returned are ordered
     * to correspond to the commands in the batch, which are ordered
     * according to the order in which they were added to the batch.
     * The elements in the array returned by the method {@code executeBatch}
     * may be one of the following:
     * <OL>
     * <LI>A number greater than or equal to zero -- indicates that the
     * command was processed successfully and is an update count giving the
     * number of rows in the database that were affected by the command's
     * execution
     * <LI>A value of {@code SUCCESS_NO_INFO} -- indicates that the command was
     * processed successfully but that the number of rows affected is
     * unknown
     * <p>
     * If one of the commands in a batch update fails to execute properly,
     * this method throws a {@code BatchUpdateException}, and a JDBC
     * driver may or may not continue to process the remaining commands in
     * the batch.  However, the driver's behavior must be consistent with a
     * particular DBMS, either always continuing to process commands or never
     * continuing to process commands.  If the driver continues processing
     * after a failure, the array returned by the method
     * {@code BatchUpdateException.getUpdateCounts}
     * will contain as many elements as there are commands in the batch, and
     * at least one of the elements will be the following:
     *
     * <LI>A value of {@code EXECUTE_FAILED} -- indicates that the command failed
     * to execute successfully and occurs only if a driver continues to
     * process commands after a command fails
     * </OL>
     * <p>
     * The possible implementations and return values have been modified in
     * the Java 2 SDK, Standard Edition, version 1.3 to
     * accommodate the option of continuing to process commands in a batch
     * update after a {@code BatchUpdateException} object has been thrown.
     *
     * @return an array of update counts containing one element for each
     * command in the batch.  The elements of the array are ordered according
     * to the order in which commands were added to the batch.
     * @throws SQLException        if a database access error occurs,
     *                             this method is called on a closed {@code Statement} or the
     *                             driver does not support batch statements. Throws {@link BatchUpdateException}
     *                             (a subclass of {@code SQLException}) if one of the commands sent to the
     *                             database fails to execute properly or attempts to return a result set.
     * @throws SQLTimeoutException when the driver has determined that the
     *                             timeout value that was specified by the {@code setQueryTimeout}
     *                             method has been exceeded and has at least attempted to cancel
     *                             the currently running {@code Statement}
     * @see #addBatch
     * @see DatabaseMetaData#supportsBatchUpdates
     * @since 1.2
     */
    public int[] executeBatch() throws SQLException {
        return getStatement().executeBatch();
    }

    /**
     * Retrieves the {@code Connection} object
     * that produced this {@code Statement} object.
     *
     * @return the connection that produced this statement
     * @throws SQLException if a database access error occurs or
     *                      this method is called on a closed {@code Statement}
     * @since 1.2
     */
    public Connection getConnection() throws SQLException {
        return getStatement().getConnection();
    }

    /**
     * Moves to this {@code Statement} object's next result, deals with
     * any current {@code ResultSet} object(s) according  to the instructions
     * specified by the given flag, and returns
     * {@code true} if the next result is a {@code ResultSet} object.
     *
     * <P>There are no more results when the following is true:
     * <PRE>{@code
     * // stmt is a Statement object
     * ((stmt.getMoreResults(current) == false) && (stmt.getUpdateCount() == -1))
     * }</PRE>
     *
     * @param current one of the following {@code Statement}
     *                constants indicating what should happen to current
     *                {@code ResultSet} objects obtained using the method
     *                {@code getResultSet}:
     *                {@code Statement.CLOSE_CURRENT_RESULT},
     *                {@code Statement.KEEP_CURRENT_RESULT}, or
     *                {@code Statement.CLOSE_ALL_RESULTS}
     * @return {@code true} if the next result is a {@code ResultSet}
     * object; {@code false} if it is an update count or there are no
     * more results
     * @throws SQLException                    if a database access error occurs,
     *                                         this method is called on a closed {@code Statement} or the argument
     *                                         supplied is not one of the following:
     *                                         {@code Statement.CLOSE_CURRENT_RESULT},
     *                                         {@code Statement.KEEP_CURRENT_RESULT} or
     *                                         {@code Statement.CLOSE_ALL_RESULTS}
     * @throws SQLFeatureNotSupportedException if
     *                                         {@code DatabaseMetaData.supportsMultipleOpenResults} returns
     *                                         {@code false} and either
     *                                         {@code Statement.KEEP_CURRENT_RESULT} or
     *                                         {@code Statement.CLOSE_ALL_RESULTS} are supplied as
     *                                         the argument.
     * @see #execute
     * @since 1.4
     */
    public boolean getMoreResults(int current) throws SQLException {
        return getStatement().getMoreResults(current);
    }

    /**
     * Retrieves any auto-generated keys created as a result of executing this
     * {@code Statement} object. If this {@code Statement} object did
     * not generate any keys, an empty {@code ResultSet}
     * object is returned.
     *
     * <p><B>Note:</B>If the columns which represent the auto-generated keys were not specified,
     * the JDBC driver implementation will determine the columns which best represent the auto-generated keys.
     *
     * @return a {@code ResultSet} object containing the auto-generated key(s)
     * generated by the execution of this {@code Statement} object
     * @throws SQLException                    if a database access error occurs or
     *                                         this method is called on a closed {@code Statement}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     * @since 1.4
     */
    public ResultSet getGeneratedKeys() throws SQLException {
        return getStatement().getGeneratedKeys();
    }

    /**
     * Executes the given SQL statement and signals the driver with the
     * given flag about whether the
     * auto-generated keys produced by this {@code Statement} object
     * should be made available for retrieval.  The driver will ignore the
     * flag if the SQL statement
     * is not an {@code INSERT} statement, or an SQL statement able to return
     * auto-generated keys (the list of such statements is vendor-specific).
     * <p>
     * <strong>Note:</strong>This method cannot be called on a
     * {@code PreparedStatement} or {@code CallableStatement}.
     *
     * @param sql               an SQL Data Manipulation Language (DML) statement, such as {@code INSERT}, {@code UPDATE} or
     *                          {@code DELETE}; or an SQL statement that returns nothing,
     *                          such as a DDL statement.
     * @param autoGeneratedKeys a flag indicating whether auto-generated keys
     *                          should be made available for retrieval;
     *                          one of the following constants:
     *                          {@code Statement.RETURN_GENERATED_KEYS}
     *                          {@code Statement.NO_GENERATED_KEYS}
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements
     * or (2) 0 for SQL statements that return nothing
     * @throws SQLException                    if a database access error occurs,
     *                                         this method is called on a closed {@code Statement}, the given
     *                                         SQL statement returns a {@code ResultSet} object,
     *                                         the given constant is not one of those allowed, the method is called on a
     *                                         {@code PreparedStatement} or {@code CallableStatement}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method with a constant of Statement.RETURN_GENERATED_KEYS
     * @throws SQLTimeoutException             when the driver has determined that the
     *                                         timeout value that was specified by the {@code setQueryTimeout}
     *                                         method has been exceeded and has at least attempted to cancel
     *                                         the currently running {@code Statement}
     * @since 1.4
     */
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return getStatement().executeUpdate(sql, autoGeneratedKeys);
    }

    /**
     * Executes the given SQL statement and signals the driver that the
     * auto-generated keys indicated in the given array should be made available
     * for retrieval.   This array contains the indexes of the columns in the
     * target table that contain the auto-generated keys that should be made
     * available. The driver will ignore the array if the SQL statement
     * is not an {@code INSERT} statement, or an SQL statement able to return
     * auto-generated keys (the list of such statements is vendor-specific).
     * <p>
     * <strong>Note:</strong>This method cannot be called on a
     * {@code PreparedStatement} or {@code CallableStatement}.
     *
     * @param sql           an SQL Data Manipulation Language (DML) statement, such as {@code INSERT}, {@code UPDATE} or
     *                      {@code DELETE}; or an SQL statement that returns nothing,
     *                      such as a DDL statement.
     * @param columnIndexes an array of column indexes indicating the columns
     *                      that should be returned from the inserted row
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements
     * or (2) 0 for SQL statements that return nothing
     * @throws SQLException                    if a database access error occurs,
     *                                         this method is called on a closed {@code Statement}, the SQL
     *                                         statement returns a {@code ResultSet} object,the second argument
     *                                         supplied to this method is not an
     *                                         {@code int} array whose elements are valid column indexes, the method is called on a
     *                                         {@code PreparedStatement} or {@code CallableStatement}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     * @throws SQLTimeoutException             when the driver has determined that the
     *                                         timeout value that was specified by the {@code setQueryTimeout}
     *                                         method has been exceeded and has at least attempted to cancel
     *                                         the currently running {@code Statement}
     * @since 1.4
     */
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return getStatement().executeUpdate(sql, columnIndexes);
    }

    /**
     * Executes the given SQL statement and signals the driver that the
     * auto-generated keys indicated in the given array should be made available
     * for retrieval.   This array contains the names of the columns in the
     * target table that contain the auto-generated keys that should be made
     * available. The driver will ignore the array if the SQL statement
     * is not an {@code INSERT} statement, or an SQL statement able to return
     * auto-generated keys (the list of such statements is vendor-specific).
     * <p>
     * <strong>Note:</strong>This method cannot be called on a
     * {@code PreparedStatement} or {@code CallableStatement}.
     *
     * @param sql         an SQL Data Manipulation Language (DML) statement, such as {@code INSERT}, {@code UPDATE} or
     *                    {@code DELETE}; or an SQL statement that returns nothing,
     *                    such as a DDL statement.
     * @param columnNames an array of the names of the columns that should be
     *                    returned from the inserted row
     * @return either the row count for {@code INSERT}, {@code UPDATE},
     * or {@code DELETE} statements, or 0 for SQL statements
     * that return nothing
     * @throws SQLException                    if a database access error occurs,
     *                                         this method is called on a closed {@code Statement}, the SQL
     *                                         statement returns a {@code ResultSet} object, the
     *                                         second argument supplied to this method is not a {@code String} array
     *                                         whose elements are valid column names, the method is called on a
     *                                         {@code PreparedStatement} or {@code CallableStatement}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     * @throws SQLTimeoutException             when the driver has determined that the
     *                                         timeout value that was specified by the {@code setQueryTimeout}
     *                                         method has been exceeded and has at least attempted to cancel
     *                                         the currently running {@code Statement}
     * @since 1.4
     */
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        return getStatement().executeUpdate(sql, columnNames);
    }

    /**
     * Executes the given SQL statement, which may return multiple results,
     * and signals the driver that any
     * auto-generated keys should be made available
     * for retrieval.  The driver will ignore this signal if the SQL statement
     * is not an {@code INSERT} statement, or an SQL statement able to return
     * auto-generated keys (the list of such statements is vendor-specific).
     * <p>
     * In some (uncommon) situations, a single SQL statement may return
     * multiple result sets and/or update counts.  Normally you can ignore
     * this unless you are (1) executing a stored procedure that you know may
     * return multiple results or (2) you are dynamically executing an
     * unknown SQL string.
     * <p>
     * The {@code execute} method executes an SQL statement and indicates the
     * form of the first result.  You must then use the methods
     * {@code getResultSet} or {@code getUpdateCount}
     * to retrieve the result, and {@code getMoreResults} to
     * move to any subsequent result(s).
     * <p>
     * <strong>Note:</strong>This method cannot be called on a
     * {@code PreparedStatement} or {@code CallableStatement}.
     *
     * @param sql               any SQL statement
     * @param autoGeneratedKeys a constant indicating whether auto-generated
     *                          keys should be made available for retrieval using the method
     *                          {@code getGeneratedKeys}; one of the following constants:
     *                          {@code Statement.RETURN_GENERATED_KEYS} or
     *                          {@code Statement.NO_GENERATED_KEYS}
     * @return {@code true} if the first result is a {@code ResultSet}
     * object; {@code false} if it is an update count or there are
     * no results
     * @throws SQLException                    if a database access error occurs,
     *                                         this method is called on a closed {@code Statement}, the second
     *                                         parameter supplied to this method is not
     *                                         {@code Statement.RETURN_GENERATED_KEYS} or
     *                                         {@code Statement.NO_GENERATED_KEYS},
     *                                         the method is called on a
     *                                         {@code PreparedStatement} or {@code CallableStatement}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method with a constant of Statement.RETURN_GENERATED_KEYS
     * @throws SQLTimeoutException             when the driver has determined that the
     *                                         timeout value that was specified by the {@code setQueryTimeout}
     *                                         method has been exceeded and has at least attempted to cancel
     *                                         the currently running {@code Statement}
     * @see #getResultSet
     * @see #getUpdateCount
     * @see #getMoreResults
     * @see #getGeneratedKeys
     * @since 1.4
     */
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return getStatement().execute(sql, autoGeneratedKeys);
    }

    /**
     * Executes the given SQL statement, which may return multiple results,
     * and signals the driver that the
     * auto-generated keys indicated in the given array should be made available
     * for retrieval.  This array contains the indexes of the columns in the
     * target table that contain the auto-generated keys that should be made
     * available.  The driver will ignore the array if the SQL statement
     * is not an {@code INSERT} statement, or an SQL statement able to return
     * auto-generated keys (the list of such statements is vendor-specific).
     * <p>
     * Under some (uncommon) situations, a single SQL statement may return
     * multiple result sets and/or update counts.  Normally you can ignore
     * this unless you are (1) executing a stored procedure that you know may
     * return multiple results or (2) you are dynamically executing an
     * unknown SQL string.
     * <p>
     * The {@code execute} method executes an SQL statement and indicates the
     * form of the first result.  You must then use the methods
     * {@code getResultSet} or {@code getUpdateCount}
     * to retrieve the result, and {@code getMoreResults} to
     * move to any subsequent result(s).
     * <p>
     * <strong>Note:</strong>This method cannot be called on a
     * {@code PreparedStatement} or {@code CallableStatement}.
     *
     * @param sql           any SQL statement
     * @param columnIndexes an array of the indexes of the columns in the
     *                      inserted row that should be  made available for retrieval by a
     *                      call to the method {@code getGeneratedKeys}
     * @return {@code true} if the first result is a {@code ResultSet}
     * object; {@code false} if it is an update count or there
     * are no results
     * @throws SQLException                    if a database access error occurs,
     *                                         this method is called on a closed {@code Statement}, the
     *                                         elements in the {@code int} array passed to this method
     *                                         are not valid column indexes, the method is called on a
     *                                         {@code PreparedStatement} or {@code CallableStatement}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     * @throws SQLTimeoutException             when the driver has determined that the
     *                                         timeout value that was specified by the {@code setQueryTimeout}
     *                                         method has been exceeded and has at least attempted to cancel
     *                                         the currently running {@code Statement}
     * @see #getResultSet
     * @see #getUpdateCount
     * @see #getMoreResults
     * @since 1.4
     */
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return getStatement().execute(sql, columnIndexes);
    }

    /**
     * Executes the given SQL statement, which may return multiple results,
     * and signals the driver that the
     * auto-generated keys indicated in the given array should be made available
     * for retrieval. This array contains the names of the columns in the
     * target table that contain the auto-generated keys that should be made
     * available.  The driver will ignore the array if the SQL statement
     * is not an {@code INSERT} statement, or an SQL statement able to return
     * auto-generated keys (the list of such statements is vendor-specific).
     * <p>
     * In some (uncommon) situations, a single SQL statement may return
     * multiple result sets and/or update counts.  Normally you can ignore
     * this unless you are (1) executing a stored procedure that you know may
     * return multiple results or (2) you are dynamically executing an
     * unknown SQL string.
     * <p>
     * The {@code execute} method executes an SQL statement and indicates the
     * form of the first result.  You must then use the methods
     * {@code getResultSet} or {@code getUpdateCount}
     * to retrieve the result, and {@code getMoreResults} to
     * move to any subsequent result(s).
     * <p>
     * <strong>Note:</strong>This method cannot be called on a
     * {@code PreparedStatement} or {@code CallableStatement}.
     *
     * @param sql         any SQL statement
     * @param columnNames an array of the names of the columns in the inserted
     *                    row that should be made available for retrieval by a call to the
     *                    method {@code getGeneratedKeys}
     * @return {@code true} if the next result is a {@code ResultSet}
     * object; {@code false} if it is an update count or there
     * are no more results
     * @throws SQLException                    if a database access error occurs,
     *                                         this method is called on a closed {@code Statement},the
     *                                         elements of the {@code String} array passed to this
     *                                         method are not valid column names, the method is called on a
     *                                         {@code PreparedStatement} or {@code CallableStatement}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     * @throws SQLTimeoutException             when the driver has determined that the
     *                                         timeout value that was specified by the {@code setQueryTimeout}
     *                                         method has been exceeded and has at least attempted to cancel
     *                                         the currently running {@code Statement}
     * @see #getResultSet
     * @see #getUpdateCount
     * @see #getMoreResults
     * @see #getGeneratedKeys
     * @since 1.4
     */
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return getStatement().execute(sql, columnNames);
    }

    /**
     * Retrieves the result set holdability for {@code ResultSet} objects
     * generated by this {@code Statement} object.
     *
     * @return either {@code ResultSet.HOLD_CURSORS_OVER_COMMIT} or
     * {@code ResultSet.CLOSE_CURSORS_AT_COMMIT}
     * @throws SQLException if a database access error occurs or
     *                      this method is called on a closed {@code Statement}
     * @since 1.4
     */
    public int getResultSetHoldability() throws SQLException {
        return getStatement().getResultSetHoldability();
    }

    /**
     * Requests that a {@code Statement} be pooled or not pooled.  The value
     * specified is a hint to the statement pool implementation indicating
     * whether the application wants the statement to be pooled.  It is up to
     * the statement pool manager as to whether the hint is used.
     * <p>
     * The poolable value of a statement is applicable to both internal
     * statement caches implemented by the driver and external statement caches
     * implemented by application servers and other applications.
     * <p>
     * By default, a {@code Statement} is not poolable when created, and
     * a {@code PreparedStatement} and {@code CallableStatement}
     * are poolable when created.
     *
     * @param poolable requests that the statement be pooled if true and
     *                 that the statement not be pooled if false
     * @throws SQLException if this method is called on a closed
     *                      {@code Statement}
     * @since 1.6
     */
    public void setPoolable(boolean poolable) throws SQLException {
        getStatement().setPoolable(poolable);
    }

    /**
     * Returns a  value indicating whether the {@code Statement}
     * is poolable or not.
     *
     * @return {@code true} if the {@code Statement}
     * is poolable; {@code false} otherwise
     * @throws SQLException if this method is called on a closed
     *                      {@code Statement}
     * @see Statement#setPoolable(boolean) setPoolable(boolean)
     * @since 1.6
     */
    public boolean isPoolable() throws SQLException {
        return getStatement().isPoolable();
    }

    /**
     * Specifies that this {@code Statement} will be closed when all its
     * dependent result sets are closed. If execution of the {@code Statement}
     * does not produce any result sets, this method has no effect.
     * <p>
     * <strong>Note:</strong> Multiple calls to {@code closeOnCompletion} do
     * not toggle the effect on this {@code Statement}. However, a call to
     * {@code closeOnCompletion} does effect both the subsequent execution of
     * statements, and statements that currently have open, dependent,
     * result sets.
     *
     * @throws SQLException if this method is called on a closed
     *                      {@code Statement}
     * @since 1.7
     */
    public void closeOnCompletion() throws SQLException {
        getStatement().closeOnCompletion();
    }

    /**
     * Returns a value indicating whether this {@code Statement} will be
     * closed when all its dependent result sets are closed.
     *
     * @return {@code true} if the {@code Statement} will be closed when all
     * of its dependent result sets are closed; {@code false} otherwise
     * @throws SQLException if this method is called on a closed
     *                      {@code Statement}
     * @since 1.7
     */
    public boolean isCloseOnCompletion() throws SQLException {
        return getStatement().isCloseOnCompletion();
    }

    /**
     * Retrieves the current result as an update count; if the result
     * is a {@code ResultSet} object or there are no more results, -1
     * is returned. This method should be called only once per result.
     * <p>
     * This method should be used when the returned row count may exceed
     * {@link Integer#MAX_VALUE}.
     * <p>
     * The default implementation will throw {@code UnsupportedOperationException}
     *
     * @return the current result as an update count; -1 if the current result
     * is a {@code ResultSet} object or there are no more results
     * @throws SQLException if a database access error occurs or
     *                      this method is called on a closed {@code Statement}
     * @see #execute
     * @since 1.8
     */
    public long getLargeUpdateCount() throws SQLException {
        return getStatement().getLargeUpdateCount();
    }

    /**
     * Sets the limit for the maximum number of rows that any
     * {@code ResultSet} object  generated by this {@code Statement}
     * object can contain to the given number.
     * If the limit is exceeded, the excess
     * rows are silently dropped.
     * <p>
     * This method should be used when the row limit may exceed
     * {@link Integer#MAX_VALUE}.
     * <p>
     * The default implementation will throw {@code UnsupportedOperationException}
     *
     * @param max the new max rows limit; zero means there is no limit
     * @throws SQLException if a database access error occurs,
     *                      this method is called on a closed {@code Statement}
     *                      or the condition {@code max >= 0} is not satisfied
     * @see #getMaxRows
     * @since 1.8
     */
    public void setLargeMaxRows(long max) throws SQLException {
        getStatement().setLargeMaxRows(max);
    }

    /**
     * Retrieves the maximum number of rows that a
     * {@code ResultSet} object produced by this
     * {@code Statement} object can contain.  If this limit is exceeded,
     * the excess rows are silently dropped.
     * <p>
     * This method should be used when the returned row limit may exceed
     * {@link Integer#MAX_VALUE}.
     * <p>
     * The default implementation will return {@code 0}
     *
     * @return the current maximum number of rows for a {@code ResultSet}
     * object produced by this {@code Statement} object;
     * zero means there is no limit
     * @throws SQLException if a database access error occurs or
     *                      this method is called on a closed {@code Statement}
     * @see #setMaxRows
     * @since 1.8
     */
    public long getLargeMaxRows() throws SQLException {
        return getStatement().getLargeMaxRows();
    }

    /**
     * Submits a batch of commands to the database for execution and
     * if all commands execute successfully, returns an array of update counts.
     * The {@code long} elements of the array that is returned are ordered
     * to correspond to the commands in the batch, which are ordered
     * according to the order in which they were added to the batch.
     * The elements in the array returned by the method {@code executeLargeBatch}
     * may be one of the following:
     * <OL>
     * <LI>A number greater than or equal to zero -- indicates that the
     * command was processed successfully and is an update count giving the
     * number of rows in the database that were affected by the command's
     * execution
     * <LI>A value of {@code SUCCESS_NO_INFO} -- indicates that the command was
     * processed successfully but that the number of rows affected is
     * unknown
     * <p>
     * If one of the commands in a batch update fails to execute properly,
     * this method throws a {@code BatchUpdateException}, and a JDBC
     * driver may or may not continue to process the remaining commands in
     * the batch.  However, the driver's behavior must be consistent with a
     * particular DBMS, either always continuing to process commands or never
     * continuing to process commands.  If the driver continues processing
     * after a failure, the array returned by the method
     * {@code BatchUpdateException.getLargeUpdateCounts}
     * will contain as many elements as there are commands in the batch, and
     * at least one of the elements will be the following:
     *
     * <LI>A value of {@code EXECUTE_FAILED} -- indicates that the command failed
     * to execute successfully and occurs only if a driver continues to
     * process commands after a command fails
     * </OL>
     * <p>
     * This method should be used when the returned row count may exceed
     * {@link Integer#MAX_VALUE}.
     * <p>
     * The default implementation will throw {@code UnsupportedOperationException}
     *
     * @return an array of update counts containing one element for each
     * command in the batch.  The elements of the array are ordered according
     * to the order in which commands were added to the batch.
     * @throws SQLException        if a database access error occurs,
     *                             this method is called on a closed {@code Statement} or the
     *                             driver does not support batch statements. Throws {@link BatchUpdateException}
     *                             (a subclass of {@code SQLException}) if one of the commands sent to the
     *                             database fails to execute properly or attempts to return a result set.
     * @throws SQLTimeoutException when the driver has determined that the
     *                             timeout value that was specified by the {@code setQueryTimeout}
     *                             method has been exceeded and has at least attempted to cancel
     *                             the currently running {@code Statement}
     * @see #addBatch
     * @see DatabaseMetaData#supportsBatchUpdates
     * @since 1.8
     */
    public long[] executeLargeBatch() throws SQLException {
        return getStatement().executeLargeBatch();
    }

    /**
     * Executes the given SQL statement, which may be an {@code INSERT},
     * {@code UPDATE}, or {@code DELETE} statement or an
     * SQL statement that returns nothing, such as an SQL DDL statement.
     * <p>
     * This method should be used when the returned row count may exceed
     * {@link Integer#MAX_VALUE}.
     * <p>
     * <strong>Note:</strong>This method cannot be called on a
     * {@code PreparedStatement} or {@code CallableStatement}.
     * <p>
     * The default implementation will throw {@code UnsupportedOperationException}
     *
     * @param sql an SQL Data Manipulation Language (DML) statement,
     *            such as {@code INSERT}, {@code UPDATE} or
     *            {@code DELETE}; or an SQL statement that returns nothing,
     *            such as a DDL statement.
     * @return either (1) the row count for SQL Data Manipulation Language
     * (DML) statements or (2) 0 for SQL statements that return nothing
     * @throws SQLException        if a database access error occurs,
     *                             this method is called on a closed {@code Statement}, the given
     *                             SQL statement produces a {@code ResultSet} object, the method is called on a
     *                             {@code PreparedStatement} or {@code CallableStatement}
     * @throws SQLTimeoutException when the driver has determined that the
     *                             timeout value that was specified by the {@code setQueryTimeout}
     *                             method has been exceeded and has at least attempted to cancel
     *                             the currently running {@code Statement}
     * @since 1.8
     */
    public long executeLargeUpdate(String sql) throws SQLException {
        return getStatement().executeLargeUpdate(sql);
    }

    /**
     * Executes the given SQL statement and signals the driver with the
     * given flag about whether the
     * auto-generated keys produced by this {@code Statement} object
     * should be made available for retrieval.  The driver will ignore the
     * flag if the SQL statement
     * is not an {@code INSERT} statement, or an SQL statement able to return
     * auto-generated keys (the list of such statements is vendor-specific).
     * <p>
     * This method should be used when the returned row count may exceed
     * {@link Integer#MAX_VALUE}.
     * <p>
     * <strong>Note:</strong>This method cannot be called on a
     * {@code PreparedStatement} or {@code CallableStatement}.
     * <p>
     * The default implementation will throw {@code SQLFeatureNotSupportedException}
     *
     * @param sql               an SQL Data Manipulation Language (DML) statement,
     *                          such as {@code INSERT}, {@code UPDATE} or
     *                          {@code DELETE}; or an SQL statement that returns nothing,
     *                          such as a DDL statement.
     * @param autoGeneratedKeys a flag indicating whether auto-generated keys
     *                          should be made available for retrieval;
     *                          one of the following constants:
     *                          {@code Statement.RETURN_GENERATED_KEYS}
     *                          {@code Statement.NO_GENERATED_KEYS}
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements
     * or (2) 0 for SQL statements that return nothing
     * @throws SQLException                    if a database access error occurs,
     *                                         this method is called on a closed {@code Statement}, the given
     *                                         SQL statement returns a {@code ResultSet} object,
     *                                         the given constant is not one of those allowed, the method is called on a
     *                                         {@code PreparedStatement} or {@code CallableStatement}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method with a constant of Statement.RETURN_GENERATED_KEYS
     * @throws SQLTimeoutException             when the driver has determined that the
     *                                         timeout value that was specified by the {@code setQueryTimeout}
     *                                         method has been exceeded and has at least attempted to cancel
     *                                         the currently running {@code Statement}
     * @since 1.8
     */
    public long executeLargeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return getStatement().executeLargeUpdate(sql, autoGeneratedKeys);
    }

    /**
     * Executes the given SQL statement and signals the driver that the
     * auto-generated keys indicated in the given array should be made available
     * for retrieval.   This array contains the indexes of the columns in the
     * target table that contain the auto-generated keys that should be made
     * available. The driver will ignore the array if the SQL statement
     * is not an {@code INSERT} statement, or an SQL statement able to return
     * auto-generated keys (the list of such statements is vendor-specific).
     * <p>
     * This method should be used when the returned row count may exceed
     * {@link Integer#MAX_VALUE}.
     * <p>
     * <strong>Note:</strong>This method cannot be called on a
     * {@code PreparedStatement} or {@code CallableStatement}.
     * <p>
     * The default implementation will throw {@code SQLFeatureNotSupportedException}
     *
     * @param sql           an SQL Data Manipulation Language (DML) statement,
     *                      such as {@code INSERT}, {@code UPDATE} or
     *                      {@code DELETE}; or an SQL statement that returns nothing,
     *                      such as a DDL statement.
     * @param columnIndexes an array of column indexes indicating the columns
     *                      that should be returned from the inserted row
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements
     * or (2) 0 for SQL statements that return nothing
     * @throws SQLException                    if a database access error occurs,
     *                                         this method is called on a closed {@code Statement}, the SQL
     *                                         statement returns a {@code ResultSet} object,the second argument
     *                                         supplied to this method is not an
     *                                         {@code int} array whose elements are valid column indexes, the method is called on a
     *                                         {@code PreparedStatement} or {@code CallableStatement}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     * @throws SQLTimeoutException             when the driver has determined that the
     *                                         timeout value that was specified by the {@code setQueryTimeout}
     *                                         method has been exceeded and has at least attempted to cancel
     *                                         the currently running {@code Statement}
     * @since 1.8
     */
    public long executeLargeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return getStatement().executeLargeUpdate(sql, columnIndexes);
    }

    /**
     * Executes the given SQL statement and signals the driver that the
     * auto-generated keys indicated in the given array should be made available
     * for retrieval.   This array contains the names of the columns in the
     * target table that contain the auto-generated keys that should be made
     * available. The driver will ignore the array if the SQL statement
     * is not an {@code INSERT} statement, or an SQL statement able to return
     * auto-generated keys (the list of such statements is vendor-specific).
     * <p>
     * This method should be used when the returned row count may exceed
     * {@link Integer#MAX_VALUE}.
     * <p>
     * <strong>Note:</strong>This method cannot be called on a
     * {@code PreparedStatement} or {@code CallableStatement}.
     * <p>
     * The default implementation will throw {@code SQLFeatureNotSupportedException}
     *
     * @param sql         an SQL Data Manipulation Language (DML) statement,
     *                    such as {@code INSERT}, {@code UPDATE} or
     *                    {@code DELETE}; or an SQL statement that returns nothing,
     *                    such as a DDL statement.
     * @param columnNames an array of the names of the columns that should be
     *                    returned from the inserted row
     * @return either the row count for {@code INSERT}, {@code UPDATE},
     * or {@code DELETE} statements, or 0 for SQL statements
     * that return nothing
     * @throws SQLException                    if a database access error occurs,
     *                                         this method is called on a closed {@code Statement}, the SQL
     *                                         statement returns a {@code ResultSet} object, the
     *                                         second argument supplied to this method is not a {@code String} array
     *                                         whose elements are valid column names, the method is called on a
     *                                         {@code PreparedStatement} or {@code CallableStatement}
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     * @throws SQLTimeoutException             when the driver has determined that the
     *                                         timeout value that was specified by the {@code setQueryTimeout}
     *                                         method has been exceeded and has at least attempted to cancel
     *                                         the currently running {@code Statement}
     * @since 1.8
     */
    public long executeLargeUpdate(String sql, String[] columnNames) throws SQLException {
        return getStatement().executeLargeUpdate(sql, columnNames);
    }

    /**
     * Returns a {@code String} enclosed in single quotes. Any occurrence of a
     * single quote within the string will be replaced by two single quotes.
     *
     * <blockquote>
     * <table class="striped">
     * <caption>Examples of the conversion:</caption>
     * <thead>
     * <tr><th scope="col">Value</th><th scope="col">Result</th></tr>
     * </thead>
     * <tbody style="text-align:center">
     * <tr> <th scope="row">Hello</th> <td>'Hello'</td> </tr>
     * <tr> <th scope="row">G'Day</th> <td>'G''Day'</td> </tr>
     * <tr> <th scope="row">'G''Day'</th>
     * <td>'''G''''Day'''</td> </tr>
     * <tr> <th scope="row">I'''M</th> <td>'I''''''M'</td>
     * </tr>
     *
     * </tbody>
     * </table>
     * </blockquote>
     *
     * @param val a character string
     * @return A string enclosed by single quotes with every single quote
     * converted to two single quotes
     * @throws NullPointerException if val is {@code null}
     * @throws SQLException         if a database access error occurs
     * @implNote JDBC driver implementations may need to provide their own implementation
     * of this method in order to meet the requirements of the underlying
     * datasource.
     * @since 9
     */
    public String enquoteLiteral(String val) throws SQLException {
        return getStatement().enquoteLiteral(val);
    }

    /**
     * Returns a SQL identifier. If {@code identifier} is a simple SQL identifier:
     * <ul>
     * <li>Return the original value if {@code alwaysQuote} is
     * {@code false}</li>
     * <li>Return a delimited identifier if {@code alwaysQuote} is
     * {@code true}</li>
     * </ul>
     * <p>
     * If {@code identifier} is not a simple SQL identifier, {@code identifier} will be
     * enclosed in double quotes if not already present. If the datasource does
     * not support double quotes for delimited identifiers, the
     * identifier should be enclosed by the string returned from
     * {@link DatabaseMetaData#getIdentifierQuoteString}.  If the datasource
     * does not support delimited identifiers, a
     * {@code SQLFeatureNotSupportedException} should be thrown.
     * <p>
     * A {@code SQLException} will be thrown if {@code identifier} contains any
     * characters invalid in a delimited identifier or the identifier length is
     * invalid for the datasource.
     *
     * @param identifier  a SQL identifier
     * @param alwaysQuote indicates if a simple SQL identifier should be
     *                    returned as a quoted identifier
     * @return A simple SQL identifier or a delimited identifier
     * @throws SQLException                    if identifier is not a valid identifier
     * @throws SQLFeatureNotSupportedException if the datasource does not support
     *                                         delimited identifiers
     * @throws NullPointerException            if identifier is {@code null}
     * @implSpec The default implementation uses the following criteria to
     * determine a valid simple SQL identifier:
     * <ul>
     * <li>The string is not enclosed in double quotes</li>
     * <li>The first character is an alphabetic character from a through z, or
     * from A through Z</li>
     * <li>The name only contains alphanumeric characters or the character "_"</li>
     * </ul>
     * <p>
     * The default implementation will throw a {@code SQLException} if:
     * <ul>
     * <li>{@code identifier} contains a {@code null} character or double quote and is not
     * a simple SQL identifier.</li>
     * <li>The length of {@code identifier} is less than 1 or greater than 128 characters
     * </ul>
     * <blockquote>
     * <table class="striped" >
     * <caption>Examples of the conversion:</caption>
     * <thead>
     * <tr>
     * <th scope="col">identifier</th>
     * <th scope="col">alwaysQuote</th>
     * <th scope="col">Result</th></tr>
     * </thead>
     * <tbody>
     * <tr>
     * <th scope="row">Hello</th>
     * <td>false</td>
     * <td>Hello</td>
     * </tr>
     * <tr>
     * <th scope="row">Hello</th>
     * <td>true</td>
     * <td>"Hello"</td>
     * </tr>
     * <tr>
     * <th scope="row">G'Day</th>
     * <td>false</td>
     * <td>"G'Day"</td>
     * </tr>
     * <tr>
     * <th scope="row">"Bruce Wayne"</th>
     * <td>false</td>
     * <td>"Bruce Wayne"</td>
     * </tr>
     * <tr>
     * <th scope="row">"Bruce Wayne"</th>
     * <td>true</td>
     * <td>"Bruce Wayne"</td>
     * </tr>
     * <tr>
     * <th scope="row">GoodDay$</th>
     * <td>false</td>
     * <td>"GoodDay$"</td>
     * </tr>
     * <tr>
     * <th scope="row">Hello"World</th>
     * <td>false</td>
     * <td>SQLException</td>
     * </tr>
     * <tr>
     * <th scope="row">"Hello"World"</th>
     * <td>false</td>
     * <td>SQLException</td>
     * </tr>
     * </tbody>
     * </table>
     * </blockquote>
     * @implNote JDBC driver implementations may need to provide their own implementation
     * of this method in order to meet the requirements of the underlying
     * datasource.
     * @since 9
     */
    public String enquoteIdentifier(String identifier, boolean alwaysQuote) throws SQLException {
        return getStatement().enquoteIdentifier(identifier, alwaysQuote);
    }

    /**
     * Retrieves whether {@code identifier} is a simple SQL identifier.
     *
     * @param identifier a SQL identifier
     * @return true if  a simple SQL identifier, false otherwise
     * @throws NullPointerException if identifier is {@code null}
     * @throws SQLException         if a database access error occurs
     * @implSpec The default implementation uses the following criteria to
     * determine a valid simple SQL identifier:
     * <ul>
     * <li>The string is not enclosed in double quotes</li>
     * <li>The first character is an alphabetic character from a through z, or
     * from A through Z</li>
     * <li>The string only contains alphanumeric characters or the character
     * "_"</li>
     * <li>The string is between 1 and 128 characters in length inclusive</li>
     * </ul>
     *
     * <blockquote>
     * <table class="striped" >
     * <caption>Examples of the conversion:</caption>
     * <thead>
     * <tr>
     * <th scope="col">identifier</th>
     * <th scope="col">Simple Identifier</th>
     * </thead>
     *
     * <tbody>
     * <tr>
     * <th scope="row">Hello</th>
     * <td>true</td>
     * </tr>
     * <tr>
     * <th scope="row">G'Day</th>
     * <td>false</td>
     * </tr>
     * <tr>
     * <th scope="row">"Bruce Wayne"</th>
     * <td>false</td>
     * </tr>
     * <tr>
     * <th scope="row">GoodDay$</th>
     * <td>false</td>
     * </tr>
     * <tr>
     * <th scope="row">Hello"World</th>
     * <td>false</td>
     * </tr>
     * <tr>
     * <th scope="row">"Hello"World"</th>
     * <td>false</td>
     * </tr>
     * </tbody>
     * </table>
     * </blockquote>
     * @implNote JDBC driver implementations may need to provide their own
     * implementation of this method in order to meet the requirements of the
     * underlying datasource.
     * @since 9
     */
    public boolean isSimpleIdentifier(String identifier) throws SQLException {
        return getStatement().isSimpleIdentifier(identifier);
    }

    /**
     * Returns a {@code String} representing a National Character Set Literal
     * enclosed in single quotes and prefixed with a upper case letter N.
     * Any occurrence of a single quote within the string will be replaced
     * by two single quotes.
     *
     * <blockquote>
     * <table class="striped">
     * <caption>Examples of the conversion:</caption>
     * <thead>
     * <tr>
     * <th scope="col">Value</th>
     * <th scope="col">Result</th>
     * </tr>
     * </thead>
     * <tbody>
     * <tr> <th scope="row">Hello</th> <td>N'Hello'</td> </tr>
     * <tr> <th scope="row">G'Day</th> <td>N'G''Day'</td> </tr>
     * <tr> <th scope="row">'G''Day'</th>
     * <td>N'''G''''Day'''</td> </tr>
     * <tr> <th scope="row">I'''M</th> <td>N'I''''''M'</td>
     * <tr> <th scope="row">N'Hello'</th> <td>N'N''Hello'''</td> </tr>
     *
     * </tbody>
     * </table>
     * </blockquote>
     *
     * @param val a character string
     * @return the result of replacing every single quote character in the
     * argument by two single quote characters where this entire result is
     * then prefixed with 'N'.
     * @throws NullPointerException if val is {@code null}
     * @throws SQLException         if a database access error occurs
     * @implNote JDBC driver implementations may need to provide their own implementation
     * of this method in order to meet the requirements of the underlying
     * datasource. An implementation of enquoteNCharLiteral may accept a different
     * set of characters than that accepted by the same drivers implementation of
     * enquoteLiteral.
     * @since 9
     */
    public String enquoteNCharLiteral(String val) throws SQLException {
        return getStatement().enquoteNCharLiteral(val);
    }

    /**
     * Returns the number of columns in this {@code ResultSet} object.
     *
     * @return the number of columns
     * @throws SQLException if a database access error occurs
     */
    public int getColumnCount() throws SQLException {
        return getMetaData().getColumnCount();
    }

    /**
     * Indicates whether the designated column is automatically numbered.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return {@code true} if so; {@code false} otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean isAutoIncrement(int column) throws SQLException {
        return getMetaData().isAutoIncrement(column);
    }

    /**
     * Indicates whether a column's case matters.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return {@code true} if so; {@code false} otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean isCaseSensitive(int column) throws SQLException {
        return getMetaData().isCaseSensitive(column);
    }

    /**
     * Indicates whether the designated column can be used in a where clause.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return {@code true} if so; {@code false} otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean isSearchable(int column) throws SQLException {
        return getMetaData().isSearchable(column);
    }

    /**
     * Indicates whether the designated column is a cash value.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return {@code true} if so; {@code false} otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean isCurrency(int column) throws SQLException {
        return getMetaData().isCurrency(column);
    }

    /**
     * Indicates the nullability of values in the designated column.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return the nullability status of the given column; one of {@code columnNoNulls},
     * {@code columnNullable} or {@code columnNullableUnknown}
     * @throws SQLException if a database access error occurs
     */
    public int isNullable(int column) throws SQLException {
        return getMetaData().isNullable(column);
    }

    /**
     * Indicates whether values in the designated column are signed numbers.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return {@code true} if so; {@code false} otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean isSigned(int column) throws SQLException {
        return getMetaData().isSigned(column);
    }

    /**
     * Indicates the designated column's normal maximum width in characters.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return the normal maximum number of characters allowed as the width
     * of the designated column
     * @throws SQLException if a database access error occurs
     */
    public int getColumnDisplaySize(int column) throws SQLException {
        return getMetaData().getColumnDisplaySize(column);
    }

    /**
     * Gets the designated column's suggested title for use in printouts and
     * displays. The suggested title is usually specified by the SQL {@code AS}
     * clause.  If a SQL {@code AS} is not specified, the value returned from
     * {@code getColumnLabel} will be the same as the value returned by the
     * {@code getColumnName} method.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return the suggested column title
     * @throws SQLException if a database access error occurs
     */
    public String getColumnLabel(int column) throws SQLException {
        return getMetaData().getColumnLabel(column);
    }

    /**
     * Get the designated column's name.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return column name
     * @throws SQLException if a database access error occurs
     */
    public String getColumnName(int column) throws SQLException {
        return getMetaData().getColumnName(column);
    }

    /**
     * Get the designated column's table's schema.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return schema name or "" if not applicable
     * @throws SQLException if a database access error occurs
     */
    public String getSchemaName(int column) throws SQLException {
        return getMetaData().getSchemaName(column);
    }

    /**
     * Get the designated column's specified column size.
     * For numeric data, this is the maximum precision.  For character data, this is the length in characters.
     * For datetime datatypes, this is the length in characters of the String representation (assuming the
     * maximum allowed precision of the fractional seconds component). For binary data, this is the length in bytes.  For the ROWID datatype,
     * this is the length in bytes. 0 is returned for data types where the
     * column size is not applicable.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return precision
     * @throws SQLException if a database access error occurs
     */
    public int getPrecision(int column) throws SQLException {
        return getMetaData().getPrecision(column);
    }

    /**
     * Gets the designated column's number of digits to right of the decimal point.
     * 0 is returned for data types where the scale is not applicable.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return scale
     * @throws SQLException if a database access error occurs
     */
    public int getScale(int column) throws SQLException {
        return getMetaData().getScale(column);
    }

    /**
     * Gets the designated column's table name.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return table name or "" if not applicable
     * @throws SQLException if a database access error occurs
     */
    public String getTableName(int column) throws SQLException {
        return getMetaData().getTableName(column);
    }

    /**
     * Gets the designated column's table's catalog name.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return the name of the catalog for the table in which the given column
     * appears or "" if not applicable
     * @throws SQLException if a database access error occurs
     */
    public String getCatalogName(int column) throws SQLException {
        return getMetaData().getCatalogName(column);
    }

    /**
     * Retrieves the designated column's SQL type.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return SQL type from java.sql.Types
     * @throws SQLException if a database access error occurs
     * @see Types
     */
    public int getColumnType(int column) throws SQLException {
        return getMetaData().getColumnType(column);
    }

    /**
     * Retrieves the designated column's database-specific type name.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return type name used by the database. If the column type is
     * a user-defined type, then a fully-qualified type name is returned.
     * @throws SQLException if a database access error occurs
     */
    public String getColumnTypeName(int column) throws SQLException {
        return getMetaData().getColumnTypeName(column);
    }

    /**
     * Indicates whether the designated column is definitely not writable.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return {@code true} if so; {@code false} otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean isReadOnly(int column) throws SQLException {
        return getMetaData().isReadOnly(column);
    }

    /**
     * Indicates whether it is possible for a write on the designated column to succeed.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return {@code true} if so; {@code false} otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean isWritable(int column) throws SQLException {
        return getMetaData().isWritable(column);
    }

    /**
     * Indicates whether a write on the designated column will definitely succeed.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return {@code true} if so; {@code false} otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean isDefinitelyWritable(int column) throws SQLException {
        return getMetaData().isDefinitelyWritable(column);
    }

    /**
     * <p>Returns the fully-qualified name of the Java class whose instances
     * are manufactured if the method {@code ResultSet.getObject}
     * is called to retrieve a value
     * from the column.  {@code ResultSet.getObject} may return a subclass of the
     * class returned by this method.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return the fully-qualified name of the class in the Java programming
     * language that would be used by the method
     * {@code ResultSet.getObject} to retrieve the value in the specified
     * column. This is the class name used for custom mapping.
     * @throws SQLException if a database access error occurs
     * @since 1.2
     */
    public String getColumnClassName(int column) throws SQLException {
        return getMetaData().getColumnClassName(column);
    }
}
