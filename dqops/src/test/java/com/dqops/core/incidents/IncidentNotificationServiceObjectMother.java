/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.incidents;

import com.dqops.utils.BeanFactoryObjectMother;

/**
 * Object mother for {@link IncidentNotificationService}
 */
public class IncidentNotificationServiceObjectMother {

    /**
     * Returns an instance of the IncidentNotificationService object.
     * @return Instance of the IncidentNotificationService object.
     */
    public static IncidentNotificationService getInstance() {
        IncidentNotificationService instance = BeanFactoryObjectMother.getBeanFactory().getBean(IncidentNotificationService.class);
        return instance;
    }

}
