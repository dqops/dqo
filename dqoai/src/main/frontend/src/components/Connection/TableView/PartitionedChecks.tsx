import React, { useState } from 'react';
import Tabs from '../../Tabs';
import {
  TableDailyPartitionedCheckCategoriesSpec,
  TableMonthlyPartitionedCheckCategoriesSpec
} from '../../../api';
import DailyPartitionedChecks from './DailyPartitionedChecks';
import MonthlyPartitionedChecks from './MonthlyPartitionedChecks';

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
  dailyPartitionedChecks?: TableDailyPartitionedCheckCategoriesSpec;
  monthlyPartitionedChecks?: TableMonthlyPartitionedCheckCategoriesSpec;
  onDailyPartitionedChecks: (
    checks: TableDailyPartitionedCheckCategoriesSpec
  ) => void;
  onMonthlyPartitionedChecks: (
    checks: TableMonthlyPartitionedCheckCategoriesSpec
  ) => void;
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
          <DailyPartitionedChecks
            dailyPartitionedChecks={dailyPartitionedChecks}
            onChange={onDailyPartitionedChecks}
          />
        )}
        {activeTab === 'monthly' && (
          <MonthlyPartitionedChecks
            monthlyPartitionedChecks={monthlyPartitionedChecks}
            onChange={onMonthlyPartitionedChecks}
          />
        )}
      </div>
    </div>
  );
};

export default PartitionedChecks;
