import React from 'react';
import { TableMonthlyCheckpointCategoriesSpec } from '../../../api';

interface IMonthlyCheckpointsProps {
  monthlyCheckpoints?: TableMonthlyCheckpointCategoriesSpec;
  onChange: (checks: TableMonthlyCheckpointCategoriesSpec) => void;
}

const MonthlyCheckpoints = ({
  monthlyCheckpoints,
  onChange
}: IMonthlyCheckpointsProps) => {
  return <div />;
};

export default MonthlyCheckpoints;
