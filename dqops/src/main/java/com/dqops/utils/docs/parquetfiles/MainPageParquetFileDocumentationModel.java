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

import java.util.ArrayList;
import java.util.List;

/**
 * Container object with a list of all parquet file types.
 */
@Data
public class MainPageParquetFileDocumentationModel {
    /**
     * List of all parquet file types.
     */
    private List<ParquetFileDocumentationModel> parquetFiles = new ArrayList<>();
}
