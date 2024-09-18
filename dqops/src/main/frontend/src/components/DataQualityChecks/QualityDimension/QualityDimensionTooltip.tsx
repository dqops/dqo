import moment from 'moment';
import React from 'react';
import { DimensionCurrentDataQualityStatusModel } from '../../../api';

const QualityDimensionTooltip = ({
  data
}: {
  data: DimensionCurrentDataQualityStatusModel | undefined;
}) => {
  if (data && data.last_check_executed_at) {
    return (
      <div>
        <div className="flex gap-x-2">
          <div className="w-49">Last executed at:</div>
          <div>
            {moment(data?.last_check_executed_at).format('YYYY-MM-DD HH:mm:ss')}
          </div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Current severity level:</div>
          <div>{data?.current_severity}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Highest historical severity level:</div>
          <div>{data?.highest_historical_severity}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Quality Dimension:</div>
          <div>{data?.dimension}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Executed checks:</div>
          <div>{data?.executed_checks}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Valid results:</div>
          <div>{data?.valid_results}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Warnings:</div>
          <div>{data?.warnings}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Errors:</div>
          <div>{data?.errors}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Fatals:</div>
          <div>{data?.fatals}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Data quality KPI:</div>
          <div>
            {data.data_quality_kpi !== undefined
              ? Number(data.data_quality_kpi).toFixed(2) + ' %'
              : '-'}
          </div>
        </div>
      </div>
    );
  }
  return (
    <div>
      <div className="flex gap-x-2">
        <div className="w-42">Quality Dimension:</div>
        <div>{data?.dimension}</div>
      </div>
      <div className="w-full">No data quality checks configured</div>
    </div>
  );
};

export default QualityDimensionTooltip;
