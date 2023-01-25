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

import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.userhome.UserHomeImpl;
import ai.dqo.services.check.matching.SimilarCheckMatchingService;
import ai.dqo.services.check.matching.SimilarChecksContainer;
import ai.dqo.services.check.matching.SimilarChecksGroup;
import ai.dqo.utils.docs.rules.RuleDocumentationModelFactory;
import ai.dqo.utils.docs.sensors.SensorDocumentationModelFactory;
import ai.dqo.utils.serialization.YamlSerializer;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Check documentation model factory. Creates documentation objects for each check.
 */
@Component
public class CheckDocumentationModelFactoryImpl implements CheckDocumentationModelFactory {
    private static final Map<String, String> TABLE_CATEGORY_HELP = new LinkedHashMap<>() {{
       put("strings", "Here is the documentation for the strings category");
       // TODO: add more
    }};

    private static final Map<String, String> COLUMN_CATEGORY_HELP = new LinkedHashMap<>() {{
        put("timeliness", "Here is the documentation for the timeliness category");
        // TODO: add more
    }};

    private DqoHomeContext dqoHomeContext;
    private SimilarCheckMatchingService similarCheckMatchingService;
    private SensorDocumentationModelFactory sensorDocumentationModelFactory;
    private RuleDocumentationModelFactory ruleDocumentationModelFactory;
    private YamlSerializer yamlSerializer;

    /**
     * Creates a check documentation service.
     * @param dqoHomeContext DQO Home.
     * @param yamlSerializer Yaml serializer.
     */
    @Autowired
    public CheckDocumentationModelFactoryImpl(DqoHomeContext dqoHomeContext,
                                              SimilarCheckMatchingService similarCheckMatchingService,
                                              SensorDocumentationModelFactory sensorDocumentationModelFactory,
                                              RuleDocumentationModelFactory ruleDocumentationModelFactory,
                                              YamlSerializer yamlSerializer) {
        this.dqoHomeContext = dqoHomeContext;
        this.similarCheckMatchingService = similarCheckMatchingService;
        this.sensorDocumentationModelFactory = sensorDocumentationModelFactory;
        this.ruleDocumentationModelFactory = ruleDocumentationModelFactory;
        this.yamlSerializer = yamlSerializer;
    }

    /**
     * Create a list of check documentation models for table level checks. Each category contains a list of similar checks to be documented on the same page.
     * @return Documentation for each check category on a table level.
     */
    @Override
    public List<CheckCategoryDocumentationModel> makeDocumentationForTableChecks() {
        UserHomeImpl userHome = new UserHomeImpl();
        ConnectionWrapper connectionWrapper = userHome.getConnections().createAndAddNew("<target_connection>");
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("<target_schema>", "<target_table>"));
        TableSpec tableSpec = new TableSpec();
        tableWrapper.setSpec(tableSpec);

        SimilarChecksContainer similarTableChecks = this.similarCheckMatchingService.findSimilarTableChecks(tableSpec);
        Map<String, Collection<SimilarChecksGroup>> checksPerGroup = similarTableChecks.getChecksPerGroup();
        List<CheckCategoryDocumentationModel> resultList = buildDocumentationForChecks(checksPerGroup, TABLE_CATEGORY_HELP);

        return resultList;
    }

    /**
     * Create a list of check documentation models for column level checks. Each category contains a list of similar checks to be documented on the same page.
     * @return Documentation for each check category on a column level.
     */
    @Override
    public List<CheckCategoryDocumentationModel> makeDocumentationForColumnChecks() {
        UserHomeImpl userHome = new UserHomeImpl();
        ConnectionWrapper connectionWrapper = userHome.getConnections().createAndAddNew("<target_connection>");
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("<target_schema>", "<target_table>"));
        TableSpec tableSpec = new TableSpec();
        tableWrapper.setSpec(tableSpec);
        ColumnSpec columnSpec = new ColumnSpec();
        tableSpec.getColumns().put("<target_column>", columnSpec);

        SimilarChecksContainer similarTableChecks = this.similarCheckMatchingService.findSimilarColumnChecks(tableSpec, columnSpec);
        Map<String, Collection<SimilarChecksGroup>> checksPerGroup = similarTableChecks.getChecksPerGroup();
        List<CheckCategoryDocumentationModel> resultList = buildDocumentationForChecks(checksPerGroup, COLUMN_CATEGORY_HELP);

        return resultList;
    }

    /**
     * Builds the documentation model for a given list of checks.
     * @param checksPerGroup  Dictionary of checks, grouped by a category.
     * @param categoryHelpMap Dictionary to find documentation for each category.
     * @return List of category documentation models.
     */
    @NotNull
    public List<CheckCategoryDocumentationModel> buildDocumentationForChecks(
            Map<String, Collection<SimilarChecksGroup>> checksPerGroup, Map<String, String> categoryHelpMap) {
        List<CheckCategoryDocumentationModel> resultList = new ArrayList<>();

        for (Map.Entry<String, Collection<SimilarChecksGroup>> similarChecksInGroup : checksPerGroup.entrySet()) {
            CheckCategoryDocumentationModel categoryDocumentationModel = new CheckCategoryDocumentationModel();
            String categoryName = similarChecksInGroup.getKey();
            categoryDocumentationModel.setCategoryName(categoryName);
            categoryDocumentationModel.setCategoryHelp(categoryHelpMap.get(categoryName));

            for (SimilarChecksGroup similarChecksGroup : similarChecksInGroup.getValue()) {
                // TODO: make docs
            }

            resultList.add(categoryDocumentationModel);
        }
        return resultList;
    }
}
