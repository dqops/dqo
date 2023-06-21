import React, { useEffect, useState } from 'react';
import TableColumns from '../TableColumnsView/TableColumns';
import { DataStreamMappingSpec, TableStatisticsModel } from '../../api';
import { AxiosResponse } from 'axios';
import { TableApiClient } from '../../services/apiClient';
import Loader from '../../components/Loader';
import { useSelector } from 'react-redux';
import { getFirstLevelState } from '../../redux/selectors';
import { CheckTypes } from '../../shared/routes';
import { useParams } from 'react-router-dom';
import moment from 'moment';
import { formatNumber } from '../../shared/constants';

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
  const { loading } = useSelector(getFirstLevelState(checkTypes));
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

  if (loading) {
    return (
      <div className="flex justify-center min-h-80">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }
  const setLevelsData = (levelsToSet: DataStreamMappingSpec): void => {};

  const doNot = (a: string): void => {};
  const setNumberOfSelected = (param: number): void => {};

  return (
    <div>
      <div className="inline-block justify-center gap-y-6 h-20 ml-4 mt-8 border border-gray-300 px-4 py-6 relative rounded mt-8">
        <div className="font-bold ml-3 px-2 absolute bg-white left-2 top-0 -translate-y-1/2 text-gray-700 font-semibold">
          Table Statistics
        </div>
        <div className="flex justify-between gap-x-10">
          <div className="flex gap-x-6 ml-3">
            <div>Total Rows</div>
            <div>
              {rowCount &&
                rowCount.statistics?.map((x, index) => (
                  <div key={index} className="font-bold">
                    {x.collector === 'row_count' && x.category === 'volume'
                      ? formatNumber(Number(renderValue(x.result)))
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
                  <div key={index} className="font-bold">
                    {x.collector === 'row_count' && x.category === 'volume'
                      ? moment(renderValue(x.collectedAt)).format(
                          'YYYY-MM-DD HH:mm:ss'
                        )
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
        updateData={doNot}
        setLevelsData={setLevelsData}
        setNumberOfSelected={setNumberOfSelected}
      />
    </div>
  );
}
