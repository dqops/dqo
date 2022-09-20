import React, { useState } from 'react';

import { BigQueryAuthenticationMode } from '../../../../shared/enums/bigquery.enum';
import Input from '../../../Input';
import Select from '../../../Select';
import SectionWrapper from '../../SectionWrapper';

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

const BigqueryConnection = () => {
  const [authenticationMode, setAuthenticationMode] =
    useState<BigQueryAuthenticationMode>(
      BigQueryAuthenticationMode.google_application_credentials
    );

  return (
    <SectionWrapper title="BigQuery connection parameters" className="mb-4">
      <Input
        label="Source GCP project ID"
        className="mb-4"
        name="sourceProjectId"
      />
      <Input
        label="Billing GCP project ID"
        className="mb-4"
        name="billingProjectId"
      />
      <Select
        label="Authentication mode to the Google Cloud"
        options={options}
        className="mb-4"
        value={authenticationMode}
        onChange={setAuthenticationMode}
      />
      {authenticationMode === BigQueryAuthenticationMode.json_key_content && (
        <Input
          label="JSON key content"
          className="mb-4"
          name="jsonKeyContent"
        />
      )}
      {authenticationMode === BigQueryAuthenticationMode.json_key_path && (
        <Input
          label="A path to the JSON key file"
          className="mb-4"
          name="jsonKeyPath"
        />
      )}
      <Input label="Quota GCP project ID" name="quotaProjectId" />
    </SectionWrapper>
  );
};

export default BigqueryConnection;
