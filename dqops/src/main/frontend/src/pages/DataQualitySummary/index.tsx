import React from 'react';
import Tabs from '../../components/Tabs';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setHomeSecondLevelTab } from '../../redux/actions/source.actions';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import ColumnListView from '../ColumnListView/ColumnListView';
import TableListView from '../TableListView/TableListView';
const tabs = [
  {
    label: 'Tables',
    value: 'tables'
  },
  {
    label: 'Columns',
    value: 'columns'
  }
];
export default function DataQualitySummary({
  secondTab
}: {
  secondTab?: string;
}) {
  const { checkTypes }: { checkTypes: CheckTypes } = useDecodedParams();
  const [activeTab, setActiveTab] = React.useState(secondTab ?? 'tables');
  const dispatch = useActionDispatch();
  const onChangeTab = (value: string) => {
    if (!checkTypes) {
      dispatch(setHomeSecondLevelTab(value));
    }
    setActiveTab(value);
  };

  // useEffect(() => {
  //   if (secondTab && secondTab !== activeTab) {
  //     setActiveTab(secondTab);
  //   }
  // }, [secondTab]);

  return (
    <div className="py-2">
      <div className="border-b border-gray-300 px-0">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
      </div>
      {checkTypes === CheckTypes.PROFILING && (
        <div className="px-4 pt-4 pb-6 text-sm">
          Data quality KPIs are calculated only from the Profiling checks. These
          KPI results are not included in the overall Table and Column summaries
          on the Home screen.
        </div>
      )}
      {checkTypes === CheckTypes.MONITORING && (
        <div className="px-4 pt-4 pb-6 text-sm">
          Data quality KPIs are calculated only from the Monitoring checks.
        </div>
      )}
      {checkTypes === CheckTypes.PARTITIONED && (
        <div className="px-4 pt-4 pb-6 text-sm">
          Data quality KPIs are calculated only from the Partition checks.
        </div>
      )}
      {activeTab === 'tables' && <TableListView />}
      {activeTab === 'columns' && <ColumnListView />}
    </div>
  );
}
