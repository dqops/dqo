import React from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import Header from '../../components/Header';
import Tabs from '../../components/Tabs';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setHomeFirstLevelTab } from '../../redux/actions/source.actions';
import { IRootState } from '../../redux/reducers';
import GlobalTables from './GlobalTables';
import StaticHomePage from './StaticHomePage';

const tabs = [
  { label: 'Home', value: '/home' },
  { label: 'Tables', value: '/tables' }
];
const HomePage = () => {
  const { activeTab } = useSelector(
    (state: IRootState) => state.source['home']
  );
  const history = useHistory();
  const dispatch = useActionDispatch();
  const onChange = (activeTab: string) => {
    dispatch(setHomeFirstLevelTab(activeTab))
    history.push(activeTab);
  };

  return (
    <div className="min-h-screen">
      <Header />
      tabs
      <div className="w-full mt-12 border-b border-gray-300 px-0">
        <Tabs tabs={tabs} onChange={onChange} activeTab={activeTab} />
      </div>
      {activeTab === '/home' && <StaticHomePage />}
      {activeTab === '/tables' && <GlobalTables />}
    </div>
  );
};

export default HomePage;
