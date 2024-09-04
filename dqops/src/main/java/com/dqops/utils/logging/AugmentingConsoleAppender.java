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
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.joran.spi.ConsoleTarget;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyPayload;
import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.event.KeyValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Special console appender for JSON logging that adds extra fields to every logged message.
 */
public class AugmentingConsoleAppender extends ConsoleAppender<ILoggingEvent> {
    private boolean quoteMessage;
    private final DqoCloudApiKeyPayload apiKeyPayload;
    private final Integer ignoredJsonMessageMaxLength;

    /**
     * Creates an augmenting console appender.
     * @param quoteMessage  Encodes all double quotes and backslashes the message field again.
     * @param apiKeyPayload DQOps Cloud api key.
     * @param ignoredJsonMessageMaxLength     Maximum length of a message field, the rest is truncated.
     */
    public AugmentingConsoleAppender(boolean quoteMessage,
                                     DqoCloudApiKeyPayload apiKeyPayload,
                                     Integer ignoredJsonMessageMaxLength) {
        this.quoteMessage = quoteMessage;
        this.apiKeyPayload = apiKeyPayload;
        this.ignoredJsonMessageMaxLength = ignoredJsonMessageMaxLength;
    }

    /**
     * Appends a wrapped message with additional information added to each logging event.
     * @param originalEvent Original event.
     */
    @Override
    protected void append(ILoggingEvent originalEvent) {
        Object[] originalArgumentArray = originalEvent.getArgumentArray();
        Object[] augmentedArgumentArray = new Object[originalArgumentArray == null ? 1 : originalArgumentArray.length + 1];
        if (originalArgumentArray != null) {
            System.arraycopy(originalArgumentArray, 0, augmentedArgumentArray, 1, originalArgumentArray.length);
        }
        augmentedArgumentArray[0] = StructuredArguments.keyValue("tag", "dqo"); // tagging with the expected docker container name, when json logs are nested in docker logs

        List<KeyValuePair> keyValuePairs = originalEvent.getKeyValuePairs() != null
                ? new ArrayList<>(originalEvent.getKeyValuePairs()) : new ArrayList<>();
        keyValuePairs.add(new KeyValuePair("stream", Objects.equals(this.getTarget(), ConsoleTarget.SystemErr.getName()) ? "stderr" : "stdout"));

        if (apiKeyPayload != null) {
            keyValuePairs.add(new KeyValuePair("tenantId", apiKeyPayload.getTenantId() + "/" + apiKeyPayload.getTenantGroup()));
            if (apiKeyPayload.getAccountName() !=  null) {
                keyValuePairs.add(new KeyValuePair("account", apiKeyPayload.getAccountName()));
            }
        }

        EncodingLoggingEvent augmentedLoggingEvent = new EncodingLoggingEvent(
                originalEvent, augmentedArgumentArray, this.quoteMessage, keyValuePairs, this.ignoredJsonMessageMaxLength);

        super.append(augmentedLoggingEvent);
    }
}
