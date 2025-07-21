/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.python;

import com.dqops.core.configuration.DqoConfigurationProperties;
import com.dqops.core.configuration.DqoConfigurationPropertiesObjectMother;
import com.dqops.core.configuration.DqoPythonConfigurationProperties;
import com.dqops.core.configuration.DqoPythonConfigurationPropertiesObjectMother;
import com.dqops.core.jobqueue.concurrency.ParallelJobLimitProviderStub;
import com.dqops.utils.BeanFactoryObjectMother;
import com.dqops.utils.serialization.JsonSerializerImpl;
import org.springframework.beans.factory.BeanFactory;

/**
 * Object mother for PythonCallService.
 */
public class PythonCallServiceObjectMother {
    /**
     * Creates a new instance of a python call service.
     * @return Python call service.
     */
    public static PythonCallerServiceImpl createNewDefault() {
        DqoConfigurationProperties configurationProperties = DqoConfigurationPropertiesObjectMother.getDefaultCloned();
        PythonVirtualEnvService pythonVirtualEnvService = PythonVirtualEnvServiceObjectMother.getDefault();
        DqoPythonConfigurationProperties pythonConfigurationProperties = DqoPythonConfigurationPropertiesObjectMother.getDefaultCloned();
        return new PythonCallerServiceImpl(configurationProperties, pythonConfigurationProperties, new JsonSerializerImpl(), pythonVirtualEnvService,
                new ParallelJobLimitProviderStub(8));
    }

    /**
     * Retrieves the default python call service.
     * @return Default python call service.
     */
    public static PythonCallerService getDefault() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return beanFactory.getBean(PythonCallerService.class);
    }
}
