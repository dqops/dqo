import SectionWrapper from '../../Dashboard/SectionWrapper';
import React, { useEffect, useState } from 'react';
import {
  DataGroupingConfigurationListModel,
  DataGroupingConfigurationSpec,
  DataGroupingDimensionSpecSourceEnum
} from '../../../api';
import Select from '../../Select';
import Button from '../../Button';
import { DataGroupingConfigurationsApi } from '../../../services/apiClient';
import clsx from 'clsx';
import { useHistory } from 'react-router-dom';

type SelectDataGroupingForTableProps = {
  title: string;
  className?: string;
  dataGroupingConfigurations: DataGroupingConfigurationListModel[];
  dataGroupingConfiguration?: DataGroupingConfigurationListModel;
  setDataGroupingConfiguration: (
    value?: DataGroupingConfigurationListModel
  ) => void;
  goToCreateNew: () => void;
};

export const SelectDataGroupingForTable = ({
  title,
  className,
  dataGroupingConfigurations,
  dataGroupingConfiguration,
  setDataGroupingConfiguration,
  goToCreateNew
}: SelectDataGroupingForTableProps) => {
  const [dataGroupingConfigurationSpec, setDataGroupingConfigurationSpec] =
    useState<DataGroupingConfigurationSpec>();
  const history = useHistory();

  const dataGroupOptions = dataGroupingConfigurations.map((item) => ({
    label: item.data_grouping_configuration_name ?? '',
    value: item.data_grouping_configuration_name ?? ''
  }));

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

  const handleChange = (value: string) => {
    const newDataGrouping = dataGroupingConfigurations.find(
      (item) => item.data_grouping_configuration_name === value
    );

    setDataGroupingConfiguration(newDataGrouping);
  };

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
    <SectionWrapper className={clsx(className, 'text-sm')} title={title}>
      <div className="flex gap-4 mb-4">
        <Select
          className="flex-1"
          options={dataGroupOptions}
          value={dataGroupingConfiguration?.data_grouping_configuration_name}
          onChange={handleChange}
        />
        <Button
          label="Create New"
          color="primary"
          variant="outlined"
          className="h-9"
          onClick={goToCreateNew}
        />
      </div>

      <table className="w-full">
        <thead>
          <tr>
            <th className="text-left py-1.5">Type</th>
            <th className="text-left py-1.5">Column / Static value(tag)</th>
          </tr>
        </thead>
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
      </table>
    </SectionWrapper>
  );
};
