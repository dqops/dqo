/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.rest.models.dictionaries;

import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Data dictionary CSV full model used to create and update the dictionary file. Contains the content of the CSV file as a text field.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "DataDictionaryModel", description = "Data dictionary CSV file full model used to create and update the dictionary. Contains the content of the CSV file as a text field.")
public class DataDictionaryModel {
    /**
     * Dictionary name. It is the name of a file in the dictionaries/ folder inside the DQOps user's home folder.
     */
    @JsonPropertyDescription("Dictionary name. It is the name of a file in the dictionaries/ folder inside the DQOps user's home folder.")
    private String dictionaryName;

    /**
     * Credential value as a text.
     */
    @JsonPropertyDescription("Dictionary CSV file content as a single file.")
    private String fileContent;

    public static class SharedCredentialModelSampleFactory implements SampleValueFactory<DataDictionaryModel> {
        @Override
        public DataDictionaryModel createSample() {
            return new DataDictionaryModel() {{
                setDictionaryName(SampleStringsRegistry.getDictionary());
                setFileContent("USD\nEUR\nGBP\nAUD\nCHF\n");
            }};
        }
    }
}
