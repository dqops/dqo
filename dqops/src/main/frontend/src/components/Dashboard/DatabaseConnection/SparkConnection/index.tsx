import React from 'react';

import SectionWrapper from '../../SectionWrapper';
import {
  SparkParametersSpec,
  SharedCredentialListModel
} from '../../../../api';
import JdbcPropertiesView from '../JdbcProperties';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';

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
    <SectionWrapper title="Spark connection parameters" className="mb-4">
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
    </SectionWrapper>
  );
};

export default SparkConnection;
