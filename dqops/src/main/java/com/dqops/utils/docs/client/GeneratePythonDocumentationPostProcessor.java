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
package com.dqops.utils.docs.client;

import com.dqops.execution.rules.finder.RuleDefinitionFindServiceImpl;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeDirectFactory;
import com.dqops.services.check.mapping.SpecToModelCheckMappingService;
import com.dqops.services.check.mapping.SpecToModelCheckMappingServiceImpl;
import com.dqops.services.check.matching.SimilarCheckMatchingService;
import com.dqops.services.check.matching.SimilarCheckMatchingServiceImpl;
import com.dqops.utils.docs.*;
import com.dqops.utils.docs.client.apimodel.OpenAPIModel;
import com.dqops.utils.docs.client.models.ModelsDocumentationGenerator;
import com.dqops.utils.docs.client.models.ModelsDocumentationGeneratorImpl;
import com.dqops.utils.docs.client.models.ModelsDocumentationModelFactoryImpl;
import com.dqops.utils.docs.client.operations.OperationsDocumentationGenerator;
import com.dqops.utils.docs.client.operations.OperationsDocumentationGeneratorImpl;
import com.dqops.utils.docs.client.operations.OperationsDocumentationModelFactoryImpl;
import com.dqops.utils.docs.files.DocumentationFolder;
import com.dqops.utils.docs.files.DocumentationFolderFactory;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import com.google.common.base.CaseFormat;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class called from the maven build. Generates documentation for the Python client.
 */
public class GeneratePythonDocumentationPostProcessor {
    public static final Path baseClientDocsPath = Path.of("docs", "client");
    public static LinkageStore<String> linkageStore;
    public static final DocumentationReflectionService documentationReflectionService = new DocumentationReflectionServiceImpl(new ReflectionServiceImpl());

    /**
     * Main method of the documentation generator that generates markdown documentation files for mkdocs.
     * @param args Command line arguments.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        try {
            if (args.length != 2) {
                System.out.println("Documentation generator utility");
                System.out.println("Missing required parameters: <path to the project dir> <path to the swagger 3 yaml file>");
                return;
            }

            Path projectDir = Path.of(args[0]);
            Path swaggerFile = Path.of(args[1]);
            Path pythonClientDir = projectDir.resolve("../distribution/python/");

            System.out.println("Generating documentation for the python client: " + pythonClientDir);

            HandlebarsDocumentationUtilities.configure(projectDir);
            Path dqoHomePath = projectDir.resolve("../home").toAbsolutePath().normalize();
            DqoHomeContext dqoHomeContext = DqoHomeDirectFactory.openDqoHome(dqoHomePath);

            OpenAPI openAPI = getParsedSwaggerFile(swaggerFile);

            linkageStore = new LinkageStore<>();
            LinkageStore<String> targetLinkage = getPopulatedLinkageStore(openAPI);

            SpecToModelCheckMappingService specToModelCheckMappingService = SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(
                    new ReflectionServiceImpl(),
                    new SensorDefinitionFindServiceImpl(),
                    new RuleDefinitionFindServiceImpl()
            );
            SimilarCheckMatchingService similarCheckMatchingService = new SimilarCheckMatchingServiceImpl(
                    specToModelCheckMappingService,
                    () -> dqoHomeContext
            );
            DocsModelLinkageService docsModelLinkageService = new DocsModelLinkageServiceImpl(
                    projectDir,
                    similarCheckMatchingService
            );

            ComponentReflectionService componentReflectionService = new ComponentReflectionServiceImpl(projectDir);

            OpenAPIModel openAPIModel = OpenAPIModel.fromOpenAPI(openAPI, targetLinkage, linkageStore, docsModelLinkageService, componentReflectionService);

            generateDocumentationForModels(projectDir, openAPIModel);
            generateDocumentationForOperations(projectDir, openAPIModel);

            Path clientDocPath = projectDir
                    .resolve("..")
                    .resolve(baseClientDocsPath)
                    .toAbsolutePath().normalize();
            DocumentationFolder modifiedClientFolder = DocumentationFolderFactory.loadCurrentFiles(clientDocPath);
            modifiedClientFolder.setLinkName("REST API Python client");
            List<String> renderedIndexYaml = modifiedClientFolder.generateMkDocsNavigation(2);
            MkDocsIndexReplaceUtility.replaceContentLines(projectDir.resolve("../mkdocs.yml"),
                    renderedIndexYaml,
                    "########## INCLUDE PYTHON CLIENT REFERENCE - DO NOT MODIFY MANUALLY",
                    "########## END INCLUDE PYTHON CLIENT REFERENCE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static LinkageStore<String> getPopulatedLinkageStore(OpenAPI openAPI) {
        LinkageStore<String> linkageStore = new LinkageStore<>();

        Map<String, Set<String>> modelToOccurrence = new HashMap<>();
        openAPI.getPaths().values().stream()
                .flatMap(pathItem -> pathItem.readOperations().stream())
                .forEach(operation -> populateModelMappingByOperation(operation, modelToOccurrence));

        for (Map.Entry<String, Set<String>> modelOccurrenceEntry : modelToOccurrence.entrySet()) {
            String modelName = modelOccurrenceEntry.getKey();
            Set<String> modelOccurrences = modelOccurrenceEntry.getValue();

            Path baseModelDestination = Path.of("/")
                    .resolve(baseClientDocsPath)
                    .resolve(Path.of("models"));
            Path modelDestination;
            if (modelOccurrences.size() == 1) {
                // Model use is restricted to a single controller.
                String modelOccurrence = modelOccurrences.stream().findFirst().get();
                modelDestination = baseModelDestination
                        .resolve(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, modelOccurrence))
                        .resolve("#" + modelName);
            } else {
                // Model is used in several places.
                modelDestination = baseModelDestination.resolve("#" + modelName);
            }

            linkageStore.put(modelName, modelDestination);
        }

        return linkageStore;
    }

    protected static String getModelNameFrom$ref(String $ref) {
        String[] splitRef = $ref.split("/");
        return splitRef[splitRef.length - 1];
    }

    protected static void populateModelMappingByOperation(Operation operation, Map<String, Set<String>> modelMethodMapping) {
        List<Parameter> operationParameters = Objects.requireNonNullElseGet(operation.getParameters(), ArrayList::new);

        // Parameters (path and query)
        Set<String> variables$refs = operationParameters.stream()
                .map(Parameter::getSchema)
                .map(OpenApiUtils::getEffective$refFromSchema)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Request body
        RequestBody requestBody = operation.getRequestBody();
        if (requestBody != null) {
            String requestBody$ref = OpenApiUtils.getEffective$refFromContent(requestBody.getContent());
            if (requestBody$ref != null) {
                variables$refs.add(requestBody$ref);
            }
        }

        // Return
        ApiResponse returnResponse = operation.getResponses().values().stream().findFirst().get();
        String return$ref = OpenApiUtils.getEffective$refFromContent(returnResponse.getContent());
        if (return$ref != null) {
            variables$refs.add(return$ref);
        }

        Set<String> variablesTypes = variables$refs.stream()
                .map(GeneratePythonDocumentationPostProcessor::getModelNameFrom$ref)
                .collect(Collectors.toSet());

        for (String typeRef : variablesTypes) {
            if (!modelMethodMapping.containsKey(typeRef)) {
                modelMethodMapping.put(typeRef, new HashSet<>());
            }

            for (String controller : operation.getTags()) {
                modelMethodMapping.get(typeRef).add(controller);
            }
        }
    }

    protected static void generateDocumentationForModels(Path projectRoot,
                                                         OpenAPIModel openAPIModel) {
        Path modelsDocPath = projectRoot
                .resolve("..")
                .resolve(baseClientDocsPath)
                .resolve("models")
                .toAbsolutePath().normalize();
        DocumentationFolder currentModelsDocFiles = DocumentationFolderFactory.loadCurrentFiles(modelsDocPath);
        ModelsDocumentationGenerator modelsDocumentationGenerator = new ModelsDocumentationGeneratorImpl(new ModelsDocumentationModelFactoryImpl());

        DocumentationFolder renderedDocumentation = modelsDocumentationGenerator.renderModelsDocumentation(projectRoot, openAPIModel.getModels());
        renderedDocumentation.writeModifiedFiles(currentModelsDocFiles);
    }

    protected static void generateDocumentationForOperations(Path projectRoot,
                                                             OpenAPIModel openAPIModel) {
        Path operationsDocPath = projectRoot
                .resolve("..")
                .resolve(baseClientDocsPath)
                .resolve("operations")
                .toAbsolutePath().normalize();
        DocumentationFolder currentOperationsDocFiles = DocumentationFolderFactory.loadCurrentFiles(operationsDocPath);
        OperationsDocumentationGenerator operationsDocumentationGenerator = new OperationsDocumentationGeneratorImpl(new OperationsDocumentationModelFactoryImpl());

        DocumentationFolder renderedDocumentation = operationsDocumentationGenerator.renderOperationsDocumentation(projectRoot, openAPIModel);
        renderedDocumentation.writeModifiedFiles(currentOperationsDocFiles);
    }

    private static OpenAPI getParsedSwaggerFile(Path pathToSwaggerFile) {
        try {
            OpenAPIParser openAPIParser = new OpenAPIParser();
            String convertedSwaggerContent = Files.readString(pathToSwaggerFile);
            SwaggerParseResult swaggerParseResult = openAPIParser.readContents(convertedSwaggerContent, null, null);
            if (swaggerParseResult == null || swaggerParseResult.getOpenAPI() == null) {
                String message = "File " + pathToSwaggerFile + " is invalid and was not parsed.";
                System.err.println(message);
                throw new RuntimeException(message);
            }
            return swaggerParseResult.getOpenAPI();
        } catch (RuntimeException rex) {
            System.err.println("Cannot generate documentation for Python client, error: " + rex.getMessage());
            throw rex;
        }
        catch (Exception ex) {
            System.err.println("Cannot generate documentation for Python client, error: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}