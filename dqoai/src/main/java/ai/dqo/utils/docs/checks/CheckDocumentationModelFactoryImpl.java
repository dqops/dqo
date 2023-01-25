package ai.dqo.utils.docs.checks;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.services.check.mapping.models.UIAllChecksModel;
import ai.dqo.services.check.mapping.models.UICheckModel;
import ai.dqo.services.check.mapping.models.UIQualityCategoryModel;
import ai.dqo.services.check.mapping.SpecToUiCheckMappingService;
import ai.dqo.utils.serialization.YamlSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Check documentation model factory. Creates documentation objects for each check.
 */
@Component
public class CheckDocumentationModelFactoryImpl {
    private DqoHomeContext dqoHomeContext;
    private SpecToUiCheckMappingService specToUiCheckMappingService;
    private YamlSerializer yamlSerializer;

    /**
     * Creates a check documentation service.
     * @param dqoHomeContext DQO Home.
     * @param specToUiCheckMappingService Specification to UI check mapping service. Retrieves information about built-in checks.
     * @param yamlSerializer Yaml serializer.
     */
    @Autowired
    public CheckDocumentationModelFactoryImpl(DqoHomeContext dqoHomeContext,
                                              SpecToUiCheckMappingService specToUiCheckMappingService,
                                              YamlSerializer yamlSerializer) {
        this.dqoHomeContext = dqoHomeContext;
        this.specToUiCheckMappingService = specToUiCheckMappingService;
        this.yamlSerializer = yamlSerializer;
    }

    /**
     * Builds documentation models for all all check categories.
     * @param rootChecksContainerSpec Root check container object that will be documented.
     * @return List of categories and their documentation, describing all checks in that category.
     */
    public List<CheckCategoryDocumentationModel> buildDocumentation(AbstractRootChecksContainerSpec rootChecksContainerSpec) {
        List<CheckCategoryDocumentationModel> categoryDocs = new ArrayList<>();
        UIAllChecksModel checkUiModel = this.specToUiCheckMappingService.createUiModel(rootChecksContainerSpec, 
                new CheckSearchFilters(), null);

        for (UIQualityCategoryModel categoryModel : checkUiModel.getCategories()) {
            CheckCategoryDocumentationModel categoryDocumentationModel = createCategoryDocumentationModel(categoryModel, rootChecksContainerSpec);
            categoryDocs.add(categoryDocumentationModel);
        }

        return categoryDocs;
    }

    /**
     * Creates a documentation object for all checks within a single category.
     * @param categoryModel Category model.
     * @param rootChecksContainerSpec Root checks container specification.
     * @return Documentation of all checks within the category.
     */
    public CheckCategoryDocumentationModel createCategoryDocumentationModel(
            UIQualityCategoryModel categoryModel, AbstractRootChecksContainerSpec rootChecksContainerSpec) {
        CheckCategoryDocumentationModel categoryDocumentationModel = new CheckCategoryDocumentationModel();
        categoryDocumentationModel.setCategoryName(categoryModel.getCategory());
        categoryDocumentationModel.setCategoryHelp(categoryModel.getHelpText());

        for (UICheckModel checkModel : categoryModel.getChecks()) {

        }

        return categoryDocumentationModel;
    }
}
