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
package com.dqops.utils.docs.checks;

import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.utils.docs.rules.RuleDocumentationModel;
import com.dqops.utils.docs.sensors.SensorDocumentationModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Documentation model that describes one check.
 */
@Data
public class CheckDocumentationModel {
    /**
     * Check description extracted from the JavaDoc comment for the whole check specification object.
     */
    private String checkSpecClassJavaDoc;

    /**
     * Check help text that is configured on the parent check container.
     */
    private String checkHelp;

    /**
     * Check category.
     */
    private String category;

    /**
     * Check name inside the category.
     */
    private String checkName;

    /**
     * Check type.
     */
    private String checkType;

    /**
     * Check time scale (daily, monthly, or empty).
     */
    private String timeScale;

    /**
     * Sensor documentation model.
     */
    private SensorDocumentationModel sensor;

    /**
     * Rule documentation model.
     */
    private RuleDocumentationModel rule;

    /**
     * Check class - "standard" or "advanced".
     */
    private String checkClass;

    /**
     * Check quality dimension (Availability, Consistency, etc.).
     */
    private String qualityDimension;

    /**
     * Check model with all remaining documentation.
     */
    private CheckModel checkModel;

    /**
     * Data quality check sample (for show structure of check)
     */
    public List<String> checkSample;

    /**
     * Begin marker to highlight data quality check sample.
     */
    public int checkSampleBeginLine;

    /**
     * Begin marker to highlight data quality check sample.
     */
    public int checkSampleEndLine;

    /**
     * Sample yaml.
     */
    private String sampleYaml;

    /**
     * Examples how the SQL for the sensor would be rendered for each template, when the sample yaml is used.
     */
    private List<CheckProviderRenderedSqlDocumentationModel> providerTemplates = new ArrayList<>();

    /**
     * Sample yaml with data streams.
     */
    private String sampleYamlWithDataStreams;

    /**
     * Split sample yaml with data streams.
     */
    private List<String> splitSampleYamlWithDataStreams;

    /**
     * Begin marker to highlight start first section data stream sample.
     */
    private int firstSectionBeginMarker;

    /**
     * End marker to highlight end first section data stream sample.
     */
    private int firstSectionEndMarker;

    /**
     * Begin marker to highlight start second section data stream sample.
     */
    private int secondSectionBeginMarker;

    /**
     * End marker to highlight end second section data stream sample.
     */
    private int secondSectionEndMarker;

    /**
     * Examples how the SQL for the sensor would be rendered for each template, when the sample yaml is used and data streams are enabled.
     */
    private List<CheckProviderRenderedSqlDocumentationModel> providerTemplatesDataStreams = new ArrayList<>();

    /**
     * CLI line with precise parameters to run this check on all tables within a connection.
     */
    private String runOnConnectionCliCommand;

    /**
     * CLI line with precise parameters to run this check on a selected table.
     */
    private String runOnTableCliCommand;

    /**
     * CLI line with precise parameters to run this check on a selected column.
     */
    private String runOnColumnCliCommand;
}
