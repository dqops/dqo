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
