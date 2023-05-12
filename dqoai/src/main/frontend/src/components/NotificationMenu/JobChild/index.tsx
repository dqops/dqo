import {
  DqoJobHistoryEntryModel,
  DqoJobHistoryEntryModelStatusEnum
} from '../../../api';
import React, { useMemo, useState } from 'react';
import SvgIcon from '../../SvgIcon';
import {
  Accordion,
  AccordionBody,
  AccordionHeader
} from '@material-tailwind/react';
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

  const renderValue = (value: any) => {
    if (typeof value === 'boolean') {
      return value ? 'Yes' : 'No';
    }
    if (typeof value === 'object') {
      return value.toString();
    }
    return value;
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
  console.log(job.status);
  return (
    <Accordion open={open}>
      {job.jobId?.parentJobId?.jobId === parentId ? (
        <AccordionHeader onClick={() => setOpen(!open)}>
          <div className="flex flex-wrap justify-between items-center text-sm w-full text-gray-700">
            <div className="flex flex-wrap space-x-1 items-center">
              <div>
                {job.jobType}
                {/* {job.jobId?.parentJobId?.jobId} */}
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
      <AccordionBody>
        <table className="text-gray-700 w-full">
          <tbody>
            <tr className="flex justify-between">
              <td>Status</td>
              <td>{job?.status}</td>
            </tr>
            <tr className="flex justify-between">
              <td>Last Changed</td>
              <td>
                {moment(job?.statusChangedAt).format('YYYY-MM-DD HH:mm:ss')}
              </td>
            </tr>

            {job?.errorMessage && (
              <>
                <tr>
                  <td className="px-2 capitalize">Error Message</td>
                  <td className="px-2 max-w-76">{job?.errorMessage}</td>
                </tr>
              </>
            )}
            {job?.parameters?.runChecksParameters?.checkSearchFilters &&
              Object.entries(
                job?.parameters?.runChecksParameters?.checkSearchFilters
              ).map(([key, value], index) => (
                <tr key={index} className="flex justify-between">
                  <td>{key}</td>
                  <td>{renderValue(value)}</td>
                </tr>
              ))}
            {job?.parameters?.importSchemaParameters && (
              <>
                <tr className="flex justify-between">
                  <td>Connection Name</td>
                  <td>
                    {job?.parameters?.importSchemaParameters?.connectionName}
                  </td>
                </tr>
                <tr className="flex justify-between">
                  <td>Schema Name</td>
                  <td>{job?.parameters?.importSchemaParameters?.schemaName}</td>
                </tr>
                <tr className="flex justify-between">
                  <td className="px-2 capitalize align-top">Tables pattern</td>
                  <td className="px-2 max-w-76">
                    {job?.parameters?.importSchemaParameters?.tableNamePattern}
                  </td>
                </tr>
              </>
            )}
            {job?.parameters?.synchronizeRootFolderParameters && (
              <>
                <tr className="flex justify-between">
                  <td>Synchronized folder</td>
                  <td>
                    {
                      job?.parameters?.synchronizeRootFolderParameters
                        ?.synchronizationParameter?.folder
                    }
                  </td>
                </tr>
                <tr className="flex justify-between">
                  <td>Synchronization direction</td>
                  <td>
                    {
                      job?.parameters?.synchronizeRootFolderParameters
                        ?.synchronizationParameter?.direction
                    }
                  </td>
                </tr>
              </>
            )}
            {job?.parameters?.collectStatisticsParameters
              ?.statisticsCollectorSearchFilters &&
              Object.entries(
                job?.parameters?.collectStatisticsParameters
                  ?.statisticsCollectorSearchFilters
              ).map(([key, value], index) => (
                <tr key={index} className="flex justify-between">
                  <td>{key}</td>
                  <td>{renderValue(value)}</td>
                </tr>
              ))}
            {job?.parameters?.importTableParameters && (
              <>
                <tr className="flex justify-between">
                  <td>Connection Name</td>
                  <td>
                    {job?.parameters?.importTableParameters?.connectionName}
                  </td>
                </tr>
                <tr className="flex justify-between">
                  <td>Schema Name</td>
                  <td>{job?.parameters?.importTableParameters?.schemaName}</td>
                </tr>
                <tr className="flex justify-between">
                  <td className="px-2 capitalize align-top">Tables</td>
                  <td className="px-2 max-w-76">
                    {job?.parameters?.importTableParameters?.tableNames?.map(
                      (item, index) => (
                        <div key={index}>{item}</div>
                      )
                    )}
                  </td>
                </tr>
              </>
            )}
          </tbody>
        </table>
      </AccordionBody>
    </Accordion>
  );
};

export default JobChild;
