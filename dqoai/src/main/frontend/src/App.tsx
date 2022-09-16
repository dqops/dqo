import React from 'react';

import { BrowserRouter as Router } from 'react-router-dom';

import AppProvider from './contexts/AppProvider';
import Routes from './Routes';

const App = () => {
  return (
    <AppProvider>
      <Router>
        <Routes />
      </Router>
    </AppProvider>
  );
};

export default App;
