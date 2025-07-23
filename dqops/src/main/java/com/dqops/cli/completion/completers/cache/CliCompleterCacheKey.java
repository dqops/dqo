/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.completion.completers.cache;

import java.util.Arrays;

/**
 * CLI completer cache key, used to identify cached parameters in the completer cache.
 */
public class CliCompleterCacheKey {
    private Class<?> completerClass;
    private String[] parameters;

    /**
     * Creates a new instance of a cli completer cache key.
     * @param completerClass Completer class type.
     * @param parameters Optional completer parameters that were used to filter out the results,
     *                   for example a connection name for a table completer that is executed in the context of a connection name.
     */
    public CliCompleterCacheKey(Class<?> completerClass, String... parameters) {
        this.completerClass = completerClass;
        this.parameters = parameters;
    }

    /**
     * Returns the completer class type.
     * @return Completer type.
     */
    public Class<?> getCompleterClass() {
        return completerClass;
    }

    /**
     * Array of parameters.
     * @return Parameters.
     */
    public String[] getParameters() {
        return parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CliCompleterCacheKey that = (CliCompleterCacheKey) o;

        if (!completerClass.equals(that.completerClass)) return false;
        return Arrays.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        int result = completerClass.hashCode();
        result = 31 * result + Arrays.hashCode(parameters);
        return result;
    }
}
