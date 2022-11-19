import React from 'react';
import SvgIcon from '../../SvgIcon';
import TableColumns from './TableColumns';

interface IColumnsViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
}

const ColumnsView = ({
  connectionName,
  schemaName,
  tableName
}: IColumnsViewProps) => {
  return (
    <div className="">
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2">
          <SvgIcon name="database" className="w-5 h-5" />
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
    </div>
  );
};

export default ColumnsView;
