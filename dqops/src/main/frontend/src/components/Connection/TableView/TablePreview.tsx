import React from 'react'
import { TableColumnsStatisticsModel } from '../../../api'
import { datatype_detected } from '../../../shared/constants';

interface tablePreviewProps {
    statistics : TableColumnsStatisticsModel;
}
interface MyData {
    null_percent: number | undefined;
    null_count?: number | undefined;
    nameOfCol?: string | undefined;
    minimalValue?: string | undefined;
    maximumValue?: string | undefined;
    detectedDatatypeVar: string | undefined;
    length?: number | undefined;
    scale?: number | undefined;
    importedDatatype?: string | undefined;
    not_null_count?: number | undefined;
    not_null_percent?: number | undefined;
    unique_value: number | undefined;
    unique_percent: number | undefined;
    duplicate_value: number | undefined;
    duplicate_percent: number | undefined
}

const firstColumnObjects = [
    { value: "detectedDatatypeVar", label: "Detected datatype" },
    // { value: "length", label: "length" },
    // { value: "scale", label: "scale" },
    { value: "minimalValue", label: "Min value" },
    { value: "maximumValue", label: "Max value" },
    { value: "unique_value", label: "Distinct count" },
    { value: "unique_percent", label:"Distinct percent"},
    { value: "duplicate_value", label: "Duplicate count" },
    { value: "duplicate_percent", label:"Duplicate percent"},
    { value: "null_count", label: "Null count" },
    { value: "null_percent", label: "Null percent" },
    { value: "not_null_count", label: "Not null count" },
    { value: "not_null_percent", label: "Not null percent" },

  ];

  
export default function TablePreview({statistics} : tablePreviewProps) {

    const renderValue = (value: any) => {
        if (typeof value === 'boolean') {
          return value ? 'Yes' : 'No';
        }
        if (typeof value === 'object') {
          return value.toString();
        }
        return value;
      };

    const dataArray = (statistics?.column_statistics || []).map((x) => {
        const newData = {
          null_percent: Number(x.statistics?.find((item) => item.collector === 'nulls_percent')?.result),
          null_count: Number(x.statistics?.find((item) => item.collector === 'nulls_count')?.result),
          not_null_percent: Number(x.statistics?.find((item) => item.collector === 'not_nulls_percent')?.result),
          not_null_count: Number(x.statistics?.find((item) => item.collector === 'not_nulls_count')?.result),
          unique_value: Number(x.statistics?.find((item) => item.collector === 'distinct_count')?.result),
          unique_percent: Number(x.statistics?.find((item) => item.collector === 'distinct_percent')?.result),
          duplicate_value: Number(x.statistics?.find((item) => item.collector === 'duplicate_count')?.result),
          duplicate_percent: Number(x.statistics?.find((item) => item.collector === 'duplicate_percent')?.result),
          detectedDatatypeVar: datatype_detected(Number(x.statistics?.find((item) => item.collector === 'string_datatype_detect')?.result)),
          nameOfCol: x.column_name,
          minimalValue: x.statistics?.find((item) => item.collector === 'min_value')?.result,
          maximumValue: x.statistics?.find((item) => item.collector === 'max_value')?.result,
          length: x.type_snapshot?.length,
          scale: x.type_snapshot?.scale,
          importedDatatype: String(x.type_snapshot?.column_type),
        };
        return newData;
      });


      const arrayOfSamples = statistics.column_statistics?.map((x) => {
        const newData = {
          sampleArray: x.statistics?.filter((item) => item.collector === "column_samples").map((c) => c.result)
        }
        return newData;
      })

      let count = 0;
      if (arrayOfSamples) {
        arrayOfSamples.forEach((x) => {
          if (x.sampleArray && x.sampleArray.length > count) {
            count = x.sampleArray.length;
          }
        });
      }
   const arrayOfSamplesIndexes = [];

    for (let i = 1; i <= count; i++) {
      arrayOfSamplesIndexes.push(i);
    }

  return (
        <table className='w-screen mt-5 p-4'>
            <thead className='border-b border-b-gray-400 relative flex '>
                <th className="px-6 py-4 text-left border border-gray-300 block w-50">Column name</th>
                {statistics.column_statistics?.map((x, index) => 
                 <th key={index} className="px-6 py-4 text-left border border-gray-300 block w-50">{x.column_name}</th>)}
            </thead>
            <tbody className=''>
                {firstColumnObjects.map((x, index) =>
                <tr key= {index} className='flex'>
                    <td className='px-6 py-2 text-left  block w-50 border border-gray-300 font-semibold' >{x.label}</td>
                    {dataArray.map((y, jIndex) => 
                    <td key={jIndex} className='px-6 py-2 text-left block w-50 border border-gray-300'>{renderValue(y[x.value as keyof MyData])}</td>
                    )}
                    </tr> 
              )}
               {arrayOfSamplesIndexes?.map((x, index) =>
                <tr key= {index} className='flex'>
                    <td className='px-6 py-2 text-left  block w-50 border border-gray-300 font-semibold' >{"Sample value " + (Number(index) + 1)}</td>
                  {arrayOfSamples?.map((y) => 
                    <td key={index} className='px-6 py-2 text-left block w-50 border border-gray-300'>{renderValue(y.sampleArray?.at(x-1))}</td>
                  )}
                </tr> 
              )}
            </tbody>
        </table>
  )
}
