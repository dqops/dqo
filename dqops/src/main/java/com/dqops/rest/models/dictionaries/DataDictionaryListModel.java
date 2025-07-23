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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Data dictionary CSV file list model with the basic information about the dictionary.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "DataDictionaryListModel", description = "Data dictionary CSV file list model with the basic information about the dictionary.")
public class DataDictionaryListModel {
    /**
     * Dictionary name. It is the name of a file in the dictionaries/ folder inside the DQOps user's home folder.
     */
    @JsonPropertyDescription("Dictionary name. It is the name of a file in the dictionaries/ folder inside the DQOps user's home folder.")
    private String dictionaryName;

    /**
     * Boolean flag that decides if the current user can update or delete the dictionary file.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can update or delete the dictionary file.")
    private boolean canEdit;

    /**
     * Boolean flag that decides if the current user can see the dictionary content or download the dictionary file.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user see or download the dictionary file.")
    private boolean canAccessDictionary;
}
