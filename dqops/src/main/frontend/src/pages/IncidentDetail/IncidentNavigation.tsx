import React from "react";
import clsx from "clsx";
import { useDispatch } from "react-redux";
import { useHistory } from "react-router-dom";
import { CheckTypes, ROUTES } from "../../shared/routes";
import { addFirstLevelTab } from "../../redux/actions/source.actions";
import SvgIcon from "../../components/SvgIcon";
import { IncidentModel } from "../../api";

type NavigationMenu = {
  label: string;
  value: CheckTypes;
}

const navigations: NavigationMenu[] = [
  {
    label: 'Table metadata',
    value: CheckTypes.SOURCES
  },
  {
    label: 'Profiling',
    value: CheckTypes.PROFILING
  },
  {
    label: 'Monitoring checks',
    value: CheckTypes.MONITORING
  },
  {
    label: 'Partition checks',
    value: CheckTypes.PARTITIONED
  },
];

type TableNavigationProps = {
  incident?: IncidentModel
};

const TableNavigation = ({ incident }: TableNavigationProps) => {
  const dispatch = useDispatch();
  const history = useHistory();
  const connection = incident?.connection || '';
  const schema = incident?.schema || '';
  const table = incident?.table || '';

  const onChangeNavigation = async (item: NavigationMenu) => {
    const value = ROUTES.TABLE_LEVEL_VALUE(item.value, connection, schema, table);

    const tab = item.value === CheckTypes.MONITORING || item.value === CheckTypes.PARTITIONED ? 'daily' : 'detail';
    const url = ROUTES.TABLE_LEVEL_PAGE(item.value, connection, schema, table, tab);

    dispatch(addFirstLevelTab(item.value, {
      url,
      value,
      state: {},
      label: table
    }))

    history.push(url);
  };

  return (
    <div className="flex space-x-3 px-4 py-4 border-b border-gray-300 mb-2">
      {navigations.map((item, index) => (
        <div
          className={clsx("flex items-center cursor-pointer w-70")}
          key={item.value}
          onClick={() => onChangeNavigation(item)}
        >
          <SvgIcon name="chevron-left" className="w-3 mr-2" />
          <span>{item.label}</span>
        </div>
      ))}
    </div>
  );
};

export default TableNavigation;