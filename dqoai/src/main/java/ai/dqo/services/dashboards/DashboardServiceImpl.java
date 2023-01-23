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

import java.util.LinkedHashMap;

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
                                    "https://datastudio.google.com/embed/reporting/5f3d7f47-ea13-465c-95e6-5ab75948a0a4/page/c5B8C", 1580, 1800,
                                    new LinkedHashMap<>() {{
                                        put("ds0.p_check_type", "");
                                    }});
                            f.withDqoCloudDashboard("KPIs adhoc",
                                    "https://datastudio.google.com/embed/reporting/5f3d7f47-ea13-465c-95e6-5ab75948a0a4/page/c5B8C", 1580, 1800,
                                    new LinkedHashMap<>() {{
                                        put("ds0.p_check_type", "adhoc");
                                    }});
                            f.withDqoCloudDashboard("KPIs checkpoint",
                                    "https://datastudio.google.com/embed/reporting/5f3d7f47-ea13-465c-95e6-5ab75948a0a4/page/c5B8C", 1580, 1800,
                                    new LinkedHashMap<>() {{
                                        put("ds0.p_check_type", "checkpoint");
                                    }});
                            f.withDqoCloudDashboard("KPIs partitioned",
                                    "https://datastudio.google.com/embed/reporting/5f3d7f47-ea13-465c-95e6-5ab75948a0a4/page/c5B8C", 1580, 1800,
                                    new LinkedHashMap<>() {{
                                        put("ds0.p_check_type", "partitioned");
                                    }});
                        })
                        .withFolder("Operational", f -> {
                            f.withDqoCloudDashboard("Tables with most alerts",
                                    "https://datastudio.google.com/embed/reporting/64146a51-d02b-498c-bc3d-e04786414575/page/c5B8C", 1580, 1800,
                                    new LinkedHashMap<>() {{
                                        put("ds0.p_check_type", "");
                                    }});
                            f.withDqoCloudDashboard("Tables with most alerts - adhoc",
                                    "https://datastudio.google.com/embed/reporting/64146a51-d02b-498c-bc3d-e04786414575/page/c5B8C", 1580, 1800,
                                    new LinkedHashMap<>() {{
                                        put("ds0.p_check_type", "adhoc");
                                    }});
                            f.withDqoCloudDashboard("Tables with most alerts - checkpoint",
                                    "https://datastudio.google.com/embed/reporting/64146a51-d02b-498c-bc3d-e04786414575/page/c5B8C", 1580, 1800,
                                    new LinkedHashMap<>() {{
                                        put("ds0.p_check_type", "checkpoint");
                                    }});
                            f.withDqoCloudDashboard("Tables with most alerts - partitioned",
                                    "https://datastudio.google.com/embed/reporting/64146a51-d02b-498c-bc3d-e04786414575/page/c5B8C", 1580, 1800,
                                    new LinkedHashMap<>() {{
                                        put("ds0.p_check_type", "partitioned");
                                    }});
                        })
                        .withFolder("Details", f -> {
                            f.withDqoCloudDashboard("History of alerts",
                                    "https://datastudio.google.com/embed/reporting/00ae6cbe-7a1d-48d7-b221-4d56d0207d3c/page/c5B8C", 1580, 2500,
                                    new LinkedHashMap<>() {{
                                        put("ds0.p_check_type", "");
                                    }});
                            f.withDqoCloudDashboard("History of alerts - adhoc",
                                    "https://datastudio.google.com/embed/reporting/00ae6cbe-7a1d-48d7-b221-4d56d0207d3c/page/c5B8C", 1580, 2500,
                                    new LinkedHashMap<>() {{
                                        put("ds0.p_check_type", "adhoc");
                                    }});
                            f.withDqoCloudDashboard("History of alerts - checkpoint",
                                    "https://datastudio.google.com/embed/reporting/00ae6cbe-7a1d-48d7-b221-4d56d0207d3c/page/c5B8C", 1580, 2500,
                                    new LinkedHashMap<>() {{
                                        put("ds0.p_check_type", "checkpoint");
                                    }});
                            f.withDqoCloudDashboard("History of alerts - partitioned",
                                    "https://datastudio.google.com/embed/reporting/00ae6cbe-7a1d-48d7-b221-4d56d0207d3c/page/c5B8C", 1580, 2500,
                                    new LinkedHashMap<>() {{
                                        put("ds0.p_check_type", "partitioned");
                                    }});
                            f.withDqoCloudDashboard("Alerts per check",
                                    "https://datastudio.google.com/embed/reporting/77fe746e-8e5f-4aae-93b7-0f3ee42dfa2a/page/c5B8C", 1580, 2100,
                                    new LinkedHashMap<>() {{
                                        put("ds0.p_check_type", "");
                                    }});
                            f.withDqoCloudDashboard("Alerts per check - adhoc",
                                    "https://datastudio.google.com/embed/reporting/77fe746e-8e5f-4aae-93b7-0f3ee42dfa2a/page/c5B8C", 1580, 2100,
                                    new LinkedHashMap<>() {{
                                        put("ds0.p_check_type", "adhoc");
                                    }});
                            f.withDqoCloudDashboard("Alerts per check - checkpoints",
                                    "https://datastudio.google.com/embed/reporting/77fe746e-8e5f-4aae-93b7-0f3ee42dfa2a/page/c5B8C", 1580, 2100,
                                    new LinkedHashMap<>() {{
                                        put("ds0.p_check_type", "checkpoint");
                                    }});
                            f.withDqoCloudDashboard("Alerts per check - partitioned",
                                    "https://datastudio.google.com/embed/reporting/77fe746e-8e5f-4aae-93b7-0f3ee42dfa2a/page/c5B8C", 1580, 2100,
                                    new LinkedHashMap<>() {{
                                        put("ds0.p_check_type", "partitioned");
                                    }});
                            f.withDqoCloudDashboard("Alerts per table",
                                    "https://datastudio.google.com/embed/reporting/ddf39a75-b3fa-413d-b41d-312b6ffb1b74/page/c5B8C", 1580, 2100,
                                    new LinkedHashMap<>() {{
                                        put("ds0.p_check_type", "");
                                    }});
                            f.withDqoCloudDashboard("Alerts per table - adhoc",
                                    "https://datastudio.google.com/embed/reporting/ddf39a75-b3fa-413d-b41d-312b6ffb1b74/page/c5B8C", 1580, 2100,
                                    new LinkedHashMap<>() {{
                                        put("ds0.p_check_type", "adhoc");
                                    }});
                            f.withDqoCloudDashboard("Alerts per table - checkpoints",
                                    "https://datastudio.google.com/embed/reporting/ddf39a75-b3fa-413d-b41d-312b6ffb1b74/page/c5B8C", 1580, 2100,
                                    new LinkedHashMap<>() {{
                                        put("ds0.p_check_type", "checkpoint");
                                    }});
                            f.withDqoCloudDashboard("Alerts per table - partitioned",
                                    "https://datastudio.google.com/embed/reporting/ddf39a75-b3fa-413d-b41d-312b6ffb1b74/page/c5B8C", 1580, 2100,
                                    new LinkedHashMap<>() {{
                                        put("ds0.p_check_type", "partitioned");
                                    }});
                           f.withDqoCloudDashboard("Partitioned row count readouts",
                                    "https://datastudio.google.com/embed/reporting/e66f82cf-efea-4e79-8190-2396d1218b2e/page/c5B8C", 1580, 2100);
                           f.withDqoCloudDashboard("Biggest tables from daily checkpoints",
                                    "https://datastudio.google.com/embed/reporting/2489ad1c-3651-4c5e-ace4-75d4ba0e43d2/page/c5B8C", 1580, 1900);
                           f.withDqoCloudDashboard("Biggest tables from monthly checkpoints",
                                    "https://datastudio.google.com/embed/reporting/812c1047-f2d7-4155-b1c8-de39c50e371b/page/c5B8C", 1580, 1900);
                           f.withDqoCloudDashboard("Table freshness known from checkpoints",
                                    "https://datastudio.google.com/embed/reporting/e5d9a738-4592-4e46-99a5-295791fad600/page/c5B8C", 1580, 1800);
                           f.withDqoCloudDashboard("Table freshness- the last ingested event",
                                    "https://datastudio.google.com/embed/reporting/19ab1796-a679-42ed-8a83-db6c56bdd0e6/page/c5B8C", 1580, 1800);
                           f.withDqoCloudDashboard("Tables ingestion delay",
                                    "https://datastudio.google.com/embed/reporting/85dc0bb8-e187-406b-b4f0-a343df5fb52a/page/c5B8C", 1580, 2200);
                           f.withDqoCloudDashboard("Most incomplete columns - count",
                                    "https://datastudio.google.com/embed/reporting/1d7ca470-04d4-479d-b702-c610cfda6601/page/c5B8C", 1580, 1700);
                           /**f.withDqoCloudDashboard("Most incomplete columns - percent",
                                    "https://datastudio.google.com/embed/reporting/e7165c33-e937-4573-a6b1-4f60fbb908ca/page/p_fbueszx31c", 1580, 1700);
                            */
                           f.withDqoCloudDashboard("Most incomplete columns - percent",
                                    "https://datastudio.google.com/embed/reporting/e7165c33-e937-4573-a6b1-4f60fbb908ca/page/p_fbueszx31c", 1580, 1700,
                                    new LinkedHashMap<>() {{
                                        put("ds0.p_check_type", "adhoc");
                                    }});

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
