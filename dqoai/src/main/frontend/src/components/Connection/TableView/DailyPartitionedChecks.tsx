import React from 'react';
import { TableDailyPartitionedCheckCategoriesSpec } from '../../../api';

interface IDailyPartitionedChecksProps {
  dailyPartitionedChecks?: TableDailyPartitionedCheckCategoriesSpec;
  onChange: (checks: TableDailyPartitionedCheckCategoriesSpec) => void;
}

const DailyPartitionedChecks = ({
  dailyPartitionedChecks,
  onChange
}: IDailyPartitionedChecksProps) => {
  return <div />;
};

export default DailyPartitionedChecks;
