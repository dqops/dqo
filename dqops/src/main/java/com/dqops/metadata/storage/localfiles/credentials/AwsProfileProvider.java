package com.dqops.metadata.storage.localfiles.credentials;

import com.dqops.core.secrets.SecretValueLookupContext;
import software.amazon.awssdk.profiles.Profile;

public interface AwsProfileProvider {

    Profile provideProfile(SecretValueLookupContext secretValueLookupContext);

}
