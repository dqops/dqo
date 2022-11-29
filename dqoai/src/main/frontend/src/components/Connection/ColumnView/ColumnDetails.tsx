import React, { useEffect, useState } from 'react';
import { ColumnBasicModel } from '../../../api';
import Input from '../../Input';
import Checkbox from '../../Checkbox';
import NumberInput from '../../NumberInput';
import ColumnActionGroup from './ColumnActionGroup';
import {
  getColumnBasic,
  updateColumnBasic
} from '../../../redux/actions/column.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';

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
  const [updatedColumnBasic, setUpdatedColumnBasic] =
    useState<ColumnBasicModel>();
  const [isUpdated, setIsUpdated] = useState(false);

  const { columnBasic, isUpdating } = useSelector(
    (state: IRootState) => state.column
  );

  useEffect(() => {
    setUpdatedColumnBasic(columnBasic);
  }, [columnBasic]);

  const handleChange = (obj: any) => {
    setUpdatedColumnBasic({
      ...updatedColumnBasic,
      ...obj
    });
    setIsUpdated(true);
  };

  const handleSnapTypeChange = (obj: any) => {
    setUpdatedColumnBasic({
      ...updatedColumnBasic,
      type_snapshot: {
        ...updatedColumnBasic?.type_snapshot,
        ...obj
      }
    });
  };

  useEffect(() => {
    dispatch(getColumnBasic(connectionName, schemaName, tableName, columnName));
  }, []);

  const onUpdate = async () => {
    await dispatch(
      updateColumnBasic(
        connectionName,
        schemaName,
        tableName,
        columnName,
        updatedColumnBasic
      )
    );
  };

  return (
    <div className="p-4">
      <ColumnActionGroup
        onUpdate={onUpdate}
        isUpdating={isUpdating}
        isUpdated={isUpdated}
      />
      <table className="mb-6 mt-4 w-160">
        <tbody>
          <tr>
            <td className="px-4 py-2">Connection Name</td>
            <td className="px-4 py-2">{updatedColumnBasic?.connection_name}</td>
          </tr>
          <tr>
            <td className="px-4 py-2">Schema Name</td>
            <td className="px-4 py-2">
              {updatedColumnBasic?.table?.schemaName}
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">Table Name</td>
            <td className="px-4 py-2">
              {updatedColumnBasic?.table?.tableName}
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">Column Name</td>
            <td className="px-4 py-2">{updatedColumnBasic?.column_name}</td>
          </tr>
          <tr>
            <td className="px-4 py-2">Disable Data Quality Checks</td>
            <td className="px-4 py-2">
              <Checkbox
                onChange={(value) => handleChange({ disabled: value })}
                checked={updatedColumnBasic?.disabled}
              />
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">Column Type</td>
            <td className="px-4 py-2">
              <Input
                value={updatedColumnBasic?.type_snapshot?.column_type}
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
                checked={updatedColumnBasic?.type_snapshot?.nullable}
              />
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">Length</td>
            <td className="px-4 py-2">
              <NumberInput
                value={updatedColumnBasic?.type_snapshot?.length || ''}
                onChange={(value) => handleSnapTypeChange({ length: value })}
              />
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">Scale</td>
            <td className="px-4 py-2">
              <NumberInput
                value={updatedColumnBasic?.type_snapshot?.scale}
                onChange={(value) => handleSnapTypeChange({ scale: value })}
              />
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">Precision</td>
            <td className="px-4 py-2">
              <NumberInput
                value={updatedColumnBasic?.type_snapshot?.precision}
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
