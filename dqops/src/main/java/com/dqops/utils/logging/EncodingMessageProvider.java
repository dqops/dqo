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

package com.dqops.utils.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.JsonGenerator;
import net.logstash.logback.composite.loggingevent.MessageJsonProvider;

import java.io.IOException;

/**
 * Custom message formatter that formats the log message inside a JSON message, adding additional quoting to double quotes.
 */
public class EncodingMessageProvider extends MessageJsonProvider {
    /**
     * Writes a message part to the stream, applying an extra encoding of double quotes.
     *
     * @param generator Json generator.
     * @param event     Original event to be encoded and written.
     * @throws IOException
     */
    @Override
    public void writeTo(JsonGenerator generator, ILoggingEvent event) throws IOException {
        EncodingLoggingEvent encodingLoggingEvent = new EncodingLoggingEvent(event);
        super.writeTo(generator, encodingLoggingEvent);
    }
}
