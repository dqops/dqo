/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.search;

import java.util.ArrayList;
import java.util.List;

/**
 * Parameter object for search visitors. Used to collect custom result objects.
 * @param <R> Collected object type.
 */
public class FoundResultsCollector<R> {
    private List<R> results = new ArrayList<>();

    /**
     * Adds a result to the collector.
     * @param result Result object.
     */
    public void add(R result) {
        assert result != null;
        this.results.add(result);
    }

    /**
     * Returns the list of results that were collected.
     * @return Collected results.
     */
    public List<R> getResults() {
        return results;
    }
}
