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
import { toggleMenu } from '../../redux/actions/job.actions';

import { useError, IError } from '../../contexts/errrorContext';
import JobItem from './JobItem';
import ErrorItem from './ErrorItem';
import moment from 'moment';
import { JobApiClient } from '../../services/apiClient';
import Switch from '../Switch';

const NotificationMenu = () => {
  const { jobs, isOpen } = useSelector((state: IRootState) => state.job);

  const dispatch = useActionDispatch();
  const { errors } = useError();
  const [cronBoolean, setCronBoolean] = useState<boolean>();
  const toggleOpen = () => {
    dispatch(toggleMenu(!isOpen));
  };

  const getNotificationDate = (notification: any) => {
    if (notification.type === 'job') {
      return notification.item.jobId?.createdAt;
    }
    return notification.item.date;
  };

  useEffect(() => {
    getData();
  }, []);

  const data = useMemo(() => {
    const jobsData = jobs?.jobs
      ? jobs?.jobs
          .sort((a, b) => {
            return (b.jobId?.jobId || 0) - (a.jobId?.jobId || 0);
          })
          .map((item) => ({ type: 'job', item }))
      : [];

    const errorData = errors.map((item: IError) => ({ type: 'error', item }));

    const newData = jobsData.concat(errorData);

    newData.sort((a, b) => {
      const date1 = getNotificationDate(a);
      const date2 = getNotificationDate(b);

      return moment(date1).isBefore(moment(date2)) ? 1 : -1;
    });

    return newData;
  }, [jobs, errors]);

  const [sizeOfNot, setSizeOfNot] = useState<number>(data.length);

  const eventHandler = () => {
    setSizeOfNot(data.length);
  };

  const getData = async () => {
    const res = await JobApiClient.isCronSchedulerRunning();
    setCronBoolean(res.data);
  };

  const startCroner = async () => {
    await JobApiClient.startCronScheduler();
    setCronBoolean(true);
  };
  const stopCroner = async () => {
    await JobApiClient.stopCronScheduler();
    setCronBoolean(false);
  };

  const changeStatus = () => {
    if (cronBoolean === true) {
      stopCroner();
    }
    if (cronBoolean === false) {
      startCroner();
    }
  };

  return (
    <Popover placement="bottom-end" open={isOpen} handler={toggleOpen}>
      <PopoverHandler>
        <IconButton
          className="!mr-3 !bg-transparent"
          ripple={false}
          variant="text"
        >
          <div className="relative" onClick={() => eventHandler()}>
            <SvgIcon name="bell" className="w-5 h-5 text-gray-700" />
            <span
              className={
                sizeOfNot !== data.length && data.length !== 0
                  ? 'absolute top-0 right-0 transform translate-x-1/2 -translate-y-1/2 rounded-full bg-teal-500 text-white px-1 py-0.5 text-xxs'
                  : ''
              }
            >
              {sizeOfNot !== data.length && data.length !== 0 ? 'New' : ''}
            </span>
          </div>
        </IconButton>
      </PopoverHandler>
      <PopoverContent className="z-50 min-w-120 max-w-120 px-0 ">
        <div className="border-b border-gray-300 text-gray-700 font-semibold pb-2 text-xl flex flex-col gap-y-2 px-4">
          <div>Notifications ({data.length})</div>
          <div className="flex items-center gap-x-3 text-sm">
            <div className="whitespace-no-wrap">Jobs scheduler </div>
            <div>
              <Switch
                checked={cronBoolean ? cronBoolean : false}
                onChange={() => changeStatus()}
              />
            </div>
            {cronBoolean === false && (
              <div className="font-light text-xs text-red-500 text-center">
                (Warning: scheduled jobs will not be executed)
              </div>
            )}
          </div>
        </div>
        <div className="overflow-auto max-h-100 py-4 px-4">
          {data.map((notification: any, index) =>
            notification.type === 'error' ? (
              <ErrorItem error={notification.item} key={index} />
            ) : (
              <JobItem
                job={notification.item}
                key={index}
                notifnumber={data.length}
              />
            )
          )}
        </div>
      </PopoverContent>
    </Popover>
  );
};

export default NotificationMenu;
