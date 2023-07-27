import React from 'react';
import { ComparisonCheckResultModel } from '../../../api';
import clsx from 'clsx';

interface data {
  item: ComparisonCheckResultModel;
}

const ResultBox = ({ item }: data) => {
  console.log(item);
  return (
    <tr className="flex flex-col text-xs font-light justify-start items-start  absolute top-0">
      <td className="flex justify-between w-2/3 ">
        <th className="text-xs font-light">Valid:</th>
        {item.valid_results}
      </td>
      <td className="flex justify-between w-2/3 ">
        <th className="text-xs font-light">Errors:</th>
        {item.errors}
      </td>
      <td className="flex justify-between w-2/3 ">
        <th className="text-xs font-light">Fatal:</th>
        {item.fatals}
      </td>
      <td className="flex justify-between w-2/3 ">
        <th className="text-xs font-light">Warning:</th>
        {item.warnings}
      </td>
      <td>
        <a className="group relative text-blue-300 underline whitespace-nowrap cursor-pointer">
          Show mismatches
          <section
            className={clsx(
              'hidden group-hover:grid grid-cols-3 absolute top-4 left-0 px-1 gap-y-1 rounded-md border border-gray-400 z-50 bg-white text-black no-underline',
              item.not_matching_data_groups ? 'w-40 h-29 ' : 'w-40 h-10'
            )}
            style={{ right: '20' }}
          >
            {item.not_matching_data_groups ? (
              item.not_matching_data_groups.map((x, index) => (
                <span key={index}>{x}</span>
              ))
            ) : (
              <span>No mismatches detected</span>
            )}
          </section>
        </a>
      </td>
    </tr>
  );
};
export default ResultBox;
