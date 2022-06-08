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
package ai.dqo.cli.completion.completers;

import ai.dqo.connectors.ProviderType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;

/**
 * Provider type parameter autocompletion source that should be applied on CLI command parameters.
 */
public class ProviderTypeCompleter implements Iterable<String> {
    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<String> iterator() {
        try {
            ArrayList<Object> providers = new ArrayList<>(EnumSet.allOf(ProviderType.class));
            ArrayList<String> providerTypes = new ArrayList<>();
            for(Object provider: providers) {
                providerTypes.add(provider.toString());
            }
            return providerTypes.iterator();
        }
        catch(Exception ex) {
            return Collections.emptyIterator();
        }
    }
}
