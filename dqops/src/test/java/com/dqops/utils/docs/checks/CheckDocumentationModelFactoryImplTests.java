/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
import com.dqops.services.check.matching.SimilarCheckGroupingKeyFactoryImpl;
import com.dqops.services.check.matching.SimilarCheckMatchingServiceImpl;
import com.dqops.utils.docs.LinkageStore;
import com.dqops.utils.docs.rules.RuleDocumentationModelFactoryImpl;
import com.dqops.utils.docs.sensors.SensorDocumentationModelFactoryImpl;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import com.dqops.utils.serialization.JsonSerializerObjectMother;
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
                DqoHomeContextFactoryObjectMother.getRealDqoHomeContextFactory(), new SimilarCheckGroupingKeyFactoryImpl());
        ModelToSpecCheckMappingServiceImpl uiToSpecCheckMappingService = new ModelToSpecCheckMappingServiceImpl(reflectionService);
        this.sut = new CheckDocumentationModelFactoryImpl(
                similarCheckMatchingService,
                new SensorDocumentationModelFactoryImpl(dqoHomeContext, specToModelCheckMappingService),
                new RuleDocumentationModelFactoryImpl(projectRoot, dqoHomeContext, specToModelCheckMappingService),
                uiToSpecCheckMappingService,
                YamlSerializerObjectMother.getDefault(),
                JsonSerializerObjectMother.getDefault(),
                JinjaTemplateRenderServiceObjectMother.getDefault(),
                new LinkageStore<>());

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
