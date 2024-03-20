import clsx from 'clsx';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import {
  ConnectionModel,
  ConnectionSpecProviderTypeEnum,
  DuckdbParametersSpecFilesFormatTypeEnum,
  FileFormatSpec,
  TableListModelProfilingChecksResultTruncationEnum
} from '../../../api';
import { TConfiguration } from '../../../components/FileFormatConfiguration/TConfiguration';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableBasic,
  setUpdatedTableBasic,
  updateTableBasic
} from '../../../redux/actions/table.actions';
import { IRootState } from '../../../redux/reducers';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../../redux/selectors';
import { ConnectionApiClient } from '../../../services/apiClient';
import { CheckTypes } from '../../../shared/routes';
import Checkbox from '../../Checkbox';
import FileFormatConfiguration from '../../FileFormatConfiguration/FileFormatConfiguration';
import FilePath from '../../FileFormatConfiguration/FilePath';
import Input from '../../Input';
import NumberInput from '../../NumberInput';
import Select from '../../Select';
import ActionGroup from './TableActionGroup';

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
  const format =
    (Object.keys(tableBasic?.file_format ?? {}).find((x) =>
      x.includes('format')
    ) as DuckdbParametersSpecFilesFormatTypeEnum) ??
    DuckdbParametersSpecFilesFormatTypeEnum.csv;

  const [connectionModel, setConnectionModel] = useState<ConnectionModel>({});

  const [fileFormatType, setFileFormatType] =
    useState<DuckdbParametersSpecFilesFormatTypeEnum>(format);

  const onChangeConfiguration = (params: Partial<TConfiguration>) => {
    // setConfiguration((prev) => ({
    //   ...prev,
    //   ...params
    // }));
    handleChange({
      file_format: {
        ...tableBasic.file_format,
        [fileFormatType as keyof FileFormatSpec]: {
          ...tableBasic.file_format?.[fileFormatType as keyof FileFormatSpec],
          ...params
        }
      }
    });
  };

  const cleanConfiguration = () => {
    // setConfiguration({});
  };

  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  useEffect(() => {
    dispatch(
      getTableBasic(checkTypes, firstLevelActiveTab, connection, schema, table)
    ).then(() => {
      // if (tableBasic?.file_format?.[format]) {
      //   setConfiguration(tableBasic?.file_format[format]);
      // }
      // if (tableBasic?.file_format?.file_paths) {
      //   setPaths([...tableBasic.file_format.file_paths, '']);
    });
    const getConnectionBasic = async () => {
      await ConnectionApiClient.getConnectionBasic(connection).then((res) =>
        setConnectionModel(res.data)
      );
    };
    getConnectionBasic();
  }, [checkTypes, connection, schema, table]);

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

  const onAddPath = () => {
    handleChange({
      file_format: {
        ...tableBasic.file_format,
        file_paths: [...tableBasic.file_format.file_paths, '']
      }
    });
    // setPaths((prev) => [...prev, '']);
  };
  const onChangePath = (value: string, index?: number) => {
    const copiedPaths = [...(tableBasic.file_format.file_paths as any[])];
    if (index !== undefined) {
      copiedPaths[index] = value;
    } else {
      copiedPaths[(tableBasic.file_format.file_paths as any[]).length - 1] =
        value;
    }
    handleChange({
      file_format: {
        ...tableBasic.file_format,
        file_paths: copiedPaths
      }
    });
    // setPaths(copiedPaths);
  };
  const onDeletePath = (index: number) => {
    // setPaths((prev) => prev.filter((_, i) => i !== index));
    handleChange({
      file_format: {
        ...tableBasic.file_format,
        file_paths: (tableBasic.file_format.file_paths as any[]).filter(
          (_, i) => i !== index
        )
      }
    });
  };

  const onChangeFile = (val: DuckdbParametersSpecFilesFormatTypeEnum) => {
    handleChange({
      file_format: {
        ...tableBasic.file_format,
        [fileFormatType]: undefined,
        [val]: {}
      }
    });
    setFileFormatType(val);
  };

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
            <td className="px-4 py-2">Connection name</td>
            <td className="px-4 py-2">{tableBasic?.connection_name}</td>
          </tr>
          <tr>
            <td className="px-4 py-2">Schema name</td>
            <td className="px-4 py-2">{tableBasic?.target?.schema_name}</td>
          </tr>
          <tr>
            <td className="px-4 py-2">Table name</td>
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
                options={Object.values(
                  TableListModelProfilingChecksResultTruncationEnum
                ).map((x) => ({
                  label: x
                    ?.replaceAll('_', ' ')
                    .replace(/./, (c) => c.toUpperCase()),
                  value: x
                }))}
                value={
                  tableBasic?.advanced_profiling_result_truncation ??
                  TableListModelProfilingChecksResultTruncationEnum.store_the_most_recent_result_per_month
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
            <td className="px-4 py-2">Table hash</td>
            <td className="px-4 py-2">{tableBasic?.table_hash}</td>
          </tr>
        </tbody>
      </table>
      {connectionModel.provider_type ===
        ConnectionSpecProviderTypeEnum.duckdb && (
        <FileFormatConfiguration
          fileFormatType={fileFormatType}
          onChangeFile={onChangeFile}
          configuration={tableBasic?.file_format?.[format]}
          onChangeConfiguration={onChangeConfiguration}
          cleanConfiguration={cleanConfiguration}
          freezeFileType={false}
        >
          <FilePath
            paths={tableBasic?.file_format?.file_paths as any[]}
            onAddPath={onAddPath}
            onChangePath={onChangePath}
            onDeletePath={onDeletePath}
          />
        </FileFormatConfiguration>
      )}
    </div>
  );
};

export default TableDetails;
