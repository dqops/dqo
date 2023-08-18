import React from 'react';

import SectionWrapper from '../../SectionWrapper';
import { RedshiftParametersSpec } from '../../../../api';
import JdbcPropertiesView from '../JdbcProperties';
import Checkbox from '../../../Checkbox';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';

interface IRedshiftConnectionProps {
  redshift?: RedshiftParametersSpec;
  onChange?: (obj: RedshiftParametersSpec) => void;
}

const RedshiftConnection = ({
  redshift,
  onChange
}: IRedshiftConnectionProps) => {
  const handleChange = (obj: Partial<RedshiftParametersSpec>) => {
    if (!onChange) return;

    onChange({
      ...redshift,
      ...obj
    });
  };

  return (
    <SectionWrapper title="Redshift connection parameters" className="mb-4">
      <FieldTypeInput
        label="Host"
        className="mb-4"
        value={redshift?.host}
        onChange={(value) => handleChange({ host: value })}
      />
      <FieldTypeInput
        label="Port"
        className="mb-4"
        value={(redshift?.port || 5439).toString()}
        onChange={(value) => handleChange({ port: value })}
      />
      <FieldTypeInput
        label="Database"
        className="mb-4"
        value={redshift?.database}
        onChange={(value) => handleChange({ database: value })}
      />
      <FieldTypeInput
        label="User name"
        className="mb-4"
        value={redshift?.user}
        onChange={(value) => handleChange({ user: value })}
      />
      <FieldTypeInput
        label="Password"
        maskingType="password"
        className="mb-4"
        value={redshift?.password}
        onChange={(value) => handleChange({ password: value })}
      />

      <JdbcPropertiesView
        properties={redshift?.properties}
        onChange={(properties) => handleChange({ properties })}
      />
    </SectionWrapper>
  );
};

export default RedshiftConnection;
