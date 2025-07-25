/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.client;

import com.dqops.utils.docs.*;
import com.dqops.utils.docs.client.apimodel.OpenAPIModel;
import com.dqops.utils.docs.client.models.ModelsDocumentationGenerator;
import com.dqops.utils.docs.client.models.ModelsDocumentationGeneratorImpl;
import com.dqops.utils.docs.client.models.ModelsDocumentationModelFactoryImpl;
import com.dqops.utils.docs.client.models.ModelsSuperiorObjectDocumentationModel;
import com.dqops.utils.docs.client.operations.OperationsDocumentationGenerator;
import com.dqops.utils.docs.client.operations.OperationsDocumentationGeneratorImpl;
import com.dqops.utils.docs.client.operations.OperationsDocumentationModelFactoryImpl;
import com.dqops.utils.docs.client.operations.examples.UsageExampleModelFactory;
import com.dqops.utils.docs.client.operations.examples.UsageExampleModelFactoryImpl;
import com.dqops.utils.docs.client.operations.examples.python.PythonExampleDocumentationGenerator;
import com.dqops.utils.docs.client.operations.examples.python.PythonExampleDocumentationGeneratorImpl;
import com.dqops.utils.docs.client.operations.examples.python.PythonExampleDocumentationModelFactory;
import com.dqops.utils.docs.client.operations.examples.python.PythonExampleDocumentationModelFactoryImpl;
import com.dqops.utils.docs.client.operations.examples.serialization.PythonSerializerImpl;
import com.dqops.utils.docs.files.*;
import com.dqops.utils.docs.generators.ParsedSampleObjectFactoryImpl;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import com.google.common.base.CaseFormat;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class called from the maven build. Generates documentation for the Python client.
 */
public class GeneratePythonDocumentationPostProcessor {
    public static final Path baseClientDocsPath = Path.of("docs", "client");
    public static final Comparator<String> cherryPickComparator = (s1, s2) -> {
        if (s1.equals(s2)) {
            return 0;
        }
        if (s1.equals(ModelsDocumentationModelFactoryImpl.SHARED_MODELS_IDENTIFIER)) {
            return -1;
        }
        if (s2.equals(ModelsDocumentationModelFactoryImpl.SHARED_MODELS_IDENTIFIER)) {
            return 1;
        }
        return 0;
    };

    public static LinkageStore<String> linkageStore;

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
            OpenAPI openAPI = getParsedSwaggerFile(swaggerFile);

            linkageStore = new LinkageStore<>();
            LinkageStore<String> targetLinkage = getPopulatedLinkageStore(openAPI);

            DocsModelLinkageService docsModelLinkageService = new DocsModelLinkageServiceImpl(projectDir);
            ComponentReflectionService componentReflectionService = new ComponentReflectionServiceImpl(projectDir);

            OpenAPIModel openAPIModel = OpenAPIModel.fromOpenAPI(openAPI, targetLinkage, linkageStore, docsModelLinkageService, componentReflectionService);

            MainPageClientDocumentationModel mainPageClientDocumentationModel = new MainPageClientDocumentationModel();
            mainPageClientDocumentationModel.setIndexDocumentationModel(new MainPageClientIndexDocumentationModel());
            mainPageClientDocumentationModel.setGuideDocumentationModel(new MainPageClientGuideDocumentationModel());
            mainPageClientDocumentationModel.setConnectingDocumentationModel(new MainPageClientConnectingDocumentationModel());

            generateDocumentationForModels(projectDir, openAPIModel, mainPageClientDocumentationModel);
            generateDocumentationForOperations(projectDir, openAPIModel, mainPageClientDocumentationModel);
            generateMainPageDocumentation(projectDir, mainPageClientDocumentationModel);

            Path clientDocPath = projectDir
                    .resolve("..")
                    .resolve(baseClientDocsPath)
                    .toAbsolutePath().normalize();
            DocumentationFolder modifiedClientFolder = DocumentationFolderFactory.loadCurrentFiles(clientDocPath);
            modifiedClientFolder.setLinkName("REST API Python client");

            modifiedClientFolder.sortByFileNameRecursive(cherryPickComparator.thenComparing(Comparator.naturalOrder()));

            List<String> renderedIndexYaml = modifiedClientFolder.generateMkDocsNavigation(2);
            FileContentIndexReplaceUtility.replaceContentLines(projectDir.resolve("../mkdocs.yml"),
                    renderedIndexYaml,
                    "########## INCLUDE PYTHON CLIENT REFERENCE - DO NOT MODIFY MANUALLY",
                    "########## END INCLUDE PYTHON CLIENT REFERENCE");

            executePostCorrections(projectDir);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DqoRuntimeException("Failed to generate documentation of the Python client: " + e.getMessage(), e);
        }
    }

    protected static LinkageStore<String> getPopulatedLinkageStore(OpenAPI openAPI) {
        LinkageStore<String> linkageStore = new LinkageStore<>();

        Map<String, Set<String>> modelToOccurrence = new LinkedHashMap<>();
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
                        .resolve(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, modelOccurrence)
                                + ".md" + "#" + modelName.toLowerCase());
            } else {
                // Model is used in several places.
                modelDestination = baseModelDestination
                        .resolve(ModelsDocumentationModelFactoryImpl.SHARED_MODELS_IDENTIFIER
                                + ".md" + "#" + modelName.toLowerCase());
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
        Set<String> variables$refs = new LinkedHashSet<>(operationParameters.stream()
                .map(Parameter::getSchema)
                .map(OpenApiUtils::getEffective$refFromSchema)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));

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

        Set<String> variablesTypes = new LinkedHashSet<>(variables$refs.stream()
                .map(GeneratePythonDocumentationPostProcessor::getModelNameFrom$ref)
                .collect(Collectors.toList()));

        for (String typeRef : variablesTypes) {
            if (!modelMethodMapping.containsKey(typeRef)) {
                modelMethodMapping.put(typeRef, new LinkedHashSet<>());
            }

            for (String controller : operation.getTags()) {
                modelMethodMapping.get(typeRef).add(controller);
            }
        }
    }

    protected static void generateDocumentationForModels(Path projectRoot,
                                                         OpenAPIModel openAPIModel,
                                                         MainPageClientDocumentationModel mainPageModel) {
        Path modelsDocPath = projectRoot
                .resolve("..")
                .resolve(baseClientDocsPath)
                .resolve("models")
                .toAbsolutePath().normalize();
        DocumentationFolder currentModelsDocFiles = DocumentationFolderFactory.loadCurrentFiles(modelsDocPath);
        ModelsDocumentationGenerator modelsDocumentationGenerator = new ModelsDocumentationGeneratorImpl(new ModelsDocumentationModelFactoryImpl());

        DocumentationFolder renderedDocumentation = modelsDocumentationGenerator.renderModelsDocumentation(
                projectRoot, openAPIModel.getModels(), mainPageModel);
        renderedDocumentation.writeModifiedFiles(currentModelsDocFiles);
    }

    protected static void generateDocumentationForOperations(Path projectRoot,
                                                             OpenAPIModel openAPIModel,
                                                             MainPageClientDocumentationModel mainPageModel) {
        Path operationsDocPath = projectRoot
                .resolve("..")
                .resolve(baseClientDocsPath)
                .resolve("operations")
                .toAbsolutePath().normalize();
        DocumentationFolder currentOperationsDocFiles = DocumentationFolderFactory.loadCurrentFiles(operationsDocPath);

        UsageExampleModelFactory usageExampleModelFactory = getUsageExampleModelFactory();
        OperationsDocumentationGenerator operationsDocumentationGenerator = new OperationsDocumentationGeneratorImpl(
                new OperationsDocumentationModelFactoryImpl(),
                usageExampleModelFactory);

        DocumentationFolder renderedDocumentation = operationsDocumentationGenerator.renderOperationsDocumentation(
                projectRoot, openAPIModel, mainPageModel);
        renderedDocumentation.writeModifiedFiles(currentOperationsDocFiles);
    }

    @NotNull
    private static UsageExampleModelFactory getUsageExampleModelFactory() {
        DocumentationReflectionService documentationReflectionService = new DocumentationReflectionServiceImpl(new ReflectionServiceImpl());
        PythonExampleDocumentationModelFactory pythonExampleDocumentationModelFactory = new PythonExampleDocumentationModelFactoryImpl(
                documentationReflectionService,
                new PythonSerializerImpl(new ParsedSampleObjectFactoryImpl(documentationReflectionService))
        );
        PythonExampleDocumentationGenerator pythonExampleDocumentationGenerator = new PythonExampleDocumentationGeneratorImpl(pythonExampleDocumentationModelFactory);
        return new UsageExampleModelFactoryImpl(pythonExampleDocumentationGenerator);
    }

    protected static void generateMainPageDocumentation(Path projectRoot,
                                                        MainPageClientDocumentationModel mainPageModel) {
        Path clientPath = projectRoot
                .resolve("..")
                .resolve(baseClientDocsPath)
                .toAbsolutePath().normalize();
        DocumentationFolder currentClientDocFiles = DocumentationFolderFactory.loadCurrentFiles(clientPath);

        mainPageModel.getIndexDocumentationModel().setModels(
                mainPageModel.getIndexDocumentationModel().getModels().stream()
                        .sorted(Comparator.comparing(
                                ModelsSuperiorObjectDocumentationModel::getLocationFilePath,
                                cherryPickComparator.thenComparing(Comparator.naturalOrder()))
                        ).collect(Collectors.toList())
        );

        DocumentationResourceFileLoader documentationResourceFileLoader = new DocumentationResourceFileLoaderImpl(projectRoot);
        MainPageClientDocumentationGenerator mainPageClientDocumentationGenerator = new MainPageClientDocumentationGeneratorImpl(documentationResourceFileLoader);
        DocumentationMarkdownFile mainPageFile = mainPageClientDocumentationGenerator.renderMainPageDocumentation(mainPageModel);

        DocumentationFolder renderedDocFiles = DocumentationFolderFactory.loadCurrentFiles(clientPath);
        DocumentationMarkdownFile nestedFile = renderedDocFiles.addNestedFile(mainPageFile);
        nestedFile.setRenderContext(mainPageModel);

        renderedDocFiles.writeModifiedFiles(currentClientDocFiles);
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

    private static void executePostCorrections(Path projectRoot) {
        Path docPath = projectRoot.resolve("../docs").toAbsolutePath().normalize();
        DocumentationFolder docsRootFolder = DocumentationFolderFactory.loadCurrentFiles(docPath);
        DocumentationFolder docsRootFolderCorrected = DocumentationFolderFactory.loadCurrentFiles(docPath);

        DocumentationFolderPostCorrectorService documentationFolderPostCorrectorService =
                new DocumentationFolderPostCorrectorServiceImpl(projectRoot.toAbsolutePath().getParent());
        documentationFolderPostCorrectorService.postProcessCorrect(docsRootFolderCorrected);
        docsRootFolderCorrected.writeModifiedFiles(docsRootFolder);

        DocumentationFolderPostValidatorService documentationFolderPostValidatorService =
                new DocumentationFolderPostValidatorServiceImpl(projectRoot.toAbsolutePath().getParent());
        documentationFolderPostValidatorService.postProcessValidate(docsRootFolderCorrected);
    }
}
