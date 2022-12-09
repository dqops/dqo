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
package ai.dqo.execution.sqltemplates;

import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindResult;

/**
 * SQL template rendering service that will populate the template with the parameters.
 */
public interface JinjaTemplateRenderService {
    /**
     * Renders a single template given the template string.
     * @param templateText Template to render as a python code (not a path to a file).
     * @param templateRenderParameters Template rendering parameters.
     * @return Filled (rendered) template.
     */
    String renderTemplate(String templateText, JinjaTemplateRenderParameters templateRenderParameters);

    /**
     * Render a template for a sensor definition that was found in the user home or dqo home. This method prefers to use disk based template loading.
     * @param executionContext Check execution context with paths to the user home and dqo home.
     * @param sensorFindResult Sensor definition (template) find result.
     * @param templateRenderParameters Template rendering parameters that are passed to the jinja2 template file and are usable in the template code.
     * @param progressListener Progress listener that receives information about rendered templates.
     * @return Rendered SQL template.
     */
    String renderTemplate(ExecutionContext executionContext,
                          SensorDefinitionFindResult sensorFindResult,
                          JinjaTemplateRenderParameters templateRenderParameters,
                          CheckExecutionProgressListener progressListener);
}
