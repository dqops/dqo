/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.id;

import java.util.function.Function;

/**
 * Lambda expression signature for a property value extraction expression.
 */
public interface GetHierarchyChildNodeFunc<T extends HierarchyNode> extends Function<T, HierarchyNode> {
}
