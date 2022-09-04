import React from 'react';
import { Route, Switch } from 'react-router';

import Dashboard from './pages/Dashboard';

const Routes: React.FC = () => {
  return (
    <Switch>
      <Route exact path="/" component={Dashboard} />
    </Switch>
  );
};

export default Routes;
