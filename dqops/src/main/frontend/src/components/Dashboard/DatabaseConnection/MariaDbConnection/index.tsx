import React, { useState } from 'react';

import {
  MariaDbParametersSpec,
  SharedCredentialListModel,
} from '../../../../api';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';
import JdbcPropertiesView from '../JdbcProperties';

interface IMariaDbConnectionProps {
  mariadb?: MariaDbParametersSpec;
  onChange?: (obj: MariaDbParametersSpec) => void;
  sharedCredentials?: SharedCredentialListModel[];
  nameOfDatabase?: string;
  onNameOfDatabaseChange?: (name: string) => void;
}

const MariaDbConnection = ({
  mariadb,
  onChange,
  sharedCredentials,
  nameOfDatabase,
  onNameOfDatabaseChange
}: IMariaDbConnectionProps) => {
  const [selectedInput, setSelectedInput] = useState<number | string>();
  const handleChange = (obj: Partial<MariaDbParametersSpec>) => {
    if (!onChange) return;
    onChange({
      ...mariadb,
      ...obj,
    });
  };

  return (
    <div>
      <FieldTypeInput
        data={sharedCredentials}
        label="Host"
        className="mb-4"
        value={mariadb?.host}
        onChange={(value) => handleChange({ host: value })}
        inputClassName={!mariadb?.host ? 'border border-red-500' : ''}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Port"
        className="mb-4"
        value={mariadb?.port}
        onChange={(value) => handleChange({ port: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Database"
        className="mb-4"
        value={mariadb?.database}
        onChange={(value) => handleChange({ database: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="User name"
        className="mb-4"
        value={mariadb?.user}
        onChange={(value) => handleChange({ user: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Password"
        className="mb-4"
        maskingType="password"
        value={mariadb?.password}
        onChange={(value) => handleChange({ password: value })}
      />

      <JdbcPropertiesView
        properties={mariadb?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </div>
  );
};

export default MariaDbConnection;
