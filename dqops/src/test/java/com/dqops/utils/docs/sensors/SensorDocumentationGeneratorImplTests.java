/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.sensors;

import com.dqops.BaseTest;
import com.dqops.connectors.ProviderType;
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceImpl;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeDirectFactory;
import com.dqops.services.check.mapping.SpecToModelCheckMappingServiceImpl;
import com.dqops.utils.docs.HandlebarsDocumentationUtilities;
import com.dqops.utils.docs.LinkageStore;
import com.dqops.utils.docs.ProviderTypeModel;
import com.dqops.utils.docs.files.DocumentationFolder;
import com.dqops.utils.docs.files.DocumentationMarkdownFile;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class SensorDocumentationGeneratorImplTests extends BaseTest {
    private SensorDocumentationGeneratorImpl sut;
    private Path projectRootPath;
    private LinkageStore<Class<?>> linkageStore;
    private DqoHome dqoHome;

    @BeforeEach
    void setUp() {
        this.projectRootPath = Path.of(".");
        HandlebarsDocumentationUtilities.configure(this.projectRootPath);
        Path dqoHomePath = Path.of(System.getenv("DQO_HOME"));
        DqoHomeContext dqoHomeContext = DqoHomeDirectFactory.openDqoHome(dqoHomePath, true);
        this.dqoHome = dqoHomeContext.getDqoHome();
        SpecToModelCheckMappingServiceImpl specToUiCheckMappingService = SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(
                new ReflectionServiceImpl(), new SensorDefinitionFindServiceImpl(), new RuleDefinitionFindServiceImpl());
        SensorDocumentationModelFactoryImpl sensorDocumentationModelFactory = new SensorDocumentationModelFactoryImpl(dqoHomeContext, specToUiCheckMappingService);
        this.linkageStore = new LinkageStore<>();

        this.sut = new SensorDocumentationGeneratorImpl(sensorDocumentationModelFactory);
    }

    @Test
    void renderSensorDocumentation_whenCalled_generatesDocumentationForAllSensorsInMemory() {
        DocumentationFolder documentationFolder = this.sut.renderSensorDocumentation(this.projectRootPath, linkageStore, this.dqoHome);
        Assertions.assertEquals(2, documentationFolder.getSubFolders().size());
    }

    @Test
    void renderSensorDocumentation_whenCalledForTableVolumeChecks_generatesDocumentationForEveryConnector() {
        DocumentationFolder documentationFolder = this.sut.renderSensorDocumentation(this.projectRootPath, linkageStore, this.dqoHome);
        Optional<String> volumeSensorsDocumentation = documentationFolder.getSubFolders().stream()
                .filter(targetFolder -> targetFolder.getFolderName().equals("table"))
                .flatMap(tableFolder -> tableFolder.getFiles().stream())
                .filter(categoryFile -> categoryFile.getFileName().equals("volume-table-sensors.md"))
                .map(DocumentationMarkdownFile::getFileContent)
                .findAny();

        Assertions.assertTrue(volumeSensorsDocumentation.isPresent());
        String volumesContent = volumeSensorsDocumentation.get();

        for (ProviderType providerType : ProviderType.values()) {
            String providerDisplay = ProviderTypeModel.fromProviderType(providerType).getProviderTypeDisplayName();
            Assertions.assertTrue(volumesContent.contains(providerDisplay), "\"" + providerDisplay + "\" not found in contents:\n" + volumesContent);
        }
    }

    @Test
    void createSensorDocumentationModels_whenCalled_generatesListOfSensorDocumentationModel() {
        List<SensorDocumentationModel> sensorDocumentationModels = this.sut.createSensorDocumentationModels(this.projectRootPath);
        Assertions.assertTrue(sensorDocumentationModels.size() > 1);
    }

    @Test
    void groupSensorsByTarget_whenCalled_generatesListOfSensorGroupedDocumentationModel() {
        List<SensorDocumentationModel> sensorDocumentationModels = this.sut.createSensorDocumentationModels(this.projectRootPath);
        List<SensorGroupedDocumentationModel> sensorGroupedDocumentationModels = this.sut.groupSensors(sensorDocumentationModels);
        Assertions.assertTrue(sensorGroupedDocumentationModels.size() > 2);
    }
}
