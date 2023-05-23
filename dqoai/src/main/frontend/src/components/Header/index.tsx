import React, {useState} from 'react';
import NotificationMenu from '../NotificationMenu';
import Logo from '../Logo';
import clsx from 'clsx';
import { useHistory, useLocation, useParams, useRouteMatch } from 'react-router-dom';
import { CheckTypes, ROUTES } from "../../shared/routes";
import HelpMenu from "../HelpMenu";
import { useSelector } from "react-redux";
import { useActionDispatch } from "../../hooks/useActionDispatch";
import { addFirstLevelTab } from "../../redux/actions/source.actions";
import { IRootState } from "../../redux/reducers";
import { COLUMN_LEVEL_TABS, CONNECTION_LEVEL_TABS, PageTab, TABLE_LEVEL_TABS } from "../../shared/constants";
import { SynchronizeButton } from "./SynchronizeButton";
import SvgIcon from '../SvgIcon';

const Header = () => {
  const history = useHistory();
  const location = useLocation();
  const { checkTypes, connection, schema, table, column, tab }: {
    checkTypes: CheckTypes,
    connection: string,
    schema: string,
    table: string,
    column: string;
    tab: string;
    timePartitioned: 'daily' | 'monthly',
    category: string;
    checkName: string;
  } = useParams();

  const dispatch = useActionDispatch();
  const { tabs, activeTab } = useSelector((state: IRootState) => state.source[checkTypes || CheckTypes.SOURCES]);
  const selectedTab = tabs?.find((item) => item.url === activeTab);
  const match = useRouteMatch();
  const [showProfile, setShowProfile] = useState<boolean>(false)
  const onClick = (newCheckTypes: CheckTypes) => () => {
    let url = '';
    let value = '';

    if (match.path === ROUTES.PATTERNS.CONNECTION) {
      let newTab = CONNECTION_LEVEL_TABS[newCheckTypes].find((item: PageTab) => item.value === tab)?.value;
      newTab = newTab ? newTab : CONNECTION_LEVEL_TABS[newCheckTypes][0].value;

      url = ROUTES.CONNECTION_DETAIL(newCheckTypes, connection, newTab);
      value = ROUTES.CONNECTION_LEVEL_VALUE(newCheckTypes, connection);
    } else if (match.path === ROUTES.PATTERNS.SCHEMA) {
      url = ROUTES.SCHEMA_LEVEL_PAGE(newCheckTypes, connection, schema, 'tables');
      value = ROUTES.SCHEMA_LEVEL_VALUE(newCheckTypes, connection, schema);
    } else if (match.path === ROUTES.PATTERNS.TABLE) {
      let newTab = TABLE_LEVEL_TABS[newCheckTypes].find((item: PageTab) => item.value === tab)?.value;
      newTab = newTab ? newTab : TABLE_LEVEL_TABS[newCheckTypes][0].value;

      url = ROUTES.TABLE_LEVEL_PAGE(newCheckTypes, connection, schema, table, newTab);
      value =  ROUTES.TABLE_LEVEL_VALUE(newCheckTypes, connection, schema, table);
    } else if (match.path === ROUTES.PATTERNS.TABLE_COLUMNS) {
      url = ROUTES.TABLE_COLUMNS(newCheckTypes, connection, schema, table);
      value = ROUTES.TABLE_COLUMNS_VALUE(newCheckTypes, connection, schema, table);
    } else if (match.path === ROUTES.PATTERNS.COLUMN) {
      let newTab = COLUMN_LEVEL_TABS[newCheckTypes].find((item: PageTab) => item.value === tab)?.value;
      newTab = newTab ? newTab : COLUMN_LEVEL_TABS[newCheckTypes][0].value;

      url = ROUTES.COLUMN_LEVEL_PAGE(newCheckTypes, connection, schema, table, column, newTab);
      value = ROUTES.COLUMN_LEVEL_VALUE(newCheckTypes, connection, schema, table, column);
    }

    if (!url) {
      url = `/` + newCheckTypes;
      history.push(url);
    } else {
      dispatch(addFirstLevelTab(newCheckTypes,{
        url,
        value,
        label: selectedTab?.label || '',
        state: {}
      }));
    }

    history.push(url);
  }

  return (
    <div
      className="fixed top-0 left-0 right-0 min-h-16 max-h-16 bg-white shadow-header flex items-center justify-between z-10 border-b border-gray-300 px-4"
    >
      <div className="flex space-x-2">
        <div onClick={() => history.push('/')}>
          <Logo className="w-30 cursor-pointer" />
        </div>
        <div className="flex items-center">
          <div
            className={clsx("px-4 cursor-pointer", location.pathname.startsWith(`/${CheckTypes.SOURCES}`) ? 'font-bold' : '' )}
            onClick={onClick(CheckTypes.SOURCES)}
          >
            Data Sources
          </div>
          <div
            className={clsx("px-4 cursor-pointer", location.pathname.startsWith(`/${CheckTypes.PROFILING}`) ? 'font-bold' : '' )}
            onClick={onClick(CheckTypes.PROFILING)}
          >
            Profiling
          </div>
          <div
            className={clsx("px-4 cursor-pointer", location.pathname.startsWith(`/${CheckTypes.RECURRING}`) ? 'font-bold' : '' )}
            onClick={onClick(CheckTypes.RECURRING)}
          >
            Recurring Checks
          </div>
          <div
            className={clsx("px-4 cursor-pointer", location.pathname.startsWith(`/${CheckTypes.PARTITIONED}`) ? 'font-bold' : '' )}
            onClick={onClick(CheckTypes.PARTITIONED)}
          >
            Partition Checks
          </div>
          <div
            className={clsx("px-4 cursor-pointer", location.pathname === '/dashboards' ? 'font-bold' : '' )}
            onClick={() => history.push('/dashboards')}
          >
            Data Quality Dashboards
          </div>
          <div
            className={clsx("px-4 cursor-pointer", location.pathname.startsWith('/incidents') ? 'font-bold' : '' )}
            onClick={() => history.push('/incidents')}
          >
            Incidents
          </div>
          <div
            className={clsx("px-4 cursor-pointer", location.pathname.startsWith('/definitions') ? 'font-bold' : '' )}
            onClick={() => history.push('/definitions')}
          >
            Definitions
          </div>
        </div>
      </div>
      <div className="flex">
        <HelpMenu />
        <SynchronizeButton />
        <NotificationMenu />
        <SvgIcon name='userprofile' onClick={() => setShowProfile(!showProfile)}/>
      </div>
      {showProfile ? <div className='bg-black w-50 h-60 absolute right-5 top-15 rounded-md'></div> : ""}
    </div>
  );
};

export default Header;
