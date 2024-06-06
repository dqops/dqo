import moment from 'moment';
import React from 'react';
import { useSelector } from 'react-redux';
import { TableColumnsStatisticsModel, TableStatisticsModel } from '../../api';
import Loader from '../../components/Loader';
import { getFirstLevelState } from '../../redux/selectors';
import { formatNumber } from '../../shared/constants';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import TableColumns from '../TableColumnsView/TableColumns';

export default function TableStatisticsView({
  connectionName,
  schemaName,
  tableName,
  checkedColumns,
  setCheckedColumns,
  statistics,
  refreshListFunc,
  rowCount
}: {
  connectionName: string;
  schemaName: string;
  tableName: string;
  statistics?: TableColumnsStatisticsModel;
  checkedColumns: Array<string>;
  setCheckedColumns: (columns: Array<string>) => void;
  refreshListFunc: () => void;
  rowCount: TableStatisticsModel;
}) {
  const { checkTypes }: { checkTypes: CheckTypes } = useDecodedParams();
  const { loading } = useSelector(getFirstLevelState(checkTypes));

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

  return (
    <div className="text-sm">
      <div className="inline-block justify-center gap-y-6 h-16 ml-4 mt-8 border border-gray-300 px-4 py-6 relative rounded">
        <div className="font-bold ml-3 px-2 absolute bg-white left-2 top-0 -translate-y-1/2 text-gray-700 ">
          Table statistics
        </div>
        <div className="flex justify-between gap-x-10">
          <div className="flex gap-x-6 ml-3">
            <div>Total rows</div>
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
          <div className="flex gap-x-6 ml-3">
            <div>Column count</div>
            <div>
              {rowCount &&
                rowCount.statistics?.map((x, index) => (
                  <div key={index} className="font-bold">
                    {x.collector === 'column_count'
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
        checkedColumns={checkedColumns}
        statistics={statistics}
        setCheckedColumns={setCheckedColumns}
        refreshListFunc={refreshListFunc}
      />
    </div>
  );
}
