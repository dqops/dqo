import { Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import React from 'react';
import { DimensionCurrentDataQualityStatusModelCurrentSeverityEnum } from '../../../api';
import { getDimensionColor } from '../../Connection/TableView/TableQualityStatus/TableQualityStatusUtils';
import QualityDimensionTooltip from './QualityDimensionTooltip';

export default function QualityDimensionStatuses({
  dimensions
}: {
  dimensions: any;
}) {
  const getBasicDimmensionsKeys = (type: string) => {
    const basicDimensions = Object.keys(dimensions ?? {})?.find(
      (x) => x === type
    );
    return basicDimensions;
  };
  const basicDimensionTypes = ['Completeness', 'Validity', 'Consistency'];

  const getAdditionalDimentionsKeys = () => {
    return (
      Object.keys(dimensions ?? {})?.filter(
        (x) => !basicDimensionTypes.includes(x)
      ) ?? []
    );
  };

  return (
    <div className="flex items-center gap-x-0.5">
      {basicDimensionTypes.map((dimType) => {
        const dimensionKey = getBasicDimmensionsKeys(dimType);
        const currentSeverity =
          dimensions?.[dimensionKey as any]?.current_severity;
        const lastCheckExecutedAt =
          dimensions?.[dimensionKey as any]?.last_check_executed_at;
        const severityColor = getDimensionColor(currentSeverity as any);
        const hasNoSeverity = severityColor.length === 0;

        const dimensionsClassNames = clsx('w-3 h-3 border border-gray-150', {
          'bg-gray-150': hasNoSeverity && lastCheckExecutedAt,
          [severityColor]: !hasNoSeverity
        });
        return (
          <Tooltip
            key={`Dimensionindex${dimType}`}
            content={
              <QualityDimensionTooltip
                data={dimensions?.[dimensionKey as any]}
              />
            }
          >
            <div
              className={dimensionsClassNames}
              style={{ borderRadius: '6px' }}
            />
          </Tooltip>
        );
      })}
      {getAdditionalDimentionsKeys().map((dimensionKey: string, dimIndex) => {
        return (
          <Tooltip
            key={`DimensionTooltipindex${dimIndex}`}
            content={
              <QualityDimensionTooltip
                data={dimensions?.[dimensionKey as any]}
              />
            }
          >
            <div
              className={clsx(
                'w-3 h-3 border border-gray-150',
                getDimensionColor(
                  dimensions?.[dimensionKey as any]?.current_severity as
                    | DimensionCurrentDataQualityStatusModelCurrentSeverityEnum
                    | undefined
                ).length === 0
                  ? 'bg-gray-150'
                  : getDimensionColor(
                      dimensions?.[dimensionKey as any]?.current_severity as
                        | DimensionCurrentDataQualityStatusModelCurrentSeverityEnum
                        | undefined
                    )
              )}
              style={{ borderRadius: '6px' }}
            />
          </Tooltip>
        );
      })}
    </div>
  );
}
