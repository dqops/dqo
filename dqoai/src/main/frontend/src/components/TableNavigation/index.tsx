import React, { useMemo } from "react";
import { CheckTypes, ROUTES } from "../../shared/routes";
import clsx from "clsx";
import SvgIcon from "../SvgIcon";
import { addFirstLevelTab } from "../../redux/actions/source.actions";
import { useDispatch } from "react-redux";
import { useHistory, useParams } from "react-router-dom";

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
    label: 'Advanced profiling',
    value: CheckTypes.PROFILING
  },
  {
    label: 'Recurring checks',
    value: CheckTypes.RECURRING
  },
  {
    label: 'Partition checks',
    value: CheckTypes.PARTITIONED
  },
];

type TableNavigationProps = {
  defaultTab?: string;
};

const TableNavigation = ({ defaultTab }: TableNavigationProps) => {
  const dispatch = useDispatch();
  const { connection, schema, table, tab: activeTab, checkTypes }: { connection: string, schema: string, table: string, tab: string, checkTypes: CheckTypes } = useParams();
  const history = useHistory();

  const activeIndex = useMemo(() => {
    return navigations.findIndex((item) => item.value === checkTypes);
  }, [checkTypes]);

  const onChangeNavigation = async (item: NavigationMenu) => {
    let url = ROUTES.TABLE_LEVEL_PAGE(item.value, connection, schema, table, 'detail');
    let value = ROUTES.TABLE_LEVEL_VALUE(item.value, connection, schema, table);

    if (defaultTab) {
      if (item.value === CheckTypes.RECURRING) {
        url = ROUTES.TABLE_RECURRING(item.value, connection, schema, table, defaultTab);
        value = ROUTES.TABLE_RECURRING_VALUE(item.value, connection, schema, table);
      } else if (item.value === CheckTypes.PARTITIONED) {
        url = ROUTES.TABLE_PARTITIONED(item.value, connection, schema, table, defaultTab);
        value = ROUTES.TABLE_PARTITIONED_VALUE(item.value, connection, schema, table);
      }
    } else {
      const tab = item.value === CheckTypes.RECURRING || item.value === CheckTypes.PARTITIONED ? 'daily' : 'detail';
      url = ROUTES.TABLE_LEVEL_PAGE(item.value, connection, schema, table, tab);
    }

    dispatch(addFirstLevelTab(item.value, {
      url,
      value,
      state: {},
      label: table
    }))

    history.push(url);
  };

  return (
    <div className="flex space-x-3 px-4 pt-2 border-b border-gray-300 pb-4 mb-2">
      {navigations.map((item, index) => (
        <div
          className={clsx("flex items-center cursor-pointer w-70", activeIndex === index ? "font-bold" : "")}
          key={item.value}
          onClick={() => onChangeNavigation(item)}
        >
          {activeIndex > index ? <SvgIcon name="chevron-left" className="w-3 mr-2" /> : ''}
          <span>{item.label}</span>
          {activeIndex < index ? <SvgIcon name="chevron-right" className="w-6 ml-2" /> : ''}
        </div>
      ))}
    </div>
  );
};

export default TableNavigation;