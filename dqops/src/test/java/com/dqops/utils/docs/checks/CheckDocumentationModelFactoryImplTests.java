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
package com.dqops.utils.docs.checks;

import com.dqops.BaseTest;
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceImpl;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import com.dqops.execution.sqltemplates.rendering.JinjaTemplateRenderServiceObjectMother;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactoryObjectMother;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;
import com.dqops.services.check.mapping.SpecToModelCheckMappingService;
import com.dqops.services.check.mapping.SpecToModelCheckMappingServiceImpl;
import com.dqops.services.check.mapping.ModelToSpecCheckMappingServiceImpl;
import com.dqops.services.check.matching.SimilarCheckMatchingServiceImpl;
import com.dqops.utils.docs.HandledClassesLinkageStore;
import com.dqops.utils.docs.rules.RuleDocumentationModelFactoryImpl;
import com.dqops.utils.docs.sensors.SensorDocumentationModelFactoryImpl;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import com.dqops.utils.serialization.YamlSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class CheckDocumentationModelFactoryImplTests extends BaseTest {
    private CheckDocumentationModelFactoryImpl sut;
    private Path checklistFolder;

    @BeforeEach
    void setUp() {
        Path projectRoot = Path.of(".");
        ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
        SpecToModelCheckMappingService specToModelCheckMappingService = SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(
                reflectionService, new SensorDefinitionFindServiceImpl(), new RuleDefinitionFindServiceImpl());
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();
        SimilarCheckMatchingServiceImpl similarCheckMatchingService = new SimilarCheckMatchingServiceImpl(specToModelCheckMappingService,
                DqoHomeContextFactoryObjectMother.getRealDqoHomeContextFactory());
        ModelToSpecCheckMappingServiceImpl uiToSpecCheckMappingService = new ModelToSpecCheckMappingServiceImpl(reflectionService);
        this.sut = new CheckDocumentationModelFactoryImpl(
                similarCheckMatchingService,
                new SensorDocumentationModelFactoryImpl(dqoHomeContext, specToModelCheckMappingService),
                new RuleDocumentationModelFactoryImpl(projectRoot, dqoHomeContext, specToModelCheckMappingService),
                uiToSpecCheckMappingService,
                YamlSerializerObjectMother.getDefault(),
                JinjaTemplateRenderServiceObjectMother.getDefault(),
                new HandledClassesLinkageStore());

        try {
            String mavenTargetFolderPath = System.getenv("DQO_TEST_TEMPORARY_FOLDER");
            checklistFolder = Path.of(mavenTargetFolderPath).resolve("checklist");
            if (!checklistFolder.toFile().exists()) {
                Files.createDirectories(checklistFolder);
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    void makeDocumentationForTableChecks_whenCalled_thenGeneratesTableLevelSensorDocumentation() throws Exception {
        List<CheckCategoryDocumentationModel> documentationModels = this.sut.makeDocumentationForTableChecks();
        Assertions.assertTrue(documentationModels.size() > 1);

        List<String> listOfAllCheckNames = documentationModels.stream()
                .flatMap(category -> category.getCheckGroups().stream())
                .flatMap(checkGroup -> checkGroup.getAllChecks().stream())
                .map(checkModel -> checkModel.getCheckName())
                .collect(Collectors.toList());

        Path targetFilePath = checklistFolder.resolve("table_checks.csv");
        Files.write(targetFilePath, listOfAllCheckNames, StandardCharsets.UTF_8);
    }

    @Test
    void makeDocumentationForColumnChecks_whenCalled_thenGeneratesColumnLevelSensorDocumentation() throws Exception {
        List<CheckCategoryDocumentationModel> documentationModels = this.sut.makeDocumentationForColumnChecks();
        Assertions.assertTrue(documentationModels.size() > 1);
        List<String> listOfAllCheckNames = documentationModels.stream()
                .flatMap(category -> category.getCheckGroups().stream())
                .flatMap(checkGroup -> checkGroup.getAllChecks().stream())
                .map(checkModel -> checkModel.getCheckName())
                .collect(Collectors.toList());

        Path targetFilePath = checklistFolder.resolve("column_checks.csv");
        Files.write(targetFilePath, listOfAllCheckNames, StandardCharsets.UTF_8);
    }
}
