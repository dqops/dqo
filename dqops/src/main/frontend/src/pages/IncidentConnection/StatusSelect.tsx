import { Popover, PopoverContent, PopoverHandler } from "@material-tailwind/react";
import React, { useMemo, useState } from "react";
import { useSelector } from "react-redux";
import Checkbox from "../../components/Checkbox";
import SvgIcon from "../../components/SvgIcon";
import { IncidentFilter } from "../../redux/reducers/incidents.reducer";
import { getFirstLevelIncidentsState } from "../../redux/selectors";

type StatusSelectProps = {
  onChangeFilter: (obj: Partial<IncidentFilter>) => void;
};

const StatusSelect = ({ onChangeFilter }: StatusSelectProps) => {
  const [isOpen, setIsOpen] = useState(false);
  const { filters = { connection: '', openIncidents: false, acknowledgedIncidents: false, resolvedIncidents: false } }: { filters: IncidentFilter} = useSelector(getFirstLevelIncidentsState);

  const toggleOpen = () => {
    setIsOpen(!isOpen);
  };

  const valueString = useMemo(() => {
    const strArray: string[] = [];
    if (filters.openIncidents) {
      strArray.push('Open');
    }
    if (filters.acknowledgedIncidents) {
      strArray.push('Acknowledged');
    }
    if (filters.resolvedIncidents) {
      strArray.push('Resolved');
    }
    if (filters.mutedIncidents) {
      strArray.push('Muted');
    }

    return strArray.join(', ');
  }, [filters]);

  return (
    <Popover placement="bottom-end" open={isOpen} handler={toggleOpen}>
      <PopoverHandler>
        <div className="flex items-center relative text-primary whitespace-nowrap cursor-pointer text-sm">
          {valueString ? valueString : 'Select Status'}
          <SvgIcon name="chevron-down" className="w-4 ml-2" />
        </div>
      </PopoverHandler>
      <PopoverContent className="z-50 px-0">
        <div className="px-4">
          <div className="flex flex-col gap-3 text-primary">
            <Checkbox
              checked={filters.openIncidents}
              label="Open"
              onChange={(checked) => onChangeFilter({ openIncidents: checked })}
            />
            <Checkbox
              checked={filters.acknowledgedIncidents}
              label="Acknowledged"
              onChange={(checked) => onChangeFilter({ acknowledgedIncidents: checked })}
            />
            <Checkbox
              checked={filters.resolvedIncidents}
              label="Resolved"
              onChange={(checked) => onChangeFilter({ resolvedIncidents: checked })}
            />
            <Checkbox
              checked={filters.mutedIncidents}
              label="Muted"
              onChange={(checked) => onChangeFilter({ mutedIncidents: checked })}
            />
          </div>
        </div>
      </PopoverContent>
    </Popover>
  );
};

export default StatusSelect;
