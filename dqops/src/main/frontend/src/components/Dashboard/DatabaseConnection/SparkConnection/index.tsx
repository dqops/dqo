import React from 'react';

import {
  SharedCredentialListModel,
  SparkParametersSpec
} from '../../../../api';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';
import JdbcPropertiesView from '../JdbcProperties';

interface ISparkConnectionProps {
  spark?: SparkParametersSpec;
  onChange?: (obj: SparkParametersSpec) => void;
  sharedCredentials?: SharedCredentialListModel[];
}

const SparkConnection = ({
  spark,
  onChange,
  sharedCredentials
}: ISparkConnectionProps) => {
  const handleChange = (obj: Partial<SparkParametersSpec>) => {
    if (!onChange) return;

    onChange({
      ...spark,
      ...obj
    });
  };

  return (
    <div>
      <FieldTypeInput
        data={sharedCredentials}
        label="Host"
        className="mb-4"
        value={spark?.host}
        onChange={(value) => handleChange({ host: value })}
        inputClassName={!spark?.host ? 'border border-red-500' : ''}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Port"
        className="mb-4"
        value={spark?.port}
        onChange={(value) => handleChange({ port: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="User name"
        className="mb-4"
        value={spark?.user}
        onChange={(value) => handleChange({ user: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Password"
        className="mb-4"
        maskingType="password"
        value={spark?.password}
        onChange={(value) => handleChange({ password: value })}
      />
      <JdbcPropertiesView
        properties={spark?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </div>
  );
};

export default SparkConnection;
