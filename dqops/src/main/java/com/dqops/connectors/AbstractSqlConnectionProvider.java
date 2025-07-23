/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors;

import com.dqops.metadata.sources.ColumnTypeSnapshotSpec;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Base class for connection providers that are using SQL.
 */
public abstract class AbstractSqlConnectionProvider implements ConnectionProvider {
    /**
     * Formats a constant for the target database.
     *
     * @param constant   Constant to be formatted.
     * @param columnType Column type snapshot.
     * @return Formatted constant.
     */
    @Override
    public String formatConstant(Object constant, ColumnTypeSnapshotSpec columnType) {
        if (constant == null) {
            return "NULL";
        }

        if (constant instanceof String) {
            String asString = (String)constant;
            return "'" + asString.replace("'", "''") + "'";
        }

        if (constant instanceof Integer) {
            Integer asInteger = (Integer)constant;
            return asInteger.toString();
        }

        if (constant instanceof Short) {
            Short asShort = (Short)constant;
            return asShort.toString();
        }

        if (constant instanceof Long) {
            Long asLong = (Long)constant;
            return asLong.toString();
        }

        if (constant instanceof Double) {
            Double asDouble = (Double)constant;
            return asDouble.toString();
        }

        if (constant instanceof Float) {
            Float asFloat = (Float)constant;
            return asFloat.toString();
        }

        if (constant instanceof Boolean) {
            Boolean asBoolean = (Boolean)constant;
            return asBoolean ? "1" : "0";
        }

        if (constant instanceof LocalDate) {
            LocalDate asLocalDate = (LocalDate)constant;
            return "'" + asLocalDate.format(DateTimeFormatter.ISO_DATE) + "'";
        }

        if (constant instanceof LocalTime) {
            LocalTime asLocalTime = (LocalTime)constant;
            return "'" + asLocalTime.format(DateTimeFormatter.ISO_TIME) + "'";
        }

        if (constant instanceof LocalDateTime) {
            LocalDateTime asLocalTimeTime = (LocalDateTime)constant;
            return "'" + asLocalTimeTime.format(DateTimeFormatter.ISO_DATE_TIME) + "'";
        }

        if (constant instanceof Instant) {
            Instant asInstant = (Instant)constant;
            return "'" + asInstant + "'";
        }

        return constant.toString();
    }
}
