import React, { ReactNode, useEffect, useState } from 'react';
import SvgIcon from '../../../SvgIcon';
import {
  ColumnApiClient,
  TableComparisonResultsApi,
  JobApiClient,
  TableComparisonsApi
} from '../../../../services/apiClient';
import { CheckTypes, ROUTES } from '../../../../shared/routes';
import { useHistory, useParams } from 'react-router-dom';
import {
  CompareThresholdsModel,
  TableComparisonModel,
  TableComparisonResultsModel,
  DqoJobHistoryEntryModelStatusEnum,
  CheckSearchFiltersCheckTypeEnum,
  CheckContainerModel
} from '../../../../api';
import SectionWrapper from '../../../Dashboard/SectionWrapper';
import Checkbox from '../../../Checkbox';
import { Option } from '../../../Select';
import {
  addFirstLevelTab,
  setCurrentJobId
} from '../../../../redux/actions/source.actions';
import { useActionDispatch } from '../../../../hooks/useActionDispatch';
import { getFirstLevelActiveTab } from '../../../../redux/selectors';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../../redux/reducers';
import clsx from 'clsx';
import EditReferenceTable from './EditReferenceTable';
import useConnectionSchemaTableExists from '../../../../hooks/useConnectionSchemaTableExists';
import { setUpdatedChecksModel } from '../../../../redux/actions/table.actions';
import { Tooltip } from '@material-tailwind/react';
import {
  EditProfilingReferenceTableProps,
  TParameters,
  TSeverityValues,
  checkNames
} from './TableComparisonConstans';
import { calculateColor, onUpdate } from './TableComparisonUtils';
import TableComparisonOverwiewBody from './TableComparisonOverwiewBody';
import TableLevelResults from './TableLevelResults';
import SeverityInputBlock from './SeverityInputBlock';

export const EditProfilingReferenceTable = ({
  checkTypes,
  timePartitioned,
  onBack,
  selectedReference,
  categoryCheck,
  isCreating,
  getNewTableComparison,
  onChangeSelectedReference,
  listOfExistingReferences,
  canUserCompareTables,
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
  } = useParams();
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
  const [loading, setLoading] = useState(false);
  const job = jobId ? job_dictionary_state[jobId] : undefined;
  const [tableComparisonResults, setTableComparisonResults] =
    useState<TableComparisonResultsModel>();
  const [tableLevelComparisonExtended, settableLevelComparisonExtended] =
    useState(false);
  const history = useHistory();
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [isDataDeleted, setIsDataDeleted] = useState(false);
  const [parameters, setParameters] = useState<TParameters>({});

  const onChangeParameters = (obj: Partial<TParameters>) => {
    setParameters((prevState) => ({
      ...prevState,
      ...obj
    }));
  };

  const { tableExist, schemaExist, connectionExist } =
    useConnectionSchemaTableExists(
      reference?.reference_connection ?? '',
      reference?.reference_table?.schema_name ?? '',
      reference?.reference_table?.table_name ?? ''
    );

  const onChangeIsDataDeleted = (arg: boolean): void => {
    setIsDataDeleted(arg);
  };

  const onChangeUpdatedParent = (variable: boolean): void => {
    setIsUpdated(variable);
  };

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

    const rowCountElem = comparisonCategory.checks.find(
      (c: any) =>
        c.check_name === 'profile_row_count_match' ||
        c.check_name === 'daily_row_count_match' ||
        c.check_name === 'monthly_row_count_match'
    );

    const columnCountElem = comparisonCategory.checks.find(
      (c: any) =>
        c.check_name === 'profile_column_count_match' ||
        c.check_name === 'daily_column_count_match' ||
        c.check_name === 'monthly_column_count_match'
    );

    if (rowCountElem) {
      setShowRowCount(!!rowCountElem.configured);
    }
    if (columnCountElem) {
      setShowColumnCount(!!columnCountElem.configured);
    }
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
    if (severity) {
      if (severity.warning) {
        selectedCheck.rule.warning.rule_parameters[0].double_value =
          severity.warning;
        selectedCheck.rule.warning.configured = true;
      }
      if (severity.error) {
        selectedCheck.rule.error.rule_parameters[0].double_value =
          severity.error;
        selectedCheck.rule.error.configured = true;
      }
      if (severity.fatal) {
        selectedCheck.rule.fatal.rule_parameters[0].double_value =
          severity.fatal;
        selectedCheck.rule.fatal.configured = true;
      }
    }
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
      checkIfRowAndColumnCountClicked();
    }
  }, [selectedReference]);

  useEffect(() => {
    if (
      reference !== undefined &&
      isCreating === false &&
      connectionExist === true &&
      schemaExist === true &&
      tableExist === true &&
      reference.reference_connection?.length !== 0 &&
      reference.reference_table?.schema_name?.length !== 0 &&
      reference.reference_table?.table_name?.length !== 0
    ) {
      ColumnApiClient.getColumns(
        reference.reference_connection ?? '',
        reference.reference_table?.schema_name ?? '',
        reference.reference_table?.table_name ?? ''
      )?.then((columnRes) => {
        if (
          columnRes &&
          columnRes.data.length !== 0 &&
          Array.isArray(columnRes.data)
        ) {
          setColumnOptions(
            columnRes.data.map((item) => ({
              label: item.column_name ?? '',
              value: item.column_name ?? ''
            }))
          );
        }
      });
    } else if (
      parameters.refConnection &&
      parameters.refSchema &&
      parameters.refTable &&
      ((connectionExist === true &&
        schemaExist === true &&
        tableExist === true) ||
        isCreating === true)
    ) {
      ColumnApiClient.getColumns(
        parameters.refConnection,
        parameters.refSchema,
        parameters.refTable
      )?.then((columnRes) => {
        setColumnOptions(
          columnRes.data.map((item) => ({
            label: item.column_name ?? '',
            value: item.column_name ?? ''
          }))
        );
      });
    }
  }, [
    selectedReference,
    tableExist,
    schemaExist,
    connectionExist,
    reference?.reference_connection,
    reference?.reference_table,
    parameters.refTable
  ]);

  const goToRefTable = (reference: TableComparisonModel | undefined) => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      CheckTypes.SOURCES,
      reference?.reference_connection ?? '',
      reference?.reference_table?.schema_name ?? '',
      reference?.reference_table?.table_name ?? '',
      'detail'
    );
    const value = ROUTES.TABLE_LEVEL_VALUE(
      CheckTypes.SOURCES,
      reference?.reference_connection ?? '',
      reference?.reference_table?.schema_name ?? '',
      reference?.reference_table?.table_name ?? ''
    );
    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url: url,
        value,
        label: reference?.reference_table?.table_name ?? '',
        state: {}
      })
    );

    history.push(url);
  };

  const onChange = (obj: Partial<TableComparisonModel>): void => {
    setReference({
      ...(reference || {}),
      ...obj
    });
    setIsUpdated(true);
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
    if (showRowCount) {
      onChange({
        compare_row_count: reference?.default_compare_thresholds
      });
    }
    if (showColumnCount) {
      onChange({
        compare_column_count: reference?.default_compare_thresholds
      });
    }
  }, [showRowCount, showColumnCount]);

  const getResultsData = async () => {
    if (isCreating === false) {
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
    }
  };

  const onRunChecks = async () => {
    try {
      setLoading(true);
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
      dispatch(
        setCurrentJobId(
          checkTypes,
          firstLevelActiveTab,
          res.data?.jobId?.jobId ?? 0
        )
      );
      setJobId(res.data?.jobId?.jobId);
    } catch (err) {
      console.error(err);
    }
  };
  const disabled =
    job &&
    job?.status !== DqoJobHistoryEntryModelStatusEnum.succeeded &&
    job?.status !== DqoJobHistoryEntryModelStatusEnum.failed;

  useEffect(() => {
    if (
      job?.status === DqoJobHistoryEntryModelStatusEnum.succeeded ||
      job?.status === DqoJobHistoryEntryModelStatusEnum.failed
    ) {
      setLoading(false);
      getResultsData();
    }
  }, [job?.status]);

  useEffect(() => {
    getResultsData();
  }, [isDataDeleted]);

  const columnKey = Object.keys(
    tableComparisonResults?.table_comparison_results ?? []
  ).find((key) => key.includes('column'));

  const rowKey = Object.keys(
    tableComparisonResults?.table_comparison_results ?? []
  ).find((key) => key.includes('row'));

  useEffect(() => {
    if (reference?.columns) {
      setComparedColumnOptions(
        reference?.columns.map((x) => ({
          label: x.compared_column_name ?? '',
          value: x.compared_column_name ?? ''
        }))
      );
    } else {
      ColumnApiClient.getColumns(connection, schema, table)?.then(
        (columnRes) => {
          setComparedColumnOptions(
            columnRes.data.map((item) => ({
              label: item.column_name ?? '',
              value: item.column_name ?? ''
            }))
          );
        }
      );
    }
  }, [reference?.columns, connection, schema, table]);

  const renderWarningTooltip = (): ReactNode => {
    return (
      <Tooltip
        content="Previous comparison results are present, delete the results before comparing the tables again"
        className="pr-6 max-w-80 py-4 px-4 bg-gray-800"
      >
        <div>
          <SvgIcon
            name="warning"
            className="w-5 h-5 absolute bottom-[10px] left-[6px]"
          />
        </div>
      </Tooltip>
    );
  };

  return (
    <div className="text-sm">
      <div className="flex flex-col items-center justify-between border-b border-t border-gray-300 py-2 px-8 w-full">
        <EditReferenceTable
          onUpdateParent={() =>
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
          onBack={onBack}
          onChange={onChange}
          selectedReference={selectedReference}
          isUpdatedParent={isUpdated}
          timePartitioned={timePartitioned}
          onRunChecksRowCount={onRunChecks}
          disabled={disabled || loading}
          isCreating={isCreating}
          goToRefTable={() => goToRefTable(reference)}
          onChangeUpdatedParent={onChangeUpdatedParent}
          combinedFunc={(name: string) => {
            onChangeSelectedReference(name), getNewTableComparison();
          }}
          cleanDataTemplate={reference?.compare_table_clean_data_job_template}
          onChangeIsDataDeleted={onChangeIsDataDeleted}
          isDataDeleted={isDataDeleted}
          listOfExistingReferences={listOfExistingReferences}
          canUserCompareTables={canUserCompareTables}
          columnOptions={{
            comparedColumnsOptions: comparedColumnOptions ?? [],
            referencedColumnsOptions: columnOptions
          }}
          onChangeParameters={onChangeParameters}
        />
      </div>
      {reference &&
        reference.columns !== undefined &&
        Object.keys(reference).length > 0 && (
          <SectionWrapper title="" className=" my-4 mx-4">
            <table className="w-full">
              <thead>
                <tr>
                  <th className="text-left pr-4 py-1.5">
                    Table-level comparison
                  </th>
                  <th></th>
                  <th className="text-center px-4 py-1.5 pr-1 w-1/12">
                    Row count
                  </th>
                  <th className="text-center px-4 py-1.5 pr-1 w-1/12">
                    {reference.supports_compare_column_count === true
                      ? 'Column count'
                      : ''}
                  </th>
                </tr>
              </thead>
              <tr className="mt-10 mb-10">
                <th
                  className="text-left pr-4 py-1.5 flex items-center gap-x-2 font-normal"
                  onClick={() =>
                    settableLevelComparisonExtended((prevState) => !prevState)
                  }
                >
                  {tableLevelComparisonExtended ? (
                    <SvgIcon name="chevron-down" className="w-5 h-5" />
                  ) : (
                    <SvgIcon name="chevron-right" className="w-5 h-5" />
                  )}{' '}
                  {table}
                </th>
                <th className="text-left px-4 py-1.5"></th>
                <th
                  className={clsx(
                    'text-center px-0 py-4 pr-2 w-1/12 relative',
                    calculateColor(
                      '',
                      '',
                      'row_count',
                      checkTypes,
                      tableComparisonResults
                    )
                  )}
                >
                  <Checkbox
                    checked={showRowCount}
                    onChange={(checked) => {
                      onUpdateChecksUI(checksUI, 'row', checked);
                    }}
                  />{' '}
                  {calculateColor(
                    '',
                    '',
                    'row_count',
                    checkTypes,
                    tableComparisonResults
                  ).length !== 0 &&
                    !showRowCount && <>{renderWarningTooltip()}</>}
                </th>
                <th
                  className={clsx(
                    'text-center px-0 py-4 pr-2 w-1/12 relative',
                    reference.supports_compare_column_count === true
                      ? calculateColor(
                          '',
                          '',
                          'column_count',
                          checkTypes,
                          tableComparisonResults
                        )
                      : ''
                  )}
                >
                  {reference.supports_compare_column_count === true ? (
                    <Checkbox
                      checked={showColumnCount}
                      onChange={(checked) => {
                        onUpdateChecksUI(checksUI, 'column', checked);
                      }}
                    />
                  ) : null}
                  {calculateColor(
                    '',
                    '',
                    'column_count',
                    checkTypes,
                    tableComparisonResults
                  ).length !== 0 &&
                    !showColumnCount && <>{renderWarningTooltip()}</>}
                </th>
              </tr>
              <tr>
                <th></th>
                <th></th>
                <th>
                  {tableLevelComparisonExtended && (
                    <div className="flex flex-col w-full font-normal">
                      {showRowCount ? (
                        <SeverityInputBlock
                          onChange={onChangeCompareRowCount}
                          reference={reference}
                          onUpdateChecksUI={onUpdateChecksUI}
                          checksUI={checksUI}
                          type="row"
                        />
                      ) : null}
                      {rowKey ? (
                        <TableLevelResults
                          tableComparisonResults={tableComparisonResults}
                          type={rowKey}
                        />
                      ) : null}
                    </div>
                  )}
                </th>
                <th>
                  {tableLevelComparisonExtended &&
                  reference?.supports_compare_column_count === true ? (
                    <div className="flex flex-col w-full font-normal">
                      {showColumnCount ? (
                        <SeverityInputBlock
                          onChange={onChangeCompareColumnCount}
                          reference={reference}
                          onUpdateChecksUI={onUpdateChecksUI}
                          checksUI={checksUI}
                          type="column"
                        />
                      ) : null}
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
              <tr>
                <th className="text-left pr-4 py-1.5">Compared column</th>
                <th className="text-left px-4 py-1.5"></th>
                {checkNames.map((x, index) => (
                  <th
                    className="text-center px-4 py-1.5 pr-1 w-1/12"
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
                  onChange={onChange}
                />
              ))}
            </table>
          </SectionWrapper>
        )}
    </div>
  );
};
