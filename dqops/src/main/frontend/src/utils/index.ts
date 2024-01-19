import moment from "moment/moment";
import { TJobDictionary, TABLE_LEVEL_TABS } from "../shared/constants";
import { CheckTypes } from "../shared/routes";
import { DqoJobChangeModel, DqoJobHistoryEntryModel } from "../api";

export const getDaysString = (value: string | number) => {
  const daysDiff = moment().diff(moment(value), 'day');
  if (daysDiff === 0) return 'Today';
  if (daysDiff === 1) return '1 day ago';

  return `${daysDiff} days ago`;
}

export const wait = (time: number) => new Promise((resolve) => setTimeout(resolve,  time));

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
    return 'TIMESTAMP';
  }
  if (Number(numberForFile) === 5) {
    return 'BOOLEAN';
  }
  if (Number(numberForFile) === 6) {
    return 'STRING';
  }
  if (Number(numberForFile) === 7) {
    return 'Mixed data type';
  }
};

export const transformAllJobs = (jobs: DqoJobHistoryEntryModel[]): TJobDictionary[] => {
  const jobsData = jobs.reverse().map((item) => ({ type: 'job', item }));
  
  const jobList = jobsData
  .filter((z) => z.item.jobId?.parentJobId?.jobId === undefined)
  .map((x) => ({
    errorMessage: x.item.errorMessage,
    jobId: {
      jobId: x.item.jobId?.jobId,
      createdAt: x.item.jobId?.createdAt
    },
    jobType: x.item.jobType,
    parameters: x.item.parameters,
    status: x.item.status,
    statusChangedAt: x.item.statusChangedAt,
    childs: jobsData
      .filter(
        (y) => y.item.jobId?.parentJobId?.jobId === x.item.jobId?.jobId
      )
      .map((y) => y.item)
  }));
  
  return  []
}

export const transformJobsChanges = (jobs: DqoJobChangeModel): TJobDictionary[] => {
  return[]
}  

// case JOB_ACTION.GET_JOBS_SUCCESS: {
//   const job_dictionary_state: Record<string, TJobDictionary> = {};
//     const jobList : TJobList = {};
// action.data.jobs.forEach((item: DqoJobHistoryEntryModel) => {
  
// const jobIdKey = String(item.jobId?.parentJobId?.jobId || item.jobId?.jobId || '');

// if (item.jobId?.parentJobId?.jobId === undefined && !job_dictionary_state[jobIdKey]) {
//   job_dictionary_state[jobIdKey] = { ...item, childs: [] };

// } else {

//   if (!job_dictionary_state[jobIdKey]) {
//     job_dictionary_state[jobIdKey] = { childs: [] };
//   }

//   const currentState = { ...job_dictionary_state[jobIdKey] };

//   job_dictionary_state[jobIdKey] = {
//     ...currentState,
//     childs: [...currentState.childs, item],
//   };

//   if (!jobList[jobIdKey]) {
//     jobList[jobIdKey] = [];
//   }

//   jobList[jobIdKey].push(String(item?.jobId?.jobId) || '');
// }});

//   return {
//     ...state,
//     loading: false,
//     job_dictionary_state,
//     jobList: action.data.jobs,
//     lastSequenceNumber: action.data.lastSequenceNumber,
//     error: null
//   };
// }
