import React from 'react';
import SectionWrapper from '../../SectionWrapper';
import {
  RedshiftParametersSpec,
  SharedCredentialListModel,
  RedshiftParametersSpecRedshiftAuthenticationModeEnum
} from '../../../../api';
import JdbcPropertiesView from '../JdbcProperties';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';
import Select from '../../../Select';

interface IRedshiftConnectionProps {
  redshift?: RedshiftParametersSpec;
  onChange?: (obj: RedshiftParametersSpec) => void;
  sharedCredentials?: SharedCredentialListModel[];
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

  const redshiftAuthenticationOptions = [
    {
      label: 'User/Password',
      value: RedshiftParametersSpecRedshiftAuthenticationModeEnum.user_password
    },
    {
      label: 'IAM',
      value: RedshiftParametersSpecRedshiftAuthenticationModeEnum.iam
    },
    {
      label: 'Default Credentials',
      value: RedshiftParametersSpecRedshiftAuthenticationModeEnum.default_credentials
    }
  ];

  return (
    <SectionWrapper title="Redshift connection parameters" className="mb-4">
      <FieldTypeInput
        data={sharedCredentials}
        label="Host"
        className="mb-4"
        value={redshift?.host}
        onChange={(value) => handleChange({ host: value })}
        inputClassName={!redshift?.host ? 'border border-red-500' : ''}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Port"
        className="mb-4"
        value={redshift?.port}
        onChange={(value) => handleChange({ port: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Database"
        className="mb-4"
        value={redshift?.database}
        onChange={(value) => handleChange({ database: value })}
      />

      <Select
        label="Redshift authentication mode"
        options={redshiftAuthenticationOptions}
        className="mb-4"
        value={redshift?.redshift_authentication_mode}
        onChange={(value) => {
          handleChange({ redshift_authentication_mode: value });
        }}
      />

      {redshift?.redshift_authentication_mode !== RedshiftParametersSpecRedshiftAuthenticationModeEnum.default_credentials && (
        <>
          <FieldTypeInput
            data={sharedCredentials}
            label={ redshift?.redshift_authentication_mode === RedshiftParametersSpecRedshiftAuthenticationModeEnum.user_password 
              ? "User name" : "Access Key ID" }
            className="mb-4"
            value={redshift?.user}
            onChange={(value) => handleChange({ user: value })}
          />
          <FieldTypeInput
            data={sharedCredentials}
            label={ redshift?.redshift_authentication_mode === RedshiftParametersSpecRedshiftAuthenticationModeEnum.user_password 
              ? "Password" : "Secret Access Key" }
            maskingType="password"
            className="mb-4"
            value={redshift?.password}
            onChange={(value) => handleChange({ password: value })}
          />
        </>
      )}

      <JdbcPropertiesView
        properties={redshift?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </SectionWrapper>
  );
};

export default RedshiftConnection;
