import React, { useEffect, useState } from 'react';
import { ColumnApiClient } from '../../services/apiClient';
import { AxiosResponse } from 'axios';
import { ColumnProfileModel } from '../../api';
import { IconButton } from '@material-tailwind/react';
import SvgIcon from '../../components/SvgIcon';
import ConfirmDialog from './ConfirmDialog';

interface ITableColumnsProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

const TableColumns = ({
  connectionName,
  schemaName,
  tableName
}: ITableColumnsProps) => {
  const [columns, setColumns] = useState<ColumnProfileModel[]>();
  const [isOpen, setIsOpen] = useState(false);
  const [selectedColumn, setSelectedColumn] = useState<ColumnProfileModel>();

  const fetchColumns = async () => {
    try {
      const res: AxiosResponse<ColumnProfileModel[]> =
        await ColumnApiClient.getProfiledColumns(connectionName, schemaName, tableName);
      setColumns(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchColumns().then();
  }, []);

  const onRemoveColumn = (column: ColumnProfileModel) => {
    setIsOpen(true);
    setSelectedColumn(column);
  };

  const removeColumn = async () => {
    if (selectedColumn?.column_name) {
      await ColumnApiClient.deleteColumn(
        connectionName,
        schemaName,
        tableName,
        selectedColumn?.column_name
      );
      await fetchColumns();
    }
  };

  const renderValue = (value: any) => {
    if (typeof value === 'boolean') {
      return value ? 'Yes' : 'No';
    }
    if (typeof value === 'object') {
      return value.toString();
    }
    return value;
  };

  return (
    <div className="p-4">
      <table className="mb-6 mt-4 w-full">
        <thead>
          <th className="border-b border-gray-100 text-left px-4 py-2">Hash</th>
          <th className="border-b border-gray-100 text-left px-4 py-2">Name</th>
          <th className="border-b border-gray-100 text-left px-4 py-2">Type</th>
          <th className="border-b border-gray-100 text-left px-4 py-2">
            Length
          </th>
          <th className="border-b border-gray-100 text-left px-4 py-2">
            Scale
          </th>
          <th className="border-b border-gray-100 text-left px-4 py-2">
            Profiler metrics
          </th>
          <th className="border-b border-gray-100 text-left px-4 py-2">
            Action
          </th>
        </thead>
        {columns &&
          columns?.map((column, index) => (
            <tr key={index}>
              <td className="border-b border-gray-100 text-left px-4 py-2">
                {column.column_hash}
              </td>
              <td className="border-b border-gray-100 text-left px-4 py-2">
                {column.column_name}
              </td>
              <td className="border-b border-gray-100 text-left px-4 py-2">
                {column.type_snapshot?.column_type}
              </td>
              <td className="border-b border-gray-100 text-left px-4 py-2">
                {column.type_snapshot?.length}
              </td>
              <td className="border-b border-gray-100 text-left px-4 py-2">
                {column.type_snapshot?.scale}
              </td>
              <td className="border-b border-gray-100 text-left px-4 py-2">
                {column?.metrics && (
                  <table>
                    <tbody>
                      {column?.metrics?.map((metric, index) => (
                        <tr key={index}>
                          <td className="px-2">{metric.category}</td>
                          <td className="px-2">{metric.profiler}</td>
                          <td className="px-2 truncate">{renderValue(metric.result)}</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                )}
              </td>
              <td className="border-b border-gray-100 text-left px-4 py-2">
                <IconButton
                  className="bg-red-500"
                  onClick={() => onRemoveColumn(column)}
                >
                  <SvgIcon name="delete" className="w-4" />
                </IconButton>
              </td>
            </tr>
          ))}
      </table>
      <ConfirmDialog
        open={isOpen}
        onClose={() => setIsOpen(false)}
        column={selectedColumn}
        onConfirm={removeColumn}
      />
    </div>
  );
};

export default TableColumns;
