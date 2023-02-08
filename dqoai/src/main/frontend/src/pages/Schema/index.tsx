import React, { useEffect, useState } from "react";
import ConnectionLayout from "../../components/ConnectionLayout";
import SvgIcon from "../../components/SvgIcon";
import Tabs from "../../components/Tabs";
import { useHistory, useParams } from "react-router-dom";
import { ROUTES } from "../../shared/routes";
import { TableBasicModel } from "../../api";
import { TableApiClient } from "../../services/apiClient";
import Button from "../../components/Button";

const tabs = [
  {
    label: 'Tables',
    value: 'tables'
  }
];

const SchemaPage = () => {
  const { connection, schema, tab: activeTab, checkTypes }: { connection: string, schema: string, tab: string, checkTypes: string } = useParams();
  const [tables, setTables] = useState<TableBasicModel[]>([]);

  const history = useHistory();

  useEffect(() => {
    TableApiClient.getTables(connection, schema).then((res) => {
      setTables(res.data);
    });
  }, [schema, connection]);

  const onChangeTab = (tab: string) => {
    history.push(ROUTES.SCHEMA_LEVEL_PAGE(checkTypes, connection, schema, tab));
  };

  return (
    <ConnectionLayout>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2">
        <div className="flex items-center space-x-2">
          <SvgIcon name="schema" className="w-5 h-5" />
          <div className="text-xl font-semibold">{`${connection}.schema.${schema}`}</div>
        </div>
        <Button
          color="primary"
          variant="contained"
          label="Save"
          className="w-40"
        />
      </div>
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
      </div>
      <div className="p-4">
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
      </div>
    </ConnectionLayout>
  );
};

export default SchemaPage;
