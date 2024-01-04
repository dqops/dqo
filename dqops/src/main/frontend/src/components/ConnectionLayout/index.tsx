import React, { useEffect, useMemo } from 'react';
import MainLayout from "../MainLayout";
import PageTabs from "../PageTabs";
import { useHistory, useLocation, useParams, useRouteMatch } from "react-router-dom";
import { CheckTypes, ROUTES } from "../../shared/routes";
import { useDispatch, useSelector } from "react-redux";
import { IRootState } from "../../redux/reducers";
import { closeFirstLevelTab, setActiveFirstLevelTab } from "../../redux/actions/source.actions";
import { TabOption } from "../PageTabs/tab";
import qs from "query-string";

import ConfirmDialog from '../CustomTree/ConfirmDialog';
import { getFirstLevelActiveTab } from '../../redux/selectors';
import { checkIfTabCouldExist } from '../../utils';
import { useTree } from '../../contexts/treeContext';

interface ConnectionLayoutProps {
  children: any;
}

const ConnectionLayout = ({ children }: ConnectionLayoutProps) => {
  const { checkTypes }: { checkTypes: CheckTypes } = useParams();
  const { objectNotFound, setObjectNotFound } = useTree()
  const { tabs: pageTabs, activeTab } = useSelector((state: IRootState) => state.source[checkTypes || CheckTypes.SOURCES]);

  const dispatch= useDispatch();
  const history = useHistory();
  const location = useLocation();
  const match = useRouteMatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const handleChange = (tab: TabOption) => {
    dispatch(setActiveFirstLevelTab(checkTypes, tab.value));
    if (tab.url && tab.url !== location.pathname) {
      history.push(tab.url);
    }
  };

  const closeTab = (value: string) => {
    dispatch(closeFirstLevelTab(checkTypes, value))
    // console.log(value)
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
      // const { import_schema } = qs.parse(location.search);
      if (activeUrl && activeUrl !== location.pathname) {
        // if (match.path !== ROUTES.PATTERNS.CONNECTION && !import_schema) {
          history.push(activeUrl)
          // history.push(checkIfTabCouldExist(checkTypes, location.pathname) ? location.pathname : activeUrl);
        // }
      }
     }
  }, [activeTab]);
  // TODO Aleksy: fix checkIfTabCouldExist function with opening tabs with url. 

  return (
    <MainLayout>
      <div className="h-full flex flex-col">
        <PageTabs
          tabs={tabOptions}
          activeTab={activeTab}
          onChange={handleChange}
          onRemoveTab={closeTab}
          limit={7}
        />
        <div
          className=" bg-white border border-gray-300 min-h-0 overflow-auto h-full w-full"
        >
          {!!activeTab && pageTabs.length ? (
            <div className='w-full h-full'>
              {children}
            </div>
          ) : null}
        </div>
      </div>
      <ConfirmDialog
      open={objectNotFound}
      onConfirm={() => new Promise(() => {dispatch(closeFirstLevelTab(checkTypes, firstLevelActiveTab)), setObjectNotFound(false)})}
      isCancelExcluded={true} 
      onClose={() => {dispatch(closeFirstLevelTab(checkTypes, firstLevelActiveTab)), setObjectNotFound(false)}}
      message='The definition of this object was deleted in the DQOps user home. The tab will be closed.'/>
    </MainLayout>
    
  );
};

export default ConnectionLayout;
