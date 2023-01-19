package ai.dqo.utils.docs.cli;

import lombok.Data;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Documentation model about one single CLI command.
 */
@Data
public class CliCommandDocumentationModel {
    /**
     * Full command name. For example: "connection list".
     */
    private String qualifiedName;

    /**
     * Command header lines.
     */
    private String[] header;

    /**
     * Command description lines.
     */
    private String[] description;

    /**
     * Command synopsis (command structure).
     */
    private String synopsis;

    /**
     * Custom usage structure.
     */
    private String[] customSynopsis;

    /**
     * Picocli command line object, for reference.
     */
    private CommandLine commandLine;

    /**
     * Command specification.
     */
    private CommandLine.Model.CommandSpec commandSpec;

    /**
     * Command help.
     */
    private CommandLine.Help help;

    /**
     * Usage message.
     */
    private CommandLine.Model.UsageMessageSpec usageMessageSpec;

    /**
     * List of command options.
     */
    private List<CliOptionDocumentationModel> options = new ArrayList<>();
}
