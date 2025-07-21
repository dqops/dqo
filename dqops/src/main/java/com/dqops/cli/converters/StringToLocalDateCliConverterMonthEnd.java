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

public class StringToLocalDateCliConverterMonthEnd extends StringToLocalDateCliConverterAbstract {
    /**
     * Determine whether the date resulting from converting from a yyyy.MM format should be from the end of the month.
     *
     * @return True if the conversion of yyyy.MM should consider end of the month. False if the first day should be considered.
     */
    @Override
    protected boolean getDayAtEndMonthBias() {
        return true;
    }
}
