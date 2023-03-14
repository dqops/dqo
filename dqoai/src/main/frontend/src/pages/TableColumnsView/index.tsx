import React from 'react';
import SvgIcon from '../../components/SvgIcon';
import TableColumns from './TableColumns';
import { useParams } from "react-router-dom";
import ConnectionLayout from "../../components/ConnectionLayout";

const TableColumnsView = () => {
  const { connection: connectionName, schema: schemaName, table: tableName }: { connection: string, schema: string, table: string } = useParams();

  return (
    <ConnectionLayout>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2">
          <SvgIcon name="column" className="w-5 h-5" />
          <div className="text-xl font-semibold">{`${connectionName}.${schemaName}.${tableName} columns`}</div>
        </div>
      </div>
      <div>
        <TableColumns
          connectionName={connectionName}
          schemaName={schemaName}
          tableName={tableName}
        />
      </div>
    </ConnectionLayout>
  );
};

export default TableColumnsView;
