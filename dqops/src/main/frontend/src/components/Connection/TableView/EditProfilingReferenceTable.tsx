import React, { useEffect, useState } from 'react';
import Input from '../../Input';
import SvgIcon from '../../SvgIcon';
import {
  ColumnApiClient,
  TableComparisonResultsApi,
  JobApiClient,
  TableComparisonsApi
} from '../../../services/apiClient';
import { CheckTypes, ROUTES } from '../../../shared/routes';
import { useHistory, useParams } from 'react-router-dom';
import {
  ColumnComparisonModel,
  CompareThresholdsModel,
  TableComparisonModel,
  TableComparisonResultsModel,
  DqoJobHistoryEntryModelStatusEnum,
  QualityCategoryModel,
  ComparisonCheckResultModel
} from '../../../api';
import SectionWrapper from '../../Dashboard/SectionWrapper';
import Checkbox from '../../Checkbox';
import Select, { Option } from '../../Select';
import {
  addFirstLevelTab,
  setCurrentJobId
} from '../../../redux/actions/source.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../../redux/selectors';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import clsx from 'clsx';
import ResultPanel from './ResultPanel';
import EditReferenceTable from './EditReferenceTable';
import useConnectionSchemaTableExists from '../../../hooks/useConnectionSchemaTableExists';

type EditProfilingReferenceTableProps = {
  onBack: (stayOnSamePage?: boolean | undefined) => void;
  selectedReference?: string;
  checkTypes: CheckTypes;
  timePartitioned?: 'daily' | 'monthly';
  categoryCheck?: QualityCategoryModel;
  isCreating?: boolean;
  getNewTableComparison: () => void;
  onChangeSelectedReference: (arg: string) => void;
  listOfExistingReferences: Array<string | undefined>;
  canUserCompareTables?: boolean;
};

const itemsToRender = [
  {
    key: 'min_match',
    prop: 'compare_min'
  },
  {
    key: 'max_match',
    prop: 'compare_max'
  },
  {
    key: 'sum_match',
    prop: 'compare_sum'
  },
  {
    key: 'mean_match',
    prop: 'compare_mean'
  },
  {
    key: 'null_count_match',
    prop: 'compare_null_count'
  },
  {
    key: 'not_null_count_match',
    prop: 'compare_not_null_count'
  }
];

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
  canUserCompareTables
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
  const {
    checksUI,
    dailyMonitoring,
    monthlyMonitoring,
    dailyPartitionedChecks,
    monthlyPartitionedChecks
  } = useSelector(getFirstLevelState(checkTypes));
  const [reference, setReference] = useState<TableComparisonModel>();
  const [showRowCount, setShowRowCount] = useState(false);
  const [showColumnCount, setShowColumnCount] = useState(false)
  const [columnOptions, setColumnOptions] = useState<Option[]>([]);
  const [jobId, setJobId] = useState<number>();
  const [loading, setLoading] = useState(false);
  const job = jobId ? job_dictionary_state[jobId] : undefined;
  const [isElemExtended, setIsElemExtended] = useState<Array<boolean>>([]);
  const [tableComparisonResults, setTableComparisonResults] =
    useState<TableComparisonResultsModel>();
  const [tableLevelComparisonExtended, settableLevelComparisonExtended] = useState(false);
  const history = useHistory();
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [isDataDeleted, setIsDataDeleted] = useState(false);

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
    if (checkTypes === CheckTypes.PROFILING) {
      values = Object.values(checksUI);
    } else if (checkTypes === CheckTypes.PARTITIONED) {
      if (timePartitioned === 'daily') {
        values = Object.values(dailyPartitionedChecks ?? {});
      } else {
        values = Object.values(monthlyPartitionedChecks ?? {});
      }
    } else if (checkTypes === CheckTypes.MONITORING) {
      if (timePartitioned === 'daily') {
        values = Object.values(dailyMonitoring ?? {});
      } else {
        values = Object.values(monthlyMonitoring ?? {});
      }
    }

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
    if(columnCountElem){
      setShowColumnCount(!!columnCountElem.configured)
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
    }
  }, [selectedReference, tableExist, schemaExist, connectionExist]);

  const goToRefTable = () => {
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

  const onUpdate = () => {
    if (
      reference !== undefined &&
      Object.keys(reference).length > 0 &&
      reference.table_comparison_configuration_name
    ) {
      if (checkTypes === CheckTypes.PROFILING) {
        TableComparisonsApi.updateTableComparisonProfiling(
          connection,
          schema,
          table,
          reference?.table_comparison_configuration_name ?? '',
          reference
        ).catch((err) => {
          console.error(err);
        });
      } else if (checkTypes === CheckTypes.MONITORING) {
        if (timePartitioned === 'daily') {
          TableComparisonsApi.updateTableComparisonMonitoringDaily(
            connection,
            schema,
            table,
            reference?.table_comparison_configuration_name ?? '',
            reference
          ).catch((err) => {
            console.error(err);
          });
        } else if (timePartitioned === 'monthly') {
          TableComparisonsApi.updateTableComparisonMonitoringMonthly(
            connection,
            schema,
            table,
            reference?.table_comparison_configuration_name ?? '',
            reference
          ).catch((err) => {
            console.error(err);
          });
        }
      } else if (checkTypes === CheckTypes.PARTITIONED) {
        if (timePartitioned === 'daily') {
          TableComparisonsApi.updateTableComparisonPartitionedDaily(
            connection,
            schema,
            table,
            reference?.table_comparison_configuration_name ?? '',
            reference
          ).catch((err) => {
            console.error(err);
          });
        } else if (timePartitioned === 'monthly') {
          TableComparisonsApi.updateTableComparisonPartitionedMonthly(
            connection,
            schema,
            table,
            reference?.table_comparison_configuration_name ?? '',
            reference
          ).catch((err) => {
            console.error(err);
          });
        }
      }
    }
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

  const onChangeColumn = (
    obj: Partial<ColumnComparisonModel>,
    columnIndex: number
  ) => {
    const newColumns = reference?.columns?.map((item, index) =>
      index === columnIndex
        ? {
            ...item,
            ...obj
          }
        : item
    );
    onChange({
      columns: newColumns
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

  const handleExtend = (index: number) => {
    const newArr = [...isElemExtended];
    newArr[index] = !isElemExtended[index];
    setIsElemExtended(newArr);
  };

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
      const res = await JobApiClient.runChecks(
        false,
        undefined,
        categoryCheck?.run_checks_job_template
          ? {
              check_search_filters: categoryCheck?.run_checks_job_template
            }
          : undefined
      );
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

  const prepareData = (
    nameOfColumn: string
  ): { [key: string]: ComparisonCheckResultModel } => {
    const columnComparisonResults =
      tableComparisonResults?.column_comparison_results ?? {};

    if (Object.keys(columnComparisonResults).find((x) => x === nameOfColumn)) {
      return columnComparisonResults[nameOfColumn] ?? [];
    } else {
      return {};
    }
  };

  const calculateColor = (
    nameOfCol: string,
    nameOfCheck: string,
    bool?: boolean
  ): string => {
    let newNameOfCheck = '';
    if (checkTypes === CheckTypes.PROFILING) {
      newNameOfCheck = 'profile_' + nameOfCheck;
    }
    if (
      checkTypes === CheckTypes.MONITORING ||
      checkTypes === CheckTypes.PARTITIONED
    ) {
      newNameOfCheck = nameOfCheck;
    }

    let colorVar = prepareData(nameOfCol)[newNameOfCheck];
    if (
      bool &&
      tableComparisonResults?.table_comparison_results &&
      tableComparisonResults
    ) {
      const comparisonResult = Object.values(
        tableComparisonResults.table_comparison_results
      )?.at(0);
      if (comparisonResult) {
        colorVar = comparisonResult;
      }
    }

    if (colorVar && colorVar.fatals && Number(colorVar.fatals) !== 0) {
      return 'bg-red-200';
    } else if (colorVar && colorVar.errors && Number(colorVar.errors) !== 0) {
      return 'bg-orange-200';
    } else if (
      colorVar &&
      colorVar.warnings &&
      Number(colorVar.warnings) !== 0
    ) {
      return 'bg-yellow-200';
    } else if (
      colorVar &&
      colorVar.valid_results &&
      Number(colorVar.valid_results) !== 0
    ) {
      return 'bg-green-200';
    } else {
      return '';
    }
  };

  useEffect(() => {
    getResultsData();
  }, [isDataDeleted]);
  
  const columnKey = Object.keys(tableComparisonResults?.table_comparison_results ??  [])
  .find((key) => key.includes("column_count_match"));

  const rowKey = Object.keys(tableComparisonResults?.table_comparison_results ??  [])
  .find((key) => key.includes("row_count_match"));

  console.log(reference)

  return (
    <div className="text-sm">
      <div className="flex flex-col items-center justify-between border-b border-t border-gray-300 py-2 px-8 w-full">
        <EditReferenceTable
          onUpdateParent = {onUpdate}
          onBack={onBack}
          onChange={onChange}
          selectedReference={selectedReference}
          isUpdatedParent={isUpdated}
          timePartitioned={timePartitioned}
          onRunChecksRowCount={onRunChecks}
          disabled={disabled || loading}
          isCreating={isCreating}
          goToRefTable={goToRefTable}
          onChangeUpdatedParent={onChangeUpdatedParent}
          combinedFunc={(name: string) => {
            onChangeSelectedReference(name), getNewTableComparison();
          }}
          cleanDataTemplate={reference?.compare_table_clean_data_job_template}
          onChangeIsDataDeleted={onChangeIsDataDeleted}
          isDataDeleted={isDataDeleted}
          listOfExistingReferences={listOfExistingReferences}
          canUserCompareTables={canUserCompareTables}
        />
      </div>
      {reference &&
        reference.columns !== undefined &&
        Object.keys(reference).length > 0 && (
          <div>
            <div className="px-8 py-4">
              <SectionWrapper title="">
                <table className="w-full">
                  <thead>
                    <tr>
                      <th className="text-left pr-4 py-1.5">
                        Table level comparison
                      </th>
                      <th className="text-left px-4 py-1.5"></th>
                      <th className="text-center px-4 py-1.5 pr-1 w-1/12">
                        Row Count
                      </th>
                      <th className="text-center px-4 py-1.5 pr-1 w-1/12">{reference.supports_compare_column_count === true ? "Column Count" : ""}</th>
                      <th className="text-center px-4 py-1.5 pr-1 w-1/12"></th>
                      <th className="text-center px-4 py-1.5 pr-1 w-1/12"></th>
                      <th className="text-center px-4 py-1.5 pr-1 w-1/12"></th>
                      <th className="text-center px-4 py-1.5 pr-1 w-1/12"></th>
                    </tr>
                  </thead>
                  <thead className="mt-10 mb-10">
                    <tr>
                      <th
                        className="text-left pr-4 py-1.5 flex items-center gap-x-2 font-normal"
                        onClick={() => settableLevelComparisonExtended(!tableLevelComparisonExtended)}
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
                          'text-center px-0 py-4 pr-2 w-1/12 ',
                          showRowCount ? calculateColor('', '', true) : ''
                        )}
                      >
                        <Checkbox
                          checked={showRowCount}
                          onChange={(checked) => {
                            setShowRowCount(checked);
                          }}
                        />{' '}
                      </th>
                      <th
                        className={clsx(
                          'text-center px-0 py-4 pr-2 w-1/12 ',
                          showColumnCount && reference.supports_compare_column_count=== true ? calculateColor('', '', true) : ''
                        )}
                      >
                        {reference.supports_compare_column_count===true ? 
                        <Checkbox
                          checked={showColumnCount}
                          onChange={(checked) => {
                            setShowColumnCount(checked);
                          }}
                        /> : null }
                      </th>
                      <th className="text-center px-4 py-1.5 pr-1 w-1/12"></th>
                      <th className="text-center px-4 py-1.5 pr-1 w-1/12"></th>
                      <th className="text-center px-4 py-1.5 pr-1 w-1/12"></th>
                      <th className="text-center px-4 py-1.5 pr-1 w-1/12"></th>
                    </tr>
                  </thead>
                  <thead className="mt-10 mb-10">
                    <tr>
                      <th className="text-left pr-4 py-1.5 flex items-center gap-x-2 font-normal"></th>
                      <th className="text-left px-4 py-1.5"></th>
                      <th>
                        {tableLevelComparisonExtended && (
                          <div className="flex flex-col w-full font-normal">
                            {rowKey ? (
                              <div className="gap-y-3">
                                Results:
                                <td className="flex justify-between w-2/3 ">
                                  <th className="text-xs font-light">Valid:</th>
                                  {                               
                                  tableComparisonResults?.table_comparison_results?.[rowKey ?? '']?.valid_results
                                  }
                                </td>
                                <td className="flex justify-between w-2/3 ">
                                  <th className="text-xs font-light">
                                    Errors:
                                  </th>
                                  {
                                   tableComparisonResults?.table_comparison_results?.[rowKey ?? ""]?.errors
                                  }
                                </td>
                                <td className="flex justify-between w-2/3 ">
                                  <th className="text-xs font-light">Fatal:</th>
                                  {
                                   tableComparisonResults?.table_comparison_results?.[rowKey ?? ""]?.fatals
                                  }
                                </td>
                                <td className="flex justify-between w-2/3 ">
                                  <th className="text-xs font-light">
                                    Warning:
                                  </th>
                                  {
                                    tableComparisonResults?.table_comparison_results?.[rowKey ?? ""]?.warnings
                                  }
                                </td>
                              </div>
                            ) : null}
                            {showRowCount && (
                              <div className="flex flex-col pt-0 mt-0 w-full">
                                <div className="bg-yellow-100 px-4 py-2 flex items-center gap-2">
                                  <Input
                                    className="max-w-30 !min-w-initial"
                                    type="number"
                                    value={
                                      reference?.compare_row_count
                                        ?.warning_difference_percent
                                    }
                                    onChange={(e) =>
                                      onChangeCompareRowCount({
                                        warning_difference_percent: Number(
                                          e.target.value
                                        )
                                      })
                                    }
                                  />
                                  %
                                </div>
                                <div className="bg-orange-100 px-4 py-2 flex items-center gap-2">
                                  <Input
                                    className="max-w-30 !min-w-initial"
                                    type="number"
                                    value={
                                      reference?.compare_row_count
                                        ?.error_difference_percent
                                    }
                                    onChange={(e) =>
                                      onChangeCompareRowCount({
                                        error_difference_percent: Number(
                                          e.target.value
                                        )
                                      })
                                    }
                                  />
                                  %
                                </div>
                                <div className="bg-red-100 px-4 py-2 flex items-center gap-2">
                                  <Input
                                    className="max-w-30 !min-w-initial"
                                    type="number"
                                    value={
                                      reference?.compare_row_count
                                        ?.fatal_difference_percent
                                    }
                                    onChange={(e) =>
                                      onChangeCompareRowCount({
                                        fatal_difference_percent: Number(
                                          e.target.value
                                        )
                                      })
                                    }
                                  />
                                  %
                                </div>
                              </div>
                            )}
                          </div>
                        )}
                      </th>
                      <th>
                        {(tableLevelComparisonExtended  && reference?.supports_compare_column_count===true) ? (
                          <div className="flex flex-col w-full font-normal">
                            {columnKey ? 
                              <div className="gap-y-3">
                                Results:
                                <td className="flex justify-between w-2/3 ">
                                  <th className="text-xs font-light">Valid:</th>
                                  {
                                   tableComparisonResults?.table_comparison_results?.[columnKey ?? ""]?.valid_results
                                  }
                                </td>
                                <td className="flex justify-between w-2/3 ">
                                  <th className="text-xs font-light">
                                    Errors:
                                  </th>
                                  {
                                    tableComparisonResults?.table_comparison_results?.[columnKey ?? ""]?.errors
                                  }
                                </td>
                                <td className="flex justify-between w-2/3 ">
                                  <th className="text-xs font-light">Fatal:</th>
                                  {
                                 tableComparisonResults?.table_comparison_results?.[columnKey ?? ""]?.fatals
                                  }
                                </td>
                                <td className="flex justify-between w-2/3 ">
                                  <th className="text-xs font-light">
                                    Warning:
                                  </th>
                                  {
                                 tableComparisonResults?.table_comparison_results?.[columnKey ?? ""]?.warnings
                                  }
                                </td>
                              </div>
                              : null
                              }           
                            {showColumnCount && (
                              <div className="flex flex-col pt-0 mt-0 w-full">
                                <div className="bg-yellow-100 px-4 py-2 flex items-center gap-2">
                                  <Input
                                    className="max-w-30 !min-w-initial"
                                    type="number"
                                    value={
                                      reference?.compare_column_count
                                        ?.warning_difference_percent
                                    }
                                    onChange={(e) =>
                                      onChangeCompareColumnCount({
                                        warning_difference_percent: Number(
                                          e.target.value
                                        )
                                      })
                                    }
                                  />
                                  %
                                </div>
                                <div className="bg-orange-100 px-4 py-2 flex items-center gap-2">
                                  <Input
                                    className="max-w-30 !min-w-initial"
                                    type="number"
                                    value={
                                      reference?.compare_column_count
                                        ?.error_difference_percent
                                    }
                                    onChange={(e) =>
                                      onChangeCompareColumnCount({
                                        error_difference_percent: Number(
                                          e.target.value
                                        )
                                      })
                                    }
                                  />
                                  %
                                </div>
                                <div className="bg-red-100 px-4 py-2 flex items-center gap-2">
                                  <Input
                                    className="max-w-30 !min-w-initial"
                                    type="number"
                                    value={
                                      reference?.compare_column_count
                                        ?.fatal_difference_percent
                                    }
                                    onChange={(e) =>
                                      onChangeCompareColumnCount({
                                        fatal_difference_percent: Number(
                                          e.target.value
                                        )
                                      })
                                    }
                                  />
                                  %
                                </div>
                              </div>
                            )}
                          </div>
                        ) : null}
                      </th>
                    </tr>
                  </thead>
                  <thead>
                    <tr>
                      <th className="text-left pr-4 py-1.5">Compared column</th>
                      <th className="text-left px-4 py-1.5"></th>
                      {[
                        'Min',
                        'Max',
                        'Sum',
                        'Mean',
                        'Nulls count',
                        'Not nulls count'
                      ].map((x, index) => (
                        <th
                          className="text-center px-4 py-1.5 pr-1 w-1/12"
                          key={index}
                        >
                          {x}
                        </th>
                      ))}
                    </tr>
                  </thead>

                  {reference?.columns?.map((item, index) => (
                    <tbody key={index}>
                      <tr key={index}>
                        <td
                          className="text-left pr-4 py-1.5 flex items-center gap-x-2 cursor-pointer"
                          onClick={() => {
                            handleExtend(index);
                          }}
                        >
                          {isElemExtended?.at(index) ? (
                            <SvgIcon name="chevron-down" className="w-5 h-5" />
                          ) : (
                            <SvgIcon name="chevron-right" className="w-5 h-5" />
                          )}
                          {item.compared_column_name}
                        </td>
                        <td className="text-left px-4 py-1.5">
                          <Select
                            value={
                              item.reference_column_name !== undefined
                                ? item.reference_column_name
                                : ''
                            }
                            options={[
                              { value: '', label: '' },
                              ...columnOptions
                            ]}
                            onChange={(e) =>
                              onChangeColumn(
                                { reference_column_name: e },
                                index
                              )
                            }
                            empty={true}
                            placeholder=""
                          />
                        </td>
                        {itemsToRender.map((itemData, jIndex) => (
                          <td
                            key={jIndex}
                            className={clsx(
                              'text-center px-4 py-1.5',
                              calculateColor(
                                item.compared_column_name ?? '',
                                itemData.key
                              )
                            )}
                          >
                            <Checkbox
                              checked={
                                !!item[
                                  itemData.prop as keyof ColumnComparisonModel
                                ] &&
                                !(
                                  item.reference_column_name === undefined ||
                                  item.reference_column_name.length === 0 ||
                                  !columnOptions.find(
                                    (x) =>
                                      x.label === item.reference_column_name
                                  )
                                )
                              }
                              onChange={(checked) => {
                                onChangeColumn(
                                  {
                                    [itemData.prop as keyof ColumnComparisonModel]:
                                      checked
                                        ? reference?.default_compare_thresholds
                                        : undefined
                                  },
                                  index
                                );
                              }}
                              disabled={
                                item.reference_column_name === undefined ||
                                item.reference_column_name.length === 0 ||
                                !columnOptions.find(
                                  (x) => x.label === item.reference_column_name
                                )
                              }
                              isDisabled={
                                item.reference_column_name === undefined ||
                                item.reference_column_name.length === 0 ||
                                !columnOptions.find(
                                  (x) => x.label === item.reference_column_name
                                )
                              }
                            />
                          </td>
                        ))}
                      </tr>
                      {isElemExtended.at(index) && (
                        <ResultPanel
                          obj={prepareData(item.compared_column_name ?? '')}
                          onChange={onChange}
                          bools={[
                            !!item.compare_min,
                            !!item.compare_max,
                            !!item.compare_sum,
                            !!item.compare_mean,
                            !!item.compare_null_count,
                            !!item.compare_not_null_count
                          ]}
                          reference={reference}
                          index={index}
                        />
                      )}
                    </tbody>
                  ))}
                </table>
              </SectionWrapper>
            </div>
          </div>
        )}
    </div>
  );
};
