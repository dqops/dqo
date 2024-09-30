import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import clsx from 'clsx';
import React from 'react';
import {
  CheckResultsOverviewDataModelStatusesEnum,
  DimensionCurrentDataQualityStatusModel,
  TableLineageFlowModel
} from '../../api';
import QualityDimensionTooltip from '../DataQualityChecks/QualityDimension/QualityDimensionTooltip';
import SvgIcon from '../SvgIcon';

export default function DataLineageDetailsDialog({
  isOpen,
  onClose,
  flow
}: {
  isOpen: boolean;
  onClose: () => void;
  flow: TableLineageFlowModel;
}) {
  return (
    <Dialog
      open={isOpen}
      handler={() => onClose()}
      className="!z-[99999] mb-2"
      style={{ width: '76.5%', minWidth: '76.5%' }}
    >
      <DialogBody>
        <div
          className="p-2 text-sm text-black flex gap-4 "
          style={{
            maxHeight: '70vh',
            maxWidth: '90vw',
            overflowY: 'auto',
            overflowX: 'auto'
          }}
        >
          {/* Upstream Combined Quality Status */}
          <div className="min-w-[360px]">
            <div className="flex items-center justify-between py-1">
              <div className="pb-2">Upstream combined quality status</div>
            </div>
            {flow.upstream_combined_quality_status?.dimensions && (
              <div>
                {renderDimensions(
                  flow.upstream_combined_quality_status.dimensions
                )}
              </div>
            )}
          </div>

          {/* Source Table Quality Status */}
          <div className="min-w-[360px]">
            <div className="flex items-center py-1">
              <div className="pb-2">Source table quality status</div>
            </div>
            {flow.source_table_quality_status?.dimensions && (
              <div>
                {renderDimensions(flow.source_table_quality_status.dimensions)}
              </div>
            )}
          </div>

          {/* Target Table Quality Status */}
          <div className="min-w-[360px]">
            <div className="flex items-center py-1">
              <div className="pb-2">Target table quality status</div>
            </div>
            {flow.target_table_quality_status?.dimensions && (
              <div>
                {renderDimensions(flow.target_table_quality_status.dimensions)}
              </div>
            )}
          </div>
        </div>
      </DialogBody>
      <DialogFooter>
        <></>
      </DialogFooter>
    </Dialog>
  );
}

const renderDimensions = (dimensions: {
  [key: string]: DimensionCurrentDataQualityStatusModel;
}) => {
  return (
    <div className="mb-6">
      {Object.keys(dimensions).map((key) => (
        <div key={key}>{renderDimensionItem(key, dimensions[key])}</div>
      ))}
    </div>
  );
};

const renderDimensionItem = (
  dimensionKey: string,
  value: DimensionCurrentDataQualityStatusModel | undefined
) => {
  const [isExpanded, setIsExpanded] = React.useState(false);

  return (
    <div className="mb-4">
      <div className="w-60 flex items-center">
        <SvgIcon
          name={isExpanded ? 'chevron-down' : 'chevron-right'}
          onClick={() => setIsExpanded((v) => !v)}
          className="w-4 h-4"
        />
        <div
          className={clsx('w-3 h-3 mr-1', getColor(value?.current_severity))}
        />
        {dimensionKey}:
        {' ' + value?.data_quality_kpi !== undefined &&
        Number(value?.data_quality_kpi)
          ? Number(value?.data_quality_kpi).toFixed(2) + ' %'
          : ' -'}
      </div>
      <br />
      {isExpanded && (
        <div className="ml-1">
          <QualityDimensionTooltip data={value ?? {}} />{' '}
        </div>
      )}
    </div>
  );
};

const getColor = (
  status: CheckResultsOverviewDataModelStatusesEnum | undefined
) => {
  switch (status) {
    case 'valid':
      return 'bg-teal-500';
    case 'warning':
      return 'bg-yellow-900';
    case 'error':
      return 'bg-orange-900';
    case 'fatal':
      return 'bg-red-900';
    case 'execution_error':
      return 'bg-black';
    default:
      return 'bg-black';
  }
};
