package com.dqops.connectors.duckdb.fileslisting;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.ListBlobsOptions;
import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
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
        URI uri = URI.create(pathString);

        String containerName = uri.getHost();

        BlobContainerClient blobContainerClient = new BlobServiceClientBuilder()
                .connectionString(duckdb.getPassword())
                .buildClient()
                .getBlobContainerClient(containerName);

        List<String> files = listBucketObjects(blobContainerClient, uri);

        List<SourceTableModel> sourceTableModels = filterAndTransform(duckdb, files, schemaName);
        return sourceTableModels;
    }

    /**
     * Lists objects form Azure Blob Storage
     * @param blobContainerClient blobContainerClient
     * @param uri uri
     * @return The list of objects from Azure Blob Storage
     */
    private List<String> listBucketObjects(BlobContainerClient blobContainerClient, URI uri) {
        List<String> paths = new ArrayList<>();

        String prefixRaw = uri.getPath();
        String folderPrefix = prefixRaw.isEmpty() ? prefixRaw : prefixRaw.substring(1);

        try {
            ListBlobsOptions listBlobsOptions = new ListBlobsOptions().setPrefix(folderPrefix);
            List<BlobItem> blobItems = blobContainerClient
                    .listBlobsByHierarchy("/", listBlobsOptions, null)
                    .stream()
                    .collect(Collectors.toList());
            for (BlobItem blobItem : blobItems) {
                paths.add(blobItem.getName());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return paths;
    }

}
