/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
     * The check target: table or column.
     */
    private String target;

    /**
     * Check category.
     */
    private String category;

    /**
     * The name of the markdown file with the documentation of the category.
     */
    private String categoryPageName;

    /**
     * Check name inside the category.
     */
    private String checkName;

    /**
     * Check friendly name.
     */
    private String friendlyName;

    /**
     * Check type.
     */
    private String checkType;

    /**
     * The name of the page with the check concept.
     */
    private String checkTypeConceptPage;

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
     * The check is standard or advanced.
     */
    private boolean standard;

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
}
