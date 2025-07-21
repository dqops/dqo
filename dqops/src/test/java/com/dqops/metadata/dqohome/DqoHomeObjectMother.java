/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.dqohome;

import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;

/**
 * Dqo home object mother. Returns a shared dqo home instance.
 */
public class DqoHomeObjectMother {
    /**
     * Returns a real dqo home.
     * @return Dqo home.
     */
    public static DqoHome getDqoHome() {
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();
        return dqoHomeContext.getDqoHome();
    }
}
