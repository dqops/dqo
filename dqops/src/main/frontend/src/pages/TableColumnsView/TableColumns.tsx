import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  ColumnCurrentDataQualityStatusModel,
  ColumnListModel,
  ColumnStatisticsModel
} from '../../api';
import Loader from '../../components/Loader';
import { getFirstLevelState } from '../../redux/selectors';
import { CheckResultApi, ColumnApiClient } from '../../services/apiClient';
import { CheckTypes } from '../../shared/routes';
import { limitTextLength, useDecodedParams } from '../../utils';
import ConfirmDialog from './ConfirmDialog';
import TableColumnsBody from './TableColumnsBody';
import { ITableColumnsProps, MyData } from './TableColumnsConstans';
import TableColumnsHeader from './TableColumnsHeader';
import { renderValue } from './TableColumnsUtils';

const rewriteDimensions = (columnStatus: {
  [key: string]: ColumnCurrentDataQualityStatusModel;
}) => {
  const obj: any = {};
  Object.entries(columnStatus).forEach(([key, value]) => {
    obj[key] = value.dimensions;
  });
  return obj;
};

const getLabelsOverview = (labels: string[]) => {
  return limitTextLength(labels.join(', '), 30);
};

const TableColumns = ({
  connectionName,
  schemaName,
  tableName,
  checkedColumns,
  setCheckedColumns,
  statistics,
  refreshListFunc
}: ITableColumnsProps) => {
  const { checkTypes }: { checkTypes: CheckTypes } = useDecodedParams();
  const [isOpen, setIsOpen] = useState(false);
  const [selectedColumn, setSelectedColumn] = useState<ColumnStatisticsModel>();
  const [sortedArray, setSortedArray] = useState<MyData[]>();

  const [status, setStatus] = useState<{
    [key: string]: ColumnCurrentDataQualityStatusModel;
  }>({});
  const [columns, setColumns] = useState<ColumnListModel[]>([]);
  const [dataArray, setDataArray] = useState<MyData[]>([]);
  const { loading } = useSelector(getFirstLevelState(CheckTypes.SOURCES));
  const onRemoveColumn = (column: ColumnStatisticsModel) => {
    setIsOpen(true);
    setSelectedColumn(column);
  };

  const removeColumn = async () => {
    console.log(selectedColumn);
    if (selectedColumn?.column_name) {
      await ColumnApiClient.deleteColumn(
        connectionName,
        schemaName,
        tableName,
        selectedColumn?.column_name
      );
    }
    setDataArray(
      dataArray.filter((x) => x.columnHash !== selectedColumn?.column_hash)
    );
    setSortedArray(
      sortedArray?.filter((x) => x.columnHash !== selectedColumn?.column_hash)
    );
  };

  useEffect(() => {
    if (statistics?.column_statistics) {
      const newDataArray: MyData[] = statistics.column_statistics.map(
        (column) => {
          const nullPercent = column.statistics?.find(
            (item) => item.collector === 'nulls_percent'
          );
          const uniqueCount = column.statistics?.find(
            (item) => item.collector === 'distinct_count'
          );
          const nullCount = column.statistics?.find(
            (item) => item.collector === 'nulls_count'
          );
          const detectedDatatype = column.statistics?.find(
            (item) => item.collector === 'text_datatype_detect'
          );
          const minimalValue = column.statistics?.find(
            (item) => item.collector === 'min_value'
          );
          const maximumValue = column.statistics?.find(
            (item) => item.collector === 'max_value'
          );
          const length = column.type_snapshot?.length;
          const scale = column.type_snapshot?.scale;
          const type = column.type_snapshot?.column_type;
          const hash = column.column_hash;

          return {
            null_percent: Number(renderValue(nullPercent?.result)),
            unique_value: Number(renderValue(uniqueCount?.result)),
            null_count: Number(renderValue(nullCount?.result)),
            detectedDatatypeVar: Number(renderValue(detectedDatatype?.result)),
            nameOfCol: column.column_name,
            minimalValue: renderValue(minimalValue?.result),
            maximumValue: renderValue(maximumValue?.result),
            length: renderValue(length),
            scale: renderValue(scale),
            importedDatatype: type,
            columnHash: hash ?? 0,
            isColumnSelected: false,
            dimentions: rewriteDimensions(status)[column.column_name ?? ''],
            labels: getLabelsOverview(
              columns.find((col) => col.column_name === column.column_name)
                ?.labels ?? []
            ),
            id: columns.find((col) => col.column_name === column.column_name)
              ?.id
          };
        }
      );

      setDataArray(newDataArray);
    }
  }, [statistics, status, columns]);

  useEffect(() => {
    const fetchStatusAndColumns = async () => {
      try {
        const tableDataQualityStatus =
          await CheckResultApi.getTableDataQualityStatus(
            connectionName,
            schemaName,
            tableName,
            undefined,
            undefined,
            checkTypes === CheckTypes.PROFILING,
            checkTypes === CheckTypes.MONITORING ||
              checkTypes === CheckTypes.SOURCES,
            checkTypes === CheckTypes.PARTITIONED ||
              checkTypes === CheckTypes.SOURCES
          );
        setStatus(tableDataQualityStatus.data.columns ?? {});

        const columnList = await ColumnApiClient.getColumns(
          connectionName,
          schemaName,
          tableName,
          true,
          checkTypes == CheckTypes.SOURCES ? undefined : checkTypes
        );
        setColumns(columnList.data);
      } catch (error) {
        console.error('Error fetching data', error);
      }
    };

    fetchStatusAndColumns();
  }, [checkTypes, connectionName, schemaName, tableName]);

  const rewriteData = (hashValue: number) => {
    const columnToDelete = statistics?.column_statistics?.find(
      (x) => x.column_hash === hashValue
    );
    console.log('columnToDelete', columnToDelete);
    if (columnToDelete) {
      Promise.resolve()
        .then(() => onRemoveColumn(columnToDelete))
        .catch((error) => console.error(error));
    }
  };

  if (loading) {
    return (
      <div className="flex justify-center min-h-80">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }
  const handleSorting = (data: MyData[]) => {
    const arr = [...data];
    setSortedArray(arr);
  };

  useEffect(() => {
    CheckResultApi.getTableDataQualityStatus(
      connectionName,
      schemaName,
      tableName,
      undefined,
      undefined,
      checkTypes === CheckTypes.PROFILING,
      checkTypes === CheckTypes.MONITORING || checkTypes === CheckTypes.SOURCES,
      checkTypes === CheckTypes.PARTITIONED || checkTypes === CheckTypes.SOURCES
    ).then((res) => setStatus(res.data.columns ?? {}));
    ColumnApiClient.getColumns(
      connectionName,
      schemaName,
      tableName,
      true,
      checkTypes == CheckTypes.SOURCES ? undefined : checkTypes
    ).then((res) => setColumns(res.data));
  }, [checkTypes, connectionName, schemaName, tableName]);

  const handleChangeCheckedColumns = (columnName: string) => {
    if (checkedColumns?.includes(columnName)) {
      setCheckedColumns?.(checkedColumns.filter((col) => col !== columnName));
    } else {
      setCheckedColumns?.([...(checkedColumns ?? []), columnName]);
    }
  };

  return (
    <div className="p-4 relative">
      <table className=" mt-4 w-full">
        <TableColumnsHeader
          dataArray={sortedArray || dataArray}
          setSortedArray={handleSorting}
        />
        <TableColumnsBody
          columns={sortedArray || dataArray}
          statistics={statistics}
          rewriteData={rewriteData}
          checkedColumns={checkedColumns ?? []}
          handleChangeCheckedColumns={handleChangeCheckedColumns}
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
