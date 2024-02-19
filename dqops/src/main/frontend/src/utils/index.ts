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

export const urlencodeDecoder = (url: string) => {
  return url.replace(/\s/g, '%20') ?? url;
};

export const urlencodeEncoder = (url: string | undefined) => {
  return url && url.replace(/%20/g, ' ');
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
