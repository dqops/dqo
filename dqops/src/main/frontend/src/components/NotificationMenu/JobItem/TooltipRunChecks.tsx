import React from 'react';
import { CheckResultsOverviewDataModelStatusesEnum } from '../../../api';
import { TJobDictionary } from '../../../shared/constants';
import {
  Popover,
  PopoverContent,
  PopoverHandler
} from '@material-tailwind/react';

export default function TooltipRunChecks({
  job,
  open
}: {
  job: TJobDictionary;
  open: boolean;
}) {
  const getColor = (status?: CheckResultsOverviewDataModelStatusesEnum) => {
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

  return (
    <Popover open={open}>
      <PopoverHandler>
        <div
          className="w-3 h-3"
          style={{
            backgroundColor: getColor(
              job.parameters?.runChecksParameters?.run_checks_result
                ?.execution_errors &&
                job.parameters?.runChecksParameters?.run_checks_result
                  ?.execution_errors > 0
                ? undefined
                : job.parameters?.runChecksParameters?.run_checks_result
                    ?.highest_severity
            )
          }}
        />
      </PopoverHandler>
      <PopoverContent className="min-w-60 max-w-60 px-3 absolute right-0 z-50 text-black">
        <div className="flex gap-x-2">
          <div className="font-light">Highest severity:</div>
          <div
            style={{
              color: getColor(
                job.parameters?.runChecksParameters?.run_checks_result
                  ?.execution_errors &&
                  job.parameters?.runChecksParameters?.run_checks_result
                    ?.execution_errors > 0
                  ? 'execution_error'
                  : job.parameters?.runChecksParameters?.run_checks_result
                      ?.highest_severity
                  ? job.parameters?.runChecksParameters?.run_checks_result
                      ?.highest_severity
                  : 'error'
              )
            }}
            className="font-bold"
          >
            {job.parameters?.runChecksParameters?.run_checks_result
              ?.execution_errors &&
            job.parameters?.runChecksParameters?.run_checks_result
              ?.execution_errors > 0
              ? 'execution error'
              : job.parameters?.runChecksParameters?.run_checks_result
                  ?.highest_severity}
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
            {job.parameters?.runChecksParameters?.run_checks_result?.errors ===
            0
              ? '-'
              : job.parameters?.runChecksParameters?.run_checks_result?.errors}
          </div>
        </div>
        <div className="flex gap-x-2">
          <div className="font-light">Fatals:</div>
          <div>
            {job.parameters?.runChecksParameters?.run_checks_result?.fatals ===
            0
              ? '-'
              : job.parameters?.runChecksParameters?.run_checks_result?.fatals}
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
      </PopoverContent>
    </Popover>
  );
}
