import React from 'react';
import Button from '../../../../components/Button';
import { CheckTemplate } from '../../../../api';
import SvgIcon from '../../../../components/SvgIcon';

type TMultiChecksTableButtons = {
  selectAll: () => void;
  deselectAll: () => void;
  selectedData: CheckTemplate[];
  checks: CheckTemplate[] | undefined;
  setAction: any;
  loading: boolean;
};

export const MultiChecksTableButtons = ({
  selectAll,
  deselectAll,
  selectedData,
  checks,
  setAction,
  loading
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

    <div className="flex gap-x-4 absolute right-12">
      {loading ? (
        <SvgIcon
          className="w-4 min-w-4 cursor-pointer shrink-0 animate-spin"
          name="sync"
        />
      ) : null}
      <Button
        className="text-sm py-2.5"
        label={
          !selectedData.length
            ? 'Activate for all matching filter'
            : 'Activate selected'
        }
        color="primary"
        onClick={() => setAction('bulkEnabled')}
        disabled={loading}
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
        disabled={loading}
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
        <th className="pl-12 py-2 text-left">Active</th>
        <th className="px-4 py-2 text-left">Check name</th>
        <th className="px-4 py-2 text-left">Check category</th>
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
