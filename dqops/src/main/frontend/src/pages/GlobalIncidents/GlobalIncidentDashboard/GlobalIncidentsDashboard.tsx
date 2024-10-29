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

  return (
    <div className="flex flex-wrap pt-1 grid lg:grid-cols-1 screen1900:grid-cols-2">
      {Object.entries(groups)
        .filter(([, value]) => value.length !== 0)
        .map(([key, value]) => {
          return (
            <div key={key} className="p-2">
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
