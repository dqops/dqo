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

import com.dqops.cli.completion.completers.cache.CliCompleterCacheKey;
import com.dqops.cli.completion.completers.cache.CliCompletionCache;
import com.dqops.connectors.bigquery.BigQueryConnectionProvider;
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
