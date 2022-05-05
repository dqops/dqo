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
package ai.dqo.cli;

import org.springframework.boot.ExitCodeGenerator;

/**
 * Exit code generator that caches the exit code that should be returned.
 */
public interface CliExitCodeGenerator extends ExitCodeGenerator {
    /**
     * Stores the exit code that will be returned to the operating system.
     * @param exitCode Exit code.
     */
    void setExitCode(int exitCode);
}
