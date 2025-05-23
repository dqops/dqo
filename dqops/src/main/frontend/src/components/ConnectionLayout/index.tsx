import React, { ReactNode, useEffect, useMemo } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory, useLocation, useRouteMatch } from 'react-router-dom';
import {
  addFirstLevelTab,
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
import { setIsTabChanged, setJobAllert } from '../../redux/actions/job.actions';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../redux/selectors';
import {
  getIsAnyChecksEnabled,
  getRouteValidLabel,
  isValidRouteWithoutTab,
  useDecodedParams
} from '../../utils';
import ConfirmDialog from '../CustomTree/ConfirmDialog';

interface ConnectionLayoutProps {
  route: string;
}

const ConnectionLayout = ({ route }: ConnectionLayoutProps) => {
  const {
    checkTypes
  }: {
    checkTypes: CheckTypes;
  } = useDecodedParams();
  const { objectNotFound, setObjectNotFound, setActiveTab } = useTree();
  const { tabs: pageTabs, activeTab } = useSelector(
    (state: IRootState) => state.source[checkTypes || CheckTypes.SOURCES]
  );
  const { isTabChanged } = useSelector((state: IRootState) => state.job);
  const {
    dailyMonitoring,
    monthlyMonitoring,
    dailyPartitionedChecks,
    monthlyPartitionedChecks,
    checksUI
  } = useSelector(getFirstLevelState(checkTypes));

  const dispatch = useDispatch();
  const history = useHistory();
  const location = useLocation();
  const match = useRouteMatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const handleChange = (tab: TabOption) => {
    dispatch(setActiveFirstLevelTab(checkTypes, tab.value));
    dispatch(setIsTabChanged(true));

    if (tab.url && tab.url !== location.pathname) {
      history.push(tab.url);
    }
  };

  const closeTab = (value: string) => {
    if (pageTabs.length === 1) {
      setActiveTab(undefined);
      history.push(`/${checkTypes}`);
    }
    dispatch(setIsTabChanged(true));
    dispatch(closeFirstLevelTab(checkTypes, value));
  };

  const tabOptions = useMemo(() => {
    return pageTabs.map((item) => ({
      value: item.value,
      url: item.url,
      label: item.label
    }));
  }, [pageTabs]);

  const getChecksConfigurated = (activeUrl: string) => {
    const tab = activeUrl.split('/')[activeUrl.split('/').length - 1];
    switch (checkTypes) {
      case CheckTypes.PROFILING: {
        if (tab !== 'advanced') return;
        if (checksUI && getIsAnyChecksEnabled(checksUI) === false) {
          dispatch(
            setJobAllert({
              activeTab,
              action: 'rule-mining',
              tooltipMessage:
                'No custom data quality rules configurated. Use the data quality rule miner to get a proposal of data quality checks.'
            })
          );
        }
        break;
      }
      case CheckTypes.MONITORING: {
        if (tab !== 'check-editor') return;
        if (
          (dailyMonitoring &&
            getIsAnyChecksEnabled(dailyMonitoring) === false) ||
          (monthlyMonitoring &&
            getIsAnyChecksEnabled(monthlyMonitoring) === false)
        ) {
          dispatch(
            setJobAllert({
              activeTab,
              action: 'rule-mining',
              tooltipMessage:
                'No custom data quality rules configurated. Use the data quality rule miner to get a proposal of data quality checks.'
            })
          );
        }
        break;
      }
      case CheckTypes.PARTITIONED: {
        if (tab !== 'check-editor') return;
        if (
          (dailyPartitionedChecks &&
            getIsAnyChecksEnabled(dailyPartitionedChecks) === false) ||
          (monthlyPartitionedChecks &&
            getIsAnyChecksEnabled(monthlyPartitionedChecks) === false)
        ) {
          dispatch(
            setJobAllert({
              activeTab,
              action: 'rule-mining',
              tooltipMessage:
                'No custom data quality rules configurated. Use the data quality rule miner to get a proposal of data quality checks.'
            })
          );
        }
        break;
      }
    }
  };
  useEffect(() => {
    setIsTabChanged(false);
    if (activeTab) {
      const activeUrl = pageTabs.find((item) => item.value === activeTab)?.url;
      getChecksConfigurated(activeUrl ?? activeTab);

      if (activeUrl && activeUrl !== location.pathname && isTabChanged) {
        history.push(activeUrl);
        return;
      }
    }

    const foundRoute = Object.values(ROUTES.PATTERNS).find(
      (value) => value === match.path
    );

    if (foundRoute) {
      const params = match.params as any;
      if (params?.tab || isValidRouteWithoutTab(foundRoute)) {
        let newRoute = foundRoute;
        let routeWithoutTab = foundRoute;
        const segments = foundRoute.split('/');
        if (segments[segments.length - 1] === ':tab') {
          routeWithoutTab = segments.slice(0, -1).join('/');
        }

        Object.entries(params).forEach(([key, value]) => {
          newRoute = newRoute.replace(`:${key}`, String(value));
          routeWithoutTab = routeWithoutTab.replace(`:${key}`, String(value));
        });
        dispatch(
          addFirstLevelTab(checkTypes, {
            url: newRoute,
            value: routeWithoutTab,
            label: getRouteValidLabel(routeWithoutTab)
          })
        );
      }
    }
  }, [location.pathname, isTabChanged, activeTab]);

  // TODO Aleksy: fix checkIfTabCouldExist function with opening tabs with url.

  const getComponent = () => {
    switch (route) {
      case ROUTES.PATTERNS.QUALITY_CHECKS:
        return <ChecksPage />;
      case ROUTES.PATTERNS.CREATE:
        return <CreateConnection />;
      case ROUTES.PATTERNS.CONNECTION:
        return <ConnectionPage />;
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
            setObjectNotFound(false), closeTab(firstLevelActiveTab);
          })
        }
        isCancelExcluded={true}
        onClose={() => {
          setObjectNotFound(false), closeTab(firstLevelActiveTab);
        }}
        message="The definition of this object was deleted in the DQOps user home. The tab will be closed."
      />
    </MainLayout>
  );
};

export default ConnectionLayout;
