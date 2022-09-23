import React from 'react';

import { Provider } from 'react-redux';
import { BrowserRouter as Router } from 'react-router-dom';

import AppProvider from './contexts/AppProvider';
import store from './redux/store';
import Routes from './Routes';
import { ThemeProvider } from '@material-tailwind/react';

const App = () => {
  return (
    <Provider store={store}>
      <AppProvider>
        <ThemeProvider value={{}}>
          <Router>
            <Routes />
          </Router>
        </ThemeProvider>
      </AppProvider>
    </Provider>
  );
};

export default App;
