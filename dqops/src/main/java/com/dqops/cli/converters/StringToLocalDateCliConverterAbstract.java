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
