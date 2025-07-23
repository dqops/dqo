/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rest.models.metadata;

import com.dqops.metadata.labels.labelcontainers.LabelCounter;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;


/**
 * Label model that is returned by the REST API. A label is a tag that was assigned to a data source, table, column or a single check.
 * Labels play the role of a business glossary.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "LabelModel", description = "Label model that is returned by the REST API. A label is a tag that was assigned to a data source, table, column or a single check. Labels play the role of a business glossary.")
public class LabelModel {
    /**
     * Label text.
     */
    @JsonPropertyDescription("Label text.")
    private String label;

    /**
     * The number of data assets tagged with this label.
     */
    @JsonPropertyDescription("The number of data assets tagged with this label.")
    private int labelsCount;

    /**
     * The number of data assets tagged with nested labels below this prefix node. For example, if the current label is "address", and there are nested labels "address/city" and "address/zipcode",
     * this value returns the count of data assets tagged with these nested tags.
     */
    @JsonPropertyDescription("The number of data assets tagged with nested labels below this prefix node. For example, if the current label is \"address\", " +
            "and there are nested labels \"address/city\" and \"address/zipcode\", this value returns the count of data assets tagged with these nested tags.")
    private int nestedLabelsCount;


    public LabelModel() {
    }

    public LabelModel(String label, int labelsCount, int nestedLabelsCount) {
        this.label = label;
        this.labelsCount = labelsCount;
        this.nestedLabelsCount = nestedLabelsCount;
    }

    /**
     * Creates a label model from a label counter.
     * @param labelCounter Label counter.
     * @return Label model.
     */
    public static LabelModel fromLabelCounter(LabelCounter labelCounter) {
        return new LabelModel(labelCounter.getLabel(), labelCounter.getLabelsCount(), labelCounter.getNestedLabelsCount());
    }

    public static class LabelModelSampleFactory implements SampleValueFactory<LabelModel> {
        @Override
        public LabelModel createSample() {
            return new LabelModel() {{
                setLabel(SampleStringsRegistry.getLabelSample());
                setLabelsCount(10);
                setNestedLabelsCount(2);
            }};
        }
    }
}
