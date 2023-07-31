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

type TableReferenceComparisonsProps = {
  checkTypes: CheckTypes;
  timePartitioned?: 'daily' | 'monthly';
  checksUI?: any;
};

export const TableReferenceComparisons = ({
  checkTypes,
  timePartitioned,
  checksUI
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
  const location = useLocation();

  useEffect(() => {
    const { isEditing: editing, reference } = qs.parse(location.search);
    setIsEditing(editing === 'true');
    setSelectedReference(reference as string);
  }, [location]);

  useEffect(() => {
    getNewTableComparison();
  }, []);

  const getReferenceComparisons = () => {
    TableComparisonsApi.getTableComparisonConfigurations(
      connection,
      schema,
      table
    ).then((res) => {
      setReferences(res.data);
      console.log('inside getting data');
    });
  };

  const getNewTableComparison = () => {
    TableComparisonsApi.getTableComparisonConfigurations(
      connection,
      schema,
      table,
      'profiling',
      undefined
    ).then((res) => {
      console.log('profiling');
      console.log(res.data);
      setReferences(res.data);
    });
    if (checkTypes === CheckTypes.PARTITIONED) {
      TableComparisonsApi.getTableComparisonConfigurations(
        connection,
        schema,
        table,
        checkTypes,
        timePartitioned
      ).then((res) => {
        setReferences(res.data);
        console.log('part');
      });
    } else if (checkTypes === CheckTypes.RECURRING) {
      TableComparisonsApi.getTableComparisonConfigurations(
        connection,
        schema,
        table,
        checkTypes,
        timePartitioned
      ).then((res) => {
        setReferences(res.data);
        console.log('rec');
      });
    }
  };

  const onCreateNewReference = () => {
    const url = `${ROUTES.TABLE_LEVEL_PAGE(
      CheckTypes.SOURCES,
      connection,
      schema,
      table,
      'table-comparisons'
    )}?isEditing=true`;
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
      setIsEditing(false);
    }
  };

  const onCreate = () => {
    const url = `${ROUTES.TABLE_LEVEL_PAGE(
      checkTypes,
      connection,
      schema,
      table,
      'table-comparisons'
    )}?isEditing=true`;
    dispatch(
      addFirstLevelTab(checkTypes, {
        url,
        value: ROUTES.TABLE_LEVEL_VALUE(checkTypes, connection, schema, table),
        state: {},
        label: table
      })
    );

    history.push(url);
  };

  console.log(references);
  console.log(checksUI);

  return (
    <>
      <div onClick={() => getNewTableComparison()}>TEST DATA</div>
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
        />
      ) : (
        <ProfilingReferenceTableList
          references={references}
          onCreate={onCreate}
          selectReference={onEditProfilingReference}
          onEdit={onEditReference}
        />
      )}
    </>
  );
};
