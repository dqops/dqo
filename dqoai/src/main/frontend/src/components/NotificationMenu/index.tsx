import React, { useMemo } from 'react';

import {
  Popover,
  PopoverHandler,
  PopoverContent,
  IconButton
} from '@material-tailwind/react';
import SvgIcon from '../SvgIcon';
import {
  useNotification,
  Notification
} from '../../contexts/notificationContext';
import moment from 'moment';
import Button from '../Button';

const NotificationMenu = () => {
  const { notifications, setNotifications, removeNotification } =
    useNotification();

  const data = useMemo(() => {
    const notificationsData = [...notifications];
    notificationsData.sort(
      (a: Notification, b: Notification) => b.date - a.date
    );

    return notificationsData;
  }, [notifications]);

  return (
    <Popover placement="bottom-end">
      <PopoverHandler>
        <IconButton className="!mr-3" variant="text">
          <div className="relative">
            <SvgIcon name="bell" className="w-5 h-5 text-gray-500" />
            <span className="absolute top-0 right-0 transform translate-x-1/2 -translate-y-1/2 rounded-full bg-purple-500 text-white px-1 py-0.5 text-xxs">
              {notifications.length}
            </span>
          </div>
        </IconButton>
      </PopoverHandler>
      <PopoverContent className="z-50 min-w-80 max-w-80">
        <div className="border-b border-gray-300 font-semibold pb-2 text-xl flex items-center justify-between">
          <div>Notifications</div>
          <Button
            label="Dismiss All"
            color="primary"
            variant="text"
            textSize="sm"
            className="py-1.5 !rounded uppercase font-medium"
            onClick={() => setNotifications([])}
          />
        </div>
        <div className="overflow-auto max-h-100">
          {!notifications.length && (
            <div className="py-2">No notifications</div>
          )}
          {data.map((item: Notification) => (
            <div key={item.date} className="text-gray-700 py-2">
              <div className="flex justify-between items-center">
                <div className="font-semibold mb-2">{item.name}</div>
              </div>
              <div className="mb-1 text-sm">
                {moment(item.date).format('MMM DD, YYYY hh:mm:ss A')}
              </div>
              <div>{item.message?.slice(0, 100)}...</div>
              <div className="flex justify-end">
                <Button
                  label="Dismiss"
                  color="primary"
                  variant="text"
                  textSize="sm"
                  className="py-1.5 !rounded uppercase font-medium"
                  onClick={() => removeNotification(item)}
                />
              </div>
            </div>
          ))}
        </div>
      </PopoverContent>
    </Popover>
  );
};

export default NotificationMenu;
