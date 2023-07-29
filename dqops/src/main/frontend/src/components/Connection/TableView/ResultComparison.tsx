import React from 'react';
import { ColumnComparisonModel } from '../../../api';

interface dataInterface {
  item: ColumnComparisonModel;
  onUpdate?: () => void;
}

export default function ResultComparison({ item, onUpdate }: dataInterface) {
  return <div>ResultComparison</div>;
}
