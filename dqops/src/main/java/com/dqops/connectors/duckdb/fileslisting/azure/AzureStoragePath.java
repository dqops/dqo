package com.dqops.connectors.duckdb.fileslisting.azure;

import lombok.Getter;

import java.net.URI;

/**
 * Parses the path to azure storage blob objects to the separate components such as endpoint, container name, prefix.
 */
@Getter
public class AzureStoragePath {

    public static final String BLOB_DOMAIN_SUFFIX = ".blob.core.windows.net";

    /**
     * Azure domain for blobs. Contains the storage account name.
     */
    private String domain;

    /**
     * The name of the container.
     */
    private String containerName;

    /**
     * The prefix in the blob container. It does not start with slash.
     */
    private String prefix;

    private AzureStoragePath(){}

    /**
     * A factory for the azure path components created from path prefix.
     * @param pathString Path string to object in azure storage.
     * @param accountName The storage account name.
     * @return AzurePathComponents
     */
    public static AzureStoragePath from(String pathString, String accountName){

        AzureStoragePath azureStoragePath = new AzureStoragePath();

        URI uri = URI.create(pathString);

        if(uri.getHost().contains(".")){
            azureStoragePath.domain = uri.getHost();
            String path = uri.getPath().substring(1);
            if(path.contains("/")){
                azureStoragePath.containerName = path.substring(0, path.indexOf("/")).replace("/","");
                azureStoragePath.prefix = path.substring(azureStoragePath.containerName.length() + 1); // + 1 removes a beginning slash
            } else {
                azureStoragePath.containerName = path;
                azureStoragePath.prefix = "";
            }
        } else {
            azureStoragePath.domain = accountName + BLOB_DOMAIN_SUFFIX;
            azureStoragePath.containerName = uri.getHost();
            String prefixRaw = uri.getPath();
            azureStoragePath.prefix = prefixRaw.isEmpty() ? prefixRaw : prefixRaw.substring(1); // substring of 1 removes a beginning slash
        }

        return azureStoragePath;
    }

    /**
     * Returns the endpoint url for azure blob container for a specific storage account.
     * @return  the endpoint url for azure blob container for a specific storage account.
     */
    public String getEndpoint(){
        return "https://" + domain;
    }

    /**
     * Returns the path in format: az://<account_name>.<BLOB_DOMAIN_SUFFIX>/<container_name>/<prefix>
     * @return the path in format: az://<account_name>.<BLOB_DOMAIN_SUFFIX>/<container_name>/<prefix>
     */
    public String getAzFullPathPrefix(){
        return AzureConstants.BLOB_STORAGE_URI_PREFIX + domain + "/" + containerName + (prefix.isEmpty() ? "" : "/" + prefix);
    }

}
