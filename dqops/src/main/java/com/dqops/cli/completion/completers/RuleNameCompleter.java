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
import com.dqops.metadata.definitions.rules.RuleDefinitionList;
import com.dqops.metadata.definitions.rules.RuleDefinitionWrapper;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextCache;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.utils.StaticBeanFactory;
import org.springframework.beans.factory.BeanFactory;

import java.util.*;

/**
 * Rule list CLI parameter autocompletion source that should be applied on CLI command parameters
 * that accept a list of rules.
 */
public class RuleNameCompleter implements Iterable<String> {
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

						DqoHomeContextFactory dqoHomeContextCache = beanFactory.getBean(DqoHomeContextFactory.class);
						DqoHomeContext dqoHomeContext = dqoHomeContextCache.openLocalDqoHome();
						DqoHome dqoHome = dqoHomeContext.getDqoHome();

						RuleDefinitionList userHomeRules = userHome.getRules();
						RuleDefinitionList localDqoRules = dqoHome.getRules();
						Set<String> ruleNames = new LinkedHashSet<>();

						for (RuleDefinitionWrapper rule : userHomeRules) {
							ruleNames.add(rule.getRuleName());
						}
						for (RuleDefinitionWrapper rule : localDqoRules) {
							ruleNames.add(rule.getRuleName());
						}

						ArrayList<String> rules = new ArrayList<>();
						for (String ruleName : ruleNames) {
							rules.add(ruleName);
						}
						Collections.sort(rules);
						return rules.iterator();
					} catch (Exception ex) {
						return Collections.emptyIterator();
					}
				});

		return cachedCompletionCandidates;
	}
}
