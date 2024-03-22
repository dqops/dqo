import moment from 'moment/moment';
import { useParams } from 'react-router-dom';
import { ConnectionModel, ConnectionModelProviderTypeEnum, TableListModel } from '../api';

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

  let decodedValue = '';
  for (let i = 0; i < url.length; i++) {
    if (url[i] === '%' && i + 2 < url.length) {
      const encodedChar = url.slice(i, i + 3);
      switch (encodedChar) {
        case '%20':
          decodedValue += ' ';
          break;
        case '%2E':
          decodedValue += '.';
          break;
        case '%2F':
          decodedValue += '/';
          break;
        case '%5C':
          decodedValue += '\\';
          break;
        // case '%25':
        //   decodedValue += '%';
        //   break;  
        default:
          decodedValue += encodedChar;
          break;
      }
      i += 2;
    } else {
      decodedValue += url[i];
    }
  }

  return decodedValue;
};

export const urlencodeDecoder = (url: string | undefined) => {
  if (!url) return '';

  let encodedValue = '';
  for (let i = 0; i < url.length; i++) {
    const char = url[i];
    switch (char) {
      // case '%': encodedValue += '%25'; break;
      case ' ':
        encodedValue += '%20';
        break;
      case '.':
        encodedValue += '%2E';
        break;
      case '/':
        encodedValue += '%2F';
        break;
      case '\\':
        encodedValue += '%5C';
        break;
      default:
        encodedValue += char;
    }
  }
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

export function useDecodedParams(): any {
  const parameters: { [key: string]: any } = useParams();
  const decodedParams: { [key: string]: any } = {};

  Object.entries(parameters).forEach(([key, value]) => {
    decodedParams[key] = urlencodeEncoder(String(value));
  });

  return decodedParams;
}
export const filterPathsDuckDbTable = (table: TableListModel) => {
  return {
    ...table,
    file_format: {
      ...table.file_format,
      file_paths: table.file_format?.file_paths?.filter((x) => x)
    }
  };
}
export const filterPropertiesDirectories = (db: ConnectionModel) => {
  const directories = { ...(db?.[db.provider_type as keyof ConnectionModel] as any)?.directories };
  const properties = { ...(db?.[db.provider_type as keyof ConnectionModel] as any)?.properties };
  Object.keys(properties).forEach((key) => {
    if (!properties[key]) {
      delete properties[key];
    }
  });
 
  if (db.provider_type === ConnectionModelProviderTypeEnum.duckdb) {
    Object.keys(directories).forEach((key) => {
      if (!directories[key]) {
        delete directories[key];
      }
    });
    return {
      ...db,
      duckdb: {
        ...db.duckdb,
        directories,
        properties
    }
  }
}
return {
  ...db,
  [db.provider_type as keyof ConnectionModel]: {
    ...(db?.[db.provider_type as keyof ConnectionModel] as any),
    properties
    }
  }
}
