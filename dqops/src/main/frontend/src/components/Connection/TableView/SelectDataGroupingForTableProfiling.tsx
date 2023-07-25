import React from 'react';
import { DataGroupingConfigurationBasicModel } from '../../../api';

type SelectDataGroupingForTableProps = {
  title: string;
  className?: string;
  dataGroupingConfigurations: DataGroupingConfigurationBasicModel[];
  dataGroupingConfiguration?: DataGroupingConfigurationBasicModel;
  setDataGroupingConfiguration: (
    value?: DataGroupingConfigurationBasicModel
  ) => void;
  goToCreateNew: () => void;
  isExtended: boolean;
  columnArray?: Array<string>;
};

export const SelectDataGroupingForTableProfiling = ({
  dataGroupingConfiguration,
  title,
  isExtended,
  columnArray
}: SelectDataGroupingForTableProps) => {
  return (
    <table className="w-full ml-30">
      <thead className="h-25">
        <div className="flex">
          <div className="flex flex-col gap-y-3">
            <span className="font-bold h-4.5">{title}</span>
            <span>
              {dataGroupingConfiguration?.data_grouping_configuration_name}
            </span>
          </div>
        </div>
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
