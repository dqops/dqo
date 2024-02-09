import React, { useEffect, useState } from 'react';
import Input from '../../Input';
import Checkbox from '../../Checkbox';
import ActionGroup from './TableActionGroup';
import { useSelector } from 'react-redux';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableBasic,
  setUpdatedTableBasic,
  updateTableBasic
} from '../../../redux/actions/table.actions';
import { useParams } from 'react-router-dom';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../../redux/selectors';
import { CheckTypes } from '../../../shared/routes';
import Select from '../../Select';
import {
  ConnectionModel,
  ConnectionSpecProviderTypeEnum,
  CsvFileFormatSpec,
  TableListModelProfilingChecksResultTruncationEnum
} from '../../../api';
import NumberInput from '../../NumberInput';
import clsx from 'clsx';
import { IRootState } from '../../../redux/reducers';
import FileFormatConfiguration from '../../FileFormatConfiguration/FileFormatConfiguration';
import { ConnectionApiClient } from '../../../services/apiClient';
enum fileFormat {
  csv = 'csv_file_format',
  json = 'json_file_format',
  parquet = 'parquet_file_format',
  file_path_list = 'file_path_list',
  file_path = 'file_path'
}

type TConfiguration = CsvFileFormatSpec;
//add json parquet type
// type TConfiguration = CsvFileFormatSpec | JsonFileFormat | ParquetFileFormat etc

const TableDetails = () => {
  const {
    checkTypes,
    connection,
    schema,
    table
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
  } = useParams();
  const { tableBasic, isUpdating, isUpdatedTableBasic } = useSelector(
    getFirstLevelState(checkTypes)
  );
  const [connectionModel, setConnectionModel] = useState<ConnectionModel>({});
  const [paths, setPaths] = useState<Array<string>>(['']);
  const [fileFormatType, setfileFormatType] = useState<fileFormat>(
    fileFormat.csv
  );
  const [configuration, setConfiguration] = useState<TConfiguration>({});

  const onChangeConfiguration = (params: Partial<TConfiguration>) => {
    setConfiguration((prev) => ({
      ...prev,
      ...params
    }));
  };

  const cleanConfiguration = () => {
    setConfiguration({});
  };

  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  useEffect(() => {
    dispatch(
      getTableBasic(checkTypes, firstLevelActiveTab, connection, schema, table)
    );
    const getConnectionBasic = async () => {
      await ConnectionApiClient.getConnectionBasic(connection).then((res) =>
        setConnectionModel(res.data)
      );
    };
    getConnectionBasic();
  }, [checkTypes, firstLevelActiveTab, connection, schema, table]);

  const handleChange = (obj: any) => {
    dispatch(
      setUpdatedTableBasic(checkTypes, firstLevelActiveTab, {
        ...tableBasic,
        ...obj
      })
    );
  };

  const onUpdate = async () => {
    if (!tableBasic) {
      return;
    }
    await dispatch(
      updateTableBasic(
        checkTypes,
        firstLevelActiveTab,
        connection,
        schema,
        table,
        tableBasic
      )
    );
    await dispatch(
      getTableBasic(checkTypes, firstLevelActiveTab, connection, schema, table)
    );
  };

  const onChangePath = (value: string) => {
    const copiedPaths = [...paths];
    copiedPaths[paths.length - 1] = value;
    setPaths(copiedPaths);
  };

  const onAddPath = () => setPaths((prev) => [...prev, '']);

  const onChangeFile = (val: fileFormat) => setfileFormatType(val);

  const onDeletePath = (index: number) =>
    setPaths((prev) => prev.filter((x, i) => i !== index));

  return (
    <div className="p-4">
      <ActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedTableBasic}
        isUpdating={isUpdating}
      />

      <table
        className={clsx(
          'mb-6 w-160 text-sm',
          userProfile.can_manage_data_sources
            ? ''
            : 'cursor-not-allowed pointer-events-none'
        )}
      >
        <tbody>
          <tr>
            <td className="px-4 py-2">Connection Name</td>
            <td className="px-4 py-2">{tableBasic?.connection_name}</td>
          </tr>
          <tr>
            <td className="px-4 py-2">Schema Name</td>
            <td className="px-4 py-2">{tableBasic?.target?.schema_name}</td>
          </tr>
          <tr>
            <td className="px-4 py-2">Table Name</td>
            <td className="px-4 py-2">{tableBasic?.target?.table_name}</td>
          </tr>
          <tr>
            <td className="px-4 py-2">Disable data quality checks</td>
            <td className="px-4 py-2">
              <div className="flex">
                <Checkbox
                  onChange={(value) => handleChange({ disabled: value })}
                  checked={tableBasic?.disabled}
                />
              </div>
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">Filter</td>
            <td className="px-4 py-2">
              <textarea
                className="focus:ring-1 focus:ring-teal-500 focus:ring-opacity-80 focus:border-0 border-gray-300 font-regular text-sm h-26 placeholder-gray-500 py-0.5 px-3 border text-gray-900 focus:text-gray-900 focus:outline-none min-w-40 w-full  rounded"
                value={tableBasic?.filter}
                onChange={(e) => handleChange({ filter: e.target.value })}
              ></textarea>
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">Priority</td>
            <td className="px-4 py-2">
              <NumberInput
                value={
                  tableBasic?.priority !== 0 ? tableBasic?.priority : undefined
                }
                onChange={(value) => handleChange({ priority: value })}
                className="min-w-30 w-1/2"
                placeholder=""
              />
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">Stage</td>
            <td className="px-4 py-2">
              <Input
                value={tableBasic?.stage}
                onChange={(e) => handleChange({ stage: e.target.value })}
              />
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">Profiling checks result truncation</td>
            <td className="px-4 py-2">
              <Select
                options={[
                  { label: '', value: undefined },
                  ...Object.values(
                    TableListModelProfilingChecksResultTruncationEnum
                  ).map((x) => ({
                    label: x
                      ?.replaceAll('_', ' ')
                      .replace(/./, (c) => c.toUpperCase()),
                    value: x
                  }))
                ]}
                value={
                  tableBasic?.advanced_profiling_result_truncation ??
                  TableListModelProfilingChecksResultTruncationEnum.one_per_month
                }
                onChange={(selected) =>
                  handleChange({
                    advanced_profiling_result_truncation: selected
                  })
                }
                placeholder=""
                empty={true}
              />
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">Table Hash</td>
            <td className="px-4 py-2">{tableBasic?.table_hash}</td>
          </tr>
        </tbody>
      </table>
      {/* {connectionModel.provider_type ===
      ConnectionSpecProviderTypeEnum.duckdb ? ( */}
      <FileFormatConfiguration
        paths={paths}
        onAddPath={onAddPath}
        onChangePath={onChangePath}
        fileFormatType={fileFormatType}
        onChangeFile={onChangeFile}
        configuration={configuration}
        onChangeConfiguration={onChangeConfiguration}
        cleanConfiguration={cleanConfiguration}
        onDeletePath={onDeletePath}
      />
      {/* ) : (
        <></>
      )} */}
    </div>
  );
};

export default TableDetails;
