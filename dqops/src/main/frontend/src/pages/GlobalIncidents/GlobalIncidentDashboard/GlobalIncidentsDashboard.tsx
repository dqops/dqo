import React from 'react';
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
    if (window.innerWidth < 2060) {
      return '100%';
    } else {
      const amount = Math.floor(window.innerWidth / 1030);
      return `${window.innerWidth / amount}px`;
    }
  };
  console.log(
    Object.entries(groups).filter(([_, value]) => value.length !== 0)
  );

  return (
    <div className="flex flex-wrap pt-4">
      {Object.entries(groups)
        .filter(([_, value]) => value.length !== 0)
        .map(([key, value]) => {
          return (
            <div key={key} style={{ width: getWidth(), padding: '8px' }}>
              <GlobalIncidentsDashboardTable
                group={key}
                incidents={value}
                groupBy={groupBy}
              />
            </div>
          );
        })}
    </div>
  );
}
