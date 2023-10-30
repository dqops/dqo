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

package com.dqops.core.dqocloud.users;

import com.dqops.core.dqocloud.login.DqoUserRole;
import com.dqops.utils.docs.SampleStringsRegistry;
import com.dqops.utils.docs.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * DQOps Cloud user model - identifies a user in a multi-user DQOps deployment.
 */
@ApiModel(value = "DqoCloudUserModel", description = "DQOps Cloud user model - identifies a user in a multi-user DQOps deployment.")
@Data
public class DqoCloudUserModel {
    /**
     * User's email that identifies the user.
     */
    @JsonPropertyDescription("User's email that identifies the user.")
    private String email;

    /**
     * Account role.
     */
    @JsonPropertyDescription("Account role.")
    private DqoUserRole accountRole;

    public static class DqoCloudUserModelSampleFactory implements SampleValueFactory<DqoCloudUserModel> {
        @Override
        public DqoCloudUserModel createSample() {
            return new DqoCloudUserModel() {{
                setEmail(SampleStringsRegistry.getUserName() + "@mail.com");
                setAccountRole(DqoUserRole.OPERATOR);
            }};
        }
    }
}
