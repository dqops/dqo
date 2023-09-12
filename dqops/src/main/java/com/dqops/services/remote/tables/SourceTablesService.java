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
package com.dqops.services.remote.tables;

import com.dqops.rest.models.remote.RemoteTableBasicModel;

import java.util.List;

/**
 * Management service for tables on the source database.
 */
public interface SourceTablesService {

    /**
     * Returns a list of tables on a schema on the source database.
     * @param connectionName     Connection name. Required import.
     * @param schemaName         Schema name.
     * @return Schema list acquired remotely.
     */
    List<RemoteTableBasicModel> showTablesOnRemoteSchema(String connectionName, String schemaName);
}
