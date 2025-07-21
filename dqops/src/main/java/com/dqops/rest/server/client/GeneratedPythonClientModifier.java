/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.rest.server.client;

import com.dqops.utils.exceptions.DqoRuntimeException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Helper class which modifies the generated python client code.
 */
public class GeneratedPythonClientModifier {

    /**
     * Modifies generated python client. It reconfigures default client timeout value to match default dqo timeout.
     * @param projectRoot Path to project root
     */
    public static void modifyClient(Path projectRoot){
        Path pythonClientFilePath = projectRoot.resolve("../distribution/python/dqops/client/client.py").toAbsolutePath();
        String content = null;
        try {
            content = Files.readString(pythonClientFilePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new DqoRuntimeException(e);
        }
        String newContent = content.replaceAll(
                "_timeout: Optional\\[httpx\\.Timeout\\] = field\\(default=None, kw_only=True\\)",
                "_timeout: Optional[httpx.Timeout] = field(default=httpx.Timeout(120.0), kw_only=True)");

        try {
            Files.write(pythonClientFilePath, newContent.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new DqoRuntimeException(e);
        }
    }

}
