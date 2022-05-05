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
package ai.dqo.core.configuration;

import ai.dqo.BaseTest;
import ai.dqo.core.filesystem.localfiles.LocalFolderTreeNodeObjectMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DqoCoreConfigurationTests extends BaseTest {
	@Autowired
	private DqoCoreConfiguration sut;

	@Test
	void contextLoads_whenCalled_thenDoesNotThrowSpringExceptions() {
	}

	@Test
	void dqo_whenRetrieved_returnsConfigurationForHomeFolder() {
		assertNotNull(this.sut.getDqo());
		String home = this.sut.getDqo().getHome();
		String dqo_home = System.getenv("DQO_HOME");
		assertNotNull(dqo_home);
		assertNotNull(home);
		assertTrue(Files.isDirectory(Path.of(home)));
	}

	@Test
	void dqo_whenRetrieved_returnsConfigurationForUserHomeFolder() {
		LocalFolderTreeNodeObjectMother.createDefaultUserHome(true);

		assertNotNull(this.sut.getDqo());
		assertNotNull(this.sut.getDqo().getUser());
		String home = this.sut.getDqo().getUser().getHome();
		assertNotNull(home);
		assertTrue(Files.isDirectory(Path.of(home)), "Missing home folder: " + home);
	}
}
