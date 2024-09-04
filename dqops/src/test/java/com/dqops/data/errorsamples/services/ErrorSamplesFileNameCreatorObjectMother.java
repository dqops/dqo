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
