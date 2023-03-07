package ai.dqo.services.rule;

import ai.dqo.metadata.definitions.rules.RuleDefinitionList;
import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapper;
import ai.dqo.metadata.dqohome.DqoHome;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.rest.models.metadata.RuleBasicModel;
import ai.dqo.rest.models.metadata.RuleModel;
import ai.dqo.services.rule.mapping.RuleMappingService;
import com.google.api.gax.rpc.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RuleServiceImpl implements RuleService{

    private final DqoHomeContextFactory dqoHomeContextFactory;
    private final UserHomeContextFactory userHomeContextFactory;

    private final RuleMappingService ruleMappingService;

    @Autowired
    public RuleServiceImpl(DqoHomeContextFactory dqoHomeContextFactory,
                           UserHomeContextFactory userHomeContextFactory,
                           RuleMappingService ruleMappingService)
    {
        this.dqoHomeContextFactory = dqoHomeContextFactory;
        this.userHomeContextFactory = userHomeContextFactory;
        this.ruleMappingService = ruleMappingService;
    }

    @Override
    public List<RuleBasicModel> getAllBuiltInRules() {
        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        RuleDefinitionList ruleDefinitionList = dqoHome.getRules();

        return ruleDefinitionList.toList().stream()
                .map(ruleMappingService::toRuleBasicModel)
                .collect(Collectors.toList());

    }

    @Override
    public RuleBasicModel getBuiltInRule(String ruleName) {

        DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
        DqoHome dqoHome = dqoHomeContext.getDqoHome();

        RuleDefinitionList ruleDefinitionList = dqoHome.getRules();
        RuleDefinitionWrapper ruleDefinitionWrapper = ruleDefinitionList.getByObjectName(ruleName, true);

        if (ruleDefinitionWrapper == null) {
            throw new RuleNotFoundException("The given rule name: "+ruleName+" was not found");
        }

        return ruleMappingService.toRuleBasicModel(ruleDefinitionWrapper);

    }

    @Override
    public RuleBasicModel getAllCustomInRules() {
        return null;
    }

    @Override
    public RuleBasicModel getCustomRule(String name) {
        return null;
    }

    @Override
    public void createRule(RuleBasicModel ruleBasicModel) {

    }

    @Override
    public void updateCustomRule(RuleBasicModel ruleBasicModel) {

    }

    @Override
    public List<RuleBasicModel> getAllCombinedRules() {
        return null;
    }

    @Override
    public RuleBasicModel getCombinedRule() {
        return null;
    }


}
