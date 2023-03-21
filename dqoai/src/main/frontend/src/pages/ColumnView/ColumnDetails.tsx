import React, { useEffect } from 'react';
import Input from '../../components/Input';
import Checkbox from '../../components/Checkbox';
import NumberInput from '../../components/NumberInput';
import ColumnActionGroup from './ColumnActionGroup';
import {
  getColumnBasic,
  setUpdatedColumnBasic,
  updateColumnBasic
} from '../../redux/actions/column.actions';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';

interface IColumnDetailsProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  columnName: string;
}

const TableDetails = ({
  connectionName,
  schemaName,
  tableName,
  columnName
}: IColumnDetailsProps) => {
  const dispatch = useActionDispatch();

  const { columnBasic, isUpdating, isUpdatedColumnBasic } = useSelector(
    (state: IRootState) => state.column
  );

  const handleChange = (obj: any) => {
    dispatch(
      setUpdatedColumnBasic({
        ...columnBasic,
        ...obj
      })
    );
  };

  const handleSnapTypeChange = (obj: any) => {
    dispatch(
      setUpdatedColumnBasic({
        ...columnBasic,
        type_snapshot: {
          ...columnBasic?.type_snapshot,
          ...obj
        }
      })
    );
  };

  useEffect(() => {
    if (
      columnBasic?.connection_name !== connectionName ||
      columnBasic?.table?.schema_name !== schemaName ||
      columnBasic?.table?.table_name !== tableName ||
      columnBasic.column_name !== columnName
    ) {
      dispatch(
        getColumnBasic(connectionName, schemaName, tableName, columnName)
      );
    }
  }, [connectionName, schemaName, columnName, tableName, columnBasic]);

  const onUpdate = async () => {
    if (!columnBasic) {
      return;
    }
    await dispatch(
      updateColumnBasic(
        connectionName,
        schemaName,
        tableName,
        columnName,
        columnBasic
      )
    );
    dispatch(getColumnBasic(connectionName, schemaName, tableName, columnName));
  };

  return (
    <div className="p-4">
      <ColumnActionGroup
        onUpdate={onUpdate}
        isUpdating={isUpdating}
        isUpdated={isUpdatedColumnBasic}
      />
      <table className="mb-6 w-160">
        <tbody>
          <tr>
            <td className="px-4 py-2">Connection Name</td>
            <td className="px-4 py-2">{columnBasic?.connection_name}</td>
          </tr>
          <tr>
            <td className="px-4 py-2">Schema Name</td>
            <td className="px-4 py-2">{columnBasic?.table?.schema_name}</td>
          </tr>
          <tr>
            <td className="px-4 py-2">Table Name</td>
            <td className="px-4 py-2">{columnBasic?.table?.table_name}</td>
          </tr>
          <tr>
            <td className="px-4 py-2">Column Name</td>
            <td className="px-4 py-2">{columnBasic?.column_name}</td>
          </tr>
          <tr>
            <td className="px-4 py-2">Disable Data Quality Checks</td>
            <td className="px-4 py-2">
              <Checkbox
                onChange={(value) => handleChange({ disabled: value })}
                checked={columnBasic?.disabled}
              />
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">Column Type</td>
            <td className="px-4 py-2">
              <Input
                value={columnBasic?.type_snapshot?.column_type}
                onChange={(e) =>
                  handleSnapTypeChange({ column_type: e.target.value })
                }
              />
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">Nullable</td>
            <td className="px-4 py-2">
              <Checkbox
                onChange={(value) => handleSnapTypeChange({ nullable: value })}
                checked={columnBasic?.type_snapshot?.nullable}
              />
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">Length</td>
            <td className="px-4 py-2">
              <NumberInput
                value={columnBasic?.type_snapshot?.length || ''}
                onChange={(value) => handleSnapTypeChange({ length: value })}
              />
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">Scale</td>
            <td className="px-4 py-2">
              <NumberInput
                value={columnBasic?.type_snapshot?.scale}
                onChange={(value) => handleSnapTypeChange({ scale: value })}
              />
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">Precision</td>
            <td className="px-4 py-2">
              <NumberInput
                value={columnBasic?.type_snapshot?.precision}
                onChange={(value) => handleSnapTypeChange({ precision: value })}
              />
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};

export default TableDetails;
