/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.dqo.services.dashboards;

import ai.dqo.metadata.dashboards.DashboardsFolderListSpec;
import ai.dqo.metadata.dashboards.DashboardsFolderSpec;
import org.springframework.stereotype.Service;

/**
 * Service that returns the list of built-in data quality dashboards, divided into folders.
 */
@Service
public class DashboardServiceImpl implements DashboardService {
    private DashboardsFolderSpec dashboards;

    /**
     * Default constructor - creates the list of default dashboards.
     */
    public DashboardServiceImpl() {
        this.dashboards =
                new DashboardsFolderSpec("")
                        .withFolder("Governance", f -> {
                            f.withDqoCloudDashboard("KPIs",
                                    "https://datastudio.google.com/embed/reporting/5f3d7f47-ea13-465c-95e6-5ab75948a0a4/page/c5B8C", 1600, 1600);
                        })
                        .withFolder("Operational", f -> {
                            f.withDqoCloudDashboard("Tables with most alerts",
                                    "https://datastudio.google.com/embed/reporting/64146a51-d02b-498c-bc3d-e04786414575/page/c5B8C", 1600, 1800);
                        })
                        .withFolder("Details", f -> {
                            f.withDqoCloudDashboard("Alerts per table",
                                    "https://datastudio.google.com/embed/reporting/00ae6cbe-7a1d-48d7-b221-4d56d0207d3c/page/c5B8C", 1600, 1800);
                        });
    }

    /**
     * Returns the list of root folders with the dashboards.
     * @return List of root folders with dashboards.
     */
    @Override
    public DashboardsFolderListSpec getDashboards() {
        return dashboards.getFolders();
    }
}
