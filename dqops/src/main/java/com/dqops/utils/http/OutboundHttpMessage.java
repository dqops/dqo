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

package com.dqops.utils.http;

import lombok.Data;
import org.springframework.http.MediaType;

/**
 * A message that is queued to be sent outside using a REST API one-way call.
 */
@Data
public class OutboundHttpMessage {
    /**
     * The target url.
     */
    private String url;

    /**
     * The message to sent. It should be a JSON object serialized to a string.
     */
    private String message;

    /**
     * MIME content type. The default content type is an application/json.
     */
    private String mimeType = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8";

    /**
     * The HTTP operation. The default is "POST".
     */
    private String method = "POST";

    /**
     * The number of retries left for this message. When null, it will be configured when the message is added to the queue.
     */
    private Integer remainingRetries;

    /**
     * Default constructor.
     */
    public OutboundHttpMessage() {
    }

    /**
     * Create a message given the url and a JSON message.
     * @param url Target url.
     * @param serializedJson Serialized JSON to send.
     */
    public OutboundHttpMessage(String url, String serializedJson) {
        this.url = url;
        this.message = serializedJson;
    }
}
