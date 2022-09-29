import React, { useEffect, useState } from 'react';
import { ColumnApiClient } from '../../../services/apiClient';
import { AxiosResponse } from 'axios';
import { ColumnBasicModel } from '../../../api';

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
  const [columns, setColumns] = useState<ColumnBasicModel[]>();

  const fetchColumns = async () => {
    try {
      const res: AxiosResponse<ColumnBasicModel[]> =
        await ColumnApiClient.getColumns(connectionName, schemaName, tableName);
      setColumns(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchColumns().then();
  }, []);

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
            </tr>
          ))}
      </table>
    </div>
  );
};

export default TableColumns;
