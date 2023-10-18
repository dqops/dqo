import React from "react";
import { TableListModel } from "../../api";

type SchemaTablesProps = {
  tables: TableListModel[];
}

export const SchemaTables = ({ tables }: SchemaTablesProps) => {
  return (
    <table className="w-full">
      <thead>
      <tr>
        <th className="px-4 text-left">Connection</th>
        <th className="px-4 text-left">Schema</th>
        <th className="px-4 text-left">Table</th>
        <th className="px-4 text-left">Disabled</th>
        <th className="px-4 text-left">Stage</th>
        <th className="px-4 text-left">Filter</th>
      </tr>
      </thead>
      <tbody>
      {tables.map((item, index) => (
        <tr key={index}>
          <td className="px-4">{item.connection_name}</td>
          <td className="px-4">{item.target?.schema_name}</td>
          <td className="px-4">{item.target?.table_name}</td>
          <td className="px-4">{item?.disabled}</td>
          <td className="px-4">{item?.stage}</td>
          <td className="px-4">{item?.filter}</td>
        </tr>
      ))}
      </tbody>
    </table>
  );
}