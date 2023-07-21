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
package com.dqops.utils.python;

import com.dqops.BaseTest;
import com.dqops.core.configuration.DqoConfigurationProperties;
import com.dqops.core.configuration.DqoConfigurationPropertiesObjectMother;
import com.dqops.core.configuration.DqoPythonConfigurationProperties;
import com.dqops.core.configuration.DqoPythonConfigurationPropertiesObjectMother;
import com.dqops.execution.sqltemplates.rendering.JinjaTemplateRenderInput;
import com.dqops.execution.sqltemplates.rendering.JinjaTemplateRenderOutput;
import com.dqops.execution.sqltemplates.rendering.JinjaTemplateRenderParameters;
import com.dqops.utils.serialization.JsonSerializerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PythonCallerServiceImplTests extends BaseTest {
    private PythonCallerServiceImpl sut;
    private DqoConfigurationProperties configurationProperties;
    private PythonVirtualEnvService pythonVirtualEnvService;
    private DqoPythonConfigurationProperties pythonConfigurationProperties;

    @BeforeEach
    void setUp() {
		this.configurationProperties = DqoConfigurationPropertiesObjectMother.getDefaultCloned();
		this.pythonVirtualEnvService = PythonVirtualEnvServiceObjectMother.getDefault();
        this.pythonConfigurationProperties = DqoPythonConfigurationPropertiesObjectMother.getDefaultCloned();
        this.sut = new PythonCallerServiceImpl(configurationProperties, pythonConfigurationProperties,
                new JsonSerializerImpl(), pythonVirtualEnvService);
    }

    @Test
    void executePythonHomeScript_whenTemplateEvaluationPythonCalledWithOneObject_thenIsExecuted() {
        JinjaTemplateRenderInput inputDto = new JinjaTemplateRenderInput();
        inputDto.setTemplateText("sample template content");
        inputDto.setParameters(new JinjaTemplateRenderParameters());
        String pythonModulePath = this.pythonConfigurationProperties.getEvaluateTemplatesModule();

        JinjaTemplateRenderOutput output =
				this.sut.executePythonHomeScript(inputDto, pythonModulePath, JinjaTemplateRenderOutput.class);

        Assertions.assertEquals("sample template content", output.getTemplate());
        Assertions.assertEquals("sample template content", output.getResult());
    }

    @Test
    void executePythonHomeScript_whenTemplateEvaluationPythonCalledTwice_thenIsExecuted() {
        String pythonModulePath = this.pythonConfigurationProperties.getEvaluateTemplatesModule();

        JinjaTemplateRenderInput inputDto1 = new JinjaTemplateRenderInput();
        inputDto1.setTemplateText("sample template content");
        inputDto1.setParameters(new JinjaTemplateRenderParameters());

        JinjaTemplateRenderOutput output1 =
				this.sut.executePythonHomeScript(inputDto1, pythonModulePath, JinjaTemplateRenderOutput.class);

        Assertions.assertEquals("sample template content", output1.getTemplate());
        Assertions.assertEquals("sample template content", output1.getResult());

        JinjaTemplateRenderInput inputDto2 = new JinjaTemplateRenderInput();
        inputDto2.setTemplateText("sample template content2");
        inputDto2.setParameters(new JinjaTemplateRenderParameters());

        JinjaTemplateRenderOutput output2 =
				this.sut.executePythonHomeScript(inputDto2, pythonModulePath, JinjaTemplateRenderOutput.class);

        Assertions.assertEquals("sample template content2", output2.getTemplate());
        Assertions.assertEquals("sample template content2", output2.getResult());
    }

    @Test
    void executePythonHomeScriptAndFinish_whenTemplateEvaluationPythonCalledWithTwoObjects_thenIsExecutedAndReturnsTwoResponses() {
        JinjaTemplateRenderInput inputDto1 = new JinjaTemplateRenderInput();
        inputDto1.setTemplateText("sample template content");
        inputDto1.setParameters(new JinjaTemplateRenderParameters());
        JinjaTemplateRenderInput inputDto2 = new JinjaTemplateRenderInput();
        inputDto2.setTemplateText("content2");
        inputDto2.setParameters(new JinjaTemplateRenderParameters());
        List<JinjaTemplateRenderInput> inputs = new ArrayList<JinjaTemplateRenderInput>();
        inputs.add(inputDto1);
        inputs.add(inputDto2);
        String pythonModulePath = this.pythonConfigurationProperties.getEvaluateTemplatesModule();

        List<JinjaTemplateRenderOutput> outputs =
				this.sut.executePythonHomeScriptAndFinish(inputs, pythonModulePath, JinjaTemplateRenderOutput.class);

        Assertions.assertEquals(2, outputs.size());
        Assertions.assertEquals("sample template content", outputs.get(0).getTemplate());
        Assertions.assertEquals("sample template content", outputs.get(0).getResult());
        Assertions.assertEquals("content2", outputs.get(1).getTemplate());
        Assertions.assertEquals("content2", outputs.get(1).getResult());
    }
}
