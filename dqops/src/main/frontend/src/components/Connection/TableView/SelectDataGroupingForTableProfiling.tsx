import React from 'react';

type SelectDataGroupingForTableProps = {
  isExtended: boolean;
  columnArray?: Array<string>;
};

export const SelectDataGroupingForTableProfiling = ({
  isExtended,
  columnArray
}: SelectDataGroupingForTableProps) => {
  return (
    <table className="w-full ml-30">
      <thead className="h-25">
        {isExtended === true && (
          <tr>
            <th className="text-left py-2 w-100">Group by column</th>
          </tr>
        )}
      </thead>
      {isExtended && (
        <tbody>
          {[1, 2, 3, 4, 5, 6, 7, 8, 9].map((item, index) => {
            return (
              <tr key={index} className="h-5">
                <td className="py-0">{columnArray && columnArray[index]}</td>
              </tr>
            );
          })}
        </tbody>
      )}
    </table>
  );
};
