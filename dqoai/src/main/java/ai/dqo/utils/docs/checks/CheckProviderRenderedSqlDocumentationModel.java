package ai.dqo.utils.docs.checks;

import ai.dqo.connectors.ProviderType;
import lombok.Data;

/**
 * Documentation model that shows how a particular provider would render the check (sensor) SQL, using the parameters given.
 */
@Data
public class CheckProviderRenderedSqlDocumentationModel {
    /**
     * Provider type.
     */
    private ProviderType providerType;

    /**
     * Jinja2 template content.
     */
    private String jinjaTemplate;

    /**
     * The SQL that would be executed on the target database.
     */
    private String renderedTemplate;
}
