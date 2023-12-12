import React from 'react';
import Input from '../../../Input';
import { CompareThresholdsModel, TableComparisonModel } from '../../../../api';
import { TSeverityValues } from './TableComparisonConstans';

type TSeverityInputBlock = {
  onChange: (obj: Partial<CompareThresholdsModel>) => void;
  reference: TableComparisonModel;
  onUpdateChecksUI: (
    checksUI: any,
    type: 'row' | 'column',
    disabled?: boolean,
    severity?: TSeverityValues
  ) => void;
  checksUI: any;
  type: 'row' | 'column';
};

export default function SeverityInputBlock({
  onChange,
  reference,
  onUpdateChecksUI,
  checksUI,
  type
}: TSeverityInputBlock) {
  return (
    <div className="flex flex-col pt-0 mt-0 w-full">
      <div className="bg-yellow-100 px-4 py-2 flex items-center gap-2">
        <Input
          className="max-w-30 !min-w-initial"
          value={
            type === 'column'
              ? reference?.compare_column_count?.warning_difference_percent
              : reference.compare_row_count?.warning_difference_percent
          }
          onChange={(e) => {
            onChange({
              warning_difference_percent:
                String(e.target.value).length === 0
                  ? undefined
                  : Number(e.target.value)
            });
            onUpdateChecksUI(checksUI, type, undefined, {
              warning:
                String(e.target.value).length === 0
                  ? undefined
                  : Number(e.target.value)
            });
          }}
        />
        %
      </div>
      <div className="bg-orange-100 px-4 py-2 flex items-center gap-2">
        <Input
          className="max-w-30 !min-w-initial"
          value={
            type === 'column'
              ? reference?.compare_column_count?.error_difference_percent
              : reference.compare_row_count?.error_difference_percent
          }
          onChange={(e) => {
            onChange({
              error_difference_percent:
                String(e.target.value).length === 0
                  ? undefined
                  : Number(e.target.value)
            });
            onUpdateChecksUI(checksUI, type, undefined, {
              error:
                String(e.target.value).length === 0
                  ? undefined
                  : Number(e.target.value)
            });
          }}
        />
        %
      </div>
      <div className="bg-red-100 px-4 py-2 flex items-center gap-2">
        <Input
          className="max-w-30 !min-w-initial"
          value={
            type === 'column'
              ? reference?.compare_column_count?.fatal_difference_percent
              : reference.compare_row_count?.fatal_difference_percent
          }
          onChange={(e) => {
            onChange({
              fatal_difference_percent:
                String(e.target.value).length === 0
                  ? undefined
                  : Number(e.target.value)
            });
            onUpdateChecksUI(checksUI, type, undefined, {
              fatal:
                String(e.target.value).length === 0
                  ? undefined
                  : Number(e.target.value)
            });
          }}
        />
        %
      </div>
    </div>
  );
}
