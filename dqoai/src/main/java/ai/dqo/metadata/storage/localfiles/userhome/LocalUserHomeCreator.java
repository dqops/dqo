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
package ai.dqo.metadata.storage.localfiles.userhome;

/**
 * Component that ensures that a DQO local user home was created and the default files were written.
 */
public interface LocalUserHomeCreator {
    /**
     * Initializes the DQO user home at the default location.
     */
    void initializeDefaultDqoUserHome();

    /**
     * Checks the default DQO_USER_HOME path if it points to a valid and initialized DQO user home.
     * @return True when the path points to a valid DQO User home, false otherwise (the user home must be initialized before first use).
     */
    boolean isDefaultDqoUserHomeInitialized();

    /**
     * Checks the given path if it points to a valid and initialized DQO user home.
     * @param userHomePathString Path to a potential DQO user home.
     * @return True when the path points to a valid DQO User home, false otherwise.
     */
    boolean isDqoUserHomeInitialized(String userHomePathString);

    /**
     * Initializes a DQO user home at a given location.
     * @param userHomePathString Path to the DQO user home.
     */
    void initializeDqoUserHome(String userHomePathString);

    /**
     * Ensures that the DQO User home is initialized at the default location. Prompts the user before creating the user home to confirm.
     * NOTE: this method may forcibly stop the program execution if the user did not agree to create the DQO User home.
     * @param isHeadless Is headless mode - when true, then the dqo user home is created silently, when false (interactive execution) then the user is asked to confirm.
     */
    void ensureDefaultUserHomeIsInitialized(boolean isHeadless);
}
