import SectionWrapper from '../../Dashboard/SectionWrapper';
import React, { useEffect, useState } from 'react';
import { DataGroupingConfigurationSpec } from '../../../api';
import { ColumnApiClient } from '../../../services/apiClient';
import clsx from 'clsx';
import { useParams } from 'react-router-dom';
import ColumnSelect from '../../DataQualityChecks/ColumnSelect';

type SelectDataGroupingForTableProps = {
  title: string;
  className?: string;
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
  placeholder,
  refConnection,
  refSchema,
  refTable,
  onSetNormalList,
  onSetRefList,
  responseList,
  object
}: SelectDataGroupingForTableProps) => {
  const [fetched, setFetched] = useState(false);
  const {
    connection,
    schema,
    table
  }: { connection: string; schema: string; table: string } = useParams();

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
          console.error('Error:', error);
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
            setRef(true);
          } catch (error) {
            console.error('Error:', error);
          }
        }
      }
    };
    if (ref === false) {
      fetchData();
    }
  }, [connection, schema, table, refConnection, refSchema, refTable, ref]);

  return (
    <SectionWrapper className={clsx(className, 'text-sm')} title={title}>
      <table className="w-full">
        <tbody>
          {[1, 2, 3, 4, 5, 6, 7, 8, 9].map((item, index) => {
            return (
              <tr key={index} className="my-1.5">
                <ColumnSelect
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