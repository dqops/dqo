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
package com.dqops.utils.docs.python;

import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeDirectFactory;
import com.dqops.utils.docs.HandlebarsDocumentationUtilities;
import com.dqops.utils.docs.LinkageStore;
import com.dqops.utils.docs.files.DocumentationFolder;
import com.dqops.utils.docs.files.DocumentationFolderFactory;
import com.dqops.utils.docs.python.apimodel.OpenAPIModel;
import com.dqops.utils.docs.python.controllers.ControllersDocumentationGenerator;
import com.dqops.utils.docs.python.controllers.ControllersDocumentationGeneratorImpl;
import com.dqops.utils.docs.python.controllers.ControllersDocumentationModelFactoryImpl;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.google.api.client.http.HttpMediaType;
import com.google.common.base.CaseFormat;
import com.google.rpc.Help;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.apache.commons.text.CaseUtils;
import org.json.HTTP;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class called from the maven build. Generates documentation for the Python client.
 */
public class GeneratePythonDocumentationPostProcessor {
    public static final Path basePythonDocsPath = Path.of("/docs", "python");

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

            OpenAPI openAPI = getParsedSwaggerFile(swaggerFile);
            OpenAPIModel openAPIModel = OpenAPIModel.fromOpenAPI(openAPI);

//            ComponentReflectionService componentReflectionService = new ComponentReflectionServiceImpl(new ReflectionServiceImpl());
//            componentReflectionService.fillComponentsDocsRefs(openAPIModel.getModels());

            HandlebarsDocumentationUtilities.configure(projectDir);

            Path dqoHomePath = projectDir.resolve("../home").toAbsolutePath().normalize();
            DqoHomeContext dqoHomeContext = DqoHomeDirectFactory.openDqoHome(dqoHomePath);
            LinkageStore<String> linkageStore = getPopulatedLinkageStore(openAPI);


            generateDocumentationForControllers(projectDir, linkageStore, dqoHomeContext, openAPI);

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
            if (modelOccurrences.size() == 1) {
                // Model use is restricted to a single controller.
                String modelOccurrence = modelOccurrences.stream().findFirst().get();

                linkageStore.put(modelName, basePythonDocsPath.resolve(
                        Path.of("controllers", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, modelOccurrence))
                ));
            } else {
                // Model is used in several places.
                linkageStore.put(modelName, basePythonDocsPath.resolve(
                        // TODO: Verify whether the file isn't too large, split it if needed.
                        Path.of("models")
                ));
            }
        }

        return linkageStore;
    }

    protected static String getModelNameFrom$ref(String $ref) {
        String[] splitRef = $ref.split("/");
        return splitRef[splitRef.length - 1];
    }

    protected static void populateModelMappingByOperation(Operation operation, Map<String, Set<String>> modelMethodMapping) {
        List<Parameter> operationParameters = operation.getParameters();
        if (operationParameters == null) {
            return;
        }

        Set<String> variables$refs = operationParameters.stream()
                .map(Parameter::get$ref)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        ApiResponse returnResponse = operation.getResponses().values().stream().findFirst().get();
        Schema<?> returnMediaSchema = returnResponse.getContent().get("application/json").getSchema();
        String return$ref = returnMediaSchema.get$ref();

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

    protected static void generateDocumentationForControllers(Path projectRoot,
                                                              LinkageStore<String> linkageStore,
                                                              DqoHomeContext dqoHomeContext,
                                                              OpenAPI openAPI) {
        Path controllersDocPath = projectRoot.resolve("../docs/client/controllers").toAbsolutePath().normalize();
        DocumentationFolder currentControllersDocFiles = DocumentationFolderFactory.loadCurrentFiles(controllersDocPath);
        ControllersDocumentationGenerator controllersDocumentationGenerator = new ControllersDocumentationGeneratorImpl(new ControllersDocumentationModelFactoryImpl(linkageStore));

//        List<ControllersDocumentationSchemaNode> controllersDocumentationSchema = getYamlDocumentationSchema();
        DocumentationFolder renderedDocumentation = controllersDocumentationGenerator.renderControllersDocumentation(projectRoot, linkageStore, openAPI);
//        renderedDocumentation.writeModifiedFiles(currentYamlDocFiles);

//        List<String> renderedIndexYaml = renderedDocumentation.generateMkDocsNavigation(4);
//        MkDocsIndexReplaceUtility.replaceContentLines(projectRoot.resolve("../mkdocs.yml"),
//                renderedIndexYaml,
//                "########## INCLUDE CONTROLLERS REFERENCE - DO NOT MODIFY MANUALLY",
//                "########## END INCLUDE CONTROLLERS REFERENCE");
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
