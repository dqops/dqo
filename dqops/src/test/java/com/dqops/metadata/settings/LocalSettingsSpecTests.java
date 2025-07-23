/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
