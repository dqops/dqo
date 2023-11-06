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
import com.dqops.utils.reflection.ObjectDataType;
import com.github.jknack.handlebars.*;
import com.github.jknack.handlebars.helper.ConditionalHelpers;
import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.jknack.handlebars.io.FileTemplateLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

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
        handlebars.registerHelper("render-type", renderTypeHelper);
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
                    templateToReturn = "[%s](" + typeModel.getClassUsedOnTheFieldPath().toLowerCase() + ")";
                } else {
                    templateToReturn = "%s";
                }
                return String.format(templateToReturn, displayText);
            }

            if (!isObject(typeModel) && !isLinkableEnum(typeModel)) {
                // Simple types
                String dataTypeString = typeModel.getDataType().toString();
                return dataTypeString.substring(0, dataTypeString.length() - "_type".length());
            }

            ObjectDataType objectDataType = Objects.requireNonNullElse(typeModel.getObjectDataType(), ObjectDataType.object_type);
            switch (objectDataType) {
                case list_type:
                    return String.format("List[%s]", apply(typeModel.getGenericKeyType(), options));
                case map_type:
                    return String.format("Dict[%s, %s]", apply(typeModel.getGenericKeyType(), options), apply(typeModel.getGenericValueType(), options));
                case object_type:
                default:
                    if (typeModel.getClassUsedOnTheFieldPath() != null) {
                        return "[" + typeModel.getClassNameUsedOnTheField() + "]" +
                                "(" + typeModel.getClassUsedOnTheFieldPath().toLowerCase() + ")";
                    }
                    else {
                        return typeModel.getClassNameUsedOnTheField();
                    }

            }
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
}
