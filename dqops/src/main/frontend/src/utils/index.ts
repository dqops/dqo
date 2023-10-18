import moment from "moment/moment";
import { TABLE_LEVEL_TABS } from "../shared/constants";
import { CheckTypes } from "../shared/routes";

export const getDaysString = (value: string | number) => {
  const daysDiff = moment().diff(moment(value), 'day');
  if (daysDiff === 0) return 'Today';
  if (daysDiff === 1) return '1 day ago';

  return `${daysDiff} days ago`;
}

export const wait = (time: number) => new Promise((resolve) => setTimeout(resolve,  time));

export const checkIfTabCouldExist = (checkType: CheckTypes, url: string) : boolean => {
  const activeTab = url.split("/")[url.split('/').length -1]
  if (url.startsWith("/" + checkType) && TABLE_LEVEL_TABS[checkType].find((x) => x.value === activeTab)) {
   return true;
  }
  return false;
}
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

  return date.toLocaleString('en-US', options);
};

export const urlencodeDecoder = (url: string) =>{
  return url.replace(/\s/g, '%20') ?? url
}

export const urlencodeEncoder = (url: string | undefined) =>{
  return url && url.replace(/%20/g, ' ')
}
