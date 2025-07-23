/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.rules;

import com.dqops.BaseTest;
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceImpl;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeDirectFactory;
import com.dqops.services.check.mapping.SpecToModelCheckMappingServiceImpl;
import com.dqops.utils.docs.HandlebarsDocumentationUtilities;
import com.dqops.utils.docs.LinkageStore;
import com.dqops.utils.docs.files.DocumentationFolder;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class RuleDocumentationGeneratorImplTests extends BaseTest {
    private RuleDocumentationGeneratorImpl sut;
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
        RuleDocumentationModelFactoryImpl ruleDocumentationModelFactory = new RuleDocumentationModelFactoryImpl(projectRootPath, dqoHomeContext, specToUiCheckMappingService);
        this.linkageStore = new LinkageStore<>();

        this.sut = new RuleDocumentationGeneratorImpl(ruleDocumentationModelFactory);
    }

    @Test
    void renderRuleDocumentation_whenCalled_generatesDocumentationForAllRulesInMemory() {
        DocumentationFolder documentationFolder = this.sut.renderRuleDocumentation(this.projectRootPath, linkageStore, this.dqoHome);
        Assertions.assertEquals(0, documentationFolder.getSubFolders().size());
    }

    @Test
    void createRuleDocumentationModels_whenCalled_generatesListOfRuleDocumentationModel() {
        List<RuleDocumentationModel> ruleDocumentationModels = new ArrayList<>(this.sut.createRuleDocumentationModels(projectRootPath));
        Assertions.assertTrue(ruleDocumentationModels.size() > 1);
    }

    @Test
    void groupRulesByCategory_whenCalled_generatesListOfRuleGroupedDocumentationModel() {
        List<RuleDocumentationModel> ruleDocumentationModels = new ArrayList<>(this.sut.createRuleDocumentationModels(projectRootPath));
        List<RuleGroupedDocumentationModel> ruleGroupedDocumentationModels = this.sut.groupRulesByCategory(ruleDocumentationModels);
        Assertions.assertTrue(ruleGroupedDocumentationModels.size() >= 3);
    }
}
