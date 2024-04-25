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
package com.dqops.metadata.storage.localfiles.ruledefinitions;

import com.dqops.BaseTest;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.FolderTreeNode;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.definitions.rules.RuleDefinitionSpec;
import com.dqops.metadata.definitions.rules.RuleDefinitionWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.utils.serialization.YamlSerializer;
import com.dqops.utils.serialization.YamlSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
public class FileRuleDefinitionWrapperImplTests extends BaseTest {
    private FileRuleDefinitionWrapperImpl sut;
    private UserHomeContext userHomeContext;
    private FileRuleDefinitionListImpl fileRuleDefinitionList;
    private FolderTreeNode ruleFolder;
    private YamlSerializer yamlSerializer;

    @BeforeEach
    void setUp() {
		this.userHomeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
		this.fileRuleDefinitionList = (FileRuleDefinitionListImpl) userHomeContext.getUserHome().getRules();
		this.ruleFolder = this.fileRuleDefinitionList.getRulesFolder().getOrAddDirectFolder("conn");
		this.yamlSerializer = YamlSerializerObjectMother.createNew();
		this.sut = new FileRuleDefinitionWrapperImpl(ruleFolder, "rule", "rule", yamlSerializer, false);
    }

    @Test
    void setModified_whenSet_returnModified() {
		this.sut.setModifiedIf(true);
        Assertions.assertEquals(InstanceStatus.MODIFIED, this.sut.getStatus());
    }

    @Test
    void setStatus_whenSet_returnStatus() {
		this.sut.setStatus(InstanceStatus.ADDED);
        Assertions.assertEquals(InstanceStatus.ADDED, this.sut.getStatus());
		this.sut.setStatus(InstanceStatus.UNCHANGED);
        Assertions.assertEquals(InstanceStatus.UNCHANGED, this.sut.getStatus());
		this.sut.setStatus(InstanceStatus.MODIFIED);
        Assertions.assertEquals(InstanceStatus.MODIFIED, this.sut.getStatus());
		this.sut.setStatus(InstanceStatus.DELETED);
        Assertions.assertEquals(InstanceStatus.DELETED, this.sut.getStatus());
		this.sut.setStatus(InstanceStatus.TO_BE_DELETED);
        Assertions.assertEquals(InstanceStatus.TO_BE_DELETED, this.sut.getStatus());
    }

    @Test
    void setSpec_whenSet_returnRuleDefinitionSpec() {
        RuleDefinitionSpec spec = new RuleDefinitionSpec();
		this.sut.setSpec(spec);
        Assertions.assertEquals(spec, this.sut.getSpec());
    }

    @Test
    void flush_whenNewWithSpec_thenSavesSpec() {
        RuleDefinitionSpec spec = new RuleDefinitionSpec();
        spec.setDirty();
		this.sut.setSpec(spec);
		this.sut.setStatus(InstanceStatus.ADDED);
		this.sut.flush();

		userHomeContext.flush();

        Assertions.assertFalse(this.sut.getSpec().isDirty());
        Assertions.assertEquals(InstanceStatus.UNCHANGED, this.sut.getStatus());
        FileRuleDefinitionWrapperImpl sut2 = new FileRuleDefinitionWrapperImpl(ruleFolder, "rule", "rule", this.yamlSerializer, false);
        RuleDefinitionSpec spec2 = sut2.getSpec();
        Assertions.assertEquals(false, spec2.isDirty());
    }

    @Test
    void flush_whenNewWithSpecAndPythonFile_thenSavesSpec() {
        RuleDefinitionSpec spec = new RuleDefinitionSpec();
        spec.setDirty();
		this.sut.setSpec(spec);
		this.sut.setRulePythonModuleContent(new FileContent("def fun()"));
		this.sut.setStatus(InstanceStatus.ADDED);
		this.sut.flush();

		userHomeContext.flush();

        Assertions.assertFalse(this.sut.getSpec().isDirty());
        Assertions.assertEquals(InstanceStatus.UNCHANGED, this.sut.getStatus());
        FileRuleDefinitionWrapperImpl sut2 = new FileRuleDefinitionWrapperImpl(ruleFolder, "rule", "rule", this.yamlSerializer, false);
        RuleDefinitionSpec spec2 = sut2.getSpec();
        Assertions.assertEquals(false, spec2.isDirty());
        Assertions.assertEquals("def fun()", sut2.getRulePythonModuleContent().getTextContent());
    }

    @Test
    void flush_whenSpecModified_thenSavesSpec() {
        RuleDefinitionSpec spec = new RuleDefinitionSpec();
        spec.setDirty();
		this.sut.setSpec(spec);
		this.sut.setStatus(InstanceStatus.ADDED);
		this.sut.flush();
		userHomeContext.flush();

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("p1", "val");
        this.sut.getSpec().setParameters(parameters);
		this.sut.flush();
		userHomeContext.flush();

        Assertions.assertEquals(InstanceStatus.UNCHANGED, this.sut.getStatus());
        FileRuleDefinitionWrapperImpl sut2 = new FileRuleDefinitionWrapperImpl(ruleFolder,"rule", "rule", this.yamlSerializer, false);
        RuleDefinitionSpec spec2 = sut2.getSpec();
        Assertions.assertEquals("val", spec2.getParameters().get("p1"));
    }

    @Test
    void flush_whenPythonModuleModified_thenSavesSpec() {
        RuleDefinitionSpec spec = new RuleDefinitionSpec();
        spec.setDirty();
		this.sut.setSpec(spec);
		this.sut.setStatus(InstanceStatus.ADDED);
		this.sut.setRulePythonModuleContent(new FileContent("def fun()"));
		this.sut.flush();
		userHomeContext.flush();
        Assertions.assertFalse(this.sut.isDirty());

		this.sut.setRulePythonModuleContent(new FileContent("def fun2()"));
        Assertions.assertTrue(this.sut.isDirty());
        Assertions.assertEquals(InstanceStatus.MODIFIED, this.sut.getStatus());
		this.sut.flush();
		userHomeContext.flush();
        Assertions.assertFalse(this.sut.isDirty());

        Assertions.assertEquals(InstanceStatus.UNCHANGED, this.sut.getStatus());
        FileRuleDefinitionWrapperImpl sut2 = new FileRuleDefinitionWrapperImpl(ruleFolder,"rule", "rule", this.yamlSerializer, false);
        RuleDefinitionSpec spec2 = sut2.getSpec();
        Assertions.assertEquals("def fun2()", sut2.getRulePythonModuleContent().getTextContent());
    }

    @Test
    void flush_whenPythonModuleNulled_thenRemovesModule() {
        RuleDefinitionSpec spec = new RuleDefinitionSpec();
        spec.setDirty();
		this.sut.setSpec(spec);
		this.sut.setStatus(InstanceStatus.ADDED);
		this.sut.setRulePythonModuleContent(new FileContent("def fun()"));
		this.sut.flush();
		userHomeContext.flush();
        Assertions.assertFalse(this.sut.isDirty());

		this.sut.setRulePythonModuleContent(null);
        Assertions.assertTrue(this.sut.isDirty());
        Assertions.assertEquals(InstanceStatus.MODIFIED, this.sut.getStatus());
		this.sut.flush();
		userHomeContext.flush();
        Assertions.assertFalse(this.sut.isDirty());

        Assertions.assertEquals(InstanceStatus.UNCHANGED, this.sut.getStatus());
        FileRuleDefinitionWrapperImpl sut2 = new FileRuleDefinitionWrapperImpl(ruleFolder,"rule", "rule", this.yamlSerializer, false);
        RuleDefinitionSpec spec2 = sut2.getSpec();
        Assertions.assertNull(sut2.getRulePythonModuleContent().getTextContent());
    }

    @Test
    void flush_whenExistingWasMarkedForDeletion_thenDeletesRuleFromDisk() {
        RuleDefinitionSpec spec = new RuleDefinitionSpec();
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("param1", "val1");
        spec.setParameters(parameters);
		this.sut.setSpec(spec);
		this.sut.setStatus(InstanceStatus.ADDED);
		this.sut.flush();
		userHomeContext.flush();

		this.sut.markForDeletion();
		this.sut.flush();
		userHomeContext.flush();

        Assertions.assertEquals(InstanceStatus.DELETED, this.sut.getStatus());
        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        RuleDefinitionWrapper sut2 = homeContext2.getUserHome().getRules().getByObjectName("conn", true);
        Assertions.assertNull(sut2);
    }

    @Test
    void getSpec_whenSpecFilePresentInFolder_thenReturnsSpec() {
        RuleDefinitionSpec spec = new RuleDefinitionSpec();
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("param1", "val1");
        spec.setParameters(parameters);
		this.sut.setSpec(spec);
		this.sut.setStatus(InstanceStatus.ADDED);
		this.sut.flush();
		userHomeContext.flush();

        FileRuleDefinitionWrapperImpl sut2 = new FileRuleDefinitionWrapperImpl(ruleFolder, "rule", "rule", this.yamlSerializer, false);
        RuleDefinitionSpec spec2 = sut2.getSpec();
        Assertions.assertNotNull(spec2);
    }

    @Test
    void getSpec_whenCalledTwice_thenReturnsTheSameInstance() {
        RuleDefinitionSpec spec = new RuleDefinitionSpec();
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("param1", "val1");
        spec.setParameters(parameters);
		this.sut.setSpec(spec);
		this.sut.setStatus(InstanceStatus.ADDED);
		this.sut.flush();
		userHomeContext.flush();

        FileRuleDefinitionWrapperImpl sut2 = new FileRuleDefinitionWrapperImpl(ruleFolder,"rule", "rule", this.yamlSerializer, false);
        RuleDefinitionSpec spec2 = sut2.getSpec();
        Assertions.assertNotNull(spec2);
        Assertions.assertSame(spec2, sut2.getSpec());
    }
}
