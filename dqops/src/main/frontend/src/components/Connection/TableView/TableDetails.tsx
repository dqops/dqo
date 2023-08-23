import React, { useEffect } from 'react';
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
import { TableBasicModelAdvancedProfilingResultTruncationEnum } from '../../../api';
import NumberInput from '../../NumberInput';

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
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  useEffect(() => {
    dispatch(
      getTableBasic(checkTypes, firstLevelActiveTab, connection, schema, table)
    );
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

  return (
    <div className="p-4">
      <ActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedTableBasic}
        isUpdating={isUpdating}
      />

      <table className="mb-6 w-160 text-sm">
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
            <td className="px-4 py-2">Advanced profile results storage</td>
            <td className="px-4 py-2">
              <Select
                options={[
                  { label: '', value: undefined },
                  ...Object.values(
                    TableBasicModelAdvancedProfilingResultTruncationEnum
                  ).map((x) => ({ label: x, value: x }))
                ]}
                value={tableBasic?.advanced_profiling_result_truncation}
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
    </div>
  );
};

export default TableDetails;
