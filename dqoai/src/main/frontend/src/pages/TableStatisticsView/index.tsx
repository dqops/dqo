import React, { useEffect, useState } from 'react';
import TableColumns from '../TableColumnsView/TableColumns';
import { TableStatisticsModel } from '../../api';
import { AxiosResponse } from 'axios';
import { TableApiClient } from '../../services/apiClient';

export default function TableStatisticsView({
  connectionName,
  schemaName,
  tableName
}: {
  connectionName: string;
  schemaName: string;
  tableName: string;
}) {
  const [rowCount, setRowCount] = useState<TableStatisticsModel>();
  const fetchRows = async () => {
    try {
      const res: AxiosResponse<TableStatisticsModel> =
        await TableApiClient.getTableStatistics(
          connectionName,
          schemaName,
          tableName
        );
      setRowCount(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchRows();
  }, [connectionName, schemaName, tableName]);
  const renderValue = (value: any) => {
    if (typeof value === 'boolean') {
      return value ? 'Yes' : 'No';
    }
    if (typeof value === 'object') {
      return value.toString();
    }
    return value;
  };
  return (
    <div>
      <div className="border border-gray-400 w-1/3 flex flex-col justify-center gap-y-6 h-28 ml-4 mt-8">
        <div className="font-bold w-11/12 ml-3">Table Statistics</div>
        <div className="flex justify-between">
          <div className="flex gap-x-6 ml-3">
            <div>Total Rows</div>
            <div>
              {rowCount &&
                rowCount.statistics?.map((x, index) => (
                  <div key={index}>
                    {x.collector === 'row_count' && x.category === 'volume'
                      ? renderValue(x.result)
                      : ''}
                  </div>
                ))}
            </div>
          </div>
          <div className="flex gap-x-6 mr-5">
            <div>Collected at</div>
            <div>
              {rowCount &&
                rowCount.statistics?.map((x, index) => (
                  <div key={index}>
                    {x.collector === 'row_count' && x.category === 'volume'
                      ? renderValue(x.collectedAt)
                      : ''}
                  </div>
                ))}
            </div>
          </div>
        </div>
      </div>
      <TableColumns
        connectionName={connectionName}
        schemaName={schemaName}
        tableName={tableName}
      />
    </div>
  );
}
