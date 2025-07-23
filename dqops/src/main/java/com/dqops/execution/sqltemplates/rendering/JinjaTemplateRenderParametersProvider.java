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
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.finder.SensorDefinitionFindResult;

public interface JinjaTemplateRenderParametersProvider {

    /**
     * Creates a template render parameters using trimmed spec objects copied from the parameters.
     * Specification object trimming means that we make a clone of a table specification, but we are removing configuration of other checks to make the result json smaller.
     * @param sensorRunParameters Sensor run parameters with connection, table, column, sensor parameters to copy (with trimming).
     * @param sensorDefinitions Sensor definition specifications for the sensor itself and its provider sensor definition.
     * @return Jinja template render parameters that will be forwarded to Jinja2.
     */
    JinjaTemplateRenderParameters createFromTrimmedObjects(ExecutionContext executionContext,
                                                           SensorExecutionRunParameters sensorRunParameters,
                                                           SensorDefinitionFindResult sensorDefinitions);

}
