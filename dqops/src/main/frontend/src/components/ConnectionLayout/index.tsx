import React, { ReactNode, useEffect, useMemo } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {
  useHistory,
  useLocation,
  useRouteMatch
} from 'react-router-dom';
import {
  closeFirstLevelTab,
  setActiveFirstLevelTab
} from '../../redux/actions/source.actions';
import { IRootState } from '../../redux/reducers';
import { CheckTypes, ROUTES } from '../../shared/routes';
import MainLayout from '../MainLayout';
import PageTabs from '../PageTabs';
import { TabOption } from '../PageTabs/tab';

import { useTree } from '../../contexts/treeContext';
import ChecksPage from '../../pages/Checks';
import ColumnDailyMonitoringChecksView from '../../pages/ColumnDailyMonitoringChecksView';
import ColumnDailyPartitionedChecksView from '../../pages/ColumnDailyPartitionedChecksView';
import ColumnMonitoringChecksUIFilterView from '../../pages/ColumnMonitoringChecksUIFilterView';
import ColumnMonthlyMonitoringChecksView from '../../pages/ColumnMonthlyMonitoringChecksView';
import ColumnMonthlyPartitionedChecksView from '../../pages/ColumnMonthlyPartitionedChecksView';
import ColumnPartitionedChecksUIFilterView from '../../pages/ColumnPartitionedChecksUIFilterView';
import ColumnProfilingChecksUIFilterView from '../../pages/ColumnProfilingChecksUIFilterView';
import ColumnProfilingChecksView from '../../pages/ColumnProfilingChecksView';
import ColumnView from '../../pages/ColumnView';
import ConnectionPage from '../../pages/Connection';
import CreateConnection from '../../pages/CreateConnection';
import SchemaPage from '../../pages/Schema';
import TablePage from '../../pages/Table';
import TableColumnsView from '../../pages/TableColumnsView';
import TableDailyChecksView from '../../pages/TableDailyMonitoringChecksView';
import TableDailyPartitionedChecksView from '../../pages/TableDailyPartitionedChecksView';
import TableIncidents from '../../pages/TableIncidents';
import TableMonitoringChecksUIFilterView from '../../pages/TableMonitoringChecksUIFilterView';
import TableMonthlyChecksView from '../../pages/TableMonthlyMonitoringChecksView';
import TableMonthlyPartitionedChecksView from '../../pages/TableMonthlyPartitionedChecksView';
import TablePartitionedChecksUIFilterView from '../../pages/TablePartitionedChecksUIFilterView';
import TableProfilingChecksUIFilterView from '../../pages/TableProfilingChecksUIFilterView';
import TableProfilingChecksView from '../../pages/TableProfilingChecksView';
import { getFirstLevelActiveTab } from '../../redux/selectors';
import { useDecodedParams } from '../../utils';
import ConfirmDialog from '../CustomTree/ConfirmDialog';

interface ConnectionLayoutProps {
  route: string;
}

const ConnectionLayout = ({ route }: ConnectionLayoutProps) => {
  const { checkTypes }: { checkTypes: CheckTypes } = useDecodedParams();
  const { objectNotFound, setObjectNotFound } = useTree();
  const { tabs: pageTabs, activeTab } = useSelector(
    (state: IRootState) => state.source[checkTypes || CheckTypes.SOURCES]
  );

  const dispatch = useDispatch();
  const history = useHistory();
  const location = useLocation();
  const match = useRouteMatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const handleChange = (tab: TabOption) => {
    dispatch(setActiveFirstLevelTab(checkTypes, tab.value));
    if (tab.url && tab.url !== location.pathname) {
      history.push(tab.url);
    }
  };

  const closeTab = (value: string) => {
    dispatch(closeFirstLevelTab(checkTypes, value));
    // console.log(value)
  };

  const tabOptions = useMemo(() => {
    return pageTabs.map((item) => ({
      value: item.value,
      url: item.url,
      label: item.label
    }));
  }, [pageTabs]);

  useEffect(() => {
    if (activeTab) {
      const activeUrl = pageTabs.find((item) => item.value === activeTab)?.url;
      // const { import_schema } = qs.parse(location.search);
      if (activeUrl && activeUrl !== location.pathname) {
        // if (match.path !== ROUTES.PATTERNS.CONNECTION && !import_schema) {
        history.push(activeUrl);
        // history.push(checkIfTabCouldExist(checkTypes, location.pathname) ? location.pathname : activeUrl);
        // }
      }
    }
  }, [activeTab]);
  // TODO Aleksy: fix checkIfTabCouldExist function with opening tabs with url.

  const getComponent = () => {
    switch (route) {
      case ROUTES.PATTERNS.QUALITY_CHECKS: 
        return <ChecksPage/>
      case ROUTES.PATTERNS.CREATE:
        return <CreateConnection/>;
      case ROUTES.PATTERNS.CONNECTION:
        return <ConnectionPage/>;
      case ROUTES.PATTERNS.SCHEMA:
        return <SchemaPage />;
      case ROUTES.PATTERNS.TABLE:
        return <TablePage />;
      case ROUTES.PATTERNS.TABLE_PROFILING:
        return <TableProfilingChecksView />;
      case ROUTES.PATTERNS.TABLE_PROFILING_FILTER:
        return <TableProfilingChecksUIFilterView />;
      case ROUTES.PATTERNS.TABLE_MONITORING_DAILY:
        return <TableDailyChecksView />;
      case ROUTES.PATTERNS.TABLE_MONITORING_MONTHLY:
        return <TableMonthlyChecksView />;
      case ROUTES.PATTERNS.TABLE_MONITORING_FILTER:
        return <TableMonitoringChecksUIFilterView />;
      case ROUTES.PATTERNS.TABLE_PARTITIONED_DAILY:
        return <TableDailyPartitionedChecksView />;
      case ROUTES.PATTERNS.TABLE_PARTITIONED_MONTHLY:
        return <TableMonthlyPartitionedChecksView />;
      case ROUTES.PATTERNS.TABLE_PARTITIONED_FILTER:
        return <TablePartitionedChecksUIFilterView />;
      case ROUTES.PATTERNS.TABLE_COLUMNS:
        return <TableColumnsView />;
      case ROUTES.PATTERNS.TABLE_INCIDENTS_NOTIFICATION:
        return <TableIncidents />;
      case ROUTES.PATTERNS.COLUMN:
        return <ColumnView />;
      case ROUTES.PATTERNS.COLUMN_PROFILING:
        return <ColumnProfilingChecksView />;
      case ROUTES.PATTERNS.COLUMN_PROFILING_FILTER:
        return <ColumnProfilingChecksUIFilterView />;
      case ROUTES.PATTERNS.COLUMN_MONITORING_DAILY:
        return <ColumnDailyMonitoringChecksView />;
      case ROUTES.PATTERNS.COLUMN_MONITORING_MONTHLY:
        return <ColumnMonthlyMonitoringChecksView />;
      case ROUTES.PATTERNS.COLUMN_MONITORING_FILTER:
        return <ColumnMonitoringChecksUIFilterView />;
      case ROUTES.PATTERNS.COLUMN_PARTITIONED_DAILY:
        return <ColumnDailyPartitionedChecksView />;
      case ROUTES.PATTERNS.COLUMN_PARTITIONED_MONTHLY:
        return <ColumnMonthlyPartitionedChecksView />;
      case ROUTES.PATTERNS.COLUMN_PARTITIONED_FILTER:
        return <ColumnPartitionedChecksUIFilterView />;
      default:
        return null;
    }
  };

  const renderComponent: ReactNode = getComponent();

  return (
    <MainLayout>
      <div className="h-full flex flex-col">
        <PageTabs
          tabs={tabOptions}
          activeTab={activeTab}
          onChange={handleChange}
          onRemoveTab={closeTab}
          limit={7}
        />
        <div className=" bg-white border border-gray-300 min-h-0 overflow-auto h-full w-full">
          {!!activeTab && pageTabs.length ? (
            <div className="w-full h-full">{renderComponent}</div>
          ) : null}
        </div>
      </div>
      <ConfirmDialog
        open={objectNotFound}
        onConfirm={() =>
          new Promise(() => {
            dispatch(closeFirstLevelTab(checkTypes, firstLevelActiveTab)),
              setObjectNotFound(false);
          })
        }
        isCancelExcluded={true}
        onClose={() => {
          dispatch(closeFirstLevelTab(checkTypes, firstLevelActiveTab)),
            setObjectNotFound(false);
        }}
        message="The definition of this object was deleted in the DQOps user home. The tab will be closed."
      />
    </MainLayout>
  );
};

export default ConnectionLayout;
