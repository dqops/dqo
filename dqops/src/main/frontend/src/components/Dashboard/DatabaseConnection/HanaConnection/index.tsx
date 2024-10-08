import React from 'react';

import { HanaParametersSpec, SharedCredentialListModel } from '../../../../api';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';
import JdbcPropertiesView from '../JdbcProperties';

interface IHanaConnectionProps {
  hana?: HanaParametersSpec;
  onChange?: (obj: HanaParametersSpec) => void;
  sharedCredentials?: SharedCredentialListModel[];
}

const HanaConnection = ({
  hana,
  onChange,
  sharedCredentials
}: IHanaConnectionProps) => {
  const handleChange = (obj: Partial<HanaParametersSpec>) => {
    if (!onChange) return;

    onChange({
      ...hana,
      ...obj
    });
  };

  return (
    <div>
      <FieldTypeInput
        data={sharedCredentials}
        label="Host"
        className="mb-4"
        value={hana?.host}
        onChange={(value) => handleChange({ host: value })}
        inputClassName={!hana?.host ? 'border border-red-500' : ''}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Port"
        className="mb-4"
        value={hana?.port}
        onChange={(value) => handleChange({ port: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Instance number"
        className="mb-4"
        value={hana?.instance_number}
        onChange={(value) => handleChange({ instance_number: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="User name"
        className="mb-4"
        value={hana?.user}
        onChange={(value) => handleChange({ user: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Password"
        className="mb-4"
        maskingType="password"
        value={hana?.password}
        onChange={(value) => handleChange({ password: value })}
      />
      <JdbcPropertiesView
        properties={hana?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </div>
  );
};

export default HanaConnection;
