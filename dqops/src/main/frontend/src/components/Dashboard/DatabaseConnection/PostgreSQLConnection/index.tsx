import React from 'react';

import {
  PostgresqlParametersSpec,
  PostgresqlParametersSpecSslmodeEnum,
  SharedCredentialListModel
} from '../../../../api';
import JdbcPropertiesView from '../JdbcProperties';
import Select from '../../../Select';
import SectionWrapper from '../../SectionWrapper';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';

interface IPostgreSQLConnectionProps {
  postgresql?: PostgresqlParametersSpec;
  onChange?: (obj: PostgresqlParametersSpec) => void;
  sharedCredentials?: SharedCredentialListModel[];
}

const sslModes = [
  {
    label: ''
  },
  {
    label: 'disable',
    value: PostgresqlParametersSpecSslmodeEnum.disable
  },
  {
    label: 'allow',
    value: PostgresqlParametersSpecSslmodeEnum.allow
  },
  {
    label: 'prefer',
    value: PostgresqlParametersSpecSslmodeEnum.prefer
  },
  {
    label: 'require',
    value: PostgresqlParametersSpecSslmodeEnum.require
  },
  {
    label: 'verify-ca',
    value: PostgresqlParametersSpecSslmodeEnum.verifyx2dca
  },
  {
    label: 'verify-full',
    value: PostgresqlParametersSpecSslmodeEnum.verifyx2dfull
  }
];

const PostgreSQLConnection = ({
  postgresql,
  onChange,
  sharedCredentials
}: IPostgreSQLConnectionProps) => {
  const handleChange = (obj: Partial<PostgresqlParametersSpec>) => {
    if (!onChange) return;

    onChange({
      ...postgresql,
      ...obj
    });
  };

  return (
    <SectionWrapper title="PostgreSQL connection parameters" className="mb-4">
      <FieldTypeInput
        label="Host"
        className="mb-4"
        value={postgresql?.host}
        onChange={(value) => handleChange({ host: value })}
        data={sharedCredentials}
        inputClassName={!postgresql?.host ? 'border border-red-500' : ''}
      />
      <FieldTypeInput
        label="Port"
        className="mb-4"
        value={postgresql?.port}
        onChange={(value) => handleChange({ port: value })}
        data={sharedCredentials}
      />
      <FieldTypeInput
        label="Database"
        className="mb-4"
        value={postgresql?.database}
        onChange={(value) => handleChange({ database: value })}
        data={sharedCredentials}
      />
      <FieldTypeInput
        label="User name"
        className="mb-4"
        value={postgresql?.user}
        onChange={(value) => handleChange({ user: value })}
        data={sharedCredentials}
      />
      <FieldTypeInput
        label="Password"
        className="mb-4"
        maskingType="password"
        value={postgresql?.password}
        onChange={(value) => handleChange({ password: value })}
        data={sharedCredentials}
      />
      <Select
        label="SSL mode"
        options={sslModes}
        className="mb-4"
        value={postgresql?.sslmode}
        onChange={(value) => handleChange({ sslmode: value })}
      />
      <JdbcPropertiesView
        properties={postgresql?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </SectionWrapper>
  );
};

export default PostgreSQLConnection;
