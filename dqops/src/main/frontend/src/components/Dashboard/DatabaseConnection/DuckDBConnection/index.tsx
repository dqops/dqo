import React, { useEffect, useState } from 'react';

import { cloneDeep } from 'lodash';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import {
  DuckdbParametersSpec,
  DuckdbParametersSpecAwsAuthenticationModeEnum,
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
  }
  // todo: uncomment below when implemented
  // {
  //   label: 'Google Cloud Storage',
  //   value: DuckdbParametersSpecStorageTypeEnum.gcs
  // },
  // {
  //   label: 'Azure Blob Storage',
  //   value: DuckdbParametersSpecStorageTypeEnum.azure
  // },
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

const DuckdbConnection = ({
  duckdb,
  onChange,
  sharedCredentials,
  freezeFileType = false
}: IDuckdbConnectionProps) => {
  // const handleChange = (obj: Partial<DuckdbParametersSpec>) => {
  //   if (!onChange) return;
  //   onChange({
  //     ...duckdb,
  //     ...obj
  //   });
  // };

  const [copiedDatabase, setCopiedDatabase] = useState<DuckdbParametersSpec>(
    cloneDeep(duckdb) ?? {}
  );
  const { checkTypes }: { checkTypes: CheckTypes } = useParams();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [fileFormatType, setFileFormatType] = useState<
    keyof DuckdbParametersSpec
  >(duckdb?.files_format_type ?? DuckdbParametersSpecFilesFormatTypeEnum.csv);
  const [refetchDirectoriesIndicator, setRefetchDirectoriesIndicator] =
    useState(false);

  const onChangeConfiguration = (params: Partial<TConfiguration>) => {
    setCopiedDatabase((prev) => ({
      ...prev,
      [fileFormatType]: {
        ...(prev?.[fileFormatType] as TConfiguration),
        ...params
      }
    }));
  };
  const cleanConfiguration = () => {};

  const onChangeFile = (val: DuckdbParametersSpecFilesFormatTypeEnum) => {
    setCopiedDatabase((prev) => ({
      ...prev,
      [fileFormatType as keyof DuckdbParametersSpec]: undefined,
      [val as keyof DuckdbParametersSpec]: {},
      files_format_type: val
    }));
    setFileFormatType(val);
    cleanConfiguration();
  };

  useEffect(() => {
    onChange(cloneDeep(copiedDatabase) ?? {});
  }, [copiedDatabase]);

  useEffect(() => {
    setCopiedDatabase(cloneDeep(duckdb) ?? {});
    setRefetchDirectoriesIndicator((prev) => !prev);
  }, [firstLevelActiveTab]);

  const changeStorageTypeDirectoryPrefixes = (
    storage_type: DuckdbParametersSpecStorageTypeEnum
  ) => {
    const directories = { ...copiedDatabase?.directories };

    Object.keys(directories ?? {}).forEach((key) => {
      if (directories[key].length && directories[key] !== 's3://') {
        return;
      }
      if (storage_type === DuckdbParametersSpecStorageTypeEnum.s3) {
        directories[key] = 's3://';
      } else {
        directories[key] = '';
      }
    });
    setCopiedDatabase((prev) => ({ ...prev, directories, storage_type }));
  };
  console.log(copiedDatabase, duckdb);
  return (
    <SectionWrapper
      title="DuckDB connection parameters"
      className="mb-4 text-sm"
    >
      <Select
        label="Files location"
        options={storageTypeOptions}
        className="mb-4 text-sm"
        value={copiedDatabase?.storage_type}
        onChange={changeStorageTypeDirectoryPrefixes}
      />

      {copiedDatabase?.storage_type ===
        DuckdbParametersSpecStorageTypeEnum.s3 && (
        <Select
          label="AWS authentication mode"
          options={awsAuthenticationOptions}
          className="mb-4"
          value={copiedDatabase?.aws_authentication_mode}
          onChange={(value) => {
            setCopiedDatabase((prev) => ({
              ...prev,
              aws_authentication_mode: value
            }));
          }}
        />
      )}

      {copiedDatabase?.storage_type ===
        DuckdbParametersSpecStorageTypeEnum.s3 &&
        copiedDatabase?.aws_authentication_mode ===
          DuckdbParametersSpecAwsAuthenticationModeEnum.iam && (
          <>
            <FieldTypeInput
              data={sharedCredentials}
              label="Access Key ID"
              className="mb-4 text-sm"
              value={copiedDatabase?.user}
              onChange={(value) =>
                setCopiedDatabase((prev) => ({ ...prev, user: value }))
              }
            />
            <FieldTypeInput
              data={sharedCredentials}
              label="Secret Access Key"
              className="mb-4 text-sm"
              maskingType="password"
              value={copiedDatabase?.password}
              onChange={(value) =>
                setCopiedDatabase((prev) => ({ ...prev, password: value }))
              }
            />
            <FieldTypeInput
              data={sharedCredentials}
              label="Region"
              className="mb-4 text-sm"
              maskingType="region"
              value={copiedDatabase?.region}
              onChange={(value) =>
                setCopiedDatabase((prev) => ({ ...prev, region: value }))
              }
            />
          </>
        )}

      {copiedDatabase?.storage_type ===
        DuckdbParametersSpecStorageTypeEnum.s3 &&
        copiedDatabase?.aws_authentication_mode ===
          DuckdbParametersSpecAwsAuthenticationModeEnum.default_credentials && (
          <>
            <FieldTypeInput
              data={sharedCredentials}
              label="Region"
              className="mb-4 text-sm"
              maskingType="region"
              value={copiedDatabase?.region}
              placeholder={
                duckdb?.aws_authentication_mode ===
                DuckdbParametersSpecAwsAuthenticationModeEnum.default_credentials
                  ? 'Use the value from the ".credentials/AWS_default_config" DQOps shared credential file'
                  : ''
              }
              onChange={(value) =>
                setCopiedDatabase((prev) => ({ ...prev, region: value }))
              }
            />
          </>
        )}

      <FileFormatConfiguration
        fileFormatType={
          fileFormatType as DuckdbParametersSpecFilesFormatTypeEnum
        }
        onChangeFile={onChangeFile}
        configuration={
          copiedDatabase?.[
            fileFormatType as keyof DuckdbParametersSpec
          ] as TConfiguration
        }
        onChangeConfiguration={onChangeConfiguration}
        cleanConfiguration={cleanConfiguration}
        freezeFileType={freezeFileType}
      >
        <KeyValueProperties
          properties={copiedDatabase?.directories}
          onChange={(directories) => {
            setCopiedDatabase((prev) => ({
              ...prev,
              directories: directories
            }));
          }}
          sharedCredentials={sharedCredentials}
          refetchDirectoriesIndicator={refetchDirectoriesIndicator}
        />
      </FileFormatConfiguration>
      <JdbcPropertiesView
        properties={copiedDatabase?.properties}
        onChange={(properties) =>
          setCopiedDatabase((prev) => ({ ...prev, properties }))
        }
        sharedCredentials={sharedCredentials}
      />
    </SectionWrapper>
  );
};

export default DuckdbConnection;
