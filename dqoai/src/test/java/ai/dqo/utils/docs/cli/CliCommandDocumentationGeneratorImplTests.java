/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.utils.docs.cli;

import ai.dqo.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CliCommandDocumentationGeneratorImplTests extends BaseTest {
    private CliCommandDocumentationGeneratorImpl sut;

    @BeforeEach
    void setUp() {
        this.sut = new CliCommandDocumentationGeneratorImpl(new CliCommandDocumentationModelFactoryImpl());
    }

    @Test
    void createCommandModels_whenCalled_generatesModelsForAllCliCommands() {
        List<CliRootCommandDocumentationModel> commandModels = this.sut.createCommandModels();
        Assertions.assertTrue(commandModels.size() > 1);
    }
}
