/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.sqltemplates.rendering;

import com.dqops.execution.ExecutionContext;
import com.dqops.execution.sensors.finder.SensorDefinitionFindResult;
import com.dqops.execution.sensors.progress.SensorExecutionProgressListener;

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
                          SensorExecutionProgressListener progressListener);

    /**
     * Render an error sampling template for a sensor definition that was found in the user home or dqo home. This method prefers to use disk based template loading.
     *
     * @param executionContext    Check execution context with paths to the user home and dqo home.
     * @param sensorFindResult         Sensor definition (template) find result.
     * @param templateRenderParameters Template rendering parameters that are passed to the jinja2 template file and are usable in the template code.
     * @param progressListener         Progress listener that receives information about rendered templates.
     * @return Rendered SQL template for error sampling.
     */
    String renderErrorSamplingTemplate(ExecutionContext executionContext,
                                       SensorDefinitionFindResult sensorFindResult,
                                       JinjaTemplateRenderParameters templateRenderParameters,
                                       SensorExecutionProgressListener progressListener);
}
