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
