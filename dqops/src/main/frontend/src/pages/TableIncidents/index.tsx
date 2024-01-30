import React from 'react';
import SvgIcon from '../../components/SvgIcon';
import { useParams } from 'react-router-dom';
import ConnectionLayout from '../../components/ConnectionLayout';
import TableIncidentsNotificationsView from "../../components/Connection/TableView/TableIncidentsNotificationsView";

const TableColumnsView = () => {
  const {
    connection: connectionName,
    schema: schemaName,
    table: tableName
  }: { connection: string; schema: string; table: string } = useParams();

  return (
    <>
      <div className="relative">
        <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
          <div className="flex items-center space-x-2 max-w-full">
            <SvgIcon name="column" className="w-5 h-5 shrink-0" />
            <div className="text-xl font-semibold truncate">{`${connectionName}.${schemaName}.${tableName} incidents`}</div>
          </div>
        </div>
        <TableIncidentsNotificationsView />
      </div>
    </>
  );
};

export default TableColumnsView;
