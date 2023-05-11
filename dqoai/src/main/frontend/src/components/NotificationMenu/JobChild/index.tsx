import {
  DqoJobHistoryEntryModel,
  DqoJobHistoryEntryModelStatusEnum
} from '../../../api';
import React, { useMemo, useState } from 'react';
import SvgIcon from '../../SvgIcon';
import { Accordion, AccordionHeader } from '@material-tailwind/react';
import moment from 'moment';
import { useSelector } from 'react-redux';
import { useError, IError } from '../../../contexts/errrorContext';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { toggleMenu } from '../../../redux/actions/job.actions';
import { IRootState } from '../../../redux/reducers';

const JobChild = ({
  job,
  parentId
}: {
  job: DqoJobHistoryEntryModel;
  parentId: number;
}) => {
  const { jobs, isOpen } = useSelector((state: IRootState) => state.job);
  const dispatch = useActionDispatch();
  const { errors } = useError();

  const getNotificationDate = (notification: any) => {
    if (notification.type === 'job') {
      return notification.item.jobId?.createdAt;
    }
    return notification.item.date;
  };

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

  const [open, setOpen] = useState(false);

  const renderStatus = () => {
    if (job.status === DqoJobHistoryEntryModelStatusEnum.succeeded) {
      return <SvgIcon name="success" className="w-4 h-4 text-primary" />;
    }
    if (job.status === DqoJobHistoryEntryModelStatusEnum.waiting) {
      return <SvgIcon name="waiting" className="w-4 h-4 text-yellow-700" />;
    }
    if (job.status === DqoJobHistoryEntryModelStatusEnum.queued) {
      return <SvgIcon name="queue" className="w-4 h-4 text-gray-700" />;
    }
    if (job.status === DqoJobHistoryEntryModelStatusEnum.failed) {
      return <SvgIcon name="failed" className="w-4 h-4 text-red-700" />;
    }
    if (job.status === DqoJobHistoryEntryModelStatusEnum.running) {
      return <SvgIcon name="running" className="w-4 h-4 text-orange-700" />;
    }
  };

  return (
    <Accordion open={open}>
      {job.jobId?.parentJobId?.jobId === parentId ? (
        <AccordionHeader onClick={() => setOpen(!open)}>
          <div className="flex justify-between items-center text-sm w-full text-gray-700">
            <div className="flex space-x-1 items-center">
              <div>
                {job.jobType}
                {job.jobId?.parentJobId?.jobId}
              </div>
              {renderStatus()}
            </div>
            <div>
              {moment(job?.statusChangedAt).format('YYYY-MM-DD HH:mm:ss')}
            </div>
          </div>
        </AccordionHeader>
      ) : (
        <div></div>
      )}
    </Accordion>
  );
};

export default JobChild;
