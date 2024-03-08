import React, { useEffect, useState } from 'react';
import axios from 'axios';
import ErrorModal from '../components/ErrorModal';
import { LogErrorsApi } from "../services/apiClient";
import { LogShippingApi } from "../api";
import { useActionDispatch } from '../hooks/useActionDispatch';
import { setError, setIsErrorModalOpen } from '../redux/actions/job.actions';
import { useSelector } from 'react-redux';
import { IRootState } from '../redux/reducers';

const ErrorContext = React.createContext({} as any);

export interface IError {
  name: string;
  message?: string;
  date: number;
}

function ErrorProvider({ children }: any) {
  const [errors, setErrors] = useState<IError[]>([]);
  const [errorMessage, setErrorMessage] = useState<string>()
  const dispatch = useActionDispatch();

  const { isErrorModalOpen } = useSelector(
    (state: IRootState) => state.job || {}
  );

  useEffect(() => {
    axios.interceptors.response.use(undefined, async (error) => {
      const { response } = error;

      const statusCode = error.response ? error.response.status : null;
      if (statusCode === 401 || statusCode === 403) {
        return; // handled elsewhere
      }

      if (response && response?.status !== 503 && response?.status !== 504) {
        const newError : IError = {
          name: response?.data?.error,
          message: response?.data?.trace,
          date: response?.data?.timestamp
        };
        dispatch(setError(newError))

        if (newError.name) {
          setErrors([...errors, newError]);
        }
      }

      if (response?.status === 500) {
        dispatch(setIsErrorModalOpen(true));
        setErrorMessage(response?.data?.trace);
      }

      if (response?.status > 500) {
        dispatch(setIsErrorModalOpen(true));
      }

      if (response?.status < 500) {
        if (response.request?.responseURL?.indexOf("api/logs/error") < 0) {
          LogErrorsApi.logError({
            window_location: window.location.href,
            message: response?.data?.trace
          })
        }
      }

      if (error && response === undefined) {
        dispatch(setIsErrorModalOpen(true));
        return Promise.reject(error);
      }

      if (error && error.response?.status !== 404){
       return Promise.reject(error);
      }
    });
  }, [errors]);
  const removeError = (error: IError) => {
    setErrors(
      errors.filter((item) => item.date !== error.date)
    );
  };

  return (
    <ErrorContext.Provider
      value={{
        errors,
        setErrors,
        removeError
      }}
    >
      {children}
      <ErrorModal open={isErrorModalOpen} onClose={() => setIsErrorModalOpen(false)} message = {errorMessage}/>
    </ErrorContext.Provider>
  );
}

function useError() {
  const context = React.useContext(ErrorContext);

  if (context === undefined) {
    throw new Error(
      'useError must be used within a ErrorProvider'
    );
  }
  return context;
}

export { ErrorProvider, useError };
