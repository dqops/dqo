/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.storage.localfiles.credentials.aws;

import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.metadata.credentials.SharedCredentialList;
import com.dqops.metadata.credentials.SharedCredentialWrapper;
import com.dqops.metadata.userhome.UserHome;

public class CredentialsFileProvider {

    public static FileContent getCredentialFileContent(String credentialName, SecretValueLookupContext secretValueLookupContext){
        if(secretValueLookupContext == null){
            return null;
        }
        UserHome userHome = secretValueLookupContext.getUserHome();
        if(userHome == null){
            return null;
        }
        SharedCredentialList sharedCredentialList = userHome.getCredentials();
        if(sharedCredentialList == null){
            return null;
        }
        SharedCredentialWrapper credentialWrapper = sharedCredentialList.getByObjectName(credentialName, true);
        if (credentialWrapper == null) {
            return null;
        }
        FileContent fileContent = credentialWrapper.getObject();
        return fileContent;
    }

}
