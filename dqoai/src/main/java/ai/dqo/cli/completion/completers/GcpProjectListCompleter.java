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

import ai.dqo.cli.completion.completers.cache.CliCompleterCacheKey;
import ai.dqo.cli.completion.completers.cache.CliCompletionCache;
import ai.dqo.connectors.bigquery.BigQueryConnectionProvider;
import org.apache.parquet.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * CLI parameter completer that retrieves a list of GCP projects accessible by the current user with the default application credentials.
 */
public class GcpProjectListCompleter implements Iterable<String> {
    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<String> iterator() {
        Iterator<String> cachedCompletionCandidates = CliCompletionCache.getCachedCompletionCandidates(
                new CliCompleterCacheKey(this.getClass()),
                () -> {
                    try {
                        String billingProject = BigQueryConnectionProvider.tryGetCurrentGcpProject();
                        if (Strings.isNullOrEmpty(billingProject)) {
                            return Collections.emptyIterator();
                        }

                        return new ArrayList<String>() {{
                            add(billingProject);
                        }}.iterator();
                    } catch (Exception ex) {
                        return Collections.emptyIterator();
                    }
                });

        return cachedCompletionCandidates;
    }
}
