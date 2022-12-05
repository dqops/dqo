import React, { useEffect, useState } from 'react';
import { TableBasicModel } from '../../../api';
import Input from '../../Input';
import Checkbox from '../../Checkbox';
import ActionGroup from './ActionGroup';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableBasic,
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
  const [isUpdated, setIsUpdated] = useState(false);
  const [updatedTableBasic, setUpdatedTableBasic] = useState<TableBasicModel>();
  const { tableBasic, isUpdating } = useSelector(
    (state: IRootState) => state.table
  );
  const dispatch = useActionDispatch();

  useEffect(() => {
    dispatch(getTableBasic(connectionName, schemaName, tableName));
  }, []);

  useEffect(() => {
    setUpdatedTableBasic(tableBasic);
  }, [tableBasic]);

  const handleChange = (obj: any) => {
    setUpdatedTableBasic({
      ...updatedTableBasic,
      ...obj
    });
    setIsUpdated(true);
  };

  const onUpdate = async () => {
    if (!updatedTableBasic) {
      return;
    }
    await dispatch(
      updateTableBasic(connectionName, schemaName, tableName, updatedTableBasic)
    );
    await dispatch(getTableBasic(connectionName, schemaName, tableName));
    setIsUpdated(false);
  };

  return (
    <div className="p-4">
      <ActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdated}
        isUpdating={isUpdating}
      />

      <table className="mb-6 mt-4 w-160">
        <tbody>
          <tr>
            <td className="px-4 py-2">Connection Name</td>
            <td className="px-4 py-2">{updatedTableBasic?.connection_name}</td>
          </tr>
          <tr>
            <td className="px-4 py-2">Schema Name</td>
            <td className="px-4 py-2">{updatedTableBasic?.target?.schema_name}</td>
          </tr>
          <tr>
            <td className="px-4 py-2">Table Name</td>
            <td className="px-4 py-2">{updatedTableBasic?.target?.table_name}</td>
          </tr>
          <tr>
            <td className="px-4 py-2">Disable</td>
            <td className="px-4 py-2">
              <Checkbox
                onChange={(value) => handleChange({ disabled: value })}
                checked={updatedTableBasic?.disabled}
              />
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">Filter</td>
            <td className="px-4 py-2">
              <Input
                value={updatedTableBasic?.filter}
                onChange={(e) => handleChange({ filter: e.target.value })}
              />
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">Stage</td>
            <td className="px-4 py-2">
              <Input
                value={updatedTableBasic?.stage}
                onChange={(e) => handleChange({ stage: e.target.value })}
              />
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">Table Hash</td>
            <td className="px-4 py-2">{updatedTableBasic?.table_hash}</td>
          </tr>
          {updatedTableBasic?.target?.properties && (
            <>
              <tr>
                <td className="px-4 py-2 font-semibold" colSpan={2}>
                  Properties
                </td>
              </tr>
              {Object.entries(updatedTableBasic.target.properties).map(
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
