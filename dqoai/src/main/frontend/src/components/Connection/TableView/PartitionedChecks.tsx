import React, { useState } from 'react';
import Tabs from '../../Tabs';
import { UIAllChecksModel } from '../../../api';
import DataQualityChecks from '../../DataQualityChecks';

const tabs = [
  {
    label: 'Daily',
    value: 'daily'
  },
  {
    label: 'Monthly',
    value: 'monthly'
  }
];

interface IPartitionedChecksProps {
  dailyPartitionedChecks?: UIAllChecksModel;
  monthlyPartitionedChecks?: UIAllChecksModel;
  onDailyPartitionedChecks: (checks: UIAllChecksModel) => void;
  onMonthlyPartitionedChecks: (checks: UIAllChecksModel) => void;
}

const PartitionedChecks = ({
  dailyPartitionedChecks,
  monthlyPartitionedChecks,
  onDailyPartitionedChecks,
  onMonthlyPartitionedChecks
}: IPartitionedChecksProps) => {
  const [activeTab, setActiveTab] = useState('daily');

  return (
    <div className="py-2">
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
      </div>
      <div>
        {activeTab === 'daily' && (
          <DataQualityChecks
            checksUI={dailyPartitionedChecks}
            onChange={onDailyPartitionedChecks}
          />
        )}
        {activeTab === 'monthly' && (
          <DataQualityChecks
            checksUI={monthlyPartitionedChecks}
            onChange={onMonthlyPartitionedChecks}
          />
        )}
      </div>
    </div>
  );
};

export default PartitionedChecks;
