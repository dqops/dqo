import { IconButton } from '@material-tailwind/react';
import clsx from 'clsx';
import React, { useState } from 'react';
import { FilteredNotificationModel } from '../../../../api';
import { FilteredNotificationsConfigurationsClient } from '../../../../services/apiClient';
import { getSeverity, sortPatterns } from '../../../../utils';
import ConfirmDialog from '../../../CustomTree/ConfirmDialog';
import SvgIcon from '../../../SvgIcon';

const headerElements = [
  { label: 'Notification filters', key: 'name' },
  { label: 'Priority', key: 'priority' },
  { label: 'Connection', key: 'connection' },
  { label: 'Schema', key: 'schema' },
  { label: 'Table', key: 'table' },
  { label: 'Quality dimension', key: 'qualityDimension' },
  { label: 'Check category', key: 'checkCategory' },
  { label: 'Check name', key: 'checkName' },
  { label: 'Check type', key: 'checkType' },
  { label: 'Highest severity', key: 'highestSeverity' },
  { label: 'Action', key: 'action' }
];

type TNotificationPattern = FilteredNotificationModel & {
  connection?: string;
  schema?: string;
  table?: string;
  column?: string;
  qualityDimension?: string;
  checkCategory?: string;
  checkName?: string;
  checkType?: string;
  highestSeverity?: number;
};

export default function NotificationPatternTable({
  filteredNotificationsConfigurations,
  onChange,
  setPatternNameEdit,
  connection
}: {
  filteredNotificationsConfigurations: Array<TNotificationPattern>;
  onChange: (data: any) => void;
  setPatternNameEdit: (patternName: string) => void;
  connection?: string;
}) {
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

  const deletePattern = (patternName: string) => {
    if (connection) {
      FilteredNotificationsConfigurationsClient.deleteConnectionFilteredNotificationConfiguration(
        connection,
        patternName
      ).then(() => {
        onChange(
          filteredNotificationsConfigurations.filter(
            (pattern) => pattern.name !== patternName
          )
        );
      });
    } else {
      FilteredNotificationsConfigurationsClient.deleteDefaultFilteredNotificationConfiguration(
        patternName
      ).then(() => {
        onChange(
          filteredNotificationsConfigurations.filter(
            (pattern) => pattern.name !== patternName
          )
        );
      });
    }
  };

  return (
    <table className="text-sm">
      <thead>
        <tr>
          {headerElements.map((elem, index) => (
            <th className="" key={elem.label}>
              <div className="flex gap-x-1 items-center cursor-default">
                <div
                  className={
                    elem.key === 'action'
                      ? filteredNotificationsConfigurations.length === 0
                        ? ''
                        : 'ml-4'
                      : ''
                  }
                >
                  {elem.label}
                </div>
                {elem.key !== 'action' && (
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
                )}
              </div>
            </th>
          ))}
        </tr>
      </thead>
      <tbody className=" border-t border-gray-100">
        {[
          ...filteredNotificationsConfigurations,
          {
            name: 'default'
          }
        ].map((notificationPattern, index) => (
          <tr key={index} className="text-sm">
            <td
              className={clsx(
                'underline cursor-pointer',
                notificationPattern.disabled && 'text-gray-200'
              )}
              onClick={() => setPatternNameEdit(notificationPattern.name ?? '')}
            >
              {notificationPattern.name}
            </td>
            <td>{notificationPattern.priority}</td>
            <td>{notificationPattern?.connection}</td>
            <td>{notificationPattern?.schema}</td>
            <td>{notificationPattern?.table}</td>
            <td>{notificationPattern?.qualityDimension}</td>
            <td>{notificationPattern?.checkCategory}</td>
            <td>{notificationPattern?.checkName}</td>
            <td>{notificationPattern?.checkType}</td>
            <td>
              {getSeverity(String(notificationPattern?.highestSeverity ?? ''))}
            </td>
            <td>
              <div className="flex items-center gap-x-4 my-0.5">
                <IconButton
                  onClick={() =>
                    setPatternNameEdit(notificationPattern.name ?? '')
                  }
                  size="sm"
                  color="teal"
                  className={clsx(
                    '!shadow-none hover:!shadow-none hover:bg-[#028770]',
                    filteredNotificationsConfigurations.length === 0 && 'ml-1.5'
                  )}
                >
                  <SvgIcon name="edit" className="w-4" />
                </IconButton>
                {notificationPattern.name !== 'default' && (
                  <IconButton
                    onClick={() =>
                      setPatternDelete(notificationPattern.name ?? '')
                    }
                    size="sm"
                    color="teal"
                    className="!shadow-none hover:!shadow-none hover:bg-[#028770]"
                  >
                    <SvgIcon name="delete" className="w-4" />
                  </IconButton>
                )}
              </div>
            </td>
          </tr>
        ))}
        <ConfirmDialog
          open={notificationPatternDelete.length > 0}
          onConfirm={async () => {
            deletePattern(notificationPatternDelete ?? ''),
              setPatternDelete('');
          }}
          onClose={() => setPatternDelete('')}
          message={`Are you sure you want to delete the ${notificationPatternDelete} notification filter?`}
        />
      </tbody>
    </table>
  );
}
