import moment from 'moment';
import React from 'react';
import { TableCurrentDataQualityStatusModel } from '../../../../api';
import SectionWrapper from '../../../Dashboard/SectionWrapper';

export default function CurrentTableStatus({
  tableDataQualityStatus
}: {
  tableDataQualityStatus: TableCurrentDataQualityStatusModel;
}) {
  return (
    <SectionWrapper title="Current table status">
      <div className="flex gap-x-2">
        <div className="w-43 mb-0.5">Status</div>
        <div>{tableDataQualityStatus.current_severity}</div>
      </div>
      <div className="flex gap-x-2">
        <div className="w-43 mb-0.5">Last check executed at</div>
        <div>
          {moment(tableDataQualityStatus.last_check_executed_at).format(
            'YYYY-MM-DD HH:mm:ss'
          )}
        </div>
      </div>
      <div className="flex gap-x-2">
        <div className="w-43">Data quality KPI score</div>
        <div>
          {tableDataQualityStatus.data_quality_kpi !== undefined
            ? Number(tableDataQualityStatus.data_quality_kpi).toFixed(2) + ' %'
            : '-'}
        </div>
      </div>
    </SectionWrapper>
  );
}
