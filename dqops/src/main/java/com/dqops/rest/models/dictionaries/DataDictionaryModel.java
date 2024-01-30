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
