import React, { useState } from 'react';
import Tabs from '../../Tabs';
import {
  TableDailyCheckpointCategoriesSpec,
  TableMonthlyCheckpointCategoriesSpec
} from '../../../api';
import DailyCheckpoints from './DailyCheckpoints';
import MonthlyCheckpoints from './MonthlyCheckpoints';

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
  dailyCheckpoints?: TableDailyCheckpointCategoriesSpec;
  monthlyCheckpoints?: TableMonthlyCheckpointCategoriesSpec;
  onDailyCheckpointsChange: (
    checks: TableDailyCheckpointCategoriesSpec
  ) => void;
  onMonthlyCheckpointsChange: (
    checks: TableMonthlyCheckpointCategoriesSpec
  ) => void;
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
          <DailyCheckpoints
            dailyCheckpoints={dailyCheckpoints}
            onChange={onDailyCheckpointsChange}
          />
        )}
        {activeTab === 'monthly' && (
          <MonthlyCheckpoints
            monthlyCheckpoints={monthlyCheckpoints}
            onChange={onMonthlyCheckpointsChange}
          />
        )}
      </div>
    </div>
  );
};

export default CheckpointsView;
