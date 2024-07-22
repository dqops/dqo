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
  const [sortedBy, setSortedBy] = useState<string>('');
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
    window.addEventListener('resize', getHeaderitemsBasedOnWidth);
    return () => {
      window.removeEventListener('resize', getHeaderitemsBasedOnWidth);
    };
  }, []);

  const handleSortClick = (column: string) => {
    setSortedBy(column);
    handleSorting(
      column as keyof MyData,
      dataArray,
      sortDirection,
      setSortDirection,
      setSortedArray
    );
  };

  const renderChevrons = (x: string) => {
    return (
      <div>
        {' '}
        {sortedBy === x ? (
          sortDirection === 'asc' ? (
            <>
              <SvgIcon
                name="chevron-up"
                className="w-2 h-2"
                onClick={() => {
                  handleSortClick(x);
                }}
              />
              <div className="w-2 h-2"></div>
            </>
          ) : (
            <>
              <div className="w-2 h-2"></div>
              <SvgIcon
                name="chevron-down"
                className="w-2 h-2"
                onClick={() => {
                  handleSortClick(x);
                }}
              />
            </>
          )
        ) : (
          <>
            <SvgIcon
              name="chevron-up"
              className="w-2 h-2"
              onClick={() => {
                handleSortClick(x);
              }}
            />
            <SvgIcon
              name="chevron-down"
              className="w-2 h-2"
              onClick={() => {
                handleSortClick(x);
              }}
            />
          </>
        )}
      </div>
    );
  };

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
            >
              <div>{x}</div>
              {renderChevrons(x)}
            </div>
          </th>
        ))}
        <th className="border-b border-gray-100 text-right px-10 py-2 ">
          <div className="flex justify-center cursor-pointer">
            <div>Nulls percent</div>
            {renderChevrons('Nulls percent')}
          </div>
        </th>
        <th className="border-b border-gray-100 text-right px-4 py-2">
          <div className="flex justify-end cursor-pointer">
            <div>Distinct count</div>
            {renderChevrons('Unique Values')}
          </div>
        </th>
        <th className="border-b border-gray-100 text-right px-7.5 py-2">
          Action
        </th>
      </tr>
    </thead>
  );
}
