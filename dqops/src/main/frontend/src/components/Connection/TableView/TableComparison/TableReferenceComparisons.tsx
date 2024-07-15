import qs from 'query-string';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory, useLocation } from 'react-router-dom';
import { TableComparisonConfigurationModel } from '../../../../api';
import { useActionDispatch } from '../../../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../../../redux/actions/source.actions';
import { IRootState } from '../../../../redux/reducers';
import { TableComparisonsApi } from '../../../../services/apiClient';
import { CheckTypes, ROUTES } from '../../../../shared/routes';
import { useDecodedParams } from '../../../../utils';
import Tabs from '../../../Tabs';
import { EditProfilingReferenceTable } from './EditProfilingReferenceTable';
import { ProfilingReferenceTableList } from './ProfilingReferenceTableList';

type TableReferenceComparisonsProps = {
  checkTypes: CheckTypes;
  checksUI?: any;
  onUpdateChecks: () => void;
  timePartitioned?: 'daily' | 'monthly';
  setTimePartitioned?: (value: 'daily' | 'monthly') => void;
};

export const TableReferenceComparisons = ({
  checkTypes,
  checksUI,
  onUpdateChecks,
  timePartitioned,
  setTimePartitioned
}: TableReferenceComparisonsProps) => {
  const tabs = [
    {
      label:
        checkTypes === CheckTypes.MONITORING
          ? 'Daily checkpoints'
          : 'Daily partitioned',
      value: 'daily'
    },
    {
      label:
        checkTypes === CheckTypes.MONITORING
          ? 'Monthly checkpoints'
          : 'Monthly partitioned',
      value: 'monthly'
    }
  ];
  const {
    connection,
    schema,
    table
  }: { connection: string; schema: string; table: string } = useDecodedParams();
  const [references, setReferences] = useState<
    TableComparisonConfigurationModel[]
  >([]);
  const dispatch = useActionDispatch();
  const history = useHistory();
  const [isEditing, setIsEditing] = useState(false);
  const [selectedReference, setSelectedReference] = useState<string>();
  const [isCreating, setIsCreting] = useState(false);
  const [isComparisonDeleted, setIsComparisonDeleted] = useState(false);
  const location = useLocation();
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

  const onChangeSelectedReference = (arg: string) => {
    setSelectedReference(arg);
  };

  useEffect(() => {
    const { isEditing: editing, reference } = qs.parse(location.search);
    setIsEditing(editing === 'true');
    setSelectedReference(reference as string);
  }, [location]);

  useEffect(() => {
    getNewTableComparison();
    setIsCreting(false);
  }, [isComparisonDeleted, table, schema, connection]);

  const getNewTableComparison = () => {
    if (checkTypes === CheckTypes.PROFILING) {
      TableComparisonsApi.getTableComparisonConfigurations(
        connection,
        schema,
        table,
        'profiling',
        undefined
      ).then((res) => {
        setReferences(res.data);
      });
    } else if (checkTypes === CheckTypes.PARTITIONED) {
      TableComparisonsApi.getTableComparisonConfigurations(
        connection,
        schema,
        table,
        checkTypes
      ).then((res) => {
        setReferences(res.data);
      });
    } else if (checkTypes === CheckTypes.MONITORING) {
      TableComparisonsApi.getTableComparisonConfigurations(
        connection,
        schema,
        table,
        checkTypes
      ).then((res) => {
        setReferences(res.data);
      });
    }
    setIsCreting(false);
  };

  const onChangeEditing = (value: boolean, reference?: string) => {
    setIsEditing(value);
    const parsed = qs.parse(location.search);
    parsed.isEditing = value.toString();

    if (reference !== undefined) {
      parsed.reference = reference;
    }
    history.replace(`${location.pathname}?${qs.stringify(parsed)}`);
  };

  const onEditProfilingReference = (
    reference: TableComparisonConfigurationModel
  ) => {
    setSelectedReference(reference.table_comparison_configuration_name);
    onChangeEditing(true, reference.table_comparison_configuration_name);
  };

  const onBack = (stayOnSamePage?: boolean | undefined) => {
    if (stayOnSamePage === false) {
      setIsEditing(true);
      setIsCreting(false);
    } else {
      let url = '';
      if (checkTypes === CheckTypes.PROFILING) {
        url = `${ROUTES.TABLE_LEVEL_PAGE(
          checkTypes,
          connection,
          schema,
          table,
          'table-comparisons'
        )}`;
        dispatch(
          addFirstLevelTab(checkTypes, {
            url,
            value: ROUTES.TABLE_LEVEL_VALUE(
              checkTypes,
              connection,
              schema,
              table
            ),
            state: {},
            label: table
          })
        );
      } else if (timePartitioned === 'daily') {
        url = `${ROUTES.TABLE_LEVEL_PAGE(
          checkTypes,
          connection,
          schema,
          table,
          'daily_comparisons'
        )}`;
        dispatch(
          addFirstLevelTab(checkTypes, {
            url,
            value: ROUTES.TABLE_LEVEL_VALUE(
              checkTypes,
              connection,
              schema,
              table
            ),
            state: {},
            label: table
          })
        );
      } else if (timePartitioned === 'monthly') {
        url = `${ROUTES.TABLE_LEVEL_PAGE(
          checkTypes,
          connection,
          schema,
          table,
          'monthly_comparisons'
        )}`;
        dispatch(
          addFirstLevelTab(checkTypes, {
            url,
            value: ROUTES.TABLE_LEVEL_VALUE(
              checkTypes,
              connection,
              schema,
              table
            ),
            state: {},
            label: table
          })
        );
      }
      if (isCreating === true) {
        getNewTableComparison();
      }
      history.push(url);
      setIsCreting(false);
    }
  };

  const onCreate = () => {
    setIsCreting(true);
    setIsEditing(true);
  };

  const deleteComparison = async (tableComparisonConfigurationName: string) => {
    await TableComparisonsApi.deleteTableComparisonConfiguration(
      connection,
      schema,
      table,
      tableComparisonConfigurationName
    ).then(() => setIsComparisonDeleted(!isComparisonDeleted));
  };

  return (
    <>
      {timePartitioned &&
        userProfile.license_type?.toLowerCase() !== 'free' && (
          <Tabs
            tabs={tabs}
            activeTab={timePartitioned}
            onChange={setTimePartitioned}
            className="py-1"
          />
        )}
      {isEditing ? (
        <EditProfilingReferenceTable
          checkTypes={checkTypes}
          timePartitioned={timePartitioned}
          onBack={onBack}
          selectedReference={selectedReference}
          categoryCheck={
            checksUI?.categories
              ? checksUI.categories.find(
                  (c: any) => c.category === `comparisons/${selectedReference}`
                )
              : undefined
          }
          getNewTableComparison={getNewTableComparison}
          onChangeSelectedReference={onChangeSelectedReference}
          listOfExistingReferences={references.map(
            (x) => x.table_comparison_configuration_name
          )}
          checksUI={checksUI}
          onUpdateChecks={onUpdateChecks}
        />
      ) : (
        <ProfilingReferenceTableList
          references={references.filter(
            (x) => x.time_scale === timePartitioned
          )}
          onCreate={onCreate}
          selectReference={onEditProfilingReference}
          canUserCreateTableComparison={userProfile.can_manage_data_sources}
          deleteComparison={deleteComparison}
        />
      )}
    </>
  );
};
