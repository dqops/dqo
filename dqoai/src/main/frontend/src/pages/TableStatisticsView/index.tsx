import React, { useEffect, useState } from 'react';
import TableColumns from '../TableColumnsView/TableColumns';
import { DataStreamMappingSpec, TableStatisticsModel } from '../../api';
import { AxiosResponse } from 'axios';
import { TableApiClient, DataStreamsApi } from '../../services/apiClient';
import Loader from '../../components/Loader';
import { useDispatch, useSelector } from 'react-redux';
import { getFirstLevelState } from '../../redux/selectors';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { useHistory, useParams } from 'react-router-dom';
import moment from 'moment';
import { formatNumber } from '../../shared/constants';
import { setCreatedDataStream } from '../../redux/actions/rule.actions';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import { LocationState } from '../TableColumnsView/TableColumnsFunctions';

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
  const [nameOfDataStream, setNameOfDataStream] = useState<string>('');
  const [levels, setLevels] = useState<DataStreamMappingSpec>({});
  const [selected, setSelected] = useState<number>(0);
  const [stringCount, setStringCount] = useState(0);
  const dispatch = useDispatch();
  const history = useHistory();
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
  const updateData = (nameOfDS: string): void => {
    setNameOfDataStream(nameOfDS);
  };

  const setLevelsData = (levelsToSet: DataStreamMappingSpec): void => {
    setLevels(levelsToSet);
  };

  const setNumberOfSelected = (param: number): void => {
    setSelected(param);
  };

  if (loading) {
    return (
      <div className="flex justify-center min-h-80">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }

  const doNothing = (): void => {};
  const postDataStream = async () => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      'sources',
      connectionName,
      schemaName,
      tableName,
      'data-streams'
    );
    const value = ROUTES.TABLE_LEVEL_VALUE(
      'sources',
      connectionName,
      schemaName,
      tableName
    );
    const data: LocationState = {
      bool: true,
      data_stream_name: nameOfDataStream,
      spec: levels
    };

    try {
      const response = await DataStreamsApi.createDataStream(
        connectionName,
        schemaName,
        tableName,
        { data_stream_name: nameOfDataStream, spec: levels }
      );
      if (response.status === 409) {
        doNothing();
      }
    } catch (error: any) {
      if (error.response && error.response.status) {
        doNothing();
      }
    }
    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url,
        value,
        state: data,
        label: tableName
      })
    );
    history.push(url);
    setCreatedDataStream(false, '', {});
  };

  const implementDataStreamName = () => {
    let count = 0;
    const columnValues = Object.values(levels)
      .map((level) => level.column)
      .filter((column) => column !== undefined);
    const joinedValues = columnValues.join(',');
    count = columnValues.length;

    setStringCount(count);

    return joinedValues;
  };
  console.log(selected);

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
        updateData={updateData}
        setLevelsData={setLevelsData}
        setNumberOfSelected={setNumberOfSelected}
      />
    </div>
  );
}
