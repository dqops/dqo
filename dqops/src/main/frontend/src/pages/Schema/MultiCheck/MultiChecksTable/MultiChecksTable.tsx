import React, { useState } from 'react';
import { CheckConfigurationModel, CheckTemplate } from '../../../../api';
import Button from '../../../../components/Button';
import { isEqual } from 'lodash';
import { UpdateCheckModel } from '../../UpdateCheckModel';
import MultiChecksTableItem from './MultiChecksTableItem';
import { IFilterTemplate } from '../../../../shared/constants';

type TMultiChecksTable = {
  checkTarget: 'column' | 'table' | undefined;
  checks: CheckConfigurationModel[] | undefined;
  filterParameters: IFilterTemplate;
};

export default function MultiChecksTable({
  checkTarget,
  checks,
  filterParameters
}: TMultiChecksTable) {
  const [selectedData, setSelectedData] = useState<CheckConfigurationModel[]>(
    []
  );
  const [action, setAction] = useState<'bulkEnabled' | 'bulkDisabled'>();
  const [selectedCheck, setSelectedCheck] = useState<CheckTemplate>(); // TODO: this component is fundamentally wrong, it should be editing a CheckTemplate (a clone of the check template), not a CheckConfigurationModel. CheckTemplate is a template of parameters to apply on all checks (for bulk), while the CheckConfigurationModel is a current configuration of the check on one table or one column (current configuration)
  // TODO: change it to ChangeTemplate, and change the selected  check template (that will be edited) when user changes a check in the combo box for selecting a check

  const selectAll = () => {
    setSelectedData(checks || []);
  };

  const deselectAll = () => {
    setSelectedData([]);
  };

  const onChangeSelection = (check: CheckConfigurationModel) => {
    if (selectedData.find((item) => isEqual(item, check))) {
      setSelectedData(selectedData.filter((item) => !isEqual(item, check)));
    } else {
      setSelectedCheck(check);
      setSelectedData([...selectedData, check]);
    }
  };
  // const onChangeSelectedData = (check: CheckConfigurationModel) => {
  //   setSelectedData(
  //     selectedData.map((item) =>
  //       check.check_name === item.check_name ? check : item
  //     )
  //   );
  // };

  return (
    <div className="border border-gray-300 rounded-lg p-4 my-4">
      <div className="flex justify-end gap-4">
        <Button
          className="text-sm py-2.5"
          label="Select All"
          color="primary"
          onClick={selectAll}
        />
        <Button
          className="text-sm py-2.5"
          label="Unselect All"
          color="secondary"
          onClick={deselectAll}
        />
        <Button
          className="text-sm py-2.5"
          label="Update selected"
          disabled={!selectedData.length}
          color="primary"
          onClick={() => setAction('bulkEnabled')}
        />
        <Button
          className="text-sm py-2.5"
          label="Update all"
          color="primary"
          onClick={() => setAction('bulkEnabled')}
        />
        <Button
          className="text-sm py-2.5"
          label="Disable selected"
          color="primary"
          disabled={!selectedData.length}
          onClick={() => setAction('bulkDisabled')}
        />
        <Button
          className="text-sm py-2.5"
          label="Disable all"
          variant="outlined"
          color="primary"
          onClick={() => setAction('bulkEnabled')}
        />
      </div>
      <table className="w-full mt-8">
        <thead>
          <tr>
            <th className="px-4 py-2 text-left"></th>
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
      </table>
      <UpdateCheckModel // TODO: this component is fundamentally wrong, it should be editing a CheckTemplate (a clone of the check template), not a CheckConfigurationModel. CheckTemplate is a template of parameters to apply on all checks (for bulk), while the CheckConfigurationModel is a current configuration of the check on one table or one column (current configuration)
        open={action !== undefined}
        action={action ?? 'bulkEnabled'}
        onClose={() => setAction(undefined)}
        check={selectedCheck}
        // onSubmit={onChangeSelectedData}
      />
    </div>
  );
}
