import React, { useMemo, useState } from 'react';
import ConnectionLayout from '../../components/ConnectionLayout';
import SvgIcon from '../../components/SvgIcon';
import Tabs from '../../components/Tabs';
import { useHistory, useParams } from 'react-router-dom';
import { CheckTypes, ROUTES } from '../../shared/routes';
import Button from '../../components/Button';
import AddTableDialog from '../../components/CustomTree/AddTableDialog';
import { SchemaTables } from './SchemaTables';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setActiveFirstLevelTab, setActiveFirstLevelUrl } from '../../redux/actions/source.actions';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { MultiChecks } from './MultiCheck/MultiChecks';
import { getFirstLevelActiveTab } from '../../redux/selectors';

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
  } = useParams();
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
      ...(checkTypes !== CheckTypes.SOURCES
        ? [
            {
              label: 'Data quality checks',
              value: 'multiple_checks'
            }
          ]
        : [])
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

  const onImportMoreTables = () => {
    dispatch(
      setActiveFirstLevelTab(
        checkTypes,
        ROUTES.CONNECTION_LEVEL_VALUE(checkTypes, connection)
      )
    );
    history.push(
      `${ROUTES.CONNECTION_DETAIL(
        checkTypes,
        connection,
        'schemas'
      )}?import_schema=true&import_table=true&schema=${schema}`
    );
  };
  console.log(activeTab)
  return (
    <ConnectionLayout>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14">
        <div className="flex items-center space-x-2 max-w-full">
          <SvgIcon name="schema" className="w-5 h-5 shrink-0" />
          <div className="text-xl font-semibold truncate">{`${connection}.${schema}`}</div>
        </div>

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
            onClick={onImportMoreTables}
            disabled={userProfile.can_manage_data_sources !== true}
          />

          {isSourceScreen && (
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
              label="Add Table"
              onClick={() => setAddTableDialogOpen(true)}
              disabled={userProfile.can_manage_data_sources !== true}
            />
          )}
        </div>
      </div>
      <div className="border-b border-gray-300">
        <Tabs tabs={tabs} activeTab={activeTab} onChange={onChangeTab} />
      </div>
      {activeTab === 'tables' && (
        <div className="p-4">
          <SchemaTables />
        </div>
      )}
      {checkTypes !== CheckTypes.SOURCES && activeTab === 'multiple_checks' && (
        <MultiChecks />
      )}
      <AddTableDialog
        open={addTableDialogOpen}
        onClose={() => setAddTableDialogOpen(false)}
      />
    </ConnectionLayout>
  );
};

export default SchemaPage;
