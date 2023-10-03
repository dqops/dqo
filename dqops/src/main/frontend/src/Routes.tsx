import TableProfilingChecksView from "./pages/TableProfilingChecksView";

import React from 'react';

import { Route, Switch, Redirect } from 'react-router';

import CreateConnection from './pages/CreateConnection';
import ChecksPage from './pages/Checks';
import HomePage from './pages/Home';
import Dashboards from './pages/Dashboards';
import ConnectionPage from "./pages/Connection";
import SchemaPage from "./pages/Schema";
import TablePage from "./pages/Table";
import TableDailyChecksView from "./pages/TableDailyMonitoringChecksView";
import TableMonthlyChecksView from "./pages/TableMonthlyMonitoringChecksView";
import TableDailyPartitionedChecksView from "./pages/TableDailyPartitionedChecksView";
import TableMonthlyPartitionedChecksView from "./pages/TableMonthlyPartitionedChecksView";
import TableProfilingChecksUIFilterView from "./pages/TableProfilingChecksUIFilterView";
import TableColumnsView from "./pages/TableColumnsView";
import ColumnView from "./pages/ColumnView";
import ColumnProfilingChecksView from "./pages/ColumnProfilingChecksView";
import ColumnProfilingChecksUIFilterView from "./pages/ColumnProfilingChecksUIFilterView";
import ColumnDailyMonitoringChecksView from "./pages/ColumnDailyMonitoringChecksView";
import ColumnMonitoringChecksUIFilterView from "./pages/ColumnMonitoringChecksUIFilterView";
import ColumnMonthlyMonitoringChecksView from "./pages/ColumnMonthlyMonitoringChecksView";
import ColumnDailyPartitionedChecksView from "./pages/ColumnDailyPartitionedChecksView";
import ColumnMonthlyPartitionedChecksView from "./pages/ColumnMonthlyPartitionedChecksView";
import ColumnPartitionedChecksUIFilterView from "./pages/ColumnPartitionedChecksUIFilterView";
import TableMonitoringChecksUIFilterView from "./pages/TableMonitoringChecksUIFilterView";
import TablePartitionedChecksUIFilterView from "./pages/TablePartitionedChecksUIFilterView";
import { ROUTES } from "./shared/routes";
import Definitions from "./pages/Definitions";
import SensorDetail from "./pages/SensorDetail";
import RuleDetail from "./pages/RuleDetail";
import Incidents from "./pages/Incidents";
import IncidentConnection from "./pages/IncidentConnection";
import IncidentDetail from "./pages/IncidentDetail";
import NotFound from "./pages/NotFound";
import TableIncidents from "./pages/TableIncidents";
import CheckDetail from "./pages/CheckDetail";
import DefaultCheckDetail from "./pages/DefaultChecksDetail"
import UserListDetail from "./pages/UserListDetail";
import UserDetail from "./pages/UserListDetail/UserDetail";
import DefaultSchedules from "./pages/DefaultSchedulesDetail";

const Routes = () => {
  return (
    <Switch>
      <Route exact path={ROUTES.PATTERNS.CREATE} component={CreateConnection} />
      <Route exact path={ROUTES.PATTERNS.CONNECTION} component={ConnectionPage} />
      <Route exact path={ROUTES.PATTERNS.SCHEMA} component={SchemaPage} />
      <Route exact path={ROUTES.PATTERNS.TABLE} component={TablePage} />
      <Route exact path={ROUTES.PATTERNS.TABLE_PROFILING} component={TableProfilingChecksView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_PROFILING_FILTER} component={TableProfilingChecksUIFilterView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_MONITORING_DAILY} component={TableDailyChecksView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_MONITORING_MONTHLY} component={TableMonthlyChecksView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_MONITORING_FILTER} component={TableMonitoringChecksUIFilterView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_PARTITIONED_DAILY} component={TableDailyPartitionedChecksView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_PARTITIONED_MONTHLY} component={TableMonthlyPartitionedChecksView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_PARTITIONED_FILTER} component={TablePartitionedChecksUIFilterView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_COLUMNS} component={TableColumnsView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_INCIDENTS_NOTIFICATION} component={TableIncidents} />
      <Route exact path={ROUTES.PATTERNS.COLUMN} component={ColumnView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_PROFILING} component={ColumnProfilingChecksView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_PROFILING_FILTER} component={ColumnProfilingChecksUIFilterView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_MONITORING_DAILY} component={ColumnDailyMonitoringChecksView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_MONITORING_MONTHLY} component={ColumnMonthlyMonitoringChecksView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_MONITORING_FILTER} component={ColumnMonitoringChecksUIFilterView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_PARTITIONED_DAILY} component={ColumnDailyPartitionedChecksView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_PARTITIONED_MONTHLY} component={ColumnMonthlyPartitionedChecksView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_PARTITIONED_FILTER} component={ColumnPartitionedChecksUIFilterView} />
      <Route exact path={ROUTES.PATTERNS.HOME} component={HomePage} />
      <Route exact path={ROUTES.PATTERNS.DASHBOARDS} component={Dashboards} />
      <Route exact path={ROUTES.PATTERNS.SENSOR_DETAIL} component={SensorDetail} />
      <Route exact path={ROUTES.PATTERNS.RULE_DETAIL} component={RuleDetail} />
      <Route exact path={ROUTES.PATTERNS.CHECK_DETAIL} component={CheckDetail} />
      <Route exact path={ROUTES.PATTERNS.CHECK_DEFAULT_DETAIL} component={DefaultCheckDetail} />
      <Route exact path={ROUTES.PATTERNS.DEFINITIONS} component={Definitions} />
      <Route exact path={ROUTES.PATTERNS.INCIDENT_DETAIL} component={IncidentDetail} />
      <Route exact path={ROUTES.PATTERNS.INCIDENT_CONNECTION} component={IncidentConnection} />
      <Route exact path={ROUTES.PATTERNS.INCIDENTS} component={Incidents} />
      <Route exact path={ROUTES.PATTERNS.QUALITY_CHECKS} component={ChecksPage} />
      <Route exact path={ROUTES.PATTERNS.USERS_LIST_DETAIL} component={UserListDetail} />
      <Route exact path={ROUTES.PATTERNS.USER_DETAIL} component={UserDetail} />
      <Route exact path={ROUTES.PATTERNS.SCHEDULES_DEFAULT_DETAIL} component={DefaultSchedules} />
      <Redirect from={ROUTES.PATTERNS.INDEX} to={ROUTES.PATTERNS.HOME} />
      <Route exact path="*" component={NotFound} />
    </Switch>
  );
};

export default Routes;
