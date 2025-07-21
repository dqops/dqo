/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.checks;

public class CheckNameUtility {
    public static String getUnifiedCheckName(String checkName) {
        String unifiedCheckName = checkName;
        if (checkName.startsWith("daily_partition_")) {
            unifiedCheckName = checkName.substring("daily_partition_".length());
        } else if (checkName.startsWith("monthly_partition_")) {
            unifiedCheckName = checkName.substring("monthly_partition_".length());
        } else if (checkName.startsWith("daily_")) {
            unifiedCheckName = checkName.substring("daily_".length());
        } else if (checkName.startsWith("monthly_")) {
            unifiedCheckName = checkName.substring("monthly_".length());
        }
        return unifiedCheckName;
    }
}
