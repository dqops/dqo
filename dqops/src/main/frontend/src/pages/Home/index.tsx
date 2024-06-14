import React from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import Header from '../../components/Header';
import Tabs from '../../components/Tabs';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setHomeFirstLevelTab } from '../../redux/actions/source.actions';
import { IRootState } from '../../redux/reducers';
import ColumnListView from '../ColumnListView/ColumnListView';
import GlobalIncidents from '../GlobalIncidents';
import TableListView from '../TableListView/TableListView';
import StaticHomePage from './StaticHomePage';

const tabs = [
  { label: 'Home', value: '/home' },
  { label: 'Tables', value: '/tables' },
  { label: 'Columns', value: '/columns' },
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
  const { activeTab } = useSelector(
    (state: IRootState) => state.source['home']
  );
  const history = useHistory();
  const dispatch = useActionDispatch();
  const onChange = (activeTab: string) => {
    dispatch(setHomeFirstLevelTab(activeTab));
    history.push(activeTab);
  };

  return (
    <div style={{ height: 'calc(100vh - 200px)' }}>
      <Header />
      <div
        className="border-b border-gray-300 px-0 mt-18"
        style={{ backgroundColor: '#F9FAFC' }}
      >
        <Tabs
          tabs={tabs}
          onChange={onChange}
          activeTab={activeTab}
          className="z-[100]"
          disableTreeWidth={true}
        />
      </div>
      {activeTab === '/home' && <StaticHomePage />}
      {activeTab === '/tables' && <TableListView />}
      {activeTab === '/columns' && <ColumnListView />}
      {activeTab === '/global-incidents' && <GlobalIncidents />}
      {/* {activeTab === '/global-incidents?groupBy=category' && (
        <GlobalIncidents groupBy="category" />
      )} */}
    </div>
  );
};

export default HomePage;
