import React, { useEffect, useState } from 'react';
import TableActionGroup from './TableActionGroup';
import Input from '../../Input';
import Button from '../../Button';
import SvgIcon from '../../SvgIcon';
import {
  ColumnApiClient,
  DataGroupingConfigurationsApi,
  TableComparisonsApi
} from '../../../services/apiClient';
import { CheckTypes, ROUTES } from '../../../shared/routes';
import { useHistory, useParams } from 'react-router-dom';
import {
  ColumnComparisonModel,
  CompareThresholdsModel,
  DataGroupingConfigurationBasicModel,
  TableComparisonModel
} from '../../../api';
import SectionWrapper from '../../Dashboard/SectionWrapper';
import Checkbox from '../../Checkbox';
import Select, { Option } from '../../Select';
import { SelectDataGroupingForTable } from './SelectDataGroupingForTable';
import { addFirstLevelTab } from '../../../redux/actions/source.actions';
import { useActionDispatch } from '../../../hooks/useActionDispatch';

type EditProfilingReferenceTableProps = {
  onBack: () => void;
  selectedReference?: string;
  checkTypes: CheckTypes;
  timePartitioned?: 'daily' | 'monthly';
};

export const EditProfilingReferenceTable = ({
  checkTypes,
  timePartitioned,
  onBack,
  selectedReference
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
  const [reference, setReference] = useState<TableComparisonModel>();
  const [showRowCount, setShowRowCount] = useState(false);
  const [columnOptions, setColumnOptions] = useState<Option[]>([]);
  const [dataGroupingConfigurations, setDataGroupingConfigurations] = useState<
    DataGroupingConfigurationBasicModel[]
  >([]);
  const [refDataGroupingConfigurations, setRefDataGroupingConfigurations] =
    useState<DataGroupingConfigurationBasicModel[]>([]);
  const [dataGroupingConfiguration, setDataGroupingConfiguration] =
    useState<DataGroupingConfigurationBasicModel>();
  const [refDataGroupingConfiguration, setRefDataGroupingConfiguration] =
    useState<DataGroupingConfigurationBasicModel>();
  const history = useHistory();
  const dispatch = useActionDispatch();

  useEffect(() => {
    if (selectedReference) {
      const callback = (res: { data: TableComparisonModel }) => {
        setReference(res.data);

        ColumnApiClient.getColumns(
          connection ?? '',
          schema ?? '',
          table ?? ''
        ).then((columnRes) => {
          setColumnOptions(
            columnRes.data.map((item) => ({
              label: item.column_name ?? '',
              value: item.column_name ?? ''
            }))
          );
        });
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
  }, [reference]);

  const goToCreateNew = () => {
    const url = ROUTES.TABLE_LEVEL_PAGE(
      CheckTypes.SOURCES,
      connection,
      schema,
      table,
      'data-streams'
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
      'data-streams'
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
        reference?.reference_table_configuration_name ?? '',
        data
      )
        .then((res) => {
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
          reference?.reference_table_configuration_name ?? '',
          data
        )
          .then((res) => {
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
          reference?.reference_table_configuration_name ?? '',
          data
        )
          .then((res) => {
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
          reference?.reference_table_configuration_name ?? '',
          data
        )
          .then((res) => {
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
          reference?.reference_table_configuration_name ?? '',
          data
        )
          .then((res) => {
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

  return (
    <div className="text-sm">
      <TableActionGroup
        onUpdate={onUpdate}
        isUpdated={isUpdated}
        isUpdating={isUpdating}
      />
      <div className="flex items-center justify-between border-b border-t border-gray-300 py-2 px-8">
        <div className="flex items-center gap-3">
          <span>
            Profiling table comparison using the reference table configuration:
          </span>
          <span>{reference?.reference_table_configuration_name}</span>
        </div>
        <Button
          label="Back"
          color="primary"
          variant="text"
          className="px-0 text-sm"
          leftIcon={<SvgIcon name="chevron-left" className="w-4 h-4 mr-2" />}
          onClick={onBack}
        />
      </div>

      <div className="px-8 py-4 border-b border-gray-300">
        <div className="flex items-center gap-4">
          <span>Comparing this table to the reference table:</span>
          <a className="text-teal-500 cursor-pointer" onClick={goToRefTable}>
            {reference?.reference_connection}.
            {reference?.reference_table?.schema_name}.
            {reference?.reference_table?.table_name}
          </a>
        </div>
      </div>

      <div className="px-8 py-4">
        <p className="mb-5">
          Table comparison will use these data grouping configurations:
        </p>

        <div className="grid grid-cols-2 gap-4 mb-5">
          <div className="flex gap-3 items-center">
            <span>Compared table:</span>
            <Input value={reference?.compared_table_grouping_name} />
          </div>
          <div className="flex gap-3 items-center">
            <span>Reference table:</span>
            <Input value={reference?.reference_table_grouping_name} />
          </div>
        </div>

        <div className="flex gap-4 mb-8">
          <div className="mt-26 mr-20">
            {[1, 2, 3, 4, 5, 6, 7, 8, 9].map((item, index) => (
              <div key={index} className="text-sm py-1.5">
                Grouping dimension level {item}
              </div>
            ))}
          </div>
          <SelectDataGroupingForTable
            className="flex-1"
            title="Data grouping on compared table"
            dataGroupingConfigurations={dataGroupingConfigurations}
            dataGroupingConfiguration={dataGroupingConfiguration}
            setDataGroupingConfiguration={setDataGroupingConfiguration}
            goToCreateNew={goToCreateNew}
          />
          <div className="flex flex-col justify-center">
            <SvgIcon name="not-equal" className="w-6 h-6 text-red-700" />
          </div>
          <SelectDataGroupingForTable
            className="flex-1"
            title="Data grouping on reference table"
            dataGroupingConfigurations={refDataGroupingConfigurations}
            dataGroupingConfiguration={refDataGroupingConfiguration}
            setDataGroupingConfiguration={setRefDataGroupingConfiguration}
            goToCreateNew={goToRefCreateNew}
          />
        </div>

        <p>Default thresholds for differences(percent):</p>
        <div className="grid grid-cols-3 mb-5">
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
                reference?.default_compare_thresholds?.error_difference_percent
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
                reference?.default_compare_thresholds?.fatal_difference_percent
              }
            />
            %
          </div>
        </div>

        <SectionWrapper title="Table level comparison" className="mb-10">
          <div className="flex items-center gap-8">
            <div className="flex items-center gap-4">
              <span>row_count:</span>
              <Checkbox
                checked={showRowCount}
                onChange={(checked) => setShowRowCount(checked)}
              />
            </div>

            {showRowCount && (
              <div className="grid grid-cols-3">
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
                <th className="text-left px-4 py-1.5">
                  <div className="flex items-center justify-between">
                    <span>Reference column</span>
                    <span className="font-medium">Compare</span>
                  </div>
                </th>
                <th className="text-left px-4 py-1.5">Min</th>
                <th className="text-left px-4 py-1.5">Max</th>
                <th className="text-left px-4 py-1.5">Sum</th>
                <th className="text-left px-4 py-1.5">Mean</th>
                <th className="text-left px-4 py-1.5">Null count</th>
                <th className="text-left px-4 py-1.5">Not null count</th>
              </tr>
            </thead>
            <tbody>
              {reference?.columns?.map((item, index) => (
                <tr key={index}>
                  <td className="text-left pr-4 py-1.5">
                    {item.compared_column_name}
                  </td>
                  <td className="text-left px-4 py-1.5">
                    <Select
                      value={item.reference_column_name}
                      options={columnOptions}
                      onChange={(e) =>
                        onChangeColumn({ reference_column_name: e }, index)
                      }
                    />
                  </td>
                  <td className="text-left px-4 py-1.5">
                    <Checkbox
                      checked={!!item.compare_min}
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
                    />
                  </td>
                  <td className="text-left px-4 py-1.5">
                    <Checkbox
                      checked={!!item.compare_max}
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
                    />
                  </td>
                  <td className="text-left px-4 py-1.5">
                    <Checkbox
                      checked={!!item.compare_sum}
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
                    />
                  </td>
                  <td className="text-left px-4 py-1.5">
                    <Checkbox
                      checked={!!item.compare_mean}
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
                    />
                  </td>
                  <td className="text-left px-4 py-1.5">
                    <Checkbox
                      checked={!!item.compare_null_count}
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
                    />
                  </td>
                  <td className="text-left px-4 py-1.5">
                    <Checkbox
                      checked={!!item.compare_not_null_count}
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
                    />
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </SectionWrapper>
      </div>
    </div>
  );
};
