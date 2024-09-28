import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import React from 'react';
import {
  DimensionCurrentDataQualityStatusModel,
  TableLineageFlowModel
} from '../../api';
import QualityDimensionTooltip from '../DataQualityChecks/QualityDimension/QualityDimensionTooltip';

export default function DataLineageDetailsDialog({
  isOpen,
  onClose,
  flow
}: {
  isOpen: boolean;
  onClose: () => void;
  flow: TableLineageFlowModel;
}) {
  console.log(flow.source_table_quality_status?.dimensions);
  return (
    <Dialog open={isOpen} handler={() => onClose()} className="!z-[99999]">
      <DialogBody>
        <div
          className="p-2 text-sm text-black flex flex-col gap-4"
          style={{
            maxHeight: '70vh',
            maxWidth: '90vw',
            overflowY: 'auto',
            overflowX: 'auto'
          }}
        >
          {/* Upstream Combined Quality Status */}
          <div className="min-w-[300px]">
            <div className="flex items-center py-1">
              <div className="w-60">Upstream combined quality status</div>
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
          <div className="min-w-[300px]">
            <div className="flex items-center py-1">
              <div className="w-60">Source table quality status</div>
            </div>
            {flow.source_table_quality_status?.dimensions && (
              <div>
                {renderDimensions(flow.source_table_quality_status.dimensions)}
              </div>
            )}
          </div>

          {/* Target Table Quality Status */}
          <div className="min-w-[300px]">
            <div className="flex items-center py-1">
              <div className="w-60">Target table quality status</div>
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
      {/* <div>Dimensions:</div> */}
      {Object.keys(dimensions).map((key) => (
        <div key={key} className="mb-4">
          <div className="w-60">{key}:</div>
          <br />
          <QualityDimensionTooltip data={dimensions[key] ?? {}} />
        </div>
      ))}
    </div>
  );
};
