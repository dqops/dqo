import React from 'react';
import { TableMonthlyPartitionedCheckCategoriesSpec } from '../../../api';

interface IMonthlyCheckpointsProps {
  monthlyPartitionedChecks?: TableMonthlyPartitionedCheckCategoriesSpec;
  onChange: (checks: TableMonthlyPartitionedCheckCategoriesSpec) => void;
}

const MonthlyCheckpoints = ({
  monthlyPartitionedChecks,
  onChange
}: IMonthlyCheckpointsProps) => {
  return <div />;
};

export default MonthlyCheckpoints;
