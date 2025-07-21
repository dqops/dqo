/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.completion.completers;

import com.dqops.connectors.ProviderType;

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
