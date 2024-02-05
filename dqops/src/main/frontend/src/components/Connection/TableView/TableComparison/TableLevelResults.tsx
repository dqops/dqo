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
    <div className="gap-y-3 h-35">
      <td className="flex justify-between w-2/3 ">
        <th className="text-sm font-light">Results:</th>
      </td>
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
      {type.includes('row') ? (
        <td>
          <a className="group relative text-blue-300 underline whitespace-nowrap cursor-pointer">
            Show mismatches
            <section
              className="hidden group-hover:grid grid-cols-1 absolute px-1 gap-y-1 rounded-md border border-gray-400 z-50 bg-white text-black no-underline font-light"
              style={{
                minWidth: '200px',
                width: 'max-content',
                whiteSpace: 'nowrap',
                minHeight: '30px'
              }}
            >
              {tableComparisonResults?.table_comparison_results?.[type ?? '']
                ?.not_matching_data_groups ? (
                (
                  tableComparisonResults?.table_comparison_results?.[type ?? '']
                    ?.not_matching_data_groups as string[]
                ).map((x, index) => (
                  <span key={index}>
                    {x.replace(/./, (c) => c.toUpperCase())}
                  </span>
                ))
              ) : (
                <span>Whole table</span>
              )}
            </section>
          </a>
        </td>
      ) : null}
    </div>
  );
}
