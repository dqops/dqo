/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.data.errorsamples.services;

import com.dqops.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

public class ErrorSamplesFileNameCreatorObjectMother {

    /**
     * Returns the default ErrorSamplesFileNameCreator that creates a file name.
     * @return ErrorSamplesFileNameCreator.
     */
    public static ErrorSamplesFileNameCreatorImpl getDefault() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return (ErrorSamplesFileNameCreatorImpl) beanFactory.getBean(ErrorSamplesFileNameCreator.class);
    }
}
