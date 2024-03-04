import moment from 'moment/moment';

export const getDaysString = (value: string | number) => {
  const daysDiff = moment().diff(moment(value), 'day');
  if (daysDiff === 0) return 'Today';
  if (daysDiff === 1) return '1 day ago';

  return `${daysDiff} days ago`;
};

export const wait = (time: number) =>
  new Promise((resolve) => setTimeout(resolve, time));

export const getLocalDateInUserTimeZone = (date: Date): string => {
  const userTimeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;
  const options: Intl.DateTimeFormatOptions = {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
    hour12: false,
    timeZone: userTimeZone
  };

  let strDate = String(date);

  if (strDate.includes(' 24:')) {
    strDate = strDate.replace(' 24:', ' 00:');
  }

  return new Date(strDate).toLocaleString('en-US', options);
};

export const urlencodeEncoder = (url: string | undefined) => {
  if (!url) return ''; 
  const decodedValue = url.replace(/%25|%20|%46|%47|%92/g, (match) => {
    switch (match) {
      // case '%25': return '%';
      case '%20': return ' ';
      case '%46': return '.';
      case '%47': return '/';
      case '%92': return '\\';
      default: return match;
    }
  });

  return decodedValue;
};

export const urlencodeDecoder = (url: string | undefined) => {
  if (!url) return ''; 
  console.log(url)
  const encodedValue = url.replace(/[% ./\\]/g, (match) => {
    switch (match) {
      // case '%': return '%25';
      case ' ': return '%20';
      case '.': return '%46';
      case '/': return '%47';
      case '\\': return '%92';
      default: return match;
    }
  });
console.log(encodedValue)
  return encodedValue;
};

export const getDetectedDatatype = (numberForFile: any) => {
  if (Number(numberForFile) === 1) {
    return 'INTEGER';
  }
  if (Number(numberForFile) === 2) {
    return 'FLOAT';
  }
  if (Number(numberForFile) === 3) {
    return 'DATETIME';
  }
  if (Number(numberForFile) === 4) {
    return 'DATETIME';
  }
  if (Number(numberForFile) === 6) {
    return 'BOOLEAN';
  }
  if (Number(numberForFile) === 7) {
    return 'STRING';
  }
  if (Number(numberForFile) === 8) {
    return 'Mixed data type';
  }
};

export const sortPatterns = <T>(
  patterns: T[],
  key: keyof T,
  order: 'asc' | 'desc'
) => {
  const copiedPatterns = [...patterns];

  copiedPatterns.sort((a, b) => {
    const valueA = a[key];
    const valueB = b[key];

    if (valueA === null && valueB === null) {
      return 0;
    } else if (valueA === null) {
      return order === 'asc' ? -1 : 1;
    } else if (valueB === null) {
      return order === 'asc' ? 1 : -1;
    }

    if (valueA === undefined && valueB === undefined) {
      return 0;
    } else if (valueA === undefined) {
      return order === 'asc' ? -1 : 1;
    } else if (valueB === undefined) {
      return order === 'asc' ? 1 : -1;
    }

    const comparison = order === 'asc' ? 1 : -1;

    if (valueA < valueB) {
      return -1 * comparison;
    } else if (valueA > valueB) {
      return 1 * comparison;
    } else {
      return 0;
    }
  });
  return copiedPatterns;
};
export function sortByKey(key: string) {
  return function (a: any, b: any): number {
    const aProp = key.split('.').reduce((obj, prop) => obj && obj[prop], a);
    const bProp = key.split('.').reduce((obj, prop) => obj && obj[prop], b);

    if (typeof aProp === 'string' && typeof bProp === 'string') {
      return aProp.localeCompare(bProp);
    } else if (typeof aProp === 'number' && typeof bProp === 'number') {
      return aProp - bProp;
    } else {
      return 0;
    }
  };
}
