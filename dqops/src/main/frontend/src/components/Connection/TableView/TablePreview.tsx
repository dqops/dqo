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

    const nullPercentData = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'nulls_percent')
      .map((item) => Number(renderValue(item.result)))
  );
  const uniqueCountData = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'distinct_count')
      .map((item) => item.result)
  );
  const nullCountData = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'nulls_count')
      .map((item) => renderValue(item.result))
  );
  const detectedDatatypeVar = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'string_datatype_detect')
      .map((item) => item.result)
  );

  const columnNameData = statistics?.column_statistics?.map(
    (x) => x.column_name
  );
  const minimalValueData = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'min_value')
      .map((item) => item.result)
  );

  const maximumValueData = statistics?.column_statistics?.map((x) =>
    x.statistics
      ?.filter((item) => item.collector === 'max_value')
      .map((item) => item.result)
  );
  const lengthData = statistics?.column_statistics?.map(
    (x) => x.type_snapshot?.length
  );

  const scaleData = statistics?.column_statistics?.map(
    (x) => x.type_snapshot?.scale
  );

  const typeData = statistics?.column_statistics?.map(
    (x) => x.type_snapshot?.column_type
  );

  const hashData = statistics?.column_statistics?.map((x) => x.column_hash);

  const dataArray: MyData[] = [];
  if (columnNameData && hashData) {
    const maxLength = Math.max(columnNameData.length, hashData.length);

    for (let i = 0; i < maxLength; i++) {
      const newData: MyData = {
        null_percent: Number(renderValue(nullPercentData?.[i])),
        unique_value: Number(renderValue(uniqueCountData?.[i])),
        null_count: Number(renderValue(nullCountData?.[i])),
        detectedDatatypeVar: Number(detectedDatatypeVar?.[i]),
        nameOfCol: columnNameData?.[i],
        minimalValue: renderValue(minimalValueData?.[i]),
        maximumValue: renderValue(maximumValueData?.[i]),
        length: renderValue(lengthData?.[i]),
        scale: renderValue(scaleData?.[i]),
        importedDatatype: String(renderValue(typeData?.[i])),
      };

      dataArray.push(newData);
    }
  }

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
                    <td key={jIndex} className='px-6 py-2 text-left block w-50 border border-gray-300'>{y[x.value as keyof MyData]}</td>
                    )}
                    
                </tr>
              )}
            </tbody>
        </table>
  )
}
