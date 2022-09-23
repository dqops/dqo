import React, { useEffect, useState } from 'react';
import { ITreeNode } from '../../../shared/interfaces';
import SvgIcon from '../../SvgIcon';
import Tabs from '../../Tabs';
import { ConnectionApiClient } from '../../../services/apiClient';
import { ConnectionModel } from '../../../api';
import ConnectionDetail from './ConnectionDetail';
import ScheduleDetail from './ScheduleDetail';
import Button from '../../Button';

interface IConnectionViewProps {
  node: ITreeNode;
}

const tabs = [
  {
    label: 'Connection',
    value: 'connection'
  },
  {
    label: 'Schedule',
    value: 'schedule'
  }
];

const ConnectionView: React.FC<IConnectionViewProps> = ({ node }) => {
  const [activeTab, setActiveTab] = useState('connection');
  const [connection, setConnection] = useState<ConnectionModel>();
  const [formData, setFormData] = useState<ConnectionModel>();

  useEffect(() => {
    setFormData(connection);
  }, [connection]);

  const mutateFormData = (obj: any) => {
    setFormData({
      ...formData,
      ...obj
    });
  };

  useEffect(() => {
    if (node) {
      ConnectionApiClient.getConnection(node.module)
        .then((res) => {
          setConnection(res.data);
        })
        .catch((err) => {
          console.error(err);
        });
    }
  }, [node]);

  return (
    <div className="">
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2">
        <div className="flex items-center space-x-2">
          <SvgIcon name="database" className="w-5 h-5" />
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
      <div>
        {activeTab === 'connection' && (
          <ConnectionDetail connection={connection} onChange={mutateFormData} />
        )}
        {activeTab === 'schedule' && (
          <ScheduleDetail
            schedule={formData?.spec?.schedule}
            onChange={mutateFormData}
          />
        )}
      </div>
    </div>
  );
};

export default ConnectionView;
