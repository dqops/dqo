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
package com.dqops.cli.completion.completers.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * CLI completion cache.
 */
public class CliCompletionCache {
    private final static Cache<CliCompleterCacheKey, List<String>> completionCache =
            CacheBuilder.newBuilder()
                    .maximumSize(5000)
                    .expireAfterAccess(30, TimeUnit.SECONDS)
                    .build();

    /**
     * Invalidates the cache.
     */
    public static void invalidateCache() {
        completionCache.invalidateAll();
    }

    /**
     * Returns or populates a cached response for the given completion line.
     * @param cacheKey Cache key that identifies the completer by type and all arguments that affected the completion.
     * @param completionCandidatesFactory Lambda that will create the iterator of completion candidates.
     * @return Iterator of values.
     */
    public static Iterator<String> getCachedCompletionCandidates(CliCompleterCacheKey cacheKey,
                                                           final Callable<Iterator<String>> completionCandidatesFactory) {
        try {
            List<String> completionCandidates = completionCache.get(cacheKey, () -> {
                Iterator<String> iterator = completionCandidatesFactory.call();
                ImmutableList<String> completionCandidatesList = ImmutableList.copyOf(iterator);
                return completionCandidatesList;
            });

            return completionCandidates.iterator();
        }
        catch (Exception ex) {
            throw new CliCompleterCacheException(ex.getMessage(), ex);
        }
    }
}
