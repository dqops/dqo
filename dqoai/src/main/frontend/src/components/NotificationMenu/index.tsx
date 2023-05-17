import React, { useMemo, useRef, useState } from 'react';

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
import { reduceCounter } from '../../redux/actions/job.actions';
import { useError, IError } from '../../contexts/errrorContext';
import JobItem from './JobItem';
import ErrorItem from './ErrorItem';
import moment from 'moment';

const NotificationMenu = () => {
  const { jobs, isOpen, wasOpen } = useSelector((state: IRootState) => state.job);
  
  const dispatch = useActionDispatch();
  const { errors } = useError();

  const toggleOpen = () => {
    dispatch(toggleMenu(!isOpen));
  };
 
  
  const getNotificationDate = (notification: any) => {
    if (notification.type === 'job') {
      return notification.item.jobId?.createdAt;
    }
    return notification.item.date;
  };
// console.log(isOpen)
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
  const [sizeOfNot, setSizeOfNot] = useState<number>(data.length)
//console.log(reduceCounter(wasOpen ? wasOpen : false).wasOpen)

  const eventHandler =async () =>{

    setSizeOfNot(reduceCounter(wasOpen ? wasOpen : false).wasOpen ? sizeOfNot-5 : sizeOfNot)
  }

return (
    <Popover placement="bottom-end" open={isOpen} handler={toggleOpen}>
      <PopoverHandler>
        <IconButton
          className="!mr-3 !bg-transparent"
          ripple={false}
          variant="text"
        >
          <div className="relative">
            <SvgIcon name="bell" className="w-5 h-5 text-gray-700" />
            <span className="absolute top-0 right-0 transform translate-x-1/2 -translate-y-1/2 rounded-full bg-teal-500 text-white px-1 py-0.5 text-xxs">
              {reduceCounter(wasOpen ? wasOpen : false).wasOpen ? data.length-5 : data.length}
            </span>
          </div>
        </IconButton>
      </PopoverHandler>
      <PopoverContent className="z-50 min-w-120 max-w-120 px-0 ">
        <div className="border-b border-gray-300 text-gray-700 font-semibold pb-2 text-xl flex items-center justify-between px-4">
          <div>Notifications</div>
        </div>
        <div className="overflow-auto max-h-100 py-4 px-4">
          {data.map((notification: any, index) =>
            notification.type === 'error' ? (
              <ErrorItem error={notification.item} key={index} />
            ) : (
              <JobItem job={notification.item} key={index} counter={index} notifnumber={data.length}/>
            )
          )}
        </div>
      </PopoverContent>
    </Popover>
  );
};

export default NotificationMenu;
