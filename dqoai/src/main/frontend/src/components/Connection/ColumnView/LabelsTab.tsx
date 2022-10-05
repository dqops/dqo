import React, { useEffect, useState } from 'react';
import { AxiosResponse } from 'axios';
import { ColumnApiClient } from '../../../services/apiClient';
import LabelsView from '../LabelsView';

interface ILabelsTabProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  columnName: string;
}

const LabelsTab = ({
  connectionName,
  schemaName,
  tableName,
  columnName
}: ILabelsTabProps) => {
  const [labels, setLabels] = useState<string[]>([]);

  const fetchLabels = async () => {
    try {
      const res: AxiosResponse<string[]> =
        await ColumnApiClient.getColumnLabels(
          connectionName,
          schemaName,
          tableName,
          columnName
        );

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
