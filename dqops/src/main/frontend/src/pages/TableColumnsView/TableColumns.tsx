import React, { useEffect, useLayoutEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  CheckCurrentDataQualityStatusModelCurrentSeverityEnum,
  ColumnCurrentDataQualityStatusModel,
  ColumnStatisticsModel,
  DataGroupingConfigurationSpec
} from '../../api';
import Loader from '../../components/Loader';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setCreatedDataStream } from '../../redux/actions/definition.actions';
import { getFirstLevelState } from '../../redux/selectors';
import { CheckResultApi, ColumnApiClient } from '../../services/apiClient';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import ConfirmDialog from './ConfirmDialog';
import TableColumnsBody from './TableColumnsBody';
import { ITableColumnsProps, MyData, spec } from './TableColumnsConstans';
import TableColumnsHeader from './TableColumnsHeader';
import { renderValue } from './TableColumnsUtils';

const getSeverityLevel = (severity: CheckCurrentDataQualityStatusModelCurrentSeverityEnum | undefined) => {
  switch (severity) {
    case 'execution_error': 
      return 4;
    case 'fatal' : 
      return 3;
    case 'error': 
      return 2;
    case 'warning': 
      return 1;
    case 'valid':
      return 0;        
  }
  return 4;
} 

const rewriteDimensions = (columnStatus : { [key: string]: ColumnCurrentDataQualityStatusModel }) => {
  const columnValues: any = {}; 
  Object.entries(columnStatus).forEach(([key, value]) => {
    const dimentions: Array<any> = [];
    Object.entries(value.checks ?? {}).forEach(([key, value]) => {
     dimentions.push({ quality_dimension: value.quality_dimension, current_severity: value.current_severity,
       highest_historical_severity: value.highest_historical_severity, last_executed_at: value.last_executed_at,
        check_name: key, category: value.category, severity_level: getSeverityLevel(value.current_severity) });
    })
    columnValues[key] = dimentions; 
  })
  return columnValues;
}

const TableColumns = ({
  connectionName,
  schemaName,
  tableName,
  updateData,
  setLevelsData,
  setNumberOfSelected,
  statistics,
  onChangeSelectedColumns,
  refreshListFunc
}: ITableColumnsProps) => {
  const {checkTypes} : {checkTypes: CheckTypes} = useDecodedParams()
  const [isOpen, setIsOpen] = useState(false);
  const [selectedColumn, setSelectedColumn] = useState<ColumnStatisticsModel>();
  const [sortedArray, setSortedArray] = useState<MyData[]>();
  const [objectStates, setObjectStates] = useState<{ [key: string]: boolean }>(
    {}
  );
  const [shouldResetCheckboxes, setShouldResetCheckboxes] = useState(false);
  const [status, setStatus] = useState<{ [key: string]: ColumnCurrentDataQualityStatusModel }>({})

  const handleButtonClick = (name: string) => {
    setObjectStates((prevStates) => ({
      ...prevStates,
      [name]: !prevStates[name]
    }));
  };
  const actionDispatch = useActionDispatch();
  const { loading } = useSelector(getFirstLevelState(CheckTypes.SOURCES));
  const onRemoveColumn = (column: ColumnStatisticsModel) => {
    setIsOpen(true);
    setSelectedColumn(column);
  };

  const removeColumn = async () => {
    if (selectedColumn?.column_name) {
      await ColumnApiClient.deleteColumn(
        connectionName,
        schemaName,
        tableName,
        selectedColumn?.column_name
      );
    }
  };

  useEffect(() => {
    setShouldResetCheckboxes(true);
  }, [connectionName, schemaName, tableName]);

  useLayoutEffect(() => {
    if (shouldResetCheckboxes) {
      setObjectStates({});
      setShouldResetCheckboxes(false);
    }
  }, [shouldResetCheckboxes]);

  const nullPercentData = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'nulls_percent')
      .map((item) => Number(renderValue(item.result)))
  );
  const uniqueCountData = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'distinct_count')
      .map((item) => item.result)
  );
  const nullCountData = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'nulls_count')
      .map((item) => renderValue(item.result))
  );
  const detectedDatatypeVar = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'text_datatype_detect')
      .map((item) => item.result)
  );

  const columnNameData = statistics?.column_statistics?.map(
    (x) => x.column_name
  );
  const minimalValueData = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'min_value')
      .map((item) => item.result)
  );

  const maximumValueData = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'max_value')
      .map((item) => item.result)
  );
  const lengthData = statistics?.column_statistics?.map(
    (x) => x.type_snapshot?.length
  );

  const scaleData = statistics?.column_statistics?.map(
    (x) => x.type_snapshot?.scale
  );

  const typeData = statistics?.column_statistics?.map(
    (x) => x.type_snapshot?.column_type
  );

  const hashData = statistics?.column_statistics?.map((x) => x.column_hash);

  const dataArray: MyData[] = [];
  if (columnNameData && hashData) {
    const maxLength = Math.max(columnNameData.length, hashData.length);

    for (let i = 0; i < maxLength; i++) {
      const newData: MyData = {
        null_percent: Number(renderValue(nullPercentData?.[i])),
        unique_value: Number(renderValue(uniqueCountData?.[i])),
        null_count: Number(renderValue(nullCountData?.[i])),
        detectedDatatypeVar: Number(detectedDatatypeVar?.[i]),
        nameOfCol: columnNameData?.[i],
        minimalValue: renderValue(minimalValueData?.[i]),
        maximumValue: renderValue(maximumValueData?.[i]),
        length: renderValue(lengthData?.[i]),
        scale: renderValue(scaleData?.[i]),
        importedDatatype: typeData?.[i],
        columnHash: Number(hashData?.[i]),
        isColumnSelected: false,
        dimentions: rewriteDimensions(status)[columnNameData?.[i] ?? '']
      };

      dataArray.push(newData);
    }
  }

  const rewriteData = (hashValue: number) => {
    const columnToDelete = statistics?.column_statistics?.find(
      (x) => x.column_hash === hashValue
    );

    if (columnToDelete) {
      Promise.resolve()
        .then(() => onRemoveColumn(columnToDelete))
        .catch((error) => console.error(error));
    }
  };

  const countTrueValues = (obj: Record<string, boolean>): number => {
    let count = 0;
    for (const key in obj) {
      if (obj[key] === true) {
        count++;
      }
    }
    setNumberOfSelected(count);
    return count;
  };
  const selectStrings = (obj: Record<string, boolean>) => {
    return Object.keys(obj).filter((key) => obj[key] === true);
  };
  const listOfStr = selectStrings(objectStates);

  const setSpec2 = () => {
    const newSpec: DataGroupingConfigurationSpec = {};

    for (let i = 1; i <= 9; i++) {
      const levelKey = `level_${i}` as keyof DataGroupingConfigurationSpec;
      const level = spec[levelKey];

      if (level) {
        level.column = listOfStr.at(i - 1);

        if (level.column !== undefined) {
          level.source = 'column_value';
          newSpec[levelKey] = level;
        }
      }
    }

    return newSpec;
  };

  const fixString = () => {
    const columnValues = Object.values(spec)
      .map((level) => level.column)
      .filter((column) => column !== undefined);

    const joinedValues = columnValues.join(',');

    return joinedValues;
  };

  const showDataStreamButtonFunc = async () => {
    await actionDispatch(setCreatedDataStream(true, fixString(), setSpec2()));
  };

  const setAllSelectedColumns = () => {
    const keysWithTrueValues = [];
    for (const key in objectStates) {
      if (objectStates[key] === true) {
        keysWithTrueValues.push(key);
      }
    }
    onChangeSelectedColumns && onChangeSelectedColumns(keysWithTrueValues);
  };

  useEffect(() => {
    const joinedValues = fixString();
    setLevelsData(setSpec2());
    countTrueValues(objectStates);
    updateData(joinedValues);
    setCreatedDataStream(true, fixString(), setSpec2());
    setAllSelectedColumns();
  }, [spec, objectStates]);

  if (loading) {
    return (
      <div className="flex justify-center min-h-80">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }
  const handleSorting  = (data: MyData[]) => {
    console.log(data);
    setSortedArray(data);
  }

  useEffect(() => {
    CheckResultApi.getTableDataQualityStatus(connectionName, schemaName, tableName, undefined, undefined, checkTypes === CheckTypes.PROFILING ? true : undefined).then((res) =>  setStatus(res.data.columns ?? {}))
  }, [connectionName, schemaName, tableName]);
  return (
    <div className="p-4 relative">
      <table className=" mt-4 w-full">
        <TableColumnsHeader
          dataArray={sortedArray || dataArray}
          updateData={updateData}
          setSortedArray={handleSorting}
        />
        <TableColumnsBody
          columns={sortedArray || dataArray}
          objectStates={objectStates}
          statistics={statistics}
          rewriteData={rewriteData}
          showDataStreamButtonFunc={showDataStreamButtonFunc}
          handleButtonClick={handleButtonClick}
          refreshListFunc={refreshListFunc}
        />
      </table>
      <ConfirmDialog
        open={isOpen}
        onClose={() => setIsOpen(false)}
        column={selectedColumn}
        onConfirm={removeColumn}
      />
    </div>
  );
};

export default TableColumns;
