import React, { useState } from 'react';
import SvgIcon from '../../components/SvgIcon';
import { MyData, labels } from './TableColumnsConstans';
import { handleSorting } from './TableColumnsUtils';

interface ITableColumnsHeaderProps {
  dataArray: MyData[];
  setSortedArray: any;
  updateData: any;
}

export default function TableColumnsHeader({
  dataArray,
  setSortedArray,
  updateData
}: ITableColumnsHeaderProps) {
  const [sortDirection, setSortDirection] = useState<'asc' | 'desc'>('asc');

  return (
    <thead>
      <tr>
        <th className="border-b border-gray-100 " style={{ width: '6px' }}></th>
        {labels.map((x, index) => (
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
                <SvgIcon name="chevron-up" className="w-3 h-3" />
                <SvgIcon name="chevron-down" className="w-3 h-3" />
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
            <div onClick={() => updateData('updated data')}>Nulls percent</div>
            <div>
              <SvgIcon name="chevron-up" className="w-3 h-3" />
              <SvgIcon name="chevron-down" className="w-3 h-3" />
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
              <SvgIcon name="chevron-up" className="w-3 h-3" />
              <SvgIcon name="chevron-down" className="w-3 h-3" />
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
