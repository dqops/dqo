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
