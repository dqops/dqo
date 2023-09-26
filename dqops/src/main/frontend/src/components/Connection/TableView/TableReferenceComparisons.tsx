import React, { useEffect, useState } from 'react';
import { useHistory, useLocation, useParams } from 'react-router-dom';
import { TableComparisonsApi } from '../../../services/apiClient';
import { TableComparisonConfigurationModel } from '../../../api';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../../redux/actions/source.actions';
import { CheckTypes, ROUTES } from '../../../shared/routes';
import { ProfilingReferenceTableList } from './ProfilingReferenceTableList';
import { EditProfilingReferenceTable } from './EditProfilingReferenceTable';
import qs from 'query-string';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';

type TableReferenceComparisonsProps = {
  checkTypes: CheckTypes;
  timePartitioned?: 'daily' | 'monthly';
  checksUI?: any;
};

export const TableReferenceComparisons = ({
  checkTypes,
  timePartitioned,
  checksUI,
}: TableReferenceComparisonsProps) => {
  const {
    connection,
    schema,
    table
  }: { connection: string; schema: string; table: string } = useParams();
  const [references, setReferences] = useState<
    TableComparisonConfigurationModel[]
  >([]);
  const dispatch = useActionDispatch();
  const history = useHistory();
  const [isEditing, setIsEditing] = useState(false);
  const [selectedReference, setSelectedReference] = useState<string>();
  const [isCreating, setIsCreting] = useState(false);
  const [isComparisonDeleted, setIsComparisonDeleted] = useState(false)
  const location = useLocation();
  const { userProfile } = useSelector(
    (state: IRootState) => state.job || {}
  );

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
  }, [isComparisonDeleted]);

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
        checkTypes,
        timePartitioned
      ).then((res) => {
        setReferences(res.data);
      });
    } else if (checkTypes === CheckTypes.MONITORING) {
      TableComparisonsApi.getTableComparisonConfigurations(
        connection,
        schema,
        table,
        checkTypes,
        timePartitioned
      ).then((res) => {
        setReferences(res.data);
      });
    }
    setIsCreting(false);
  };

  const onEditReference = (reference: TableComparisonConfigurationModel) => {
    const url = `${ROUTES.TABLE_LEVEL_PAGE(
      CheckTypes.SOURCES,
      connection,
      schema,
      table,
      'table-comparisons'
    )}?isEditing=true&reference=${
      reference.table_comparison_configuration_name
    }`;

    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url,
        value: ROUTES.TABLE_LEVEL_VALUE(
          CheckTypes.SOURCES,
          connection,
          schema,
          table
        ),
        state: {},
        label: table
      })
    );

    history.push(url);
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
    } else {
      if (checkTypes === CheckTypes.PROFILING) {
        const url = `${ROUTES.TABLE_LEVEL_PAGE(
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
        if (isCreating === true) {
          getNewTableComparison();
        }
        history.push(url);
      } else if (timePartitioned === 'daily') {
        const url = `${ROUTES.TABLE_LEVEL_PAGE(
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
        if (isCreating === true) {
          getNewTableComparison();
        }
        history.push(url);
      } else if (timePartitioned === 'monthly') {
        const url = `${ROUTES.TABLE_LEVEL_PAGE(
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
        if (isCreating === true) {
          getNewTableComparison();
        }
        history.push(url);
      }
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
    ).then(() =>  setIsComparisonDeleted(!isComparisonDeleted))
  };

  return (
    <>
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
          isCreating={isCreating}
          getNewTableComparison={getNewTableComparison}
          onChangeSelectedReference={onChangeSelectedReference}
          listOfExistingReferences={references.map(
            (x) => x.table_comparison_configuration_name
          )}
        />
      ) : (
        <ProfilingReferenceTableList
          references={references}
          onCreate={onCreate}
          selectReference={onEditProfilingReference}
          onEdit={onEditReference}
          canUserCreateTableComparison={userProfile.can_manage_data_sources}
          deleteComparison = {deleteComparison}
        />
      )}
    </>
  );
};
