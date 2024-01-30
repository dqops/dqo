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
import DefaultWebhooksDetail from "./pages/DefaultWebhooksDetail";
import SharedCredentialsDetail from "./pages/SharedCredentialsDetail";
import SingleSharedCredential from "./pages/SharedCredentialsDetail/SingleSharedCredential";
import DataDictionary from "./pages/DataDictionaryConfiguration";
import DataDictionaryItemOverview from "./pages/DataDictionaryConfiguration/DataDictionaryItemOverview";
import ConnectionLayout from "./components/ConnectionLayout";
import DefinitionLayout from "./components/DefinitionLayout";

const Routes = () => {
  return (
    <Switch>
      <Route exact path={ROUTES.PATTERNS.HOME} component={HomePage} />

      {/* Dashboard Layout pages */}
      <Route exact path={ROUTES.PATTERNS.DASHBOARDS} component={Dashboards} />

      {/* Incident Layout Pages */}
      <Route exact path={ROUTES.PATTERNS.INCIDENT_DETAIL} component={IncidentDetail} />
      <Route exact path={ROUTES.PATTERNS.INCIDENTS} component={Incidents} />

      {/* Definition Layout Pages */}
      <Route exact path={ROUTES.PATTERNS.SENSOR_DETAIL} render={() => <DefinitionLayout route={ROUTES.PATTERNS.SENSOR_DETAIL} />} />
      <Route exact path={ROUTES.PATTERNS.RULE_DETAIL} render={() => <DefinitionLayout route={ROUTES.PATTERNS.RULE_DETAIL} />} />
      <Route exact path={ROUTES.PATTERNS.CHECK_DETAIL} render={() => <DefinitionLayout route={ROUTES.PATTERNS.CHECK_DETAIL} />} />
      <Route exact path={ROUTES.PATTERNS.CHECK_DEFAULT_DETAIL} render={() => <DefinitionLayout route={ROUTES.PATTERNS.CHECK_DEFAULT_DETAIL} />} />
      <Route exact path={ROUTES.PATTERNS.DEFINITIONS} render={() => <DefinitionLayout route={ROUTES.PATTERNS.DEFINITIONS} />} />
      <Route exact path={ROUTES.PATTERNS.INCIDENT_CONNECTION} render={() => <DefinitionLayout route={ROUTES.PATTERNS.INCIDENT_CONNECTION} />} />
      <Route exact path={ROUTES.PATTERNS.USERS_LIST_DETAIL} render={() => <DefinitionLayout route={ROUTES.PATTERNS.USERS_LIST_DETAIL} />} />
      <Route exact path={ROUTES.PATTERNS.USER_DETAIL} render={() => <DefinitionLayout route={ROUTES.PATTERNS.USER_DETAIL} />} />
      <Route exact path={ROUTES.PATTERNS.SCHEDULES_DEFAULT_DETAIL} render={() => <DefinitionLayout route={ROUTES.PATTERNS.SCHEDULES_DEFAULT_DETAIL} />} />
      <Route exact path={ROUTES.PATTERNS.WEBHOOKS_DEFAULT_DETAIL} render={() => <DefinitionLayout route={ROUTES.PATTERNS.WEBHOOKS_DEFAULT_DETAIL} />} />
      <Route exact path={ROUTES.PATTERNS.SHARED_CREDENTIALS_LIST_DETAIL} render={() => <DefinitionLayout route={ROUTES.PATTERNS.SHARED_CREDENTIALS_LIST_DETAIL} />} />
      <Route exact path={ROUTES.PATTERNS.SHARED_CREDENTIALS_DETAIL} render={() => <DefinitionLayout route={ROUTES.PATTERNS.SHARED_CREDENTIALS_DETAIL} />} />
      <Route exact path={ROUTES.PATTERNS.DATA_DICTIONARY_LIST_DETAIL} render={() => <DefinitionLayout route={ROUTES.PATTERNS.DATA_DICTIONARY_LIST_DETAIL} />} />
      <Route exact path={ROUTES.PATTERNS.DATA_DICTIONARY_DETAIL} render={() => <DefinitionLayout route={ROUTES.PATTERNS.DATA_DICTIONARY_DETAIL} />} />

      {/* Connction Layout pages */}
      <Route exact path={ROUTES.PATTERNS.CREATE} render={() => <ConnectionLayout route={ROUTES.PATTERNS.CREATE} />} />
      <Route exact path={ROUTES.PATTERNS.CONNECTION} render={() => <ConnectionLayout route={ROUTES.PATTERNS.CONNECTION} />} />
      <Route exact path={ROUTES.PATTERNS.SCHEMA} render={() => <ConnectionLayout route={ROUTES.PATTERNS.SCHEMA} />} />
      <Route exact path={ROUTES.PATTERNS.TABLE} render={() => <ConnectionLayout route={ROUTES.PATTERNS.TABLE} />} />
      <Route exact path={ROUTES.PATTERNS.TABLE_PROFILING} render={() => <ConnectionLayout route={ROUTES.PATTERNS.TABLE_PROFILING} />} />
      <Route exact path={ROUTES.PATTERNS.TABLE_PROFILING_FILTER} render={() => <ConnectionLayout route={ROUTES.PATTERNS.TABLE_PROFILING_FILTER} />} />
      <Route exact path={ROUTES.PATTERNS.TABLE_MONITORING_DAILY} render={() => <ConnectionLayout route={ROUTES.PATTERNS.TABLE_MONITORING_DAILY} />} />
      <Route exact path={ROUTES.PATTERNS.TABLE_MONITORING_MONTHLY} render={() => <ConnectionLayout route={ROUTES.PATTERNS.TABLE_MONITORING_MONTHLY} />} />
      <Route exact path={ROUTES.PATTERNS.TABLE_MONITORING_FILTER} render={() => <ConnectionLayout route={ROUTES.PATTERNS.TABLE_MONITORING_FILTER} />} />
      <Route exact path={ROUTES.PATTERNS.TABLE_PARTITIONED_DAILY} render={() => <ConnectionLayout route={ROUTES.PATTERNS.TABLE_PARTITIONED_DAILY} />} />
      <Route exact path={ROUTES.PATTERNS.TABLE_PARTITIONED_MONTHLY} render={() => <ConnectionLayout route={ROUTES.PATTERNS.TABLE_PARTITIONED_MONTHLY} />} />
      <Route exact path={ROUTES.PATTERNS.TABLE_PARTITIONED_FILTER} render={() => <ConnectionLayout route={ROUTES.PATTERNS.TABLE_PARTITIONED_FILTER} />} />
      <Route exact path={ROUTES.PATTERNS.TABLE_COLUMNS} render={() => <ConnectionLayout route={ROUTES.PATTERNS.TABLE_COLUMNS} />} />
      <Route exact path={ROUTES.PATTERNS.TABLE_INCIDENTS_NOTIFICATION} render={() => <ConnectionLayout route={ROUTES.PATTERNS.TABLE_INCIDENTS_NOTIFICATION} />} />
      <Route exact path={ROUTES.PATTERNS.COLUMN} render={() => <ConnectionLayout route={ROUTES.PATTERNS.COLUMN} />} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_PROFILING} render={() => <ConnectionLayout route={ROUTES.PATTERNS.COLUMN_PROFILING} />} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_PROFILING_FILTER} render={() => <ConnectionLayout route={ROUTES.PATTERNS.COLUMN_PROFILING_FILTER} />} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_MONITORING_DAILY} render={() => <ConnectionLayout route={ROUTES.PATTERNS.COLUMN_MONITORING_DAILY} />} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_MONITORING_MONTHLY} render={() => <ConnectionLayout route={ROUTES.PATTERNS.COLUMN_MONITORING_MONTHLY} />} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_MONITORING_FILTER} render={() => <ConnectionLayout route={ROUTES.PATTERNS.COLUMN_MONITORING_FILTER} />} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_PARTITIONED_DAILY} render={() => <ConnectionLayout route={ROUTES.PATTERNS.COLUMN_PARTITIONED_DAILY} />} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_PARTITIONED_MONTHLY} render={() => <ConnectionLayout route={ROUTES.PATTERNS.COLUMN_PARTITIONED_MONTHLY} />} />
      <Route exact path={ROUTES.PATTERNS.COLUMN_PARTITIONED_FILTER} render={() => <ConnectionLayout route={ROUTES.PATTERNS.COLUMN_PARTITIONED_FILTER} />} />
      <Route exact path={ROUTES.PATTERNS.QUALITY_CHECKS} render={() => <ConnectionLayout route={ROUTES.PATTERNS.QUALITY_CHECKS} />}/>

      <Redirect from={ROUTES.PATTERNS.INDEX} to={ROUTES.PATTERNS.HOME} />
      <Route exact path="*" component={NotFound} />
    </Switch>
  );
};

export default Routes;
