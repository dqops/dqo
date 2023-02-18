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
package ai.dqo.core.scheduler.quartz;

/**
 * Constants with the default Quartz job names.
 */
public final class JobNames {
    /**
     * Job name to synchronize the metadata.
     */
    public static final String SYNCHRONIZE_METADATA = "SYNCHRONIZE_METADATA";

    /**
     * Job name to periodically runs data quality checks.
     */
    public static final String RUN_CHECKS = "RUN_CHECKS";

    /**
     * Job name that does nothing.
     */
    public static final String DUMMY = "DUMMY";
}
