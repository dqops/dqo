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
  refConnection?: string;
  refSchema?: string;
  reftable?: string;
  onSetRef?: (obj: { [key: number]: boolean }) => void;
  onSetNormal?: (obj: { [key: number]: boolean }) => void;
  onSetRefList?: (obj: Array<string>) => void;
  onSetNormalList?: (obj: Array<string>) => void;
};

export const SelectGroupColumnsTable = ({
  title,
  className,
  dataGroupingConfigurations,
  dataGroupingConfiguration,
  setDataGroupingConfiguration,
  goToCreateNew,
  placeholder,
  refConnection,
  refSchema,
  reftable,
  onSetNormal,
  onSetRef,
  onSetNormalList,
  onSetRefList
}: SelectDataGroupingForTableProps) => {
  const [dataGroupingConfigurationSpec, setDataGroupingConfigurationSpec] =
    useState<DataGroupingConfigurationSpec>();
  const history = useHistory();
  //   const dataGroupOptions = dataGroupingConfigurations.map((item) => ({
  //     label: item.data_grouping_configuration_name ?? '',

  //     value: item.data_grouping_configuration_name ?? ''
  //   }));
  const [myObject, setMyObject] = useState<{ [key: number]: boolean }>();
  const [fastCheck, setFastCheck] = useState<boolean>(false);
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
  const workOnMyObj = (): { [key: number]: boolean } => {
    const initialObject: { [key: number]: boolean } = {};
    let check = false;
    for (let i = listOfColumns.length - 1; i >= 0; i--) {
      if (listOfColumns[i].length === 0 && check === false) {
        initialObject[i] = false;
      } else if (listOfColumns[i].length !== 0 && check === false) {
        check = true;
        initialObject[i] = false;
      } else if (check === true && listOfColumns[i].length === 0) {
        initialObject[i] = true;
      } else if (check === true && listOfColumns[i].length !== 0) {
        initialObject[i] = false;
      }
    }
    return initialObject;
  };
  useEffect(() => {
    fillArrayWithEmptyStrings(9);
  }, [dataGroupingConfiguration]);

  useEffect(() => {
    if (onSetNormalList) {
      onSetNormalList(listOfColumns);
    }
    if (onSetRefList) {
      onSetRefList(listOfColumns);
    }
  }, [listOfColumns]);

  const handleColumnSelectChange = (value: string, index: number) => {
    const updatedList = [...listOfColumns];
    updatedList[index] = value;
    setListOfColumns(updatedList);
  };

  console.log(listOfColumns);

  return (
    <SectionWrapper className={clsx(className, 'text-sm')} title={title}>
      {/* <div className="bg-black w-5 h-5" onClick={() => workOnMyObj()}></div> */}
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
                  triggerClassName={clsx(
                    workOnMyObj()[index] === true
                      ? 'my-0.5 border border-red-500'
                      : 'my-0.5'
                  )}
                  value={listOfColumns[index]}
                  onChange={(value: string) =>
                    handleColumnSelectChange(value, index)
                  }
                  placeholder={placeholder}
                  refConnection={refConnection}
                  refSchema={refSchema}
                  refTable={reftable}
                />
              </tr>
            );
          })}
        </tbody>
      </table>
    </SectionWrapper>
  );
};
