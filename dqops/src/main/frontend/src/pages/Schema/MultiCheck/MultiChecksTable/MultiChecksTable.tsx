import React, { useState } from 'react';
import { CheckModel, CheckTemplate } from '../../../../api';
import Button from '../../../../components/Button';
import { isEqual } from 'lodash';
import { UpdateCheckModel } from '../../UpdateCheckModel';
import MultiChecksTableItem from './MultiChecksTableItem';
import { IFilterTemplate } from '../../../../shared/constants';

type TMultiChecksTable = {
  checkTarget: 'column' | 'table' | undefined;
  checks: CheckTemplate[] | undefined;
  filterParameters: IFilterTemplate;
  selectedCheckModel: CheckModel;
};

export default function MultiChecksTable({
  checkTarget,
  checks,
  filterParameters,
  selectedCheckModel
}: TMultiChecksTable) {
  const [selectedData, setSelectedData] = useState<CheckTemplate[]>([]);
  const [action, setAction] = useState<'bulkEnabled' | 'bulkDisabled'>();
  //const [selectedCheck, setSelectedCheck] = useState<CheckTemplate>(); // TODO: this component is fundamentally wrong, it should be editing a CheckTemplate (a clone of the check template), not a CheckTemplate. CheckTemplate is a template of parameters to apply on all checks (for bulk), while the CheckTemplate is a current configuration of the check on one table or one column (current configuration)
  // TODO: change it to ChangeTemplate, and change the selected  check template (that will be edited) when user changes a check in the combo box for selecting a check

  const selectAll = () => {
    setSelectedData(checks || []);
  };

  const deselectAll = () => {
    setSelectedData([]);
  };

  const onChangeSelection = (check: CheckTemplate) => {
    if (selectedData.find((item) => isEqual(item, check))) {
      setSelectedData(selectedData.filter((item) => !isEqual(item, check)));
    } else {
      // setSelectedCheck(check);
      setSelectedData([...selectedData, check]);
    }
  };
  return (
    <div className="border border-gray-300 rounded-lg p-4 my-4">
      <div className="flex justify-between gap-4">
        <div className="flex gap-x-4">
          <Button
            className="text-sm py-2.5"
            label="Select All"
            color="primary"
            onClick={selectAll}
          />
          <Button
            className="text-sm py-2.5"
            label="Unselect All"
            color={!selectedData.length ? 'secondary' : 'primary'}
            onClick={deselectAll}
          />
        </div>
        <div className="flex gap-x-4">
          <Button
            className="text-sm py-2.5"
            label={!selectedData.length ? 'Update all' : 'Update selected'}
            color="primary"
            onClick={() => setAction('bulkEnabled')}
          />
          <Button
            className="text-sm py-2.5"
            label={!selectedData.length ? 'Disable all' : 'Disable selected'}
            color="primary"
            onClick={() => setAction('bulkDisabled')}
          />
        </div>
      </div>
      <table className="w-full mt-8">
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
        {filterParameters.checkCategory && filterParameters.checkName && (
          <tbody>
            {checks?.map((check, index) => (
              <MultiChecksTableItem
                checkTarget={filterParameters.checkTarget}
                check={check}
                key={index}
                checked={selectedData.includes(check)}
                onChangeSelection={onChangeSelection}
              />
            ))}
          </tbody>
        )}
      </table>
      <UpdateCheckModel // TODO: this component is fundamentally wrong, it should be editing a CheckTemplate (a clone of the check template), not a CheckTemplate. CheckTemplate is a template of parameters to apply on all checks (for bulk), while the CheckTemplate is a current configuration of the check on one table or one column (current configuration)
        open={action !== undefined}
        action={action ?? 'bulkEnabled'}
        onClose={() => setAction(undefined)}
        selectedCheckModel={selectedCheckModel}
        filterParameters={filterParameters}
        selectedData={selectedData}
        // onSubmit={onChangeSelectedData}
      />
    </div>
  );
}
