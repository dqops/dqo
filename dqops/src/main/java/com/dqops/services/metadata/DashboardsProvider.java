/*
 * Copyright © 2021 DQOps (support@dqops.com)
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

package com.dqops.services.metadata;

import com.dqops.metadata.dashboards.DashboardsFolderListSpec;

/**
 * Service that builds an expanded tree of dashboards. Builds an expanded dashboard tree only once.
 */
public interface DashboardsProvider {
    /**
     * Returns a cached dashboard folder tree that is expanded. All templated (multi parameter valued) dashboards are expanded into multiple dashboards.
     *
     * @return Expanded dashboard tree.
     */
    DashboardsFolderListSpec getDashboardTree();
}
