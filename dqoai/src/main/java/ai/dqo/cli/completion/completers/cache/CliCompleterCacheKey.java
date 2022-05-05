/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.cli.completion.completers.cache;

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
