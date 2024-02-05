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
import { CheckTypes } from '../../shared/routes';
import { useParams } from 'react-router-dom';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../redux/selectors';
import TextArea from '../../components/TextArea';

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
  const { checkTypes }: { checkTypes: CheckTypes } = useParams();
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const { columnBasic, isUpdating, isUpdatedColumnBasic } = useSelector(
    getFirstLevelState(checkTypes)
  );

  const handleChange = (obj: any) => {
    dispatch(
      setUpdatedColumnBasic(checkTypes, firstLevelActiveTab, {
        ...columnBasic,
        ...obj
      })
    );
  };

  const handleSnapTypeChange = (obj: any) => {
    dispatch(
      setUpdatedColumnBasic(checkTypes, firstLevelActiveTab, {
        ...columnBasic,
        type_snapshot: {
          ...columnBasic?.type_snapshot,
          ...obj
        }
      })
    );
  };

  useEffect(() => {
    dispatch(
      getColumnBasic(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        columnName
      )
    );
  }, [
    checkTypes,
    firstLevelActiveTab,
    connectionName,
    schemaName,
    columnName,
    tableName
  ]);

  const onUpdate = async () => {
    if (!columnBasic) {
      return;
    }
    await dispatch(
      updateColumnBasic(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        columnName,
        columnBasic
      )
    );
    dispatch(
      getColumnBasic(
        checkTypes,
        firstLevelActiveTab,
        connectionName,
        schemaName,
        tableName,
        columnName
      )
    );
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
            <td className="px-4 py-2">Connection name</td>
            <td className="px-4 py-2">{columnBasic?.connection_name}</td>
          </tr>
          <tr>
            <td className="px-4 py-2">Schema name</td>
            <td className="px-4 py-2">{columnBasic?.table?.schema_name}</td>
          </tr>
          <tr>
            <td className="px-4 py-2">Table name</td>
            <td className="px-4 py-2">{columnBasic?.table?.table_name}</td>
          </tr>
          <tr>
            <td className="px-4 py-2">Column name</td>
            <td className="px-4 py-2">{columnBasic?.column_name}</td>
          </tr>
          <tr>
            <td className="px-4 py-2">Disable data quality checks</td>
            <td className="px-4 py-2">
              <div className="flex">
                <Checkbox
                  onChange={(value) => handleChange({ disabled: value })}
                  checked={columnBasic?.disabled}
                />
              </div>
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">
              SQL expression for a calculated column (use an {'{'}alias{'}.'}{' '}
              token to reference the table)
            </td>
            <td className="px-4 py-2">
              <TextArea
                value={columnBasic?.sql_expression}
                onChange={(e) =>
                  handleSnapTypeChange({ sql_expression: e.target.value })
                }
                className="min-h-25"
              />
            </td>
          </tr>
          <tr>
            <td className="px-4 py-2">Column data type</td>
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
