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
import ai.dqo.metadata.definitions.checks.CheckDefinitionList;
import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextCache;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.services.check.matching.SimilarCheckMatchingService;
import ai.dqo.services.check.matching.SimilarChecksContainer;
import ai.dqo.utils.StaticBeanFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.BeanFactory;

import java.util.*;

/**
 * Check name completer. Returns a list of all known checks.
 */
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
        if (builtInCheckNames == null) {
            SimilarCheckMatchingService similarCheckMatchingService = beanFactory.getBean(SimilarCheckMatchingService.class);
            SimilarChecksContainer similarTableChecks = similarCheckMatchingService.findSimilarTableChecks(new TableSpec());
            SimilarChecksContainer similarColumnChecks = similarCheckMatchingService.findSimilarColumnChecks(new TableSpec(), new ColumnSpec());

            builtInCheckNames = new ArrayList<>();
            similarTableChecks.getSimilarCheckGroups()
                    .stream()
                    .flatMap(group -> group.getSimilarChecks().stream())
                    .forEach(similarCheckModel -> builtInCheckNames.add(similarCheckModel.getCheckModel().getCheckName()));

            similarColumnChecks.getSimilarCheckGroups()
                    .stream()
                    .flatMap(group -> group.getSimilarChecks().stream())
                    .forEach(similarCheckModel -> builtInCheckNames.add(similarCheckModel.getCheckModel().getCheckName()));
        }

        Iterator<String> cachedCompletionCandidates = CliCompletionCache.getCachedCompletionCandidates(
                new CliCompleterCacheKey(this.getClass()),
                () -> {
                    try {
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
                        return Collections.emptyIterator();
                    }
                });

        return cachedCompletionCandidates;
    }
}
