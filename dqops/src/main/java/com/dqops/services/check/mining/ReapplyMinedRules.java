/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.check.mining;

/**
 * Marker interface that identifies {@link com.dqops.checks.AbstractCheckSpec} classes for checks that must go
 * through rule mining again, even if they already have rule thresholds configured, because the rule miner will apply better rules.
 * For example, the {@link com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec} will be recalculated.
 */
public interface ReapplyMinedRules {
}
