import React from 'react';

import { Route, Switch, Redirect } from 'react-router';

import CreateConnection from './pages/CreateConnection';
import ChecksPage from './pages/Checks';
import HomePage from './pages/Home';
import Dashboards from './pages/Dashboards';
import ConnectionPage from "./pages/Connection";
import SchemaPage from "./pages/Schema";
import TablePage from "./pages/Table";
import TableProfilingsView from "./pages/TableProfilingsView";
import TableDailyChecksView from "./pages/TableDailyChecksView";
import TableMonthlyChecksView from "./pages/TableMonthlyChecksView";
import TableDailyPartitionedChecksView from "./pages/TableDailyPartitionedChecksView";
import TableMonthlyPartitionedChecksView from "./pages/TableMonthlyPartitionedChecksView";
import TableProfilingChecksUIFilterView from "./pages/TableProfilingChecksUIFilterView";
import TableColumnsView from "./pages/TableColumnsView";
import ColumnView from "./pages/ColumnView";
import ColumnProfilingsView from "./pages/ColumnProfilingsView";
import ColumnProfilingChecksUIFilterView from "./pages/ColumnProfilingChecksUIFilterView";
import ColumnDailyChecksView from "./pages/ColumnDailyChecksView";
import ColumnRecurringUIFilterView from "./pages/ColumnRecurringUIFilterView";
import ColumnMonthlyChecksView from "./pages/ColumnMonthlyChecksView";
import ColumnDailyPartitionedChecksView from "./pages/ColumnDailyPartitionedChecksView";
import ColumnMonthlyPartitionedChecksView from "./pages/ColumnMonthlyPartitionedChecksView";
import ColumnPartitionedChecksUIFilterView from "./pages/ColumnPartitionedChecksUIFilterView";
import TableRecurringUIFilterView from "./pages/TableRecurringUIFilterView";
import TablePartitionedChecksUIFilterView from "./pages/TablePartitionedChecksUIFilterView";
import { ROUTES } from "./shared/routes";
import Definitions from "./pages/Definitions";
import SensorDetail from "./pages/SensorDetail";
import RuleDetail from "./pages/RuleDetail";
import Incidents from "./pages/Incidents";
import IncidentDetail from "./pages/IncidentDetail";

const Routes = () => {
  return (
    <Switch>
      <Route exact path={ROUTES.PATTERNS.CREATE} component={CreateConnection} />
      <Route exact path={ROUTES.PATTERNS.CONNECTION} component={ConnectionPage} />
      <Route exact path={ROUTES.PATTERNS.SCHEMA} component={SchemaPage} />
      <Route exact path={ROUTES.PATTERNS.TABLE} component={TablePage} />
      <Route exact path={ROUTES.PATTERNS.TABLE_PROFILINGS} component={TableProfilingsView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_PROFILINGS_FILTER} component={TableProfilingChecksUIFilterView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_RECURRING_DAILY} component={TableDailyChecksView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_RECURRING_MONTHLY} component={TableMonthlyChecksView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_RECURRING_FILTER} component={TableRecurringUIFilterView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_PARTITIONED_DAILY} component={TableDailyPartitionedChecksView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_PARTITIONED_MONTHLY} component={TableMonthlyPartitionedChecksView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_PARTITIONED_FILTER} component={TablePartitionedChecksUIFilterView} />
      <Route exact path={ROUTES.PATTERNS.TABLE_COLUMNS} component={TableColumnsView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN} component={ColumnView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_PROFILINGS} component={ColumnProfilingsView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_PROFILINGS_FILTER} component={ColumnProfilingChecksUIFilterView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_RECURRING_DAILY} component={ColumnDailyChecksView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_RECURRING_MONTHLY} component={ColumnMonthlyChecksView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_RECURRING_FILTER} component={ColumnRecurringUIFilterView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_PARTITIONED_DAILY} component={ColumnDailyPartitionedChecksView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_PARTITIONED_MONTHLY} component={ColumnMonthlyPartitionedChecksView} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_PARTITIONED_FILTER} component={ColumnPartitionedChecksUIFilterView} />
      <Route exact path={ROUTES.PATTERNS.HOME} component={HomePage} />
      <Route exact path={ROUTES.PATTERNS.DASHBOARDS} component={Dashboards} />
      <Route exact path={ROUTES.PATTERNS.SENSOR_DETAIL} component={SensorDetail} />
      <Route exact path={ROUTES.PATTERNS.RULE_DETAIL} component={RuleDetail} />
      <Route exact path={ROUTES.PATTERNS.DEFINITIONS} component={Definitions} />
      <Route exact path={ROUTES.PATTERNS.INCIDENT_DETAIL} component={IncidentDetail} />
      <Route exact path={ROUTES.PATTERNS.INCIDENTS} component={Incidents} />
      <Route exact path={ROUTES.PATTERNS.QUALITY_CHECKS} component={ChecksPage} />
      <Redirect from={ROUTES.PATTERNS.INDEX} to={ROUTES.PATTERNS.HOME} />
      <Route component={ChecksPage} />
    </Switch>
  );
};

export default Routes;
