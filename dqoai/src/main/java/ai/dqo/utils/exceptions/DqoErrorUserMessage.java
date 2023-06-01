/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.utils.exceptions;

/**
 * Interface for DQO exceptions with the ability to provide user-friendly messages.
 */
public interface DqoErrorUserMessage {
    /**
     * Get an informative explanation for throwing the exception.
     * @return User-friendly message with the cause of this exception.
     */
    String getUserFriendlyMessage();
}
