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

package com.dqops.core.dqocloud.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

import java.time.Instant;

/**
 * Object that is signed by the DQOps Cloud and is used as a parameter with a base url to the return address used to authenticate
 * a remote user who is logging to the DQOps instance. This object with a signature (and converted to hex) is sent as part of a request to the login page.
 */
@Data
public class UserLoginTicketGrantingTicketPayload {
    /**
     * Tenant id.
     */
    @JsonPropertyDescription("Tenant id.")
    @JsonProperty("tid")
    private String tenantId;

    /**
     * Tenant group.
     */
    @JsonPropertyDescription("Tenant group.")
    @JsonProperty("tg")
    private int tenantGroup;

    /**
     * The expiration timestamp.
     */
    @JsonPropertyDescription("The expiration timestamp.")
    @JsonProperty("exp")
    private Instant expiresAt;

    /**
     * Random salt.
     */
    @JsonPropertyDescription("Random salt.")
    @JsonProperty("st")
    private long salt;

    /**
     * The base return url that is authorized.
     */
    @JsonPropertyDescription("The base return url that is authorized.")
    @JsonProperty("url")
    private String returnUrl;

    /**
     * Encrypted client secret - a passcode used to sign and verify the token. DQOps Cloud decrypts this token and uses it sign the response token.
     */
    @JsonPropertyDescription("Encrypted client secret - a passcode used to sign and verify the token. DQOps Cloud decrypts this token and uses it sign the response token.")
    @JsonProperty("cse")
    private String clientSecretEncrypted;
}
