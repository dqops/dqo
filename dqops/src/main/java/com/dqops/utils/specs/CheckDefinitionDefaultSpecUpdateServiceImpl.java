/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.specs;

import com.dqops.checks.DefaultRuleSeverityLevel;
import com.dqops.metadata.definitions.checks.CheckDefinitionList;
import com.dqops.metadata.definitions.checks.CheckDefinitionSpec;
import com.dqops.metadata.definitions.checks.CheckDefinitionWrapper;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.rules.TargetRuleSeverityLevel;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.matching.SimilarCheckMatchingService;
import com.dqops.services.check.matching.SimilarCheckModel;
import com.dqops.services.check.matching.SimilarChecksContainer;
import com.dqops.services.check.matching.SimilarChecksGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Built-in check definition update service that updates the list of supported built-in checks as check specification files
 * stored in the DQOps Home folder.
 */
@Component
public class CheckDefinitionDefaultSpecUpdateServiceImpl implements CheckDefinitionDefaultSpecUpdateService {
    private SimilarCheckMatchingService similarCheckMatchingService;

    @Autowired
    public CheckDefinitionDefaultSpecUpdateServiceImpl(SimilarCheckMatchingService similarCheckMatchingService) {
        this.similarCheckMatchingService = similarCheckMatchingService;
    }

    /**
     * Updates the definitions of built-in checks in the DQOps Home's checks folder.
     * @param dqoHomeContext DQOps Home context.
     */
    @Override
    public void updateCheckSpecifications(DqoHomeContext dqoHomeContext) {
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        CheckDefinitionList dqoHomeChecksList = dqoHome.getChecks();

        Set<String> foundChecks = new LinkedHashSet<>();
        ArrayList<SimilarChecksGroup> allCheckGroups = new ArrayList<>();
        SimilarChecksContainer similarTableChecks = this.similarCheckMatchingService.findSimilarTableChecks();
        allCheckGroups.addAll(similarTableChecks.getSimilarCheckGroups());
        SimilarChecksContainer similarColumnChecks = this.similarCheckMatchingService.findSimilarColumnChecks();
        allCheckGroups.addAll(similarColumnChecks.getSimilarCheckGroups());

        for (SimilarChecksGroup similarChecksGroup : allCheckGroups) {
            for (SimilarCheckModel similarCheckModel : similarChecksGroup.getSimilarChecks()) {
                CheckModel checkModel = similarCheckModel.getCheckModel();
                String categoryName = similarCheckModel.getCategory();
                String fullCheckName = CheckDefinitionList.makeCheckName(similarCheckModel.getCheckTarget(),
                        similarCheckModel.getCheckType(), similarCheckModel.getTimeScale(),
                        categoryName, checkModel.getCheckName());

                String sensorName = checkModel.getSensorName();
                String ruleName = checkModel.getRule().getError().getRuleName();
                String helpText = checkModel.getHelpText();
                String friendlyName = checkModel.getFriendlyName();
                DefaultRuleSeverityLevel defaultSeverity = checkModel.getDefaultSeverity();
                boolean isStandard = checkModel.isStandard();

                CheckDefinitionWrapper checkDefinitionWrapper = dqoHomeChecksList.getByObjectName(fullCheckName, true);
                if (checkDefinitionWrapper != null) {
                    checkDefinitionWrapper.getSpec().setSensorName(sensorName);
                    checkDefinitionWrapper.getSpec().setRuleName(ruleName);
                    checkDefinitionWrapper.getSpec().setHelpText(helpText);
                    checkDefinitionWrapper.getSpec().setFriendlyName(friendlyName);
                    checkDefinitionWrapper.getSpec().setDefaultSeverity(defaultSeverity);
                    checkDefinitionWrapper.getSpec().setStandard(isStandard);
                } else {
                    CheckDefinitionSpec checkDefinitionSpec = new CheckDefinitionSpec(sensorName, ruleName, helpText);
                    checkDefinitionWrapper = dqoHomeChecksList.createAndAddNew(fullCheckName);
                    checkDefinitionWrapper.setSpec(checkDefinitionSpec);
                }

                foundChecks.add(fullCheckName);
            }
        }

        for (CheckDefinitionWrapper currentCheckWrapper : dqoHomeChecksList.toList()) {
            if (!foundChecks.contains(currentCheckWrapper.getCheckName())) {
                currentCheckWrapper.markForDeletion();
            }
        }

        dqoHomeContext.flush();
    }
}
