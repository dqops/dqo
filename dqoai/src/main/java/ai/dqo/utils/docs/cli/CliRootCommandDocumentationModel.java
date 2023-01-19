package ai.dqo.utils.docs.cli;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Documentation model about one single CLI command.
 */
@Data
public class CliRootCommandDocumentationModel {
    /**
     * The name of the first command.
     */
    private String rootCommandName;

    /**
     * List of commands.
     */
    private List<CliCommandDocumentationModel> commands = new ArrayList<>();
}
