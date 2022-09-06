import React from 'react';
import { Route, Switch } from 'react-router';

import Dashboard from './pages/Dashboard';
import TestPage from './pages/Test';

const Routes = () => {
  return (
    <Switch>
      <Route exact path="/" component={Dashboard} />
      <Route exact path="/test" component={TestPage} />
    </Switch>
  );
};

export default Routes;
