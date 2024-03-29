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
package com.dqops.utils.docs.parquetfiles;

import lombok.Data;

/**
 * Parquet columns details model. Contains details about each column occurring on the superior class.
 */
@Data
public class ParquetColumnDetailsDocumentationModel {
    /**
     * Column name.
     */
    private String columnName;
    /**
     * Column type.
     */
    private String columnType;
    /**
     * Column description.
     */
    private String columnDescription;
}
