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
