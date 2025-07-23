/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.sensors;

import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapper;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.utils.docs.ProviderTypeModel;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Sensor description model. Describes the sensor and contains all information about the sensor that could be gathered
 * from the sensor parameters, definition, etc.
 */
@Data
public class SensorDocumentationModel {
    private Class<? extends AbstractSensorParametersSpec> sensorParametersClazz;
    /**
     * Sensor description extracted from the JavaDoc comment for the whole sensor definition parameter.
     */
    private String sensorParametersJavaDoc;

    /**
     * Sensor name.
     */
    private String fullSensorName;

    /**
     * Sensor target ('table' or 'column')
     */
    private String target;

    /**
     * Sensor category.
     */
    private String category;

    /**
     * Sensor name inside the category.
     */
    private String sensorName;

    /**
     * Sensor SQL Templates for each provider.
     * Templates are split by end of line and load as list because of mkdocs rendering require add extra spaces
     * before each line.
     */
    private Map<ProviderTypeModel, List<String>> sqlTemplates;

    /**
     * Sensor definition wrapper.
     */
    private SensorDefinitionWrapper definition;
}
