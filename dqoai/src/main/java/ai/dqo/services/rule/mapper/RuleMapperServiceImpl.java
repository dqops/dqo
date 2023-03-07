package ai.dqo.services.rule.mapper;

import ai.dqo.core.filesystem.virtual.FileContent;
import ai.dqo.metadata.definitions.rules.RuleDefinitionSpec;
import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapper;
import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapperImpl;
import ai.dqo.rest.models.metadata.RuleBasicModel;
import ai.dqo.rest.models.metadata.RuleModel;
import ai.dqo.services.rule.mapper.RuleMapperService;
import org.springframework.stereotype.Service;

@Service
public class RuleMapperServiceImpl implements RuleMapperService {

    @Override
    public RuleDefinitionWrapper toRuleDefinitionWrapper(RuleBasicModel ruleBasicModel) {

        FileContent fileContent = new FileContent(ruleBasicModel.getRulePythonModuleContent());

        RuleDefinitionSpec ruleDefinitionSpec = new RuleDefinitionSpec();
        ruleDefinitionSpec.setType(ruleBasicModel.getType());
        ruleDefinitionSpec.setJavaClassName(ruleBasicModel.getJavaClassName());
        ruleDefinitionSpec.setMode(ruleBasicModel.getMode());
        ruleDefinitionSpec.setTimeWindow(ruleBasicModel.getTimeWindow());
        ruleDefinitionSpec.setFields(ruleBasicModel.getFields());
        ruleDefinitionSpec.setParameters(ruleBasicModel.getParameters());

        RuleDefinitionWrapper ruleDefinitionWrapper = new RuleDefinitionWrapperImpl();
        ruleDefinitionWrapper.setRuleName(ruleBasicModel.getRuleName());
        ruleDefinitionWrapper.setSpec(new RuleDefinitionSpec());
        ruleDefinitionWrapper.setRulePythonModuleContent(fileContent);

        return ruleDefinitionWrapper;
    }

    @Override
    public RuleDefinitionWrapper toRuleDefinitionWrapper(RuleModel ruleModel) {
        return null;
    }

    @Override
    public RuleModel toRuleModel(RuleDefinitionWrapper ruleDefinitionWrapper) {
        return null;
    }

    @Override
    public RuleBasicModel toRuleBasicModel(RuleDefinitionWrapper ruleDefinitionWrapper) {

        RuleBasicModel ruleBasicModel = new RuleBasicModel();
        ruleBasicModel.withRuleName(ruleDefinitionWrapper.getRuleName())
                .withRulePythonModuleContent(ruleDefinitionWrapper.getRulePythonModuleContent().getTextContent())
                .withType(ruleDefinitionWrapper.getSpec().getType())
                .withJavaClassName(ruleDefinitionWrapper.getSpec().getJavaClassName())
                .withMode(ruleDefinitionWrapper.getSpec().getMode())
                .withTimeWindow(ruleDefinitionWrapper.getSpec().getTimeWindow())
                .withFields(ruleDefinitionWrapper.getSpec().getFields())
                .withParameters(ruleDefinitionWrapper.getSpec().getParameters());

        return ruleBasicModel;
    }
}
