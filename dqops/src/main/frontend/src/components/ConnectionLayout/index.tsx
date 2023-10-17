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
import axios from 'axios';
import ConfirmDialog from '../CustomTree/ConfirmDialog';
import { getFirstLevelActiveTab } from '../../redux/selectors';
import { checkIfTabCouldExist } from '../../utils';

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
  const [objectNotFound, setObjectNotFound] = useState<boolean | undefined>()

  const handleChange = (tab: TabOption) => {
    console.log("here")
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
          history.push(checkIfTabCouldExist(checkTypes, location.pathname) ? location.pathname : activeUrl);
        }
      }
    }
  }, [activeTab]);

  const onChangeObjectNotFount = (param : boolean) => {
    setObjectNotFound(param)
  }

  const catchError = () => {
  axios.interceptors.response.use(undefined, async function (error) {
    console.log('inside')
    const statusCode = error.response ? error.response.status : null;
    console.log(statusCode)
    if (statusCode === 404 ) {
      console.log('inside if')
      onChangeObjectNotFount(true)
      console.log(error)
    }
      return Promise.reject(error);
  });
}

useEffect(() => {
  catchError()
}, [activeTab])

console.log(objectNotFound)
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
      open={objectNotFound ?? false}
      onConfirm={() => new Promise(() => {dispatch(closeFirstLevelTab(checkTypes, firstLevelActiveTab)), setObjectNotFound(false)})}
      isCancelExcluded={true} 
      onClose={() => {dispatch(closeFirstLevelTab(checkTypes, firstLevelActiveTab)), setObjectNotFound(false)}}
      message='The definition of this object was deleted in DQOps user home, closing the tab'/>
    </MainLayout>
    
  );
};

export default ConnectionLayout;
