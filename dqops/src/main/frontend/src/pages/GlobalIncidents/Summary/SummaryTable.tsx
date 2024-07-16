import React from "react";
import { TopIncidentsModel } from "../../../api";
import SectionWrapper from "../../../components/Dashboard/SectionWrapper";
import IncidentSeverityLevelCounts from "./IncidentSeverityLevelCounts";
import SvgIcon from "../../../components/SvgIcon";

export default function Summary({
  incidents
}: {
  incidents: TopIncidentsModel
}) {

  return (
    <div className="flex flex-wrap mt-4 ml-2 p-1 grid grid-cols-1 lg:grid-cols-2">
      <IncidentSeverityLevelCounts 
        incidentSeverityLevelCounts={incidents.openIncidentSeverityLevelCounts} 
        title="Open incidents"
        icon={<SvgIcon name="info-filled" className="text-red-900 w-6 h-6" />}
        incidentStatus="open"
      />
      <IncidentSeverityLevelCounts 
        incidentSeverityLevelCounts={incidents.acknowledgedIncidentSeverityLevelCounts} 
        title="Acknowledged incidents"
        icon={<div className="w-5 h-5 rounded-full bg-black" />}
        incidentStatus="acknowledged"
      />
    </div>
  );

}