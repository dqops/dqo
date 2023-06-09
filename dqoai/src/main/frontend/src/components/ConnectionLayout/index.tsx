import React, { useEffect, useMemo } from 'react';
import MainLayout from "../MainLayout";
import PageTabs from "../PageTabs";
import { useHistory, useLocation, useParams } from "react-router-dom";
import { CheckTypes } from "../../shared/routes";
import { useDispatch, useSelector } from "react-redux";
import { IRootState } from "../../redux/reducers";
import { closeFirstLevelTab, setActiveFirstLevelTab } from "../../redux/actions/source.actions";
import { TabOption } from "../PageTabs/tab";

interface ConnectionLayoutProps {
  children: any;
}

const ConnectionLayout = ({ children }: ConnectionLayoutProps) => {
  const { checkTypes }: { checkTypes: CheckTypes } = useParams();

  const { tabs: pageTabs, activeTab } = useSelector((state: IRootState) => state.source[checkTypes || CheckTypes.SOURCES]);

  const dispatch= useDispatch();
  const history = useHistory();
  const location = useLocation();

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
      if (activeUrl && activeUrl !== location.pathname) {
        history.push(activeUrl);
      }
    }
  }, [activeTab]);

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
    </MainLayout>
  );
};

export default ConnectionLayout;
