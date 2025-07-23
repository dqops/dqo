/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.rest.models.credentials;

/**
 * Credential type - a text credential or a binary credential that must be updated as a base64 value.
 */
public enum CredentialType {
    /**
     * The credential is a valid text value.
     */
    text,

    /**
     * The credential is a binary file that cannot be parsed as an utf-8 txt value.
     */
    binary
}
