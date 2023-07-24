import SectionWrapper from '../../Dashboard/SectionWrapper';
import React, { useEffect, useState } from 'react';
import {
  DataGroupingConfigurationBasicModel,
  DataGroupingConfigurationSpec
} from '../../../api';
import {
  ColumnApiClient,
  ConnectionApiClient,
  DataGroupingConfigurationsApi
} from '../../../services/apiClient';
import clsx from 'clsx';
import { useHistory, useParams } from 'react-router-dom';
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
  refTable?: string;
  onSetRef?: (obj: { [key: number]: boolean }) => void;
  onSetNormal?: (obj: { [key: number]: boolean }) => void;
  onSetRefList?: (obj: Array<string>) => void;
  onSetNormalList?: (obj: Array<string>) => void;

  object?: { [key: number]: number };
  responseList?: Array<string>;
};
interface Option {
  label: string;
  value: string;
}

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
  refTable,
  onSetNormalList,
  onSetRefList,
  responseList,
  object
}: SelectDataGroupingForTableProps) => {
  const [dataGroupingConfigurationSpec, setDataGroupingConfigurationSpec] =
    useState<DataGroupingConfigurationSpec>();
  const [fetched, setFetched] = useState(false);
  const {
    connection,
    schema,
    table
  }: { connection: string; schema: string; table: string } = useParams();
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
  const fillArray = (length: number) => {
    const emptyStrings = Array.from({ length: length }, () => '');
    if (responseList === undefined || responseList.length === 0) {
      setListOfColumns(emptyStrings);
    } else {
      const emptyStringsArray = Array.from(
        { length: 9 - responseList.length },
        () => ''
      );
      const combinedArrayVar = responseList.concat(emptyStringsArray);

      setListOfColumns(combinedArrayVar);
      setFetched(true);
    }
    console.log('inside');
  };

  useEffect(() => {
    if (fetched === false && responseList?.length !== 0) {
      fillArray(9);
    }
  }, [
    connection,
    schema,
    table,
    responseList,
    refConnection,
    refSchema,
    refTable
  ]);

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
  const [options, setOptions] = useState<Option[]>([]);

  const [ref, setRef] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      if (refConnection && refSchema && refTable) {
        try {
          const response = await ColumnApiClient.getColumns(
            refConnection,
            refSchema,
            refTable
          );
          setOptions([
            { label: ' - ', value: '' },
            ...response.data.map((item) => ({
              label: item.column_name || '',
              value: item.column_name || ''
            }))
          ]);
          setRef(true);
        } catch (error) {
          console.error('Błąd pobierania danych:', error);
        }
      } else {
        if (
          table &&
          !refTable &&
          !refConnection &&
          !refSchema &&
          ref === false
        ) {
          try {
            const response = await ColumnApiClient.getColumns(
              connection,
              schema,
              table
            );
            setOptions([
              { label: ' - ', value: '' },
              ...response.data.map((item) => ({
                label: item.column_name || '',
                value: item.column_name || ''
              }))
            ]);
          } catch (error) {
            console.error('Error:', error);
          }
        }
      }
    };

    fetchData();
  }, [connection, schema, table, refConnection, refSchema, refTable, ref]);

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
                  triggerClassName={clsx(
                    object && object[index] === 1
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
                  refTable={refTable}
                  passedOptions={options}
                />
              </tr>
            );
          })}
        </tbody>
      </table>
    </SectionWrapper>
  );
};
