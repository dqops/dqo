import React, { useEffect, useState } from 'react';
import TableActionGroup from './TableActionGroup';
import Input from '../../Input';
import Button from '../../Button';
import SvgIcon from '../../SvgIcon';
import {
  ColumnApiClient,
  DataGroupingConfigurationsApi,
  TableComparisonResultsApi,
  JobApiClient,
  TableComparisonsApi
} from '../../../services/apiClient';
import { CheckTypes, ROUTES } from '../../../shared/routes';
import { useHistory, useParams } from 'react-router-dom';
import {
  ColumnComparisonModel,
  CompareThresholdsModel,
  DataGroupingConfigurationBasicModel,
  TableComparisonModel,
  TableComparisonResultsModel,
  DqoJobHistoryEntryModelStatusEnum,
  QualityCategoryModel
} from '../../../api';
import SectionWrapper from '../../Dashboard/SectionWrapper';
import Checkbox from '../../Checkbox';
import Select, { Option } from '../../Select';
import {
  addFirstLevelTab,
  setCurrentJobId
} from '../../../redux/actions/source.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { SelectDataGroupingForTableProfiling } from './SelectDataGroupingForTableProfiling';
import { getFirstLevelActiveTab } from '../../../redux/selectors';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import clsx from 'clsx';
import ResultPanel from './ResultPanel';

type EditProfilingReferenceTableProps = {
  onBack: (stayOnSamePage?: boolean | undefined) => void;
  selectedReference?: string;
  checkTypes: CheckTypes;
  timePartitioned?: 'daily' | 'monthly';
  categoryCheck?: QualityCategoryModel;
};

export const EditProfilingReferenceTable = ({
  checkTypes,
  timePartitioned,
  onBack,
  selectedReference,
  categoryCheck
}: EditProfilingReferenceTableProps) => {
  const [isUpdated, setIsUpdated] = useState(false);
  const [isUpdating, setIsUpdating] = useState(false);
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
  const [isRowCountExtended, setIsRowCountExtended] = useState(false);
  const [columnOptions, setColumnOptions] = useState<Option[]>([]);
  const [dataGroupingConfigurations, setDataGroupingConfigurations] = useState<
    DataGroupingConfigurationBasicModel[]
  >([]);
  const [jobId, setJobId] = useState<number>();
  const [loading, setLoading] = useState(false);
  const job = jobId ? job_dictionary_state[jobId] : undefined;

  const [refDataGroupingConfigurations, setRefDataGroupingConfigurations] =
    useState<DataGroupingConfigurationBasicModel[]>([]);
  const [dataGroupingConfiguration, setDataGroupingConfiguration] =
    useState<DataGroupingConfigurationBasicModel>();
  const [refDataGroupingConfiguration, setRefDataGroupingConfiguration] =
    useState<DataGroupingConfigurationBasicModel>();
  const [isElemExtended, setIsElemExtended] = useState<Array<boolean>>([]);
  const [isExtended, setIsExtended] = useState(false);
  const [tableComparisonResults, setTableComparisonResults] =
    useState<TableComparisonResultsModel>();

  const history = useHistory();
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  useEffect(() => {
    if (selectedReference) {
      const callback = (res: { data: TableComparisonModel }) => {
        setReference(res.data);
      };
      if (checkTypes === CheckTypes.PROFILING) {
        TableComparisonsApi.getTableComparisonProfiling(
          connection,
          schema,
          table,
          selectedReference
        ).then(callback);
      } else if (checkTypes === CheckTypes.RECURRING) {
        if (timePartitioned === 'daily') {
          TableComparisonsApi.getTableComparisonRecurringDaily(
            connection,
            schema,
            table,
            selectedReference
          ).then(callback);
        } else if (timePartitioned === 'monthly') {
          TableComparisonsApi.getTableComparisonRecurringMonthly(
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
    if (reference) {
      ColumnApiClient.getColumns(
        reference.reference_connection ?? '',
        reference.reference_table?.schema_name ?? '',
        reference.reference_table?.table_name ?? ''
      ).then((columnRes) => {
        setColumnOptions(
          columnRes.data.map((item) => ({
            label: item.column_name ?? '',
            value: item.column_name ?? ''
          }))
        );
      });
    }
  }, [selectedReference]);

  useEffect(() => {
    DataGroupingConfigurationsApi.getTableGroupingConfigurations(
      connection,
      schema,
      table
    ).then((res) => {
      setDataGroupingConfigurations(res.data);
      setDataGroupingConfiguration(
        res.data.find((item) => item.default_data_grouping_configuration)
      );
    });
  }, [connection, schema, table]);

  useEffect(() => {
    if (reference) {
      DataGroupingConfigurationsApi.getTableGroupingConfigurations(
        reference.reference_connection ?? '',
        reference.reference_table?.schema_name ?? '',
        reference.reference_table?.table_name ?? ''
      ).then((res) => {
        setRefDataGroupingConfigurations(res.data);
        setRefDataGroupingConfiguration(
          res.data.find((item) => item.default_data_grouping_configuration)
        );
      });
    }
    if (reference) {
      ColumnApiClient.getColumns(
        reference.reference_connection ?? '',
        reference.reference_table?.schema_name ?? '',
        reference.reference_table?.table_name ?? ''
      ).then((columnRes) => {
        setColumnOptions(
          columnRes.data.map((item) => ({
            label: item.column_name ?? '',
            value: item.column_name ?? ''
          }))
        );
      });
    }
  }, [reference, selectedReference]);

  const goToCreateNew = () => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      CheckTypes.SOURCES,
      connection,
      schema,
      table,
      'data-groupings'
    );
    const value = ROUTES.TABLE_LEVEL_VALUE(
      CheckTypes.SOURCES,
      connection,
      schema,
      table
    );

    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url: `${url}?isEditing=true`,
        value,
        label: reference?.reference_table?.table_name ?? '',
        state: {}
      })
    );
    history.push(`${url}?isEditing=true`);
  };

  const goToRefCreateNew = () => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      CheckTypes.SOURCES,
      reference?.reference_connection ?? '',
      reference?.reference_table?.schema_name ?? '',
      reference?.reference_table?.table_name ?? '',
      'data-groupings'
    );
    const value = ROUTES.TABLE_LEVEL_VALUE(
      CheckTypes.SOURCES,
      reference?.reference_connection ?? '',
      reference?.reference_table?.schema_name ?? '',
      reference?.reference_table?.table_name ?? ''
    );

    dispatch(
      addFirstLevelTab(CheckTypes.SOURCES, {
        url: `${url}?isEditing=true`,
        value,
        label: reference?.reference_table?.table_name ?? '',
        state: {}
      })
    );

    history.push(`${url}?isEditing=true`);
  };

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
    setIsUpdating(true);
    const data = {
      ...reference,
      compared_table_grouping_name:
        dataGroupingConfiguration?.data_grouping_configuration_name,
      reference_table_grouping_name:
        refDataGroupingConfiguration?.data_grouping_configuration_name
    };

    if (checkTypes === CheckTypes.PROFILING) {
      TableComparisonsApi.updateTableComparisonProfiling(
        connection,
        schema,
        table,
        reference?.table_comparison_configuration_name ?? '',
        data
      )
        .then(() => {
          onBack();
        })
        .catch((err) => {
          console.log(err);
        })
        .finally(() => {
          setIsUpdating(false);
        });
    } else if (checkTypes === CheckTypes.RECURRING) {
      if (timePartitioned === 'daily') {
        TableComparisonsApi.updateTableComparisonRecurringDaily(
          connection,
          schema,
          table,
          reference?.table_comparison_configuration_name ?? '',
          data
        )
          .then(() => {
            onBack();
          })
          .catch((err) => {
            console.log(err);
          })
          .finally(() => {
            setIsUpdating(false);
          });
      } else if (timePartitioned === 'monthly') {
        TableComparisonsApi.updateTableComparisonRecurringMonthly(
          connection,
          schema,
          table,
          reference?.table_comparison_configuration_name ?? '',
          data
        )
          .then(() => {
            onBack();
          })
          .catch((err) => {
            console.log(err);
          })
          .finally(() => {
            setIsUpdating(false);
          });
      }
    } else if (checkTypes === CheckTypes.PARTITIONED) {
      if (timePartitioned === 'daily') {
        TableComparisonsApi.updateTableComparisonPartitionedDaily(
          connection,
          schema,
          table,
          reference?.table_comparison_configuration_name ?? '',
          data
        )
          .then(() => {
            onBack();
          })
          .catch((err) => {
            console.log(err);
          })
          .finally(() => {
            setIsUpdating(false);
          });
      } else if (timePartitioned === 'monthly') {
        TableComparisonsApi.updateTableComparisonPartitionedMonthly(
          connection,
          schema,
          table,
          reference?.table_comparison_configuration_name ?? '',
          data
        )
          .then(() => {
            onBack();
          })
          .catch((err) => {
            console.log(err);
          })
          .finally(() => {
            setIsUpdating(false);
          });
      }
    }
    setIsUpdated(false);
  };

  const onChange = (obj: Partial<TableComparisonModel>) => {
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
  }, [showRowCount]);

  const handleExtend = (index: number) => {
    const newArr = [...isElemExtended];
    newArr[index] = !isElemExtended[index];
    setIsElemExtended(newArr);
  };

  const getResultsData = async () => {
    await TableComparisonResultsApi.getTableComparisonProfilingResults(
      connection,
      schema,
      table,
      selectedReference ?? ''
    ).then((res) => setTableComparisonResults(res.data));
    console.log(tableComparisonResults);
  };
  const onRunChecksRowCount = async () => {
    try {
      setLoading(true);
      const res = await JobApiClient.runChecks(false, undefined, {
        checkSearchFilters: categoryCheck?.run_checks_job_template
      });
      dispatch(
        setCurrentJobId(
          checkTypes,
          firstLevelActiveTab,
          (res.data as any)?.jobId?.jobId
        )
      );
      setJobId((res.data as any)?.jobId?.jobId);
    } catch (err) {
      console.error(err);
    } finally {
      getResultsData();
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

  console.log(reference);

  return (
    <div className="text-sm">
      <TableActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdated}
        isUpdating={isUpdating}
      />
      <div className="flex items-center justify-between border-b border-t border-gray-300 py-2 px-8">
        <div className="flex items-center gap-3">
          <span onClick={getResultsData}>
            Profiling table comparison using the table comparison configuration:
          </span>
          <span>{reference?.table_comparison_configuration_name}</span>
        </div>
        <Button
          label="Back"
          color="primary"
          variant="text"
          className="px-0 text-sm"
          leftIcon={<SvgIcon name="chevron-left" className="w-4 h-4 mr-2" />}
          onClick={() => onBack(false)}
        />
      </div>

      <div className="px-8 py-4 border-b border-gray-300 flex items-center justify-between">
        <div className="flex items-center gap-4">
          <span>Comparing this table to the reference table:</span>
          <a className="text-teal-500 cursor-pointer" onClick={goToRefTable}>
            {reference?.reference_connection}.
            {reference?.reference_table?.schema_name}.
            {reference?.reference_table?.table_name}
          </a>
        </div>
        <div className="flex justify-center items-center gap-x-2">
          {job?.status !== DqoJobHistoryEntryModelStatusEnum.succeeded &&
            job?.status !== DqoJobHistoryEntryModelStatusEnum.failed &&
            job?.status && (
              <SvgIcon
                name="sync"
                className={clsx('w-4 h-4 mr-3', loading ? 'animate-spin' : '')}
              />
            )}
          <Button
            label="Compare Tables"
            color="primary"
            variant="contained"
            onClick={onRunChecksRowCount}
            disabled={disabled || loading}
          />
        </div>
      </div>

      <div className="px-8 py-4">
        <p className="text-center mb-7">
          Table comparison will use these data grouping configurations:
        </p>

        <div className="flex gap-4 mb-8">
          <div>
            <div
              className="flex h-18 w-40 mt-8"
              onClick={() => setIsExtended(!isExtended)}
            >
              {isExtended === false ? (
                <SvgIcon
                  name="chevron-right"
                  className="w-5 h-5 text-gray-700 cursor-pointer"
                />
              ) : (
                <SvgIcon
                  name="chevron-down"
                  className="w-5 h-5 text-gray-700 cursor-pointer"
                />
              )}
              <span className="cursor-pointer">Data grouping name</span>
            </div>
            {isExtended === true && (
              <div>
                {[1, 2, 3, 4, 5, 6, 7, 8, 9].map((item, index) => (
                  <div key={index} className="text-sm py-1.5 w-44">
                    Column {item}
                  </div>
                ))}
              </div>
            )}
          </div>

          <SelectDataGroupingForTableProfiling
            className="flex-1"
            title="Data grouping on compared table"
            dataGroupingConfigurations={dataGroupingConfigurations}
            dataGroupingConfiguration={dataGroupingConfiguration}
            setDataGroupingConfiguration={setDataGroupingConfiguration}
            goToCreateNew={goToCreateNew}
            isExtended={isExtended}
            columnArray={reference?.grouping_columns?.map((x) =>
              typeof x.compared_table_column_name === 'string'
                ? x.compared_table_column_name
                : ''
            )}
          />

          <SelectDataGroupingForTableProfiling
            className="flex-1"
            title="Data grouping on reference table"
            dataGroupingConfigurations={refDataGroupingConfigurations}
            dataGroupingConfiguration={refDataGroupingConfiguration}
            setDataGroupingConfiguration={setRefDataGroupingConfiguration}
            goToCreateNew={goToRefCreateNew}
            isExtended={isExtended}
            columnArray={reference?.grouping_columns?.map((x) =>
              typeof x.reference_table_column_name === 'string'
                ? x.reference_table_column_name
                : ''
            )}
          />
        </div>

        <div className="px-4">
          <p>Default thresholds for differences(percent):</p>
          <div className="grid grid-cols-3 mb-5 mt-3">
            <div className="bg-yellow-100 px-4 py-2 flex items-center gap-2">
              <span className="flex-1">Warning when the difference above:</span>
              <Input
                className="max-w-30 !min-w-initial"
                type="number"
                value={
                  reference?.default_compare_thresholds
                    ?.warning_difference_percent
                }
              />
              %
            </div>
            <div className="bg-orange-100 px-4 py-2 flex items-center gap-2">
              <span className="flex-1">Error when the difference above:</span>
              <Input
                className="max-w-30 !min-w-initial"
                type="number"
                value={
                  reference?.default_compare_thresholds
                    ?.error_difference_percent
                }
              />
              %
            </div>
            <div className="bg-red-100 px-4 py-2 flex items-center gap-2">
              <span className="flex-1">
                Fatal Error when the difference above:
              </span>
              <Input
                className="max-w-30 !min-w-initial"
                type="number"
                value={
                  reference?.default_compare_thresholds
                    ?.fatal_difference_percent
                }
              />
              %
            </div>
          </div>
        </div>

        <SectionWrapper
          title="Table level comparison"
          className="mb-10 px-0 mt-10"
        >
          <div className="flex flex-col gap-8">
            <div className="flex gap-4">
              <span
                className="flex items-center cursor-pointer"
                onClick={() => setIsRowCountExtended(!isRowCountExtended)}
              >
                {isRowCountExtended ? (
                  <SvgIcon name="chevron-down" className="w-5 h-5" />
                ) : (
                  <SvgIcon name="chevron-right" className="w-5 h-5" />
                )}
                Compare row count between target and reference column:
              </span>
              <Checkbox
                checked={showRowCount}
                onChange={(checked) => setShowRowCount(checked)}
              />
            </div>

            {isRowCountExtended && (
              <div className="grid grid-cols-3 w-full">
                <div className="bg-yellow-100 px-4 py-2 flex items-center gap-2">
                  <span className="flex-1">
                    Warning when the difference above:
                  </span>
                  <Input
                    className="max-w-30 !min-w-initial"
                    type="number"
                    value={
                      reference?.compare_row_count?.warning_difference_percent
                    }
                    onChange={(e) =>
                      onChangeCompareRowCount({
                        warning_difference_percent: Number(e.target.value)
                      })
                    }
                  />
                  %
                </div>
                <div className="bg-orange-100 px-4 py-2 flex items-center gap-2">
                  <span className="flex-1">
                    Error when the difference above:
                  </span>
                  <Input
                    className="max-w-30 !min-w-initial"
                    type="number"
                    value={
                      reference?.compare_row_count?.error_difference_percent
                    }
                    onChange={(e) =>
                      onChangeCompareRowCount({
                        error_difference_percent: Number(e.target.value)
                      })
                    }
                  />
                  %
                </div>
                <div className="bg-red-100 px-4 py-2 flex items-center gap-2">
                  <span className="flex-1">
                    Fatal Error when the difference above:
                  </span>
                  <Input
                    className="max-w-30 !min-w-initial"
                    type="number"
                    value={
                      reference?.compare_row_count?.fatal_difference_percent
                    }
                    onChange={(e) =>
                      onChangeCompareRowCount({
                        fatal_difference_percent: Number(e.target.value)
                      })
                    }
                  />
                  %
                </div>
              </div>
            )}
          </div>
        </SectionWrapper>

        <SectionWrapper title="Column level comparison">
          <table className="w-full">
            <thead>
              <tr>
                <th className="text-left pr-4 py-1.5">Compared column</th>
                <th className="text-left px-4 py-1.5"></th>
                <th className="text-center px-4 py-1.5 pr-1 w-1/12">Min</th>
                <th className="text-center px-4 py-1.5 pr-1 w-1/12">Max</th>
                <th className="text-center px-4 py-1.5 pr-1 w-1/12">Sum</th>
                <th className="text-center px-4 py-1.5 pr-1 w-1/12">Mean</th>
                <th className="text-center px-4 py-1.5 pr-1 w-1/12">
                  Null count
                </th>
                <th className="text-center px-4 py-1.5 pr-1 w-1/12">
                  Not null count
                </th>
              </tr>
            </thead>
            {reference?.columns?.map((item, index) => (
              <tbody key={index}>
                <tr key={index}>
                  <td
                    className="text-left pr-4 py-1.5 flex items-center gap-x-2"
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
                      options={[{ value: '', label: '' }, ...columnOptions]}
                      onChange={(e) =>
                        onChangeColumn({ reference_column_name: e }, index)
                      }
                      empty={true}
                    />
                  </td>
                  <td className="text-center px-4 py-1.5">
                    <Checkbox
                      checked={
                        !!item.compare_min &&
                        !(
                          item.reference_column_name === undefined ||
                          item.reference_column_name.length === 0
                        )
                      }
                      onChange={(checked) =>
                        onChangeColumn(
                          {
                            compare_min: checked
                              ? reference?.compare_row_count
                              : undefined
                          },
                          index
                        )
                      }
                      disabled={
                        item.reference_column_name === undefined ||
                        item.reference_column_name.length === 0
                      }
                      isDisabled={
                        item.reference_column_name === undefined ||
                        item.reference_column_name.length === 0
                      }
                    />
                  </td>
                  <td className="text-center px-4 py-1.5">
                    <Checkbox
                      checked={
                        !!item.compare_max &&
                        !(
                          item.reference_column_name === undefined ||
                          item.reference_column_name.length === 0
                        )
                      }
                      onChange={(checked) =>
                        onChangeColumn(
                          {
                            compare_max: checked
                              ? reference?.compare_row_count
                              : undefined
                          },
                          index
                        )
                      }
                      disabled={
                        item.reference_column_name === undefined ||
                        item.reference_column_name.length === 0
                      }
                      isDisabled={
                        item.reference_column_name === undefined ||
                        item.reference_column_name.length === 0
                      }
                    />
                  </td>
                  <td className="text-center px-4 py-1.5">
                    <Checkbox
                      checked={
                        !!item.compare_sum &&
                        !(
                          item.reference_column_name === undefined ||
                          item.reference_column_name.length === 0
                        )
                      }
                      onChange={(checked) =>
                        onChangeColumn(
                          {
                            compare_sum: checked
                              ? reference?.compare_row_count
                              : undefined
                          },
                          index
                        )
                      }
                      disabled={
                        item.reference_column_name === undefined ||
                        item.reference_column_name.length === 0
                      }
                      isDisabled={
                        item.reference_column_name === undefined ||
                        item.reference_column_name.length === 0
                      }
                    />
                  </td>
                  <td className="text-center px-4 py-1.5">
                    <Checkbox
                      checked={
                        !!item.compare_mean &&
                        !(
                          item.reference_column_name === undefined ||
                          item.reference_column_name.length === 0
                        )
                      }
                      onChange={(checked) =>
                        onChangeColumn(
                          {
                            compare_mean: checked
                              ? reference?.compare_row_count
                              : undefined
                          },
                          index
                        )
                      }
                      disabled={
                        item.reference_column_name === undefined ||
                        item.reference_column_name.length === 0
                      }
                      isDisabled={
                        item.reference_column_name === undefined ||
                        item.reference_column_name.length === 0
                      }
                    />
                  </td>
                  <td className="text-center px-4 py-1.5">
                    <Checkbox
                      checked={
                        !!item.compare_null_count &&
                        !(
                          item.reference_column_name === undefined ||
                          item.reference_column_name.length === 0
                        )
                      }
                      onChange={(checked) =>
                        onChangeColumn(
                          {
                            compare_null_count: checked
                              ? reference?.compare_row_count
                              : undefined
                          },
                          index
                        )
                      }
                      disabled={
                        item.reference_column_name === undefined ||
                        item.reference_column_name.length === 0
                      }
                      isDisabled={
                        item.reference_column_name === undefined ||
                        item.reference_column_name.length === 0
                      }
                    />
                  </td>
                  <td className="text-center px-4 py-1.5">
                    <Checkbox
                      checked={
                        !!item.compare_not_null_count &&
                        !(
                          item.reference_column_name === undefined ||
                          item.reference_column_name.length === 0
                        )
                      }
                      onChange={(checked) =>
                        onChangeColumn(
                          {
                            compare_not_null_count: checked
                              ? reference?.compare_row_count
                              : undefined
                          },
                          index
                        )
                      }
                      disabled={
                        item.reference_column_name === undefined ||
                        item.reference_column_name.length === 0
                      }
                      isDisabled={
                        item.reference_column_name === undefined ||
                        item.reference_column_name.length === 0
                      }
                    />
                  </td>
                </tr>
                {isElemExtended.at(index) && (
                  <ResultPanel
                    minBool={
                      !!item.compare_min &&
                      !(
                        item.reference_column_name === undefined ||
                        item.reference_column_name.length === 0
                      )
                    }
                    maxBool={
                      !!item.compare_max &&
                      !(
                        item.reference_column_name === undefined ||
                        item.reference_column_name.length === 0
                      )
                    }
                    sumBool={
                      !!item.compare_sum &&
                      !(
                        item.reference_column_name === undefined ||
                        item.reference_column_name.length === 0
                      )
                    }
                    meanBool={
                      !!item.compare_mean &&
                      !(
                        item.reference_column_name === undefined ||
                        item.reference_column_name.length === 0
                      )
                    }
                    nullCount={
                      !!item.compare_null_count &&
                      !(
                        item.reference_column_name === undefined ||
                        item.reference_column_name.length === 0
                      )
                    }
                    notNullCount={
                      !!item.compare_not_null_count &&
                      !(
                        item.reference_column_name === undefined ||
                        item.reference_column_name.length === 0
                      )
                    }
                  />
                )}
              </tbody>
            ))}
          </table>
        </SectionWrapper>
      </div>
    </div>
  );
};
