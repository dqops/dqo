import clsx from 'clsx';
import React, { useState } from 'react';
import { FilteredNotificationModel } from '../../../../api';
import { sortPatterns } from '../../../../utils';
import Button from '../../../Button';
import ConfirmDialog from '../../../CustomTree/ConfirmDialog';
import SvgIcon from '../../../SvgIcon';

const headerElements = [
  { label: 'Notification patterns', key: 'name' },
  { label: 'Priority', key: 'priority' },
  { label: 'Connection', key: 'connection' },
  { label: 'Schema', key: 'schema' },
  { label: 'Table', key: 'table' },
  { label: 'Column', key: 'column' }
];

type TNotificationPattern = FilteredNotificationModel & {
  connection?: string;
  schema?: string;
  table?: string;
  column?: string;
};

export default function NotificationPatternTable({
  filteredNotificationsConfigurations,
  onChange,
  setPatternNameEdit
}: {
  filteredNotificationsConfigurations: Array<TNotificationPattern>;
  onChange: (data: any) => void;
  setPatternNameEdit: (patternName: string) => void;
}) {
  const editPattern = (type: string, notificationPattern: string) => {
    // openDefaultCheckPatternFirstLevelTab(type, notificationPattern);
  };
  const [dir, setDir] = useState<'asc' | 'desc'>('desc');
  const [notificationPatternDelete, setPatternDelete] = useState('');
  const [indexSortingElement, setIndexSortingElement] = useState(1);

  const sortPreparedPattern = (
    elem: any,
    index: number,
    dir: 'asc' | 'desc'
  ) => {
    onChange(
      sortPatterns(
        filteredNotificationsConfigurations,
        elem.key as keyof TNotificationPattern,
        dir
      )
    ),
      setDir(dir === 'asc' ? 'desc' : 'asc'),
      setIndexSortingElement(index);
  };

  return (
    <table>
      <thead>
        <tr>
          {headerElements.map((elem, index) => (
            <th className="px-4" key={elem.label}>
              <div className="flex gap-x-1 items-center cursor-default">
                <div>{elem.label}</div>
                <div>
                  {!(indexSortingElement === index && dir === 'asc') ? (
                    <SvgIcon
                      name="chevron-up"
                      className="w-2 h-2 text-black"
                      onClick={() => sortPreparedPattern(elem, index, 'desc')}
                    />
                  ) : (
                    <div className="w-2 h-2" />
                  )}
                  {!(indexSortingElement === index && dir === 'desc') ? (
                    <SvgIcon
                      name="chevron-down"
                      className="w-2 h-2 text-black"
                      onClick={() => sortPreparedPattern(elem, index, 'asc')}
                    />
                  ) : (
                    <div className="w-2 h-2" />
                  )}
                </div>
              </div>
            </th>
          ))}
        </tr>
      </thead>
      <tbody className=" border-t border-gray-100">
        {filteredNotificationsConfigurations.map(
          (notificationPattern, index) => (
            <tr key={index} className="text-sm">
              <td
                className={clsx(
                  'px-4 underline cursor-pointer',
                  notificationPattern.disabled && 'text-gray-200'
                )}
                onClick={() =>
                  setPatternNameEdit(notificationPattern.name ?? '')
                }
              >
                {notificationPattern.name}
              </td>
              <td className="px-4">{notificationPattern.priority}</td>
              <td className="px-4">{notificationPattern?.connection}</td>
              <td className="px-4">{notificationPattern?.schema}</td>
              <td className="px-4">{notificationPattern?.table}</td>
              <td className="px-4">{notificationPattern?.column}</td>
              <td className="px-4">
                <Button
                  variant="text"
                  label="edit"
                  color="primary"
                  // onClick={() =>
                  //   editPattern(
                  //     type,
                  //     notificationPattern.notificationPattern_name ?? ''
                  //   )
                  // }
                />
              </td>
              <td className="px-4">
                <Button
                  variant="text"
                  label="delete"
                  color="primary"
                  onClick={() =>
                    setPatternDelete(notificationPattern.name ?? '')
                  }
                />
              </td>
            </tr>
          )
        )}
        <ConfirmDialog
          open={notificationPatternDelete.length > 0}
          onConfirm={async () => {
            // deletePattern(notificationPatternDelete ?? ''),
            setPatternDelete('');
          }}
          onClose={() => setPatternDelete('')}
          message={`Are you sure you want to delete the ${notificationPatternDelete} notificationPattern ?`}
        />
      </tbody>
    </table>
  );
}
