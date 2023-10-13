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
    { value: "importedDatatype", label: "Imported datatype"},
    // { value: "length", label: "length" },
    // { value: "scale", label: "scale" },
    { value: "minimalValue", label: "Min value" },
    { value: "maximumValue", label: "Max value" },
    { value: "unique_value", label: "Distinct count" },
    { value: "unique_percent", label:"Distinct percent"},
    { value: "duplicate_value", label: "Duplicate count" },
    { value: "duplicate_percent", label:"Duplicate percent"},
    { value: "null_count", label: "Nulls count" },
    { value: "null_percent", label: "Nulls percent" },
    { value: "not_null_count", label: "Not nulls count" },
    { value: "not_null_percent", label: "Not nulls percent" },
  ];

  
export default function TablePreview({statistics} : tablePreviewProps) {

    const renderValue = (value: any) => {
      if(typeof value === 'number' ){
        if(isNaN(value)){
          return ''
        }
        else if(Number(value) % 1 !== 0) {
          return Math.round(Number(value) * 100) / 100
        }
      }
      else if (typeof value === 'boolean') {
          return value ? 'Yes' : 'No';
        }
      else if (typeof value === 'object') {
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
    <div className='w-screen mt-5 p-4 grid grid-cols-1 gap-4'>
    <table className='w-full'>
      <thead className='border-b border-b-gray-400'>
        <tr>
          <th className='px-6 py-4 text-left border border-gray-300 bg-gray-300 max-w-60 whitespace-normal' >
            Column name
          </th>
          {statistics.column_statistics?.map((x, index) => (
            <th key={index} className='px-6 py-4 text-left border border-gray-300 bg-gray-300 max-w-60 whitespace-normal'>
              {x.column_name}
            </th>
          ))}
        </tr>
      </thead>
      <tbody>
        {firstColumnObjects.map((x, index) => (
          <tr key={index}>
            <td className='px-6 py-2 text-left border border-gray-300 font-semibold bg-gray-50 min-w-50 max-w-60 whitespace-normal break-all' >
              {x.label}
            </td>
            {dataArray.map((y, jIndex) => (
              <td
                key={jIndex}
                className='px-6 py-2 text-left border border-gray-300 bg-gray-50 min-w-30 max-w-60 whitespace-normal break-all'
                
              >
                {renderValue(y[x.value as keyof MyData])} {(x.label.includes("percent") && y[x.value as keyof MyData]) ? "%" : ""}
              </td>
            ))}
          </tr>
        ))}
  
        <tr className='w-full h-12'>
          <td className='px-6 py-2 text-left font-semibold' style={{ whiteSpace: 'nowrap' }}>
            Sample values
          </td>
        </tr>
  
        {arrayOfSamplesIndexes?.map((x, index) => (
          <tr key={index}>
            <td className='px-6 py-2 text-left border border-gray-300 font-semibold' style={{ whiteSpace: 'nowrap' }}>
              # {(Number(index) + 1)}
            </td>
            {arrayOfSamples?.map((y, jIndex) => (
              <td
                key={jIndex}
                className='px-6 py-2 text-left border border-gray-300 max-w-60 whitespace-normal break-all'
              >
                {renderValue(y.sampleArray?.at(index) ? String(y.sampleArray?.at(index)).slice(0, 50) : "" )}
              </td>
            ))}
          </tr>
        ))}
      </tbody>
    </table>
  </div>  
  )
}
