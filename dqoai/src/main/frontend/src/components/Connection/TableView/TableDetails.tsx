import React, { useEffect } from 'react';
import Input from '../../Input';
import Checkbox from '../../Checkbox';
import ActionGroup from './TableActionGroup';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableBasic,
  setUpdatedTableBasic,
  updateTableBasic
} from '../../../redux/actions/table.actions';

interface ITableDetailsProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

const TableDetails = ({
  connectionName,
  schemaName,
  tableName
}: ITableDetailsProps) => {
  const { tableBasic, isUpdating, isUpdatedTableBasic } = useSelector(
    (state: IRootState) => state.table
  );
  const dispatch = useActionDispatch();

  useEffect(() => {
    if (
      !tableBasic ||
      tableBasic?.connection_name !== connectionName ||
      tableBasic?.target?.schema_name !== schemaName ||
      tableBasic?.target?.table_name !== tableName
    ) {
      dispatch(getTableBasic(connectionName, schemaName, tableName));
    }
  }, [connectionName, schemaName, tableName, tableBasic]);

  const handleChange = (obj: any) => {
    dispatch(
      setUpdatedTableBasic({
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
      updateTableBasic(connectionName, schemaName, tableName, tableBasic)
    );
    await dispatch(getTableBasic(connectionName, schemaName, tableName));
  };

  return (
    <div className="p-4">
      <ActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdatedTableBasic}
        isUpdating={isUpdating}
      />

      <table className="mb-6 mt-4 w-160">
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
              <Checkbox
                onChange={(value) => handleChange({ disabled: value })}
                checked={tableBasic?.disabled}
              />
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
          {tableBasic?.target?.properties && (
            <>
              <tr>
                <td className="px-4 py-2 font-semibold" colSpan={2}>
                  Properties
                </td>
              </tr>
              {Object.entries(tableBasic.target.properties).map(
                ([key, value], index) => (
                  <tr key={index}>
                    <td className="px-4 py-2">{key}</td>
                    <td className="px-4 py-2">
                      <Input
                        value={value}
                        onChange={(e) =>
                          handleChange({
                            target: { properties: { [key]: e.target.value } }
                          })
                        }
                      />
                    </td>
                  </tr>
                )
              )}
            </>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default TableDetails;
