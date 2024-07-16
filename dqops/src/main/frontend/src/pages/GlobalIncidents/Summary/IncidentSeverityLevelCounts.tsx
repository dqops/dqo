import React from "react";
import { IncidentSeverityLevelCountsModel, TopIncidentsModel } from "../../../api";
import SectionWrapper from "../../../components/Dashboard/SectionWrapper";
import moment from 'moment';
import Gauge from "./Gauge";

export default function IncidentSeverityLevelCounts({
  incidentSeverityLevelCounts,
  title,
  icon,
  incidentStatus
}: {
  incidentSeverityLevelCounts: IncidentSeverityLevelCountsModel | undefined,
  title: string,
  icon: React.ReactNode
  incidentStatus: string
}) {

  return (
    <SectionWrapper 
      className={"flex items-center grid grid-cols-3 "}
      title={title}
      titleIcon={icon}
    >
      <Gauge 
        incidentCounts={incidentSeverityLevelCounts?.warningCounts}
        colorClassName="border-yellow-500"
        title="Warnings"
        severity={1}
        status={incidentStatus}
      />
      <Gauge 
        incidentCounts={incidentSeverityLevelCounts?.errorCounts} 
        colorClassName="border-orange-500"
        title="Errors"
        severity={2}
        status={incidentStatus}
      />
      <Gauge 
        incidentCounts={incidentSeverityLevelCounts?.fatalCounts}
        colorClassName="border-red-500"
        title="Fatal errors"
        severity={3}
        status={incidentStatus}
      />
    </SectionWrapper>
  );

}