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
