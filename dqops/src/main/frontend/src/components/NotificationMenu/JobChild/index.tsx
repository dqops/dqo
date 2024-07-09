import {
  Accordion,
  AccordionBody,
  AccordionHeader
} from '@material-tailwind/react';
import moment from 'moment';
import React, { useState } from 'react';
import {
  DqoJobHistoryEntryModel,
  DqoJobHistoryEntryModelStatusEnum
} from '../../../api';
import { JobApiClient } from '../../../services/apiClient';
import SvgIcon from '../../SvgIcon';

const JobChild = ({ job }: { job: DqoJobHistoryEntryModel }) => {
  const [open, setOpen] = useState(false);

  const cancelJob = async (jobId: number) => {
    await JobApiClient.cancelJob(jobId.toString());
  };

  if (!job.jobType) return null;

  return (
    <Accordion open={open}>
      <AccordionHeader onClick={() => setOpen(!open)}>
        <div className="flex flex-wrap justify-between items-center text-sm w-full text-gray-700">
          <div className="flex flex-wrap space-x-1 items-center">
            <div className="px-2">
              {job.jobType
                .replace(/_/g, ' ')
                .replace(/./, (c) => c.toUpperCase())}
            </div>
          </div>
          <div className="flex items-center gap-x-2">
            {job.status === DqoJobHistoryEntryModelStatusEnum.running ? (
              <div
                onClick={() =>
                  cancelJob(job.jobId?.jobId ? Number(job.jobId?.jobId) : 0)
                }
              >
                <SvgIcon name="canceljobs" />
              </div>
            ) : (
              <div></div>
            )}
            <div className="group relative">
              <div className="flex items-center gap-x-2">
                {renderStatus(job)}
                {moment(job?.statusChangedAt).format('YYYY-MM-DD HH:mm:ss')}
              </div>
            </div>
          </div>
        </div>
      </AccordionHeader>

      <AccordionBody>
        <table className="text-gray-700 w-full">
          <tbody>
            <tr>
              <td className="px-2">Status</td>
              <td className="px-2 ">
                {job?.status}
                {'  '}

                {job.errorMessage &&
                  job.errorMessage.includes('dqocloud.accesskey') && (
                    <span className="px-2 text-red-500">
                      (Cloud DQOps Api Key is invalid. Your trial period has
                      expired or a new version of DQOps was released. Please run{' '}
                      {"'"}cloud login{"'"} from DQOps shell)
                    </span>
                  )}
              </td>
            </tr>
            <tr>
              <td className="px-2">Last changed</td>
              <td className="px-2">
                {moment(job?.statusChangedAt).format('YYYY-MM-DD HH:mm:ss')}
              </td>
            </tr>

            {job?.parameters?.runChecksParameters?.check_search_filters &&
              Object.entries(
                job?.parameters?.runChecksParameters?.check_search_filters
              ).map(([key, value], index) => (
                <tr key={index}>
                  <td className="px-2">{key}</td>
                  <td className="px-2">{renderValue(value)}</td>
                </tr>
              ))}
            {job?.parameters?.importSchemaParameters && (
              <>
                <tr>
                  <td className="px-2">Connection name</td>
                  <td className="px-2">
                    {job?.parameters?.importSchemaParameters?.connectionName}
                  </td>
                </tr>
                <tr>
                  <td className="px-2">Schema name</td>
                  <td className="px-2">
                    {job?.parameters?.importSchemaParameters?.schemaName}
                  </td>
                </tr>
                <tr>
                  <td className="px-2 capitalize align-top">
                    Table name contains
                  </td>
                  <td className="px-2 max-w-76">
                    {job?.parameters?.importSchemaParameters?.tableNameContains}
                  </td>
                </tr>
              </>
            )}
            {job?.parameters?.synchronizeRootFolderParameters && (
              <>
                <tr>
                  <td className="px-2">Synchronized folder</td>
                  <td className="px-2">
                    {
                      job?.parameters?.synchronizeRootFolderParameters
                        ?.synchronizationParameter?.folder
                    }
                  </td>
                </tr>
                <tr>
                  <td className="px-2">Synchronization direction</td>
                  <td className="px-2">
                    {
                      job?.parameters?.synchronizeRootFolderParameters
                        ?.synchronizationParameter?.direction
                    }
                  </td>
                </tr>
              </>
            )}
            {job?.parameters?.runChecksOnTableParameters && (
              <>
                <tr>
                  <td className="px-2">Connection</td>
                  <td className="px-2">
                    {job?.parameters?.runChecksOnTableParameters.connection}
                  </td>
                </tr>
                <tr>
                  <td className="px-2">Full table name</td>
                  <td className="px-2">
                    {job?.parameters?.runChecksOnTableParameters.table
                      ?.schema_name +
                      '.' +
                      job?.parameters?.runChecksOnTableParameters.table
                        ?.table_name}
                  </td>
                </tr>
              </>
            )}
            {job?.parameters?.collectStatisticsParameters
              ?.statistics_collector_search_filters &&
              Object.entries(
                job?.parameters?.collectStatisticsParameters
                  ?.statistics_collector_search_filters
              ).map(([key, value], index) => (
                <tr key={index}>
                  <td className="px-2">{key}</td>
                  <td className="px-2">{renderValue(value)}</td>
                </tr>
              ))}
            {job?.parameters?.importTableParameters && (
              <>
                <tr>
                  <td className="px-2">Connection name</td>
                  <td className="px-2">
                    {job?.parameters?.importTableParameters?.connectionName}
                  </td>
                </tr>
                <tr>
                  <td className="px-2">Schema name</td>
                  <td className="px-2">
                    {job?.parameters?.importTableParameters?.schemaName}
                  </td>
                </tr>
                <tr>
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
            {job?.errorMessage && (
              <tr>
                <td className="px-2 capitalize">Error message</td>
                <td className="px-2 max-w-76">{job?.errorMessage}</td>
              </tr>
            )}
          </tbody>
        </table>
      </AccordionBody>
    </Accordion>
  );
};

export default JobChild;

const renderValue = (value: any) => {
  if (typeof value === 'boolean') {
    return value ? 'Yes' : 'No';
  }
  if (typeof value === 'object') {
    return value.toString();
  }
  return value;
};

const renderStatus = (job: DqoJobHistoryEntryModel) => {
  if (job.status === DqoJobHistoryEntryModelStatusEnum.finished) {
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
