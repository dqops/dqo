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
package ai.dqo.core.filesystem.filesystemservice;

import java.util.Map;

public class InvalidYamlCollector {
	private Map<String, Exception> invalidYamls;
	private boolean areInvalidFiles = false;

	public Map<String, Exception> getInvalidYamls() {
		return this.invalidYamls;
	}

	public void addInvalidYaml(String path, Exception e) {
		this.invalidYamls.put(path, e);
	}

	public boolean areInvalidFiles() {
		return this.areInvalidFiles;
	}

	public void setAreInvalidFiles(boolean areInvalidFiles) {
		this.areInvalidFiles = areInvalidFiles;
	}
}
