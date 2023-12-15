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
package com.dqops.metadata.userhome;

import com.dqops.core.principal.UserDomainIdentity;

/**
 * User home object mother.
 */
public class UserHomeObjectMother {
    /**
     * Creates a bare user home without any file system backend.
     * @return Bare, empty user home.
     */
    public static UserHomeImpl createBareUserHome() {
        return new UserHomeImpl(UserDomainIdentity.LOCAL_INSTANCE_ADMIN_IDENTITY);
    }
}
