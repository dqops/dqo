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

interface ICheckpointsViewProps {
  dailyCheckpoints?: UIAllChecksModel;
  monthlyCheckpoints?: UIAllChecksModel;
  onDailyCheckpointsChange: (checks: UIAllChecksModel) => void;
  onMonthlyCheckpointsChange: (checks: UIAllChecksModel) => void;
}

const CheckpointsView = ({
  dailyCheckpoints,
  monthlyCheckpoints,
  onDailyCheckpointsChange,
  onMonthlyCheckpointsChange
}: ICheckpointsViewProps) => {
  const [activeTab, setActiveTab] = useState('daily');

  console.log('daily', dailyCheckpoints, monthlyCheckpoints);
  return (
    <div className="py-2">
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
      </div>
      <div>
        {activeTab === 'daily' && (
          <DataQualityChecks
            checksUI={dailyCheckpoints}
            onChange={onDailyCheckpointsChange}
          />
        )}
        {activeTab === 'monthly' && (
          <DataQualityChecks
            checksUI={monthlyCheckpoints}
            onChange={onMonthlyCheckpointsChange}
          />
        )}
      </div>
    </div>
  );
};

export default CheckpointsView;
