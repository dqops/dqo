import moment from 'moment';
import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import {
  DataGroupingConfigurationSpec,
  TableColumnsStatisticsModel,
  TableStatisticsModel
} from '../../api';
import Loader from '../../components/Loader';
import { setCreatedDataStream } from '../../redux/actions/definition.actions';
import { getFirstLevelState } from '../../redux/selectors';
import { formatNumber } from '../../shared/constants';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import TableColumns from '../TableColumnsView/TableColumns';

export default function TableStatisticsView({
  connectionName,
  schemaName,
  tableName,
  updateData2,
  setLevelsData2,
  setNumberOfSelected2,
  statistics,
  onChangeSelectedColumns,
  refreshListFunc,
  rowCount
}: {
  connectionName: string;
  schemaName: string;
  tableName: string;
  updateData2: (arg: string) => void;
  setLevelsData2: (arg: DataGroupingConfigurationSpec) => void;
  setNumberOfSelected2: (arg: number) => void;
  statistics?: TableColumnsStatisticsModel;
  onChangeSelectedColumns?: (columns: string[]) => void;
  refreshListFunc: () => void;
  rowCount: TableStatisticsModel;
}) {
  const { checkTypes }: { checkTypes: CheckTypes } = useDecodedParams();
  const { loading } = useSelector(getFirstLevelState(checkTypes));

  const {
    connection,
    schema,
    table
  }: {
    connection: string;
    schema: string;
    table: string;
    tab: string;
  } = useDecodedParams();

  useEffect(() => {
    setNumberOfSelected(0);
    updateData('');
    setLevelsData({});
    setCreatedDataStream(false, '', {});
  }, [connection, schema, table]);

  const renderValue = (value: any) => {
    if (typeof value === 'boolean') {
      return value ? 'Yes' : 'No';
    }
    if (typeof value === 'object') {
      return value.toString();
    }
    return value;
  };
  const updateData = (nameOfDS: string): void => {
    updateData2(nameOfDS);
  };

  const setLevelsData = (levelsToSet: DataGroupingConfigurationSpec): void => {
    setLevelsData2(levelsToSet);
  };

  const setNumberOfSelected = (param: number): void => {
    setNumberOfSelected2(param);
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
        levels={{}}
        setLevels={setLevelsData}
        statistics={statistics}
        refreshListFunc={refreshListFunc}
      />
    </div>
  );
}
