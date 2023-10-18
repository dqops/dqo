import React from 'react';
import SectionWrapper from '../../SectionWrapper';
import { RedshiftParametersSpec, SharedCredentialListModel } from '../../../../api';
import JdbcPropertiesView from '../JdbcProperties';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';

interface IRedshiftConnectionProps {
  redshift?: RedshiftParametersSpec;
  onChange?: (obj: RedshiftParametersSpec) => void;
  sharedCredentials ?: SharedCredentialListModel[];
}

const RedshiftConnection = ({
  redshift,
  onChange,
  sharedCredentials
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
        data = {sharedCredentials}
        label="Host"
        className="mb-4"
        value={redshift?.host}
        onChange={(value) => handleChange({ host: value })}
      />
      <FieldTypeInput  
        data = {sharedCredentials}
        label="Port"
        className="mb-4"
        value={(redshift?.port || 5439).toString()}
        onChange={(value) => handleChange({ port: value })}
      />
      <FieldTypeInput  
        data = {sharedCredentials}
        label="Database"
        className="mb-4"
        value={redshift?.database}
        onChange={(value) => handleChange({ database: value })}
      />
      <FieldTypeInput  
        data = {sharedCredentials}
        label="User name"
        className="mb-4"
        value={redshift?.user}
        onChange={(value) => handleChange({ user: value })}
      />
      <FieldTypeInput  
        data = {sharedCredentials}
        label="Password"
        maskingType="password"
        className="mb-4"
        value={redshift?.password}
        onChange={(value) => handleChange({ password: value })}
      />

      <JdbcPropertiesView
        properties={redshift?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </SectionWrapper>
  );
};

export default RedshiftConnection;
