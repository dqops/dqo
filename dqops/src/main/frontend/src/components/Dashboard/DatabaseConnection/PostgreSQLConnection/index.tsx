import React, { useState } from 'react';

import {
  PostgresqlParametersSpec,
  PostgresqlParametersSpecPostgresqlEngineTypeEnum,
  PostgresqlParametersSpecSslmodeEnum,
  SharedCredentialListModel
} from '../../../../api';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';
import Select from '../../../Select';
import JdbcPropertiesView from '../JdbcProperties';
import clsx from 'clsx';

interface IPostgreSQLConnectionProps {
  postgresql?: PostgresqlParametersSpec;
  onChange?: (obj: PostgresqlParametersSpec) => void;
  sharedCredentials?: SharedCredentialListModel[];
  nameOfDatabase?: string;
  onNameOfDatabaseChange?: (name: string) => void;
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

const postgresqlEngineType = [
  {
    label: 'postgresql',
    value: PostgresqlParametersSpecPostgresqlEngineTypeEnum.postgresql
  },
  {
    label: 'timescale',
    value: PostgresqlParametersSpecPostgresqlEngineTypeEnum.timescale
  }
];

const PostgreSQLConnection = ({
  postgresql,
  onChange,
  sharedCredentials,
  nameOfDatabase,
  onNameOfDatabaseChange
}: IPostgreSQLConnectionProps) => {
  const [selectedInput, setSelectedInput] = useState<number | string>();
  const handleChange = (obj: Partial<PostgresqlParametersSpec>) => {
    if (!onChange) return;

    onChange({
      ...postgresql,
      ...obj
    });
  };

  return (
    <div>

      <Select
        label="PostgreSQL Engine Type"
        options={postgresqlEngineType}
        className={clsx('mb-4')}
        value={
          postgresql?.postgresql_engine_type ||
          (nameOfDatabase
            ?.toLowerCase()
            .trim() as PostgresqlParametersSpecPostgresqlEngineTypeEnum)
        }
        onChange={(value) => {
          handleChange({
            postgresql_engine_type: value
          }),
            value &&
              onNameOfDatabaseChange &&
              onNameOfDatabaseChange(
                String(value).replace(/\w/, (x) => x.toUpperCase())
              );
        }}
        onClickValue={setSelectedInput}
        selectedMenu={selectedInput}
        menuClassName="!top-14"
      />

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
        menuClassName="!top-14"
      />
      <JdbcPropertiesView
        properties={postgresql?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </div>
  );
};

export default PostgreSQLConnection;
