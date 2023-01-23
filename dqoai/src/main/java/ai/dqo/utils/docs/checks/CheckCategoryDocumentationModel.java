package ai.dqo.utils.docs.checks;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Container object with a list of checks within the same category.
 */
@Data
public class CheckCategoryDocumentationModel {
    /**
     * Check type (adhoc, partitioned, checkpoint).
     */
    private String checkType;

    /**
     * Time scale name for checkpoints and partitioned checks (daily, monthly, etc).
     */
    private String timeScale;

    /**
     * Category name.
     */
    private String categoryName;

    /**
     * Category documentation that is provided on the category container node in Java and visible in YAML help.
     */
    private String categoryHelp;

    /**
     * List of table level checks within the category.
     */
    private List<CheckDocumentationModel> tableChecks = new ArrayList<>();

    /**
     * List of column level checks within the category.
     */
    private List<CheckDocumentationModel> columnChecks = new ArrayList<>();
}
