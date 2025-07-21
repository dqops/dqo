/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.rule.impl;

import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.RuleFileExtension;
import com.dqops.cli.edit.EditorLaunchService;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.ruledefinitions.FileRuleDefinitionWrapperImpl;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.dqops.metadata.storage.localfiles.SpecFileNames.CUSTOM_RULE_PYTHON_MODULE_FILE_EXT_PY;
import static com.dqops.metadata.storage.localfiles.SpecFileNames.CUSTOM_RULE_SPEC_FILE_EXT_YAML;

/**
 * Service called from the "rule" cli commands to edit a template.
 */
@Service
public class RuleCliServiceImpl implements RuleCliService {
	private final UserHomeContextFactory userHomeContextFactory;
	private final DqoHomeContextFactory dqoHomeContextFactory;
	private final EditorLaunchService editorLaunchService;
	private final DqoUserPrincipalProvider userPrincipalProvider;

	@Autowired
	public RuleCliServiceImpl(UserHomeContextFactory userHomeContextFactory,
							  DqoHomeContextFactory dqoHomeContextFactory,
							  EditorLaunchService editorLaunchService,
							  DqoUserPrincipalProvider userPrincipalProvider) {
		this.userHomeContextFactory = userHomeContextFactory;
		this.dqoHomeContextFactory = dqoHomeContextFactory;
		this.editorLaunchService = editorLaunchService;
		this.userPrincipalProvider = userPrincipalProvider;
	}

	@Override
	public CliOperationStatus editTemplate(String ruleName, RuleFileExtension ext) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		DqoUserPrincipal userPrincipal = this.userPrincipalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
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
