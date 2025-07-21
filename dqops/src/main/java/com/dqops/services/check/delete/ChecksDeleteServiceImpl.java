/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.check.delete;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.custom.CustomCategoryCheckSpecMap;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Deleted selected configured checks from the matched hierarchy node.
 */
@Component
public class ChecksDeleteServiceImpl implements ChecksDeleteService {

    private final UserHomeContextFactory userHomeContextFactory;
    private final HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;

    public ChecksDeleteServiceImpl(UserHomeContextFactory userHomeContextFactory,
                                   HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.hierarchyNodeTreeSearcher = hierarchyNodeTreeSearcher;
    }

    /**
     * Deletes selected checks that match the filters.
     * @param filters CheckSearchFilters which matches will be deleted.
     * @param userIdentity User identity
     */
    public void deleteSelectedChecks(CheckSearchFilters filters, UserDomainIdentity userIdentity) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userIdentity, false);
        UserHome userHome = userHomeContext.getUserHome();
        Collection<AbstractCheckSpec<?, ?, ?, ?>> checks = hierarchyNodeTreeSearcher.findChecks(userHome.getConnections(), filters);

        for (AbstractCheckSpec<?,?,?,?> check : checks) {
            HierarchyId parentHierarchyId = check.getHierarchyId().getParentHierarchyId();
            HierarchyNode checkParentNode = userHome.findNode(parentHierarchyId);
            if (checkParentNode instanceof AbstractCheckCategorySpec) {
                AbstractCheckCategorySpec checkCategoryNode = (AbstractCheckCategorySpec)checkParentNode;
                checkCategoryNode.detachChildNode(check.getCheckName());
            }
            else if (checkParentNode instanceof CustomCategoryCheckSpecMap) {
                CustomCategoryCheckSpecMap customChecksMap = (CustomCategoryCheckSpecMap)checkParentNode;
                customChecksMap.remove(check.getCheckName());
            }
        }
        userHomeContext.flush();
    }

}
