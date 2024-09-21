import { Dialog, Tooltip } from '@material-tailwind/react';
import React, { useEffect, useState } from 'react';
import { ColumnListModel } from '../../api';
import { ColumnApiClient } from '../../services/apiClient';
import { useDecodedParams } from '../../utils';
import Button from '../Button';
import Checkbox from '../Checkbox';
import SvgIcon from '../SvgIcon';

interface IColumnsRecordDialogProps {
  className?: string;
  label?: string;
  value: string[];
  tooltipText?: string;
  onChange: (value: string[]) => void;
  onSave?: () => void;
  disabled?: boolean;
}

type ColumnWithCheckbox = ColumnListModel & { checked: boolean };

const ColumnsRecordDialog = ({
  label,
  value,
  tooltipText,
  onChange,
  disabled
}: IColumnsRecordDialogProps) => {
  const {
    connection,
    schema,
    table
  }: { connection: string; schema: string; table: string } = useDecodedParams();
  const [columnsWithCheckboxes, setColumnsWithCheckboxes] = useState<
    ColumnWithCheckbox[]
  >([]);
  const [open, setOpen] = useState(false);

  const handleSave = () => {
    const selectedColumns = columnsWithCheckboxes
      .filter((column) => column.checked)
      .map((column) => column.column_name ?? '');
    onChange(selectedColumns);
    setOpen(false);
  };

  useEffect(() => {
    const getColumns = async () => {
      await ColumnApiClient.getColumns(connection, schema, table).then(
        (res) => {
          const columnsWithCheckboxes = res.data
            .map((column) => ({
              ...column,
              checked: value.includes(column.column_name ?? '')
            }))
            .sort((a, b) =>
              (a.column_name ?? '')?.localeCompare(b.column_name ?? '')
            );
          setColumnsWithCheckboxes(columnsWithCheckboxes);
        }
      );
    };
    if (open) getColumns();
  }, [open]);

  return (
    <div>
      <div className="flex space-x-1">
        {label && (
          <label className="block font-regular text-gray-700 mb-1 text-sm flex space-x-1">
            <span>{label}</span>
            {!!tooltipText && (
              <Tooltip
                content={tooltipText}
                className="max-w-80 py-2 px-2 bg-gray-800"
              >
                <div className="!min-w-4 !w-4">
                  <SvgIcon
                    name="info"
                    className="!w-4 !h-4 z-10 text-gray-700 cursor-pointer"
                  />
                </div>
              </Tooltip>
            )}
          </label>
        )}
      </div>
      <div className="flex space-x-2 items-center">
        <div className="relative text-sm leading-1">
          {value?.join(', ')?.slice(0, 200)}
          {value.join(', ').length > 200 && '...'}
        </div>
        <div className="!min-w-4 !w-4">
          <SvgIcon
            name="edit"
            className="w-4 h-4 text-gray-700 cursor-pointer"
            onClick={() => !disabled && setOpen(true)}
          />
        </div>
      </div>
      <Dialog
        open={open}
        handler={() => setOpen(false)}
        style={{ width: '200px !important', minWidth: '25%', maxWidth: '25%' }}
        className="pt-6"
      >
        <div className="w-full h-full overflow-hidden">
          <table
            className="table-fixed text-black text-sm w-full"
            style={{ tableLayout: 'fixed' }}
          >
            <thead>
              <tr>
                <th className="text-left px-10">Column</th>
              </tr>
            </thead>
            <tbody className="border-t border-gray-100">
              {columnsWithCheckboxes.map((column) => (
                <tr key={column.column_name}>
                  <td>
                    <div className="flex items-center py-0.5 pl-4 gap-x-2">
                      <Checkbox
                        checked={column.checked}
                        onChange={(value) => {
                          const newColumns = columnsWithCheckboxes.map((c) =>
                            c.column_name === column.column_name
                              ? { ...c, checked: value }
                              : c
                          );
                          setColumnsWithCheckboxes(newColumns);
                        }}
                      />
                      <div
                        className="ml-4 truncate"
                        style={{
                          minWidth: '0', // Allow flex-grow to control width
                          maxWidth: '100%',
                          overflow: 'hidden',
                          textOverflow: 'ellipsis'
                        }}
                      >
                        {column.column_name}
                      </div>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        <div className="w-full flex justify-center items-center space-x-4 p-4 mt-2">
          <div className="flex items-center justify-center gap-x-4">
            <Button
              color="primary"
              variant="outlined"
              label="Cancel"
              className="w-30"
              onClick={() => setOpen(false)}
            />
            <Button
              variant="contained"
              label="Save"
              color="primary"
              className="w-30"
              onClick={handleSave}
            />
          </div>
        </div>
      </Dialog>
    </div>
  );
};

export default ColumnsRecordDialog;
