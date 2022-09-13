import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import Routes from "./Routes";
import AppProvider from './contexts/AppProvider';

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
