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
import com.dqops.metadata.definitions.checks.CheckDefinitionList;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextCache;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.services.check.matching.SimilarCheckMatchingService;
import com.dqops.services.check.matching.SimilarChecksContainer;
import com.dqops.utils.StaticBeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.BeanFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Check name completer. Returns a list of all known checks.
 */
@Slf4j
public class CheckNameCompleter implements Iterable<String> {
    private static List<String> builtInCheckNames;

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @NotNull
    @Override
    public Iterator<String> iterator() {
        BeanFactory beanFactory = StaticBeanFactory.getBeanFactory();
        if (builtInCheckNames == null && beanFactory != null) {
            try {
                SimilarCheckMatchingService similarCheckMatchingService = beanFactory.getBean(SimilarCheckMatchingService.class);
                SimilarChecksContainer similarTableChecks = similarCheckMatchingService.findSimilarTableChecks();
                SimilarChecksContainer similarColumnChecks = similarCheckMatchingService.findSimilarColumnChecks();

                builtInCheckNames = new ArrayList<>();
                similarTableChecks.getSimilarCheckGroups()
                        .stream()
                        .flatMap(group -> group.getSimilarChecks().stream())
                        .forEach(similarCheckModel -> builtInCheckNames.add(similarCheckModel.getCheckModel().getCheckName()));

                similarColumnChecks.getSimilarCheckGroups()
                        .stream()
                        .flatMap(group -> group.getSimilarChecks().stream())
                        .forEach(similarCheckModel -> builtInCheckNames.add(similarCheckModel.getCheckModel().getCheckName()));

                if (builtInCheckNames.isEmpty()) {
                    log.warn("No build-in checks found for auto completion");
                }
            }
            catch (Exception ex) {
                log.error("Cannot get the list of built-in checks for command completion, error: " + ex.getMessage(), ex);
                return Collections.emptyIterator();
            }
        }

        Iterator<String> cachedCompletionCandidates = CliCompletionCache.getCachedCompletionCandidates(
                new CliCompleterCacheKey(this.getClass()),
                () -> {
                    try {
                        if (builtInCheckNames == null) {
                            return Collections.emptyIterator();
                        }

                        List<String> allCheckNames = new ArrayList<>(builtInCheckNames);

                        UserHomeContextCache userHomeContextCache = beanFactory.getBean(UserHomeContextCache.class);
                        UserHomeContext userHomeContext = userHomeContextCache.getCachedLocalUserHome();
                        UserHome userHome = userHomeContext.getUserHome();
                        CheckDefinitionList checkDefinitionList = userHome.getChecks();

                        checkDefinitionList.toList()
                                .forEach(checkDefinitionWrapper -> allCheckNames.add(checkDefinitionWrapper.getCheckName()));

                        Collections.sort(allCheckNames);

                        return allCheckNames.iterator();
                    } catch (Exception ex) {
                        log.error("Error while extracting a list of available data quality checks for command completion, error: " + ex.getMessage(), ex);
                        return Collections.emptyIterator();
                    }
                });

        return cachedCompletionCandidates;
    }
}
