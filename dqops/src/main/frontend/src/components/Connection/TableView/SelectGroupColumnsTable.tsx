import SectionWrapper from '../../Dashboard/SectionWrapper';
import React, { useEffect, useState } from 'react';
import {
  DataGroupingConfigurationBasicModel,
  DataGroupingConfigurationSpec,
  DataGroupingDimensionSpecSourceEnum
} from '../../../api';
import Select from '../../Select';
import Button from '../../Button';
import { DataGroupingConfigurationsApi } from '../../../services/apiClient';
import clsx from 'clsx';
import { useHistory } from 'react-router-dom';
import ColumnSelect from '../../DataQualityChecks/ColumnSelect';

type SelectDataGroupingForTableProps = {
  title: string;
  className?: string;
  dataGroupingConfigurations: DataGroupingConfigurationBasicModel[];
  dataGroupingConfiguration?: DataGroupingConfigurationBasicModel;
  setDataGroupingConfiguration: (
    value?: DataGroupingConfigurationBasicModel
  ) => void;
  goToCreateNew: () => void;
  dataGroupingConfigurationSpec?: DataGroupingConfigurationSpec;
  placeholder?: string;
};

export const SelectGroupColumnsTable = ({
  title,
  className,
  dataGroupingConfigurations,
  dataGroupingConfiguration,
  setDataGroupingConfiguration,
  goToCreateNew,
  placeholder
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

  const onChangeDataGroupingConfiguration = (spec: string): void => {};

  return (
    <SectionWrapper className={clsx(className, 'text-sm')} title={title}>
      <table className="w-full">
        <tbody>
          {[1, 2, 3, 4, 5, 6, 7, 8, 9].map((item, index) => {
            return (
              <tr key={index} className="my-1.5">
                <ColumnSelect
                  // triggerClassName={clsx(
                  //   getDataGroupingDimensionLevel(index)?.source ===
                  //     DataGroupingDimensionSpecSourceEnum.column_value &&
                  //     !dataGroupingLevel.column
                  //     ? 'h-8 border border-red-500'
                  //     : ''
                  // )}

                  onChange={(value: string) =>
                    onChangeDataGroupingConfiguration(value)
                  }
                  placeholder={placeholder}
                />
              </tr>
            );
          })}
        </tbody>
      </table>
    </SectionWrapper>
  );
};
