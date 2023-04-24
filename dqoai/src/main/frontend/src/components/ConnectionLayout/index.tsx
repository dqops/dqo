import React, { useEffect, useMemo } from 'react';
import MainLayout from "../MainLayout";
import PageTabs from "../PageTabs";
import { useTree } from "../../contexts/treeContext";
import { useHistory } from "react-router-dom";
import { CheckTypes } from "../../shared/routes";
import { useDispatch, useSelector } from "react-redux";
import { IRootState } from "../../redux/reducers";
import { closeFirstLevelTab, setActiveFirstLevelTab } from "../../redux/actions/source.actions";

interface ConnectionLayoutProps {
  children: any;
}

const ConnectionLayout = ({ children }: ConnectionLayoutProps) => {
  const { sourceRoute } = useTree();

  const { tabs: pageTabs, activeTab } = useSelector((state: IRootState) => state.source[sourceRoute as CheckTypes || CheckTypes.SOURCES]);
  const dispatch= useDispatch();
  const history = useHistory();

  const handleChange = (value: string) => {
    dispatch(setActiveFirstLevelTab(sourceRoute, value));
  };

  const closeTab = (value: string) => {
    dispatch(closeFirstLevelTab(sourceRoute, value))
  };

  const tabOptions = useMemo(() => {
    return pageTabs.map((item) => ({
      value: item.url,
      label: item.label
    }))
  }, [pageTabs]);

  useEffect(() => {
    if (activeTab) {
      history.push(activeTab);
    }
  }, [activeTab])


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
          {!!activeTab && (
            <div>
              {children}
            </div>
          )}
        </div>
      </div>
    </MainLayout>
  );
};

export default ConnectionLayout;
