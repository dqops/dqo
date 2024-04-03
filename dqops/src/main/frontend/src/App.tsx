import React, { useEffect } from 'react';

import { BrowserRouter as Router } from 'react-router-dom';

import AppProvider from './contexts/AppProvider';
import Routes from './Routes';
import { ThemeProvider } from '@material-tailwind/react';
import { useActionDispatch } from './hooks/useActionDispatch';
import { getAllJobs, getJobsChanges } from './redux/actions/job.actions';
import { useSelector } from 'react-redux';
import { IRootState } from './redux/reducers';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { LogErrorsApi } from './services/apiClient';
import axios from 'axios';

import Chart from 'chart.js/auto';
import { CategoryScale } from 'chart.js';

Chart.register(CategoryScale);
// import { Chart as ChartJS, LinearScale, PointElement, Tooltip, Legend, TimeScale } from "chart.js";
import 'chartjs-adapter-moment';
// ChartJS.register(LinearScale, PointElement, Tooltip, Legend, TimeScale);

axios.interceptors.response.use(undefined, function (error) {
  const statusCode = error.response ? error.response.status : null;
  const isDashboardRequest = error.request.responseURL.includes("/api/dashboards/");
  if ((statusCode === 401 && !isDashboardRequest) || statusCode === 403) {
     location.reload();
  }
  return Promise.reject(error);
});

const App = () => {
  const dispatch = useActionDispatch();
  const { lastSequenceNumber, loading } = useSelector(
    (state: IRootState) => state.job || {}
  );

  useEffect(() => {
    dispatch(getAllJobs());

    window.onunhandledrejection = (event) => {
      if (event?.reason?.request?.responseURL?.indexOf('api/logs/error') > 0) {
        LogErrorsApi.logError({
          window_location: window.location.href,
          message: event.reason?.toString()
        });
      }
    };

    window.onerror = function (message) {
      LogErrorsApi.logError({
        window_location: window.location.href,
        message: message?.toString()
      });
    };
  }, []);

  useEffect(() => {
    if (!lastSequenceNumber || loading === true) return;

    dispatch(getJobsChanges(lastSequenceNumber ?? 0));
  }, [lastSequenceNumber, loading]);

  return (
    <Router>
      <AppProvider>
        <ThemeProvider>
          <Routes />
          <ToastContainer />
        </ThemeProvider>
      </AppProvider>
    </Router>
  );
};

export default App;
