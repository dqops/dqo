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
package com.dqops.connectors.duckdb;

import com.dqops.connectors.ConnectionProviderSpecificParameters;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.sources.BaseProviderParametersSpec;
import com.dqops.metadata.sources.fileformat.CsvFileFormatSpec;
import com.dqops.metadata.sources.fileformat.JsonFileFormatSpec;
import com.dqops.metadata.sources.fileformat.ParquetFileFormatSpec;
import com.dqops.metadata.storage.localfiles.credentials.aws.AwsConfigProfileSettingNames;
import com.dqops.metadata.storage.localfiles.credentials.aws.AwsCredentialProfileSettingNames;
import com.dqops.metadata.storage.localfiles.credentials.aws.AwsDefaultConfigProfileProvider;
import com.dqops.metadata.storage.localfiles.credentials.aws.AwsDefaultCredentialProfileProvider;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import org.apache.parquet.Strings;
import picocli.CommandLine;
import software.amazon.awssdk.profiles.Profile;

import java.util.*;

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
        }
    };

    @CommandLine.Option(names = {"--duckdb-read-mode"}, description = "DuckDB read mode.")
    @JsonPropertyDescription("DuckDB read mode.")
    private DuckdbReadMode readMode = DuckdbReadMode.files;

    @CommandLine.Option(names = {"--duckdb-files-format-type"}, description = "Type of source files format for DuckDB.")
    @JsonPropertyDescription("Type of source files format for DuckDB.")
    private DuckdbFilesFormatType filesFormatType;

    @CommandLine.Option(names = {"--duckdb-database"}, description = "DuckDB database name for in-memory read mode. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("DuckDB database name for in-memory read mode. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String database;

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

    @CommandLine.Option(names = "--duckdb-directories", split = ",")
    @JsonPropertyDescription("Virtual schema name to directory mappings. The path must be an absolute path.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> directories = new HashMap<>();

    @CommandLine.Option(names = {"--duckdb-storage-type"}, description = "The storage type.")
    @JsonPropertyDescription("The storage type.")
    private DuckdbStorageType storageType;

    @CommandLine.Option(names = {"--duckdb-user"}, description = "DuckDB user name for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("DuckDB user name for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String user;

    @CommandLine.Option(names = {"--duckdb-password"}, description = "DuckDB password for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("DuckDB password for a remote storage type. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String password;

    @CommandLine.Option(names = {"--duckdb-region"}, description = "The region for the storage credentials. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("The region for the storage credentials. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String region;

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
            }
        }
        return false;
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
            default: throw new RuntimeException("The file format is not supported : " + filesFormatType);
        }
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
        cloned.user = secretValueProvider.expandValue(cloned.user, lookupContext);
        cloned.password = secretValueProvider.expandValue(cloned.password, lookupContext);
        cloned.region = secretValueProvider.expandValue(cloned.region, lookupContext);

        return cloned;
    }

    /**
     * Fills the spec with the default credentials (when not set) for a cloud storage when any cloud storage is used.
     *
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that could be used in the connection names.
     */
    public void fillSpecWithDefaultCredentials(SecretValueLookupContext secretValueLookupContext){
        DuckdbStorageType storageType = this.getStorageType();

        switch (storageType){
            case s3:
                if(Strings.isNullOrEmpty(this.getAwsAccessKeyId()) || Strings.isNullOrEmpty(this.getAwsSecretAccessKey())){
                    Optional<Profile> credentialProfile = AwsDefaultCredentialProfileProvider.provideProfile(secretValueLookupContext);
                    if(credentialProfile.isPresent()){
                        Optional<String> accessKeyId = credentialProfile.get().property(AwsCredentialProfileSettingNames.AWS_ACCESS_KEY_ID);
                        if(!Strings.isNullOrEmpty(this.getAwsAccessKeyId()) && accessKeyId.isPresent()){
                            String awsAccessKeyId = accessKeyId.get();
                            this.setUser(awsAccessKeyId);
                        }
                        Optional<String> secretAccessKey = credentialProfile.get().property(AwsCredentialProfileSettingNames.AWS_SECRET_ACCESS_KEY);
                        if(!Strings.isNullOrEmpty(this.getAwsSecretAccessKey()) && secretAccessKey.isPresent()){
                            String awsSecretAccessKey = secretAccessKey.get();
                            this.setPassword(awsSecretAccessKey);
                        }
                    }
                }

                if(Strings.isNullOrEmpty(this.getUser())){
                    Optional<Profile> configProfile = AwsDefaultConfigProfileProvider.provideProfile(secretValueLookupContext);
                    if(configProfile.isPresent()){
                        Optional<String> region = configProfile.get().property(AwsConfigProfileSettingNames.REGION);
                        if(!Strings.isNullOrEmpty(this.getRegion()) && region.isPresent()){
                            String awsRegion = region.get();
                            this.setRegion(awsRegion);
                        }
                    }
                }

                break;
            default:
                throw new RuntimeException("This type of DuckdbSecretsType is not supported: " + storageType);
        }
    }

}
