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
import { useParams } from "react-router-dom";
import { getFirstLevelActiveTab, getFirstLevelState } from "../../../redux/selectors";
import { CheckTypes } from "../../../shared/routes";

const TableDetails = () => {
  const { checkTypes, connection, schema, table }: { checkTypes: CheckTypes, connection: string, schema: string, table: string } = useParams();
  const { tableBasic, isUpdating, isUpdatedTableBasic } = useSelector(getFirstLevelState(checkTypes));
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  useEffect(() => {
    dispatch(getTableBasic(checkTypes, firstLevelActiveTab, connection, schema, table));
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
      updateTableBasic(checkTypes, firstLevelActiveTab, connection, schema, table, tableBasic)
    );
    await dispatch(getTableBasic(checkTypes, firstLevelActiveTab, connection, schema, table));
  };

  return (
    <div className="p-4">
      <ActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedTableBasic}
        isUpdating={isUpdating}
      />

      <table className="mb-6 w-160">
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
              <Input
                value={tableBasic?.filter}
                onChange={(e) => handleChange({ filter: e.target.value })}
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
            <td className="px-4 py-2">Table Hash</td>
            <td className="px-4 py-2">{tableBasic?.table_hash}</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};

export default TableDetails;
