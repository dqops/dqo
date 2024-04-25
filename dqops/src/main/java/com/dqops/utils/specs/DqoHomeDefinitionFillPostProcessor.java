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

package com.dqops.utils.specs;

import com.dqops.execution.rules.finder.RuleDefinitionFindServiceImpl;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeDirectFactory;
import com.dqops.services.check.mapping.SpecToModelCheckMappingServiceImpl;
import com.dqops.services.check.matching.SimilarCheckGroupingKeyFactoryImpl;
import com.dqops.services.check.matching.SimilarCheckMatchingServiceImpl;
import com.dqops.utils.docs.HandlebarsDocumentationUtilities;
import com.dqops.utils.reflection.ReflectionServiceImpl;

import java.nio.file.Path;

/**
 * Build post processing utility called from maven. Updates sensor and rule definition specification with the
 * correct list of fields retrieved using reflection.
 */
public class DqoHomeDefinitionFillPostProcessor {
    /**
     * Main method of the documentation generator that generates markdown documentation files for mkdocs.
     * @param args Command line arguments.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        try {
            if (args.length == 0) {
                System.out.println("DQOPs Home specification fix utility");
                System.out.println("Missing required parameter: <path to the project dir>");
                return;
            }

            System.out.println("Updating specification files in DQOps Home for project: " + args[0]);
            Path projectDir = Path.of(args[0]);
            HandlebarsDocumentationUtilities.configure(projectDir);

            Path dqoHomePath = projectDir.resolve("../home").toAbsolutePath().normalize();
            DqoHomeContext dqoHomeContext = DqoHomeDirectFactory.openDqoHome(dqoHomePath, false);

            updateSpecificationsForRules(projectDir, dqoHomeContext);
            updateSpecificationsForSensors(projectDir, dqoHomeContext);
            updateSpecificationsForChecks(dqoHomeContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates specifications for rules.
     * @param projectRoot Path to the project root.
     * @param dqoHomeContext DQOps home instance with access to the rule references.
     */
    public static void updateSpecificationsForRules(Path projectRoot, DqoHomeContext dqoHomeContext) {
        SpecToModelCheckMappingServiceImpl specToUiCheckMappingService = SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(
                new ReflectionServiceImpl(), new SensorDefinitionFindServiceImpl(), new RuleDefinitionFindServiceImpl());
        RuleDefinitionDefaultSpecUpdateService ruleDefinitionDefaultSpecUpdateService =
                new RuleDefinitionDefaultSpecUpdateServiceImpl(dqoHomeContext, specToUiCheckMappingService);

        ruleDefinitionDefaultSpecUpdateService.updateRuleSpecifications(projectRoot, dqoHomeContext);
    }


    /**
     * Updates specifications for sensors.
     * @param projectRoot Path to the project root.
     * @param dqoHomeContext DQOps home instance with access to the sensor references.
     */
    public static void updateSpecificationsForSensors(Path projectRoot, DqoHomeContext dqoHomeContext) {
        SpecToModelCheckMappingServiceImpl specToUiCheckMappingService = SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(
                new ReflectionServiceImpl(), new SensorDefinitionFindServiceImpl(), new RuleDefinitionFindServiceImpl());
        SensorDefinitionDefaultSpecUpdateService sensorDefinitionDefaultSpecUpdateService =
                new SensorDefinitionDefaultSpecUpdateServiceImpl(dqoHomeContext, specToUiCheckMappingService);

        sensorDefinitionDefaultSpecUpdateService.updateSensorSpecifications(projectRoot, dqoHomeContext);
    }

    /**
     * Updates specifications for built-in checks.
     * @param dqoHomeContext DQOps home instance with access to the check references.
     */
    public static void updateSpecificationsForChecks(DqoHomeContext dqoHomeContext) {
        SpecToModelCheckMappingServiceImpl specToUiCheckMappingService = SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(
                new ReflectionServiceImpl(), new SensorDefinitionFindServiceImpl(), new RuleDefinitionFindServiceImpl());
        CheckDefinitionDefaultSpecUpdateService sensorDefinitionDefaultSpecUpdateService =
                new CheckDefinitionDefaultSpecUpdateServiceImpl(new SimilarCheckMatchingServiceImpl(specToUiCheckMappingService,
                        new DqoHomeContextFactory() {
                            @Override
                            public DqoHomeContext openLocalDqoHome() {
                                return dqoHomeContext;
                            }
                        }, new SimilarCheckGroupingKeyFactoryImpl()));

        sensorDefinitionDefaultSpecUpdateService.updateCheckSpecifications(dqoHomeContext);
    }
}
