import { Tooltip } from '@material-tailwind/react';
import moment from 'moment';
import React from 'react';
import { useHistory } from 'react-router-dom';
import { IncidentModel } from '../../../api';
import { ROUTES } from '../../../shared/routes';

type GlobalIncidentsDashboardTableProps = {
  group: string;
  incidents: IncidentModel[];
  groupBy: 'dimension' | 'category' | 'connection' | undefined;
  maxHeight?: number;
};

const tableHeaderClassName = "py-2 px-2 text-left whitespace-nowrap screen2000:col-span-2 ";
const tableRowClassName = "px-2 truncate screen2000:col-span-2 ";
const tableMinWidth = '800px'

export default function GlobalIncidentsDashboardTable({
  group,
  incidents,
  groupBy,
  maxHeight
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

  const showMore = () => {
    // history.push(ROUTES.INCIDENT_CONNECTION);
  };

  return (
    <div
      className="border border-gray-150 p-2 rounded-md text-xs"
      style={{ maxHeight: `${maxHeight}px` }}
    >
      <div className="flex items-center justify-between pl-4 py-2 border-b border-gray-300 mb-2 text-md font-semibold">
        <div>
          {groupBy === 'dimension'
            ? incidents[0].qualityDimension
            : incidents[0].checkCategory}
        </div>
      </div>
      <div className="overflow-auto w-full">
          <div className='grid grid-cols-12 font-bold min-w-800' style={{ minWidth: tableMinWidth}}>
            <div className={tableHeaderClassName + ' col-span-2'}>
              Connection
            </div>
            <div className={tableHeaderClassName + ' col-span-2'}>
              Schema
            </div>
            <div className={tableHeaderClassName + ' col-span-2 2xl:col-span-5'}>
              Table
            </div>
            <div className={tableHeaderClassName + ' col-span-2 2xl:col-span-1'}>
              {groupBy === 'dimension' ? (
                <>Check category</>
              ) : (
                <>Quality dimension</>
              )}
              </div>

            <div className={tableHeaderClassName + 'col-span-2 2xl:col-span-1'}>
              First seen
            </div>
            <div className={tableHeaderClassName + 'col-span-2 2xl:col-span-1'}>
              Last seen
            </div>
          </div>
          <div>
            {incidents.map((incident) => (
              <div
                key={incident.incidentId}
                className="py-2 border-b border-gray-300 grid grid-cols-12"
              >
                <div className={tableRowClassName + "col-span-2"}>
                  <Tooltip
                    content={incident.connection}
                    placement="top"
                    color="light"
                  >
                    {incident.connection}
                  </Tooltip>
                </div>
                <div className={tableRowClassName + "col-span-2"}>
                  <Tooltip
                    content={incident.schema}
                    placement="top"
                    color="light"
                  >
                    {incident.schema}
                  </Tooltip>
                </div>
                <div
                  className={tableRowClassName + "underline cursor-pointer col-span-2 2xl:col-span-5"}
                  onClick={() => goToIncidents(incident)}
                >
                  <Tooltip
                    content={incident.table}
                    placement="top"
                    color="light"
                  >
                    {incident.table}
                  </Tooltip>
                </div>
                <div className={tableRowClassName + "col-span-2 2xl:col-span-1"}>
                  {groupBy === 'dimension' ? (
                    <>{incident.checkCategory}</>
                  ) : (
                    <>{incident.qualityDimension}</>
                  )}
                </div>
                <div className={tableRowClassName + "col-span-2 2xl:col-span-1"}>
                  {moment(incident.firstSeen).format('YYYY-MM-DD')}
                </div>
                <div className={tableRowClassName + "col-span-2 2xl:col-span-1"}>
                  {moment(incident.lastSeen).format('YYYY-MM-DD')}
                </div>
              </div>
            ))}
        </div>
      </div>
    </div>
  );
}
