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
package ai.dqo.execution.sqltemplates;

import ai.dqo.connectors.ProviderDialectSettings;
import ai.dqo.connectors.ProviderDialectSettingsObjectMother;
import ai.dqo.connectors.ProviderType;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.CheckExecutionContextObjectMother;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListenerStub;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindResult;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindResultObjectMother;
import ai.dqo.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.BeanFactory;

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
//        Assertions.assertNull(runParameters.getConnection().getDefaultDataStreamMapping());
        JinjaTemplateRenderParameters renderParameters = JinjaTemplateRenderParametersObjectMother.createForRunParameters(runParameters);
        return renderBuiltInTemplate(renderParameters);
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
                        dialectForProvider.quoteIdentifier(runParameters.getTable().getTarget().getSchemaName()) + "." +
                        dialectForProvider.quoteIdentifier(runParameters.getTable().getTarget().getTableName());
            case snowflake:
                return dialectForProvider.quoteIdentifier(runParameters.getTable().getTarget().getSchemaName()) + "." +
                        dialectForProvider.quoteIdentifier(runParameters.getTable().getTarget().getTableName());
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
}
