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
import com.dqops.checks.custom.CustomCheckSpec;
import com.dqops.connectors.ConnectionProviderSpecificParameters;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionSpec;
import com.dqops.metadata.definitions.sensors.SensorDefinitionSpec;
import com.dqops.metadata.fields.ParameterDefinitionSpec;
import com.dqops.metadata.incidents.TableIncidentGroupingSpec;
import com.dqops.metadata.scheduling.DefaultSchedulesSpec;
import com.dqops.metadata.sources.BaseProviderParametersSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.ConnectionWrapperImpl;
import com.dqops.metadata.sources.TableWrapperImpl;
import com.dqops.metadata.storage.localfiles.sources.ConnectionYaml;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.rules.CustomRuleParametersSpec;
import com.dqops.rules.RuleTimeWindowSettingsSpec;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.sensors.CustomSensorParametersSpec;
import com.dqops.services.check.matching.SimilarCheckMatchingService;
import com.dqops.services.check.matching.SimilarChecksContainer;
import com.dqops.statistics.AbstractRootStatisticsCollectorsContainerSpec;
import com.dqops.statistics.AbstractStatisticsCollectorCategorySpec;
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
    public final TreeSet<String> notMapped = new TreeSet<>();

    private final SimilarCheckMatchingService similarCheckMatchingService;
    private SimilarChecksContainer tableSimilarChecks = null;
    private SimilarChecksContainer columnSimilarChecks = null;

    private final Map<String, Class<? extends AbstractSensorParametersSpec>> sensorParametersSpecsClasses;
    private final Map<String, Class<? extends AbstractRuleParametersSpec>> ruleParametersSpecsClasses;
    private final Map<String, Class<? extends ConnectionProviderSpecificParameters>> connectionProviderParametersClasses;
    private final Map<String, Class<? extends AbstractCheckCategorySpec>> checkCategorySpecsClasses;
    private final Map<String, Class<? extends AbstractCheckSpec>> checkSpecsClasses;

    private final Map<String, Class<?>> tableYamlClasses;
    private final Map<String, Class<?>> tableProfilingChecksClasses;
    private final Map<String, Class<?>> connectionYamlClasses;

    private final Map<String, Path> extraLinkageMappings;

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

        this.tableYamlClasses = generateTableYamlClasses(projectDir);
        this.tableProfilingChecksClasses = new HashMap<>() {{
            put(CustomCheckSpec.class.getSimpleName(), CustomCheckSpec.class);
            put(CustomRuleParametersSpec.class.getSimpleName(), CustomRuleParametersSpec.class);
            put(CustomSensorParametersSpec.class.getSimpleName(), CustomSensorParametersSpec.class);
        }};
        this.connectionYamlClasses = generateConnectionYamlClasses(projectDir);
        this.extraLinkageMappings = generateExtraLinkageMappings();
    }

    protected Map<String, Class<?>> generateTableYamlClasses(Path projectDir) {
        Map<String, Class<?>> tableYaml = new HashMap<>();
        tableYaml.putAll(
                TargetClassSearchUtility.findClasses(
                        "com.dqops.metadata.sources", projectDir, AbstractSpec.class
                ).stream().collect(Collectors.toMap(
                        Class::getSimpleName,
                        Function.identity()
                ))
        );
        tableYaml.remove("");
        tableYaml.remove(ConnectionSpec.class.getSimpleName());
        tableYaml.remove(TableWrapperImpl.class.getSimpleName());
        tableYaml.remove(ConnectionWrapperImpl.class.getSimpleName());

        tableYaml.putAll(
                TargetClassSearchUtility.findClasses(
                        "com.dqops.metadata.comparisons", projectDir, AbstractSpec.class
                ).stream().collect(Collectors.toMap(
                        Class::getSimpleName,
                        Function.identity()
                ))
        );

        tableYaml.putAll(
                TargetClassSearchUtility.findClasses(
                        "com.dqops.statistics", projectDir, AbstractRootStatisticsCollectorsContainerSpec.class
                ).stream().collect(Collectors.toMap(
                        Class::getSimpleName,
                        Function.identity()
                ))
        );
        tableYaml.putAll(
                TargetClassSearchUtility.findClasses(
                        "com.dqops.statistics", projectDir, AbstractStatisticsCollectorCategorySpec.class
                ).stream().collect(Collectors.toMap(
                        Class::getSimpleName,
                        Function.identity()
                ))
        );
        tableYaml.putAll(
                TargetClassSearchUtility.findClasses(
                        "com.dqops.statistics", projectDir, AbstractStatisticsCollectorSpec.class
                ).stream().collect(Collectors.toMap(
                        Class::getSimpleName,
                        Function.identity()
                ))
        );
        tableYaml.put(TableIncidentGroupingSpec.class.getSimpleName(), TableIncidentGroupingSpec.class);

        return tableYaml;
    }

    protected Map<String, Class<?>> generateConnectionYamlClasses(Path projectDir) {
        Map<String, Class<?>> connectionYaml = new HashMap<>();
        connectionYaml.putAll(
                TargetClassSearchUtility.findClasses(
                                "com.dqops.metadata.groupings", projectDir, AbstractSpec.class
                        ).stream()
                        .collect(Collectors.toMap(
                                Class::getSimpleName,
                                Function.identity()
                        ))
        );

        connectionYaml.putAll(
                TargetClassSearchUtility.findClasses(
                        "com.dqops.metadata.incidents", projectDir, AbstractSpec.class
                ).stream()
                        .filter(c -> !c.getSimpleName().toLowerCase().contains("table"))
                        .collect(Collectors.toMap(
                                Class::getSimpleName,
                                Function.identity()
                        ))
        );

        connectionYaml.put(ConnectionYaml.class.getSimpleName(), ConnectionYaml.class);
        connectionYaml.put(ConnectionSpec.class.getSimpleName(), ConnectionSpec.class);
        connectionYaml.put(DefaultSchedulesSpec.class.getSimpleName(), DefaultSchedulesSpec.class);

        connectionYaml.putAll(
                TargetClassSearchUtility.findClasses(
                        "com.dqops.connectors", projectDir, BaseProviderParametersSpec.class
                ).stream().collect(Collectors.toMap(
                        Class::getSimpleName,
                        Function.identity()
                ))
        );

        return connectionYaml;
    }

    protected Map<String, Path> generateExtraLinkageMappings() {
        Map<String, Path> extraLinkage = new HashMap<>();

        extraLinkage.put(ProviderSensorDefinitionSpec.class.getSimpleName(),
                Path.of("/docs/reference/yaml/ProviderSensorYaml/#providersensordefinitionspec"));
        extraLinkage.put(ParameterDefinitionSpec.class.getSimpleName(),
                Path.of("/docs/reference/yaml/RuleDefinitionYaml/#parameterdefinitionspec"));
        extraLinkage.put(RuleTimeWindowSettingsSpec.class.getSimpleName(),
                Path.of("/docs/reference/yaml/RuleDefinitionYaml/#ruletimewindowsettingsspec"));
        extraLinkage.put(SensorDefinitionSpec.class.getSimpleName(),
                Path.of("/docs/reference/yaml/SensorDefinitionYaml/#sensordefinitionspec"));

        return extraLinkage;
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
        else if (tableYamlClasses.containsKey(modelClassName)) {
            return docsPath.resolve("reference/yaml/TableYaml").resolve("#" + modelClassName.toLowerCase());
        }
        else if (tableProfilingChecksClasses.containsKey(modelClassName)) {
            return docsPath.resolve("reference/yaml/profiling/table-profiling-checks").resolve("#" + modelClassName.toLowerCase());
        }
        else if (connectionYamlClasses.containsKey(modelClassName)) {
            return docsPath.resolve("reference/yaml/ConnectionYaml").resolve("#" + modelClassName.toLowerCase());
        }
        else if (extraLinkageMappings.containsKey(modelClassName)) {
            return extraLinkageMappings.get(modelClassName);
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
