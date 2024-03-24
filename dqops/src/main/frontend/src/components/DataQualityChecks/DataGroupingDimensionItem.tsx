import clsx from 'clsx';
import React from 'react';
import {
  DataGroupingDimensionSpec,
  DataGroupingDimensionSpecSourceEnum
} from '../../api';
import Input from '../Input';
import RadioButton from '../RadioButton';
import ColumnSelect from './ColumnSelect';

interface IDataGroupingDimensionItemProps {
  dataGroupingLevel?: DataGroupingDimensionSpec;
  idx: number;
  onChange: (dataGroupingLevel: DataGroupingDimensionSpec) => void;
  scope?: string;
  error?: string;
  onClearError?: (idx: number) => void;
}

const DataGroupingDimensionItem = ({
  idx,
  onChange,
  dataGroupingLevel,
  scope,
  error,
  onClearError
}: IDataGroupingDimensionItemProps) => {
  return (
    <div className="mb-4 last:mb-0 text-sm">
      <div className="flex justify-between items-center space-x-6">
        <div className="text-sm font-semibold flex-1">{`Grouping dimension level ${
          idx + 1
        }`}</div>
        <div className="flex-1">
          <RadioButton
            checked={dataGroupingLevel === undefined}
            label="None"
            onClick={() => onChange(undefined!)}
          />
        </div>
        <div className="">
          <RadioButton
            checked={
              dataGroupingLevel?.source ===
              DataGroupingDimensionSpecSourceEnum.tag
            }
            label="Tag"
            onClick={() =>
              onChange({
                ...dataGroupingLevel,
                source: DataGroupingDimensionSpecSourceEnum.tag
              })
            }
          />
        </div>
        <div className="flex-1">
          <Input
            className={clsx(
              'h-8',
              dataGroupingLevel?.source ===
                DataGroupingDimensionSpecSourceEnum.tag &&
                (!dataGroupingLevel.tag || dataGroupingLevel.tag.length === 0)
                ? 'border border-red-500'
                : ''
            )}
            value={dataGroupingLevel?.tag}
            onChange={(e) => {
              onChange({
                ...dataGroupingLevel,
                tag: e.target.value
              });
              if (onClearError) {
                onClearError(idx + 1);
              }
            }}
            disabled={
              dataGroupingLevel?.source !==
              DataGroupingDimensionSpecSourceEnum.tag
            }
          />
          {error && <div className="text-red-700 text-xs">{error}</div>}
        </div>
        <RadioButton
          checked={
            dataGroupingLevel?.source ===
            DataGroupingDimensionSpecSourceEnum.column_value
          }
          label="Group by column"
          onClick={() =>
            onChange({
              ...dataGroupingLevel,
              source: DataGroupingDimensionSpecSourceEnum.column_value
            })
          }
        />
        <div className="flex-1">
          <ColumnSelect
            triggerClassName={clsx(
              dataGroupingLevel?.source ===
                DataGroupingDimensionSpecSourceEnum.column_value &&
                !dataGroupingLevel.column
                ? 'h-8 border border-red-500'
                : ''
            )}
            value={dataGroupingLevel?.column}
            onChange={(value) =>
              onChange({
                ...dataGroupingLevel,
                source: DataGroupingDimensionSpecSourceEnum.column_value,
                column: value
              })
            }
            scope={scope}
          />
        </div>
      </div>
    </div>
  );
};

export default DataGroupingDimensionItem;
