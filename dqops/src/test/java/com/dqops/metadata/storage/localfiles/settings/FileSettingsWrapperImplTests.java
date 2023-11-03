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
package com.dqops.metadata.storage.localfiles.settings;

import com.dqops.BaseTest;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.settings.LocalSettingsSpec;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.utils.serialization.YamlSerializer;
import com.dqops.utils.serialization.YamlSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileSettingsWrapperImplTests extends BaseTest {
	private FileSettingsWrapperImpl sut;
	private UserHomeContext userHomeContext;
	private FolderTreeNode settingsFolder;
	private YamlSerializer yamlSerializer;

	@BeforeEach
	void setUp() {
		this.userHomeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
		this.sut = (FileSettingsWrapperImpl) userHomeContext.getUserHome().getSettings();
		this.settingsFolder = userHomeContext.getHomeRoot();
		this.yamlSerializer = YamlSerializerObjectMother.createNew();
	}

	@Test
	void flush_whenNew_thenSavesSpec() {
		LocalSettingsSpec spec = new LocalSettingsSpec();
		spec.setEditorName("vsc");
		this.sut.setSpec(spec);
		this.sut.setStatus(InstanceStatus.ADDED);
		this.sut.flush();

		userHomeContext.flush();

		Assertions.assertFalse(this.sut.getSpec().isDirty());
		Assertions.assertEquals(InstanceStatus.UNCHANGED, this.sut.getStatus());
		FileSettingsWrapperImpl sut2 = new FileSettingsWrapperImpl(this.settingsFolder, this.yamlSerializer);
		LocalSettingsSpec spec2 = sut2.getSpec();
		Assertions.assertEquals("vsc", spec2.getEditorName());
	}

	@Test
	void flush_whenModified_thenSavesSpec() {
		LocalSettingsSpec spec = new LocalSettingsSpec();
		spec.setEditorName("vsc");
		this.sut.setSpec(spec);
		this.sut.setStatus(InstanceStatus.ADDED);
		this.sut.flush();
		userHomeContext.flush();

		this.sut.getSpec().setEditorName("intellj");
		this.sut.flush();
		userHomeContext.flush();

		Assertions.assertEquals(InstanceStatus.UNCHANGED, this.sut.getStatus());
		FileSettingsWrapperImpl sut2 = new FileSettingsWrapperImpl(settingsFolder, this.yamlSerializer);
		LocalSettingsSpec spec2 = sut2.getSpec();
		Assertions.assertEquals("intellj", spec2.getEditorName());
	}


	@Test
	void getSpec_whenSpecFilePresentInFolder_thenReturnsSpec() {
		LocalSettingsSpec spec = new LocalSettingsSpec();
		spec.setEditorName("vsc");
		this.sut.setSpec(spec);
		this.sut.setStatus(InstanceStatus.ADDED);
		this.sut.flush();
		userHomeContext.flush();

		FileSettingsWrapperImpl sut2 = new FileSettingsWrapperImpl(settingsFolder, this.yamlSerializer);
		LocalSettingsSpec spec2 = sut2.getSpec();
		Assertions.assertNotNull(spec2);
	}

	@Test
	void getSpec_whenCalledTwice_thenReturnsTheSameInstance() {
		LocalSettingsSpec spec = new LocalSettingsSpec();
		spec.setEditorName("vsc");
		this.sut.setSpec(spec);
		this.sut.setStatus(InstanceStatus.ADDED);
		this.sut.flush();
		userHomeContext.flush();

		FileSettingsWrapperImpl sut2 = new FileSettingsWrapperImpl(settingsFolder, this.yamlSerializer);
		LocalSettingsSpec spec2 = sut2.getSpec();
		Assertions.assertNotNull(spec2);
		Assertions.assertSame(spec2, sut2.getSpec());
	}
}
