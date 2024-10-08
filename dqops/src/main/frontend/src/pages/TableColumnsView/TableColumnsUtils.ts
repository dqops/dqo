import {
  DimensionCurrentDataQualityStatusModel,
  TableColumnsStatisticsModel
} from '../../api';
import { MyData } from './TableColumnsConstans';

export const renderValue = (value: any) => {
  if (typeof value === 'boolean') {
    return value ? 'Yes' : 'No';
  }
  if (typeof value === 'object') {
    return value.toString();
  }
  return value;
};

export const cutString = (text: string) => {
  if (text.length > 19 && isNaN(Number(text))) {
    return text.slice(0, 19) + '...';
  } else {
    return text;
  }
};

export const calculate_color = (
  uniqueCount: number,
  maxUniqueCount: number
) => {
  if (uniqueCount === 0) {
    return 'rgba(255, 255, 255, 1)';
  }

  if (uniqueCount === maxUniqueCount) {
    return 'rgba(2, 154, 128, 0.1)';
  }

  if (uniqueCount === 1) {
    return 'rgba(2, 154, 128, 0.8)';
  }

  const logarithm = Math.log2(uniqueCount);
  const alpha = (1 - (logarithm / Math.log2(maxUniqueCount)) * 0.9) / 1.3;
  const color = `rgba(2, 154, 128, ${alpha})`;

  return color;
};

export const max_unique_value = (
  statistics: TableColumnsStatisticsModel | undefined
) => {
  const arr: number[] = [];
  statistics?.column_statistics?.map((x) => {
    x.statistics?.map((y) => {
      if (y.collector == 'distinct_count') {
        arr.push(Number(y.result));
      }
    });
  });
  let max = 0;

  for (let i = 0; i < arr.length; i++) {
    if (Number(arr.at(i)) > max) {
      max = Number(arr.at(i));
    }
  }

  return max;
};
export const getSortedArrayAlphabetictly = (
  typ: keyof MyData,
  dataArray: MyData[],
  sortDirection: 'asc' | 'desc'
) => {
  const sortedArrayAlphabetically = [...dataArray];
  sortedArrayAlphabetically.sort((a, b) => {
    const nullsCountA = String(a[typ]);
    const nullsCountB = String(b[typ]);

    if (nullsCountA && nullsCountB) {
      return sortDirection === 'asc'
        ? nullsCountA.localeCompare(nullsCountB)
        : nullsCountB.localeCompare(nullsCountA);
    } else if (nullsCountA) {
      return sortDirection === 'asc' ? -1 : 1;
    } else if (nullsCountB) {
      return sortDirection === 'asc' ? 1 : -1;
    } else {
      return 0;
    }
  });
  return sortedArrayAlphabetically;
};

export const getSortedArrayByMinimalValue = (
  typ: keyof MyData,
  dataArray: MyData[],
  sortDirection: 'asc' | 'desc'
) => {
  const sortedArray = [...dataArray];

  const getType = (value: any): string => {
    if (value === undefined || value === null) return 'null';
    if (typeof value === 'boolean' || value === 'Yes' || value === 'No')
      return 'boolean';
    if (typeof value === 'string' && isNaN(Number(value))) return 'string';
    if (!isNaN(Number(value))) return 'number';
    return 'string';
  };

  const sortFunction = (a: MyData, b: MyData): number => {
    const typeA = getType(a[typ]);
    const typeB = getType(b[typ]);
    const valueA = a[typ];
    const valueB = b[typ];

    if (typeA !== typeB) {
      const order = ['null', 'boolean', 'string', 'number'];
      return order.indexOf(typeA) - order.indexOf(typeB);
    }

    if (typeA === 'boolean' || typeA === 'string') {
      return sortDirection === 'asc'
        ? String(valueA).localeCompare(String(valueB))
        : String(valueB).localeCompare(String(valueA));
    }

    if (typeA === 'number') {
      return sortDirection === 'asc'
        ? parseFloat(String(valueA)) - parseFloat(String(valueB))
        : parseFloat(String(valueB)) - parseFloat(String(valueA));
    }

    return 0;
  };

  sortedArray.sort(sortFunction);

  return sortedArray;
};

export const getSortedData = <T extends { [key: string]: any }>(
  key: keyof T,
  dataArray: MyData[],
  sortDirection: 'asc' | 'desc'
) => {
  const sortedArray = [...dataArray];
  sortedArray.sort((a, b) => {
    const valueA = String(a[key as keyof typeof a]);
    const valueB = String(b[key as keyof typeof b]);

    if (valueA && valueB) {
      return sortDirection === 'asc'
        ? parseFloat(valueA) - parseFloat(valueB)
        : parseFloat(valueB) - parseFloat(valueA);
    } else if (valueA) {
      return sortDirection === 'asc' ? -1 : 1;
    } else if (valueB) {
      return sortDirection === 'asc' ? 1 : -1;
    } else {
      return 0;
    }
  });
  return sortedArray;
};
function countDimensions(dimensions: any[] | undefined): number {
  return dimensions ? dimensions.length : 0;
}

const getSeverityLevel = (severity: any): number => {
  switch (severity) {
    case 'fatal':
      return 3;
    case 'error':
      return 2;
    case 'warning':
      return 1;
    case 'valid':
      return 0;
  }
  return 4;
};

function getHighestSeverity(
  dimensions:
    | (
        | {
            [key: string]: DimensionCurrentDataQualityStatusModel;
          }
        | undefined
      )[]
    | undefined
    | undefined
): number {
  if (!dimensions || dimensions.length === 0) return -1;
  let highestSeverity = -1;
  let counter = 0;
  Object.values(dimensions).forEach((dimension) => {
    if (getSeverityLevel(dimension?.current_severity) === highestSeverity) {
      counter++;
    }
    if (getSeverityLevel(dimension?.current_severity) > highestSeverity) {
      highestSeverity = getSeverityLevel(dimension?.current_severity);
      counter = 0;
    }
  });
  return highestSeverity * 100 + counter;
}
function sortByDimenstion(
  dataArray: MyData[],
  direction: 'asc' | 'desc'
): MyData[] {
  const array = dataArray.sort((a, b) => {
    const aDimensionCount = countDimensions(a.dimentions);
    const bDimensionCount = countDimensions(b.dimentions);

    if (aDimensionCount !== bDimensionCount) {
      return direction === 'desc'
        ? aDimensionCount - bDimensionCount
        : bDimensionCount - aDimensionCount;
    }

    const aHighestSeverity = getHighestSeverity(a.dimentions);
    const bHighestSeverity = getHighestSeverity(b.dimentions);

    return direction === 'desc'
      ? aHighestSeverity - bHighestSeverity
      : bHighestSeverity - aHighestSeverity;
  });
  return array;
}

export const handleSorting = (
  param: string,
  dataArray: MyData[],
  sortDirection: 'asc' | 'desc',
  setSortDirection: React.Dispatch<React.SetStateAction<'asc' | 'desc'>>,
  setSortedArray: any
) => {
  switch (param) {
    case 'Column name':
      setSortedArray(
        getSortedArrayAlphabetictly('nameOfCol', dataArray, sortDirection)
      );
      break;
    case 'Detected data type':
      setSortedArray(
        getSortedData<MyData>('detectedDatatypeVar', dataArray, sortDirection)
      );
      break;
    case 'Imported data type':
      setSortedArray(
        getSortedArrayAlphabetictly(
          'importedDatatype',
          dataArray,
          sortDirection
        )
      );
      break;
    case 'Labels':
      setSortedArray(
        getSortedArrayAlphabetictly('labels', dataArray, sortDirection)
      );
      break;
    case 'Length':
      setSortedArray(getSortedData<MyData>('length', dataArray, sortDirection));
      break;
    case 'Scale':
      setSortedArray(getSortedData<MyData>('scale', dataArray, sortDirection));
      break;
    case 'Nulls count':
      setSortedArray(
        getSortedData<MyData>('null_count', dataArray, sortDirection)
      );
      break;
    case 'Min value':
      setSortedArray(
        getSortedArrayByMinimalValue('minimalValue', dataArray, sortDirection)
      );
      break;
    case 'Max value':
      setSortedArray(
        getSortedArrayByMinimalValue('maximumValue', dataArray, sortDirection)
      );
      break;
    case 'Nulls percent':
      setSortedArray(
        getSortedData<MyData>('null_percent', dataArray, sortDirection)
      );
      break;
    case 'Unique Values':
      setSortedArray(
        getSortedData<MyData>('unique_value', dataArray, sortDirection)
      );
      break;
    case 'Dimensions':
      setSortedArray(sortByDimenstion(dataArray, sortDirection));
  }
  setSortDirection(sortDirection === 'asc' ? 'desc' : 'asc');
};
