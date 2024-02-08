import { TableColumnsStatisticsModel } from '../../api';
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
  const BoolArray = [];
  const StringArray = [];
  const NumberArray = [];

  for (let i = 0; i < sortedArray.length; i++) {
    const object = sortedArray[i];
    const minimalValue = object[typ];

    if (
      String(minimalValue)?.charAt(0) === '0' &&
      String(minimalValue)?.length !== 1 &&
      String(minimalValue)?.charAt(1) !== '.'
    ) {
      StringArray.push(object);
    } else if (minimalValue === 'Yes' || minimalValue === 'No') {
      BoolArray.push(object);
    } else if (minimalValue === undefined) {
      StringArray.push(object);
    } else if (
      typeof minimalValue === 'string' &&
      isNaN(Number(minimalValue))
    ) {
      StringArray.push(object);
    } else if (
      typeof minimalValue === 'string' &&
      !isNaN(Number(minimalValue))
    ) {
      NumberArray.push(object);
    }
  }
  BoolArray.sort((a, b) => {
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

  StringArray.sort((a, b) => {
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

  NumberArray.sort((a, b) => {
    const nullsPercentA = String(a[typ]);
    const nullsPercentB = String(b[typ]);

    if (nullsPercentA && nullsPercentB) {
      return sortDirection === 'asc'
        ? parseFloat(nullsPercentA) - parseFloat(nullsPercentB)
        : parseFloat(nullsPercentB) - parseFloat(nullsPercentA);
    } else if (nullsPercentA) {
      return sortDirection === 'asc' ? -1 : 1;
    } else if (nullsPercentB) {
      return sortDirection === 'asc' ? 1 : -1;
    } else {
      return 0;
    }
  });

  const sortedResult = [...BoolArray, ...StringArray, ...NumberArray];
  return sortedResult.length > 0 ? sortedResult : dataArray;
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
  }
  setSortDirection(sortDirection === 'asc' ? 'desc' : 'asc');
};
