/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.docs.client.apimodel;

import io.swagger.v3.oas.models.PathItem.HttpMethod;
import io.swagger.v3.oas.models.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationModel {
    private final Operation operation;
    private final HttpMethod httpMethod;
    private final String path;
}
