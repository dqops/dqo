import React from 'react';

import { Route, Switch, Redirect } from 'react-router';

import CreateConnection from './pages/CreateConnection';
import ConnectionPage from './pages/Connection';
import HomePage from './pages/Home';
import Dashboards from './pages/Dashboards';

const Routes = () => {
  return (
    <Switch>
      <Route exact path="/create" component={CreateConnection} />
      <Route exact path="/checks" component={ConnectionPage} />
      <Route exact path="/home" component={HomePage} />
      <Route exact path="/dashboards" component={Dashboards} />
      <Redirect from="/" to="/home" />
      <Route component={ConnectionPage} />
    </Switch>
  );
};

export default Routes;
