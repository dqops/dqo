/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
