import React from 'react';

import { Provider } from 'react-redux';
import { BrowserRouter as Router } from 'react-router-dom';

import AppProvider from './contexts/AppProvider';
import store from './redux/store';
import Routes from './Routes';

const App = () => {
  return (
    <Provider store={store}>
      <AppProvider>
        <Router>
          <Routes />
        </Router>
      </AppProvider>
    </Provider>
  );
};

export default App;
