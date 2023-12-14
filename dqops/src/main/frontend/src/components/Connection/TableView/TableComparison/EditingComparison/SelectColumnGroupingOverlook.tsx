import React from 'react';
import SvgIcon from '../../../../SvgIcon';
import { TableComparisonGroupingColumnPairModel } from '../../../../../api';

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
          .map((x, index) =>
            index !==
            (dataGroupingArray?.map(
              (item) => item?.compared_table_column_name ?? ''
            ).length ?? 9) -
              1
              ? x + ','
              : x
          )}
      </div>
      <div className="pl-8">
        Data grouping on reference table:{' '}
        {dataGroupingArray
          ?.map((item) => item?.reference_table_column_name ?? '')
          .map((x, index) =>
            index !==
            (dataGroupingArray?.map(
              (item) => item?.reference_table_column_name ?? ''
            ).length ?? 9) -
              1
              ? x + ','
              : x
          )}
      </div>
    </div>
  );
}
