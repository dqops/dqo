import { Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import moment from 'moment';
import React from 'react';
import {
  DimensionCurrentDataQualityStatusModel,
  DimensionCurrentDataQualityStatusModelCurrentSeverityEnum,
  TableListModel
} from '../../api';
import { getColor } from '../../components/Connection/TableView/TableQualityStatus/TableQualityStatusUtils';

export default function SchemaTableItemDimensions({
  item
}: {
  item: TableListModel;
}) {
  const getBasicDimmensionsKeys = (dimentions: any, type: string) => {
    const basicDimensions = Object.keys(dimentions ?? {})?.find(
      (x) => x === type
    );
    return basicDimensions;
  };
  const basicDimensionTypes = ['Completeness', 'Validity', 'Consistency'];

  const getAdditionalDimentionsKeys = (dimentions: any) => {
    return (
      Object.keys(dimentions ?? {})?.filter(
        (x) => !basicDimensionTypes.includes(x)
      ) ?? []
    );
  };

  return (
    <>
      {' '}
      {basicDimensionTypes.map((dimType) => {
        const dimensionKey = getBasicDimmensionsKeys(
          item.data_quality_status?.dimensions,
          dimType
        );
        const currentSeverity = (item.data_quality_status?.dimensions ?? {})[
          dimensionKey as any
        ]?.current_severity;
        const lastCheckExecutedAt = (item.data_quality_status?.dimensions ??
          {})?.[dimensionKey as any]?.last_check_executed_at;
        const severityColor = getColor(currentSeverity as any);
        const hasNoSeverity = severityColor.length === 0;

        const dimensionsClassNames = clsx('w-3 h-3', {
          'bg-gray-150': hasNoSeverity && lastCheckExecutedAt,
          [severityColor]: !hasNoSeverity,
          'border border-gray-150': hasNoSeverity
        });
        return (
          <td key={`Dimensionindex${dimType}`}>
            <Tooltip
              content={renderSecondLevelTooltip(
                (item.data_quality_status?.dimensions ?? {})?.[
                  dimensionKey as any
                ] ?? {
                  dimension: dimType
                }
              )}
            >
              <div
                className={dimensionsClassNames}
                style={{ borderRadius: '6px' }}
              />
            </Tooltip>
          </td>
        );
      })}
      {getAdditionalDimentionsKeys(
        item.data_quality_status?.dimensions ?? {}
      ).map((dimensionKey: string, dimIndex) => {
        return (
          <td key={`DimensionTooltipindex${dimIndex}`}>
            <Tooltip
              content={renderSecondLevelTooltip(
                (item.data_quality_status?.dimensions ?? {})?.[
                  dimensionKey as any
                ]
              )}
            >
              <div
                className={clsx(
                  'w-3 h-3',
                  getColor(
                    (item.data_quality_status?.dimensions ?? {})?.[
                      dimensionKey as any
                    ]?.current_severity as
                      | DimensionCurrentDataQualityStatusModelCurrentSeverityEnum
                      | undefined
                  ).length === 0
                    ? 'bg-gray-150'
                    : getColor(
                        (item.data_quality_status?.dimensions ?? {})?.[
                          dimensionKey as any
                        ]?.current_severity as
                          | DimensionCurrentDataQualityStatusModelCurrentSeverityEnum
                          | undefined
                      )
                )}
                style={{ borderRadius: '6px' }}
              />
            </Tooltip>
          </td>
        );
      })}
    </>
  );
}

const renderSecondLevelTooltip = (
  data: DimensionCurrentDataQualityStatusModel | undefined
) => {
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
