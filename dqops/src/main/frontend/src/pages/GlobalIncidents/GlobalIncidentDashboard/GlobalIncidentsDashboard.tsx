import React, { useEffect, useState } from 'react';
import { TopIncidentsModel } from '../../../api';
import GlobalIncidentsDashboardTable from './GlobalIncidentsDashboardTable';

export default function GlobalIncidentsDashboard({
  incidents,
  groupBy
}: {
  incidents: TopIncidentsModel;
  groupBy: 'dimension' | 'category' | 'connection' | undefined;
}) {
  const groups = incidents.topIncidents || {};
  const getWidth = () => {
    if (window.innerWidth < 1800) {
      return '100%';
    } else {
      const amount = Math.floor(window.innerWidth / 900);
      return `${window.innerWidth / amount}px`;
    }
  };

  const getMaxHeight = () => {
    return Object.entries(groups)
      .filter(([, value]) => value.length !== 0)
      .reduce((maxHeight, [, value]) => {
        return value.length * 33 > maxHeight ? value.length * 33 : maxHeight;
      }, 0);
  };

  const [maxHeight, setMaxHeight] = useState(0);

  useEffect(() => {
    setMaxHeight(getMaxHeight());
  }, [incidents]);

  return (
    <div className="flex flex-wrap pt-4">
      {Object.entries(groups)
        .filter(([, value]) => value.length !== 0)
        .map(([key, value]) => {
          return (
            <div key={key} style={{ width: getWidth(), padding: '8px' }}>
              <GlobalIncidentsDashboardTable
                group={key}
                incidents={value}
                groupBy={groupBy}
                maxHeight={maxHeight + 95}
              />
            </div>
          );
        })}
    </div>
  );
}
