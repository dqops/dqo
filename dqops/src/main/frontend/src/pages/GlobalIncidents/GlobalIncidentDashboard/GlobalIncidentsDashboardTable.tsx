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
    <div className="border border-gray-150 p-2 rounded-md text-sm">
      <div className="flex items-center justify-between pl-4 py-2 border-b border-gray-300 mb-2 text-md font-semibold">
        <div>{group}</div>
        <div>
          {/* <Button label="Show more" color="primary" className="text-sm" /> */}
        </div>
      </div>
      <table>
        <thead>
          <tr>
            <th className="py-2 px-4 text-left whitespace-nowrap">
              Connection
            </th>
            <th className="py-2 px-4 text-left whitespace-nowrap">Schema</th>
            <th className="py-2 px-4 text-left whitespace-nowrap">Table</th>
            <th className="py-2 px-4 text-left whitespace-nowrap">
              Quality dimension
            </th>
            <th className="py-2 px-4 text-left whitespace-nowrap">
              Check category
            </th>
            <th className="py-2 px-4  text-left whitespace-nowrap">
              First seen
            </th>
            <th className="py-2 px-4 text-left whitespace-nowrap">Last seen</th>
            {/* <th className="py-2 px-4 text-left whitespace-nowrap">
              Incident id
            </th> */}
          </tr>
        </thead>
        <tbody>
          {incidents.map((incident) => (
            <tr
              key={incident.incidentId}
              className="py-2 border-b border-gray-300 pl-2"
            >
              <td
                className="py-2 px-4 underline cursor-pointer"
                onClick={() => goToIncidents(incident)}
              >
                {incident.connection}
              </td>
              <td
                className="py-2 px-4 underline cursor-pointer"
                onClick={() => goToIncidents(incident)}
              >
                {incident.schema}
              </td>
              <td
                className="py-2 px-4 underline cursor-pointer"
                onClick={() => goToIncidents(incident)}
              >
                {incident.table}
              </td>
              <td className="py-2 px-4">{incident.qualityDimension}</td>
              <td className="py-2 px-4">{incident.checkCategory}</td>
              <td className="py-2 px-4">
                {moment(incident.firstSeen).format('YYYY-MM-DD')}
              </td>
              <td className="py-2 px-4">
                {moment(incident.lastSeen).format('YYYY-MM-DD')}
              </td>
              {/* <td className="py-2 px-4">{incident.incidentId}</td> */}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
