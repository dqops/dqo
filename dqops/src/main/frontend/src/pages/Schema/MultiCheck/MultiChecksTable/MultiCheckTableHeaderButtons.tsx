import React from 'react';
import Button from '../../../../components/Button';
import { CheckTemplate } from '../../../../api';

type TMultiChecksTableButtons = {
  selectAll: () => void;
  deselectAll: () => void;
  selectedData: CheckTemplate[];
  checks: CheckTemplate[] | undefined;
  setAction: any;
};

export const MultiChecksTableButtons = ({
  selectAll,
  deselectAll,
  selectedData,
  checks,
  setAction
}: TMultiChecksTableButtons) => (
  <div className="flex justify-between gap-4">
    {checks && checks.length > 0 ? (
      <div className="flex gap-x-4">
        <Button
          className="text-sm py-2.5"
          label="Select all"
          onClick={selectAll}
          color={selectedData === checks ? 'secondary' : 'primary'}
        />
        <Button
          className="text-sm py-2.5"
          label="Unselect all"
          onClick={deselectAll}
          color={selectedData.length === 0 ? 'secondary' : 'primary'}
        />
      </div>
    ) : (
      <div />
    )}

    <div className="flex gap-x-4">
      <Button
        className="text-sm py-2.5"
        label={
          !selectedData.length
            ? 'Activate for all matching filter'
            : 'Update for selected'
        }
        color="primary"
        onClick={() => setAction('bulkEnabled')}
      />
      <Button
        className="text-sm py-2.5"
        label={
          !selectedData.length
            ? 'Deactivate for all matching filter'
            : 'Deactivate selected'
        }
        color="primary"
        onClick={() => setAction('bulkDisabled')}
      />
    </div>
  </div>
);

type TCheckTarget = {
  checkTarget: 'table' | 'column' | undefined;
};

export const MultiChecksTableHeader = ({ checkTarget }: TCheckTarget) => {
  return (
    <thead>
      <tr>
        <th></th>
        <th className="px-4 py-2 text-left">Check Name</th>
        <th className="px-4 py-2 text-left">Check Category</th>
        <th className="px-4 py-2 text-left">Table</th>
        {checkTarget === 'column' && (
          <th className="px-4 py-2 text-left">Column</th>
        )}
        <th className="px-4 py-2 text-left">Sensor parameters</th>
        <th className="px-4 py-2 text-left">Warning threshold</th>
        <th className="px-4 py-2 text-left">Error threshold</th>
        <th className="px-4 py-2 text-left">Fatal threshold</th>
      </tr>
    </thead>
  );
};
