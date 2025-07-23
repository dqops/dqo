/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.configuration;

import com.dqops.BaseTest;
import com.dqops.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DqoUserConfigurationPropertiesTests extends BaseTest {
    private DqoUserConfigurationProperties sut;

    @BeforeEach
    void setUp() {
        this.sut = BeanFactoryObjectMother.getBeanFactory().getBean(DqoUserConfigurationProperties.class);
    }

    @Test
    void dqo_whenHomeRetrieved_returnsConfigurationForHomeFolder() {
        String home = this.sut.getHome();
        String dqo_home = System.getenv("DQO_HOME");
        assertNotNull(dqo_home);
        assertNotNull(home);
        assertTrue(Files.isDirectory(Path.of(home)));
    }
}
