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
import TableDailyChecksView from "./pages/TableDailyRecurringChecksView";
import TableMonthlyChecksView from "./pages/TableMonthlyRecurringChecksView";
import TableDailyPartitionedChecksView from "./pages/TableDailyPartitionedChecksView";
import TableMonthlyPartitionedChecksView from "./pages/TableMonthlyPartitionedChecksView";
import TableProfilingChecksUIFilterView from "./pages/TableProfilingChecksUIFilterView";
import TableColumnsView from "./pages/TableColumnsView";
import ColumnView from "./pages/ColumnView";
import ColumnProfilingChecksView from "./pages/ColumnProfilingChecksView";
import ColumnProfilingChecksUIFilterView from "./pages/ColumnProfilingChecksUIFilterView";
import ColumnDailyRecurringChecksView from "./pages/ColumnDailyRecurringChecksView";
import ColumnRecurringChecksUIFilterView from "./pages/ColumnRecurringChecksUIFilterView";
import ColumnMonthlyRecurringChecksView from "./pages/ColumnMonthlyRecurringChecksView";
import ColumnDailyPartitionedChecksView from "./pages/ColumnDailyPartitionedChecksView";
import ColumnMonthlyPartitionedChecksView from "./pages/ColumnMonthlyPartitionedChecksView";
import ColumnPartitionedChecksUIFilterView from "./pages/ColumnPartitionedChecksUIFilterView";
import TableRecurringChecksUIFilterView from "./pages/TableRecurringChecksUIFilterView";
import TablePartitionedChecksUIFilterView from "./pages/TablePartitionedChecksUIFilterView";
import { ROUTES } from "./shared/routes";
import Definitions from "./pages/Definitions";
import SensorDetail from "./pages/SensorDetail";
import RuleDetail from "./pages/RuleDetail";
import Incidents from "./pages/Incidents";
import IncidentConnection from "./pages/IncidentConnection";
import IncidentDetail from "./pages/IncidentDetail";
import NotFound from "./pages/NotFound";

const Routes = () => {
  return (
    <Switch>
      <Route exact path={ROUTES.PATTERNS.CREATE} component={CreateConnection} />
      <Route exact path={ROUTES.PATTERNS.CONNECTION} component={ConnectionPage} />
      <Route exact path={ROUTES.PATTERNS.SCHEMA} component={SchemaPage} />
      <Route exact path={ROUTES.PATTERNS.TABLE} component={TablePage} />
      <Route exact path={ROUTES.PATTERNS.TABLE_PROFILING} component={TableProfilingChecksView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_PROFILING_FILTER} component={TableProfilingChecksUIFilterView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_RECURRING_DAILY} component={TableDailyChecksView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_RECURRING_MONTHLY} component={TableMonthlyChecksView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_RECURRING_FILTER} component={TableRecurringChecksUIFilterView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_PARTITIONED_DAILY} component={TableDailyPartitionedChecksView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_PARTITIONED_MONTHLY} component={TableMonthlyPartitionedChecksView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_PARTITIONED_FILTER} component={TablePartitionedChecksUIFilterView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_COLUMNS} component={TableColumnsView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN} component={ColumnView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_PROFILING} component={ColumnProfilingChecksView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_PROFILING_FILTER} component={ColumnProfilingChecksUIFilterView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_RECURRING_DAILY} component={ColumnDailyRecurringChecksView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_RECURRING_MONTHLY} component={ColumnMonthlyRecurringChecksView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_RECURRING_FILTER} component={ColumnRecurringChecksUIFilterView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_PARTITIONED_DAILY} component={ColumnDailyPartitionedChecksView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_PARTITIONED_MONTHLY} component={ColumnMonthlyPartitionedChecksView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_PARTITIONED_FILTER} component={ColumnPartitionedChecksUIFilterView} />
      <Route exact path={ROUTES.PATTERNS.HOME} component={HomePage} />
      <Route exact path={ROUTES.PATTERNS.DASHBOARDS} component={Dashboards} />
      <Route exact path={ROUTES.PATTERNS.SENSOR_DETAIL} component={SensorDetail} />
      <Route exact path={ROUTES.PATTERNS.RULE_DETAIL} component={RuleDetail} />
      <Route exact path={ROUTES.PATTERNS.DEFINITIONS} component={Definitions} />
      <Route exact path={ROUTES.PATTERNS.INCIDENT_DETAIL} component={IncidentDetail} />
      <Route exact path={ROUTES.PATTERNS.INCIDENT_CONNECTION} component={IncidentConnection} />
      <Route exact path={ROUTES.PATTERNS.INCIDENTS} component={Incidents} />
      <Route exact path={ROUTES.PATTERNS.QUALITY_CHECKS} component={ChecksPage} />
      <Redirect from={ROUTES.PATTERNS.INDEX} to={ROUTES.PATTERNS.HOME} />
      <Route exact path="*" component={NotFound} />
    </Switch>
  );
};

export default Routes;
