import { DqoJobHistoryEntryModel, DqoJobHistoryEntryModelStatusEnum } from "../../../api";
import React, { useState } from "react";
import SvgIcon from "../../SvgIcon";
import { Accordion, AccordionBody, AccordionHeader } from "@material-tailwind/react";
import moment from "moment";

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
            {moment(job?.statusChangedAt).format('YYYY-MM-DD HH:mm:ss')}
          </div>
        </div>
      </AccordionHeader>
      <AccordionBody>
        <table>
          <tbody>
          <tr>
            <td className="px-2 capitalize">Status</td>
            <td className="px-2 max-w-76">
              {job?.status}
            </td>
          </tr>
          <tr>
            <td className="px-2 capitalize">Last Changed</td>
            <td className="px-2 max-w-76">
              {moment(job?.statusChangedAt).format('YYYY-MM-DD HH:mm:ss')}
            </td>
          </tr>
          {job?.errorMessage && (
            <>
              <tr>
                <td className="px-2 capitalize">Error Message</td>
                <td className="px-2 max-w-76">
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
              <td className="px-2 max-w-76">{renderValue(value)}</td>
            </tr>
          ))}
          {job?.parameters?.importSchemaParameters &&(
            <>
              <tr>
                <td className="px-2 capitalize">Connection Name</td>
                <td className="px-2 max-w-76">
                  {job?.parameters?.importSchemaParameters?.connectionName}
                </td>
              </tr>
              <tr>
                <td className="px-2 capitalize">Schema Name</td>
                <td className="px-2 max-w-76">
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
          {job?.parameters?.synchronizeRootFolderParameters &&(
            <>
              <tr>
                <td className="px-2 capitalize">Synchronized folder</td>
                <td className="px-2 max-w-76">
                  {job?.parameters?.synchronizeRootFolderParameters?.synchronizationParameter?.folder}
                </td>
              </tr>
              <tr>
                <td className="px-2 capitalize">Synchronization direction</td>
                <td className="px-2 max-w-76">
                  {job?.parameters?.synchronizeRootFolderParameters?.synchronizationParameter?.direction}
                </td>
              </tr>
            </>
          )}
          {job?.parameters?.collectStatisticsParameters?.statisticsCollectorSearchFilters &&
          Object.entries(
            job?.parameters?.collectStatisticsParameters?.statisticsCollectorSearchFilters
          ).map(([key, value], index) => (
            <tr key={index}>
              <td className="px-2 capitalize">{key}</td>
              <td className="px-2 max-w-76">{renderValue(value)}</td>
            </tr>
          ))}
          {job?.parameters?.importTableParameters && (
            <>
              <tr>
                <td className="px-2 capitalize">Connection Name</td>
                <td className="px-2 max-w-76">
                  {job?.parameters?.importTableParameters?.connectionName}
                </td>
              </tr>
              <tr>
                <td className="px-2 capitalize">Schema Name</td>
                <td className="px-2 max-w-76">
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
          </tbody>
        </table>
      </AccordionBody>
    </Accordion>
  );
};

export default JobItem;
