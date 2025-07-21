/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.cli.commands.connection;

import com.dqops.BaseTest;
import com.dqops.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit tests for ConnectionAddCliCommand
 */
@SpringBootTest
public class ConnectionAddCliCommandTest extends BaseTest {
    @Test
    void ConnectionAddCliCommand_whenCreatedFromIoC_instanceIsCreated() {
        ConnectionAddCliCommand bean = BeanFactoryObjectMother.getBeanFactory().getBean(ConnectionAddCliCommand.class);
        Assertions.assertNotNull(bean);
    }
}
