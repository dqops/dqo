/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.converters;

import com.dqops.utils.datetime.StringToLocalDateConverter;
import picocli.CommandLine.ITypeConverter;

import java.time.LocalDate;

public abstract class StringToLocalDateCliConverterAbstract implements ITypeConverter<LocalDate> {
    @Override
    public LocalDate convert(String value) {
        LocalDate result = StringToLocalDateConverter.convertFromYearMonthDay(value);
        if (result != null) {
            return result;
        }

        result = StringToLocalDateConverter.convertFromYearMonth(value, this.getDayAtEndMonthBias());
        if (result == null) {
            throw new IllegalArgumentException("Incorrect date format (expected yyyy.MM or yyyy.MM.dd)");
        }
        return result;
    }

    /**
     * Determine whether the date resulting from converting from a yyyy.MM format should be from the end of the month.
     * @return True if the conversion of yyyy.MM should consider end of the month. False if the first day should be considered.
     */
    protected abstract boolean getDayAtEndMonthBias();
}
