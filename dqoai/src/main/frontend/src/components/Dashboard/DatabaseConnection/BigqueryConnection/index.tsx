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
  bigquery?: BigQueryParametersSpec;
  onChange?: (obj: BigQueryParametersSpec) => void;
}

const BigqueryConnection: React.FC<IBigqueryConnectionProps> = ({
  bigquery,
  onChange
}) => {
  const handleChange = (obj: Partial<BigQueryParametersSpec>) => {
    if (!onChange) return;

    onChange({
      ...bigquery,
      ...obj
    });
  };

  return (
    <SectionWrapper title="BigQuery connection parameters" className="mb-4">
      <Input
        label="Source GCP project ID"
        className="mb-4"
        name="source_project_id"
        value={bigquery?.source_project_id}
        onChange={(e) => handleChange({ source_project_id: e.target.value })}
      />
      <Input
        label="Billing GCP project ID"
        className="mb-4"
        name="billing_project_id"
        value={bigquery?.billing_project_id}
        onChange={(e) => handleChange({ billing_project_id: e.target.value })}
      />
      <Select
        label="Authentication mode to the Google Cloud"
        options={options}
        className="mb-4"
        value={bigquery?.authentication_mode}
        onChange={(value) => handleChange({ authentication_mode: value })}
      />
      {bigquery?.authentication_mode ===
        BigQueryParametersSpecAuthenticationModeEnum.json_key_content && (
        <Input
          label="JSON key content"
          className="mb-4"
          name="json_key_content"
          value={bigquery?.json_key_content}
          onChange={(e) => handleChange({ json_key_content: e.target.value })}
        />
      )}
      {bigquery?.authentication_mode ===
        BigQueryParametersSpecAuthenticationModeEnum.json_key_path && (
        <Input
          label="A path to the JSON key file"
          className="mb-4"
          name="json_key_path"
          value={bigquery?.json_key_path}
          onChange={(e) => handleChange({ json_key_path: e.target.value })}
        />
      )}
      <Input
        label="Quota GCP project ID"
        name="quota_project_id"
        value={bigquery?.quota_project_id}
        onChange={(e) => handleChange({ quota_project_id: e.target.value })}
      />
    </SectionWrapper>
  );
};

export default BigqueryConnection;
