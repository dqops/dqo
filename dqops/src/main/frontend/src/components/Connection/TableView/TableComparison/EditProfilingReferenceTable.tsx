import React, { useEffect, useMemo, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  CheckContainerModel,
  CheckSearchFiltersCheckTypeEnum,
  CompareThresholdsModel,
  DqoJobHistoryEntryModelStatusEnum,
  TableComparisonModel,
  TableComparisonResultsModel
} from '../../../../api';
import { useActionDispatch } from '../../../../hooks/useActionDispatch';
import { setUpdatedChecksModel } from '../../../../redux/actions/table.actions';
import { IRootState } from '../../../../redux/reducers';
import { getFirstLevelActiveTab } from '../../../../redux/selectors';
import {
  ColumnApiClient,
  JobApiClient,
  TableComparisonResultsApi,
  TableComparisonsApi
} from '../../../../services/apiClient';
import { TParameters } from '../../../../shared/constants';
import { CheckTypes } from '../../../../shared/routes';
import SectionWrapper from '../../../Dashboard/SectionWrapper';
import Loader from '../../../Loader';
import { Option } from '../../../Select';
import EditReferenceTable from './EditReferenceTable';
import SeverityInputBlock from './SeverityInputBlock';
import {
  EditProfilingReferenceTableProps,
  TSeverityValues,
  checkNames
} from './TableComparisonConstans';
import TableComparisonOverwiewBody from './TableComparisonOverwiewBody';
import { onUpdate, validate404Status } from './TableComparisonUtils';
import TableLevelResults from './TableLevelResults';
import TableRow from './TableLevelRowResults';
import { useDecodedParams } from '../../../../utils';

export const EditProfilingReferenceTable = ({
  checkTypes,
  timePartitioned,
  onBack,
  selectedReference,
  categoryCheck,
  getNewTableComparison,
  onChangeSelectedReference,
  listOfExistingReferences,
  checksUI,
  onUpdateChecks
}: EditProfilingReferenceTableProps) => {
  const [isUpdated, setIsUpdated] = useState(false);
  const {
    connection,
    schema,
    table
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
  } = useDecodedParams();
  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const [reference, setReference] = useState<TableComparisonModel>();
  const [showRowCount, setShowRowCount] = useState(false);
  const [showColumnCount, setShowColumnCount] = useState(false);
  const [comparedColumnOptions, setComparedColumnOptions] = useState<Option[]>(
    []
  );
  const [columnOptions, setColumnOptions] = useState<Option[]>([]);
  const [jobId, setJobId] = useState<number>();
  const [loading, setLoading] = useState<boolean>();
  const job = jobId ? job_dictionary_state[jobId] : undefined;
  const [tableComparisonResults, setTableComparisonResults] =
    useState<TableComparisonResultsModel>();
  const [tableLevelComparisonExtended, settableLevelComparisonExtended] =
    useState(false);
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [parameters, setParameters] = useState<TParameters>({});

  const onChangeParameters = (obj: Partial<TParameters>) => {
    setParameters((prevState) => ({
      ...prevState,
      ...obj
    }));
  };

  const handleChange = async (value: CheckContainerModel) => {
    return new Promise<void>((resolve) => {
      dispatch(setUpdatedChecksModel(checkTypes, firstLevelActiveTab, value));
      resolve();
    }).then(() => {
      onUpdateChecks();
    });
  };

  const onUpdateChecksUI = (
    checksUI: any,
    type: 'row' | 'column',
    disabled?: boolean,
    severity?: TSeverityValues
  ) => {
    const checks = checksUI.categories.find(
      (item: any) =>
        String(item.category) ===
        `comparisons/${
          (selectedReference ? selectedReference : parameters.name) ?? ''
        }`
    )?.checks;

    let selectedCheck;
    if (type === 'row') {
      selectedCheck = checks?.find((item: any) =>
        String(item.check_name).includes('row')
      );
    } else {
      selectedCheck = checks.find((item: any) =>
        String(item.check_name).includes('column')
      );
    }
    if (disabled !== undefined) {
      selectedCheck.configured = disabled;
      if (type === 'row') {
        setShowRowCount(disabled);
      } else {
        setShowColumnCount(disabled);
      }
    }
    selectedCheck.rule.warning.configured = true;
    selectedCheck.rule.error.configured = true;
    if (severity) {
      if (severity.warning) {
        selectedCheck.rule.warning.configured = true;
        selectedCheck.rule.warning.rule_parameters[0].double_value =
          severity.warning;
      }
      if (severity.error) {
        selectedCheck.rule.error.configured = true;
        selectedCheck.rule.error.rule_parameters[0].double_value =
          severity.error;
      }
      if (severity.fatal) {
        selectedCheck.rule.fatal.configured = true;
        selectedCheck.rule.fatal.rule_parameters[0].double_value =
          severity.fatal;
      }
    }

    if (type === 'row') {
      if (disabled === true) {
        if (reference?.compare_row_count !== undefined) {
          onChange({
            compare_row_count: undefined
          });
        }
      } else if (disabled == false) {
        if (reference?.compare_row_count === undefined) {
          onChange({
            compare_row_count: reference?.default_compare_thresholds
          });
        }
      }
    } else {
      if (disabled === true) {
        if (reference?.compare_column_count !== undefined) {
          onChange({
            compare_column_count: undefined
          });
        }
      } else if (disabled == false) {
        if (reference?.compare_column_count === undefined) {
          onChange({
            compare_column_count: reference?.default_compare_thresholds
          });
        }
      }
    }

    setIsUpdated(true);
  };

  useEffect(() => {
    if (selectedReference) {
      const callback = (res: { data: TableComparisonModel }) => {
        if (res && res?.data) {
          setReference(res.data);
        }
      };
      if (checkTypes === CheckTypes.PROFILING) {
        TableComparisonsApi.getTableComparisonProfiling(
          connection,
          schema,
          table,
          selectedReference
        ).then(callback);
      } else if (checkTypes === CheckTypes.MONITORING) {
        if (timePartitioned === 'daily') {
          TableComparisonsApi.getTableComparisonMonitoringDaily(
            connection,
            schema,
            table,
            selectedReference
          ).then(callback);
        } else if (timePartitioned === 'monthly') {
          TableComparisonsApi.getTableComparisonMonitoringMonthly(
            connection,
            schema,
            table,
            selectedReference
          ).then(callback);
        }
      } else if (checkTypes === CheckTypes.PARTITIONED) {
        if (timePartitioned === 'daily') {
          TableComparisonsApi.getTableComparisonPartitionedDaily(
            connection,
            schema,
            table,
            selectedReference
          ).then(callback);
        } else if (timePartitioned === 'monthly') {
          TableComparisonsApi.getTableComparisonPartitionedMonthly(
            connection,
            schema,
            table,
            selectedReference
          ).then(callback);
        }
      }
    }
  }, [selectedReference]);

  useEffect(() => {
    const checkIfRowAndColumnCountClicked = () => {
      let values: string | any[] = [];
      if (!checksUI) {
        return;
      }
      values = Object.values(checksUI);

      if (values.length === 0 || !Array.isArray(values[0])) {
        return;
      }
      const comparisonCategory = values[0].find(
        (x) => x && x.category === `comparisons/${selectedReference}`
      );
      if (!comparisonCategory || !comparisonCategory.checks) {
        return;
      }

      const rowCountElem = comparisonCategory.checks.find((c: any) =>
        c.check_name.includes('row_count_match')
      );

      const columnCountElem = comparisonCategory.checks.find((c: any) =>
        c.check_name.includes('column_count_match')
      );

      if (rowCountElem) {
        setShowRowCount(!!rowCountElem.configured);
      }
      if (columnCountElem) {
        setShowColumnCount(!!columnCountElem.configured);
      }
      if (rowCountElem?.configured === true) {
        onChange({
          compare_row_count: {
            warning_difference_percent:
              rowCountElem.rule.warning.rule_parameters[0].double_value,
            error_difference_percent:
              rowCountElem.rule.error.rule_parameters[0].double_value,
            fatal_difference_percent:
              rowCountElem.rule.fatal.rule_parameters[0].double_value
          }
        });
      } else {
        onChange({ compare_row_count: undefined });
      }
      if (columnCountElem?.configured === true) {
        onChange({
          compare_column_count: {
            warning_difference_percent:
              columnCountElem.rule.warning.rule_parameters[0].double_value,
            error_difference_percent:
              columnCountElem.rule.error.rule_parameters[0].double_value,
            fatal_difference_percent:
              columnCountElem.rule.fatal.rule_parameters[0].double_value
          }
        });
      } else {
        onChange({
          compare_column_count: undefined
        });
      }
      //cCompareThreshholdsModel in java fatal returns null
    };
    getResultsData();
    checkIfRowAndColumnCountClicked();
  }, [selectedReference, checksUI]);

  const onChange = (obj: Partial<TableComparisonModel>): void => {
    setReference({
      ...(reference || {}),
      ...obj
    });
  };

  const onChangeCompareRowCount = (obj: Partial<CompareThresholdsModel>) => {
    onChange({
      compare_row_count: {
        ...(reference?.compare_row_count || {}),
        ...obj
      }
    });
  };

  const onChangeCompareColumnCount = (obj: Partial<CompareThresholdsModel>) => {
    onChange({
      compare_column_count: {
        ...(reference?.compare_column_count || {}),
        ...obj
      }
    });
  };

  useEffect(() => {
    const updatedReference: TableComparisonModel = {};

    if (showRowCount && reference?.compare_row_count === undefined) {
      updatedReference.compare_row_count =
        reference?.default_compare_thresholds;
    }

    if (showColumnCount && reference?.compare_column_count === undefined) {
      updatedReference.compare_column_count =
        reference?.default_compare_thresholds;
    }

    if (Object.keys(updatedReference).length > 0) {
      onChange(updatedReference);
    }
  }, [showColumnCount, showRowCount]);

  const getResultsData = async () => {
    if (selectedReference === undefined || selectedReference === '') {
      return;
    }

    if (checkTypes === 'profiling') {
      await TableComparisonResultsApi.getTableComparisonProfilingResults(
        connection,
        schema,
        table,
        selectedReference ?? ''
      ).then((res) => setTableComparisonResults(res.data));
    } else if (checkTypes === 'monitoring') {
      await TableComparisonResultsApi.getTableComparisonMonitoringResults(
        connection,
        schema,
        table,
        timePartitioned === 'daily' ? 'daily' : 'monthly',
        selectedReference ?? ''
      ).then((res) => setTableComparisonResults(res.data));
    } else if (checkTypes === 'partitioned') {
      await TableComparisonResultsApi.getTableComparisonPartitionedResults(
        connection,
        schema,
        table,
        timePartitioned === 'daily' ? 'daily' : 'monthly',
        selectedReference ?? ''
      ).then((res) => setTableComparisonResults(res.data));
    }
  };

  const compareTables = async () => {
    onUpdate(
      connection,
      schema,
      table,
      checkTypes,
      timePartitioned,
      reference,
      handleChange,
      checksUI
    );
    setIsUpdated(false);
    try {
      const res = await JobApiClient.runChecks(undefined, false, undefined, {
        check_search_filters: categoryCheck
          ? categoryCheck?.run_checks_job_template
          : {
              connection: connection,
              fullTableName: schema + '.' + table,
              tableComparisonName:
                reference?.table_comparison_configuration_name,
              enabled: true,
              checkCategory: 'comparisons',
              checkType: checkTypes as CheckSearchFiltersCheckTypeEnum
            }
      });
      setJobId(res.data?.jobId?.jobId);
    } catch (err) {
      console.error(err);
    }
  };

  const deleteData = async (params: { [key: string]: string | boolean }) => {
    try {
      const res = await JobApiClient.deleteStoredData(
        undefined,
        false,
        undefined,
        {
          ...(reference?.compare_table_clean_data_job_template || {}),
          ...params
        }
      );
      setJobId(res.data?.jobId?.jobId);
    } catch (err) {
      console.error(err);
    }
  };

  const disabled =
    job &&
    job?.status !== DqoJobHistoryEntryModelStatusEnum.finished &&
    job?.status !== DqoJobHistoryEntryModelStatusEnum.failed &&
    job?.status !== DqoJobHistoryEntryModelStatusEnum.cancelled;

  useEffect(() => {
    if (
      job?.status === DqoJobHistoryEntryModelStatusEnum.finished ||
      job?.status === DqoJobHistoryEntryModelStatusEnum.failed
    ) {
      getResultsData();
    }
  }, [job?.status, selectedReference]);

  const columnKey = Object.keys(
    tableComparisonResults?.table_comparison_results ?? []
  ).find((key) => key.includes('column'));

  const rowKey = Object.keys(
    tableComparisonResults?.table_comparison_results ?? []
  ).find((key) => key.includes('row'));

  useEffect(() => {
    const fetchColumns = async () => {
      try {
        const columnRes = await ColumnApiClient.getColumns(
          connection,
          schema,
          table,
          { validateStatus: validate404Status }
        );
        setComparedColumnOptions(
          columnRes.data.map((item) => ({
            label: item.column_name ?? '',
            value: item.column_name ?? ''
          }))
        );
      } catch (error) {
        console.error(error);
      }
    };

    fetchColumns();
  }, [connection, schema, table]);

  useEffect(() => {
    if (
      parameters.refConnection &&
      parameters.refSchema &&
      parameters.refTable
    ) {
      const fetchReferenceColumns = async () => {
        try {
          const columnRes = await ColumnApiClient.getColumns(
            parameters.refConnection ?? '',
            parameters.refSchema ?? '',
            parameters.refTable ?? '',
            { validateStatus: validate404Status }
          );
          setColumnOptions(
            columnRes.data.map((item) => ({
              label: item.column_name ?? '',
              value: item.column_name ?? ''
            }))
          );
        } catch (error) {
          console.error(error);
        }
      };

      fetchReferenceColumns();
    }
  }, [parameters.refTable]);

  useEffect(() => {
    if (selectedReference) {
      const fetchTableComparisonConfiguration = async () => {
        try {
          setLoading(true);
          const res = await TableComparisonsApi.getTableComparisonConfiguration(
            connection,
            schema,
            table,
            selectedReference
          );
          if (res && res.data) {
            onChangeParameters({
              name: res.data?.table_comparison_configuration_name ?? '',
              refConnection: res.data?.reference_connection ?? '',
              refSchema: res.data?.reference_table?.schema_name ?? '',
              refTable: res.data?.reference_table?.table_name ?? '',
              dataGroupingArray: res.data.grouping_columns ?? []
            });
          }
        } catch (error) {
          console.error(error);
        } finally {
          setLoading(false);
        }
      };

      fetchTableComparisonConfiguration();
    }
  }, [selectedReference]);

  const memoizedReference = useMemo(() => {
    return {
      grouping_columns: parameters.dataGroupingArray,
      reference_connection: parameters.refConnection,
      reference_table: {
        table_name: parameters.refTable,
        schema_name: parameters.refSchema
      }
    };
  }, [parameters]);

  useEffect(() => {
    setReference((prevState) => ({
      ...prevState,
      ...memoizedReference
    }));
  }, [memoizedReference]);

  if (loading === true) {
    return (
      <div className="flex justify-center h-100">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }

  return (
    <div className="text-sm">
      <div className="flex flex-col items-center justify-between border-b border-t border-gray-300 py-2 px-8 w-full">
        <EditReferenceTable
          onUpdateChecks={() =>
            onUpdate(
              connection,
              schema,
              table,
              checkTypes,
              timePartitioned,
              reference,
              handleChange,
              checksUI
            )
          }
          deleteData={deleteData}
          onBack={onBack}
          onChangeIsUpdated={(isUpdated: boolean) => setIsUpdated(isUpdated)}
          isUpdated={isUpdated}
          selectedReference={selectedReference}
          timePartitioned={timePartitioned}
          editConfigurationParameters={parameters}
          onChangeParameters={onChangeParameters}
          compareTables={compareTables}
          disabled={disabled}
          setConfigurationToEditing={(name: string) => {
            onChangeSelectedReference(name), getNewTableComparison();
          }}
          existingTableComparisonConfigurations={listOfExistingReferences}
          columnOptions={{
            comparedColumnsOptions: comparedColumnOptions ?? [],
            referencedColumnsOptions: columnOptions
          }}
        />
      </div>
      {reference &&
        reference.columns !== undefined &&
        Object.keys(reference).length > 0 && (
          <SectionWrapper title="" className=" my-4 mx-4">
            <table className="max-w-300">
              <thead>
                <tr>
                  <th className="text-left pr-4 py-1.5">
                    Table-level comparison
                  </th>
                  <th></th>
                  <th className="text-center py-1.5">
                    Row count
                  </th>
                  <th className="text-center py-1.5 whitespace-nowrap">
                    {reference.supports_compare_column_count === true
                      ? 'Column count'
                      : ''}
                  </th>
                </tr>
              </thead>
              <TableRow
                table={table}
                tableLevelComparisonExtended={tableLevelComparisonExtended}
                settableLevelComparisonExtended={
                  settableLevelComparisonExtended
                }
                showRowCount={showRowCount}
                onUpdateChecksUI={onUpdateChecksUI}
                checksUI={checksUI}
                setIsUpdated={setIsUpdated}
                tableComparisonResults={tableComparisonResults}
                showColumnCount={showColumnCount}
                reference={reference}
                checkTypes={checkTypes}
              />
              {tableLevelComparisonExtended && (
              <tr>
                <th></th>
                <th></th>
                <th>
                    <div className="flex flex-col font-normal !w-30 !max-w-30 !min-w-30">
                      {showRowCount ? (
                        <SeverityInputBlock
                        onChange={onChangeCompareRowCount}
                        reference={reference}
                        onUpdateChecksUI={onUpdateChecksUI}
                        checksUI={checksUI}
                        type="row"
                        />
                        ) : (
                          <div className="h-39"></div>
                          )}
                      {rowKey ? (
                        <TableLevelResults
                        tableComparisonResults={tableComparisonResults}
                        type={rowKey}
                        />
                        ) : null}
                    </div>
                </th>              
                <th>
                  {reference?.supports_compare_column_count === true ? (
                    <div className="flex flex-col font-normal !w-30 !max-w-30 !min-w-30">
                      {showColumnCount ? (
                        <SeverityInputBlock
                        onChange={onChangeCompareColumnCount}
                        reference={reference}
                        onUpdateChecksUI={onUpdateChecksUI}
                        checksUI={checksUI}
                        type="column"
                        />
                        ) : (
                          <div className="h-39"></div>
                          )}
                      {columnKey ? (
                        <TableLevelResults
                        tableComparisonResults={tableComparisonResults}
                        type={columnKey}
                        />
                        ) : null}
                    </div>
                  ) : null}
                </th>
              </tr>
                )}
              <tr>
                <th className="text-left pr-4 py-1.5">Compared column</th>
                <th className="text-left px-4 py-1.5"></th>
                {checkNames.map((x, index) => (
                  <th
                    className="text-center px-4 py-1.5 pr-1 w-25"
                    key={index}
                  >
                    {x}
                  </th>
                ))}
              </tr>
              {reference?.columns?.map((item, index) => (
                <TableComparisonOverwiewBody
                  key={index}
                  item={item}
                  index={index}
                  columnOptions={columnOptions}
                  checkTypes={checkTypes}
                  tableComparisonResults={tableComparisonResults}
                  reference={reference}
                  onChange={(obj: Partial<TableComparisonModel>) => {
                    onChange(obj), setIsUpdated(true);
                  }}
                />
              ))}
            </table>
          </SectionWrapper>
        )}
    </div>
  );
};
