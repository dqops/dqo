import { IconButton, Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import React, { useEffect, useMemo, useState } from 'react';
import { FilteredNotificationModel } from '../../../../api';
import { FilteredNotificationsConfigurationsClient } from '../../../../services/apiClient';
import { getSeverity, sortPatterns } from '../../../../utils';
import ConfirmDialog from '../../../CustomTree/ConfirmDialog';
import Loader from '../../../Loader';
import ClientSidePagination from '../../../Pagination/ClientSidePagination';
import SvgIcon from '../../../SvgIcon';

const HEADER_ELEMENTS = [
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
  connection,
  loading
}: {
  filteredNotificationsConfigurations: Array<TNotificationPattern>;
  onChange: (data: any) => void;
  setPatternNameEdit: (patternName: string) => void;
  connection?: string;
  loading?: boolean;
}) {
  const [dir, setDir] = useState<'asc' | 'desc'>('asc');
  const [notificationPatternDelete, setPatternDelete] = useState('');
  const [indexSortingElement, setIndexSortingElement] = useState(1);
  const [headerItems, setHeaderItems] = useState(HEADER_ELEMENTS);
  const [displayedPatterns, setDisplayedPatterns] = useState<any[]>([]); // State for displayed patterns

  const getFilteredItemsBasedOnWidth = () => {
    const width = window.innerWidth;

    return HEADER_ELEMENTS.filter((item) => {
      if (
        width < 1800 &&
        [
          'qualityDimension',
          'checkCategory',
          'checkName',
          'checkType',
          'highestSeverity'
        ].includes(item.key)
      )
        return false;
      if (
        width < 1900 &&
        ['checkCategory', 'checkName', 'checkType', 'highestSeverity'].includes(
          item.key
        )
      )
        return false;
      if (width < 2000 && ['checkType', 'highestSeverity'].includes(item.key))
        return false;
      return true;
    });
  };

  const updateHeaderItems = () => {
    setHeaderItems(getFilteredItemsBasedOnWidth());
  };

  useEffect(() => {
    updateHeaderItems();
    window.addEventListener('resize', updateHeaderItems);
    return () => window.removeEventListener('resize', updateHeaderItems);
  }, []);

  const handleSort = (elem: { label: string; key: string }, index: number) => {
    const newDir = dir === 'asc' ? 'desc' : 'asc';
    onChange(
      sortPatterns(
        filteredNotificationsConfigurations,
        elem.key as keyof TNotificationPattern,
        newDir
      )
    );
    setDir(newDir);
    setIndexSortingElement(index);
  };

  const handleDeletePattern = (patternName: string) => {
    const deleteAction = connection
      ? FilteredNotificationsConfigurationsClient.deleteConnectionFilteredNotificationConfiguration(
          connection,
          patternName
        )
      : FilteredNotificationsConfigurationsClient.deleteDefaultFilteredNotificationConfiguration(
          patternName
        );

    deleteAction.then(() => {
      onChange(
        filteredNotificationsConfigurations.filter(
          (pattern) => pattern.name !== patternName
        )
      );
    });
  };

  const sortedNotifications = useMemo(
    () => [...filteredNotificationsConfigurations, { name: 'default' }],
    [filteredNotificationsConfigurations]
  );
  if (loading) {
    return (
      <div className="w-full h-screen flex justify-center items-center">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }
  return (
    <>
      <table className="text-sm">
        <thead className="!mb-1">
          <tr>
            {headerItems.map((elem, index) => (
              <th key={elem.label}>
                <div
                  className={clsx(
                    'flex gap-x-1 items-center cursor-default',
                    elem.key === 'priority' &&
                      'text-right flex items-center justify-end !max-w-10'
                  )}
                >
                  <div
                    className={clsx(
                      elem.key === 'action' &&
                        filteredNotificationsConfigurations.length > 0 &&
                        'ml-4',
                      elem.key === 'priority' &&
                        'text-right flex items-center justify-end !max-w-10'
                    )}
                  >
                    {elem.label}
                  </div>
                  {elem.key !== 'action' && (
                    <div>
                      {!(indexSortingElement === index && dir === 'asc') ? (
                        <SvgIcon
                          name="chevron-up"
                          className="w-2 h-2 text-black cursor-pointer"
                          onClick={() => handleSort(elem, index)}
                        />
                      ) : (
                        <div className="w-2 h-2" />
                      )}
                      {!(indexSortingElement === index && dir === 'desc') ? (
                        <SvgIcon
                          name="chevron-down"
                          className="w-2 h-2 text-black cursor-pointer"
                          onClick={() => handleSort(elem, index)}
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
        <div className="w-full h-1.5"></div>
        <tbody className="border-t border-gray-100 mt-1">
          <div className="w-full h-1"></div>
          {displayedPatterns.map((notificationPattern, index) => (
            <tr key={index} className="text-sm">
              {getFilteredItemsBasedOnWidth().map((elem) => (
                <td
                  key={elem.key}
                  className={clsx(
                    elem.key === 'name' && 'underline cursor-pointer',
                    notificationPattern.disabled && 'text-gray-200',
                    'max-w-40 truncate'
                  )}
                  onClick={() =>
                    elem.key === 'name' &&
                    setPatternNameEdit(notificationPattern.name ?? '')
                  }
                >
                  {elem.key === 'name' && notificationPattern.name}
                  {elem.key === 'priority' && (
                    <div className="text-right !max-w-10">
                      {notificationPattern.priority}{' '}
                    </div>
                  )}
                  {elem.key === 'connection' && notificationPattern.connection}
                  {elem.key === 'schema' && notificationPattern.schema}
                  {elem.key === 'table' && notificationPattern.table}
                  {elem.key === 'qualityDimension' &&
                    notificationPattern.qualityDimension}
                  {elem.key === 'checkCategory' &&
                    notificationPattern.checkCategory}
                  {elem.key === 'checkName' && notificationPattern.checkName}
                  {elem.key === 'checkType' && notificationPattern.checkType}
                  {elem.key === 'highestSeverity' &&
                    getSeverity(
                      String(notificationPattern.highestSeverity ?? '')
                    )}
                  {elem.key === 'action' && (
                    <div className="flex items-center gap-x-4 my-0.5">
                      <IconButton
                        ripple={false}
                        onClick={() =>
                          setPatternNameEdit(notificationPattern.name ?? '')
                        }
                        size="sm"
                        color="teal"
                        className={clsx(
                          '!shadow-none hover:!shadow-none hover:bg-[#028770]',
                          filteredNotificationsConfigurations.length === 0 &&
                            'ml-1.5'
                        )}
                      >
                        <Tooltip content="Modify">
                          <div>
                            <SvgIcon name="edit" className="w-4" />
                          </div>
                        </Tooltip>
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
                          <Tooltip content="Delete">
                            <div>
                              <SvgIcon name="delete" className="w-4" />
                            </div>
                          </Tooltip>
                        </IconButton>
                      )}
                    </div>
                  )}
                </td>
              ))}
            </tr>
          ))}

          <ConfirmDialog
            open={notificationPatternDelete.length > 0}
            onConfirm={async () => {
              handleDeletePattern(notificationPatternDelete);
              setPatternDelete('');
            }}
            onClose={() => setPatternDelete('')}
            message={`Are you sure you want to delete the ${notificationPatternDelete} notification filter?`}
          />
        </tbody>
      </table>
      <div className="w-full">
        <ClientSidePagination
          items={sortedNotifications}
          onChangeItems={setDisplayedPatterns}
          className="pl-0 !w-full pr-4"
        />
      </div>
    </>
  );
}
