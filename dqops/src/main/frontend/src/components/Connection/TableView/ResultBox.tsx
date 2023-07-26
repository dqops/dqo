import React from 'react';
import { ComparisonCheckResultModel } from '../../../api';

interface data {
  item: ComparisonCheckResultModel;
}

const ResultBox = ({ item }: data) => {
  console.log(item);
  return (
    <tr className="flex flex-col text-xs font-light justify-start items-start bg-green-500">
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
        <a className="text-blue-300 underline" style={{ whiteSpace: 'nowrap' }}>
          Show mismatches
        </a>
      </td>
    </tr>
  );
};
export default ResultBox;
