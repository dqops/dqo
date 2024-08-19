import React from 'react';

export default function RuleMiningChecksContainerHeader({
  ruleParamenterConfigured
}: {
  ruleParamenterConfigured: boolean;
}) {
  return (
    <thead>
      {ruleParamenterConfigured && (
        <tr>
          <td
            className="text-center whitespace-nowrap text-gray-700 py-2 px-4 font-semibold bg-gray-200"
            colSpan={2}
          ></td>
          <td className="text-center whitespace-nowrap text-gray-700 py-2 px-4 font-semibold bg-gray-200 relative pl-1">
            Passing rule (KPI met)
            <div className="w-4 bg-white absolute h-full right-0 top-0"></div>
          </td>
          <td
            className="text-center whitespace-nowrap text-gray-700 py-2 px-4 font-semibold bg-gray-200"
            colSpan={2}
          >
            Failing rule (KPI not met)
          </td>
        </tr>
      )}
      <tr>
        <th className="text-left whitespace-nowrap text-gray-700 py-2 px-4 font-semibold bg-gray-200">
          <div className="flex items-center ">
            <div className="flex space-x-1 items-center w-45">
              <span className="mr-1">Data quality check</span>
            </div>
          </div>
        </th>
        <th className="text-start whitespace-nowrap text-gray-700 py-2 px-4 font-semibold bg-gray-200"></th>
        {ruleParamenterConfigured ? (
          <>
            <td className="text-center whitespace-nowrap text-gray-700 py-2 px-4   font-semibold bg-yellow-100 relative pl-1 min-w-44">
              Warning threshold
              <div className="w-4 bg-white absolute h-full right-0 top-0"></div>
            </td>
            <td className="text-center whitespace-nowrap text-gray-700 py-2 px-4   font-semibold bg-orange-100">
              Error threshold
            </td>
            <td className="text-center whitespace-nowrap text-gray-700 py-2 px-4   font-semibold bg-red-100">
              Fatal threshold
            </td>
          </>
        ) : (
          <>
            <td className="text-center whitespace-nowrap text-gray-700 py-2 px-4   font-semibold bg-gray-200">
              <div className="flex items-center !w-40">
                Issue severity level
              </div>
            </td>
            <td className="text-left whitespace-nowrap text-gray-700 py-2 px-4   font-semibold bg-gray-200">
              <div className="flex items-center">Rule thresholds</div>
            </td>
            <td className="text-left whitespace-nowrap text-gray-700 py-2 px-4   font-semibold bg-gray-200">
              <div className="w-38 h-full bg-gray-200"></div>
            </td>
          </>
        )}
      </tr>
    </thead>
  );
}
