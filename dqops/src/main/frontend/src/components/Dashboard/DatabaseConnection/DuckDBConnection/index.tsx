import React, { useState } from 'react';

import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import {
  DuckdbParametersSpec,
  DuckdbParametersSpecAwsAuthenticationModeEnum,
  DuckdbParametersSpecAzureAuthenticationModeEnum,
  DuckdbParametersSpecFilesFormatTypeEnum,
  DuckdbParametersSpecStorageTypeEnum,
  SharedCredentialListModel
} from '../../../../api';
import { TConfiguration } from '../../../../components/FileFormatConfiguration/TConfiguration';
import { getFirstLevelActiveTab } from '../../../../redux/selectors';
import { CheckTypes } from '../../../../shared/routes';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';
import FileFormatConfiguration from '../../../FileFormatConfiguration/FileFormatConfiguration';
import KeyValueProperties from '../../../FileFormatConfiguration/KeyValueProperties';
import Select from '../../../Select';
import SectionWrapper from '../../SectionWrapper';
import JdbcPropertiesView from '../JdbcProperties';

interface IDuckdbConnectionProps {
  duckdb?: DuckdbParametersSpec;
  onChange: (obj: DuckdbParametersSpec) => void;
  sharedCredentials?: SharedCredentialListModel[];
  freezeFileType?: boolean;
}

const storageTypeOptions = [
  {
    label: 'Local files',
    value: DuckdbParametersSpecStorageTypeEnum.local
  },
  {
    label: 'AWS S3',
    value: DuckdbParametersSpecStorageTypeEnum.s3
  },
  {
    label: 'Azure Blob Storage',
    value: DuckdbParametersSpecStorageTypeEnum.azure
  },
  {
    label: 'Google Cloud Storage',
    value: DuckdbParametersSpecStorageTypeEnum.gcs
  }
  // todo: uncomment below when implemented
  // {
  //   label: 'Cloudflare R2',
  //   value: DuckdbParametersSpecStorageTypeEnum.r2
  // }

  // remember to declare constans outside the component.
];

const awsAuthenticationOptions = [
  {
    label: 'IAM',
    value: DuckdbParametersSpecAwsAuthenticationModeEnum.iam
  },
  {
    label: 'Default Credentials',
    value: DuckdbParametersSpecAwsAuthenticationModeEnum.default_credentials
  }
];

const azureAuthenticationOptions = [
  {
    label: 'Connection String',
    value: DuckdbParametersSpecAzureAuthenticationModeEnum.connection_string
  },
  {
    label: 'Credential Chain',
    value: DuckdbParametersSpecAzureAuthenticationModeEnum.credential_chain
  },
  {
    label: 'Service Principal',
    value: DuckdbParametersSpecAzureAuthenticationModeEnum.service_principal
  },
  {
    label: 'Default Credentials',
    value: DuckdbParametersSpecAzureAuthenticationModeEnum.default_credentials
  }
];

enum StoragePrefixes {
  S3 = 's3://',
  AZ = 'az://',
  GCS = 'gs://'
}

const DuckdbConnection = ({
  duckdb,
  onChange,
  sharedCredentials,
  freezeFileType = false
}: IDuckdbConnectionProps) => {
  const { checkTypes }: { checkTypes: CheckTypes } = useParams();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [fileFormatType, setFileFormatType] = useState<
    keyof DuckdbParametersSpec
  >(duckdb?.files_format_type ?? DuckdbParametersSpecFilesFormatTypeEnum.csv);
  const [refetchDirectoriesIndicator, setRefetchDirectoriesIndicator] =
    useState(false);
  const [selectedInput, setSelectedInput] = useState<number | string>();
  // const [duckdb, setduckdb] = useState<DuckdbParametersSpec>(
  //   duckdb ??{}
  // );
  const handleChange = (obj: Partial<DuckdbParametersSpec>) => {
    onChange({
      ...duckdb,
      ...obj
    });
  };
  const onChangeConfiguration = (params: Partial<TConfiguration>) => {
    handleChange({
      [fileFormatType]: {
        ...(duckdb?.[fileFormatType] as TConfiguration),
        ...params
      }
    });
  };
  const cleanConfiguration = () => {};

  const onChangeFile = (val: DuckdbParametersSpecFilesFormatTypeEnum) => {
    handleChange({
      [fileFormatType as keyof DuckdbParametersSpec]: undefined,
      [val as keyof DuckdbParametersSpec]: {},
      files_format_type: val
    });
    setFileFormatType(val);
    cleanConfiguration();
  };

  // useEffect(() => {
  //   setduckdb(cloneDeep(duckdb) ?? {});
  //   setRefetchDirectoriesIndicator((prev) => !prev);
  // }, [firstLevelActiveTab]);

  const changeStorageTypeDirectoryPrefixes = (
    storage_type: DuckdbParametersSpecStorageTypeEnum
  ) => {
    const directories = { ...duckdb?.directories };

    Object.keys(directories ?? {}).forEach((key) => {
      if (
        directories[key].length &&
        directories[key] !== StoragePrefixes.S3 &&
        directories[key] !== StoragePrefixes.AZ &&
        directories[key] !== StoragePrefixes.GCS
      ) {
        return;
      }

      if (storage_type === DuckdbParametersSpecStorageTypeEnum.s3) {
        directories[key] = StoragePrefixes.S3;
      } else if (storage_type === DuckdbParametersSpecStorageTypeEnum.azure) {
        directories[key] = StoragePrefixes.AZ;
      } else if (storage_type === DuckdbParametersSpecStorageTypeEnum.gcs) {
        directories[key] = StoragePrefixes.GCS;
      } else {
        directories[key] = '';
      }
    });
    handleChange({ directories, storage_type });
  };

  const awsStorageForm = (): JSX.Element => {
    return (
      <>
        <Select
          label="AWS authentication mode"
          options={awsAuthenticationOptions}
          className="mb-4"
          value={duckdb?.aws_authentication_mode}
          onChange={(value) => {
            handleChange({
              aws_authentication_mode: value
            });
          }}
          onClickValue={setSelectedInput}
          selectedMenu={selectedInput}
          menuClassName="!top-14"
        />

        {duckdb?.aws_authentication_mode ===
          DuckdbParametersSpecAwsAuthenticationModeEnum.iam && (
          <>
            <FieldTypeInput
              data={sharedCredentials}
              label="Access Key ID"
              className="mb-4 text-sm"
              value={duckdb?.user}
              onChange={(value) => handleChange({ user: value })}
            />
            <FieldTypeInput
              data={sharedCredentials}
              label="Secret Access Key"
              className="mb-4 text-sm"
              maskingType="password"
              value={duckdb?.password}
              onChange={(value) => handleChange({ password: value })}
            />
            <FieldTypeInput
              data={sharedCredentials}
              label="Region"
              className="mb-4 text-sm"
              maskingType="region"
              value={duckdb?.region}
              onChange={(value) => handleChange({ region: value })}
            />
          </>
        )}

        {duckdb?.aws_authentication_mode ===
          DuckdbParametersSpecAwsAuthenticationModeEnum.default_credentials && (
          <FieldTypeInput
            data={sharedCredentials}
            label="Region"
            className="mb-4 text-sm"
            maskingType="region"
            value={duckdb?.region}
            placeholder={
              duckdb?.aws_authentication_mode ===
              DuckdbParametersSpecAwsAuthenticationModeEnum.default_credentials
                ? 'Use the value from the ".credentials/AWS_default_config" DQOps shared credential file'
                : ''
            }
            onChange={(value) => handleChange({ region: value })}
          />
        )}
      </>
    );
  };

  const azureStorageForm = (): JSX.Element => {
    return (
      <>
        <Select
          label="Azure authentication mode"
          options={azureAuthenticationOptions}
          className="mb-4"
          value={duckdb?.azure_authentication_mode}
          onChange={(value) => {
            handleChange({ azure_authentication_mode: value });
          }}
          onClickValue={setSelectedInput}
          selectedMenu={selectedInput}
        />

        {duckdb?.azure_authentication_mode ===
          DuckdbParametersSpecAzureAuthenticationModeEnum.connection_string && (
          <>
            <FieldTypeInput
              data={sharedCredentials}
              label="Connection string"
              className="mb-4 text-sm"
              maskingType="password"
              value={duckdb?.password}
              onChange={(value) => handleChange({ password: value })}
            />
          </>
        )}

        {duckdb?.azure_authentication_mode ===
          DuckdbParametersSpecAzureAuthenticationModeEnum.credential_chain && (
          <>
            <FieldTypeInput
              data={sharedCredentials}
              label="Storage account name"
              className="mb-4 text-sm"
              value={duckdb?.account_name}
              onChange={(value) => handleChange({ account_name: value })}
            />
          </>
        )}

        {duckdb?.azure_authentication_mode ===
          DuckdbParametersSpecAzureAuthenticationModeEnum.service_principal && (
          <>
            <FieldTypeInput
              data={sharedCredentials}
              label="Tenant ID"
              className="mb-4 text-sm"
              value={duckdb?.tenant_id}
              onChange={(value) => handleChange({ tenant_id: value })}
            />
            <FieldTypeInput
              data={sharedCredentials}
              label="Client ID"
              className="mb-4 text-sm"
              value={duckdb?.client_id}
              onChange={(value) => handleChange({ client_id: value })}
            />
            <FieldTypeInput
              data={sharedCredentials}
              label="Client Secret"
              className="mb-4 text-sm"
              maskingType="password"
              value={duckdb?.client_secret}
              onChange={(value) => handleChange({ client_secret: value })}
            />
            <FieldTypeInput
              data={sharedCredentials}
              label="Storage account name"
              className="mb-4 text-sm"
              value={duckdb?.account_name}
              onChange={(value) => handleChange({ account_name: value })}
            />
          </>
        )}
      </>
    );
  };

  const googleStorageForm = (): JSX.Element => {
    return (
      <>
        <FieldTypeInput
          data={sharedCredentials}
          label="Access Key"
          className="mb-4 text-sm"
          value={duckdb?.user}
          onChange={(value) => handleChange({ user: value })}
        />
        <FieldTypeInput
          data={sharedCredentials}
          label="Secret"
          className="mb-4 text-sm"
          maskingType="password"
          value={duckdb?.password}
          onChange={(value) => handleChange({ password: value })}
        />
      </>
    );
  };

  return (
    <SectionWrapper
      title="DuckDB connection parameters"
      className="mb-4 text-sm"
    >
      <Select
        label="Files location"
        options={storageTypeOptions}
        className="mb-4 text-sm"
        value={duckdb?.storage_type}
        onChange={changeStorageTypeDirectoryPrefixes}
        onClickValue={setSelectedInput}
        selectedMenu={selectedInput}
        menuClassName="!top-14"
      />

      {duckdb?.storage_type === DuckdbParametersSpecStorageTypeEnum.s3 &&
        awsStorageForm()}

      {duckdb?.storage_type === DuckdbParametersSpecStorageTypeEnum.azure &&
        azureStorageForm()}

      {duckdb?.storage_type === DuckdbParametersSpecStorageTypeEnum.gcs &&
        googleStorageForm()}

      <FileFormatConfiguration
        fileFormatType={
          fileFormatType as DuckdbParametersSpecFilesFormatTypeEnum
        }
        onChangeFile={onChangeFile}
        configuration={
          duckdb?.[
            fileFormatType as keyof DuckdbParametersSpec
          ] as TConfiguration
        }
        onChangeConfiguration={onChangeConfiguration}
        cleanConfiguration={cleanConfiguration}
        freezeFileType={freezeFileType}
      >
        <KeyValueProperties
          properties={duckdb?.directories}
          onChange={(directories) => {
            handleChange({ directories });
          }}
          sharedCredentials={sharedCredentials}
          storageType={duckdb?.storage_type}
          refetchDirectoriesIndicator={refetchDirectoriesIndicator}
        />
      </FileFormatConfiguration>
      <JdbcPropertiesView
        properties={duckdb?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </SectionWrapper>
  );
};

export default DuckdbConnection;
