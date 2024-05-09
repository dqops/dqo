package com.dqops.connectors.duckdb.fileslisting.azure;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.ListBlobsOptions;
import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.fileslisting.RemoteTablesLister;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Used to retrieve the list of files from Azure Blob Storage that are used as tables by duckdb's listTables.
 */
@Slf4j
public class AzureTablesLister extends RemoteTablesLister {

    /**
     * Returns list of SourceTableModel from remote storage - Azure Blob Storage
     * @param duckdb DuckdbParametersSpec
     * @param schemaName The name of a virtual schema.
     * @return The list of SourceTableModel.
     */
    public List<SourceTableModel> listTables(DuckdbParametersSpec duckdb, String schemaName){

        String pathString = duckdb.getDirectories().get(schemaName);

        AzureStoragePath pathComponents = AzureStoragePath.from(pathString, duckdb.getAccountName());

        BlobContainerClient blobContainerClient = null;

        switch(duckdb.getAzureAuthenticationMode()){
            case connection_string:
                blobContainerClient = new BlobServiceClientBuilder()
                        .connectionString(duckdb.getPassword())
                        .buildClient()
                        .getBlobContainerClient(pathComponents.getContainerName());
                break;
            case service_principal:

                ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                        .tenantId(duckdb.getTenantId())
                        .clientId(duckdb.getClientId())
                        .clientSecret(duckdb.getClientSecret())
                        .build();

                blobContainerClient = new BlobServiceClientBuilder()
                        .credential(clientSecretCredential)
                        .endpoint(pathComponents.getEndpoint())
                        .buildClient()
                        .getBlobContainerClient(pathComponents.getContainerName());
                break;
            case credential_chain:
                DefaultAzureCredential defaultAzureCredential = new DefaultAzureCredentialBuilder()
                        .build();

                blobContainerClient = new BlobServiceClientBuilder()
                        .credential(defaultAzureCredential)
                        .endpoint(pathComponents.getEndpoint())
                        .buildClient()
                        .getBlobContainerClient(pathComponents.getContainerName());

                break;
            default:
                throw new RuntimeException("Azure authentication mode " + duckdb.getAzureAuthenticationMode() + " is not supported for listing tables.");
        }

        List<String> files = listBucketObjects(blobContainerClient, pathComponents);

        List<SourceTableModel> sourceTableModels = filterAndTransform(duckdb, files, schemaName);
        return sourceTableModels;
    }

    /**
     * Lists objects form Azure Blob Storage
     * @param blobContainerClient blobContainerClient
     * @param pathComponents AzurePathComponents
     * @return The list of objects from Azure Blob Storage
     */
    private List<String> listBucketObjects(BlobContainerClient blobContainerClient, AzureStoragePath pathComponents) {
        List<String> paths = new ArrayList<>();
        try {
            ListBlobsOptions listBlobsOptions = new ListBlobsOptions().setPrefix(pathComponents.getPrefix());
            List<BlobItem> blobs = CompletableFuture.supplyAsync(() -> blobContainerClient
                    .listBlobsByHierarchy("/", listBlobsOptions, null)
                    .stream()
                    .collect(Collectors.toList())).get();

            blobs.forEach(blobItem -> {
                paths.add(blobItem.getName());
            });

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return paths;
    }

}
