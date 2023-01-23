import React, { useState } from 'react';

import {
  Accordion,
  AccordionBody,
  AccordionHeader,
  Popover,
  PopoverHandler,
  PopoverContent,
  IconButton
} from '@material-tailwind/react';
import SvgIcon from '../SvgIcon';
import moment from 'moment';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import {
  DqoJobHistoryEntryModel,
  DqoJobHistoryEntryModelStatusEnum
} from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { toggleMenu } from '../../redux/actions/job.actions';

const JobItem = ({ job }: { job: DqoJobHistoryEntryModel }) => {
  const [open, setOpen] = useState(false);

  const renderValue = (value: any) => {
    if (typeof value === 'boolean') {
      return value ? 'Yes' : 'No';
    }
    if (typeof value === 'object') {
      return value.toString();
    }
    return value;
  };

  const renderStatus = () => {
    if (job.status === DqoJobHistoryEntryModelStatusEnum.succeeded) {
      return <SvgIcon name="success" className="w-4 h-4 text-green-700" />;
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
      <AccordionHeader onClick={() => setOpen(!open)}>
        <div className="flex justify-between items-center text-sm w-full">
          <div className="flex space-x-1 items-center">
            <div>{job.jobType}</div>
            {renderStatus()}
          </div>
          <div>
            {moment(job.jobId?.createdAt).format('YYYY-MM-DD HH:mm:ss')}
          </div>
        </div>
      </AccordionHeader>
      <AccordionBody>
        <table>
          <tbody>
            <tr>
              <td className="px-2 capitalize">Status</td>
              <td className="px-2 truncate">
                {job?.status}
              </td>
            </tr>
            <tr>
              <td className="px-2 capitalize">Last Changed</td>
              <td className="px-2 truncate">
                {moment(job?.statusChangedAt).format('YYYY-MM-DD HH:mm:ss')}
              </td>
            </tr>
            {job?.errorMessage && (
              <>
                <tr>
                  <td className="px-2 capitalize">Error Message</td>
                  <td className="px-2 max-w-78">
                    {job?.errorMessage}
                  </td>
                </tr>
              </>
            )}
            {job?.parameters?.runChecksParameters?.checkSearchFilters &&
              Object.entries(
                job?.parameters?.runChecksParameters?.checkSearchFilters
              ).map(([key, value], index) => (
                <tr key={index}>
                  <td className="px-2 capitalize">{key}</td>
                  <td className="px-2 truncate">{renderValue(value)}</td>
                </tr>
              ))}
             {job?.parameters?.importSchemaParameters &&(
              <>
                <tr>
                  <td className="px-2 capitalize">Connection Name</td>
                  <td className="px-2 truncate">
                    {job?.parameters?.importSchemaParameters?.connectionName}
                  </td>
                </tr>
                <tr>
                  <td className="px-2 capitalize">Schema Name</td>
                  <td className="px-2 truncate">
                    {job?.parameters?.importSchemaParameters?.schemaName}
                  </td>
                </tr>
                <tr>
                  <td className="px-2 capitalize align-top">Tables pattern</td>
                  <td className="px-2 truncate">
                    {job?.parameters?.importSchemaParameters?.tableNamePattern}
                  </td>
                </tr>
              </>
            )}
            {job?.parameters?.synchronizeRootFolderParameters &&(
              <>
                <tr>
                  <td className="px-2 capitalize">Synchronized folder</td>
                  <td className="px-2 truncate">
                    {job?.parameters?.synchronizeRootFolderParameters?.rootType}
                  </td>
                </tr>
              </>
            )}
            {job?.parameters?.runProfilersParameters?.profilerSearchFilters &&
              Object.entries(
                job?.parameters?.runProfilersParameters?.profilerSearchFilters
              ).map(([key, value], index) => (
                <tr key={index}>
                  <td className="px-2 capitalize">{key}</td>
                  <td className="px-2 truncate">{renderValue(value)}</td>
                </tr>
              ))}
            {job?.parameters?.importTableParameters && (
              <>
                <tr>
                  <td className="px-2 capitalize">Connection Name</td>
                  <td className="px-2 truncate">
                    {job?.parameters?.importTableParameters?.connectionName}
                  </td>
                </tr>
                <tr>
                  <td className="px-2 capitalize">Schema Name</td>
                  <td className="px-2 truncate">
                    {job?.parameters?.importTableParameters?.schemaName}
                  </td>
                </tr>
                <tr>
                  <td className="px-2 capitalize align-top">Tables</td>
                  <td className="px-2 truncate">
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

const NotificationMenu = () => {
  const { jobs, isOpen } = useSelector((state: IRootState) => state.job);
  const dispatch = useActionDispatch();

  const data = jobs?.jobs
    ? jobs?.jobs.sort((a, b) => {
        return (b.jobId?.jobId || 0) - (a.jobId?.jobId || 0);
      })
    : [];

  const toggleOpen = () => {
    dispatch(toggleMenu(!isOpen));
  };

  return (
    <Popover placement="bottom-end" open={isOpen} handler={toggleOpen}>
      <PopoverHandler>
        <IconButton className="!mr-3" variant="text">
          <div className="relative">
            <SvgIcon name="bell" className="w-5 h-5 text-gray-500" />
            <span className="absolute top-0 right-0 transform translate-x-1/2 -translate-y-1/2 rounded-full bg-purple-500 text-white px-1 py-0.5 text-xxs">
              {jobs?.jobs?.length}
            </span>
          </div>
        </IconButton>
      </PopoverHandler>
      <PopoverContent className="z-50 min-w-120 max-w-120 px-0 ">
        <div className="border-b border-gray-300 font-semibold pb-2 text-xl flex items-center justify-between px-4">
          <div>Notifications</div>
        </div>
        <div className="overflow-auto max-h-100 py-4 px-4">
          {data?.map((job) => (
            <JobItem key={job.jobId?.jobId} job={job} />
          ))}
        </div>
      </PopoverContent>
    </Popover>
  );
};

export default NotificationMenu;
