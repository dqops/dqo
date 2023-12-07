import React from 'react';
import Input from '../../../Input';
import { CompareThresholdsModel, TableComparisonModel } from '../../../../api';
import { TSeverityValues } from './TableComparisonConstans';

type TSeverityInputBlock = {
  onChange: (obj: Partial<CompareThresholdsModel>) => void;
  reference: TableComparisonModel;
  onUpdateChecksUI: (
    type: 'row' | 'column',
    disabled?: boolean,
    severity?: TSeverityValues
  ) => void;
  type: 'row' | 'column';
};

export default function SeverityInputBlock({
  onChange,
  reference,
  onUpdateChecksUI,
  type
}: TSeverityInputBlock) {
  return (
    <div className="flex flex-col pt-0 mt-0 w-full">
      <div className="bg-yellow-100 px-4 py-2 flex items-center gap-2">
        <Input
          className="max-w-30 !min-w-initial"
          type="number"
          value={
            type === 'column'
              ? reference?.compare_column_count?.warning_difference_percent
              : reference.compare_row_count?.warning_difference_percent
          }
          onChange={(e) => {
            onChange({
              warning_difference_percent: Number(e.target.value)
            });
            onUpdateChecksUI(type, undefined, {
              warning: Number(e.target.value)
            });
          }}
        />
        %
      </div>
      <div className="bg-orange-100 px-4 py-2 flex items-center gap-2">
        <Input
          className="max-w-30 !min-w-initial"
          type="number"
          value={
            type === 'column'
              ? reference?.compare_column_count?.error_difference_percent
              : reference.compare_row_count?.error_difference_percent
          }
          onChange={(e) => {
            onChange({
              error_difference_percent: Number(e.target.value)
            });
            onUpdateChecksUI(type, undefined, {
              error: Number(e.target.value)
            });
          }}
        />
        %
      </div>
      <div className="bg-red-100 px-4 py-2 flex items-center gap-2">
        <Input
          className="max-w-30 !min-w-initial"
          type="number"
          value={
            type === 'column'
              ? reference?.compare_column_count?.fatal_difference_percent
              : reference.compare_row_count?.fatal_difference_percent
          }
          onChange={(e) => {
            onChange({
              fatal_difference_percent: Number(e.target.value)
            });
            onUpdateChecksUI(type, undefined, {
              fatal: Number(e.target.value)
            });
          }}
        />
        %
      </div>
    </div>
  );
}
