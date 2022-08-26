import React from 'react';
import { Route, Switch } from 'react-router';

import Dashboard from './pages/Dashboard';

function Routes(): React.ReactElement {
  return (
    <Switch>
      <Route exact path="/" component={Dashboard} />
    </Switch>
  );
}

export default Routes;
