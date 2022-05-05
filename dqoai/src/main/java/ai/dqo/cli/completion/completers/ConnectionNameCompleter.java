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
import ai.dqo.metadata.sources.ConnectionList;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextCache;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.utils.StaticBeanFactory;
import org.springframework.beans.factory.BeanFactory;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Connection list CLI parameter autocompletion source that should be applied on CLI command parameters
 * that accept a list of connections.
 */
public class ConnectionNameCompleter implements Iterable<String> {
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
                        BeanFactory beanFactory = StaticBeanFactory.getBeanFactory();
                        UserHomeContextCache userHomeContextCache = beanFactory.getBean(UserHomeContextCache.class);
                        UserHomeContext userHomeContext = userHomeContextCache.getCachedLocalUserHome();
                        UserHome userHome = userHomeContext.getUserHome();
                        ConnectionList connections = userHome.getConnections();
                        List<String> connectionNames = connections.toList()
                                .stream().map(cw -> cw.getName())
                                .collect(Collectors.toList());
                        return connectionNames.iterator();
                    } catch (Exception ex) {
                        return Collections.emptyIterator();
                    }
                });

        return cachedCompletionCandidates;
    }
}
