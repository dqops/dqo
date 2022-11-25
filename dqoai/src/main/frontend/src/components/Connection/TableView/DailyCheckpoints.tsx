import React from 'react';
import { TableDailyCheckpointCategoriesSpec } from '../../../api';

interface IDailyCheckpointsProps {
  dailyCheckpoints?: TableDailyCheckpointCategoriesSpec;
  onChange: (checks: TableDailyCheckpointCategoriesSpec) => void;
}

const DailyCheckpoints = ({
  dailyCheckpoints,
  onChange
}: IDailyCheckpointsProps) => {
  return <div />;
};

export default DailyCheckpoints;
