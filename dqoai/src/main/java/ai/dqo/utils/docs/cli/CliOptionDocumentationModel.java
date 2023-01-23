package ai.dqo.utils.docs.cli;

import lombok.Data;

/**
 * Documentation model for a single parameter (option) of a CLI command.
 */
@Data
public class CliOptionDocumentationModel {
    /**
     * Array of all option names (shorter, longer).
     */
    private String[] names;

    /**
     * Option description.
     */
    private String[] description;

    /**
     * The option is required.
     */
    private boolean required;

    /**
     * Array of accepted values.
     */
    private String[] acceptedValues;
}
