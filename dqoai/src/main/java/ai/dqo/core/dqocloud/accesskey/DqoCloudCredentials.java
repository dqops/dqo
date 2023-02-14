package ai.dqo.core.dqocloud.accesskey;

import ai.dqo.cloud.rest.model.TenantAccessTokenModel;
import com.google.auth.oauth2.AccessToken;

/**
 * Model object that stores the credentials model returned from the DQO Cloud and a GCP API access token.
 */
public class DqoCloudCredentials {
    private TenantAccessTokenModel tenantAccessTokenModel;
    private AccessToken accessToken;

    /**
     * Creates a pair of a full tenant access token (with the details of the google storage bucket) and a GCP API access token.
     * @param tenantAccessTokenModel Access token model with the storage bucket details.
     * @param accessToken Access token.
     */
    public DqoCloudCredentials(TenantAccessTokenModel tenantAccessTokenModel, AccessToken accessToken) {
        this.tenantAccessTokenModel = tenantAccessTokenModel;
        this.accessToken = accessToken;
    }

    /**
     * Returns the tentant's remote folder credentials model with the Google Storage bucket name and the path inside the bucket.
     * @return Tenant access token model.
     */
    public TenantAccessTokenModel getTenantAccessTokenModel() {
        return tenantAccessTokenModel;
    }

    /**
     * Returns the access token to be used in GCP API.
     * @return Access token.
     */
    public AccessToken getAccessToken() {
        return accessToken;
    }
}
