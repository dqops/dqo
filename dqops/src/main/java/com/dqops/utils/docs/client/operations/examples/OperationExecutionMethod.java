/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.docs.client.operations.examples;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum OperationExecutionMethod {
    curl("curl", "bash"),
    python_sync("Python sync client", "python"),
    python_async("Python async client", "python"),
    auth_python_sync("Python auth sync client", "python"),
    auth_python_async("Python auth async client", "python");

    private final String navbarHeader;
    private final String codeFormatting;
}
