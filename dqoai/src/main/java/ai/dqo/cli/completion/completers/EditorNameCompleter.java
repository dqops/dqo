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

import ai.dqo.cli.commands.settings.impl.EditorFinderService;
import ai.dqo.cli.commands.settings.impl.EditorFinderServiceImpl;
import ai.dqo.cli.commands.settings.impl.EditorInformation;
import ai.dqo.cli.completion.completers.cache.CliCompleterCacheKey;
import ai.dqo.cli.completion.completers.cache.CliCompletionCache;

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
