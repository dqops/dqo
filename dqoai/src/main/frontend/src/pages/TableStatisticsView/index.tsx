import React, { useEffect, useState } from 'react';
import TableColumns from '../TableColumnsView/TableColumns';
import { TableStatisticsModel } from '../../api';
import { AxiosResponse } from 'axios';
import { TableApiClient } from '../../services/apiClient';
import Loader from '../../components/Loader';
import { useSelector } from 'react-redux';
import { getFirstLevelState } from '../../redux/selectors';
import { CheckTypes } from '../../shared/routes';
import { useParams } from 'react-router-dom';
import moment from 'moment';
import { IRootState } from '../../redux/reducers';

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
  const { jobs } = useSelector((state: IRootState) => state.job || {});
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
  console.log(jobs);
  if (loading) {
    return (
      <div className="flex justify-center min-h-80">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }
  const formatNumber = (k: number) => {
    if (k > 1000 && k < 1000000) {
      if (k > Math.pow(10, 3) && k < Math.pow(10, 4)) {
        return (k / Math.pow(10, 3)).toFixed(3) + 'k';
      } else if (k > Math.pow(10, 4) && k < Math.pow(10, 5)) {
        return (k / Math.pow(10, 3)).toFixed(2) + 'k';
      } else {
        return (k / Math.pow(10, 3)).toFixed(1) + 'k';
      }
    } else if (k > Math.pow(10, 6) && k < Math.pow(10, 9)) {
      if (k > Math.pow(10, 6) && k < Math.pow(10, 7)) {
        return (k / Math.pow(10, 6)).toFixed(3) + 'M';
      } else if (k > Math.pow(10, 7) && k < Math.pow(10, 8)) {
        return (k / Math.pow(10, 6)).toFixed(2) + 'M';
      } else {
        return (k / Math.pow(10, 6)).toFixed(1) + 'M';
      }
    } else if (k > Math.pow(10, 9) && k < Math.pow(10, 12)) {
      if (k > Math.pow(10, 9) && k < Math.pow(10, 10)) {
        return (k / Math.pow(10, 9)).toFixed(3) + 'G';
      } else if (k > Math.pow(10, 10) && k < Math.pow(10, 11)) {
        return (k / Math.pow(10, 9)).toFixed(2) + 'G';
      } else {
        return (k / Math.pow(10, 9)).toFixed(1) + 'G';
      }
    } else if (k > Math.pow(10, 12) && k < Math.pow(10, 15)) {
      if (k > Math.pow(10, 12) && k < Math.pow(10, 13)) {
        return (k / Math.pow(10, 12)).toFixed(3) + 'T';
      } else if (k > Math.pow(10, 13) && k < Math.pow(10, 14)) {
        return (k / Math.pow(10, 12)).toFixed(2) + 'T';
      } else {
        return (k / Math.pow(10, 12)).toFixed(1) + 'T';
      }
    } else {
      return k;
    }
  };

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
                      ? moment(renderValue(x.collectedAt)).format('L') +
                        '  ' +
                        moment(renderValue(x.collectedAt)).format('LTS')
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
