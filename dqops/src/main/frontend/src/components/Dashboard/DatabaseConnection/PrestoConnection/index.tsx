import React from 'react';

import SectionWrapper from '../../SectionWrapper';
import {
  PrestoParametersSpec,
  SharedCredentialListModel
} from '../../../../api';
import JdbcPropertiesView from '../JdbcProperties';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';

interface IPrestoConnectionProps {
  presto?: PrestoParametersSpec;
  onChange?: (obj: PrestoParametersSpec) => void;
  sharedCredentials?: SharedCredentialListModel[];
}

const PrestoConnection = ({
  presto,
  onChange,
  sharedCredentials
}: IPrestoConnectionProps) => {
  const handleChange = (obj: Partial<PrestoParametersSpec>) => {
    if (!onChange) return;

    onChange({
      ...presto,
      ...obj
    });
  };

  return (
    <SectionWrapper title="Presto connection parameters" className="mb-4">
      <FieldTypeInput
        data={sharedCredentials}
        label="Host"
        className="mb-4"
        value={presto?.host}
        onChange={(value) => handleChange({ host: value })}
        inputClassName={!presto?.host ? 'border border-red-500' : ''}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Port"
        className="mb-4"
        value={presto?.port}
        onChange={(value) => handleChange({ port: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Database"
        className="mb-4"
        value={presto?.database}
        onChange={(value) => handleChange({ database: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="User name"
        className="mb-4"
        value={presto?.user}
        onChange={(value) => handleChange({ user: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Password"
        className="mb-4"
        maskingType="password"
        value={presto?.password}
        onChange={(value) => handleChange({ password: value })}
      />
      <JdbcPropertiesView
        properties={presto?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </SectionWrapper>
  );
};

export default PrestoConnection;
