import React, { useEffect, useState } from 'react';
import {
  DataGroupingConfigurationBasicModel,
  DataGroupingConfigurationSpec,
  DataGroupingDimensionSpecSourceEnum
} from '../../../api';

import { DataGroupingConfigurationsApi } from '../../../services/apiClient';

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
};

export const SelectDataGroupingForTableProfiling = ({
  className,
  dataGroupingConfiguration,
  title,
  isExtended
}: SelectDataGroupingForTableProps) => {
  const [dataGroupingConfigurationSpec, setDataGroupingConfigurationSpec] =
    useState<DataGroupingConfigurationSpec>();

  useEffect(() => {
    if (dataGroupingConfiguration) {
      DataGroupingConfigurationsApi.getTableGroupingConfiguration(
        dataGroupingConfiguration.connection_name ?? '',
        dataGroupingConfiguration.schema_name ?? '',
        dataGroupingConfiguration.table_name ?? '',
        dataGroupingConfiguration.data_grouping_configuration_name || ''
      ).then((res) => {
        setDataGroupingConfigurationSpec(res.data.spec);
      });
    }
  }, [dataGroupingConfiguration]);

  const values = {
    [DataGroupingDimensionSpecSourceEnum.tag]: 'Tag',
    [DataGroupingDimensionSpecSourceEnum.column_value]: 'Group by column'
  };

  const getDataGroupingDimensionLevel = (index: number) => {
    if (index === 0) return dataGroupingConfigurationSpec?.level_1;
    if (index === 1) return dataGroupingConfigurationSpec?.level_2;
    if (index === 2) return dataGroupingConfigurationSpec?.level_3;
    if (index === 3) return dataGroupingConfigurationSpec?.level_4;
    if (index === 4) return dataGroupingConfigurationSpec?.level_5;
    if (index === 5) return dataGroupingConfigurationSpec?.level_6;
    if (index === 6) return dataGroupingConfigurationSpec?.level_7;
    if (index === 7) return dataGroupingConfigurationSpec?.level_8;
    if (index === 8) return dataGroupingConfigurationSpec?.level_9;
  };

  return (
    <table className="w-full ml-44">
      <thead className="h-25">
        <div className="flex">
          <div className="flex flex-col gap-y-3 mb-5">
            <span>{title}</span>
            <span className="ml-2">id </span>
          </div>
        </div>
        {isExtended === true && (
          <tr>
            <th className="text-left py-1.5 ">Type</th>
            <th className="text-left py-1.5">Column / Static value(tag)</th>
          </tr>
        )}
      </thead>
      {isExtended && (
        <tbody>
          {[1, 2, 3, 4, 5, 6, 7, 8, 9].map((item, index) => {
            const level = getDataGroupingDimensionLevel(index);
            return (
              <tr key={index}>
                <td className="py-1.5">
                  {level?.source ? values[level?.source] : 'None'}
                </td>
                <td className="py-1.5">
                  <div>
                    {level?.source ===
                      DataGroupingDimensionSpecSourceEnum.tag && level?.tag}
                  </div>
                  <div>
                    {level?.source ===
                      DataGroupingDimensionSpecSourceEnum.column_value &&
                      level?.column}
                  </div>
                </td>
              </tr>
            );
          })}
        </tbody>
      )}
    </table>
  );
};
