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
package ai.dqo.utils.docs.checks;

import ai.dqo.BaseTest;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import ai.dqo.execution.sqltemplates.rendering.JinjaTemplateRenderServiceObjectMother;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;
import ai.dqo.services.check.mapping.SpecToUiCheckMappingService;
import ai.dqo.services.check.mapping.SpecToUiCheckMappingServiceImpl;
import ai.dqo.services.check.mapping.UiToSpecCheckMappingServiceImpl;
import ai.dqo.services.check.matching.SimilarCheckMatchingServiceImpl;
import ai.dqo.utils.docs.rules.RuleDocumentationModelFactoryImpl;
import ai.dqo.utils.docs.sensors.SensorDocumentationModelFactoryImpl;
import ai.dqo.utils.reflection.ReflectionServiceImpl;
import ai.dqo.utils.serialization.YamlSerializerObjectMother;
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
        SpecToUiCheckMappingService specToUiCheckMappingService = SpecToUiCheckMappingServiceImpl.createInstanceUnsafe(
                reflectionService, new SensorDefinitionFindServiceImpl());
        SimilarCheckMatchingServiceImpl similarCheckMatchingService = new SimilarCheckMatchingServiceImpl(specToUiCheckMappingService);
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();
        UiToSpecCheckMappingServiceImpl uiToSpecCheckMappingService = new UiToSpecCheckMappingServiceImpl(reflectionService);
        this.sut = new CheckDocumentationModelFactoryImpl(
                similarCheckMatchingService,
                new SensorDocumentationModelFactoryImpl(dqoHomeContext, specToUiCheckMappingService),
                new RuleDocumentationModelFactoryImpl(projectRoot, dqoHomeContext, specToUiCheckMappingService),
                uiToSpecCheckMappingService,
                YamlSerializerObjectMother.getDefault(),
                JinjaTemplateRenderServiceObjectMother.getDefault()
        );

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
    void makeDocumentationForTableChecks_whenCalled_thenGeneratesTableLevelSensorDocumentation() {
        List<CheckCategoryDocumentationModel> documentationModels = this.sut.makeDocumentationForTableChecks();
        Assertions.assertTrue(documentationModels.size() > 1);
    }

    @Test
    void makeDocumentationForColumnChecks_whenCalled_thenGeneratesColumnLevelSensorDocumentation() {
        List<CheckCategoryDocumentationModel> documentationModels = this.sut.makeDocumentationForColumnChecks();
        Assertions.assertTrue(documentationModels.size() > 1);
    }

    @Test
    void makeDocumentationForTableChecks_whenCalledDuringTesting_thenGeneratesFileWithCheckNames() throws Exception {
        List<CheckCategoryDocumentationModel> documentationModels = this.sut.makeDocumentationForTableChecks();
        List<String> listOfAllCheckNames = documentationModels.stream()
                .flatMap(category -> category.getCheckGroups().stream())
                .flatMap(checkGroup -> checkGroup.getAllChecks().stream())
                .map(checkModel -> checkModel.getCheckName())
                .collect(Collectors.toList());

        Path targetFilePath = checklistFolder.resolve("table_checks.csv");
        Files.write(targetFilePath, listOfAllCheckNames, StandardCharsets.UTF_8);
    }

    @Test
    void makeDocumentationForColumnChecks_whenCalledDuringTesting_thenGeneratesFileWithCheckNames() throws Exception {
        List<CheckCategoryDocumentationModel> documentationModels = this.sut.makeDocumentationForColumnChecks();
        List<String> listOfAllCheckNames = documentationModels.stream()
                .flatMap(category -> category.getCheckGroups().stream())
                .flatMap(checkGroup -> checkGroup.getAllChecks().stream())
                .map(checkModel -> checkModel.getCheckName())
                .collect(Collectors.toList());

        Path targetFilePath = checklistFolder.resolve("column_checks.csv");
        Files.write(targetFilePath, listOfAllCheckNames, StandardCharsets.UTF_8);
    }
}
