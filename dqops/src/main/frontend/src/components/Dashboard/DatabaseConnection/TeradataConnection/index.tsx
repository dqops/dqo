import React, { useState } from 'react';

import {
  TeradataParametersSpec,
  SharedCredentialListModel
} from '../../../../api';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';
import JdbcPropertiesView from '../JdbcProperties';

interface ITeradataConnectionProps {
  teradata?: TeradataParametersSpec;
  onChange?: (obj: TeradataParametersSpec) => void;
  sharedCredentials?: SharedCredentialListModel[];
  nameOfDatabase?: string;
  onNameOfDatabaseChange?: (name: string) => void;
}

const TeradataConnection = ({
  teradata,
  onChange,
  sharedCredentials,
  nameOfDatabase,
  onNameOfDatabaseChange
}: ITeradataConnectionProps) => {
  const [selectedInput, setSelectedInput] = useState<number | string>();
  const handleChange = (obj: Partial<TeradataParametersSpec>) => {
    if (!onChange) return;

    onChange({
      ...teradata,
      ...obj
    });
  };

  return (
    <div>
      <FieldTypeInput
        label="Host"
        className="mb-4"
        value={teradata?.host}
        onChange={(value) => handleChange({ host: value })}
        data={sharedCredentials}
        inputClassName={!teradata?.host ? 'border border-red-500' : ''}
      />
      <FieldTypeInput
        label="Port"
        className="mb-4"
        value={teradata?.port}
        onChange={(value) => handleChange({ port: value })}
        data={sharedCredentials}
      />
      <FieldTypeInput
        label="User name"
        className="mb-4"
        value={teradata?.user}
        onChange={(value) => handleChange({ user: value })}
        data={sharedCredentials}
      />
      <FieldTypeInput
        label="Password"
        className="mb-4"
        maskingType="password"
        value={teradata?.password}
        onChange={(value) => handleChange({ password: value })}
        data={sharedCredentials}
      />
      <JdbcPropertiesView
        properties={teradata?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </div>
  );
};

export default TeradataConnection;
