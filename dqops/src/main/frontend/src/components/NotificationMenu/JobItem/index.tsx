import {
  CheckResultsOverviewDataModelStatusesEnum,
  DqoJobHistoryEntryModelJobTypeEnum,
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
import JobChild from '../JobChild';
import { reduceCounter } from '../../../redux/actions/job.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { JobApiClient } from '../../../services/apiClient';
import clsx from 'clsx';
import { IJob } from '../../../shared/constants';

const JobItem = ({
  job,
  notifnumber,
  canUserCancelJobs
}: {
  job: IJob;
  notifnumber?: number;
  canUserCancelJobs?: boolean
}) => {
  const dispatch = useActionDispatch();

  const [sizeOfNot, setSizeOfNot] = useState<number | undefined>(notifnumber);
  const reduceCount = () => {
    dispatch(reduceCounter(true, sizeOfNot));
  };

  const [open, setOpen] = useState(false);
  const [open2, setOpen2] = useState(false);

  const renderValue = (value: any) => {
    if (typeof value === 'boolean') {
      return value ? 'Yes' : 'No';
    }
    if (typeof value === 'object') {
      return value.toString();
    }
    return value;
  };

  const cancelJob = async (jobId: number) => {
    await JobApiClient.cancelJob(jobId.toString());
  };

  const getColor = (status: CheckResultsOverviewDataModelStatusesEnum) => {
    switch (status) {
      case 'valid':
        return '#029a80';
      case 'warning':
        return '#ebe51e';
      case 'error':
        return '#ff9900';
      case 'fatal':
        return '#e3170a';
      case 'execution_error':
        return 'black';
      default:
        return 'black';
    }
  };
  const hasInvalidApiKeyError = job.childs.some((x) => x.errorMessage?.includes('dqocloud.accesskey'));

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
    if (job.status === DqoJobHistoryEntryModelStatusEnum.cancelled) {
      return <SvgIcon name="failed" className="w-4 h-4 text-red-700" />;
    }
    if(hasInvalidApiKeyError){
      return <SvgIcon name="failed" className="w-4 h-4 text-red-700" />;
    }
  };


  return (
    <Accordion open={open} style={{ position: 'relative' }}>
      <AccordionHeader className="!outline-none" onClick={() => setOpen(!open)}>
        <div className="group flex justify-between items-center text-sm w-full text-gray-700 ">
          <div className="flex space-x-1 items-center">
            <div>{(job.jobType !== undefined && String(job.jobType).length !== 0) ? job.jobType.replace(/_/g, " ") : "Error"}</div>
          </div>
          <div className="flex items-center gap-x-2">
            {job.status === DqoJobHistoryEntryModelStatusEnum.running ? (
              <div className={clsx(canUserCancelJobs === false ? "pointer-events-none cursor-not-allowed" : "")}
                onClick={() =>
                  cancelJob(job.jobId.jobId ? Number(job.jobId?.jobId) : 0)
                }
              >
                <SvgIcon name="canceljobs" />
              </div>
            ) : (
              <></>
            )}
            <div className=" relative">
              <div className="flex items-center gap-x-3">
                {job.jobType === DqoJobHistoryEntryModelJobTypeEnum.run_checks &&
                  job.status == DqoJobHistoryEntryModelStatusEnum.succeeded && (
                    <div
                      className="w-3 h-3"
                      style={{
                        backgroundColor: getColor(
                          job.parameters?.runChecksParameters?.run_checks_result?.execution_errors && 
                          job.parameters?.runChecksParameters?.run_checks_result?.execution_errors > 0 ? 'execution_error' :
                          (job.parameters?.runChecksParameters?.run_checks_result
                            ?.highest_severity
                            ? job.parameters?.runChecksParameters
                                ?.run_checks_result?.highest_severity
                            : 'error')
                        )
                      }}
                    />
                  )}
                <div className='flex gap-x-2 items-center'>
                  {renderStatus()}
                  {moment(job?.statusChangedAt).format('YYYY-MM-DD HH:mm:ss')}
                </div>
              </div>
              {job.jobType === DqoJobHistoryEntryModelJobTypeEnum.run_checks &&
                job.status == DqoJobHistoryEntryModelStatusEnum.succeeded && (
                  <div
                    className="hidden group-hover:block absolute px-5 gap-y-1 w-80 h-29 rounded-md border border-gray-400 z-50 bg-white"
                    style={{
                      transform: 'translate(50%, -50%)',
                      top: '550%',
                      right: '145%'
                    }}
                  >
                    <div className="flex gap-x-2">
                      <div className="font-light">Highest severity:</div>
                      <div
                        style={{
                          color: getColor(
                            job.parameters?.runChecksParameters?.run_checks_result?.execution_errors && 
                            job.parameters?.runChecksParameters?.run_checks_result?.execution_errors > 0 ? 'execution_error' :
                            (job.parameters?.runChecksParameters?.run_checks_result
                              ?.highest_severity
                              ? job.parameters?.runChecksParameters
                                  ?.run_checks_result?.highest_severity
                              : 'error')
                          )
                        }}
                      >
                        {
                          job.parameters?.runChecksParameters?.run_checks_result?.execution_errors && 
                          job.parameters?.runChecksParameters?.run_checks_result?.execution_errors > 0 ? "execution error" :
                          job.parameters?.runChecksParameters?.run_checks_result?.highest_severity
                        }
                      </div>
                    </div>
                    <div className="flex gap-x-2">
                      <div className="font-light">Executed checks:</div>
                      <div>
                        {
                          job.parameters?.runChecksParameters?.run_checks_result
                            ?.executed_checks
                        }
                      </div>
                    </div>
                    <div className="flex gap-x-2">
                      <div className="font-light">Valid results:</div>
                      <div>
                        {job.parameters?.runChecksParameters?.run_checks_result
                          ?.valid_results === 0
                          ? '-'
                          : job.parameters?.runChecksParameters?.run_checks_result
                              ?.valid_results}
                      </div>
                    </div>
                    <div className="flex gap-x-2">
                      <div className="font-light">Warnings:</div>
                      <div>
                        {job.parameters?.runChecksParameters?.run_checks_result
                          ?.warnings === 0
                          ? '-'
                          : job.parameters?.runChecksParameters?.run_checks_result
                              ?.warnings}
                      </div>
                    </div>
                    <div className="flex gap-x-2">
                      <div className="font-light">Errors:</div>
                      <div>
                        {job.parameters?.runChecksParameters?.run_checks_result
                          ?.errors === 0
                          ? '-'
                          : job.parameters?.runChecksParameters?.run_checks_result
                              ?.errors}
                      </div>
                    </div>
                    <div className="flex gap-x-2">
                      <div className="font-light">Fatals:</div>
                      <div>
                        {job.parameters?.runChecksParameters?.run_checks_result
                          ?.fatals === 0
                          ? '-'
                          : job.parameters?.runChecksParameters?.run_checks_result
                              ?.fatals}
                      </div>
                    </div>
                    <div className="flex gap-x-2">
                      <div className="font-light">Execution errors:</div>
                      <div>
                        {job.parameters?.runChecksParameters?.run_checks_result
                          ?.execution_errors === 0
                          ? '-'
                          : job.parameters?.runChecksParameters?.run_checks_result
                              ?.execution_errors}
                      </div>
                    </div>
                  </div>
                )}
            </div>
          </div>
        </div>
      </AccordionHeader>

      <AccordionBody>
        <table className="text-gray-700 w-auto">
          <tbody>
            <tr className="flex justify-between w-108">
              <td>Status</td>
              <td>{job?.status}</td>
              {hasInvalidApiKeyError && (
                    <span className="px-2 text-red-500">
                      (DQOps Cloud Api Key is invalid. Your trial period has expired or a new version of DQOps was released.{' '}
                      Please run {"'"}cloud login{"'"} from DQOps shell)
                    </span>
                  )}
            </tr>
            <tr className="flex justify-between w-108">
              <td>Last Changed</td>
              <td>
                {moment(job?.statusChangedAt).format('YYYY-MM-DD HH:mm:ss')}
              </td>
            </tr>
            {job?.parameters?.runChecksParameters?.check_search_filters &&
              Object.entries(
                job?.parameters?.runChecksParameters?.check_search_filters
              ).map(([key, value], index) => (
                <tr key={index} className="flex justify-between w-108">
                  <td>{key.replace(/([a-z])([A-Z])/g, '$1 $2').replace(/^\w/, (c) => c.toUpperCase())}</td>
                  <td>{renderValue(value)}</td>
                </tr>
              ))}
            {job?.parameters?.importSchemaParameters && (
              <>
                <tr className="flex justify-between w-108">
                  <td>Connection Name</td>
                  <td>
                    {job?.parameters?.importSchemaParameters?.connectionName}
                  </td>
                </tr>
                <tr className="flex justify-between w-108">
                  <td>Schema Name</td>
                  <td>{job?.parameters?.importSchemaParameters?.schemaName}</td>
                </tr>
                <tr className="flex justify-between w-108">
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
                  <td className="px-2 capitalize">Synchronized folder</td>
                  <td className="px-2 max-w-76">
                    {job?.parameters?.synchronizeRootFolderParameters
                      ?.synchronizationParameter?.folder ||
                      (job as any).updatedModel?.parameters
                        ?.synchronizeRootFolderParameters
                        ?.synchronizationParameter?.folder}
                  </td>
                </tr>
                <tr>
                  <td className="px-2 capitalize">Synchronization direction</td>
                  <td className="px-2 max-w-76">
                    {job?.parameters?.synchronizeRootFolderParameters
                      ?.synchronizationParameter?.direction ||
                      (job as any).updatedModel?.parameters
                        ?.synchronizeRootFolderParameters
                        ?.synchronizationParameter?.direction}
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
                <tr key={index} className="flex justify-between w-108">
                  <td>{key.replace(/([a-z])([A-Z])/g, '$1 $2').replace(/^\w/, (c) => c.toUpperCase())}</td>
                  <td>{renderValue(value)}</td>
                </tr>
              ))}
            {job?.parameters?.importTableParameters && (
              <>
                <tr className="flex justify-between w-108">
                  <td>Connection Name</td>
                  <td>
                    {job?.parameters?.importTableParameters?.connectionName}
                  </td>
                </tr>
                <tr className="flex justify-between w-108">
                  <td>Schema Name</td>
                  <td>{job?.parameters?.importTableParameters?.schemaName}</td>
                </tr>
                <tr className="flex justify-between w-108">
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
              <>
                <tr className="flex flex-col w-108">
                  <td className="capitalize">Error Message:</td>
                  <td className="px-2 ">{job?.errorMessage}</td>
                </tr>
              </>
            )}
            {job.childs.length !== 0 && (
              <Accordion open={open2} className="min-w-100">
                <AccordionHeader
                  onClick={() => {
                    setOpen2(!open2), reduceCount();
                  }}
                >
                  <div
                    className=" flex justify-between items-center text-sm w-full text-gray-700"
                    onClick={() => setSizeOfNot(sizeOfNot && sizeOfNot - 6)}
                  >
                    Tasks{' '}
                  </div>
                </AccordionHeader>
                <AccordionBody className="py-0">
                  <div className="overflow-y-hidden">
                    {job.childs.map((notification: any, index) => (
                      <div key={index}>
                        <JobChild job={notification} />
                      </div>
                    ))}
                  </div>
                </AccordionBody>
              </Accordion>
            )}
          </tbody>
        </table>
      </AccordionBody>
    </Accordion>
  );
};

export default JobItem;
