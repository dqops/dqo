import React, { useEffect, useState } from 'react';
import SvgIcon from '../../components/SvgIcon';
import { MyData, labels } from './TableColumnsConstans';
import { handleSorting } from './TableColumnsUtils';

interface ITableColumnsHeaderProps {
  dataArray: MyData[];
  setSortedArray: any;
}

export default function TableColumnsHeader({
  dataArray,
  setSortedArray
}: ITableColumnsHeaderProps) {
  const [sortDirection, setSortDirection] = useState<'asc' | 'desc'>('asc');
  const [headerItems, setHeaderItems] = useState<Array<string>>([]);
  const getHeaderitemsBasedOnWidth = () => {
    const width = window.innerWidth;
    const excludedItems: string[] = [];
    if (width < 1700) {
      excludedItems.push(
        'Nulls count',
        'Max value',
        'Min value',
        'Scale',
        'Length'
      );
    } else if (width < 1800) {
      excludedItems.push('Nulls count', 'Max value', 'Min value', 'Scale');
    } else if (width < 1900) {
      excludedItems.push('Nulls count', 'Max value', 'Min value');
    } else if (width < 2000) {
      excludedItems.push('Nulls count', 'Max value');
    } else if (width < 2200) {
      excludedItems.push('Nulls count');
    }
    const arr = labels.filter((item) => !excludedItems.includes(item));
    setHeaderItems(arr);
    return arr;
  };
  useEffect(() => {
    getHeaderitemsBasedOnWidth();
  }, [window.innerWidth]);
  return (
    <thead className="text-sm">
      <tr>
        <th className="border-b border-gray-100 " style={{ width: '6px' }}></th>
        {headerItems.map((x, index) => (
          <th
            className="border-b border-gray-100 text-left px-4 py-2 cursor-pointer"
            key={index}
          >
            <div
              className="flex"
              style={{
                justifyContent:
                  x === 'Min value' || x === 'Nulls count' || x === 'Max value'
                    ? 'flex-end'
                    : 'flex-start'
              }}
              onClick={() => {
                handleSorting(
                  x,
                  dataArray,
                  sortDirection,
                  setSortDirection,
                  setSortedArray
                );
              }}
            >
              <div>{x}</div>
              <div>
                <SvgIcon name="chevron-up" className="w-2 h-2" />
                <SvgIcon name="chevron-down" className="w-2 h-2" />
              </div>
            </div>
          </th>
        ))}

        <th className="border-b border-gray-100 text-right px-10 py-2 ">
          <div
            className="flex justify-center cursor-pointer"
            onClick={() => {
              handleSorting(
                'Nulls percent',
                dataArray,
                sortDirection,
                setSortDirection,
                setSortedArray
              );
            }}
          >
            <div>Nulls percent</div>
            <div>
              <SvgIcon name="chevron-up" className="w-2 h-2" />
              <SvgIcon name="chevron-down" className="w-2 h-2" />
            </div>
          </div>
        </th>
        <th className="border-b border-gray-100 text-right px-4 py-2">
          <div
            className="flex justify-end cursor-pointer"
            onClick={() => {
              handleSorting(
                'Unique Values',
                dataArray,
                sortDirection,
                setSortDirection,
                setSortedArray
              );
            }}
          >
            <div>Distinct count</div>
            <div>
              <SvgIcon name="chevron-up" className="w-2 h-2" />
              <SvgIcon name="chevron-down" className="w-2 h-2" />
            </div>
          </div>
        </th>
        <th className="border-b border-gray-100 text-right px-7.5 py-2">
          Action
        </th>
      </tr>
    </thead>
  );
}
