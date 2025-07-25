/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.duckdb;

import com.dqops.connectors.ConnectionProviderSpecificParameters;
import com.dqops.connectors.duckdb.config.DuckdbFilesFormatType;
import com.dqops.connectors.duckdb.config.DuckdbReadMode;
import com.dqops.connectors.duckdb.config.DuckdbStorageType;
import com.dqops.connectors.storage.aws.AwsAuthenticationMode;
import com.dqops.connectors.storage.azure.AzureAuthenticationMode;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.sources.BaseProviderParametersSpec;
import com.dqops.metadata.sources.fileformat.avro.AvroFileFormatSpec;
import com.dqops.metadata.sources.fileformat.csv.CsvFileFormatSpec;
import com.dqops.metadata.sources.fileformat.deltalake.DeltaLakeFileFormatSpec;
import com.dqops.metadata.sources.fileformat.iceberg.IcebergFileFormatSpec;
import com.dqops.metadata.sources.fileformat.json.JsonFileFormatSpec;
import com.dqops.metadata.sources.fileformat.ParquetFileFormatSpec;
import com.dqops.metadata.storage.localfiles.credentials.aws.AwsConfigProfileSettingNames;
import com.dqops.metadata.storage.localfiles.credentials.aws.AwsCredentialProfileSettingNames;
import com.dqops.metadata.storage.localfiles.credentials.aws.AwsDefaultConfigProfileProvider;
import com.dqops.metadata.storage.localfiles.credentials.aws.AwsDefaultCredentialProfileProvider;
import com.dqops.metadata.storage.localfiles.credentials.azure.AzureCredential;
import com.dqops.metadata.storage.localfiles.credentials.azure.AzureCredentialsProvider;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import picocli.CommandLine;
import software.amazon.awssdk.profiles.Profile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * DuckDB connection parameters.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class DuckdbParametersSpec extends BaseProviderParametersSpec
        implements ConnectionProviderSpecificParameters {
    private static final ChildHierarchyNodeFieldMapImpl<DuckdbParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(BaseProviderParametersSpec.FIELDS) {
        {
            put("csv", o -> o.csv);
            put("json", o -> o.json);
            put("parquet", o -> o.parquet);
            put("avro", o -> o.avro);
            put("iceberg", o -> o.iceberg);
            put("delta_lake", o -> o.deltaLake);
        }
    };

    @CommandLine.Option(names = {"--duckdb-read-mode"}, description = "DuckDB read mode.")
    @JsonPropertyDescription("DuckDB read mode.")
    private DuckdbReadMode readMode = DuckdbReadMode.in_memory;

    @CommandLine.Option(names = {"--duckdb-files-format-type"}, description = "Type of source files format for DuckDB.")
    @JsonPropertyDescription("Type of source files format for DuckDB.")
    private DuckdbFilesFormatType filesFormatType;

    @CommandLine.Option(names = {"--duckdb-database"}, description = "DuckDB database name for in-memory read mode. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("DuckDB database name for in-memory read mode. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String database;

    @CommandLine.Option(names = {"--duckdb-enable-optimizer"}, description = "Enables a query optimizer that uses statistics. By default, the optimizer is disabled to enable analysis of Parquet files with invalid or outdated statistics.")
    @JsonPropertyDescription("Enables a query optimizer that uses statistics. By default, the optimizer is disabled to enable analysis of Parquet files with invalid or outdated statistics.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean enableOptimizer;

    @CommandLine.Option(names = {"-Duck"}, description = "DuckDB additional properties that are added to the JDBC connection string")
    @JsonPropertyDescription("A dictionary of custom JDBC parameters that are added to the JDBC connection string, a key/value dictionary.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> properties;

    @JsonPropertyDescription("Csv file format specification.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CsvFileFormatSpec csv;

    @JsonPropertyDescription("Json file format specification.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private JsonFileFormatSpec json;

    @JsonPropertyDescription("Parquet file format specification.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ParquetFileFormatSpec parquet;

    @JsonPropertyDescription("Avro file format specification.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private AvroFileFormatSpec avro;

    @JsonPropertyDescription("Iceberg file format specification.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private IcebergFileFormatSpec iceberg;

    @JsonPropertyDescription("Delta Lake file format specification.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DeltaLakeFileFormatSpec deltaLake;

    @JsonPropertyDescription("Virtual schema name to directory mappings. The path must be an absolute path.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> directories = new HashMap<>();

    @CommandLine.Option(names = {"--duckdb-directories"}, description = "Virtual schema name to directory mappings. The path must be an absolute path.")
    @JsonIgnore
    private String directoriesString;

    @CommandLine.Option(names = {"--duckdb-storage-type"}, description = "The storage type.")
    @JsonPropertyDescription("The storage type.")
    private DuckdbStorageType storageType;

    @CommandLine.Option(names = {"--duckdb-aws-authentication-mode"}, description = "The authentication mode for AWS. Supports also a ${DUCKDB_AWS_AUTHENTICATION_MODE} configuration with a custom environment variable.")
    @JsonPropertyDescription("The authentication mode for AWS. Supports also a ${DUCKDB_AWS_AUTHENTICATION_MODE} configuration with a custom environment variable.")
    private AwsAuthenticationMode awsAuthenticationMode;

    @CommandLine.Option(names = {"--duckdb-aws-default-authentication-chain"}, description = "The default authentication chain for AWS. For example: 'env;config;sts;sso;instance;process'. Supports also a ${DUCKDB_AWS_AUTHENTICATION_MODE} configuration with a custom environment variable.")
    @JsonPropertyDescription("The default authentication chain for AWS. For example: 'env;config;sts;sso;instance;process'.. Supports also a ${DUCKDB_AWS_AUTHENTICATION_MODE} configuration with a custom environment variable.")
    private String awsDefaultAuthenticationChain;

    @CommandLine.Option(names = {"--duckdb-azure-authentication-mode"}, description = "The authentication mode for Azure. Supports also a ${DUCKDB_AZURE_AUTHENTICATION_MODE} configuration with a custom environment variable.")
    @JsonPropertyDescription("The authentication mode for Azure. Supports also a ${DUCKDB_AZURE_AUTHENTICATION_MODE} configuration with a custom environment variable.")
    private AzureAuthenticationMode azureAuthenticationMode;

    @CommandLine.Option(names = {"--duckdb-user"}, description = "DuckDB user name for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("DuckDB user name for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String user;

    @CommandLine.Option(names = {"--duckdb-password"}, description = "DuckDB password for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("DuckDB password for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String password;

    @CommandLine.Option(names = {"--duckdb-region"}, description = "The region for the storage credentials. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("The region for the storage credentials. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String region;

    @CommandLine.Option(names = {"--duckdb-profile"}, description = "The AWS profile used for the default authentication. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("The AWS profile used for the default authentication. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String profile;

    @CommandLine.Option(names = {"--duckdb-azure-tenant-id"}, description = "Azure Tenant ID used by DuckDB Secret Manager. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("Azure Tenant ID used by DuckDB Secret Manager. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String tenantId;

    @CommandLine.Option(names = {"--duckdb-azure-client-id"}, description = "Azure Client ID used by DuckDB Secret Manager. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("Azure Client ID used by DuckDB Secret Manager. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String clientId;

    @CommandLine.Option(names = {"--duckdb-azure-client-secret"}, description = "Azure Client Secret used by DuckDB Secret Manager. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("Azure Client Secret used by DuckDB Secret Manager. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String clientSecret;

    @CommandLine.Option(names = {"--duckdb-azure-account-name"}, description = "Azure Storage Account Name used by DuckDB Secret Manager. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("Azure Storage Account Name used by DuckDB Secret Manager. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String accountName;

    /**
     * Returns a readMode value.
     * @return readMode value.
     */
    public DuckdbReadMode getReadMode() {
        return readMode;
    }

    /**
     * Sets a readMode value.
     * @param readMode readMode value.
     */
    public void setReadMode(DuckdbReadMode readMode) {
        setDirtyIf(!Objects.equals(readMode, readMode));
        this.readMode = readMode;
    }

    /**
     * Returns a filesFormatType value.
     * @return filesFormatType value.
     */
    public DuckdbFilesFormatType getFilesFormatType() {
        return filesFormatType;
    }

    /**
     * Sets a filesFormatType value.
     * @param filesFormatType filesFormatType value.
     */
    public void setFilesFormatType(DuckdbFilesFormatType filesFormatType) {
        setDirtyIf(!Objects.equals(filesFormatType, filesFormatType));
        this.filesFormatType = filesFormatType;
    }

    /**
     * Returns a physical database name.
     * @return Physical database name.
     */
    public String getDatabase() {
        return database;
    }

    /**
     * Sets a physical database name.
     * @param database Physical database name.
     */
    public void setDatabase(String database) {
        setDirtyIf(!Objects.equals(this.database, database));
        this.database = database;
    }

    /**
     * Returns the flag to enable optimizer. By default, the optimizer is disabled.
     * @return True when the optimizer is disabled. False or null when disabled.
     */
    public Boolean getEnableOptimizer() {
        return enableOptimizer;
    }

    /**
     * Sets the flag to enable the optimizer.
     * @param enableOptimizer New flag value.
     */
    public void setEnableOptimizer(Boolean enableOptimizer) {
        setDirtyIf(!Objects.equals(this.enableOptimizer, enableOptimizer));
        this.enableOptimizer = enableOptimizer;
    }

    /**
     * Returns a key/value map of additional properties that are included in the JDBC connection string.
     * @return Key/value dictionary of additional JDBC properties.
     */
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * Sets a dictionary of additional connection parameters that are added to the JDBC connection string.
     * @param properties Key/value dictionary with extra parameters.
     */
    public void setProperties(Map<String, String> properties) {
        setDirtyIf(!Objects.equals(this.properties, properties));
        this.properties = properties != null ? Collections.unmodifiableMap(properties) : null;
    }

    /**
     * Returns the csv file format specification.
     * @return Csv file format specification.
     */
    public CsvFileFormatSpec getCsv() {
        return csv;
    }

    /**
     * Sets the csv file format specification.
     * @param csv Csv file format specification.
     */
    public void setCsv(CsvFileFormatSpec csv) {
        setDirtyIf(!Objects.equals(this.csv, csv));
        this.csv = csv;
        propagateHierarchyIdToField(csv, "csv");
    }

    /**
     * Returns the json file format specification.
     * @return Json file format specification.
     */
    public JsonFileFormatSpec getJson() {
        return json;
    }

    /**
     * Sets the json file format specification.
     * @param json Json file format specification.
     */
    public void setJson(JsonFileFormatSpec json) {
        setDirtyIf(!Objects.equals(this.json, json));
        this.json = json;
        propagateHierarchyIdToField(json, "json");
    }

    /**
     * Returns the parquet file format specification.
     * @return Parquet file format specification.
     */
    public ParquetFileFormatSpec getParquet() {
        return parquet;
    }

    /**
     * Sets the parquet file format specification.
     * @param parquet Parquet file format specification.
     */
    public void setParquet(ParquetFileFormatSpec parquet) {
        setDirtyIf(!Objects.equals(this.parquet, parquet));
        this.parquet = parquet;
        propagateHierarchyIdToField(parquet, "parquet");
    }

    /**
     * Returns the avro file format specification.
     * @return Avro file format specification.
     */
    public AvroFileFormatSpec getAvro() {
        return avro;
    }

    /**
     * Sets the avro file format specification.
     * @param avro Avro file format specification.
     */
    public void setAvro(AvroFileFormatSpec avro) {
        setDirtyIf(!Objects.equals(this.avro, avro));
        this.avro = avro;
        propagateHierarchyIdToField(avro, "avro");
    }

    /**
     * Returns the Iceberg table format specification.
     * @return Iceberg table format specification.
     */
    public IcebergFileFormatSpec getIceberg() {
        return iceberg;
    }

    /**
     * Sets the iceberg table format specification.
     * @param iceberg Iceberg table format specification.
     */
    public void setIceberg(IcebergFileFormatSpec iceberg) {
        setDirtyIf(!Objects.equals(this.iceberg, iceberg));
        this.iceberg = iceberg;
        propagateHierarchyIdToField(iceberg, "iceberg");
    }


    /**
     * Returns the Delta Lake table format specification.
     * @return Delta Lake table format specification.
     */
    public DeltaLakeFileFormatSpec getDeltaLake() {
        return deltaLake;
    }

    /**
     * Sets the Delta Lake table format specification.
     * @param deltaLake Delta Lake table format specification.
     */
    public void setDeltaLake(DeltaLakeFileFormatSpec deltaLake) {
        setDirtyIf(!Objects.equals(this.deltaLake, deltaLake));
        this.deltaLake = deltaLake;
        propagateHierarchyIdToField(deltaLake, "delta_lake");
    }

    /**
     * Returns a key/value map of schema to directory mappings
     * @return Key/value dictionary of schema to directory mappings
     */
    public Map<String, String> getDirectories() {
        return directories;
    }

    /**
     * Sets a dictionary of schema to directory mappings
     * @param directories Key/value dictionary with schema to directory mappings
     */
    public void setDirectories(Map<String, String> directories) {
        setDirtyIf(!Objects.equals(this.directories, directories));
        this.directories = directories != null ? Collections.unmodifiableMap(directories) : null;
    }

    /**
     * Returns a raw directories string containing mapping between schemas and directories
     * @return Raw directories string containing mapping between schemas and directories
     */
    public String getDirectoriesString() {
        return directoriesString;
    }

    /**
     * Sets a raw directories string containing mapping between schemas and directories
     * @param directoriesString Raw directories string containing mapping between schemas and directories
     */
    public void setDirectoriesString(String directoriesString) {
        setDirtyIf(!Objects.equals(this.directoriesString, directoriesString));
        this.directoriesString = directoriesString;
    }

    /**
     * Returns the storage type.
     * @return the storage type.
     */
    public DuckdbStorageType getStorageType() {
        return storageType;
    }

    /**
     * Sets the storage type.
     * @param storageType the storage type.
     */
    public void setStorageType(DuckdbStorageType storageType) {
        setDirtyIf(!Objects.equals(this.storageType, storageType));
        this.storageType = storageType;
    }

    /**
     * Returns the AWS's authentication mode.
     * @return AWS's authentication mode.
     */
    public AwsAuthenticationMode getAwsAuthenticationMode() {
        return awsAuthenticationMode;
    }

    /**
     * Sets AWS's authentication mode.
     * @param awsAuthenticationMode AWS's authentication mode.
     */
    public void setAwsAuthenticationMode(AwsAuthenticationMode awsAuthenticationMode) {
        setDirtyIf(!Objects.equals(this.awsAuthenticationMode, awsAuthenticationMode));
        this.awsAuthenticationMode = awsAuthenticationMode;
    }

    /**
     * Returns the AWS default authentication provider chain.
     * @return Authentication provider chain for AWS.
     */
    public String getAwsDefaultAuthenticationChain() {
        return awsDefaultAuthenticationChain;
    }

    /**
     * Sets the default AWS authentication chain.
     * @param awsDefaultAuthenticationChain Default authentication chain.
     */
    public void setAwsDefaultAuthenticationChain(String awsDefaultAuthenticationChain) {
        setDirtyIf(!Objects.equals(this.awsDefaultAuthenticationChain, awsDefaultAuthenticationChain));
        this.awsDefaultAuthenticationChain = awsDefaultAuthenticationChain;
    }

    /**
     * Returns the Azure's authentication mode.
     * @return Azure's authentication mode.
     */
    public AzureAuthenticationMode getAzureAuthenticationMode() {
        return azureAuthenticationMode;
    }

    /**
     * Sets Azure's authentication mode.
     * @param azureAuthenticationMode Azure's authentication mode.
     */
    public void setAzureAuthenticationMode(AzureAuthenticationMode azureAuthenticationMode) {
        setDirtyIf(!Objects.equals(this.azureAuthenticationMode, azureAuthenticationMode));
        this.azureAuthenticationMode = azureAuthenticationMode;
    }

    /**
     * Returns the user that is used to log in to the data source.
     * @return User name.
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets a user name.
     * @param user User name.
     */
    public void setUser(String user) {
        setDirtyIf(!Objects.equals(this.user, user));
        this.user = user;
    }

    /**
     * Returns a password.
     * @return Password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets a password.
     * @param password Password.
     */
    public void setPassword(String password) {
        setDirtyIf(!Objects.equals(this.password, password));
        this.password = password;
    }

    /**
     * Returns the region
     * @return region.
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets region.
     * @param region region.
     */
    public void setRegion(String region) {
        setDirtyIf(!Objects.equals(this.region, region));
        this.region = region;
    }

    /**
     * Returns the profile used for the default AWS authentication.
     * @return AWS profile.
     */
    public String getProfile() {
        return profile;
    }

    /**
     * Sets the AWS default profile name.
     * @param profile AWS profile name.
     */
    public void setProfile(String profile) {
        this.profile = profile;
    }

    /**
     * Returns the tenantId
     * @return tenantId.
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Sets tenantId.
     * @param tenantId tenantId.
     */
    public void setTenantId(String tenantId) {
        setDirtyIf(!Objects.equals(this.tenantId, tenantId));
        this.tenantId = tenantId;
    }

    /**
     * Returns the clientId
     * @return clientId.
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Sets clientId.
     * @param clientId clientId.
     */
    public void setClientId(String clientId) {
        setDirtyIf(!Objects.equals(this.clientId, clientId));
        this.clientId = clientId;
    }

    /**
     * Returns the clientSecret
     * @return clientSecret.
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Sets clientSecret.
     * @param clientSecret clientSecret.
     */
    public void setClientSecret(String clientSecret) {
        setDirtyIf(!Objects.equals(this.clientSecret, clientSecret));
        this.clientSecret = clientSecret;
    }

    /**
     * Returns the accountName
     * @return accountName.
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Sets accountName.
     * @param accountName accountName.
     */
    public void setAccountName(String accountName) {
        setDirtyIf(!Objects.equals(this.accountName, accountName));
        this.accountName = accountName;
    }

    /**
     * Returns the AWS AccessKeyID which is placed in user field when configured.
     * @return region.
     */
    @JsonIgnore
    public String getAwsAccessKeyId() {
        return user;
    }

    /**
     * Returns the AWS SecretAccessKey which is placed in user field when configured.
     * @return region.
     */
    @JsonIgnore
    public String getAwsSecretAccessKey() {
        return password;
    }

    /**
     * Whether the hive partitioning option is set on a file format.
     * @return Whether the hive partitioning option is set on a file format.
     */
    @JsonIgnore
    public boolean isSetHivePartitioning(){
        if(filesFormatType != null){
            switch(filesFormatType){
                case csv: return getCsv() != null && getCsv().getHivePartitioning() != null && getCsv().getHivePartitioning();
                case json: return getJson() != null && getJson().getHivePartitioning() != null && getJson().getHivePartitioning();
                case parquet: return getParquet() != null && getParquet().getHivePartitioning() != null && getParquet().getHivePartitioning();
                case avro: return false; // not supported by DuckDB
            }
        }
        return false;
    }

    /**
     * Returns the file format extension. The compression type is included when "no_extension" is not set.
     * @return the file format extension. The compression type is included when "no_extension" is not set.
     */
    @JsonIgnore
    public String getFullExtension(){
        if(filesFormatType == null) {
            return "";
        }
        String fileTypeExtension = "." + filesFormatType;

        if (filesFormatType.equals(DuckdbFilesFormatType.csv) && getCsv() != null) {
            CsvFileFormatSpec formatSpec = getCsv();

            if (formatSpec.getFileExtension() != null)
            {
                return formatSpec.getFileExtension();
            }

            if (formatSpec.getCompression() != null && (formatSpec.getNoCompressionExtension() == null || !formatSpec.getNoCompressionExtension())) {
                String compressionExtension = formatSpec.getCompression().getCompressionExtension();
                return fileTypeExtension + (compressionExtension == null ? "" : compressionExtension);
            }
        }
        if (filesFormatType.equals(DuckdbFilesFormatType.json) && getJson() != null) {
            JsonFileFormatSpec formatSpec = getJson();

            if (formatSpec.getFileExtension() != null)
            {
                return formatSpec.getFileExtension();
            }

            if (formatSpec.getCompression() != null && (formatSpec.getNoCompressionExtension() == null || !formatSpec.getNoCompressionExtension())) {
                String compressionExtension = formatSpec.getCompression().getCompressionExtension();
                return fileTypeExtension + (compressionExtension == null ? "" : compressionExtension);
            }
        }
        if (filesFormatType.equals(DuckdbFilesFormatType.parquet) && getParquet() != null) {
            ParquetFileFormatSpec formatSpec = getParquet();

            if (formatSpec.getFileExtension() != null)
            {
                return formatSpec.getFileExtension();
            }

            if (formatSpec.getCompression() != null && (formatSpec.getNoCompressionExtension() == null || !formatSpec.getNoCompressionExtension())) {
                String compressionExtension = formatSpec.getCompression().getCompressionExtension();
                return fileTypeExtension + (compressionExtension == null ? "" : compressionExtension);
            }
        }
        // avro does not support compression

        return fileTypeExtension;
    }

    /**
     * Returns state that whether the file format for the specific file type is set.
     * @return State that whether the file format for the specific file type is set.
     */
    @JsonIgnore
    public boolean isFormatSetForType(){
        if(filesFormatType == null){
            throw new RuntimeException("The file format type is not set : " + filesFormatType);
        }
        switch(filesFormatType){
            case csv: return this.getCsv() != null;
            case json: return this.getJson() != null;
            case parquet: return this.getParquet() != null;
            case avro: return this.getAvro() != null;
            case iceberg: return this.getIceberg() != null;
            case delta_lake: return this.getDeltaLake() != null;
            default: throw new RuntimeException("The file format is not supported : " + filesFormatType);
        }
    }

    /**
     * Returns scopes based on directories. Scopes are
     * @return scopes based on directories.
     */
    @JsonIgnore
    public List<String> getScopes(){
        List<String> scopes = getDirectories().values().stream().map(pathString -> {
            int wildcardIndex = pathString.indexOf("*");
            String absolutePath = wildcardIndex != -1 ? pathString.substring(0, wildcardIndex) : pathString;
            return absolutePath;
        }).collect(Collectors.toList());
        return scopes;
    }

    /**
     * Resolves storage account name for azure. It parses the connection string.
     * @return Storage account name.
     */
    @JsonIgnore
    public String resolveAccountName(){
        if(accountName != null){
            return accountName;
        }
        if(this.getAzureAuthenticationMode().equals(AzureAuthenticationMode.connection_string)){
            String connectionString = this.getPassword();
            try {
                int start = connectionString.indexOf("AccountName") + "AccountName".length() + 1;
                String accountName = connectionString.substring(start, start + connectionString.substring(start).indexOf(";"));
                return accountName;
            } catch (Exception ex){
                throw new RuntimeException("The connection string does not contain the AccountName part");
            }
        }
        return null;
    }

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    /**
     * Creates and returns a deep copy of this object.
     */
    @Override
    public DuckdbParametersSpec deepClone() {
        DuckdbParametersSpec cloned = (DuckdbParametersSpec)super.deepClone();
        return cloned;
    }

    /**
     * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
     * @param secretValueProvider Secret provider.
     * @param lookupContext Secret lookup context.
     * @return Trimmed and expanded version of this object.
     */
    public DuckdbParametersSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        DuckdbParametersSpec cloned = this.deepClone();
        cloned.database = secretValueProvider.expandValue(cloned.database, lookupContext);
        cloned.properties = secretValueProvider.expandProperties(cloned.properties, lookupContext);
        if(cloned.csv != null){
            cloned.csv = cloned.csv.expandAndTrim(secretValueProvider, lookupContext);
        }
        if(cloned.json != null){
            cloned.json = cloned.json.expandAndTrim(secretValueProvider, lookupContext);
        }
        if(cloned.avro != null){
            cloned.avro = cloned.avro.expandAndTrim(secretValueProvider, lookupContext);
        }
        if (cloned.user != null) {
            cloned.user = secretValueProvider.expandValue(cloned.user, lookupContext);
        }
        if (cloned.password != null) {
            cloned.password = secretValueProvider.expandValue(cloned.password, lookupContext);
        }
        if (cloned.region != null) {
            cloned.region = secretValueProvider.expandValue(cloned.region, lookupContext);
        }
        if (cloned.profile != null) {
            cloned.profile = secretValueProvider.expandValue(cloned.profile, lookupContext);
        }

        if (cloned.tenantId != null) {
            cloned.tenantId = secretValueProvider.expandValue(cloned.tenantId, lookupContext);
        }
        if (cloned.clientId != null) {
            cloned.clientId = secretValueProvider.expandValue(cloned.clientId, lookupContext);
        }
        if (cloned.clientSecret != null) {
            cloned.clientSecret = secretValueProvider.expandValue(cloned.clientSecret, lookupContext);
        }
        if (cloned.accountName != null) {
            cloned.accountName = secretValueProvider.expandValue(cloned.accountName, lookupContext);
        }
        if (cloned.awsDefaultAuthenticationChain != null) {
            cloned.awsDefaultAuthenticationChain = secretValueProvider.expandValue(cloned.awsDefaultAuthenticationChain, lookupContext);
        }

        return cloned;
    }

    /**
     * Fills the spec with the default credentials from the .credentials/AWS_default_credentials file for a cloud storage.
     *
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that could be used in the connection names.
     */
    public void fillSpecWithDefaultAwsCredentials(SecretValueLookupContext secretValueLookupContext){
        Optional<Profile> credentialProfile = AwsDefaultCredentialProfileProvider.provideProfile(secretValueLookupContext, this.getProfile());
        if (credentialProfile.isPresent()) {
            Optional<String> accessKeyId = credentialProfile.get().property(AwsCredentialProfileSettingNames.AWS_ACCESS_KEY_ID);
            if (accessKeyId.isPresent()) {
                String awsAccessKeyId = accessKeyId.get();
                if (!Objects.equals(accessKeyId, "PLEASE_REPLACE_WITH_YOUR_AWS_ACCESS_KEY_ID")) {
                    this.setUser(awsAccessKeyId);
                }
            }
            Optional<String> secretAccessKey = credentialProfile.get().property(AwsCredentialProfileSettingNames.AWS_SECRET_ACCESS_KEY);
            if (secretAccessKey.isPresent()) {
                String awsSecretAccessKey = secretAccessKey.get();
                if (!Objects.equals(awsSecretAccessKey, "PLEASE_REPLACE_WITH_YOUR_AWS_SECRET_ACCESS_KEY")) {
                    this.setPassword(awsSecretAccessKey);
                }
            }
        }
        fillSpecWithDefaultAwsConfig(secretValueLookupContext);
    }

    /**
     * Fills the spec with the default credentials from the .credentials/Azure_default_credentials file for a cloud storage.
     *
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that could be used in the connection names.
     */
    public void fillSpecWithDefaultAzureCredentials(SecretValueLookupContext secretValueLookupContext,
                                                  AzureCredentialsProvider azureCredentialsProvider){
        Optional<AzureCredential> azureCredential = azureCredentialsProvider.provideCredentials(secretValueLookupContext);
        if(azureCredential.isPresent()
            && !azureCredential.get().getTenantId().isEmpty()
            && !azureCredential.get().getClientId().isEmpty()
            && !azureCredential.get().getClientSecret().isEmpty()
            && !azureCredential.get().getAccountName().isEmpty()
        ) {
            this.setTenantId(azureCredential.get().getTenantId());
            this.setClientId(azureCredential.get().getClientId());
            this.setClientSecret(azureCredential.get().getClientSecret());
            this.setAccountName(azureCredential.get().getAccountName());
        }
    }

    /**
     * Fills the spec with the default AWS config.
     *
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that could be used in the connection names.
     */
    public void fillSpecWithDefaultAwsConfig(SecretValueLookupContext secretValueLookupContext){
        Optional<Profile> configProfile = AwsDefaultConfigProfileProvider.provideProfile(secretValueLookupContext, this.getProfile());
        if(configProfile.isPresent()){
            Optional<String> region = configProfile.get().property(AwsConfigProfileSettingNames.REGION);
            if(region.isPresent()){
                String awsRegion = region.get();
                this.setRegion(awsRegion);
            }
        }
    }

}
