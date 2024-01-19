import React, { useEffect, useMemo, useState } from 'react';
import {
  Popover,
  PopoverHandler,
  PopoverContent,
  IconButton
} from '@material-tailwind/react';
import SvgIcon from '../SvgIcon';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setCronScheduler, toggleMenu } from '../../redux/actions/job.actions';
import { IError, useError} from '../../contexts/errrorContext';
import JobItem from './JobItem';
import ErrorItem from './ErrorItem';
import { JobApiClient } from '../../services/apiClient';
import Switch from '../Switch';
import clsx from 'clsx';
import { DqoJobChangeModel, DqoJobHistoryEntryModel, DqoJobQueueInitialSnapshotModel } from '../../api';

const NotificationMenu = () => {
  const { job_dictionary_state, isOpen, isCronScheduled, userProfile } = useSelector(
    (state: IRootState) => state.job || {}
    );
  const [showNewIcon, setShowNewIcon] = useState(false);

  const dispatch = useActionDispatch();
  const { errors } = useError();

  const toggleOpen = () => {
    dispatch(toggleMenu(!isOpen));
  };
  const scheduleCron = (bool: boolean) => {
    dispatch(setCronScheduler(bool));
  };

  useEffect(() => {
    const getIsCronSchedulerRunning = async () => {
      const res = await JobApiClient.isCronSchedulerRunning();
      scheduleCron(res.data);
    };
      getIsCronSchedulerRunning();
    }, []);

  const [jobsData, errorsData, jobs] = useMemo(() => {
    const jobsData = Object.values(job_dictionary_state)
      .reverse()
      .map((item) => ({ type: 'job', item }));
  
    const errorData = errors.map((item : any) => ({ type: 'error', item }));

    const newJobArray = jobsData
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

    const errorsData : IError[] = errorData.map((x: any) => ({
      message: x.item.message,
      name: x.item.name,
      date: x.item.date
    }));
  
    const updatedArray = newJobArray.map((x) => {
      if (x.jobType === undefined) {
        return {
          ...x,
          jobType: (
            Object.values(job_dictionary_state).find(
              (y) => y.jobId?.jobId === x.jobId?.jobId
            ) as any
          )?.updatedModel?.jobType
        };
      }
      return x;
    });
  
    const updatedChildArray = updatedArray.map((x) => {
      if (x.childs) {
        const updatedChilds = x.childs.map((z) => {
          if (z.jobType === undefined) {
            return {
              ...z,
              jobType: (
                Object.values(job_dictionary_state).find(
                  (y) => y.jobId?.jobId === z.jobId?.jobId
                ) as any
              )?.updatedModel?.jobType
            };
          }
          return z;
        });
        return { ...x, childs: updatedChilds };
      }
  
      return x;
    });
    return [jobsData, errorsData, updatedChildArray] as const;
  }, [isOpen]);

  const startCroner = async () => {
    await JobApiClient.startCronScheduler();
  };
  const stopCroner = async () => {
    await JobApiClient.stopCronScheduler();
  };

  const changeStatus = () => {
    if (isCronScheduled === true) {
      stopCroner();
      scheduleCron(false);
    }
    if (isCronScheduled === false) {
      startCroner();
      scheduleCron(true);
    }
  };

  useEffect(() => {
    setShowNewIcon(true)
  }, [job_dictionary_state])

  return (
    <Popover placement="bottom-end" open={isOpen} handler={toggleOpen}>
      <PopoverHandler style={{ position: 'relative' }}>
        <IconButton
          className="!mr-3 !bg-transparent"
          ripple={false}
          variant="text"
        >
          <div className="relative" onClick={() => setShowNewIcon(false)}> 
            <SvgIcon name="bell" className="w-5 h-5 text-gray-700" />
            <span
              className={
                showNewIcon && jobs.length !== 0
                  ? 'absolute top-0 right-0 transform translate-x-1/2 -translate-y-1/2 rounded-full bg-teal-500 text-white px-1 py-0.5 text-xxs'
                  : ''
              }
            >
              {showNewIcon && jobsData.length !== 0 ? 'New' : ''}
            </span>
          </div>
        </IconButton>
      </PopoverHandler>
      <PopoverContent
        className="min-w-120 max-w-120 px-0 relative z-50"
        style={{ position: 'relative', zIndex: '100000' }}
      >
        <div className="border-b border-gray-300 text-gray-700 font-semibold pb-2 text-xl flex flex-col gap-y-2 px-4 relative">
          <div>
            Notifications ({Object.keys(job_dictionary_state).length})
          </div>
          <div className="flex items-center gap-x-3 text-sm">
            <div className="whitespace-no-wrap">Jobs scheduler </div>
            <div className={clsx(userProfile.can_manage_scheduler !== true ? "pointer-events-none cursor-not-allowed" : "")}>
              <Switch
                checked={isCronScheduled ? isCronScheduled : false}
                onChange={() => changeStatus()}
              />
            </div>
            {isCronScheduled === false && (
              <div className="font-light text-xs text-red-500 text-center">
                (Warning: scheduled jobs will not be executed)
              </div>
            )}
          </div>
        </div>
        <div className="overflow-x-hidden max-h-100 py-4 px-4 relative">
          {errorsData.map((error, index) => <ErrorItem error={error} key={index}/>)}
          {jobs.map((notification: any, index) =>
              <JobItem
                job={notification}
                key={index}
                notifnumber={jobsData.length}
                canUserCancelJobs={userProfile.can_cancel_jobs}
              />
            // )
          )}
        </div>
      </PopoverContent>
    </Popover>
  );
};

export default NotificationMenu;
