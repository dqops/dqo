import React, { useMemo, useState } from "react";
import { Popover, PopoverContent, PopoverHandler } from "@material-tailwind/react";
import Checkbox from "../../components/Checkbox";
import SvgIcon from "../../components/SvgIcon";
import { useSelector } from "react-redux";
import { getFirstLevelIncidentsState } from "../../redux/selectors";
import { useActionDispatch } from "../../hooks/useActionDispatch";
import { setIncidentsFilter } from "../../redux/actions/incidents.actions";
import { IncidentFilter } from "../../redux/reducers/incidents.reducer";

const StatusSelect = () => {
  const [isOpen, setIsOpen] = useState(false);
  const { filters = { connection: '' } }: { filters: IncidentFilter} = useSelector(getFirstLevelIncidentsState);
  const dispatch = useActionDispatch();

  const toggleOpen = () => {
    setIsOpen(!isOpen);
  };

  const onChangeFilter = (obj: Partial<IncidentFilter>) => {
    dispatch(setIncidentsFilter({
      ...filters || {},
      ...obj
    }));
  };

  const valueString = useMemo(() => {
    const strArray: string[] = [];
    if (filters.openIncidents) {
      strArray.push('Open');
    }
    if (filters.acknowledgeIncidents) {
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
        <div className="flex items-center relative text-primary whitespace-nowrap cursor-pointer">
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
              checked={filters.acknowledgeIncidents}
              label="Acknowledged"
              onChange={(checked) => onChangeFilter({ acknowledgeIncidents: checked })}
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
