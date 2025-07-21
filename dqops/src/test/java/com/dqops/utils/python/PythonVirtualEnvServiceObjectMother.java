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

import com.dqops.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

/**
 * Object mother for PythonVirtualEnvService.
 */
public class PythonVirtualEnvServiceObjectMother {
    /**
     * Returns the default python environment service.
     * @return Python environment service.
     */
    public static PythonVirtualEnvService getDefault() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        PythonVirtualEnvService pythonVirtualEnvService = beanFactory.getBean(PythonVirtualEnvService.class);
        return pythonVirtualEnvService;
    }

    /**
     * Ensures that the python virtual environment exist.
     */
    public static void ensurePythonVirtualEnvironmentExist() {
        PythonVirtualEnvService pythonVirtualEnvService = getDefault();
        pythonVirtualEnvService.getVirtualEnv();
    }
}
