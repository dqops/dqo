import React, { useEffect, useState } from 'react';
import axios from 'axios';
import ErrorModal from '../components/ErrorModal';

const ErrorContext = React.createContext({} as any);

export interface IError {
  name: string;
  message?: string;
  date: number;
}

function ErrorProvider({ children }: any) {
  const [errors, setErrors] = useState<IError[]>([]);
  const [isOpen, setIsOpen] = useState(false);

  useEffect(() => {
    axios.interceptors.response.use(undefined, async (error) => {
      const { response } = error;

      const newError = {
        name: response?.data?.error,
        message: response?.data?.trace,
        date: response?.data?.timestamp
      };

      if (newError.name) {
        setErrors([...errors, newError]);
      }
      
      if (response.status > 500) {
        setIsOpen(true);
      }
      return Promise.reject(error);
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
      <ErrorModal open={isOpen} onClose={() => setIsOpen(false)} />
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
