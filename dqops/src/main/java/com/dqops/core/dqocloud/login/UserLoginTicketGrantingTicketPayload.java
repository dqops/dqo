/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
