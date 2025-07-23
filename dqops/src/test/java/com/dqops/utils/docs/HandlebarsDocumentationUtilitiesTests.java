/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs;

import com.dqops.BaseTest;
import com.github.jknack.handlebars.Template;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;

@SpringBootTest
public class HandlebarsDocumentationUtilitiesTests extends BaseTest {
    @BeforeEach
    void setUp() {
        HandlebarsDocumentationUtilities.configure(Path.of("."));
    }

    @Test
    void compileTemplate_whenTemplateGiven_thenIsCompiled() {
        Template template = HandlebarsDocumentationUtilities.compileTemplate("sensors/sensor_documentation");
        Assertions.assertNotNull(template);
    }
}
