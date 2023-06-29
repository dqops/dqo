import {
  CheckResultsOverviewDataModelStatusesEnum,
  DqoJobHistoryEntryModel,
  DqoJobHistoryEntryModelStatusEnum
} from '../../../api';
import React, { useState } from 'react';
import SvgIcon from '../../SvgIcon';
import {
  Accordion,
  AccordionBody,
  AccordionHeader
} from '@material-tailwind/react';
import moment from 'moment';
import { JobApiClient } from '../../../services/apiClient';
import clsx from 'clsx';
type GetColorFunc = (status: string) => string;
const JobChild = ({
  job,
  parentId,
  renderStatusFunc,
  getColorFunc
}: {
  job: DqoJobHistoryEntryModel;
  parentId: number;
  renderStatusFunc?: void;
  getColorFunc?: GetColorFunc;
}) => {
  const renderValue = (value: any) => {
    if (typeof value === 'boolean') {
      return value ? 'Yes' : 'No';
    }
    if (typeof value === 'object') {
      return value.toString();
    }
    return value;
  };
  const getColor = (status: CheckResultsOverviewDataModelStatusesEnum) => {
    switch (status) {
      case 'valid':
        return 'teal-500';
      case 'warning':
        return 'yellow-900';
      case 'error':
        return 'orange-900';
      case 'fatal':
        return 'red-900';
      case 'execution_error':
        return 'black';
      default:
        return 'black';
    }
  };
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
  const cancelJob = async (jobId: number) => {
    await JobApiClient.cancelJob(jobId);
  };

  return (
    <Accordion open={open}>
      {job.jobId?.parentJobId?.jobId === parentId ? (
        <AccordionHeader onClick={() => setOpen(!open)}>
          <div className="flex flex-wrap justify-between items-center text-sm w-full text-gray-700">
            <div className="flex flex-wrap space-x-1 items-center">
              <div className="px-2">{job.jobType}</div>

              {renderStatus()}
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
                  {job.jobType === 'run checks on table' && (
                    <div
                      className={clsx(
                        `w-3 h-3 bg-${getColor(
                          job.parameters?.runChecksOnTableParameters
                            ?.runChecksResult?.highestSeverity
                            ? job.parameters?.runChecksOnTableParameters
                                ?.runChecksResult?.highestSeverity
                            : 'error'
                        )}`
                      )}
                    />
                  )}
                  {moment(job?.statusChangedAt).format('YYYY-MM-DD HH:mm:ss')}
                </div>

                <div className="hidden group-hover:block fixed px-1 w-50 h-30  rounded-md border border-gray-400 z-50 top-50 right-25 bg-white">
                  <div className="flex gap-x-2">
                    <div className="font-light">Highest severity:</div>
                    <div
                      className={clsx(
                        `text-${getColor(
                          job.parameters?.runChecksOnTableParameters
                            ?.runChecksResult?.highestSeverity
                            ? job.parameters?.runChecksOnTableParameters
                                ?.runChecksResult?.highestSeverity
                            : 'error'
                        )}`
                      )}
                    >
                      {
                        job.parameters?.runChecksOnTableParameters
                          ?.runChecksResult?.highestSeverity
                      }
                    </div>
                  </div>
                  <div className="flex gap-x-2">
                    <div className="font-light">Executed check:</div>
                    <div>
                      {
                        job.parameters?.runChecksOnTableParameters
                          ?.runChecksResult?.executedChecks
                      }
                    </div>
                  </div>
                  <div className="flex gap-x-2">
                    <div className="font-light">Valid result:</div>
                    <div>
                      {
                        job.parameters?.runChecksOnTableParameters
                          ?.runChecksResult?.validResults
                      }
                    </div>
                  </div>
                  <div className="flex gap-x-2">
                    <div className="font-light">Warnings:</div>
                    <div>
                      {
                        job.parameters?.runChecksOnTableParameters
                          ?.runChecksResult?.warnings
                      }
                    </div>
                  </div>
                  <div className="flex gap-x-2">
                    <div className="font-light">Errors</div>
                    <div>
                      {
                        job.parameters?.runChecksOnTableParameters
                          ?.runChecksResult?.errors
                      }
                    </div>
                  </div>
                  <div className="flex gap-x-2">
                    <div className="font-light">Fatals:</div>
                    <div>
                      {
                        job.parameters?.runChecksOnTableParameters
                          ?.runChecksResult?.fatals
                      }
                    </div>
                  </div>
                  <div className="flex gap-x-2">
                    <div className="font-light">Execution Fatals:</div>
                    <div>
                      {
                        job.parameters?.runChecksOnTableParameters
                          ?.runChecksResult?.executionErrors
                      }
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </AccordionHeader>
      ) : (
        <div></div>
      )}
      <AccordionBody>
        <table className="text-gray-700 w-full">
          <tbody>
            <tr>
              <td className="px-2">Status</td>
              <td className="px-2">{job?.status}</td>
            </tr>
            <tr>
              <td className="px-2">Last Changed</td>
              <td className="px-2">
                {moment(job?.statusChangedAt).format('YYYY-MM-DD HH:mm:ss')}
              </td>
            </tr>

            {job?.parameters?.runChecksParameters?.checkSearchFilters &&
              Object.entries(
                job?.parameters?.runChecksParameters?.checkSearchFilters
              ).map(([key, value], index) => (
                <tr key={index}>
                  <td className="px-2">{key}</td>
                  <td className="px-2">{renderValue(value)}</td>
                </tr>
              ))}
            {job?.parameters?.importSchemaParameters && (
              <>
                <tr>
                  <td className="px-2">Connection Name</td>
                  <td className="px-2">
                    {job?.parameters?.importSchemaParameters?.connectionName}
                  </td>
                </tr>
                <tr>
                  <td className="px-2">Schema Name</td>
                  <td className="px-2">
                    {job?.parameters?.importSchemaParameters?.schemaName}
                  </td>
                </tr>
                <tr>
                  <td className="px-2 capitalize align-top">Tables pattern</td>
                  <td className="px-2 max-w-76">
                    {job?.parameters?.importSchemaParameters?.tableNamePattern}
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
            {job?.parameters?.collectStatisticsParameters
              ?.statisticsCollectorSearchFilters &&
              Object.entries(
                job?.parameters?.collectStatisticsParameters
                  ?.statisticsCollectorSearchFilters
              ).map(([key, value], index) => (
                <tr key={index}>
                  <td className="px-2">{key}</td>
                  <td className="px-2">{renderValue(value)}</td>
                </tr>
              ))}
            {job?.parameters?.importTableParameters && (
              <>
                <tr>
                  <td className="px-2">Connection Name</td>
                  <td className="px-2">
                    {job?.parameters?.importTableParameters?.connectionName}
                  </td>
                </tr>
                <tr>
                  <td className="px-2">Schema Name</td>
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
                <td className="px-2 capitalize">Error Message</td>
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
