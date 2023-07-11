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

import { useError, IError } from '../../contexts/errrorContext';
import JobItem from './JobItem';
import ErrorItem from './ErrorItem';
import moment from 'moment';
import { JobApiClient } from '../../services/apiClient';
import Switch from '../Switch';
import { DqoJobHistoryEntryModel } from '../../api';

interface jobInterface extends Omit<DqoJobHistoryEntryModel, 'childs'> {
  childs?: DqoJobHistoryEntryModel[];
}

const NotificationMenu = () => {
  const { job_dictionary_state, isOpen, isCronScheduled } = useSelector(
    (state: IRootState) => state.job || {}
  );

  const [data, setData] = useState<
    Array<{ type: string; item: DqoJobHistoryEntryModel }>
  >([]);
  const dispatch = useActionDispatch();
  const { errors } = useError();

  const toggleOpen = () => {
    dispatch(toggleMenu(!isOpen));
  };
  const scheduleCron = (bool: boolean) => {
    dispatch(setCronScheduler(bool));
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

  useEffect(() => {
    const jobsData = Object.values(job_dictionary_state)
      .sort((a, b) => {
        return (b.jobId?.jobId || 0) - (a.jobId?.jobId || 0);
      })
      .map((item) => ({ type: 'job', item }));

    const errorData = errors.map((item: IError) => ({ type: 'error', item }));

    const newData = jobsData.concat(errorData);

    newData.sort((a, b) => {
      const date1 = getNotificationDate(a);
      const date2 = getNotificationDate(b);

      return moment(date1).isBefore(moment(date2)) ? 1 : -1;
    });

    setData(newData);
  }, [job_dictionary_state, errors]);

  const [sizeOfNot, setSizeOfNot] = useState<number>(data.length);

  const eventHandler = () => {
    setSizeOfNot(data.length);
  };

  const getData = async () => {
    const res = await JobApiClient.isCronSchedulerRunning();

    scheduleCron(res.data);
  };

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

  const setNewJobArray = (): jobInterface[] => {
    const newArray: jobInterface[] = data
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
        childs: data
          .filter(
            (y) => y.item.jobId?.parentJobId?.jobId === x.item.jobId?.jobId
          )
          .map((y) => y.item)
      }));

    const updatedArray: jobInterface[] = newArray.map((x) => {
      if (x.jobType === undefined) {
        return {
          ...x,
          jobType: (
            Object.values(job_dictionary_state).find(
              (y) => y.jobId?.jobId === x.jobId?.jobId
            ) as any
          ).updatedModel?.jobType
        };
      }
      return x;
    });

    return updatedArray;
  };

  return (
    <Popover placement="bottom-end" open={isOpen} handler={toggleOpen}>
      <PopoverHandler style={{ position: 'relative' }}>
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
      <PopoverContent
        className="z-30 min-w-120 max-w-120 px-0 relative"
        style={{ position: 'relative' }}
      >
        <div className="border-b border-gray-300 text-gray-700 font-semibold pb-2 text-xl flex flex-col gap-y-2 px-4 relative">
          <div onClick={() => setNewJobArray()}>
            Notifications ({data.length})
          </div>
          <div className="flex items-center gap-x-3 text-sm">
            <div className="whitespace-no-wrap">Jobs scheduler </div>
            <div>
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
          {setNewJobArray().map((notification: any, index) =>
            notification.type === 'error' ? (
              <ErrorItem error={notification} key={index} />
            ) : (
              <JobItem
                job={notification}
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
