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
package ai.dqo.metadata.notifications;

import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Notification settings that specify how the notifications about new data quality issues are published.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class NotificationSettingsSpec extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<NotificationSettingsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Webhook URL where the notification messages are pushed using a HTTP POST request.")
    private String webhookUrl;

    @JsonPropertyDescription("Number of hours since the last alert. New data quality issues identified before that timestamp are not sent as notifications.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int hoursSinceLastAlert = 0;

    /**
     * Returns the URL where notifications are pushed using a HTTP POST request.
     * @return Webhook url.
     */
    public String getWebhookUrl() {
        return webhookUrl;
    }

    /**
     * Sets an url to a HTTP webhook where notifications are posted.
     * @param webhookUrl Webhook url.
     */
    public void setWebhookUrl(String webhookUrl) {
        setDirtyIf(!Objects.equals(this.webhookUrl, webhookUrl));
        this.webhookUrl = webhookUrl;
    }

    /**
     * Returns the number of hours since the last alert that causes sending a new alert.
     * @return Number of hours since the last alert.
     */
    public int getHoursSinceLastAlert() {
        return hoursSinceLastAlert;
    }

    /**
     * Sets the number of hours since the last alert that causes a notification.
     * @param hoursSinceLastAlert Number of hours.
     */
    public void setHoursSinceLastAlert(int hoursSinceLastAlert) {
        setDirtyIf(this.hoursSinceLastAlert != hoursSinceLastAlert);
        this.hoursSinceLastAlert = hoursSinceLastAlert;
    }

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public NotificationSettingsSpec deepClone() {
        NotificationSettingsSpec cloned = (NotificationSettingsSpec) super.deepClone();
        return cloned;
    }

    /**
     * Creates a cloned and expanded version of the objects. All parameters are changed to the values expanded from variables like ${ENV_VAR}.
     * @param secretValueProvider Secret value provider.
     * @return Cloned and expanded copy of the object.
     */
    public NotificationSettingsSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        NotificationSettingsSpec cloned = this.deepClone();
        cloned.webhookUrl = secretValueProvider.expandValue(cloned.webhookUrl);
        return cloned;
    }
}
