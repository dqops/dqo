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
package com.dqops.execution.sqltemplates.rendering;

import com.dqops.core.configuration.DqoPythonConfigurationProperties;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.sensors.finder.SensorDefinitionFindResult;
import com.dqops.execution.sensors.progress.BeforeSqlTemplateRenderEvent;
import com.dqops.execution.sensors.progress.SensorExecutionProgressListener;
import com.dqops.execution.sensors.progress.SqlTemplateRenderedRenderedEvent;
import com.dqops.utils.python.PythonCallerService;
import com.dqops.utils.python.PythonExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.time.Instant;

/**
 * SQL template rendering service that will populate the template with the parameters.
 */
@Component
public class JinjaTemplateRenderServiceImpl implements JinjaTemplateRenderService {
    private final PythonCallerService pythonCallerService;
    private final DqoPythonConfigurationProperties pythonConfigurationProperties;

    /**
     * Creates a jinja template rendering service.
     * @param pythonCallerService Python call service.
     * @param pythonConfigurationProperties Python configuration properties. We need to find the python file that will run jinja2.
     */
    @Autowired
    public JinjaTemplateRenderServiceImpl(PythonCallerService pythonCallerService,
                                          DqoPythonConfigurationProperties pythonConfigurationProperties) {
        this.pythonCallerService = pythonCallerService;
        this.pythonConfigurationProperties = pythonConfigurationProperties;
    }

    /**
     * Renders a single template given the template string.
     * @param templateText Template to render as a python code (not a path to a file).
     * @param templateRenderParameters Template rendering parameters.
     * @return Filled (rendered) template.
     */
    @Override
    public String renderTemplate(String templateText, JinjaTemplateRenderParameters templateRenderParameters) {
        JinjaTemplateRenderInput inputDto = new JinjaTemplateRenderInput();
        inputDto.setTemplateText(templateText);
        inputDto.setTemplateLastModified(Instant.now());
        inputDto.setParameters(templateRenderParameters);
        String evaluateTemplatesModule = this.pythonConfigurationProperties.getEvaluateTemplatesModule();

        JinjaTemplateRenderOutput output =
				this.pythonCallerService.executePythonHomeScript(inputDto, evaluateTemplatesModule, JinjaTemplateRenderOutput.class);

        if (output.getError() != null) {
            throw new PythonExecutionException("Quality check template failed to render, error: " + output.getError());
        }
        return output.getResult();
    }

    /**
     * Render a template for a sensor definition that was found in the user home or dqo home. This method prefers to use disk based template loading.
     *
     * @param executionContext    Check execution context with paths to the user home and dqo home.
     * @param sensorFindResult         Sensor definition (template) find result.
     * @param templateRenderParameters Template rendering parameters that are passed to the jinja2 template file and are usable in the template code.
     * @param progressListener         Progress listener that receives information about rendered templates.
     * @return Rendered SQL template.
     */
    @Override
    public String renderTemplate(ExecutionContext executionContext,
                                 SensorDefinitionFindResult sensorFindResult,
                                 JinjaTemplateRenderParameters templateRenderParameters,
                                 SensorExecutionProgressListener progressListener) {
        JinjaTemplateRenderInput inputDto = new JinjaTemplateRenderInput();
        inputDto.setTemplateText(sensorFindResult.getSqlTemplateText());
        inputDto.setTemplateLastModified(sensorFindResult.getSqlTemplateLastModified());
        inputDto.setHomeType(sensorFindResult.getHome());
        String relativePathToTemplate = sensorFindResult.getTemplateFilePath() != null ?
                sensorFindResult.getTemplateFilePath().toRelativePath().toString().replace('\\', '/')
                : null;
        inputDto.setTemplateHomePath(relativePathToTemplate);
        Path userHomePhysicalPath = executionContext.getUserHomeContext().getHomeRoot().getPhysicalAbsolutePath();
        if (userHomePhysicalPath != null) {
            inputDto.setUserHomePath(userHomePhysicalPath.toString().replace('\\', '/'));
        }
//        Path dqoHomePhysicalPath = checkExecutionContext.getDqoHomeContext().getHomeRoot().getPhysicalAbsolutePath();
//        if (dqoHomePhysicalPath != null) {
//            inputDto.setDqoHomePath(dqoHomePhysicalPath.toString().replace('\\', '/'));
//        }

        inputDto.setParameters(templateRenderParameters);
        String evaluateTemplatesModule = this.pythonConfigurationProperties.getEvaluateTemplatesModule();

        progressListener.onBeforeSqlTemplateRender(new BeforeSqlTemplateRenderEvent(inputDto));
        JinjaTemplateRenderOutput output =
				this.pythonCallerService.executePythonHomeScript(inputDto, evaluateTemplatesModule, JinjaTemplateRenderOutput.class);

        if (output.getError() != null) {
            progressListener.onSqlTemplateRendered(new SqlTemplateRenderedRenderedEvent(inputDto, output));
            throw new PythonExecutionException("Quality check template failed to render, error: " + output.getError() + ", template path in the home folder: " + relativePathToTemplate);
        }

        progressListener.onSqlTemplateRendered(new SqlTemplateRenderedRenderedEvent(inputDto, output));
        return output.getResult();
    }
}
