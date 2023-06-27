import React from 'react';

import { BigQueryAuthenticationMode } from '../../../../shared/enums/bigquery.enum';
import Select from '../../../Select';
import SectionWrapper from '../../SectionWrapper';
import {
  BigQueryParametersSpec,
  BigQueryParametersSpecAuthenticationModeEnum
} from '../../../../api';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';
import FieldTypeTextarea from '../../../Connection/ConnectionView/FieldTypeTextarea';

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
      <FieldTypeInput
        className="mb-4"
        label="Source GCP project ID"
        name="source_project_id"
        value={bigquery?.source_project_id}
        onChange={(value) => handleChange({ source_project_id: value })}
      />
      <FieldTypeInput
        className="mb-4"
        label="Billing GCP project ID"
        name="billing_project_id"
        value={bigquery?.billing_project_id}
        onChange={(value) => handleChange({ billing_project_id: value })}
      />
      <Select
        label="Authentication mode to the Google Cloud"
        options={options}
        className="mb-4"
        value={
          bigquery?.authentication_mode ||
          BigQueryAuthenticationMode.google_application_credentials
        }
        onChange={(value) => handleChange({ authentication_mode: value })}
      />
      {bigquery?.authentication_mode ===
        BigQueryParametersSpecAuthenticationModeEnum.json_key_content && (
        <FieldTypeTextarea
          label="JSON key content"
          className="mb-4"
          name="json_key_content"
          value={bigquery?.json_key_content}
          onChange={(value) => handleChange({ json_key_content: value })}
        />
      )}
      {bigquery?.authentication_mode ===
        BigQueryParametersSpecAuthenticationModeEnum.json_key_path && (
        <FieldTypeInput
          label="A path to the JSON key file"
          className="mb-4"
          name="json_key_path"
          value={bigquery?.json_key_path}
          onChange={(value) => handleChange({ json_key_path: value })}
        />
      )}
      <FieldTypeInput
        label="Quota GCP project ID"
        name="quota_project_id"
        value={bigquery?.quota_project_id}
        onChange={(value) => handleChange({ quota_project_id: value })}
      />
    </SectionWrapper>
  );
};

export default BigqueryConnection;
