import moment from 'moment';
import React from 'react';
import { useHistory } from 'react-router-dom';
import { IncidentModel } from '../../../api';
import { ROUTES } from '../../../shared/routes';

type GlobalIncidentsDashboardTableProps = {
  group: string;
  incidents: IncidentModel[];
};
export default function GlobalIncidentsDashboardTable({
  group,
  incidents
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
      className="border border-gray-150 p-2 rounded-md text-sm w-full"
      style={{ width: '1030px' }}
    >
      <div className="flex items-center justify-between pl-4 py-2 border-b border-gray-300 mb-2 text-md font-semibold">
        <div>{group}</div>
        <div>
          {/* <Button label="Show more" color="primary" className="text-sm" /> */}
        </div>
      </div>
      <div className="overflow-auto ">
        <table style={{ width: '1000px' }}>
          <thead>
            <tr>
              <th
                className="py-2 px-4 text-left whitespace-nowrap"
                style={{ width: '150px' }}
              >
                Connection
              </th>
              <th
                className="py-2 px-4 text-left whitespace-nowrap"
                style={{ width: '150px' }}
              >
                Schema
              </th>
              <th
                className="py-2 px-4 text-left whitespace-nowrap"
                style={{ width: '150px' }}
              >
                Table
              </th>
              <th
                className="py-2 px-4 text-left whitespace-nowrap"
                style={{ width: '150px' }}
              >
                Quality dimension
              </th>
              <th
                className="py-2 px-4 text-left whitespace-nowrap"
                style={{ width: '150px' }}
              >
                Check category
              </th>
              <th
                className="py-2 px-4 text-left whitespace-nowrap"
                style={{ width: '125px' }}
              >
                First seen
              </th>
              <th
                className="py-2 px-4 text-left whitespace-nowrap"
                style={{ width: '125px' }}
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
                  style={{ maxWidth: '150px' }}
                  onClick={() => goToIncidents(incident)}
                >
                  {incident.connection}
                </td>
                <td
                  className="py-2 px-4 underline cursor-pointer truncate"
                  style={{ maxWidth: '150px' }}
                  onClick={() => goToIncidents(incident)}
                >
                  {incident.schema}
                </td>
                <td
                  className="py-2 px-4 underline cursor-pointer truncate"
                  style={{ maxWidth: '150px' }}
                  onClick={() => goToIncidents(incident)}
                >
                  {incident.table}
                </td>
                <td
                  className="py-2 px-4 truncate"
                  style={{ maxWidth: '150px' }}
                >
                  {incident.qualityDimension}
                </td>
                <td
                  className="py-2 px-4 truncate"
                  style={{ maxWidth: '150px' }}
                >
                  {incident.checkCategory}
                </td>
                <td
                  className="py-2 px-4 truncate"
                  style={{ maxWidth: '125px' }}
                >
                  {moment(incident.firstSeen).format('YYYY-MM-DD')}
                </td>
                <td
                  className="py-2 px-4 truncate"
                  style={{ maxWidth: '125px' }}
                >
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
