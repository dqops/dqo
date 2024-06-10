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
package com.dqops.execution.sqltemplates.rendering;

import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.connectors.ProviderDialectSettingsObjectMother;
import com.dqops.connectors.ProviderType;
import com.dqops.execution.CheckExecutionContextObjectMother;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.checks.progress.CheckExecutionProgressListenerStub;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.finder.SensorDefinitionFindResult;
import com.dqops.execution.sensors.finder.SensorDefinitionFindResultObjectMother;
import com.dqops.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.BeanFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Object mother for JinjaTemplateRenderServiceImpl.
 */
public class JinjaTemplateRenderServiceObjectMother {
    /**
     * Returns the default implementation of the jinja template rendering service.
     * @return Jinja2 template rendering service.
     */
    public static JinjaTemplateRenderServiceImpl getDefault() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return (JinjaTemplateRenderServiceImpl) beanFactory.getBean(JinjaTemplateRenderService.class);
    }

    /**
     * Renders a template for a built-in sql template that could be identified by searching the details in the given render parameters.
     * @param runParameters Run sensor parameters with the sensor specification.
     * @return Rendered template.
     */
    public static String renderBuiltInTemplate(SensorExecutionRunParameters runParameters) {
        JinjaTemplateRenderParameters renderParameters = JinjaTemplateRenderParametersObjectMother.createForRunParameters(runParameters);
        String renderedSql = renderBuiltInTemplate(renderParameters);
        return renderedSql;
    }

    /**
     * Renders an error sampling template for a built-in sql sampling template that could be identified by searching the details in the given render parameters.
     * @param runParameters Run sensor parameters with the sensor specification.
     * @return Rendered template for the error sampling SQL query.
     */
    public static String renderErrorSamplingTemplate(SensorExecutionRunParameters runParameters) {
        JinjaTemplateRenderParameters renderParameters = JinjaTemplateRenderParametersObjectMother.createForRunParameters(runParameters);
        ErrorSamplingRenderParameters errorSampling = new ErrorSamplingRenderParameters();
        List<String> idColumns = runParameters.getTable().getColumns().values().stream()
                .filter(c -> c.isId())
                .map(c -> c.getColumnName())
                .collect(Collectors.toList());
        errorSampling.setIdColumns(idColumns);
        renderParameters.setErrorSampling(errorSampling);
        String renderedSql = renderErrorSamplingTemplate(renderParameters);
        return renderedSql;
    }


    /**
     * Makes an expected table name that would be rendered.
     * @param runParameters Run parameters with a table reference.
     * @return Table name, for example `gcp-proj`.`dataset`.`tablename`
     */
    public static String makeExpectedTableName(SensorExecutionRunParameters runParameters) {
        ProviderType providerType = runParameters.getConnection().getProviderType();
        ProviderDialectSettings dialectForProvider = ProviderDialectSettingsObjectMother.getDialectForProvider(providerType);

        switch (providerType) {
            case bigquery:
                return dialectForProvider.quoteIdentifier(runParameters.getConnection().getBigquery().getSourceProjectId()) + "." +
                        dialectForProvider.quoteIdentifier(runParameters.getTable().getPhysicalTableName().getSchemaName()) + "." +
                        dialectForProvider.quoteIdentifier(runParameters.getTable().getPhysicalTableName().getTableName());
            case snowflake:
                return dialectForProvider.quoteIdentifier(runParameters.getTable().getPhysicalTableName().getSchemaName()) + "." +
                        dialectForProvider.quoteIdentifier(runParameters.getTable().getPhysicalTableName().getTableName());
            default:
                Assertions.fail("Missing provider, add a case statement to support a new provider.");
        }

        return null;
    }

    /**
     * Renders a template for a built-in sql template that could be identified by searching the details in the given render parameters.
     * @param renderParameters Render parameters with the sensor specification.
     * @return Rendered template.
     */
    public static String renderBuiltInTemplate(JinjaTemplateRenderParameters renderParameters) {
        SensorDefinitionFindResult sensorDefinitions =
                SensorDefinitionFindResultObjectMother.findDqoHomeSensorDefinition(renderParameters.getParameters().getSensorDefinitionName(),
                        renderParameters.getConnection().getProviderType());

        JinjaTemplateRenderServiceImpl renderService = getDefault();
        ExecutionContext executionContext = CheckExecutionContextObjectMother.createWithInMemoryUserContext();

        CheckExecutionProgressListenerStub progressListener = new CheckExecutionProgressListenerStub();
        String renderedText = renderService.renderTemplate(executionContext, sensorDefinitions, renderParameters, progressListener);
        return renderedText;
    }

    /**
     * Renders an error sampling template for a built-in sql sampling template that could be identified by searching the details in the given render parameters.
     * @param renderParameters Render parameters with the sensor specification.
     * @return Rendered template for the error sampling query.
     */
    public static String renderErrorSamplingTemplate(JinjaTemplateRenderParameters renderParameters) {
        SensorDefinitionFindResult sensorDefinitions =
                SensorDefinitionFindResultObjectMother.findDqoHomeSensorDefinition(renderParameters.getParameters().getSensorDefinitionName(),
                        renderParameters.getConnection().getProviderType());

        JinjaTemplateRenderServiceImpl renderService = getDefault();
        ExecutionContext executionContext = CheckExecutionContextObjectMother.createWithInMemoryUserContext();

        CheckExecutionProgressListenerStub progressListener = new CheckExecutionProgressListenerStub();
        String renderedText = renderService.renderErrorSamplingTemplate(executionContext, sensorDefinitions, renderParameters, progressListener);
        return renderedText;
    }
}
