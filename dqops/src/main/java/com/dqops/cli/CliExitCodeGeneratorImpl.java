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
package com.dqops.cli;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Exit code generator that caches the exit code that should be returned.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CliExitCodeGeneratorImpl implements CliExitCodeGenerator {
    private int exitCode;

    /**
     * Returns the exit code that is returned back to the operating system when the application quits.
     * @return Process exit code.
     */
    @Override
    public int getExitCode() {
        return this.exitCode;
    }

    /**
     * Stores the exit code that will be returned to the operating system.
     *
     * @param exitCode Exit code.
     */
    @Override
    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }
}
