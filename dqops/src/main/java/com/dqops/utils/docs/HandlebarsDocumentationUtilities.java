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
package com.dqops.utils.docs;

import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.utils.docs.generators.TypeModel;
import com.dqops.utils.reflection.ObjectDataType;
import com.github.jknack.handlebars.*;
import com.github.jknack.handlebars.helper.ConditionalHelpers;
import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

/**
 * Handlebars template helper used to render documentation markdown files using Handlebars.
 */
public class HandlebarsDocumentationUtilities {
    private static Handlebars handlebars;

    /**
     * Configures the handlebars parser.
     * @param projectRootPath Path to the project root.
     */
    public static void configure(Path projectRootPath) {
        File templateDir = projectRootPath.resolve("src/main/java/com/dqops/utils/docs").toFile();
        handlebars = new Handlebars(new FileTemplateLoader(templateDir));
        handlebars.registerHelpers(StringHelpers.class);
        handlebars.registerHelpers(ConditionalHelpers.class);
        handlebars.registerHelpers(IntegerMathHelpers.class);
        handlebars.registerHelper("render-type", renderTypeHelper);
        handlebars.registerHelper("checkmark", checkmarkHelper);
        handlebars.registerHelper("single-line", singleLineHelper);
        handlebars.registerHelper("indent", indentHelper);
        handlebars.registerHelper("contains", containsHelper);
        handlebars.registerHelper("var", variableHelper);
    }

    /**
     * Compiles a template inside the "docs" folder.
     * @param templateFile Template file name. The template file name should have a .hbs extension and should be relative to the folder where this class is located.
     * @return Compiled Handlebars template.
     */
    public static Template compileTemplate(String templateFile) {
        try {
            return handlebars.compile(templateFile);
        }
        catch (Exception ex) {
            throw new RuntimeException("Template " + templateFile + " cannot be compiled, error: " + ex.getMessage(), ex);
        }
    }

    /**
     * Renders a template given the model object.
     * @param template Template to render.
     * @param model Model object.
     * @return Rendered content as a string.
     */
    public static String renderTemplate(Template template, Object model) {
        try {
            Context handlebarsContext = Context.newBuilder(model).build();
            return template.apply(handlebarsContext);
        }
        catch (Exception ex) {
            throw new RuntimeException("Cannot render template " + template.filename() + ", error: " + ex.getMessage(), ex);
        }
    }

    private enum IntegerMathHelpers implements Helper<Integer> {
        add {
            @Override
            public Integer apply(Integer n, Options options) throws IOException {
                if (n == null) {
                    return null;
                }

                if (Arrays.stream(options.params)
                        .anyMatch(i -> !(i instanceof Integer))) {
                    throw IntegerMathHelpers.nonIntegerParametersException(options.params);
                }

                Integer sumUp = Arrays.stream(options.params)
                        .map(i->(Integer)i)
                        .reduce(0, Integer::sum);

                return n + sumUp;
            }
        },

        subtract {
            @Override
            public Integer apply(Integer n, Options options) throws IOException {
                if (n == null) {
                    return null;
                }

                if (Arrays.stream(options.params)
                        .anyMatch(i -> !(i instanceof Integer))) {
                    throw IntegerMathHelpers.nonIntegerParametersException(options.params);
                }

                Integer sumUp = Arrays.stream(options.params)
                        .map(i->(Integer)i)
                        .reduce(0, Integer::sum);

                return n - sumUp;
            }
        },

        multiply {
            @Override
            public Integer apply(Integer n, Options options) throws IOException {
                if (n == null) {
                    return null;
                }

                if (Arrays.stream(options.params)
                        .anyMatch(i -> !(i instanceof Integer))) {
                    throw IntegerMathHelpers.nonIntegerParametersException(options.params);
                }

                Integer multiplication = Arrays.stream(options.params)
                        .map(i->(Integer)i)
                        .reduce(1, (a, b) -> a * b);

                return n * multiplication;
            }
        },

        divide {
            @Override
            public Integer apply(Integer n, Options options) throws IOException {
                if (n == null) {
                    return null;
                }

                if (Arrays.stream(options.params)
                        .anyMatch(i -> !(i instanceof Integer))) {
                    throw IntegerMathHelpers.nonIntegerParametersException(options.params);
                }

                Integer multiplication = Arrays.stream(options.params)
                        .map(i->(Integer)i)
                        .reduce(1, (a, b) -> a * b);

                return n / multiplication;
            }
        },
        ;

        private static IOException nonIntegerParametersException(Object[] params) {
            return new IOException(String.format("Not every parameter is an integer: %s",
                    Arrays.toString(params)));
        }
    }

    private static final Helper<TypeModel> renderTypeHelper = new Helper<>() {
        @Override
        public CharSequence apply(TypeModel typeModel, Options options) {
            if (typeModel == null) {
                return "";
            }

            String displayText = options.param(0, null);
            if (displayText != null) {
                // Only simple objects get complete linkage
                String templateToReturn = null;
                if ((isSimpleObject(typeModel) || isLinkableEnum(typeModel)) && typeModel.getClassUsedOnTheFieldPath() != null) {
                    templateToReturn = "[`%s`](" + changeAnchorToLowerCaseAndFixWindowsUrls(typeModel.getClassUsedOnTheFieldPath()) + ")";
                } else {
                    templateToReturn = "`%s`";
                }
                return String.format(templateToReturn, displayText);
            }

            if (!isObject(typeModel) && !isLinkableEnum(typeModel)) {
                // Simple types
                String dataTypeString = typeModel.getDataType().toString();
                switch (typeModel.getDataType()) {
                    case string_list_type:
                    case integer_list_type:
                        return String.format("List[%s]", dataTypeString.substring(0, dataTypeString.length() - "_list_type".length()));
                    default:
                        return dataTypeString.substring(0, dataTypeString.length() - "_type".length());
                }
            }

            ObjectDataType objectDataType = Objects.requireNonNullElse(typeModel.getObjectDataType(), ObjectDataType.object_type);
            switch (objectDataType) {
                case list_type:
                    return String.format("List[%s]",
                            changeAnchorToLowerCaseAndFixWindowsUrls(apply(typeModel.getGenericKeyType(), options).toString()));
                case map_type:
                    return String.format("Dict[%s, %s]",
                            changeAnchorToLowerCaseAndFixWindowsUrls(apply(typeModel.getGenericKeyType(), options).toString()),
                            changeAnchorToLowerCaseAndFixWindowsUrls(apply(typeModel.getGenericValueType(), options).toString()));
                case object_type:
                default:
                    if (typeModel.getClassUsedOnTheFieldPath() != null) {
                        return "[" + typeModel.getClassNameUsedOnTheField() + "]" +
                                "(" + changeAnchorToLowerCaseAndFixWindowsUrls(typeModel.getClassUsedOnTheFieldPath()) + ")";
                    }
                    else {
                        return "`" + typeModel.getClassNameUsedOnTheField() + "`";
                    }

            }
        }

        /**
         * Finds an anchor after the '#' sign in the url and changes it to lower case.
         * @param url Url to fix.
         * @return Fixed url.
         */
        private String changeAnchorToLowerCaseAndFixWindowsUrls(String url) {
            if (url.indexOf('\\') >= 0) {
                url = url.replace('\\', '/');
            }

            int indexOfHash = url.indexOf('#');
            if (indexOfHash < 0) {
                return url;
            }

            return url.substring(0, indexOfHash) + url.substring(indexOfHash).toLowerCase();
        }

        private boolean isLinkableEnum(TypeModel typeModel) {
            return typeModel.getDataType() == ParameterDataType.enum_type && typeModel.getClassUsedOnTheFieldPath() != null;
        }

        private boolean isObject(TypeModel typeModel) {
            return typeModel.getDataType() == ParameterDataType.object_type;
        }
        private boolean isSimpleObject(TypeModel typeModel) {
            return isObject(typeModel) && typeModel.getObjectDataType() == ObjectDataType.object_type;
        }
    };

    private static final Helper<Boolean> checkmarkHelper = (bool, _ignore) -> {
        if (bool == null || !bool) {
            return " ";
        }

        return ":material-check-bold:";
    };

    private static final Helper<String> singleLineHelper = (s, _ignore) -> {
        if (s == null) {
            return null;
        }

        return s.replaceAll("\\s+", " ");
    };

    private static final Helper<String> indentHelper = (s, o) -> {
        if (s == null
            || o.params.length == 0
            || !(NumberUtils.isNumber(o.params[0].toString()))) {
            return s;
        }

        int indentAmount = NumberUtils.toInt(o.params[0].toString());
        String currentIndent;
        String targetIndent;

        if (indentAmount < 0) {
            currentIndent = "\n" + StringUtils.repeat("\t", -1 * indentAmount);
            targetIndent = "\n";
        } else {
            currentIndent = "\n";
            targetIndent = "\n" + StringUtils.repeat("\t", indentAmount);
        }

        return s.replace(currentIndent, targetIndent);
    };

    private static final Helper<String> containsHelper = (s, o) -> {
        if (s == null
            || o.params.length == 0
            || !(o.params[0] instanceof CharSequence)
        ) {
            return false;
        }

        return s.contains((CharSequence)o.params[0]);
    };

    private static final Helper<String> variableHelper = new Helper<>() {
        final Map<Long, Map<String, String>> state = new LinkedHashMap<>();

        @Override
        public CharSequence apply(String variableName, Options options) {
            if (variableName == null) {
                return "";
            }

            Long scope = getScope(options);
            String value = options.param(0, null);
            if (value == null) {
                return Objects.requireNonNullElse(getValue(scope, variableName), "");
            } else {
                setValue(scope, variableName, value);
                return "";
            }
        }

        private Long getScope(Options options) {
            return Long.parseLong(Integer.toString(options.fn.filename().hashCode()));
        }

        private String getValue(Long scope, String variableName) {
            Map<String, String> scopedState = state.computeIfAbsent(scope, _i -> new LinkedHashMap<>());
            return scopedState.getOrDefault(variableName, null);
        }

        private void setValue(Long scope, String variableName, String value) {
            if (!state.containsKey(scope)) {
                state.put(scope, new LinkedHashMap<>());
            }

            Map<String, String> scopedState = state.get(scope);
            scopedState.put(variableName, value);
        }
    };
}
