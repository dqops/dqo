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
package com.dqops.cli.terminal;

/**
 * Provides instances of the {@link TerminalReader} and {@link TerminalWriter} that are created when needed.
 */
public interface TerminalFactory {
    /**
     * Returns the instance of the terminal reader.
     *
     * @return Terminal reader.
     */
    TerminalReader getReader();

    /**
     * Returns the instance of the terminal writer.
     *
     * @return Terminal writer.
     */
    TerminalWriter getWriter();
}
