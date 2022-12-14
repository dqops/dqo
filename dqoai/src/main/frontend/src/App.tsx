import React, { useEffect } from 'react';

import { BrowserRouter as Router } from 'react-router-dom';

import AppProvider from './contexts/AppProvider';
import Routes from './Routes';
import { ThemeProvider } from '@material-tailwind/react';
import { useActionDispatch } from './hooks/useActionDispatch';
import { getAllJobs, getJobsChanges } from './redux/actions/job.actions';
import { useSelector } from 'react-redux';
import { IRootState } from './redux/reducers';

const App = () => {
  const dispatch = useActionDispatch();
  const { lastSequenceNumber } = useSelector((state: IRootState) => state.job);

  useEffect(() => {
    dispatch(getAllJobs());
  }, []);

  useEffect(() => {
    if (!lastSequenceNumber) return;

    dispatch(getJobsChanges(lastSequenceNumber));
  }, [lastSequenceNumber]);

  return (
    <AppProvider>
      <ThemeProvider value={{}}>
        <Router>
          <Routes />
        </Router>
      </ThemeProvider>
    </AppProvider>
  );
};

export default App;
