/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.check.mapping.utils;

import com.dqops.services.check.mapping.basicmodels.CheckListModel;
import com.dqops.services.check.mapping.basicmodels.CheckContainerListModel;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for operations related to {@link CheckContainerListModel}.
 */
public class CheckContainerListModelUtility {
    /**
     * Get all categories to which the checks from the model belong.
     * @param model {@link CheckContainerListModel} from which to extract.
     * @return Distinct list of category names in the checks of the model, sorted ascending.
     */
    public static List<String> getCheckCategoryNames(CheckContainerListModel model) {
        return model.getChecks().stream()
                .map(CheckListModel::getCheckCategory)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
