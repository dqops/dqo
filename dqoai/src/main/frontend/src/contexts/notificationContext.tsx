import React, { useEffect, useState } from 'react';
import axios from 'axios';

const NotificationContext = React.createContext({} as any);

export interface Notification {
  name: string;
  message?: string;
  date: number;
}

function NotificationProvider(props: any) {
  const [notifications, setNotifications] = useState<Notification[]>([]);

  useEffect(() => {
    axios.interceptors.response.use(undefined, async (error) => {
      const { response } = error;

      const newNotification = {
        name: response?.data?.error,
        message: response?.data?.trace,
        date: response?.data?.timestamp
      };

      setNotifications([...notifications, newNotification]);
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
      {...props}
    />
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
