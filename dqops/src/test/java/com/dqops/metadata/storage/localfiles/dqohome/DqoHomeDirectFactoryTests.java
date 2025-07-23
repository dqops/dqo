/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.dqohome;

import com.dqops.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;

@SpringBootTest
public class DqoHomeDirectFactoryTests extends BaseTest {
    @Test
    void openDqoHome_whenPathToHomeGiven_thenOpensDqoHome() {
        Path dqoHomePath = Path.of(System.getenv("DQO_HOME"));
        DqoHomeContext dqoHomeContext = DqoHomeDirectFactory.openDqoHome(dqoHomePath, false);
        Assertions.assertNotNull(dqoHomeContext);
        Assertions.assertTrue(dqoHomeContext.getDqoHome().getSensors().size() > 10);
    }
}
