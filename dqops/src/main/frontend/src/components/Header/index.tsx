import React, { useEffect } from 'react';
import NotificationMenu from '../NotificationMenu';
import Logo from '../Logo';
import clsx from 'clsx';
import {
  useHistory,
  useLocation,
  useParams,
  useRouteMatch
} from 'react-router-dom';
import { CheckTypes, ROUTES } from '../../shared/routes';
import HelpMenu from '../HelpMenu';
import { useSelector } from 'react-redux';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../redux/actions/source.actions';
import { IRootState } from '../../redux/reducers';
import {
  COLUMN_LEVEL_TABS,
  CONNECTION_LEVEL_TABS,
  PageTab,
  TABLE_LEVEL_TABS
} from '../../shared/constants';
import { SynchronizeButton } from './SynchronizeButton';
import UserProfile from '../UserProfile';
import { HeaderBanner } from './HeaderBanner';
import {
  toggleAdvisor,
  setAdvisorObject,
  setAdvisorJobId
} from '../../redux/actions/job.actions';
import { Tooltip } from '@material-tailwind/react';
import { DqoJobChangeModelStatusEnum, DqoJobHistoryEntryModelJobTypeEnum } from '../../api';

const Header = () => {
  const history = useHistory();
  const location = useLocation();
  const {
    checkTypes,
    connection,
    schema,
    table,
    column,
    tab
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
    column: string;
    tab: string;
    timePartitioned: 'daily' | 'monthly';
    category: string;
    checkName: string;
  } = useParams();

  const dispatch = useActionDispatch();
  const { tabs, activeTab } = useSelector(
    (state: IRootState) => state.source[checkTypes || CheckTypes.SOURCES]
  );
  const selectedTab = tabs?.find((item) => item.value === activeTab);
  const match = useRouteMatch();
  const { isAdvisorOpen, job_dictionary_state, advisorObject, advisorJobId } =
    useSelector((state: IRootState) => state.job);

  const onClick = (newCheckTypes: CheckTypes) => () => {
    let url = '';
    let value = '';

    if (match.path === ROUTES.PATTERNS.CONNECTION) {
      let newTab = CONNECTION_LEVEL_TABS[newCheckTypes].find(
        (item: PageTab) => item.value === tab
      )?.value;
      newTab = newTab ? newTab : CONNECTION_LEVEL_TABS[newCheckTypes][0].value;

      url = ROUTES.CONNECTION_DETAIL(newCheckTypes, connection, newTab);
      value = ROUTES.CONNECTION_LEVEL_VALUE(newCheckTypes, connection);
    } else if (match.path === ROUTES.PATTERNS.SCHEMA) {
      url = ROUTES.SCHEMA_LEVEL_PAGE(
        newCheckTypes,
        connection,
        schema,
        'tables'
      );
      value = ROUTES.SCHEMA_LEVEL_VALUE(newCheckTypes, connection, schema);
    } else if (match.path === ROUTES.PATTERNS.TABLE) {
      let newTab = TABLE_LEVEL_TABS[newCheckTypes].find(
        (item: PageTab) => item.value === tab
      )?.value;
      newTab = newTab ? newTab : TABLE_LEVEL_TABS[newCheckTypes][0].value;

      url = ROUTES.TABLE_LEVEL_PAGE(
        newCheckTypes,
        connection,
        schema,
        table,
        newTab
      );
      value = ROUTES.TABLE_LEVEL_VALUE(
        newCheckTypes,
        connection,
        schema,
        table
      );
    } else if (match.path === ROUTES.PATTERNS.TABLE_COLUMNS) {
      url = ROUTES.TABLE_COLUMNS(newCheckTypes, connection, schema, table);
      value = ROUTES.TABLE_COLUMNS_VALUE(
        newCheckTypes,
        connection,
        schema,
        table
      );
    } else if (match.path === ROUTES.PATTERNS.TABLE_INCIDENTS_NOTIFICATION) {
      url = ROUTES.TABLE_INCIDENTS_NOTIFICATION(
        newCheckTypes,
        connection,
        schema,
        table
      );
      value = ROUTES.TABLE_INCIDENTS_NOTIFICATION_VALUE(
        newCheckTypes,
        connection,
        schema,
        table
      );
    } else if (match.path === ROUTES.PATTERNS.COLUMN) {
      let newTab = COLUMN_LEVEL_TABS[newCheckTypes].find(
        (item: PageTab) => item.value === tab
      )?.value;
      newTab = newTab ? newTab : COLUMN_LEVEL_TABS[newCheckTypes][0].value;

      url = ROUTES.COLUMN_LEVEL_PAGE(
        newCheckTypes,
        connection,
        schema,
        table,
        column,
        newTab
      );
      value = ROUTES.COLUMN_LEVEL_VALUE(
        newCheckTypes,
        connection,
        schema,
        table,
        column
      );
    }

    if (!url) {
      url = `/` + newCheckTypes;
      history.push(url);
    } else {
      dispatch(
        addFirstLevelTab(newCheckTypes, {
          url,
          value,
          label: selectedTab?.label || '',
          state: {}
        })
      );
    }

    if (url !== location.pathname) {
      history.push(url);
    }
  };

  const onCloseAdvisor = () => {
    dispatch(toggleAdvisor(false));
    dispatch(setAdvisorJobId(0))
  };

  useEffect(() => {
    if (
      Object.values(job_dictionary_state)
        .filter((x) => x.jobType === DqoJobHistoryEntryModelJobTypeEnum.import_selected_tables)
        .find(
          (y) =>
            y.status === DqoJobChangeModelStatusEnum.queued ||
            y.status === DqoJobChangeModelStatusEnum.waiting ||
            y.status === DqoJobChangeModelStatusEnum.running
        )
    ) {
      dispatch(
        setAdvisorJobId(
          Number(
            Object.keys(job_dictionary_state).find(
              (key) =>
                job_dictionary_state[key] ===
                Object.values(job_dictionary_state)
                  .filter((x) => x.jobType === DqoJobHistoryEntryModelJobTypeEnum.import_selected_tables)
                  ?.find(
                    (y) =>
                    y.status === DqoJobChangeModelStatusEnum.queued ||
                    y.status === DqoJobChangeModelStatusEnum.waiting ||
                    y.status === DqoJobChangeModelStatusEnum.running
                  )
            )
          )
        )
      );
      dispatch(
        setAdvisorObject(
          Object.values(job_dictionary_state).find(
            (x) => x.jobType === DqoJobHistoryEntryModelJobTypeEnum.import_selected_tables
          )?.parameters?.importTableParameters ?? {}
        )
      );
    }
  }, [job_dictionary_state]);

  useEffect(() => {
    if (
      advisorJobId !== 0 &&
      job_dictionary_state[advisorJobId].status === DqoJobChangeModelStatusEnum.succeeded
    ) {
      dispatch(toggleAdvisor(true));
    }
  }, [advisorObject]);

  return (
    <div className="fixed top-0 left-0 right-0 min-h-16 max-h-16 bg-white shadow-header flex items-center justify-between z-10 border-b border-gray-300 px-4">
      {isAdvisorOpen && (
        <div className="fixed top-0 left-0 z-50 right-0">
          <HeaderBanner onClose={onCloseAdvisor} />
        </div>
      )}
      <div className="flex space-x-2">
        <div onClick={() => history.push('/')}>
          <Logo className="w-30 cursor-pointer" />
        </div>
        <div className="flex items-center">
          <Tooltip content={"Add a new connection and manage its settings"}
              className="max-w-80 py-4 px-4 bg-gray-800 delay-700" >
          <div
            className={clsx(
              'px-4 cursor-pointer',
              location.pathname.startsWith(`/${CheckTypes.SOURCES}`)
              ? 'font-bold'
              : ''
              )}
              onClick={onClick(CheckTypes.SOURCES)}
              >
            Data Sources
          </div>
          </Tooltip>
          <Tooltip content={"Measure basic data statistics and experiment with various types of data quality checks"}
              className="max-w-80 py-4 px-4 bg-gray-800 delay-700">
          <div
            className={clsx(
              'px-4 cursor-pointer',
              location.pathname.startsWith(`/${CheckTypes.PROFILING}`)
              ? 'font-bold'
              : ''
              )}
              onClick={onClick(CheckTypes.PROFILING)}
              >
            Profiling
          </div>
          </Tooltip>
          <Tooltip content={"Run standard checks that monitor the data quality"}
              className="max-w-80 py-4 px-4 bg-gray-800 delay-700">
          <div
            className={clsx(
              'px-4 cursor-pointer',
              location.pathname.startsWith(`/${CheckTypes.MONITORING}`)
              ? 'font-bold'
              : ''
              )}
              onClick={onClick(CheckTypes.MONITORING)}
              >
            Monitoring Checks
          </div>
            </Tooltip>
            <Tooltip content={"Run checks designed to monitor the data quality of partitioned data"}
              className="max-w-80 py-4 px-4 bg-gray-800 delay-700">
          <div
            className={clsx(
              'px-4 cursor-pointer',
              location.pathname.startsWith(`/${CheckTypes.PARTITIONED}`)
              ? 'font-bold'
              : ''
              )}
              onClick={onClick(CheckTypes.PARTITIONED)}
              >
            Partition Checks
          </div>
            </Tooltip>
            <Tooltip content={"Review the summaries of data quality monitoring"}
              className="max-w-80 py-4 px-4 bg-gray-800 delay-700">
          <div
            className={clsx(
              'px-4 cursor-pointer',
              location.pathname === '/dashboards' ? 'font-bold' : ''
              )}
              onClick={() => history.push('/dashboards')}
              >
            Data Quality Dashboards
          </div>
            </Tooltip>
            <Tooltip content={"Review and manage the issues that arise during data quality monitoring"}
              className="max-w-80 py-4 px-4 bg-gray-800 delay-700">
          <div
            className={clsx(
              'px-4 cursor-pointer',
              location.pathname.startsWith('/incidents') ? 'font-bold' : ''
              )}
              onClick={() => history.push('/incidents')}
              >
            Incidents
          </div>
            </Tooltip>
          <Tooltip content={"Customize built-in data quality sensors and rules"}
              className="max-w-80 py-4 px-4 bg-gray-800 delay-700">
            <div
            className={clsx(  
              'px-4 cursor-pointer',
              location.pathname.startsWith('/definitions') ? 'font-bold' : ''
              )}
              onClick={() => history.push('/definitions')}
              >
            Configuration
            </div>
          </Tooltip>
        </div>
      </div>
      <div className="flex">
        <HelpMenu />
        <SynchronizeButton />
        <NotificationMenu />
        <UserProfile />
      </div>
    </div>
  );
};

export default Header;
