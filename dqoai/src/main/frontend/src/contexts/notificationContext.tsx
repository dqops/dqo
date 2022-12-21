import React, { useEffect, useState } from 'react';
import axios from 'axios';
import ErrorModal from '../components/ErrorModal';

const NotificationContext = React.createContext({} as any);

export interface Notification {
  name: string;
  message?: string;
  date: number;
}

function NotificationProvider({ children }: any) {
  const [notifications, setNotifications] = useState<Notification[]>([]);
  const [isOpen, setIsOpen] = useState(false);

  useEffect(() => {
    axios.interceptors.response.use(undefined, async (error) => {
      const { response } = error;

      const newNotification = {
        name: response?.data?.error,
        message: response?.data?.trace,
        date: response?.data?.timestamp
      };

      setNotifications([...notifications, newNotification]);
      
      if (response.status === 504) {
        setIsOpen(true);
      }
      return Promise.reject(error);
    });
  }, [notifications]);
  const removeNotification = (notification: Notification) => {
    setNotifications(
      notifications.filter((item) => item.date !== notification.date)
    );
  };

  return (
    <NotificationContext.Provider
      value={{
        notifications,
        setNotifications,
        removeNotification
      }}
    >
      {children}
      <ErrorModal open={isOpen} onClose={() => setIsOpen(false)} />
    </NotificationContext.Provider>
  );
}

function useNotification() {
  const context = React.useContext(NotificationContext);

  if (context === undefined) {
    throw new Error(
      'useNotification must be used within a NotificationProvider'
    );
  }
  return context;
}

export { NotificationProvider, useNotification };
