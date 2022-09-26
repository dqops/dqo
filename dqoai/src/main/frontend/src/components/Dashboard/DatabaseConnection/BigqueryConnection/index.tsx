import React from 'react';

import { BigQueryAuthenticationMode } from '../../../../shared/enums/bigquery.enum';
import Input from '../../../Input';
import Select from '../../../Select';
import SectionWrapper from '../../SectionWrapper';
import {
  BigQueryParametersSpec,
  BigQueryParametersSpecAuthenticationModeEnum
} from '../../../../api';

const options = [
  {
    label: 'Google Application Credentials',
    value: BigQueryAuthenticationMode.google_application_credentials
  },
  {
    label: 'Json Key Content',
    value: BigQueryAuthenticationMode.json_key_content
  },
  {
    label: 'Json Key Path',
    value: BigQueryAuthenticationMode.json_key_path
  }
];

interface IBigqueryConnectionProps {
  spec?: BigQueryParametersSpec;
  onChange?: any;
}

const BigqueryConnection: React.FC<IBigqueryConnectionProps> = ({
  spec,
  onChange
}) => {
  const handleChange = (obj: any) => {
    onChange({
      spec: {
        bigquery: obj
      }
    });
  };

  return (
    <SectionWrapper title="BigQuery connection parameters" className="mb-4">
      <Input
        label="Source GCP project ID"
        className="mb-4"
        name="source_project_id"
        value={spec?.source_project_id}
        onChange={(e) => handleChange({ source_project_id: e.target.value })}
      />
      <Input
        label="Billing GCP project ID"
        className="mb-4"
        name="billing_project_id"
        value={spec?.billing_project_id}
        onChange={(e) => handleChange({ billing_project_id: e.target.value })}
      />
      <Select
        label="Authentication mode to the Google Cloud"
        options={options}
        className="mb-4"
        value={spec?.authentication_mode}
        onChange={(value) => handleChange({ authentication_mode: value })}
      />
      {spec?.authentication_mode ===
        BigQueryParametersSpecAuthenticationModeEnum.json_key_content && (
        <Input
          label="JSON key content"
          className="mb-4"
          name="json_key_content"
          value={spec?.json_key_content}
          onChange={(e) => handleChange({ json_key_content: e.target.value })}
        />
      )}
      {spec?.authentication_mode ===
        BigQueryParametersSpecAuthenticationModeEnum.json_key_path && (
        <Input
          label="A path to the JSON key file"
          className="mb-4"
          name="json_key_path"
          value={spec?.json_key_path}
          onChange={(e) => handleChange({ json_key_path: e.target.value })}
        />
      )}
      <Input
        label="Quota GCP project ID"
        name="quota_project_id"
        value={spec?.quota_project_id}
        onChange={(e) => handleChange({ quota_project_id: e.target.value })}
      />
    </SectionWrapper>
  );
};

export default BigqueryConnection;
