import React from 'react';

type TTableLevelResults = {
  tableComparisonResults: any;
  key: string;
};

export default function TableLevelResults({
  tableComparisonResults,
  key
}: TTableLevelResults) {
  return (
    <div className="gap-y-3">
      Results:
      <td className="flex justify-between w-2/3 ">
        <th className="text-xs font-light">Valid:</th>
        {
          tableComparisonResults?.table_comparison_results?.[key ?? '']
            ?.valid_results
        }
      </td>
      <td className="flex justify-between w-2/3 ">
        <th className="text-xs font-light">Errors:</th>
        {tableComparisonResults?.table_comparison_results?.[key ?? '']?.errors}
      </td>
      <td className="flex justify-between w-2/3 ">
        <th className="text-xs font-light">Fatal:</th>
        {tableComparisonResults?.table_comparison_results?.[key ?? '']?.fatals}
      </td>
      <td className="flex justify-between w-2/3 ">
        <th className="text-xs font-light">Warning:</th>
        {
          tableComparisonResults?.table_comparison_results?.[key ?? '']
            ?.warnings
        }
      </td>
    </div>
  );
}
