import React, { useState } from 'react';

import {
  QuestDbParametersSpec,
  SharedCredentialListModel
} from '../../../../api';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';
import JdbcPropertiesView from '../JdbcProperties';

interface IQuestDbConnectionProps {
  questdb?: QuestDbParametersSpec;
  onChange?: (obj: QuestDbParametersSpec) => void;
  sharedCredentials?: SharedCredentialListModel[];
  nameOfDatabase?: string;
  onNameOfDatabaseChange?: (name: string) => void;
}

const QuestDbConnection = ({
  questdb,
  onChange,
  sharedCredentials,
  nameOfDatabase,
  onNameOfDatabaseChange
}: IQuestDbConnectionProps) => {
  const [selectedInput, setSelectedInput] = useState<number | string>();
  const handleChange = (obj: Partial<QuestDbParametersSpec>) => {
    if (!onChange) return;

    onChange({
      ...questdb,
      ...obj
    });
  };

  return (
    <div>
      <FieldTypeInput
        label="Host"
        className="mb-4"
        value={questdb?.host}
        onChange={(value) => handleChange({ host: value })}
        data={sharedCredentials}
        inputClassName={!questdb?.host ? 'border border-red-500' : ''}
      />
      <FieldTypeInput
        label="Port"
        className="mb-4"
        value={questdb?.port}
        onChange={(value) => handleChange({ port: value })}
        data={sharedCredentials}
      />
      <FieldTypeInput
        label="Database"
        className="mb-4"
        value={questdb?.database}
        onChange={(value) => handleChange({ database: value })}
        data={sharedCredentials}
      />
      <FieldTypeInput
        label="User name"
        className="mb-4"
        value={questdb?.user}
        onChange={(value) => handleChange({ user: value })}
        data={sharedCredentials}
      />
      <FieldTypeInput
        label="Password"
        className="mb-4"
        maskingType="password"
        value={questdb?.password}
        onChange={(value) => handleChange({ password: value })}
        data={sharedCredentials}
      />
      <JdbcPropertiesView
        properties={questdb?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </div>
  );
};

export default QuestDbConnection;
