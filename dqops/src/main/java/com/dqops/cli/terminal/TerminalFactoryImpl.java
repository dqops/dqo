/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.terminal;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Provides instances of the {@link TerminalReader} and {@link TerminalWriter} that are created when needed.
 */
@Component
public class TerminalFactoryImpl implements TerminalFactory {
    private BeanFactory beanFactory;

    /**
     * Creates an instance and stores the reference to the IoC bean factory.
     * @param beanFactory Bean factory used to create the terminal reader and writer.
     */
    @Autowired
    public TerminalFactoryImpl(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Returns the instance of the terminal reader.
     * @return Terminal reader.
     */
    @Override
    public TerminalReader getReader() {
        return this.beanFactory.getBean(TerminalReader.class);
    }

    /**
     * Returns the instance of the terminal writer.
     * @return Terminal writer.
     */
    @Override
    public TerminalWriter getWriter() {
        return this.beanFactory.getBean(TerminalWriter.class);
    }
}
