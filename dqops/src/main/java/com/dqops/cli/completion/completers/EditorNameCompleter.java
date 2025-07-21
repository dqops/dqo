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

import com.dqops.cli.commands.settings.impl.EditorFinderService;
import com.dqops.cli.commands.settings.impl.EditorFinderServiceImpl;
import com.dqops.cli.commands.settings.impl.EditorInformation;
import com.dqops.cli.completion.completers.cache.CliCompleterCacheKey;
import com.dqops.cli.completion.completers.cache.CliCompletionCache;

import java.util.ArrayList;
import java.util.Iterator;

public class EditorNameCompleter implements Iterable<String> {
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
					EditorFinderService editorFinderService = new EditorFinderServiceImpl();
					ArrayList<EditorInformation> editors = editorFinderService.getAllEditors();
					ArrayList<String> editorList = new ArrayList<>();
					for (EditorInformation editor : editors) {
						editorList.add(editor.name);
					}
					return editorList.iterator();
				});

		return cachedCompletionCandidates;
	}
}
