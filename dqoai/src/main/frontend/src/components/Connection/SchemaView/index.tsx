import React, { useEffect, useState } from 'react';
import { TableApiClient } from '../../../services/apiClient';
import Tabs from '../../Tabs';
import SvgIcon from '../../SvgIcon';
import Button from '../../Button';
import qs from 'query-string';
import { useHistory } from 'react-router-dom';
import { TableBasicModel } from '../../../api';

interface ISchemaViewProps {
  connectionName: string;
  schemaName: string;
}

const tabs = [
  {
    label: 'Tables',
    value: 'tables'
  }
];

const SchemaView = ({ connectionName, schemaName }: ISchemaViewProps) => {
  const [activeTab, setActiveTab] = useState('tables');
  const [tables, setTables] = useState<TableBasicModel[]>([]);

  const history = useHistory();

  useEffect(() => {
    TableApiClient.getTables(connectionName, schemaName).then((res) => {
      setTables(res.data);
    });

    const searchQuery = qs.stringify({
      connection: connectionName,
      schema: schemaName
    });

    history.replace(`/dashboard?${searchQuery}`);
  }, [schemaName, connectionName]);

  return (
    <div>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2">
        <div className="flex items-center space-x-2">
          <SvgIcon name="schema" className="w-5 h-5" />
          <div className="text-xl font-semibold">{`${connectionName}.${schemaName}`}</div>
        </div>
        <Button
          color="primary"
          variant="contained"
          label="Save"
          className="w-40"
        />
      </div>
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
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
    </div>
  );
};

export default SchemaView;
