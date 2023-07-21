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
  //   const dataGroupOptions = dataGroupingConfigurations.map((item) => ({
  //     label: item.data_grouping_configuration_name ?? '',

  //     value: item.data_grouping_configuration_name ?? ''
  //   }));

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

  //   const handleChange = (value: string) => {
  //     const newDataGrouping = dataGroupingConfigurations.find(
  //       (item) => item.data_grouping_configuration_name === value
  //     );

  //     setDataGroupingConfiguration(newDataGrouping);
  //   };

  const [listOfColumns, setListOfColumns] = useState<Array<string>>([]);
  const fillArrayWithEmptyStrings = (length: number) => {
    const emptyStrings = Array.from({ length: length }, () => '');
    setListOfColumns(emptyStrings);
  };

  useEffect(() => {
    fillArrayWithEmptyStrings(9);
  }, []);

  console.log(listOfColumns);
  const handleColumnSelectChange = (value: string, index: number) => {
    const updatedList = [...listOfColumns];
    updatedList[index - 1] = value;
    setListOfColumns(updatedList);
  };

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
                  triggerClassName="my-0.5"
                  value={listOfColumns[index - 1]}
                  onChange={(value: string) =>
                    handleColumnSelectChange(value, index)
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
