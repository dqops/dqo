package com.dqops.metadata.storage.localfiles.credentials.aws;

import com.dqops.core.secrets.SecretValueLookupContext;
import software.amazon.awssdk.profiles.Profile;

import java.util.Optional;

/**
 * AWS profile provider.
 */
public interface AwsProfileProvider {

    /**
     * Provides the AWS profile.
     * @param secretValueLookupContext Secret value lookup context used to access shared credentials.
     * @return AWS Profile object.
     */
    Optional<Profile> provideProfile(SecretValueLookupContext secretValueLookupContext);

}
