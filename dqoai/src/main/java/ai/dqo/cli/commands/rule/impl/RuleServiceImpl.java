package ai.dqo.cli.commands.rule.impl;

import ai.dqo.cli.commands.CliOperationStatus;
import ai.dqo.cli.commands.RuleFileExtension;
import ai.dqo.cli.edit.EditorLaunchService;
import ai.dqo.metadata.basespecs.InstanceStatus;
import ai.dqo.metadata.dqohome.DqoHome;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.ruledefinitions.FileRuleDefinitionWrapperImpl;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static ai.dqo.metadata.storage.localfiles.SpecFileNames.*;

/**
 * Service called from the "rule" cli commands to edit a template.
 */
@Service
public class RuleServiceImpl implements RuleService {
	private final UserHomeContextFactory userHomeContextFactory;
	private final DqoHomeContextFactory dqoHomeContextFactory;
	private final EditorLaunchService editorLaunchService;

	@Autowired
	public RuleServiceImpl(UserHomeContextFactory userHomeContextFactory, DqoHomeContextFactory dqoHomeContextFactory,
						   EditorLaunchService editorLaunchService) {
		this.userHomeContextFactory = userHomeContextFactory;
		this.dqoHomeContextFactory = dqoHomeContextFactory;
		this.editorLaunchService = editorLaunchService;
	}

	@Override
	public CliOperationStatus editTemplate(String ruleName, RuleFileExtension ext) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
		UserHome userHome = userHomeContext.getUserHome();

		DqoHomeContext dqoHomeContext = this.dqoHomeContextFactory.openLocalDqoHome();
		DqoHome dqoHome = dqoHomeContext.getDqoHome();

		FileRuleDefinitionWrapperImpl ruleDefinitionWrapper = (FileRuleDefinitionWrapperImpl) userHome.getRules()
				.getByObjectName(ruleName, true);
		FileRuleDefinitionWrapperImpl dqoRuleDefinitionWrapper = (FileRuleDefinitionWrapperImpl) dqoHome.getRules()
				.getByObjectName(ruleName, true);

		if (ruleDefinitionWrapper == null && dqoRuleDefinitionWrapper == null) {
			cliOperationStatus.setFailedMessage("There are not any rules with this name");
			return cliOperationStatus;
		}

		if (ruleDefinitionWrapper == null) {
			FileRuleDefinitionWrapperImpl newRuleDefinitionWrapper = (FileRuleDefinitionWrapperImpl)userHome.getRules().createAndAddNew(ruleName);
			newRuleDefinitionWrapper.setSpec(dqoRuleDefinitionWrapper.getSpec());
			ruleDefinitionWrapper = newRuleDefinitionWrapper;
			ruleDefinitionWrapper.setStatus(InstanceStatus.ADDED);
			ruleDefinitionWrapper.flush();
			userHome.flush();
		}

		if (ext == RuleFileExtension.PYTHON) {
			this.editorLaunchService.launchEditorForFile(ruleDefinitionWrapper.getRuleFolderNode().getPhysicalAbsolutePath()
					+ "/" + ruleDefinitionWrapper.getRuleFileNameBaseName() + CUSTOM_RULE_PYTHON_MODULE_FILE_EXT_PY);
			cliOperationStatus.setSuccess(true);
			return cliOperationStatus;
		}

		this.editorLaunchService.launchEditorForFile(ruleDefinitionWrapper.getRuleFolderNode().getPhysicalAbsolutePath()
				+ "/" + ruleDefinitionWrapper.getRuleFileNameBaseName() + CUSTOM_RULE_SPEC_FILE_EXT_YAML);
		cliOperationStatus.setSuccess(true);
		return cliOperationStatus;
	}
}
