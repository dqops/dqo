import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import Header from '../../components/Header';
import Tabs from '../../components/Tabs';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setHomeFirstLevelTab } from '../../redux/actions/source.actions';
import { IRootState } from '../../redux/reducers';
import DataQualitySummary from '../DataQualitySummary';
import GlobalIncidents from '../GlobalIncidents';
import StaticHomePage from './StaticHomePage';

const tabs = [
  { label: 'Home', value: '/home' },
  {
    label: 'Data quality summary',
    value: '/data-quality-summary'
  },
  {
    label: 'Incidents summary',
    value: '/global-incidents'
  }
  // {
  //   label: 'Incidents grouped by category',
  //   value: '/global-incidents?groupBy=category'
  // }
];
const HomePage = () => {
  const { activeTab, secondTab } = useSelector(
    (state: IRootState) => state.source['home']
  );
  const history = useHistory();
  const dispatch = useActionDispatch();
  const onChange = (activeTab: string) => {
    dispatch(setHomeFirstLevelTab(activeTab));
    history.push(activeTab);
  };

  useEffect(() => {
    if (!activeTab) {
      dispatch(setHomeFirstLevelTab('/home'));
    } else if (activeTab !== history.location.pathname) {
      history.push(activeTab);
    }
  }, [activeTab]);

  return (
    <div style={{ height: 'calc(100vh - 200px)' }}>
      <Header />
      <div
        className="border-b border-gray-300 px-0 top-[64px] fixed r-0 l-0 w-full z-1"
        style={{ backgroundColor: '#F9FAFC', userSelect: 'none' }}
      >
        <Tabs
          tabs={tabs}
          onChange={onChange}
          activeTab={activeTab}
          disableTreeWidth={true}
        />
      </div>
      <div className="mt-24.5">
        {activeTab === '/home' && <StaticHomePage />}
        {activeTab === '/data-quality-summary' && (
          <DataQualitySummary secondTab={secondTab} />
        )}
        {activeTab === '/global-incidents' && <GlobalIncidents />}
        {/* {activeTab === '/global-incidents?groupBy=category' && (
        <GlobalIncidents groupBy="category" />
        )} */}
      </div>
    </div>
  );
};

export default HomePage;
