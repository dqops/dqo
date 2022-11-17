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
package ai.dqo.rules;

/**
 * Rule severity levels. Matches the severity level name (warning - 1, alert - 2, fatal - 3) with a numeric level.
 */
public enum RuleSeverityLevel {
    WARNING(1),
    ALERT(2),
    FATAL(3);

    /**
     * Numeric severity level.
     */
    public final int level;

    RuleSeverityLevel(int level) {
        this.level = level;
    }
}
