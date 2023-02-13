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
package ai.dqo.metadata.search;

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
