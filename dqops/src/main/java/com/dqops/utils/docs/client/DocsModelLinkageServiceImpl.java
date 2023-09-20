/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dqops.utils.docs.client;

import com.dqops.checks.*;
import com.dqops.connectors.ConnectionProviderSpecificParameters;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.rules.CustomRuleParametersSpec;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.sensors.CustomSensorParametersSpec;
import com.dqops.services.check.matching.SimilarCheckMatchingService;
import com.dqops.services.check.matching.SimilarChecksContainer;
import com.dqops.statistics.AbstractStatisticsCollectorSpec;
import com.dqops.utils.reflection.TargetClassSearchUtility;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DocsModelLinkageServiceImpl implements DocsModelLinkageService {
    private final SimilarCheckMatchingService similarCheckMatchingService;
    private SimilarChecksContainer tableSimilarChecks = null;
    private SimilarChecksContainer columnSimilarChecks = null;

    private final Map<String, Class<? extends AbstractSensorParametersSpec>> sensorParametersSpecsClasses;
    private final Map<String, Class<? extends AbstractRuleParametersSpec>> ruleParametersSpecsClasses;
    private final Map<String, Class<? extends ConnectionProviderSpecificParameters>> connectionProviderParametersClasses;
    private final Map<String, Class<? extends AbstractCheckCategorySpec>> checkCategorySpecsClasses;
    private final Map<String, Class<? extends AbstractCheckSpec>> checkSpecsClasses;

    public DocsModelLinkageServiceImpl(Path projectDir,
                                       SimilarCheckMatchingService similarCheckMatchingService) {
        this.similarCheckMatchingService = similarCheckMatchingService;

        this.sensorParametersSpecsClasses = TargetClassSearchUtility.findClasses(
                "com.dqops.sensors", projectDir, AbstractSensorParametersSpec.class
        ).stream().collect(Collectors.toMap(
                Class::getSimpleName,
                Function.identity()
        ));

        this.ruleParametersSpecsClasses = TargetClassSearchUtility.findClasses(
                "com.dqops.rules", projectDir, AbstractRuleParametersSpec.class
        ).stream().collect(Collectors.toMap(
                Class::getSimpleName,
                Function.identity()
        ));

        this.connectionProviderParametersClasses = TargetClassSearchUtility.findClasses(
                "com.dqops.connectors", projectDir, ConnectionProviderSpecificParameters.class
        ).stream().collect(Collectors.toMap(
                Class::getSimpleName,
                Function.identity()
        ));

        this.checkCategorySpecsClasses = TargetClassSearchUtility.findClasses(
                "com.dqops.checks", projectDir, AbstractCheckCategorySpec.class
        ).stream().collect(Collectors.toMap(
                Class::getSimpleName,
                Function.identity()
        ));

        this.checkSpecsClasses = Stream.concat(
                TargetClassSearchUtility.findClasses(
                        "com.dqops.checks.column", projectDir, AbstractCheckSpec.class
                ).stream(),
                TargetClassSearchUtility.findClasses(
                        "com.dqops.checks.table", projectDir, AbstractCheckSpec.class
                ).stream()
        ).collect(Collectors.toMap(
                Class::getSimpleName,
                Function.identity()
        ));
    }

    protected SimilarChecksContainer getTableSimilarChecks() {
        if (tableSimilarChecks == null) {
            tableSimilarChecks = similarCheckMatchingService.findSimilarTableChecks();
        }
        return tableSimilarChecks;
    }

    protected SimilarChecksContainer getColumnSimilarChecks() {
        if (columnSimilarChecks == null) {
            columnSimilarChecks = similarCheckMatchingService.findSimilarColumnChecks();
        }
        return columnSimilarChecks;
    }

    public final Set<String> notMapped = new HashSet<>();

    /**
     * Gets docs linkage for a certain class, if this class can be linked to external pages in the documentation.
     * @param modelClassName Searched class name.
     * @return Path to the class's documentation if it can be found. Null otherwise.
     */
    @Override
    public Path findDocsLinkage(String modelClassName) {
        Path docsPath = Path.of("/docs");

        if (sensorParametersSpecsClasses.containsKey(modelClassName)) {
            docsPath = docsPath.resolve(Path.of("reference", "sensors"));
            Class<? extends AbstractSensorParametersSpec> clazz = sensorParametersSpecsClasses.get(modelClassName);
            if (CustomSensorParametersSpec.class.isAssignableFrom(clazz)) {
                notMapped.add(modelClassName);
                return null;
            }
            AbstractSensorParametersSpec sensorParametersSpec = getClassDefaultInstance(clazz);

            Path sensorDefinitionPath = convertSensorDefinitionPath(sensorParametersSpec.getSensorDefinitionName());
            return docsPath.resolve(sensorDefinitionPath);
        }
        else if (ruleParametersSpecsClasses.containsKey(modelClassName)) {
            docsPath = docsPath.resolve(Path.of("reference", "rules"));
            Class<? extends AbstractRuleParametersSpec> clazz = ruleParametersSpecsClasses.get(modelClassName);
            if (CustomRuleParametersSpec.class.isAssignableFrom(clazz)) {
                notMapped.add(modelClassName);
                return null;
            }
            AbstractRuleParametersSpec ruleParametersSpec = getClassDefaultInstance(clazz);

            Path ruleDefinitionPath = convertRuleDefinitionPath(ruleParametersSpec.getRuleDefinitionName());
            return docsPath.resolve(ruleDefinitionPath);
        }
        else if (connectionProviderParametersClasses.containsKey(modelClassName)) {
            docsPath = docsPath.resolve(Path.of("reference", "yaml", "ConnectionYaml"));
            return docsPath.resolve("#" + modelClassName.toLowerCase());
        }
        else if (checkCategorySpecsClasses.containsKey(modelClassName)) {
            docsPath = docsPath.resolve(Path.of("reference", "yaml"));
            Class<? extends AbstractCheckCategorySpec> clazz = checkCategorySpecsClasses.get(modelClassName);
            AbstractCheckCategorySpec checkCategorySpec = getClassDefaultInstance(clazz);

            CheckType checkType = checkCategorySpec.getCheckType();
            docsPath = docsPath.resolve(checkType.name());

            List<String> pathFileNameWords = new ArrayList<>();
            CheckTarget checkTarget = checkCategorySpec.getCheckTarget();
            pathFileNameWords.add(checkTarget.name());
            if (checkType == CheckType.monitoring || checkType == CheckType.partitioned) {
                CheckTimeScale checkTimeScale = checkCategorySpec.getCheckTimeScale();
                pathFileNameWords.add(checkTimeScale.name());
            }
            pathFileNameWords.add(checkType.name());
            pathFileNameWords.add("checks");
            String pathFileName = String.join("-", pathFileNameWords);
            docsPath = docsPath.resolve(pathFileName);
            return docsPath.resolve("#" + modelClassName.toLowerCase());
        }
        else if (checkSpecsClasses.containsKey(modelClassName)) {
            docsPath = docsPath.resolve(Path.of("checks"));
            Class<? extends AbstractCheckSpec> clazz = checkSpecsClasses.get(modelClassName);
            AbstractCheckSpec checkSpec = getClassDefaultInstance(clazz);

//            CheckTarget checkTarget = getCheckTarget(clazz);
//            docsPath = docsPath.resolve(checkTarget.name());
//
//            String checkCategory = getCheckCategory(clazz);
//            docsPath = docsPath.resolve(checkCategory);

            String checkNameFromSpec = getCheckNameFromSpec(checkSpec);

//            SimilarChecksContainer similarChecks = checkTarget == CheckTarget.column ? getColumnSimilarChecks() : getTableSimilarChecks();
//            Collection<SimilarChecksGroup> similarChecksInCategory = similarChecks.getChecksPerGroup().get(checkCategory);
//            String name = similarChecksInCategory.stream().findFirst().get().getSimilarChecks().get(0).getCheckName();

            //table/accuracy/total-row-count-match-percent/#daily-total-row-count-match-percent

            return docsPath.resolve(checkNameFromSpec);
        }

        notMapped.add(modelClassName);
        return null;
    }

    private String getCheckNameFromSpec(AbstractCheckSpec checkSpec) {
        String sensorName = checkSpec.getParameters().getSensorDefinitionName();
        return sensorName.replace("_", "-");
    }

    private <T> T getClassDefaultInstance(Class<? extends T> clazz) {
        try {
            Optional<Constructor<?>> oConstructor = Arrays.stream(clazz.getConstructors())
                    .filter(constructor -> constructor.getParameterCount() == 0)
                    .findFirst();
            return (T) oConstructor.get().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Path convertSensorDefinitionPath(String definitionName) {
        String[] splitName = definitionName.split("/");

        String target = splitName[0];
        String category = splitName[1];
        String fileName = splitName[splitName.length - 1];

        splitName[1] = String.join("-", category, target, "sensors");
        splitName[splitName.length - 1] = "#" + fileName.replace("_", "-");
        return Path.of(String.join("/", splitName));
    }

    private Path convertRuleDefinitionPath(String definitionName) {
        String[] splitName = definitionName.split("/");

        String fileName = splitName[splitName.length - 1];
        splitName[splitName.length - 1] = "#" + fileName.replace("_", "-");
        return Path.of(String.join("/", splitName));
    }

    private CheckTarget getCheckTarget(Class<? extends AbstractCheckSpec> checkClass) {
        String packageName = getIndexedPackageModuleName(checkClass, 3);
        if (packageName.equals("column")) {
            return CheckTarget.column;
        } else if (packageName.equals("table")) {
            return CheckTarget.table;
        } else {
            throw new RuntimeException("Invalid check target for class: " + checkClass.getName());
        }
    }

    private String getCheckCategory(Class<? extends AbstractCheckSpec> checkClass) {
        return getIndexedPackageModuleName(checkClass, 5);
    }

    private String getIndexedPackageModuleName(Class<?> clazz, int i) {
        String packageName = clazz.getPackageName();
        String[] packageNameSplit = packageName.split("\\.");
        return packageNameSplit[i];
    }
}
