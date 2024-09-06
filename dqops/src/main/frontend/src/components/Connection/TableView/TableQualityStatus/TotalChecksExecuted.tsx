import React from 'react';
import { TableCurrentDataQualityStatusModel } from '../../../../api';
import SectionWrapper from '../../../Dashboard/SectionWrapper';

export default function TotalChecksExecuted({
  tableDataQualityStatus
}: {
  tableDataQualityStatus: TableCurrentDataQualityStatusModel;
}) {
  return (
    <SectionWrapper title="Total checks executed">
      <div className="flex gap-x-2 justify-between">
        <div className="w-42 mb-0.5">Total checks executed</div>
        <div>{tableDataQualityStatus.executed_checks}</div>
      </div>
      <div className="flex gap-x-2 justify-between">
        <div className="w-42 mb-0.5">Correct results</div>
        <div>{tableDataQualityStatus.valid_results}</div>
      </div>
      <div className="flex gap-x-2 justify-between">
        <div className="w-42 mb-0.5">Warnings</div>
        <div>{tableDataQualityStatus.warnings}</div>
      </div>
      <div className="flex gap-x-2 justify-between">
        <div className="w-42 mb-0.5">Errors</div>
        <div>{tableDataQualityStatus.errors}</div>
      </div>
      <div className="flex gap-x-2 justify-between">
        <div className="w-42 mb-0.5">Fatal results</div>
        <div>{tableDataQualityStatus.fatals}</div>
      </div>
      <div className="flex gap-x-2 justify-between">
        <div className="w-42 mb-0.5">Execution errors</div>
        <div>{tableDataQualityStatus.execution_errors}</div>
      </div>
    </SectionWrapper>
  );
}
