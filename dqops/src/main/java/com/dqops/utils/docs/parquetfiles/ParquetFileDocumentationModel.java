/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.parquetfiles;

import lombok.Data;

import java.util.List;

/**
 * Parquet file class description model. Contains info about class and all the columns occurring on them.
 */
@Data
public class ParquetFileDocumentationModel {
    /**
     * Parquet file class name.
     */
    private String parquetFileFullName;
    /**
     * Parquet file class description.
     */
    private String parquetFileDescription;
    /**
     * Parquet file class description.
     */
    private String parquetFileShortDescription;
    /**
     * Details about each column occurring on this class.
     */
    private List<ParquetColumnDetailsDocumentationModel> parquetColumnDetailsDocumentationModels;
}
