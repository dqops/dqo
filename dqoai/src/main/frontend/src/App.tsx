import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import Routes from "./Routes";
import AppProvider from './contexts/AppProvider';
import store from './redux/store';
import { Provider } from 'react-redux';

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
