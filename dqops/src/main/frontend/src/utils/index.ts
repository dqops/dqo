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
