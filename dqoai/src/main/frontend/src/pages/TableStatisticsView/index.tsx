import React, { useEffect, useState } from 'react';
import TableColumns from '../TableColumnsView/TableColumns';
import { TableStatisticsModel } from '../../api';
import { AxiosResponse } from 'axios';
import { TableApiClient } from '../../services/apiClient';
import Loader from '../../components/Loader';
import { useSelector } from 'react-redux';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../redux/selectors';
import { CheckTypes } from '../../shared/routes';
import { useParams } from 'react-router-dom';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { getTableProfilingChecksUI } from '../../redux/actions/table.actions';

export default function TableStatisticsView({
  connectionName,
  schemaName,
  tableName
}: {
  connectionName: string;
  schemaName: string;
  tableName: string;
}) {
  const { checkTypes }: { checkTypes: CheckTypes } = useParams();
  const [rowCount, setRowCount] = useState<TableStatisticsModel>();
  const { loading, tableBasic } = useSelector(getFirstLevelState(checkTypes));
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
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
    fetchRows().then(() =>
      dispatch(
        getTableProfilingChecksUI(
          checkTypes,
          firstLevelActiveTab,
          connectionName,
          schemaName,
          tableName
        )
      )
    );
  }, [
    checkTypes,
    firstLevelActiveTab,
    connectionName,
    schemaName,
    tableName,
    tableBasic
  ]);

  const renderValue = (value: any) => {
    if (typeof value === 'boolean') {
      return value ? 'Yes' : 'No';
    }
    if (typeof value === 'object') {
      return value.toString();
    }
    return value;
  };

  if (loading) {
    return (
      <div className="flex justify-center min-h-80">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }
  console.log(loading);

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
