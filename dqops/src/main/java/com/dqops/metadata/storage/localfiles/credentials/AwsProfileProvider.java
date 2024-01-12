package com.dqops.metadata.storage.localfiles.credentials;

import com.dqops.core.secrets.SecretValueLookupContext;
import software.amazon.awssdk.profiles.Profile;

import java.util.Optional;

// todo javadoc
public interface AwsProfileProvider {

    Optional<Profile> provideProfile(SecretValueLookupContext secretValueLookupContext);

}
