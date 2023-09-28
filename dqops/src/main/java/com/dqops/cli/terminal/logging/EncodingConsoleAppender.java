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

package com.dqops.cli.terminal.logging;

import ch.qos.logback.core.ConsoleAppender;

import java.io.OutputStream;

/**
 * Console appender that wraps the output stream with a helper that converts characters that must be encoded before they are sent to the docker log.
 */
public class EncodingConsoleAppender<E> extends ConsoleAppender<E> {
    private boolean encodeCharacters;

    public EncodingConsoleAppender() {
    }

    /**
     * Returns true if encoding selected characters is enabled.
     * @return True when encoding is enabled.
     */
    public boolean isEncodeCharacters() {
        return encodeCharacters;
    }

    /**
     * Enables or disables encoding of characters.
     * @param encodeCharacters True when characters should be encoded.
     */
    public void setEncodeCharacters(boolean encodeCharacters) {
        this.encodeCharacters = encodeCharacters;
    }

    /**
     * <p>
     * Sets the @link OutputStream} where the log output will go. The specified
     * <code>OutputStream</code> must be opened by the user and be writable. The
     * <code>OutputStream</code> will be closed when the appender instance is
     * closed.
     *
     * @param outputStream An already opened OutputStream.
     */
    @Override
    public void setOutputStream(OutputStream outputStream) {
        if (this.encodeCharacters) {
            super.setOutputStream(new LogQuotingOutputStream(outputStream));
        } else {
            super.setOutputStream(outputStream);
        }
    }
}
