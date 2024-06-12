import moment from 'moment';
import React from 'react';
import { useHistory } from 'react-router-dom';
import { IncidentModel } from '../../../api';
import { ROUTES } from '../../../shared/routes';

type GlobalIncidentsDashboardTableProps = {
  group: string;
  incidents: IncidentModel[];
  groupBy: 'dimension' | 'category' | 'connection' | undefined;
};
export default function GlobalIncidentsDashboardTable({
  group,
  incidents,
  groupBy
}: GlobalIncidentsDashboardTableProps) {
  const history = useHistory();
  const goToIncidents = (incident: IncidentModel) => {
    const url = ROUTES.INCIDENT_DETAIL(
      incident.connection ?? '',
      incident.year ?? 0,
      incident.month ?? 0,
      incident.incidentId ?? ''
    );
    history.push(url);
  };

  if (incidents.length === 0) {
    return null;
  }

  return (
    <div
      className="border border-gray-150 p-2 rounded-md text-xs w-full"
      style={{ width: window.innerWidth > 2060 ? '1030px' : '100%' }}
    >
      <div className="flex items-center justify-between pl-4 py-2 border-b border-gray-300 mb-2 text-md font-semibold">
        <div>{group}</div>
        <div>
          {/* <Button label="Show more" color="primary" className="text-sm" /> */}
        </div>
      </div>
      <div className="overflow-auto w-full">
        <table className="w-full">
          <thead>
            <tr>
              <th
                className="py-2 px-4 text-left whitespace-nowrap"
                style={{ width: '12.5%' }}
              >
                Connection
              </th>
              <th
                className="py-2 px-4 text-left whitespace-nowrap"
                style={{ width: '12.5%' }}
              >
                Schema
              </th>
              <th
                className="py-2 px-4 text-left whitespace-nowrap"
                style={{ width: '12.5%' }}
              >
                Table
              </th>
              {groupBy === 'dimension' ? (
                <th
                  className="py-2 px-4 text-left whitespace-nowrap"
                  style={{ width: '12.5%' }}
                >
                  Check category
                </th>
              ) : (
                <th
                  className="py-2 px-4 text-left whitespace-nowrap"
                  style={{ width: '12.5%' }}
                >
                  Quality dimension
                </th>
              )}

              <th
                className="py-2 px-4 text-left whitespace-nowrap"
                style={{ width: '12.5%' }}
              >
                First seen
              </th>
              <th
                className="py-2 px-4 text-left whitespace-nowrap"
                style={{ width: '12.5%' }}
              >
                Last seen
              </th>
              {/* <th className="py-2 px-4 text-left whitespace-nowrap">
            Incident id
          </th> */}
            </tr>
          </thead>
          <tbody>
            {incidents.map((incident) => (
              <tr
                key={incident.incidentId}
                className="py-2 border-b border-gray-300"
              >
                <td
                  className="py-2 px-4 underline cursor-pointer truncate"
                  onClick={() => goToIncidents(incident)}
                >
                  {incident.connection}
                </td>
                <td
                  className="py-2 px-4 underline cursor-pointer truncate"
                  onClick={() => goToIncidents(incident)}
                >
                  {incident.schema}
                </td>
                <td
                  className="py-2 px-4 underline cursor-pointer truncate"
                  onClick={() => goToIncidents(incident)}
                >
                  {incident.table}
                </td>
                {groupBy === 'dimension' ? (
                  <td className="py-2 px-4 truncate">
                    {incident.checkCategory}
                  </td>
                ) : (
                  <td className="py-2 px-4 truncate">
                    {incident.qualityDimension}
                  </td>
                )}
                <td className="py-2 px-4 truncate">
                  {moment(incident.firstSeen).format('YYYY-MM-DD')}
                </td>
                <td className="py-2 px-4 truncate">
                  {moment(incident.lastSeen).format('YYYY-MM-DD')}
                </td>
                {/* <td className="py-2 px-4">{incident.incidentId}</td> */}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
