import React from 'react';

import { Route, Switch, Redirect } from 'react-router';

import Dashboard from './pages/Dashboard';
import ConnectionPage from './pages/Connection';
import HomePage from './pages/Home';

const Routes = () => {
  return (
    <Switch>
      <Route exact path="/create" component={Dashboard} />
      <Route exact path="/dashboard" component={ConnectionPage} />
      <Route exact path="/home" component={HomePage} />
      <Redirect from="/" to="/home" />
      <Route component={ConnectionPage} />
    </Switch>
  );
};

export default Routes;
