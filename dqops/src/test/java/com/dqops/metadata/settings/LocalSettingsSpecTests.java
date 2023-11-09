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
package com.dqops.metadata.settings;

import com.dqops.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocalSettingsSpecTests extends BaseTest {
	private LocalSettingsSpec sut;

	@BeforeEach
	void setUp() {
		this.sut = new LocalSettingsSpec();
	}

	@Test
	void isDirty_whenEditorNameSet_thenIsDirtyIsTrue() {
		this.sut.setEditorName("vsc");
		Assertions.assertEquals("vsc", this.sut.getEditorName());
		Assertions.assertTrue(this.sut.isDirty());
	}

	@Test
	void isDirty_whenSameEditorNameAsCurrentSet_thenIsDirtyIsFalse() {
		this.sut.setEditorName("vsc");
		Assertions.assertTrue(this.sut.isDirty());
		this.sut.clearDirty(true);
		Assertions.assertFalse(this.sut.isDirty());
		this.sut.setEditorName("vsc");
		Assertions.assertFalse(this.sut.isDirty());
	}

	@Test
	void isDirty_whenApiKeySet_thenIsDirtyIsTrue() {
		this.sut.setApiKey("test");
		Assertions.assertEquals("test", this.sut.getApiKey());
		Assertions.assertTrue(this.sut.isDirty());
	}

	@Test
	void isDirty_whenSameApiKeyAsCurrentSet_thenIsDirtyIsFalse() {
		this.sut.setApiKey("test");
		Assertions.assertTrue(this.sut.isDirty());
		this.sut.clearDirty(true);
		Assertions.assertFalse(this.sut.isDirty());
		this.sut.setApiKey("test");
		Assertions.assertFalse(this.sut.isDirty());
	}
}
