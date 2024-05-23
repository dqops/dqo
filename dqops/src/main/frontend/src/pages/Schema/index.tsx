import React, { useMemo, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import Button from '../../components/Button';
import SourceTablesView from '../../components/Connection/ConnectionView/SourceTablesView';
import AddTableDialog from '../../components/CustomTree/AddTableDialog';
import SvgIcon from '../../components/SvgIcon';
import Tabs from '../../components/Tabs';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setActiveFirstLevelUrl } from '../../redux/actions/source.actions';
import { IRootState } from '../../redux/reducers';
import { getFirstLevelActiveTab } from '../../redux/selectors';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import TableListView from '../TableListView/TableListView';
import { MultiChecks } from './MultiCheck/MultiChecks';
import SchemaColumns from './SchemaColumns';

const SchemaPage = () => {
  const {
    connection,
    schema,
    tab: activeTab,
    checkTypes
  }: {
    connection: string;
    schema: string;
    tab: string;
    checkTypes: CheckTypes;
  } = useDecodedParams();
  const [addTableDialogOpen, setAddTableDialogOpen] = useState(false);
  const isSourceScreen = checkTypes === CheckTypes.SOURCES;
  const dispatch = useActionDispatch();
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const history = useHistory();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const tabs = useMemo(
    () => [
      {
        label: 'Tables',
        value: 'tables'
      },
      {
        label: 'Columns',
        value: 'columns'
      },
      ...(checkTypes !== CheckTypes.SOURCES
        ? [
            {
              label: 'Data quality checks',
              value: 'multiple_checks'
            }
          ]
        : [{ label: 'Import tables', value: 'import-tables' }])
    ],
    [checkTypes]
  );

  const onChangeTab = (tab: string) => {
    dispatch(
      setActiveFirstLevelUrl(
        checkTypes,
        firstLevelActiveTab,
        ROUTES.SCHEMA_LEVEL_PAGE(checkTypes, connection, schema, tab)
      )
    );

    history.push(ROUTES.SCHEMA_LEVEL_PAGE(checkTypes, connection, schema, tab));
  };

  return (
    <>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
        <div className="flex items-center space-x-2 max-w-full">
          <SvgIcon name="schema" className="w-5 h-5 shrink-0" />
          <div className="text-lg font-semibold truncate">{`${connection}.${schema}`}</div>
        </div>
        {isSourceScreen && activeTab === 'tables' && (
          <div className="flex gap-4 items-center">
            <Button
              className="!h-10"
              color={
                !(userProfile.can_manage_data_sources !== true)
                  ? 'primary'
                  : 'secondary'
              }
              variant={
                !(userProfile.can_manage_data_sources !== true)
                  ? 'outlined'
                  : 'contained'
              }
              label="Import more tables"
              onClick={() => onChangeTab('import-tables')}
              disabled={userProfile.can_manage_data_sources !== true}
            />
            <Button
              className="!h-10"
              color={
                !(userProfile.can_manage_data_sources !== true)
                  ? 'primary'
                  : 'secondary'
              }
              variant={
                !(userProfile.can_manage_data_sources !== true)
                  ? 'outlined'
                  : 'contained'
              }
              label="Add table"
              onClick={() => setAddTableDialogOpen(true)}
              disabled={userProfile.can_manage_data_sources !== true}
            />
          </div>
        )}
      </div>
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
      </div>
      {activeTab === 'tables' && (
        <div className="p-4">
          <TableListView />
        </div>
      )}
      {activeTab === 'columns' && (
        <div className="p-4">
          <SchemaColumns />
        </div>
      )}
      {activeTab === 'import-tables' && <SourceTablesView />}
      {checkTypes !== CheckTypes.SOURCES && activeTab === 'multiple_checks' && (
        <MultiChecks />
      )}
      <AddTableDialog
        open={addTableDialogOpen}
        onClose={() => setAddTableDialogOpen(false)}
      />
    </>
  );
};

export default SchemaPage;
