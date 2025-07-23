/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.exceptions;

/**
 * Exception thrown from the terminal reader when a parameter value was prompted, but the mode was --headless and asking user is disabled.
 */
public class CliRequiredParameterMissingException extends BaseCliParameterException {
    private final String parameterName;

    /**
     * Creates a parameter missing exception when a parameter prompt (asking for a parameter value using an interactive prompt) cannot be performed
     * because the application was started in a --headless mode (no UI, no prompting) and a required parameter was not provided.
     * @param parameterName Parameter name that was prompted.
     */
    public CliRequiredParameterMissingException(String parameterName) {
        this.parameterName = parameterName;
    }

    /**
     * Parameter name.
     * @return Parameter name.
     */
    public String getParameterName() {
        return parameterName;
    }
}
