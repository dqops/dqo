import { IRootState } from "../reducers";
import { CheckTypes } from "../../shared/routes";

export const getFirstLevelState = (checkType: CheckTypes) => (state: IRootState) => {
  const { tabs, activeTab = ''} = state.source[checkType || CheckTypes.SOURCES];

  return tabs.find((item) => item.url === activeTab)?.state || {} as any;
};

export const getFirstLevelActiveTab = (checkType: CheckTypes) => (state: IRootState) => {
  const { activeTab = ''} = state.source[checkType || CheckTypes.SOURCES];

  return activeTab;
};