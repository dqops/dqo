/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
