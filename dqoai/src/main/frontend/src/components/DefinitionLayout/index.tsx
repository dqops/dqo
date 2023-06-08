import React, { useEffect, useMemo } from 'react';

import Header from "../Header";
import DefinitionTree from "./DefinitionTree";
import { useDispatch, useSelector } from "react-redux";
import { IRootState } from "../../redux/reducers";
import PageTabs from "../PageTabs";
import { useHistory, useLocation } from "react-router-dom";
import { closeFirstLevelTab, setActiveFirstLevelTab } from "../../redux/actions/sensor.actions";
import { TabOption } from "../PageTabs/tab";

interface LayoutProps {
  children?: any;
}

const DefinitionLayout = ({ children }: LayoutProps) => {
  const { tabs: pageTabs, activeTab } = useSelector((state: IRootState) => state.sensor);
  const dispatch= useDispatch();
  const history = useHistory();
  const location = useLocation();

  const handleChange = (tab: TabOption) => {
    dispatch(setActiveFirstLevelTab(tab.value));
    history.push(tab?.url ?? '');
  };

  const closeTab = (value: string) => {
    dispatch(closeFirstLevelTab(value))
  };

  const tabOptions = useMemo(() => {
    return pageTabs?.map((item) => ({
      value: item.url,
      label: item.label
    })) || [];
  }, [pageTabs]);

  useEffect(() => {
    if (activeTab && activeTab !== location.pathname) {
      history.push(activeTab);
    }
  }, [activeTab])

  return (
    <div className="flex min-h-screen overflow-hidden">
      <Header />
      <DefinitionTree />
      <div className="flex flex-1">
        <div
          className="mt-16 p-5 flex-1 overflow-auto"
          style={{
            marginLeft: 320,
            maxWidth: `calc(100vw - 320px)`
          }}
        >
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
              {!!activeTab && (
                <div>
                  {children}
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DefinitionLayout;
