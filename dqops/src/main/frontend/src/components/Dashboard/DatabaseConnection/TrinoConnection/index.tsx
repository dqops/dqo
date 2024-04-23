import React from 'react';

import SectionWrapper from '../../SectionWrapper';
import {
  TrinoParametersSpec,
  SharedCredentialListModel,
  TrinoParametersSpecTrinoEngineTypeEnum,
  TrinoParametersSpecAwsAuthenticationModeEnum
} from '../../../../api';
import JdbcPropertiesView from '../JdbcProperties';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';
import Select from '../../../Select';
import clsx from 'clsx';

interface ITrinoConnectionProps {
  trino?: TrinoParametersSpec;
  onChange?: (obj: TrinoParametersSpec) => void;
  sharedCredentials?: SharedCredentialListModel[];
  nameOfDatabase?: string;
  onNameOfDatabaseChange?: (name: string) => void;
}

const options = [
  {
    label: 'Trino',
    value: TrinoParametersSpecTrinoEngineTypeEnum.trino
  },
  {
    label: 'AWS Athena',
    value: TrinoParametersSpecTrinoEngineTypeEnum.athena
  }
];

const athenaAuthenticationOptions = [
  {
    label: 'IAM',
    value: TrinoParametersSpecAwsAuthenticationModeEnum.iam
  },
  {
    label: 'Default Credentials',
    value: TrinoParametersSpecAwsAuthenticationModeEnum.default_credentials
  }
];

const TrinoConnection = ({
  trino,
  onChange,
  sharedCredentials,
  nameOfDatabase,
  onNameOfDatabaseChange
}: ITrinoConnectionProps) => {
  const handleChange = (obj: Partial<TrinoParametersSpec>) => {
    if (!onChange) return;
    onChange({
      ...trino,
      ...obj
    });
  };

  return (
    <SectionWrapper title="Trino connection parameters" className="mb-4">
      <Select
        label="Trino engine type"
        options={options}
        className={clsx(
          'mb-4',
          !trino?.trino_engine_type ? 'border border-red-500' : ''
        )}
        value={
          trino?.trino_engine_type ||
          (nameOfDatabase &&
            (nameOfDatabase.toLowerCase() as TrinoParametersSpecTrinoEngineTypeEnum))
        }
        onChange={(value) => {
          handleChange({
            trino_engine_type: value,
            catalog:
              value === TrinoParametersSpecTrinoEngineTypeEnum.trino
                ? ''
                : 'awsdatacatalog'
          }),
            value &&
              onNameOfDatabaseChange &&
              onNameOfDatabaseChange(
                String(value).replace(/\w/, (x) => x.toUpperCase())
              );
        }}
      />
      {trino?.trino_engine_type ===
        TrinoParametersSpecTrinoEngineTypeEnum.trino && (
        <>
          <FieldTypeInput
            data={sharedCredentials}
            label="Host"
            className="mb-4"
            value={trino?.host}
            onChange={(value) => handleChange({ host: value })}
            inputClassName={!trino?.host ? 'border border-red-500' : ''}
          />
          <FieldTypeInput
            data={sharedCredentials}
            label="Port"
            className="mb-4"
            value={trino?.port}
            onChange={(value) => handleChange({ port: value })}
          />
          <FieldTypeInput
            data={sharedCredentials}
            label="Database"
            className="mb-4"
            value={trino?.database}
            onChange={(value) => handleChange({ database: value })}
          />
          <FieldTypeInput
            data={sharedCredentials}
            label="User name"
            className="mb-4"
            value={trino?.user}
            onChange={(value) => handleChange({ user: value })}
          />
        </>
      )}
      {trino?.trino_engine_type ===
        TrinoParametersSpecTrinoEngineTypeEnum.athena && (
        <>
          <Select
            label="Athena authentication option"
            options={athenaAuthenticationOptions}
            className="mb-4"
            value={trino?.aws_authentication_mode}
            onChange={(value) => {
              handleChange({ aws_authentication_mode: value });
            }}
          />
          {trino?.aws_authentication_mode ===
            TrinoParametersSpecAwsAuthenticationModeEnum.iam && (
            <>
              <FieldTypeInput
                data={sharedCredentials}
                label="AWS AccessKeyId"
                className="mb-4"
                value={trino?.user}
                onChange={(value) => handleChange({ user: value })}
              />
              <FieldTypeInput
                data={sharedCredentials}
                label="AWS SecretAccessKey"
                className="mb-4"
                value={trino?.password}
                onChange={(value) => handleChange({ password: value })}
              />
            </>
          )}
          <FieldTypeInput
            data={sharedCredentials}
            label="Athena Region"
            className="mb-4"
            value={trino?.athena_region}
            placeholder={trino?.aws_authentication_mode === 
              TrinoParametersSpecAwsAuthenticationModeEnum.default_credentials ? 'Use the value from the ".credentials/AWS_default_config" DQOps shared credential file' : ''}
            onChange={(value) => handleChange({ athena_region: value })}
          />
          <FieldTypeInput
            data={sharedCredentials}
            label="Athena WorkGroup"
            className="mb-4"
            value={trino?.athena_work_group}
            onChange={(value) => handleChange({ athena_work_group: value })}
          />
          <FieldTypeInput
            data={sharedCredentials}
            label="Athena OutputLocation"
            className="mb-4"
            value={trino?.athena_output_location}
            onChange={(value) =>
              handleChange({ athena_output_location: value })
            }
          />
        </>
      )}
      <FieldTypeInput
        data={sharedCredentials}
        label="Catalog"
        className="mb-4"
        value={trino?.catalog}
        onChange={(value) => handleChange({ catalog: value })}
      />
      <JdbcPropertiesView
        properties={trino?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </SectionWrapper>
  );
};

export default TrinoConnection;
