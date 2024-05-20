import React from 'react';

import SectionWrapper from '../../SectionWrapper';
import {
  SharedCredentialListModel,
  SqlServerParametersSpec,
  SqlServerParametersSpecAuthenticationModeEnum
} from '../../../../api';
import JdbcPropertiesView from '../JdbcProperties';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';
import Checkbox from '../../../Checkbox';
import Select from '../../../Select';

interface ISqlServerConnectionProps {
  sqlserver?: SqlServerParametersSpec;
  onChange?: (obj: SqlServerParametersSpec) => void;
  sharedCredentials?: SharedCredentialListModel[];
}

const authenticationModes = [
  {
    label: 'SQL Password',
    value: SqlServerParametersSpecAuthenticationModeEnum.sql_password
  },
  {
    label: 'Active Directory Password',
    value:
      SqlServerParametersSpecAuthenticationModeEnum.active_directory_password
  },
  // service principal is not supported yet
//   {
//     label: 'Active Directory Service Principal',
//     value:
//       SqlServerParametersSpecAuthenticationModeEnum.active_directory_service_principal
//   },
  {
    label: 'Active Directory Default',
    value: SqlServerParametersSpecAuthenticationModeEnum.active_directory_default
  }
];

const SqlServerConnection = ({
  sqlserver,
  onChange,
  sharedCredentials
}: ISqlServerConnectionProps) => {
  const handleChange = (obj: Partial<SqlServerParametersSpec>) => {
    if (!onChange) return;

    onChange({
      ...sqlserver,
      ...obj
    });
  };

  return (
    <SectionWrapper
      title="Microsoft SQL Server/SQL Server connection parameters"
      className="mb-4"
    >
      <FieldTypeInput
        data={sharedCredentials}
        label="Host"
        className="mb-4"
        value={sqlserver?.host}
        onChange={(value) => handleChange({ host: value })}
        inputClassName={!sqlserver?.host ? 'border border-red-500' : ''}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Port"
        className="mb-4"
        value={sqlserver?.port}
        onChange={(value) => handleChange({ port: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Database"
        className="mb-4"
        value={sqlserver?.database}
        onChange={(value) => handleChange({ database: value })}
      />

      <Select
        label="SQL Server authentication method"
        options={authenticationModes}
        className="mb-4"
        value={sqlserver?.authentication_mode}
        onChange={(value) => {
          handleChange({ authentication_mode: value });
        }}
      />

      {sqlserver?.authentication_mode !==
        SqlServerParametersSpecAuthenticationModeEnum.active_directory_default && (
        <>
          <FieldTypeInput
            data={sharedCredentials}
            label="User name"
            className="mb-4"
            value={sqlserver?.user}
            onChange={(value) => handleChange({ user: value })}
          />
          <FieldTypeInput
            data={sharedCredentials}
            label="Password"
            className="mb-4"
            maskingType="password"
            value={sqlserver?.password}
            onChange={(value) => handleChange({ password: value })}
          />
        </>
      )}

      <Checkbox
        checked={sqlserver?.disable_encryption}
        onChange={(checked) => handleChange({ disable_encryption: checked })}
        label="Disable encryption"
        labelPosition="left"
      />
      <JdbcPropertiesView
        properties={sqlserver?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </SectionWrapper>
  );
};

export default SqlServerConnection;
