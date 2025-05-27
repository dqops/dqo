import React, { useEffect } from 'react';

import { BrowserRouter as Router } from 'react-router-dom';

import { ThemeProvider } from '@material-tailwind/react';
import axios from 'axios';
import { useSelector } from 'react-redux';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import AppProvider from './contexts/AppProvider';
import { useActionDispatch } from './hooks/useActionDispatch';
import {
  getAllJobs,
  getJobsChanges,
  setUserSettings
} from './redux/actions/job.actions';
import { IRootState } from './redux/reducers';
import Routes from './Routes';
import { EnviromentApiClient, LogErrorsApi } from './services/apiClient';

import { CategoryScale } from 'chart.js';
import Chart from 'chart.js/auto';

Chart.register(CategoryScale);
// import { Chart as ChartJS, LinearScale, PointElement, Tooltip, Legend, TimeScale } from "chart.js";
import 'chartjs-adapter-moment';
// ChartJS.register(LinearScale, PointElement, Tooltip, Legend, TimeScale);

axios.interceptors.response.use(undefined, function (error) {
  const statusCode = error.response ? error.response.status : null;
  const isDashboardRequest =
    error.request.responseURL.includes('/api/dashboards/');
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

    EnviromentApiClient.getDqoSettings().then((res) => {
      dispatch(setUserSettings(res.data || {}));
      if (res.data?.properties?.['dqo.ui.application-name']) {
        document.title = String(
          res.data?.properties?.['dqo.ui.application-name']
        );
        localStorage.setItem(
          'applicationName',
          String(res.data?.properties?.['dqo.ui.application-name'])
        );
      }
    });
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
