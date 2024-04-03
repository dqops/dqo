import React from 'react';
import { Route, Switch, Redirect } from 'react-router';
import HomePage from './pages/Home';
import { ROUTES } from "./shared/routes";
import NotFound from "./pages/NotFound";
import ConnectionLayout from "./components/ConnectionLayout";
import DefinitionLayout from "./components/DefinitionLayout";
import DashboardLayout from "./components/DashboardLayout";
import IncidentsLayout from "./components/IncidentsLayout";
import CreateConnection from './pages/CreateConnection';

const Routes = () => {
  return (
    <Switch>
      <Route exact path={ROUTES.PATTERNS.HOME} component={HomePage} />

      {/* Dashboard Layout pages */}
      <Route exact path={ROUTES.PATTERNS.DASHBOARDS} render={() => <DashboardLayout route={ROUTES.PATTERNS.DASHBOARDS} />} />

      {/* Incident Layout Pages */}
      <Route exact path={ROUTES.PATTERNS.INCIDENT_DETAIL} render={() => <IncidentsLayout route={ROUTES.PATTERNS.INCIDENT_DETAIL}/>} />
      <Route exact path={ROUTES.PATTERNS.INCIDENTS} render={() => <IncidentsLayout route={ROUTES.PATTERNS.INCIDENTS}/>} />
      <Route exact path={ROUTES.PATTERNS.INCIDENT_CONNECTION} render={() => <IncidentsLayout route={ROUTES.PATTERNS.INCIDENT_CONNECTION}/>} />

      {/* Definition Layout Pages */}
      <Route exact path={ROUTES.PATTERNS.SENSOR_DETAIL} render={() => <DefinitionLayout route={ROUTES.PATTERNS.SENSOR_DETAIL} />} />
      <Route exact path={ROUTES.PATTERNS.RULE_DETAIL} render={() => <DefinitionLayout route={ROUTES.PATTERNS.RULE_DETAIL} />} />
      <Route exact path={ROUTES.PATTERNS.CHECK_DETAIL} render={() => <DefinitionLayout route={ROUTES.PATTERNS.CHECK_DETAIL} />} />
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
      <Route exact path={ROUTES.PATTERNS.DEFAULT_CHECKS_PATTERNS} render={() => <DefinitionLayout route={ROUTES.PATTERNS.DEFAULT_CHECKS_PATTERNS} />} />
      <Route exact path={ROUTES.PATTERNS.DEFAULT_CHECK_PATTERN_DETAIL} render={() => <DefinitionLayout route={ROUTES.PATTERNS.DEFAULT_CHECK_PATTERN_DETAIL} />} />

      {/* Create Connection (packed with MainLayout) */}
      <Route exact path={ROUTES.PATTERNS.CREATE} render={() => <CreateConnection/>} />

      {/* Connction Layout pages */}
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
