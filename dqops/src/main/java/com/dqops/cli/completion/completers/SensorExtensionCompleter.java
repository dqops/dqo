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
package com.dqops.cli.completion.completers;

import com.dqops.cli.commands.SensorFileExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;

public class SensorExtensionCompleter implements Iterable<String> {
	/**
	 * Returns an iterator over elements of type {@code T}.
	 *
	 * @return an Iterator.
	 */
	@Override
	public Iterator<String> iterator() {
		try {
			ArrayList<Object> extensions = new ArrayList<>(EnumSet.allOf(SensorFileExtension.class));
			ArrayList<String> fileExtensions = new ArrayList<>();
			for(Object extension: extensions) {
				fileExtensions.add(extension.toString());
			}
			return fileExtensions.iterator();
		}
		catch(Exception ex) {
			return Collections.emptyIterator();
		}
	}
}
