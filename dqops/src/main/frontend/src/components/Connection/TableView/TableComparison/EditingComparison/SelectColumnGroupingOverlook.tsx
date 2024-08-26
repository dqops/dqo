import React from 'react';
import { TableComparisonGroupingColumnPairModel } from '../../../../../api';
import SvgIcon from '../../../../SvgIcon';

type TSelecColumnGroupingOverlook = {
  onChangeEditColumnGrouping: (open: boolean) => void;
  dataGroupingArray: TableComparisonGroupingColumnPairModel[];
};
export default function SelecColumnGroupingOverlook({
  onChangeEditColumnGrouping,
  dataGroupingArray
}: TSelecColumnGroupingOverlook) {
  return (
    <div className="flex items-center">
      <SvgIcon
        name="chevron-right"
        className="w-5 h-5"
        onClick={() => onChangeEditColumnGrouping(true)}
      />
      <div className="px-4">
        Data grouping on compared table:{' '}
        {dataGroupingArray
          ?.map((item) => item?.compared_table_column_name ?? '')
          .filter(Boolean)
          .join(', ')}
      </div>
      <div className="pl-8">
        Data grouping on reference table:{' '}
        {dataGroupingArray
          ?.map((item) => item?.reference_table_column_name ?? '')
          .filter(Boolean)
          .join(', ')}
      </div>
    </div>
  );
}
