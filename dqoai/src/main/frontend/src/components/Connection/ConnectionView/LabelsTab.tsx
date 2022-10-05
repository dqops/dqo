import React, { useEffect, useState } from 'react';
import { AxiosResponse } from 'axios';
import { ConnectionApiClient } from '../../../services/apiClient';
import LabelsView from '../LabelsView';

interface ILabelsTabProps {
  connectionName: string;
}

const LabelsTab = ({ connectionName }: ILabelsTabProps) => {
  const [labels, setLabels] = useState<string[]>([]);

  const fetchLabels = async () => {
    try {
      const res: AxiosResponse<string[]> =
        await ConnectionApiClient.getConnectionLabels(connectionName);

      setLabels(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchLabels().then();
  }, [connectionName]);

  return <LabelsView labels={labels} onChange={setLabels} />;
};

export default LabelsTab;
