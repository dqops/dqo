package ai.dqo.rest.models.checks.mappping;

import ai.dqo.checks.AbstractCheckCategoriesSpec;
import ai.dqo.rest.models.checks.UIAllChecksModel;
import org.springframework.stereotype.Component;

/**
 * Service that converts UI models to the data quality check specifications or creates the UI model from the data quality check specifications,
 * enabling transformation between the storage model (YAML compliant) with a UI friendly UI model.
 */
@Component
public class CheckMappingServiceImpl {
    /**
     * Creates a checks UI model for the whole container of table level or column level data quality checks, divided into DAMA dimensions.
     * @param checkCategoriesSpec Table level data quality checks container or a column level data quality checks container.
     * @return Checks data quality container.
     */
    public UIAllChecksModel createUiModel(AbstractCheckCategoriesSpec checkCategoriesSpec) {
        UIAllChecksModel uiAllChecksModel = new UIAllChecksModel();
        return uiAllChecksModel;
    }


}
