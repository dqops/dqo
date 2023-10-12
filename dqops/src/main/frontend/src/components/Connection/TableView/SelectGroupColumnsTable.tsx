import SectionWrapper from '../../Dashboard/SectionWrapper';
import React, { useEffect, useState } from 'react';
import { ColumnApiClient } from '../../../services/apiClient';
import clsx from 'clsx';
import { useParams } from 'react-router-dom';
import ColumnSelect from '../../DataQualityChecks/ColumnSelect';
import { Tooltip } from '@material-tailwind/react';

type SelectDataGroupingForTableProps = {
  title: string;
  className?: string;
  placeholder?: string;
  refConnection?: string;
  refSchema?: string;
  refTable?: string;
  onSetRef?: (obj: { [key: number]: boolean }) => void;
  onSetNormal?: (obj: { [key: number]: boolean }) => void;
  onChangeDataGroupingArray: (reference: boolean, index: number, columnName: string) => void
  warningMessageList?: Array<boolean>;

  object?: { [key: number]: number };
  responseList: Array<string>;
  checkIfDistinctCountIsBiggerThanLimit?: (columnName: string, index: number, reference : boolean) => void
  dqoLimit?: number
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
  onChangeDataGroupingArray,
  responseList,
  object,
  warningMessageList,
  checkIfDistinctCountIsBiggerThanLimit,
  dqoLimit
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
    refConnection,
    refSchema,
    refTable
  ]);


  const handleColumnSelectChange = (value: string, index: number) => {
    if (refTable) {
      onChangeDataGroupingArray(true, index, value)
    } else {
      onChangeDataGroupingArray(false, index, value);
    }
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

    fetchData();
  }, [connection, schema, table, refTable, ref]);

  const message = `The last known distinct count statistics for this column detected more than ${dqoLimit} rows or the statistics were not collected for this table yet`

  return (
    <SectionWrapper className={clsx(className, 'text-sm')} title={title}>
      <table className="w-full">
        <tbody>
          {[1, 2, 3, 4, 5, 6, 7, 8, 9].map((item, index) => {
            return (
              <tr key={index} className=''>
              <td className="my-1.5 w-11/12">
                <ColumnSelect
                  triggerClassName={clsx(
                    object && object[index] === 1
                    ? 'my-0.5 border border-red-500'
                      : options.find((x) => x.label === listOfColumns[index] || listOfColumns[index]?.length === 0) ?'my-0.5' :  "my-0.5 text-red-500",
                     
                      )}
                  value={responseList[index] ?? ""}
                  onChange={(value: string) =>{
                    handleColumnSelectChange(value, index),
                    checkIfDistinctCountIsBiggerThanLimit && checkIfDistinctCountIsBiggerThanLimit(value, index, refTable ? true : false)
                  }}
                  placeholder={placeholder}
                  refConnection={refConnection}
                  refSchema={refSchema}
                  refTable={refTable}
                  passedOptions={options}
                />
              </td>
                {warningMessageList?.[index] === true ? 
                <Tooltip content={message}>
                  <td className='bg-red-500 block mx-5 mt-3' style={{height: "20px", width: "20px", borderRadius: "10px"}}> </td> 
                  </Tooltip> : null }
              </tr>
            );
          })}
        </tbody>
      </table>
    </SectionWrapper>
  );
};
