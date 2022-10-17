import React from 'react';

import { Route, Switch } from 'react-router';

import Dashboard from './pages/Dashboard';
import ConnectionPage from './pages/Connection';

const Routes = () => {
  return (
    <Switch>
      <Route exact path="/" component={Dashboard} />
      <Route exact path="/connection" component={ConnectionPage} />
    </Switch>
  );
};

export default Routes;
