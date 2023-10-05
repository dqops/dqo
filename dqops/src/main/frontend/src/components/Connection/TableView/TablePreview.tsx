import React from 'react'
import { TableColumnsStatisticsModel } from '../../../api'

interface tablePreviewProps {
    statistics : TableColumnsStatisticsModel;
}
interface MyData {
    null_percent: number | undefined;
    unique_value: number | undefined;
    null_count?: number | undefined;
    nameOfCol?: string | undefined;
    minimalValue?: string | undefined;
    maximumValue?: string | undefined;
    detectedDatatypeVar: number | undefined;
    length?: number | undefined;
    scale?: number | undefined;
    importedDatatype?: string | undefined;
}

const firstColumnObjects = [
    { value: "detectedDatatypeVar", label: "detected type" },
    { value: "length", label: "length" },
    { value: "scale", label: "scale" },
    { value: "minimalValue", label: "min" },
    { value: "maximumValue", label: "max" },
    { value: "null_count", label: "null count" },
    { value: "null_percent", label: "null percent" },
    { value: "unique_value", label: "distinct count" },
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
          unique_value: Number(x.statistics?.find((item) => item.collector === 'distinct_count')?.result),
          null_count: Number(x.statistics?.find((item) => item.collector === 'nulls_count')?.result),
          detectedDatatypeVar: Number(x.statistics?.find((item) => item.collector === 'string_datatype_detect')?.result),
          nameOfCol: x.column_name,
          minimalValue: x.statistics?.find((item) => item.collector === 'min_value')?.result,
          maximumValue: x.statistics?.find((item) => item.collector === 'max_value')?.result,
          length: x.type_snapshot?.length,
          scale: x.type_snapshot?.scale,
          importedDatatype: String(x.type_snapshot?.column_type),
        };
        return newData;
      });

  console.log(statistics)

  return (
        <table className='w-screen mt-5 p-4'>
            <thead className='border-b border-b-gray-400 relative flex bg-green-500'>
                <th className="px-6 py-4 text-left border border-gray-300 block w-50">Column name</th>
                {statistics.column_statistics?.map((x, index) => 
                 <th key={index} className="px-6 py-4 text-left border border-gray-300 block w-50">{x.column_name}</th>)}
            </thead>
            <tbody className=''>
                {firstColumnObjects.map((x, index) =>
                <tr key= {index} className='flex'>
                    <td className='px-6 py-2 text-left  block w-50 border border-gray-300' >{x.label}</td>
                    {dataArray.map((y, jIndex) => 
                    <td key={jIndex} className='px-6 py-2 text-left block w-50 border border-gray-300'>{renderValue(y[x.value as keyof MyData])}</td>
                    )}
                    
                </tr>
              )}
            </tbody>
        </table>
  )
}
