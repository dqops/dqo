import clsx from 'clsx';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  ConnectionModel,
  ConnectionSpecProviderTypeEnum,
  DuckdbParametersSpecFilesFormatTypeEnum,
  FileFormatSpec,
  SharedCredentialListModel,
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
import {
  ConnectionApiClient,
  SharedCredentialsApi
} from '../../../services/apiClient';
import { CheckTypes } from '../../../shared/routes';
import { useDecodedParams } from '../../../utils';
import AdvancedProperties from '../../AdvancedProperties/AdvancedProperties';
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
  } = useDecodedParams();
  const { tableBasic, isUpdating, isUpdatedTableBasic } = useSelector(
    getFirstLevelState(checkTypes)
  );
  const format = Object.keys(tableBasic?.file_format ?? {}).find((x) => {
    return DuckdbParametersSpecFilesFormatTypeEnum[
      x as keyof typeof DuckdbParametersSpecFilesFormatTypeEnum
    ];
  });
  const [connectionModel, setConnectionModel] = useState<ConnectionModel>({});
  const [sharedCredentials, setSharedCredentials] = useState<
    SharedCredentialListModel[]
  >([]);
  const [fileFormatType, setFileFormatType] =
    useState<DuckdbParametersSpecFilesFormatTypeEnum>(
      (format as DuckdbParametersSpecFilesFormatTypeEnum) ??
        DuckdbParametersSpecFilesFormatTypeEnum.csv
    );

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
    );
    const getConnectionBasic = async () => {
      await ConnectionApiClient.getConnectionBasic(connection).then((res) =>
        setConnectionModel(res.data)
      );
    };
    const getSharedCredentials = async () => {
      await SharedCredentialsApi.getAllSharedCredentials().then((res) =>
        setSharedCredentials(res.data)
      );
    };
    getSharedCredentials();
    getConnectionBasic();
  }, [checkTypes, connection, schema, table]);

  useEffect(() => {
    if (!tableBasic || !tableBasic?.file_format) {
      return;
    }

    setFileFormatType(
      (format as DuckdbParametersSpecFilesFormatTypeEnum) ??
        DuckdbParametersSpecFilesFormatTypeEnum.csv
    );
  }, [tableBasic?.file_format]);

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
    const copiedPaths = [
      ...(tableBasic.file_format.file_paths ?? ([] as any[]))
    ];
    if (index !== undefined) {
      copiedPaths[index] = value;
    } else {
      if (!copiedPaths.length) {
        copiedPaths.push(value);
      }
      copiedPaths[
        (tableBasic.file_format.file_paths ?? ([] as any[])).length - 1
      ] = value;
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
        {TableDetailBody({ tableBasic, handleChange })}
      </table>
      <AdvancedProperties
        properties={tableBasic?.advanced_properties}
        handleChange={handleChange}
        sharedCredentials={sharedCredentials}
      />
      {connectionModel.provider_type ===
        ConnectionSpecProviderTypeEnum.duckdb && (
        <FileFormatConfiguration
          fileFormatType={fileFormatType}
          onChangeFile={onChangeFile}
          configuration={tableBasic?.file_format?.[fileFormatType]}
          onChangeConfiguration={onChangeConfiguration}
          cleanConfiguration={cleanConfiguration}
          freezeFileType
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

const TableDetailBody = ({
  tableBasic,
  handleChange
}: {
  tableBasic: any;
  handleChange: any;
}) => {
  const filter = tableBasic?.filter || '';
  const priority = tableBasic?.priority || '';
  const stage = tableBasic?.stage || '';

  return (
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
        <td className="px-4 py-2">
          Do not collect error samples for profiling checks
        </td>
        <td className="px-4 py-2">
          <div className="flex">
            <Checkbox
              onChange={(value) =>
                handleChange({
                  do_not_collect_error_samples_in_profiling: value
                })
              }
              checked={tableBasic?.do_not_collect_error_samples_in_profiling}
            />
          </div>
        </td>
      </tr>
      <tr>
        <td className="px-4 py-2">
          Always collect error samples for scheduled monitoring checks
        </td>
        <td className="px-4 py-2">
          <div className="flex">
            <Checkbox
              onChange={(value) =>
                handleChange({ always_collect_error_samples: value })
              }
              checked={tableBasic?.always_collect_error_samples}
            />
          </div>
        </td>
      </tr>
      <tr>
        <td className="px-4 py-2">Filter</td>
        <td className="px-4 py-2">
          <textarea
            className="focus:ring-1 focus:ring-teal-500 focus:ring-opacity-80 focus:border-0 border-gray-300 font-regular text-sm h-26 placeholder-gray-500 py-0.5 px-3 border text-gray-900 focus:text-gray-900 focus:outline-none min-w-40 w-full  rounded !min-h-10"
            value={filter}
            onChange={(e) => handleChange({ filter: e.target.value })}
          ></textarea>
        </td>
      </tr>
      <tr>
        <td className="px-4 py-2">Priority</td>
        <td className="px-4 py-2">
          <NumberInput
            value={priority}
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
            value={stage}
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
              tableBasic?.profiling_checks_result_truncation ??
              TableListModelProfilingChecksResultTruncationEnum.store_the_most_recent_result_per_month
            }
            onChange={(selected) =>
              handleChange({
                profiling_checks_result_truncation: selected
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
  );
};

export default TableDetails;
