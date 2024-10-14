import React, { useState } from 'react';

import clsx from 'clsx';
import {
  SharedCredentialListModel,
  ClickHouseParametersSpec,
} from '../../../../api';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';
import Select from '../../../Select';
import JdbcPropertiesView from '../JdbcProperties';

interface IClickHouseConnectionProps {
  clickhouse?: ClickHouseParametersSpec;
  onChange?: (obj: ClickHouseParametersSpec) => void;
  sharedCredentials?: SharedCredentialListModel[];
  nameOfDatabase?: string;
  onNameOfDatabaseChange?: (name: string) => void;
}

const ClickHouseConnection = ({
  clickhouse,
  onChange,
  sharedCredentials,
  nameOfDatabase,
  onNameOfDatabaseChange
}: IClickHouseConnectionProps) => {
  const [selectedInput, setSelectedInput] = useState<number | string>();
  const handleChange = (obj: Partial<ClickHouseParametersSpec>) => {
    if (!onChange) return;
    onChange({
      ...clickhouse,
      ...obj
    });
  };

  return (
    <div>
      <FieldTypeInput
        data={sharedCredentials}
        label="Host"
        className="mb-4"
        value={clickhouse?.host}
        onChange={(value) => handleChange({ host: value })}
        inputClassName={!clickhouse?.host ? 'border border-red-500' : ''}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Port"
        className="mb-4"
        value={clickhouse?.port}
        onChange={(value) => handleChange({ port: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="User name"
        className="mb-4"
        value={clickhouse?.user}
        onChange={(value) => handleChange({ user: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Password"
        className="mb-4"
        value={clickhouse?.password}
        onChange={(value) => handleChange({ password: value })}
      />
      <JdbcPropertiesView
        properties={clickhouse?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </div>
  );
};

export default ClickHouseConnection;
