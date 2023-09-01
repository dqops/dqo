import React, { useEffect, useMemo, useState } from 'react';
import MainLayout from "../MainLayout";
import PageTabs from "../PageTabs";
import { useHistory, useLocation, useParams, useRouteMatch } from "react-router-dom";
import { CheckTypes, ROUTES } from "../../shared/routes";
import { useDispatch, useSelector } from "react-redux";
import { IRootState } from "../../redux/reducers";
import { closeFirstLevelTab, setActiveFirstLevelTab } from "../../redux/actions/source.actions";
import { TabOption } from "../PageTabs/tab";
import qs from "query-string";
import axios, { AxiosError } from 'axios';
import ConfirmDialog from '../CustomTree/ConfirmDialog';
import { getFirstLevelActiveTab } from '../../redux/selectors';

interface ConnectionLayoutProps {
  children: any;
}

const ConnectionLayout = ({ children }: ConnectionLayoutProps) => {
  const { checkTypes }: { checkTypes: CheckTypes } = useParams();

  const { tabs: pageTabs, activeTab } = useSelector((state: IRootState) => state.source[checkTypes || CheckTypes.SOURCES]);

  const dispatch= useDispatch();
  const history = useHistory();
  const location = useLocation();
  const match = useRouteMatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [objectNotFound, setObjectNotFound] = useState(false)

  const handleChange = (tab: TabOption) => {
    dispatch(setActiveFirstLevelTab(checkTypes, tab.value));
    if (tab.url && tab.url !== location.pathname) {
      history.push(tab.url);
    }
  };

  const closeTab = (value: string) => {
    dispatch(closeFirstLevelTab(checkTypes, value))
  };

  const tabOptions = useMemo(() => {
    return pageTabs.map((item) => ({
      value: item.value,
      url: item.url,
      label: item.label
    }))
  }, [pageTabs]);

  useEffect(() => {
    if (activeTab) {
      const activeUrl = pageTabs.find((item) => item.value === activeTab)?.url;
      const { import_schema } = qs.parse(location.search);
      if (activeUrl && activeUrl !== location.pathname) {
        if (match.path !== ROUTES.PATTERNS.CONNECTION && !import_schema) {
          history.push(activeUrl);
        }
      }
    }
  }, [activeTab]);
// Inicjalizacja zmiennej, do której będziemy zapisywać komunikaty błędów
const errorLogs: string[] = [];

function captureConsoleErrors() {
  const originalConsoleError = console.error;
  console.error = function (...args: any[]) {
    const errorMessage = args.map(arg => JSON.stringify(arg)).join(' ');
    console.log(errorMessage)
    if(errorMessage.includes("404")){
      setObjectNotFound(true)
    }
    const timestampedError = `${new Date().toLocaleString()}: ${errorMessage}`;

    errorLogs.push(timestampedError);

    originalConsoleError(...args);
  };
  console.log(originalConsoleError)
}

useEffect(() => {
  captureConsoleErrors();
}, [])

console.log('Zapisane komunikaty błędów:');
console.log(errorLogs);

  return (
    <MainLayout>
      <div className="flex-1 h-full flex flex-col">
        <PageTabs
          tabs={tabOptions}
          activeTab={activeTab}
          onChange={handleChange}
          onRemoveTab={closeTab}
          limit={10}
        />
        <div
          className="flex-1 bg-white border border-gray-300 flex-auto min-h-0 overflow-auto"
          style={{ maxHeight: "calc(100vh - 80px)" }}
        >
          {!!activeTab && pageTabs.length ? (
            <div>
              {children}
            </div>
          ) : null}
        </div>
      </div>
      <ConfirmDialog
      open={objectNotFound}
      onConfirm={() => new Promise(() => dispatch(closeFirstLevelTab(checkTypes, firstLevelActiveTab)))}
      isCancelExcluded={true} 
      onClose={() => dispatch(closeFirstLevelTab(checkTypes, firstLevelActiveTab))}
      message='The definition of this object was deleted in DQO user home, closing the tab'/>
    </MainLayout>
    
  );
};

export default ConnectionLayout;
