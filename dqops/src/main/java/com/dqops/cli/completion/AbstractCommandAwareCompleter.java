/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.completion;

import com.dqops.utils.StaticBeanFactory;
import org.jline.reader.ParsedLine;
import org.springframework.beans.factory.BeanFactory;
import picocli.CommandLine;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Base class for command completers that can check the other parameters that were already parsed in the command.
 * @param <T>
 */
public abstract class AbstractCommandAwareCompleter<T> implements Iterable<String> {
    /**
     * The overriding method should create an iterator using the data from the command.
     * @param command User command.
     * @return List of completion candidates.
     */
    public abstract Iterator<String> createIterator(T command);

    /**
     * A convenience method to create an empty completion list.
     * @return
     */
    protected Iterator<String> createEmptyCompletionIterator() {
        return Collections.emptyIterator();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<String> iterator() {
        try {
            ParsedLine currentLine = CurrentCompletionLine.getCurrentLine();
            if (currentLine == null) {
                return createIterator(null);
            }

            BeanFactory beanFactory = StaticBeanFactory.getBeanFactory();
            CommandLine commandLine = beanFactory.getBean(CommandLine.class);
            List<String> commandLineArgsList = currentLine.words();
            String[] args = commandLineArgsList.toArray(new String[commandLineArgsList.size()]);
            CommandLine.ParseResult parseResult = commandLine.parseArgs(args);

            List<CommandLine> commandLines = parseResult.asCommandLineList();
            if (commandLines == null || commandLines.size() == 0) {
                return createEmptyCompletionIterator();
            }

            CommandLine deepestSubCommandLine = commandLines.get(commandLines.size() - 1);
            T commandObject = (T) deepestSubCommandLine.getCommandSpec().userObject();

            return createIterator(commandObject);
        }
        catch (Exception ex) {
            // we cannot perform dynamic completion
            return createEmptyCompletionIterator();
        }
    }
}
