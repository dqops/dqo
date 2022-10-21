import React, { useEffect, useMemo, useState } from 'react';
import { ITreeNode } from '../../../shared/interfaces';
import { TableApiClient } from '../../../services/apiClient';
import Tabs from '../../Tabs';
import { TabOption } from '../../Tabs/tab';
import TableDetails from '../TableView/TableDetails';
import SvgIcon from '../../SvgIcon';
import Button from '../../Button';
import qs from 'query-string';
import { useHistory } from 'react-router-dom';

interface ISchemaViewProps {
  node: ITreeNode;
}

const SchemaView = ({ node }: ISchemaViewProps) => {
  const [tabs, setTabs] = useState<TabOption[]>([]);
  const [activeTab, setActiveTab] = useState('');

  const connectionName = useMemo(() => node.key.split('.')[1] || '', [node]);
  const schemaName = useMemo(() => node.module, [node]);
  const history = useHistory();

  useEffect(() => {
    TableApiClient.getTables(connectionName, schemaName).then((res) => {
      setTabs(
        res.data.map((item) => ({
          label: item.target?.table_name || '',
          value: item.target?.table_name || ''
        }))
      );

      setActiveTab(res.data[0]?.target?.table_name || '');
    });

    const searchQuery = qs.stringify({
      connection: connectionName,
      schema: schemaName
    });

    history.replace(`/connection?${searchQuery}`);
  }, [schemaName, connectionName]);

  const renderContent = () => {
    if (!activeTab) {
      return null;
    }

    return <div />;
    // return (
    //   <TableDetails
    //     connectionName={connectionName}
    //     schemaName={node.module}
    //     tableName={activeTab}
    //   />
    // );
  };

  return (
    <div>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2">
        <div className="flex items-center space-x-2">
          <SvgIcon name="schema" className="w-5 h-5" />
          <div className="text-xl font-semibold">{node.module}</div>
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
      <div>{renderContent()}</div>
    </div>
  );
};

export default SchemaView;
