import React from 'react'
import { Table } from '../../Table'
import { useParams } from 'react-router-dom'
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
    columnHash: number;
    isColumnSelected: boolean;
}
  


export default function TablePreview({statistics} : tablePreviewProps) {

    const mergeSort=(arr: number[]): number[] => {
        if (arr.length <= 1) {
          return arr;
        }
      
        const middle = Math.floor(arr.length / 2);
        const left = arr.slice(0, middle);
        const right = arr.slice(middle);
      
        return merge(mergeSort(left), mergeSort(right));
      }
      
      const merge=(left: number[], right: number[]): number[] => {
       const result: number[] = [];
        let leftIndex = 0;
        let rightIndex = 0;
      
        while (leftIndex < left.length && rightIndex < right.length) {
          if (left[leftIndex] < right[rightIndex]) {
            result.push(left[leftIndex]);
            leftIndex++;
          } else {
            result.push(right[rightIndex]);
            rightIndex++;
          }
        }
      
        return result.concat(left.slice(leftIndex), right.slice(rightIndex));
      }
      
   const {connection, schema, table } : {connection : string, schema : string, table : string } = useParams() 

    // const distinguish10TopValues = () => {
    //     const array = statistics.column_statistics?.map((x) => x.statistics)
    //     const filteredArray = [] 
    //     for(let i =0; i< firstColumnObjects.length; i++){
    //         console.log(array)
    //     }


    // }
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
        columnHash: Number(hashData?.[i]),
        isColumnSelected: false
      };

      dataArray.push(newData);
    }
  }
  console.log(dataArray)
    const firstColumnObjects = [
        { value: "string_datatype_detect", label: "detected type" },
        { value: "min_value", label: "min" },
        { value: "max_value", label: "max" },
        { value: "nulls_count", label: "null count" },
        { value: "nulls_percent", label: "null percent" },
        { value: "distinct_count", label: "distinct count" },
      ];

  return (
    // <div className='p-4 overflow-hidden'>
    //     123
    //     <Table 
    //     columns={[{label: "Column name", value: "Column name"},
    //      ...statistics.column_statistics?.map((x) => ({label: x.column_name, value: x.column_name ?? "", classname: "border boder-gray-300" })) ?? []]} 
    //     data={dataArray} 
    //     className='p-6 border border-gray-300 w-full overflow-x-auto'/>
    // </div>
        <table className='w-screen mt-5 p-4'>
            <thead className='border-b border-b-gray-400 relative flex bg-green-500'>
                <th className="px-6 py-4 text-left border border-gray-300 block w-50">Column name</th>
                {statistics.column_statistics?.map((x, index) => 
                 <th key={index} className="px-6 py-4 text-left border border-gray-300 block w-50">{x.column_name}</th>)}
            </thead>
            <tbody className='bg-red-500'>
                {firstColumnObjects.map((x, index) =>
                <tr key= {index} className='flex'>
                    <td className='px-6 py-2 text-left bg-yellow-300 block w-50'>{x.label}</td>
                    {dataArray.map((y, jIndex) => 
                    <td key={jIndex} className='px-6 py-2 text-left block w-50'>{y.maximumValue}</td>
                    )}
                    
                </tr>
              )}
            </tbody>
        </table>
  )
}
