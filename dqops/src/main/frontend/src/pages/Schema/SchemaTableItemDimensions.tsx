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
  item,
  dimensionKeys
}: {
  item: TableListModel;
  dimensionKeys: string[];
}) {
  const getBasicDimmensionsKeys = (dimentions: any, type: string) => {
    const basicDimensions = Object.keys(dimentions ?? {})?.find(
      (x) => x === type
    );
    return basicDimensions;
  };
  const basicDimensionTypes = ['Completeness', 'Validity', 'Consistency'];

  const getAdditionalDimentionsKeys = (dimentions: any) => {
    const additionalDimensions =
      Object.keys(dimentions ?? {})?.filter(
        (x) => !basicDimensionTypes.includes(x)
      ) ?? [];

    const arr: (string | undefined)[] = [];
    dimensionKeys.forEach((item) => {
      if (additionalDimensions.includes(item)) {
        arr.push(item);
      } else {
        arr.push(undefined);
      }
    });
    return arr;
  };

  return (
    <>
      {' '}
      <td className="pl-4 text-[10px]">
        <div
          className={clsx(
            'w-[35px] h-4.5 text-center flex items-center justify-center',
            getColor(item.data_quality_status?.current_severity)
          )}
        >
          {item.data_quality_status?.data_quality_kpi !== undefined
            ? Math.round(item.data_quality_status?.data_quality_kpi) + '%'
            : ''}
        </div>
      </td>
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

        const dimensionsClassNames = clsx(
          'w-[35px] h-4.5 text-center flex items-center justify-center text-[10px]',
          {
            'bg-gray-150': hasNoSeverity && lastCheckExecutedAt,
            [severityColor]: !hasNoSeverity
            // 'border border-gray-150': hasNoSeverity
          }
        );
        if (
          (item.data_quality_status?.dimensions ?? {})?.[dimensionKey as any]
        ) {
          return (
            <td key={`Dimensionindex${dimType}`} className="pl-4">
              <Tooltip
                content={renderSecondLevelTooltip(
                  (item.data_quality_status?.dimensions ?? {})?.[
                    dimensionKey as any
                  ] ?? {
                    dimension: dimType
                  }
                )}
              >
                <div className={dimensionsClassNames}>
                  {(item.data_quality_status?.dimensions ?? {})?.[
                    dimensionKey as any
                  ]?.data_quality_kpi !== undefined
                    ? Math.round(
                        Number(
                          (item.data_quality_status?.dimensions ?? {})?.[
                            dimensionKey as any
                          ].data_quality_kpi
                        )
                      ) + '%'
                    : ''}
                </div>
              </Tooltip>
            </td>
          );
        } else {
          return (
            <td key={`Dimensionindex${dimType}`} className="pl-4">
              <div className="w-[35px]"></div>
            </td>
          );
        }
      })}
      {getAdditionalDimentionsKeys(
        item.data_quality_status?.dimensions ?? {}
      ).map((dimensionKey: string | undefined, dimIndex) => {
        if (dimensionKey) {
          return (
            <td key={`DimensionTooltipindex${dimIndex}`} className="pl-4">
              <Tooltip
                content={renderSecondLevelTooltip(
                  (item.data_quality_status?.dimensions ?? {})?.[
                    dimensionKey as any
                  ]
                )}
              >
                <div
                  className={clsx(
                    'w-[35px] h-4.5 text-[10px] text-center flex items-center justify-center',
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
                >
                  {(item.data_quality_status?.dimensions ?? {})?.[
                    dimensionKey as any
                  ]?.data_quality_kpi !== undefined
                    ? Math.round(
                        Number(
                          (item.data_quality_status?.dimensions ?? {})?.[
                            dimensionKey as any
                          ].data_quality_kpi
                        )
                      ) + '%'
                    : ''}
                </div>
              </Tooltip>
            </td>
          );
        } else {
          return <td key={`DimensionTooltipindex${dimIndex}`}></td>;
        }
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
          <div className="w-49">Quality dimension:</div>
          <div>{data?.dimension}</div>
        </div>
        <div className="flex gap-x-2">
          <div className="w-49">Data quality KPI:</div>
          <div>
            {data?.data_quality_kpi ? data?.data_quality_kpi + '%' : ''}
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
