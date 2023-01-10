import React from 'react';

import { Route, Switch, Redirect } from 'react-router';

import CreateConnection from './pages/CreateConnection';
import ChecksPage from './pages/Checks';
import HomePage from './pages/Home';
import Dashboards from './pages/Dashboards';
import ConnectionPage from "./pages/Connection";
import SchemaPage from "./pages/Schema";
import TablePage from "./pages/Table";
import TableAdhocsView from "./pages/TableAdhocsView";
import TableDailyChecksView from "./pages/TableDailyChecksView";
import TableMonthlyChecksView from "./pages/TableMonthlyChecksView";
import TableDailyPartitionedChecksView from "./pages/TableDailyPartitionedChecksView";
import TableMonthlyPartitionedChecksView from "./pages/TableMonthlyPartitionedChecksView";
import TableAdHockChecksUIFilterView from "./pages/TableAdHockChecksUIFilterView";

const Routes = () => {
  return (
    <Switch>
      <Route exact path="/create" component={CreateConnection} />
      <Route exact path="/checks/connection/:connection/:tab" component={ConnectionPage} />
      <Route exact path="/checks/connection/:connection/schema/:schema/:tab" component={SchemaPage} />
      <Route exact path="/checks/connection/:connection/schema/:schema/table/:table/:tab" component={TablePage} />
      <Route exact path="/checks/connection/:connection/schema/:schema/table/:table/checks/ad-hocs" component={TableAdhocsView} />
      <Route exact path="/checks/connection/:connection/schema/:schema/table/:table/checks/ad-hocs/:category/:checkName" component={TableAdHockChecksUIFilterView} />
      <Route exact path="/checks/connection/:connection/schema/:schema/table/:table/checkpoints/daily" component={TableDailyChecksView} />
      <Route exact path="/checks/connection/:connection/schema/:schema/table/:table/checkpoints/monthly" component={TableMonthlyChecksView} />
      <Route exact path="/checks/connection/:connection/schema/:schema/table/:table/partitioned/daily" component={TableDailyPartitionedChecksView} />
      <Route exact path="/checks/connection/:connection/schema/:schema/table/:table/partitioned/monthly" component={TableMonthlyPartitionedChecksView} />
      <Route exact path="/checks" component={ChecksPage} />
      <Route exact path="/home" component={HomePage} />
      <Route exact path="/dashboards" component={Dashboards} />
      <Redirect from="/" to="/home" />
      <Route component={ChecksPage} />
    </Switch>
  );
};

export default Routes;
