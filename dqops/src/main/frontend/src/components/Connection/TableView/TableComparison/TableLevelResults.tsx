import React from 'react';

type TTableLevelResults = {
  tableComparisonResults: any;
  type: string;
};

export default function TableLevelResults({
  tableComparisonResults,
  type
}: TTableLevelResults) {
  return (
    <div className="gap-y-3">
      Results:
      <td className="flex justify-between w-2/3 ">
        <th className="text-xs font-light">Valid:</th>
        {
          tableComparisonResults?.table_comparison_results?.[type ?? '']
            ?.valid_results
        }
      </td>
      <td className="flex justify-between w-2/3 ">
        <th className="text-xs font-light">Errors:</th>
        {tableComparisonResults?.table_comparison_results?.[type ?? '']?.errors}
      </td>
      <td className="flex justify-between w-2/3 ">
        <th className="text-xs font-light">Fatal:</th>
        {tableComparisonResults?.table_comparison_results?.[type ?? '']?.fatals}
      </td>
      <td className="flex justify-between w-2/3 ">
        <th className="text-xs font-light">Warning:</th>
        {
          tableComparisonResults?.table_comparison_results?.[type ?? '']
            ?.warnings
        }
      </td>
    </div>
  );
}
